package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.jabber.JabberPlanningPokerService;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
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
import it.uniba.di.cdg.jabber.JabberBackend;
import it.uniba.di.cdg.xcore.econference.IEConferenceService.AgendaOperation;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceManager;
import it.uniba.di.cdg.xcore.econference.ui.views.AgendaView;
import it.uniba.di.cdg.xcore.econference.ui.views.IAgendaView;
import it.uniba.di.cdg.xcore.econference.ui.views.IWhiteBoard;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.ParticipantSpecialPrivileges;
import it.uniba.di.cdg.xcore.multichat.model.Privileged;
import it.uniba.di.cdg.xcore.multichat.model.SpecialPrivilegesAction;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.multichat.service.IMultiChatService;
import it.uniba.di.cdg.xcore.multichat.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.multichat.ui.views.IChatRoomView;
import it.uniba.di.cdg.xcore.multichat.ui.views.IMultiChatTalkView;
import it.uniba.di.cdg.xcore.multichat.ui.views.MultiChatTalkView;
import it.uniba.di.cdg.xcore.network.BackendException;
import it.uniba.di.cdg.xcore.network.IBackend;
import it.uniba.di.cdg.xcore.network.services.INetworkService;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.WorkbenchException;

public class PlanningPokerManager extends EConferenceManager implements IPlanningPokerManager {
	
        
	@Override
	protected void setupUI() throws WorkbenchException {
		getUihelper().switchPerspective( PlanningPokerPerspective.ID );

		IViewPart viewPart;  
		
		viewPart = getWorkbenchWindow().getActivePage().showView( MultiChatTalkView.ID );
		talkView = (IMultiChatTalkView) viewPart;
		talkView.setTitleText( getContext().getRoom() );
		talkView.setModel( getService().getTalkModel() );

		viewPart = (IViewPart) getWorkbenchWindow().getActivePage().findView( ChatRoomView.ID );
		roomView = (IChatRoomView) viewPart;
		roomView.setManager( this );
		

		viewPart = getWorkbenchWindow().getActivePage().showView( PPWhiteBoardView.ID );
		IWhiteBoard whiteBoardView = (IWhiteBoard) viewPart;
		whiteBoardView.setManager( this );
		// By default the whiteboard cannot be modified: when the user is given the SCRIBE 
		// special role than it will be set read-write
		whiteBoardView.setReadOnly( true );

		viewPart = getWorkbenchWindow().getActivePage().showView( DeckView.ID );
		IDeckView deckView = (DeckView) viewPart;
		deckView.setManager(this);
        deckView.setReadOnly(!Role.MODERATOR.equals(getRole()));        
        
        viewPart = getWorkbenchWindow().getActivePage().showView( EstimatesView.ID );
        IEstimatesView estimatesView = (EstimatesView) viewPart;   
        estimatesView.setManager(this);
        estimatesView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
        viewPart = getWorkbenchWindow().getActivePage().showView( BacklogView.ID );
        IBacklogView storiesListView = (BacklogView) viewPart;
        storiesListView.setManager(this);
        storiesListView.setReadOnly(!Role.MODERATOR.equals(getRole()));        
        
        //Moderator must be able to write the Whiteboard while participant has the voter privileges as default
        if(Role.MODERATOR.equals(getRole())){
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
        IBackend backend = getBackendHelper().getRegistry().getBackend( getContext().getBackendId() );
        
        IPlanningPokerService service =(IPlanningPokerService) backend.createService( 
        		IPlanningPokerService.PLANNINGPOKER_SERVICE, 
                getContext() );
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
