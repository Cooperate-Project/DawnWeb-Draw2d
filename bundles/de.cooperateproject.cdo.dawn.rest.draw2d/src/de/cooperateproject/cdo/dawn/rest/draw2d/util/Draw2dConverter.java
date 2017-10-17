package de.cooperateproject.cdo.dawn.rest.draw2d.util;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ClassShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ListEntry;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.RichConnection;
import de.cooperateproject.cdo.dawn.rest.util.DawnWebUtil;

/**
 * The draw2d converter is the current solution of converting papyrus diagram
 * information to draw2d data transfer objects. Basically this is a simple meta
 * model to meta model transformation.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Draw2dConverter {

	// TODO: Maybe it is possible to refactor the class and package conversion
	// by merging the conversion methods.

	/**
	 * Extracts all classes from a diagram.
	 * 
	 * @param diagram
	 *            a papyrus diagram
	 * @return a collection of class DTOs
	 */
	@SuppressWarnings("unchecked")
	public static Collection<ClassShape> diagram2classes(Diagram diagram) {

		return elements2classes(diagram.getChildren(), 0, 0);

	}

	/**
	 * Converts a collection of elements (nodes) to a collection of classes.
	 * 
	 * @param elements
	 *            a collection of papyrus diagram nodes
	 * @param baseX
	 *            the base x position of the parent object (e.g. the root or a
	 *            package)
	 * @param baseY
	 *            the base y position of the parent object (e.g. the root or a
	 *            package)
	 * @return a collection of class DTOs
	 */
	@SuppressWarnings("unchecked")
	private static Collection<ClassShape> elements2classes(Collection<Object> elements, int baseX, int baseY) {

		ArrayList<ClassShape> classes = new ArrayList<ClassShape>();

		for (Object o : elements) {
			if (o instanceof Node) {

				// Add class
				if (((Node) o).getElement() instanceof Class) {
					classes.add(node2class((Node) o, baseX, baseY));

					// Add all child classes if package
				} else if (((Node) o).getElement() instanceof Package) {

					// Use package location, as class location is package
					// relative
					LayoutConstraint l = ((Node) o).getLayoutConstraint();
					if (l instanceof Location) {
						baseX = baseX + ((Location) l).getX();
						baseY = baseY + ((Location) l).getY();

						// FIXME: getChildren() is not the correct method,
						// create wrong relations
						classes.addAll(elements2classes(((Node) o).getChildren(), baseX, baseY));
					}
				}
			}
		}

		return classes;
	}

	/**
	 * Converts a papyrus node to a class.
	 * 
	 * @param node
	 *            the papyrus node from a diagram
	 * @param baseX
	 *            the base x position of the parent object (e.g. the root or a
	 *            package)
	 * @param baseY
	 *            the base y position of the parent object (e.g. the root or a
	 *            package)
	 * @return a class DTO
	 */
	private static ClassShape node2class(Node node, int baseX, int baseY) {

		ClassShape classShape = new ClassShape();

		// Current fix: Reset base-values when not in a package
		if (node.eContainer() instanceof Diagram) {
			baseX = 0;
			baseY = 0;
		}

		// ID
		classShape.setId(DawnWebUtil.getUniqueId(node));

		// Location
		LayoutConstraint l = node.getLayoutConstraint();
		if (l instanceof Location) {
			classShape.setX(((Location) l).getX() + baseX);
			classShape.setY(((Location) l).getY() + baseY);
		}

		// Name
		Class clazz = (Class) node.getElement();
		String className = clazz.getName();
		classShape.setName(className);

		// Attributes
		for (Property attribute : clazz.getAttributes()) {
			ListEntry entry = new ListEntry();
			String name = attribute.getName();
			String type = (attribute.getType() == null) ? "" : attribute.getType().getName();
			entry.setText(String.format("%s : %s", name, type));
			classShape.getAttributeCompartment().getAttributes().add(entry);
		}

		// Operations
		for (Operation operation : clazz.getOperations()) {
			ListEntry entry = new ListEntry();
			String name = operation.getName();
			String type = (operation.getType() == null) ? "void" : operation.getType().getName();
			entry.setText(String.format("%s : %s", name, type));
			classShape.getOperationCompartment().getOperations().add(entry);
		}

		// Parent figure
		if (node.eContainer() != null && !(node.eContainer() instanceof Diagram)) {
			classShape.setParentFigure(DawnWebUtil.getUniqueId(node.eContainer().eContainer()));
		}

		return classShape;
	}

	/**
	 * Extracts all packages from a diagram.
	 * 
	 * @param diagram
	 *            a papyrus diagram
	 * @return a collection of package DTOs
	 */
	@SuppressWarnings("unchecked")
	public static Collection<PackageShape> diagram2packages(Diagram diagram) {
		return elements2packages(diagram.getChildren(), 0, 0);
	}

	/**
	 * Converts a collection of elements (nodes) to a collection of packages.
	 * 
	 * @param elements
	 *            a collection of papyrus diagram nodes
	 * @param baseX
	 *            the base x position of the parent object (e.g. the root or a
	 *            package)
	 * @param baseY
	 *            the base y position of the parent object (e.g. the root or a
	 *            package)
	 * @return a collection of package DTOs
	 */
	private static Collection<PackageShape> elements2packages(Collection<Object> elements, int baseX, int baseY) {
		ArrayList<PackageShape> packages = new ArrayList<PackageShape>();

		for (Object o : elements) {
			if (o instanceof Node) {
				if (((Node) o).getElement() instanceof Package) {

					packages.add(node2package((Node) o, baseX, baseY));

					LayoutConstraint l = ((Node) o).getLayoutConstraint();
					if (l instanceof Location) {
						baseX = baseX + ((Location) l).getX();
						baseY = baseY + ((Location) l).getY();

						// TODO: Iterate over nested packages
					}
				}
			}
		}

		return packages;
	}

	/**
	 * Converts a papyrus node to a package.
	 * 
	 * @param node
	 *            the papyrus node from a diagram
	 * @param baseX
	 *            the base x position of the parent object (e.g. the root or a
	 *            package)
	 * @param baseY
	 *            the base y position of the parent object (e.g. the root or a
	 *            package)
	 * @return a package DTO
	 */
	private static PackageShape node2package(Node node, int baseX, int baseY) {

		PackageShape packageShape = new PackageShape();

		// ID
		packageShape.setId(DawnWebUtil.getUniqueId(node));

		// Location
		LayoutConstraint l = node.getLayoutConstraint();
		if (l instanceof Location) {
			packageShape.setX(((Location) l).getX() + baseX);
			packageShape.setY(((Location) l).getY() + baseY);
		}

		// Name
		Package pg = (Package) node.getElement();
		packageShape.setName(pg.getName());

		// Size
		if (l instanceof Size) {
			packageShape.setWeight(((Size) l).getWidth());
			packageShape.setHeight(((Size) l).getHeight());
		}

		// TODO: aboardFigures (maybe never used by the client, maybe
		// deprecated)

		// Parent figure
		if (node.eContainer() != null && !(node.eContainer() instanceof Diagram)) {
			packageShape.setParentFigure(DawnWebUtil.getUniqueId(node.eContainer()));
		}

		return packageShape;
	}

	/**
	 * Extracts all connections from a diagram.
	 * 
	 * @param diagram
	 *            a papyrus diagram
	 * @return a collection of connection DTOs
	 */
	public static Collection<RichConnection> diagram2connections(Diagram diagram) {

		ArrayList<RichConnection> connections = new ArrayList<RichConnection>();

		@SuppressWarnings("unchecked")
		Collection<Connector> edges = diagram.getEdges();

		for (Connector edge : edges) {
			connections.add(edge2connection(edge));
		}

		return connections;
	}

	/**
	 * Converts a diagram edge to a connection.
	 * 
	 * @param edge
	 *            the diagram edge (connector)
	 * @return a connection DTO
	 */
	private static RichConnection edge2connection(Connector edge) {

		RichConnection connection = new RichConnection();

		// Ends
		connection.getSource().setNode(DawnWebUtil.getUniqueId(edge.getSource()));
		connection.getTarget().setNode(DawnWebUtil.getUniqueId(edge.getTarget()));
		connection.setDirectionSourceToTarget(true);

		// Connection type
		// TODO: Richer support for connection types

		switch (edge.getType()) {

		case "Generalization_Edge":
			connection.setDecorationType("inheritance");
			break;
		case "Association_Edge":
			connection.setDecorationType("association");
			break;
		case "Realization_Edge":
			connection.setDecorationType("realization");
			break;
		case "Dependency_Edge":
			connection.setDecorationType("dependency");
			break;
		default:
			connection.setDecorationType("connection");
			break;
		}

		// TODO: Label
		connection.setLabelText("");

		return connection;
	}

}
