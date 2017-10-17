package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the attribute list of class element.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class AttributeCompartment {

	private Collection<ListEntry> attributes;

	/**
	 * Creates a new compartment with zero attributes.
	 */
	public AttributeCompartment() {
		this.attributes = new ArrayList<ListEntry>();
	}

	/**
	 * Returns the list of all attributes
	 * 
	 * @return a collection of list entries
	 */
	public Collection<ListEntry> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the list of all attributes
	 * 
	 * @param attributes
	 *            a collection of list entries
	 */
	public void setAttributes(Collection<ListEntry> attributes) {
		this.attributes = attributes;
	}

}
