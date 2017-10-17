package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the operation list of a class element.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class OperationCompartment {

	private Collection<ListEntry> operations;

	/**
	 * Creates a new operation list with zero operations.
	 */
	public OperationCompartment() {
		this.operations = new ArrayList<ListEntry>();
	}

	/**
	 * Returns a list of all operations
	 * 
	 * @return a collection of list entries
	 */
	public Collection<ListEntry> getOperations() {
		return operations;
	}

	/**
	 * Sets the list of all operations
	 * 
	 * @param operations
	 *            a collection of list entries
	 */
	public void setOperations(Collection<ListEntry> operations) {
		this.operations = operations;
	}

}
