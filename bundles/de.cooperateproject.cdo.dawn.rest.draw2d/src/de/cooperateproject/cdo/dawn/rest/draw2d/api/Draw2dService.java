package de.cooperateproject.cdo.dawn.rest.draw2d.api;

import java.util.Collection;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.Draw2dLabel;

public interface Draw2dService {
	
	Collection<Draw2dLabel> getClassesAsLabels(String projectId, String modelId);

}
