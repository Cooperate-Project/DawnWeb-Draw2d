package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

/**
 * Represents a list entry of a compartment.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class ListEntry {

	private String text;
	private String id;

	/**
	 * Returns the text / value of the entry
	 * 
	 * @return the value
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text / value of the entry
	 * 
	 * @param text
	 *            the value
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Returns the unique Id of the entry
	 * 
	 * @return the unique Id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique id of the entry
	 * 
	 * @param id
	 *            the unique id
	 */
	public void setId(String id) {
		this.id = id;
	}

}
