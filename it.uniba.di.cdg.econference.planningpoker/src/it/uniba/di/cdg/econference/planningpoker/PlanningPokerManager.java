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

import it.uniba.di.cdg.econference.planningpoker.jabber.JabberPlanningPokerService;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.DeckView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.EstimatesView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.IBacklogView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.IDeckView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.IEstimatesView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.PPWhiteBoardView;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.PlanningPokerPerspective;
import it.uniba.di.cdg.xcore.econference.IEConferenceService.AgendaOperation;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceManager;
import it.uniba.di.cdg.xcore.econference.ui.views.IWhiteBoard;
import it.uniba.di.cdg.xcore.m2m.model.IParticipant;
import it.uniba.di.cdg.xcore.m2m.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.m2m.model.ParticipantSpecialPrivileges;
import it.uniba.di.cdg.xcore.m2m.model.Privileged;
import it.uniba.di.cdg.xcore.m2m.model.SpecialPrivilegesAction;
import it.uniba.di.cdg.xcore.m2m.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.m2m.ui.views.IChatRoomView;
import it.uniba.di.cdg.xcore.m2m.ui.views.IMultiChatTalkView;
import it.uniba.di.cdg.xcore.m2m.ui.views.MultiChatTalkView;
import it.uniba.di.cdg.xcore.network.BackendException;
import it.uniba.di.cdg.xcore.network.IBackend;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;

public class PlanningPokerManager extends EConferenceManager implements IPlanningPokerManager {
	
        
	@Override
	protected void setupUI() throws WorkbenchException {
		getUihelper().switchPerspective( PlanningPokerPerspective.ID );

		IViewPart viewPart;  
		
		IWorkbenchWindow workbenchWindow = getWorkbenchWindow();
		viewPart = workbenchWindow.getActivePage().showView( MultiChatTalkView.ID );
		talkView = (IMultiChatTalkView) viewPart;
		talkView.setTitleText( getContext().getRoom() );
		talkView.setModel( getService().getTalkModel() );

		viewPart = (IViewPart) workbenchWindow.getActivePage().findView( ChatRoomView.ID );
		roomView = (IChatRoomView) viewPart;
		roomView.setManager( this );
		

		viewPart = workbenchWindow.getActivePage().showView( PPWhiteBoardView.ID );
		IWhiteBoard whiteBoardView = (IWhiteBoard) viewPart;
		whiteBoardView.setManager( this );
		// By default the whiteboard cannot be modified: when the user is given the SCRIBE 
		// special role than it will be set read-write
		whiteBoardView.setReadOnly( true );

		viewPart = workbenchWindow.getActivePage().showView( DeckView.ID );
		IDeckView deckView = (DeckView) viewPart;
		deckView.setManager(this);
        Role role = getRole();
		deckView.setReadOnly(!Role.MODERATOR.equals(role));        
        
        viewPart = workbenchWindow.getActivePage().showView( EstimatesView.ID );
        IEstimatesView estimatesView = (EstimatesView) viewPart;   
        estimatesView.setManager(this);
        estimatesView.setReadOnly(!Role.MODERATOR.equals(role));
        
        viewPart = workbenchWindow.getActivePage().showView( BacklogView.ID );
        IBacklogView storiesListView = (BacklogView) viewPart;
        storiesListView.setManager(this);
        storiesListView.setReadOnly(!Role.MODERATOR.equals(role));        
        
        //Moderator must be able to write the Whiteboard while participant has the voter privileges as default
        if(Role.MODERATOR.equals(role)){
        	//getService().getModel().getLocalUser().addSpecialPriviliges(ParticipantSpecialPrivileges.SCRIBE);  
        	getService().notifyChangedSpecialPrivilege(getService().getModel().getLocalUser(), ParticipantSpecialPrivileges.SCRIBE, 
        			SpecialPrivilegesAction.GRANT);
        }else{
        	//getService().getModel().getLocalUser().addSpecialPriviliges(ParticipantSpecialPrivileges.VOTER);
        	getService().notifyChangedSpecialPrivilege(getService().getModel().getLocalUser(), ParticipantSpecialPrivileges.VOTER, 
        			SpecialPrivilegesAction.GRANT);
        }
	}
	
	 /* (non-Javadoc)
     * @see it.uniba.di.cdg.xcore.multichat.MultiChat#setupListeners()
     */
    @Override
    protected void setupListeners() {     
        conferenceStarteMessage = "The meeting has been STARTED";
        conferenceStoppedMessage = "The meeting has been STOPPED";
        
        super.setupListeners();                             
        
        final IPlanningPokerModel model = (IPlanningPokerModel) getService().getModel();     
        
        IParticipant p = model.getLocalUser();
        if(p!=null)
        	if(getContext().getPersonalStatus()!=null && getContext().getPersonalStatus()!="")
        		getService().notifyChangedMUCPersonalPrivilege(p, getContext().getPersonalStatus());      
    }
	
    @Override
    protected IPlanningPokerService setupChatService() throws BackendException {
    	PlanningPokerContext context = getContext();
    	context.setBacklog(new Backlog());
		//IBackend backend = getBackendHelper().getRegistry().getBackend( context.getBackendId() );
    	IBackend backend = getBackendHelper().getRegistry().getDefaultBackend();
    	
//        IPlanningPokerService service =(IPlanningPokerService) backend.createService( 
//        		IPlanningPokerService.PLANNINGPOKER_SERVICE, 
//                getContext() );
		
        IPlanningPokerService service =  new JabberPlanningPokerService(context, backend);
        return service;
    }
    
    @Override
    protected PlanningPokerContext getContext() {
        return (PlanningPokerContext) super.getContext();
    }
	
	
	@Override
	public IPlanningPokerService getService() {	
		return (IPlanningPokerService)super.getService();
	}
	

	@Override
	public void notifyCardSelected(String storyId, IPokerCard card) {
		getService().notifyCardSelection(storyId, card);
	}
	
	@Override
	public void notifyCardDeckToRemote(CardDeck deck){
		getService().notifyCardDeckToRemote(deck);
	}

	@Privileged( atleast = Role.MODERATOR )
    public void notifyItemListToRemote() {
        getService().notifyItemListToRemote();
    }

	@Override
	public void notifyEstimateSessionStatusChange(IEstimatesList estimates, EstimateStatus status) {
		getService().notifyEstimateSessionStatusChange(estimates, status);
		
	}

	@Override
	public void notifyRemoveBacklogItem(String itemIndex) {
	    getService().notifyAgendaOperation( AgendaOperation.REMOVE, itemIndex );
		
	}

	@Override
	public void notifyEstimateAssigned(String storyId, String estimateValue) {
		getService().notifyEstimateAssigned(storyId, estimateValue);
		
	}
	
	
//	@Privileged( atleast = Role.MODERATOR )
//	public void notifyVoterListToRemote(){
//		getService().notifyVoterListToRemote();
//	}

}
