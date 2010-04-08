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
package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.LoadBacklogDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.w3c.dom.Document;

public class ImportBacklogAction extends Action {

	private IViewPart view;
	
	public ImportBacklogAction(IViewPart view) {
		super();
		this.view = view;	
		setText("Import Backlog");
		setToolTipText("Import a new Backlog");
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/import16x16.png" ));
	}

	@Override
	public void run() {
		if (NetworkPlugin.getDefault().getHelper().getOnlineBackends().size() == 0) {
            UiPlugin.getUIHelper().showErrorMessage( "Please, connect first!" );
            return;
        }
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		
		if(backlogView.getModel()==null){
			UiPlugin.getUIHelper().showErrorMessage( "Please, start a Planning Poker session first!" );
			return;
		}		
		
		final LoadBacklogDialog dlg = new LoadBacklogDialog( window.getShell() );
		if (dlg.open() == Dialog.OK) {
			
			String fileName = dlg.getFileName();
			if(fileName!=""){
				FileInputStream is;
				Document doc;
				Backlog backlog;
				try {
					is = new FileInputStream( fileName );
					IBacklogContextLoader backlogLoader = PlanningPokerPlugin
					.getDefault().getHelper().getBacklogContextLoader();						
					doc = XMLUtils.loadDocument(is);
					backlog = backlogLoader.load(doc);
					backlogView.getModel().setBacklog(backlog);
					backlogView.getManager().notifyItemListToRemote();
				} catch (FileNotFoundException ex) {
					UiPlugin.getUIHelper().showErrorMessage( "Sorry, the specified file seems invalid: " 
                            + ex.getMessage() );
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					UiPlugin.getUIHelper().showErrorMessage( "Could not import this Backlog file: " + e.getMessage() );
				}				
			}
			
		}
	}
}
