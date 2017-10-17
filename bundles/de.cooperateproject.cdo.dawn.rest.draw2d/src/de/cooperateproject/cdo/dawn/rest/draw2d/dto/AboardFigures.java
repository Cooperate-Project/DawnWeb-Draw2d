package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

import java.util.Collection;

/**
 * Represents the figures (e.g. classes) in a container (e.g. package).
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class AboardFigures {

	private Collection<String> data;

	/**
	 * Returns the aboard figures.
	 * 
	 * @return a collection of IDs, representing the aboard figures
	 */
	public Collection<String> getData() {
		return data;
	}

	/**
	 * Set the aboard figures.
	 * 
	 * @param data
	 *            a collection with the IDs of all contained figures
	 */
	public void setData(Collection<String> data) {
		this.data = data;
	}

}
