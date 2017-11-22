package de.cooperateproject.cdo.dawn.rest.draw2d.api;

import java.util.Collection;

import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ClassShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.RichConnection;

/**
 * The draw2d service is used to get general diagram information for graphical
 * editors.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public interface Draw2dService {

	/**
	 * Returns a collection of all classes in the diagram.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a collection of Class DTOs
	 */
	Collection<ClassShape> getClasses(String projectId, String modelId);

	/**
	 * Returns a collection of all packages in the diagram.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a collection of package DTOs
	 */
	Collection<PackageShape> getPackages(String projectId, String modelId);

	/**
	 * Returns a collection of all connectors / edges in the diagram.
	 * 
	 * @param projectId
	 *            the projectId (project name) of a existing project
	 * @param modelId
	 *            the modelId (model name) of a existing model
	 * @return a collection of connection DTOs
	 */
	Collection<RichConnection> getConnections(String projectId, String modelId);
}
