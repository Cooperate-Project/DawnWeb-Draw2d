package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

import java.util.ArrayList;
import java.util.Collection;

public class OperationCompartment {

	private Collection<ListEntry> operations;

	public OperationCompartment() {
		this.operations = new ArrayList<ListEntry>();
	}

	public Collection<ListEntry> getOperations() {
		return operations;
	}

	public void setOperations(Collection<ListEntry> operations) {
		this.operations = operations;
	}

}
