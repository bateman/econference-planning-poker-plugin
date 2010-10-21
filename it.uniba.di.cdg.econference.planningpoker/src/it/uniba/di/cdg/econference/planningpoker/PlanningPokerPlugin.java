/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IModelAbstractFactory;
import it.uniba.di.cdg.xcore.econference.EConferencePlugin;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
		setupLogging();
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
	
	private void setupLogging() throws IOException {
		FileHandler fh = new FileHandler("./pplog.txt");
		fh.setFormatter(new SimpleFormatter());
		Logger.getLogger("planningpoker").addHandler(fh);
		Logger.getAnonymousLogger().setLevel(Level.ALL);
	}
}
