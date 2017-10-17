package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

/**
 * Represents the end / port of a connection.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class RichConnectionEnd {

	private String node;
	private String port;

	/**
	 * Returns the unique id of the connected figure.
	 * 
	 * @return a unique id of a figure
	 */
	public String getNode() {
		return node;
	}

	/**
	 * Sets the unique id of the connected figure.
	 * 
	 * @param node
	 *            a unique id of a figure
	 */
	public void setNode(String node) {
		this.node = node;
		this.port = "port_" + node;
	}

	/**
	 * Returns the unique id of the connected port
	 * 
	 * @return a unique id of the figure port
	 */
	public String getPort() {
		return port;
	}

}
