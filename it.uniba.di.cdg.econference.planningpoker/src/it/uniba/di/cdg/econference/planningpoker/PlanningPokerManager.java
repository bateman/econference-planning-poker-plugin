package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;
import it.uniba.di.cdg.econference.planningpoker.workbench.EstimatesView;
import it.uniba.di.cdg.econference.planningpoker.workbench.PlanningPokerPerspective;
import it.uniba.di.cdg.xcore.econference.IEConferenceService;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.Privileged;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.network.BackendException;
import it.uniba.di.cdg.xcore.network.IBackend;

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
		
		IViewPart viewPart;      
        
        viewPart = getWorkbenchWindow().getActivePage().showView( DeckView.ID );
        deckView = (DeckView) viewPart;
        deckView.setManager(this);
        deckView.setReadOnly(false);
        
        viewPart = getWorkbenchWindow().getActivePage().showView( EstimatesView.ID );
        estimatesView = (EstimatesView) viewPart;   
        estimatesView.setManager(this);
        estimatesView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
        viewPart = getWorkbenchWindow().getActivePage().showView( BacklogView.ID );
        storiesListView = (BacklogView) viewPart;
        storiesListView.setManager(this);
        storiesListView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
	}
	
    @Override
    protected IEConferenceService setupChatService() throws BackendException {
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
	public void notifyCardSelected(String cardValue) {
		getService().notifyCardSelection(cardValue);
	}

	@Privileged( atleast = Role.MODERATOR )
    public void notifyItemListToRemote() {
        getService().notifyItemListToRemote();
    }

}
