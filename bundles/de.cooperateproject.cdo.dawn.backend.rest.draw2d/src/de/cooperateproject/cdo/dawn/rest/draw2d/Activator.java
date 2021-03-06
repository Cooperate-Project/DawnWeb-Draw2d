package de.cooperateproject.cdo.dawn.rest.draw2d;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.cooperateproject.cdo.dawn.rest.draw2d.api.Draw2dService;
import de.cooperateproject.cdo.dawn.rest.draw2d.util.ServiceFactory;
import de.cooperateproject.cdo.dawn.rest.util.ServiceRegistry;

/**
 * This activator is called on startup of the draw2d rest project.
 * 
 * @author Sebastian Hahner (sebinside)
 *
 */
public class Activator implements BundleActivator {

	ServiceRegistry serviceRegistry = new ServiceRegistry();

	public void start(BundleContext bundleContext) throws Exception {
		// Register the draw2d rest service
		serviceRegistry.addService(bundleContext.registerService(Draw2dService.class,
				ServiceFactory.getInstance().getDraw2dService(), null));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistry.unregisterAll();
	}

}
