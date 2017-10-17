package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

/**
 * Represents a class.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class ClassShape {

	// May not be static to get serialized correctly
	private String type;
	
	private int x;
	private int y;
	private String id;
	private String name;
	private AttributeCompartment attributeCompartment;
	private OperationCompartment operationCompartment;
	private String parentFigure;

	/**
	 * Creates a new class with empty attribute and operation lists.
	 */
	public ClassShape() {
		this.type = "ClassShape";
		this.attributeCompartment = new AttributeCompartment();
		this.operationCompartment = new OperationCompartment();
	}

	/**
	 * Returns the type of the figure.
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the absolute x position
	 * 
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the absolute x position
	 * 
	 * @param x
	 *            the new x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the absolute y position
	 * 
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the absolute y position
	 * 
	 * @param y
	 *            the new y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the unique Id of the class
	 * 
	 * @return a unique id string
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique Id of the class
	 * 
	 * @param id
	 *            a unique id string
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the name of the class
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the class
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the attribute part of the class
	 * 
	 * @return a attribute compartment
	 */
	public AttributeCompartment getAttributeCompartment() {
		return attributeCompartment;
	}

	/**
	 * Sets the attribute part of the class
	 * 
	 * @param attributeCompartment
	 *            a valid attribute compartment
	 */
	public void setAttributeCompartment(AttributeCompartment attributeCompartment) {
		this.attributeCompartment = attributeCompartment;
	}

	/**
	 * Returns the operation part of the class
	 * 
	 * @return a operation compartment
	 */
	public OperationCompartment getOperationCompartment() {
		return operationCompartment;
	}

	/**
	 * Sets the operation part of the class
	 * 
	 * @param operationCompartment
	 *            a valid operation compartment
	 */
	public void setOperationCompartment(OperationCompartment operationCompartment) {
		this.operationCompartment = operationCompartment;
	}

	/**
	 * Returns the parent figure Id
	 * 
	 * @return the unique id of the parent figure
	 */
	public String getParentFigure() {
		return parentFigure;
	}

	/**
	 * Sets the parent figure Id
	 * 
	 * @param parentFigure
	 *            the unique Id of the parent figure
	 */
	public void setParentFigure(String parentFigure) {
		this.parentFigure = parentFigure;
	}

}
