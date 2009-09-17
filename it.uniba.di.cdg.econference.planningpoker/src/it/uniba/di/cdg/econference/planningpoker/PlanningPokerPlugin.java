package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.DefaultModelFactory;
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
    
    
    /**
     * The factory used to build the Backlog, the Card Deck and dialogs
     * 
     */
    private IModelAbstractFactory factory;
    
    

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
		EConferencePlugin defaultPlugin = EConferencePlugin.getDefault();
        defaultPlugin.setHelper(new PlanningPokerHelper( UiPlugin.getUIHelper(), NetworkPlugin.getDefault().getHelper()));
		plugin = this;		       
		factory = new DefaultModelFactory();
	}
	
	
	public void setModelFactory(IModelAbstractFactory factory){
		this.factory = factory;
		//TODO: inserire un listener per notificare a tutti che la factory è cambiata
	}
	
	public IModelAbstractFactory getModelFactory(){
		return factory;
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
