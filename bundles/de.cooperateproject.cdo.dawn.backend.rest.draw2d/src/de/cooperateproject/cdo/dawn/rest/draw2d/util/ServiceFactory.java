package de.cooperateproject.cdo.dawn.rest.draw2d.util;

import de.cooperateproject.cdo.dawn.rest.draw2d.api.Draw2dService;
import de.cooperateproject.cdo.dawn.rest.draw2d.impl.Draw2dServiceImpl;

/**
 * The Service Factory creates instances of the draw2d service API with the
 * project private implementations.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class ServiceFactory {

	private static ServiceFactory instance = new ServiceFactory();

	public static ServiceFactory getInstance() {
		return instance;
	}

	public Draw2dService getDraw2dService() {
		return new Draw2dServiceImpl();
	}

}
