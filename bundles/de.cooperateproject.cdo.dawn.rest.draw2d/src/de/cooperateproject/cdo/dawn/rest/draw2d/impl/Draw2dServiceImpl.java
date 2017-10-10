package de.cooperateproject.cdo.dawn.rest.draw2d.impl;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.gmf.runtime.notation.Diagram;

import de.cooperateproject.cdo.dawn.rest.api.DiagramService;
import de.cooperateproject.cdo.dawn.rest.draw2d.api.Draw2dService;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.ClassShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.Draw2dLabel;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.PackageShape;
import de.cooperateproject.cdo.dawn.rest.draw2d.dto.RichConnection;
import de.cooperateproject.cdo.dawn.rest.draw2d.util.Draw2dConverter;
import de.cooperateproject.cdo.dawn.rest.util.ServiceFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces(MediaType.APPLICATION_JSON)
@Path("/draw2d")
@Api(value = "/draw2d")
public class Draw2dServiceImpl implements Draw2dService {

	private Diagram getDiagram(String projectId, String modelId) {
		DiagramService diagramService = ServiceFactory.getInstance().getDiagramService();
		return diagramService.getDiagram(projectId, modelId);
	}

	@Override
	@GET
	@Path("/labels/{projectId}/{modelId}")
	@ApiOperation(value = "Renders class as draw2d labels", response = Draw2dLabel.class, responseContainer = "List")
	public Collection<Draw2dLabel> getClassesAsLabels(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		Diagram diagram = getDiagram(projectId, modelId);
		return Draw2dConverter.diagram2labels(diagram);
	}

	@Override
	@POST
	@Path("/update/{projectId}/{modelId}")
	// @Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Updates diagrams with data from draw2d labels")
	public boolean setLabelsAsClasses(@FormParam("labels") String labels, @PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {

		System.out.println(labels);

		// TODO: Implement?
		return false;
	}

	@Override
	@GET
	@Path("/classes/{projectId}/{modelId}")
	@ApiOperation(value = "Returns diagram classes as draw2d ClassShapes.", response = ClassShape.class, responseContainer = "List")
	public Collection<ClassShape> getClasses(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		Diagram diagram = getDiagram(projectId, modelId);
		return Draw2dConverter.diagram2classes(diagram);
	}

	@Override
	@GET
	@Path("/packages/{projectId}/{modelId}")
	@ApiOperation(value = "Returns diagram packages as draw2d PackageShapes.", response = PackageShape.class, responseContainer = "List")
	public Collection<PackageShape> getPackages(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		Diagram diagram = getDiagram(projectId, modelId);
		return Draw2dConverter.diagram2packages(diagram);
	}

	@Override
	@GET
	@Path("/edges/{projectId}/{modelId}")
	@ApiOperation(value = "Returns diagram connections as draw2d RichConnections.", response = RichConnection.class, responseContainer = "List")
	public Collection<RichConnection> getConnections(@PathParam("projectId") String projectId,
			@PathParam("modelId") String modelId) {
		Diagram diagram = getDiagram(projectId, modelId);
		return Draw2dConverter.diagram2connections(diagram);
	}

}
