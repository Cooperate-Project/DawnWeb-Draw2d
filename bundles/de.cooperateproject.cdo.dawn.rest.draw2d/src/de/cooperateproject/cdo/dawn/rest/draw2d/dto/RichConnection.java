package de.cooperateproject.cdo.dawn.rest.draw2d.dto;

public class RichConnection {

	private String type;
	private String id;
	private String labelText;
	private String decorationType;
	private boolean directionSourceToTarget;
	private RichConnectionEnd source;
	private RichConnectionEnd target;

	public RichConnection() {
		type = "RichConnection";
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	public String getDecorationType() {
		return decorationType;
	}

	public void setDecorationType(String decorationType) {
		this.decorationType = decorationType;
	}

	public boolean isDirectionSourceToTarget() {
		return directionSourceToTarget;
	}

	public void setDirectionSourceToTarget(boolean directionSourceToTarget) {
		this.directionSourceToTarget = directionSourceToTarget;
	}

	public RichConnectionEnd getSource() {
		return source;
	}

	public void setSource(RichConnectionEnd source) {
		this.source = source;
	}

	public RichConnectionEnd getTarget() {
		return target;
	}

	public void setTarget(RichConnectionEnd target) {
		this.target = target;
	}

}
