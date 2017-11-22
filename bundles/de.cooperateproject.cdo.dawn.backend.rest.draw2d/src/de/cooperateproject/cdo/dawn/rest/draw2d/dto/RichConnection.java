package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

/**
 * Represents a connection with custom connecotor type.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class RichConnection {

	// May not be static to get serialized correctly
	private String type;

	private String id;
	private String labelText;
	private String decorationType;
	private boolean directionSourceToTarget;
	private RichConnectionEnd source;
	private RichConnectionEnd target;

	/**
	 * Creates a new connection if empty source and target.
	 */
	public RichConnection() {
		type = "RichConnection";
		source = new RichConnectionEnd();
		target = new RichConnectionEnd();
	}

	/**
	 * Returns the type of the figure.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the unique id of the connection
	 * 
	 * @return the unique id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique id of the conneciton
	 * 
	 * @param id
	 *            the unique id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the connection label. Might be empty.
	 * 
	 * @return the label value / text
	 */
	public String getLabelText() {
		return labelText;
	}

	/**
	 * Sets the connection label. Might be empty.
	 * 
	 * @param labelText
	 *            the label value / text
	 */
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	/**
	 * Returns the decoration type of the connection.
	 * 
	 * @return a string representing the custom decoration
	 */
	public String getDecorationType() {
		return decorationType;
	}

	/**
	 * Sets the decoration type of the connection.
	 * 
	 * @param decorationType
	 *            valid decoration types can be found in the client
	 *            implementation
	 */
	public void setDecorationType(String decorationType) {
		this.decorationType = decorationType;
	}

	/**
	 * Returns the direction of the connection
	 * 
	 * @return true, if the connection points source to target
	 */
	public boolean isDirectionSourceToTarget() {
		return directionSourceToTarget;
	}

	/**
	 * Sets the direction of the connection. The direction is used for cutom
	 * decoration rendering.
	 * 
	 * @param directionSourceToTarget
	 *            true, if connected from source to target
	 */
	public void setDirectionSourceToTarget(boolean directionSourceToTarget) {
		this.directionSourceToTarget = directionSourceToTarget;
	}

	/**
	 * Returns the connection end of the source point.
	 * 
	 * @return a connection end containing a unique Id of the source figure
	 */
	public RichConnectionEnd getSource() {
		return source;
	}

	/**
	 * Sets the connection end of the source point.
	 * 
	 * @param source
	 *            a valid connection end containing a unique Id of a figure
	 */
	public void setSource(RichConnectionEnd source) {
		this.source = source;
	}

	/**
	 * Returns the connection end of the target point.
	 * 
	 * @return a connection end, containing a unique Id of the target figure
	 */
	public RichConnectionEnd getTarget() {
		return target;
	}

	/**
	 * Sets the connection end of the target point.
	 * 
	 * @param source
	 *            a valid connection end containing a unique Id of a figure
	 */
	public void setTarget(RichConnectionEnd target) {
		this.target = target;
	}

}
