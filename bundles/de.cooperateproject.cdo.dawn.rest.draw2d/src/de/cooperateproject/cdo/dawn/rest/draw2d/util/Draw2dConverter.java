package de.cooperateproject.cdo.dawn.rest.draw2d.util;

import java.util.ArrayList;
import java.util.Collection;

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
						classes.addAll(elements2classes(((Node) o).getChildren(), baseX, baseY));
					}
				}
			}
		}

		return classes;
	}

	private static ClassShape node2class(Node node, int baseX, int baseY) {

		ClassShape classShape = new ClassShape();

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
		classShape.setName(clazz.getName());

		// Attributes
		for (Property attribute : clazz.getAttributes()) {
			System.out.println(attribute);
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
		if (node.eContainer() != null) {
			classShape.setParentFigure(DawnWebUtil.getUniqueId(node.eContainer()));
		}

		return classShape;
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
