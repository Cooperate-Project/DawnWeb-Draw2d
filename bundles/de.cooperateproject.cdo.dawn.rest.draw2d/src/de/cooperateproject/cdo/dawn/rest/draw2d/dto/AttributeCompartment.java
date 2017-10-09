package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

import java.util.ArrayList;
import java.util.Collection;

public class AttributeCompartment {

	private Collection<ListEntry> attributes;
	
	public AttributeCompartment() {
		this.attributes = new ArrayList<ListEntry>();
	}

	public Collection<ListEntry> getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection<ListEntry> attributes) {
		this.attributes = attributes;
	}

}
