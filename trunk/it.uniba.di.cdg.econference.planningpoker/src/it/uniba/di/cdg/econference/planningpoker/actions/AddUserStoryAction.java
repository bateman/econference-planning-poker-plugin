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
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * This class represent the action associated to the adding of a new User Story in the Backlog view.
 * This action is added in the context menu displayed after a right-click on the table in the Backlog view. 
 * @author Alessandro Brucoli
 *
 */
public class AddUserStoryAction extends Action {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.addUserStoryAction";


	private IViewPart view;
	
	public AddUserStoryAction(IViewPart view) {
		super();
		this.view = view;
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/new-user-story.gif" ));
		setText("New User Story");
		setToolTipText("Add new User Story to the Backlog");		
	}
		

	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		IUserStoryDialog dialog = PlanningPokerPlugin.getDefault().getHelper().getUserStoryDialog(window.getShell());		
		dialog.setCardDeck(backlogView.getModel().getCardDeck());
		Backlog backlog = backlogView.getModel().getBacklog();
		dialog.setBacklog(backlog);
		dialog.show();
		IUserStory newStory = dialog.getStory();
		if (newStory != null) {
			backlogView.getModel().getBacklog().addUserStory(newStory);
			//notify changes of the backlog's content
			backlogView.getManager().notifyItemListToRemote();		
		}
	}

}
