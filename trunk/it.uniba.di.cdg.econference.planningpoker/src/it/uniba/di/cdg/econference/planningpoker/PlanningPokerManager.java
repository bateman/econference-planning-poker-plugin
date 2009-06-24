package it.uniba.di.cdg.econference.planningpoker;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.WorkbenchException;

import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;
import it.uniba.di.cdg.econference.planningpoker.workbench.EstimatesView;
import it.uniba.di.cdg.econference.planningpoker.workbench.PlanningPokerPerspective;
import it.uniba.di.cdg.econference.planningpoker.workbench.StoriesListView;
import it.uniba.di.cdg.xcore.econference.IEConferenceService;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceManager;
import it.uniba.di.cdg.xcore.econference.ui.views.AgendaView;
import it.uniba.di.cdg.xcore.econference.ui.views.HandRaisingView;
import it.uniba.di.cdg.xcore.econference.ui.views.IHandRaisingView;
import it.uniba.di.cdg.xcore.econference.ui.views.IWhiteBoard;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.multichat.internal.MultiChatManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.multichat.ui.MultiChatPerspective;
import it.uniba.di.cdg.xcore.multichat.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.network.BackendException;
import it.uniba.di.cdg.xcore.network.IBackend;

public class PlanningPokerManager extends EConferenceManager implements IPlanningPokerManager {
	
	// TODO This should go in i18n file
    private static final String FREE_TALK_NOW_MESSAGE = "Free talk now ...";
	
    private IWhiteBoard whiteBoardView;
    
    private DeckView deckView;
    
    private EstimatesView estimatesView;
    
    private StoriesListView storiesListView;
	
	@Override
	protected void setupUI() throws WorkbenchException {	
		super.setupUI();
		getUihelper().switchPerspective( PlanningPokerPerspective.ID );
		
		IViewPart viewPart;
        
        viewPart = getWorkbenchWindow().getActivePage().showView( WhiteBoardView.ID );
        whiteBoardView = (IWhiteBoard) viewPart;
        whiteBoardView.setManager( this );
        // By default the whiteboard cannot be modified: when the user is given the SCRIBE 
        // special role than it will be set read-write
        whiteBoardView.setReadOnly( true );
        
        viewPart = getWorkbenchWindow().getActivePage().showView( DeckView.ID );
        deckView = (DeckView) viewPart;
        deckView.setManager(this);
        deckView.setReadOnly(false);
        
        viewPart = getWorkbenchWindow().getActivePage().showView( EstimatesView.ID );
        estimatesView = (EstimatesView) viewPart;   
        estimatesView.setManager(this);
        estimatesView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
        viewPart = getWorkbenchWindow().getActivePage().showView( StoriesListView.ID );
        storiesListView = (StoriesListView) viewPart;
        storiesListView.setManager(this);
        storiesListView.setReadOnly(!Role.MODERATOR.equals(getRole()));
        
        // By default user can chat freely before the conference is started
        getTalkView().setReadOnly( false );
        // Display that there is free talk ongoing
        getTalkView().setTitleText( FREE_TALK_NOW_MESSAGE );
        
        // Ensure that the focus is switched to this new chat
        ((IViewPart) getTalkView()).setFocus();
	}
	
    @Override
    protected IEConferenceService setupChatService() throws BackendException {
        IBackend backend = getBackendHelper().getRegistry().getBackend( getContext().getBackendId() );
        
        return (IPlanningPokerService) backend.createService( 
        		IPlanningPokerService.PLANNINGPOKER_SERVICE, 
                getContext() );
    }
	
	
	@Override
	public IPlanningPokerService getService() {	
		return (IPlanningPokerService)super.getService();
	}
	
	
	@Override
	public void addAgendaItem(String newItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyCardSelected(IParticipant moderator, String cardValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyItemListToRemote() {
		// TODO Auto-generated method stub
		
	}

}
