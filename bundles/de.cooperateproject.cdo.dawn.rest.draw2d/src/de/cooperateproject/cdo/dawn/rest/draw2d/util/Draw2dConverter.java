package de.cooperateproject.cdo.dawn.rest.draw2d.util;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Size;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.Draw2dLabel;
import de.cooperateproject.cdo.dawn.rest.util.DawnWebUtil;

public class Draw2dConverter {

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
