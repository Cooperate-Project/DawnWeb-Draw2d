package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

public class ClassShape {

	private String type;
	private int x;
	private int y;
	private String id;
	private String name;
	private AttributeCompartment attributeCompartment;
	private OperationCompartment operationCompartment;
	private String parentFigure;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AttributeCompartment getAttributeCompartment() {
		return attributeCompartment;
	}

	public void setAttributeCompartment(AttributeCompartment attributeCompartment) {
		this.attributeCompartment = attributeCompartment;
	}

	public OperationCompartment getOperationCompartment() {
		return operationCompartment;
	}

	public void setOperationCompartment(OperationCompartment operationCompartment) {
		this.operationCompartment = operationCompartment;
	}

	public String getParentFigure() {
		return parentFigure;
	}

	public void setParentFigure(String parentFigure) {
		this.parentFigure = parentFigure;
	}

}
