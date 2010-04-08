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
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * This class represent the action to start estimating a User Story in the
 * Backlog view. This action is added in the context menu displayed after a
 * right-click on the table in the Backlog view or after a double-click on a
 * User Story.
 * 
 * @author Alessandro Brucoli
 * 
 */
public class EstimateStoryAction extends SingleSelectionAction {

	
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.EstimateStoryAction";
	
	public EstimateStoryAction(IViewPart view) {
		super(view);		
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/estimate-story.gif" ));
		setText("Estimate User Story");
		setToolTipText("Set selected story as a Story to estimate");
	}

	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStory selectedStory = backlogView.getSelectedStory();
		if(selectedStory!=null){			
			Backlog backlog = backlogView.getModel().getBacklog();
			for(int i=0; i < backlog.getUserStories().length; i++){
				if(backlog.getUserStories()[i].equals(selectedStory)){
					//backlog.setCurrentItemIndex(i);
					backlogView.getManager().notifyCurrentAgendaItemChanged(String.format("%d",i));
					break;
				}
			}	
						
		}
	}


}
