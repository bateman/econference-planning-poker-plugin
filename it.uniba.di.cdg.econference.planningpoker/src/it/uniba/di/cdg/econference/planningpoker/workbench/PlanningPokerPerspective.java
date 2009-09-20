package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.multichat.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.multichat.ui.views.MultiChatTalkView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PlanningPokerPerspective implements IPerspectiveFactory {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.PPPerspective";
	
	private static final String BOTTOM_LEFT_FOLDER = PlanningPokerPlugin.ID + ".bottomLeftFolder";



	@Override
	public void createInitialLayout(IPageLayout layout) {
        String editorAreaId = layout.getEditorArea();
        layout.setEditorAreaVisible( false );
        layout.setFixed( true );

        
        layout.addPlaceholder( ChatRoomView.ID + ":*", IPageLayout.LEFT, 0.2f, editorAreaId );
        layout.addView( ChatRoomView.ID, IPageLayout.LEFT, 0.7f, editorAreaId );
        layout.addShowViewShortcut( ChatRoomView.ID );

        //adding the whiteboard view
        layout.addPlaceholder(WhiteBoardView.ID, IPageLayout.RIGHT,0.7f,ChatRoomView.ID); 

        layout.addPlaceholder( MultiChatTalkView.ID, IPageLayout.RIGHT, 0.05f, editorAreaId );

        //adding the backlog view (this view has to be located under the ChatRoomView and the WhiteBoardView)
        layout.addPlaceholder(BacklogView.ID, IPageLayout.BOTTOM, 0.25f, ChatRoomView.ID);  

        layout.addPerspectiveShortcut( ID );

        IFolderLayout bottomLeft = layout.createFolder(BOTTOM_LEFT_FOLDER, IPageLayout.BOTTOM, 0.6f, BacklogView.ID);
		bottomLeft.addPlaceholder(EstimatesView.ID);
		bottomLeft.addPlaceholder(DeckView.ID);
		
//		String editorArea = layout.getEditorArea();
//		//there is no editor
//		layout.setEditorAreaVisible(false);
//
//
//		//adding the ChatRoomView (participants view)
//		layout.addView(ChatRoomView.ID, IPageLayout.LEFT, 0.95f, editorArea);  
//
//		//adding the whiteboard view
//		layout.addView(WhiteBoardView.ID, IPageLayout.RIGHT,0.4f,ChatRoomView.ID);  
//
//		//adding the backlog view (this view has to be located under the ChatRoomView and the WhiteBoardView)
//		layout.addView(BacklogView.ID, IPageLayout.BOTTOM, 0.25f, ChatRoomView.ID);  
//
//		//adding the folder for card deck and the estimates view
//		
//		 IFolderLayout bottomLeft = layout.createFolder(BOTTOM_LEFT_FOLDER, IPageLayout.BOTTOM, 0.65f, ChatRoomView.ID);
//		bottomLeft.addPlaceholder(EstimatesView.ID);
//		bottomLeft.addPlaceholder(DeckView.ID);
//
//		//adding the MultiChat View
//		layout.addView( MultiChatTalkView.ID, IPageLayout.RIGHT, 0.45f, WhiteBoardView.ID );

	}

}
