package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates;
import it.uniba.di.cdg.econference.planningpoker.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;
import it.uniba.di.cdg.econference.planningpoker.workbench.EstimatesView;
import it.uniba.di.cdg.econference.planningpoker.workbench.PlanningPokerPerspective;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.IEConferenceService.AgendaOperation;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceManager;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModelListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.ChatRoomModelAdapter;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.ParticipantSpecialPrivileges;
import it.uniba.di.cdg.xcore.multichat.model.Privileged;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.network.BackendException;
import it.uniba.di.cdg.xcore.network.IBackend;
import it.uniba.di.cdg.xcore.network.messages.SystemMessage;
import it.uniba.di.cdg.xcore.network.model.tv.ITalkModel;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.WorkbenchException;

public class PlanningPokerManager extends EConferenceManager implements IPlanningPokerManager {
	
	
    private DeckView deckView;
    
    private EstimatesView estimatesView;
    
    private BacklogView storiesListView;
	
    
	@Override
	protected void setupUI() throws WorkbenchException {	
		super.setupUI();
		getUihelper().switchPerspective( PlanningPokerPerspective.ID );
		
		 boolean conferenceStopped = ConferenceStatus.STOPPED.equals( getService().getModel().getStatus() );
		
		IViewPart viewPart;      
        		
        viewPart = getWorkbenchWindow().getActivePage().showView( DeckView.ID );
        deckView = (DeckView) viewPart;
        deckView.setManager(this);
        deckView.setReadOnly(true); 
        
        viewPart = getWorkbenchWindow().getActivePage().showView( EstimatesView.ID );
        estimatesView = (EstimatesView) viewPart;   
        estimatesView.setManager(this);
        estimatesView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
        viewPart = getWorkbenchWindow().getActivePage().showView( BacklogView.ID );
        storiesListView = (BacklogView) viewPart;
        storiesListView.setManager(this);
        storiesListView.setReadOnly(!Role.MODERATOR.equals(getRole()));        
        
        //Moderator must be able to write the Whiteboard
       if(Role.MODERATOR.equals(getRole()))
        		getService().getModel().getLocalUser().addSpecialPriviliges(ParticipantSpecialPrivileges.SCRIBE);      	
	}
	
	 /* (non-Javadoc)
     * @see it.uniba.di.cdg.xcore.multichat.MultiChat#setupListeners()
     */
    @Override
    protected void setupListeners() {
    	 EConferenceManager.CONFERENCE_STARTED_MESSAGE = "The meeting has been STARTED";
         EConferenceManager.CONFERENCE_STOPPED_MESSAGE = "The meeting has been STOPPED";
         
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
        
        return (IPlanningPokerService) backend.createService( 
        		IPlanningPokerService.PLANNINGPOKER_SERVICE, 
                getContext() );
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
	public void notifyEstimateSessionStatusChange(IEstimates estimates) {
		getService().notifyEstimateSessionStatusChange(estimates);
		
	}

	@Override
	public void notifyRemoveBacklogItem(String itemIndex) {
	    getService().notifyAgendaOperation( AgendaOperation.REMOVE, itemIndex );
		
	}
	
	
//	@Privileged( atleast = Role.MODERATOR )
//	public void notifyVoterListToRemote(){
//		getService().notifyVoterListToRemote();
//	}

}
