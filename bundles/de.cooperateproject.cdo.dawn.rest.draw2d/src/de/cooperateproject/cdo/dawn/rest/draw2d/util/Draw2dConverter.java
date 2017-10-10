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
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.Draw2dLabel;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ListEntry;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.RichConnection;
import de.cooperateproject.cdo.dawn.rest.util.DawnWebUtil;

public class Draw2dConverter {

	@SuppressWarnings("unchecked")
	public static Collection<ClassShape> diagram2classes(Diagram diagram) {

		// TODO: Test parent figure
		// TODO: Implement package + connection (edges)

		return elements2classes(diagram.getChildren(), 0, 0);

	}

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

	@SuppressWarnings("unchecked")
	public static Collection<PackageShape> diagram2packages(Diagram diagram) {
		return elements2packages(diagram.getChildren(), 0, 0);
	}

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

		// TODO: aboardFigures

		// Parent figure
		if (node.eContainer() != null && !(node.eContainer() instanceof Diagram)) {
			packageShape.setParentFigure(DawnWebUtil.getUniqueId(node.eContainer()));
		}

		return packageShape;
	}

	public static Collection<RichConnection> diagram2connections(Diagram diagram) {

		ArrayList<RichConnection> connections = new ArrayList<RichConnection>();

		@SuppressWarnings("unchecked")
		Collection<Connector> edges = diagram.getEdges();

		for (Connector edge : edges) {
			connections.add(edge2connection(edge));
		}

		return connections;
	}

	private static RichConnection edge2connection(Connector edge) {

		RichConnection connection = new RichConnection();
		
		// Ends
		connection.getSource().setNode(DawnWebUtil.getUniqueId(edge.getSource()));
		connection.getTarget().setNode(DawnWebUtil.getUniqueId(edge.getTarget()));
		connection.setDirectionSourceToTarget(true);
		
		// Connection type
		// TODO: Richer support for connection types
		
		switch(edge.getType()) {
		
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

	public static Collection<Draw2dLabel> diagram2labels(Diagram diagram) {

		ArrayList<Draw2dLabel> labels = new ArrayList<Draw2dLabel>();

		for (Object o : diagram.getChildren()) {

			// TODO: Separate types from structure, more abstract
			if (o instanceof Node) {
				Draw2dLabel label = new Draw2dLabel();

				// Id
				Node node = (Node) o;
				label.setId(DawnWebUtil.getUniqueId(node));

				// X, Y
				LayoutConstraint l = node.getLayoutConstraint();
				if (l instanceof Location) {
					label.setX(((Location) l).getX());
					label.setY(((Location) l).getY());
				}

				// Size
				// FIXME: Default values are -1, calculate own values
				if (l instanceof Size) {
					// label.setWeight(((Size) l).getWidth());
					// label.setHeight(((Size) l).getHeight());
					label.setWeight(100);
					label.setHeight(40);
				}

				// Text
				if (node.getElement() instanceof org.eclipse.uml2.uml.NamedElement) {
					org.eclipse.uml2.uml.NamedElement clazz = (org.eclipse.uml2.uml.NamedElement) node.getElement();
					label.setText(clazz.getName());
				}
				if (node.getElement() instanceof org.eclipse.uml2.uml.Package) {
					label.setText(String.format("[%s]", label.getText()));
				}

				labels.add(label);
			}

		}

		return labels;
	}

}
