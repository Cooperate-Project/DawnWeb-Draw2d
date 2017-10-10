package de.cooperateproject.cdo.dawn.rest.draw2d.api;

import java.util.Collection;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ClassShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.RichConnection;

public interface Draw2dService {

	Collection<ClassShape> getClasses(String projectId, String modelId);

	Collection<PackageShape> getPackages(String projectId, String modelId);

	Collection<RichConnection> getConnections(String projectId, String modelId);
}
