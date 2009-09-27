package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.DefaultModelFactory;
import it.uniba.di.cdg.econference.planningpoker.model.IModelAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.JoinPPDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.PlanningPokerPerspective;
import it.uniba.di.cdg.jabber.InvitationEvent;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.econference.internal.EConferenceHelper;
import it.uniba.di.cdg.xcore.multichat.IMultiChatManager.IMultiChatListener;
import it.uniba.di.cdg.xcore.multichat.service.Invitee;
import it.uniba.di.cdg.xcore.network.IBackend;
import it.uniba.di.cdg.xcore.network.INetworkBackendHelper;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.IUIHelper;
import it.uniba.di.cdg.xcore.ui.UiPlugin;
import it.uniba.di.cdg.xcore.ui.util.NotEmptyStringValidator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PlanningPokerHelper extends EConferenceHelper implements IPlanningPokerHelper {
	

	private IUIHelper uihelper;
	private INetworkBackendHelper backendHelper;
	
	 /**
     * The factory used to build the Backlog, the Card Deck the Story dialog and the
     * UIProviders
     * 
     */
	private IModelAbstractFactory factory;

	
    public PlanningPokerHelper(IUIHelper uihelper,
			INetworkBackendHelper backendHelper) {
		super(uihelper, backendHelper);		
		this.uihelper = uihelper;
		this.backendHelper = backendHelper;
		factory = new DefaultModelFactory();
	}


	@Override
	public IPlanningPokerManager open(EConferenceContext context) {
		IPlanningPokerManager manager = null;
			
        try {
            final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

            manager = new PlanningPokerManager();
            manager.setBackendHelper( NetworkPlugin.getDefault().getHelper() );
            manager.setUihelper( UiPlugin.getUIHelper() );
            manager.setWorkbenchWindow( window );

            manager.addListener( new IMultiChatListener() {
                private Point previousSize;

                public void open() {
                    System.out.println( "Resizing window!" );
                    Shell shell = window.getShell();
                    Point size = shell.getSize();
                    if (size.x < 800 || size.y < 600)
                        shell.setSize( 800, 600 );
                    previousSize = size;
                }

                public void closed() {
                    Shell shell = window.getShell();
                    shell.setSize( previousSize );
                }
            } );

            manager.open( context );
        } catch (Exception e) {
            e.printStackTrace();
            uihelper.showErrorMessage( "Could not start Planning Poker: " + e.getMessage() );

            // Close this perspective since it is unuseful ...
            uihelper.closePerspective(PlanningPokerPerspective.ID);
        }
        return manager;
	}

	@Override
	public void openFromFile() {
		final JoinPPDialog dlg = new JoinPPDialog( null );
		if (dlg.open() == Dialog.OK) {
			// 1. Open a file dialog, asking the conference file name
			IPlanningPokerManager manager = open( dlg.getContext() );

			if (dlg.isSendInvitations()) {
				for (Invitee i : dlg.getContext().getInvitees())
					manager.inviteNewParticipant( i.getId() );
			}
		}
	}

    /* (non-Javadoc)
     * @see it.uniba.di.cdg.xcore.econference.IEConferenceHelper#askUserAcceptInvitation(it.uniba.di.cdg.xcore.multichat.InvitationEvent)
     */
	public PlanningPokerContext askUserAcceptInvitation( InvitationEvent invitation ) {
		// Skip invitations which do not interest us ...
		if (!ECONFERENCE_REASON.equals( invitation.getReason() ))
			return null;

		IBackend backend = backendHelper.getRegistry().getBackend( invitation.getBackendId() );

		String message = String
		.format(
				"User %s has invited you to join to a Planning Poker session." +
				"If you want to accept, choose your own role in the meeting and " +
				"your display name then press Yes, otherwise " +
				"press Cancel.", invitation.getInviter() );				
		String[] userInputs = new String[]{"Role: ", "Nickname: "};
		String[] defaultValues = new String[]{"", backend.getUserAccount().getId()};
		IInputValidator[] validators = new IInputValidator[]{null, new NotEmptyStringValidator()};
		String[]  userInput = uihelper.askUserInput( "Invitation received", message,userInputs, defaultValues, validators  );
		if (userInput != null) {
			String chosenRole = userInput[0];
			String chosenNickNamer = userInput[1];			
			PlanningPokerContext context = new PlanningPokerContext( chosenNickNamer,chosenRole,
					invitation );
			return context;
		} else
			invitation.decline( "No reason" );

		return null;
	}
	
	
	/**
	 * Set the model factory
	 * @param factory see {@link IModelAbstractFactory}
	 */
	public void setModelFactory(IModelAbstractFactory factory){
		this.factory = factory;		
	}
	
	public IUserStoryDialog getUserStoryDialog(Shell shell){
		return factory.createUserStoryDialog(shell);
	}
	
	public IBacklogContextLoader getBacklogContextLoader(){
		return factory.createBacklogContextLoader();
	}
	
	public IBacklogViewUIProvider getBacklogViewUIProvider(){
		return factory.createBacklogViewUIProvider();
	}
	
	public IEstimatesViewUIProvider getEstimateViewUIProvider(){
		return factory.createEstimateViewUIProvider();
	}
	
	public IDeckViewUIHelper getCardDeckViewUIHelper(Composite parent, CardDeck deck){
		return factory.createCardDeckViewUIHelper(parent, deck);
	}
	
	

}
