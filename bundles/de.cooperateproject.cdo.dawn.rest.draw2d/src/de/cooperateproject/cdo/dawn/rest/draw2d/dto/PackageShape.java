package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

/**
 * Represents a package.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class PackageShape {

	// May not be static to get serialized correctly
	private String type;

	private int x;
	private int y;
	private String id;
	private String name;
	private int height;
	private int weight;
	private AboardFigures aboardFigures;
	private String parentFigure;

	/**
	 * Creates a new package.
	 */
	public PackageShape() {
		type = "PackageShape";
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
	 * Returns the unique Id of the package
	 * 
	 * @return a unique id string
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the unique Id of the package
	 * 
	 * @param id
	 *            a unique id string
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the name of the package
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the package
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the height of the package figure.
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the package figure.
	 * 
	 * @param height
	 *            the height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the weight of the package figure.
	 * 
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight of the package figure.
	 * 
	 * @param weight
	 *            the weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Returns a list of all aboard figures.
	 * 
	 * @return a aboard figure object containing unique Ids
	 */
	public AboardFigures getAboardFigures() {
		return aboardFigures;
	}

	/**
	 * Sets the list of all aboard figures.
	 * 
	 * @param aboardFigures
	 *            a aboard figure object containing unique Ids
	 */
	public void setAboardFigures(AboardFigures aboardFigures) {
		this.aboardFigures = aboardFigures;
	}

	/**
	 * Returns the unique id of the parent figure
	 * 
	 * @return a unique id
	 */
	public String getParentFigure() {
		return parentFigure;
	}

	/**
	 * Sets the unique id of the parent figure
	 * 
	 * @param parentFigure
	 *            the unique id
	 */
	public void setParentFigure(String parentFigure) {
		this.parentFigure = parentFigure;
	}

}
