package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

public class RichConnectionEnd {

	private String node;
	private String port;

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
		this.port = "port_" + node;
	}

	public String getPort() {
		return port;
	}

}
