package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

public class PackageShape {

	private String type;
	private int x;
	private int y;
	private String id;
	private String name;
	private int height;
	private int weight;
	private AboardFigures aboardFigures;

	public PackageShape() {
		type = "PackageShape";
	}

	public String getType() {
		return type;
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public AboardFigures getAboardFigures() {
		return aboardFigures;
	}

	public void setAboardFigures(AboardFigures aboardFigures) {
		this.aboardFigures = aboardFigures;
	}

}
