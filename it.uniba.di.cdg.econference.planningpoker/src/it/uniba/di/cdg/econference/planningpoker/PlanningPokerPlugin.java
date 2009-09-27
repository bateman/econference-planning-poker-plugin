package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IModelAbstractFactory;
import it.uniba.di.cdg.xcore.econference.EConferencePlugin;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PlanningPokerPlugin extends AbstractUIPlugin {
	
	
	 /**
     * The unique id for this plug-in.
     */
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker";
	
	

    // The shared instance.
    private static PlanningPokerPlugin plugin;
    
    private PlanningPokerHelper helper;

    /**
     * The constructor.
     */
    public PlanningPokerPlugin() {
        plugin = this;
    }

    
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);		
		helper = new PlanningPokerHelper(UiPlugin.getUIHelper(), NetworkPlugin.getDefault().getHelper());
		EConferencePlugin defaultPlugin = EConferencePlugin.getDefault();
        defaultPlugin.setHelper(helper);
		plugin = this;		       		
	}
	
	

    

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop( BundleContext context ) throws Exception {
        super.stop( context );
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * @return the instance of Planning Poker plugin
     */
    public static PlanningPokerPlugin getDefault() {
        return plugin;
    }

    
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(ID, path);
	}
	
    /**
     * Returns the helper for this plug-in.
     * 
     * @return
     */
    public IPlanningPokerHelper getHelper() {
        return (IPlanningPokerHelper) helper;
    }


	/**
	 * <p>For each new Model factory here must be the match between the 
	 * name of the factory and the relative class </p>
	 * 
	 * @param stringFactory the symbolic name of the factory
	 * @return the java class of the factory that implements {@link IModelAbstractFactory}
	 * 
	 * <p>For example:
	 * <code>if(stringFactory.equals("bar_factory")
	 * return new BarFactory();<code></p>
	 * 
	 */
	public static IModelAbstractFactory getFactoryFromString(
			String stringFactory) {
		
		return null;
	}
}
