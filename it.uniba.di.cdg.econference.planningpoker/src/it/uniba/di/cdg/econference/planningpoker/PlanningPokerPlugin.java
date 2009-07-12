package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.xcore.econference.EConferencePlugin;
import it.uniba.di.cdg.xcore.econference.IEConferenceHelper;
import it.uniba.di.cdg.xcore.econference.model.storedevents.IStoredEventsModel;
import it.uniba.di.cdg.xcore.econference.model.storedevents.StoredEventsModel;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PlanningPokerPlugin extends AbstractUIPlugin {
	// The plug-in ID
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker";
	
	 /**
     * The unique id for this plug-in.
     */

    // The shared instance.
    private static PlanningPokerPlugin plugin;
    
    

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
		plugin = this;
		EConferencePlugin defaultPlugin = EConferencePlugin.getDefault(); 
		defaultPlugin.setHelper(new PlanningPokerHelper( UiPlugin.getUIHelper(), NetworkPlugin.getDefault().getHelper()));        

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
     * Returns the helper for this plug-in.
     * 
     * @return
     */
 /*   public IEConferenceHelper getHelper() {
        return helper;
    }*/
    
    
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
}
