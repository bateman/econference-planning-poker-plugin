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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * <p>This class is associated to a view and model an action that 
 * is enable only if one element is selected on the View.</p>
 * 
 * <p>The action for delete, edit and estimate User Stories in the
 * BacklogView are an examples of this kind of action</p>
 * 
 * @author Alex
 *
 */
public class SingleSelectionAction extends Action {
	
	protected IViewPart view;
	
	private ISelectionListener listener;
	
	public SingleSelectionAction(IViewPart view) {
		super();
		this.view = view;		
		addSelectionListener();
		setEnabled(false);
	}
	
	/**
	 * 
	 * <p>This method works like setEnable method.</p>
	 * <p>We have created a different method for distinguish between the</p>
	 * <ul>
	 * <li>action enabling in relation to the selection</li>
	 * <li>action enabling permanently (for example because only the moderator can use the action)</li> 
	 * </ul>
	 * This method is for the latter point
	 * 
	 * @param accessible if this action is accessible or not
	 */
	public void setAccessible(boolean accessible) {
		// Only if the action is accessible we can have a listener to know if
		// the object is selected and so enabling the action
		if (accessible) {
			addSelectionListener();
		} else {
			removeSelectionListener();
			setEnabled(false);
		}
	}

	/**
	 * Add a listener that enable the action only if the item/object is selected
	 */
	private void addSelectionListener() {
		if(listener==null)
		listener = new ISelectionListener(){

			@Override
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {
				setEnabled((IStructuredSelection)selection);
				
			}			
		};
		view.getViewSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);		
	}

	/**
	 * Remove the selection listener. This is done only if the view is not
	 * accessible which means that the user is not allowed to use this action
	 */
	private void removeSelectionListener(){
		if(listener!=null)
		view.getViewSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(listener);
		listener = null;
	}
	
	
	/**
	 * 
	 * <p>This method works like setAccessible method.</p>
	 * <p>We have created a different method for distinguish between the</p>
	 * <ul>
	 * <li>action enabling in relation to the selection</li>
	 * <li>action enabling permanently (for example because only the moderator can use the action)</li> 
	 * </ul>
	 * This method is for the first point
	 * 
	 * @param accessible if this action is accessible or not
	 */
	private void setEnabled(IStructuredSelection selection) {		
		//This action is enable only if one element is selected
		boolean enabled = selection.size() == 1;
		setEnabled(enabled);
	}	

}
