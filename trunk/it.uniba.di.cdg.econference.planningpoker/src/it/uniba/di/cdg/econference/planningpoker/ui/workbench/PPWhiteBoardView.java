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
package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import it.uniba.di.cdg.aspects.SwtAsyncExec;
import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListenerAdapter;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.ui.views.IWhiteBoard;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;

public class PPWhiteBoardView extends WhiteBoardView implements IWhiteBoard {
		
	public static final String ID ="it.uniba.di.cdg.econference.planningpoker.views.PPWhiteBoardView";
	
	private IPlanningPokerManager manager;

	private boolean readOnly;

	private IBacklogListener backlogListener = new IBacklogListenerAdapter() {

		public void currentSelectionChanged( int currItemIndex ) {
			if(!readOnly){
				if(currItemIndex!=IItemList.NO_ITEM_SELECTED){
					setAccessible(true);
				}else{
					setAccessible(false);
				}
			}
		}
	};

	public void setManager(IEConferenceManager manager) {
		this.manager = (IPlanningPokerManager) manager;
		super.setManager(manager);
		setAccessible(false);
		getModel().getBacklog().addListener(backlogListener);
	};
	

	@SwtAsyncExec
	public void setAccessible(boolean accessible){
		whiteBoardText.setEditable(accessible);
		refreshRemoteAction.setEnabled(accessible);
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		if(!readOnly){
			if(manager ==null || getModel()==null || getModel().getBacklog()==null ||
					getModel().getBacklog().getCurrentItemIndex()==IItemList.NO_ITEM_SELECTED){
				setAccessible(false);
			}else{
				setAccessible(true);
			}
		}else{
			super.setReadOnly(true);
		}
	}	
	
	private IPlanningPokerModel getModel(){
		return manager.getService().getModel();
	}
		

}
