package de.cooperateproject.cdo.dawn.rest.draw2d.api;

import java.util.Collection;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ClassShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.Draw2dLabel;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;

public interface Draw2dService {

	Collection<Draw2dLabel> getClassesAsLabels(String projectId, String modelId);

	boolean setLabelsAsClasses(String labels, String projectId, String modelId);

	Collection<ClassShape> getClasses(String projectId, String modelId);

	Collection<PackageShape> getPackages(String projectId, String modelId);
}
