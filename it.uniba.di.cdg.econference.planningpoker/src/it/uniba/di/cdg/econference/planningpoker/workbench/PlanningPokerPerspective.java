package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.multichat.MultiChatPlugin;
import it.uniba.di.cdg.xcore.multichat.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.multichat.ui.views.MultiChatTalkView;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PlanningPokerPerspective implements IPerspectiveFactory {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.PPPerspective";
	
	private static final String BOTTOM_LEFT_FOLDER = PlanningPokerPlugin.ID + ".bottomLeftFolder";
//	private static final String TOP_LEFT_FOLDER = PlanningPokerPlugin.ID + ".topLeftFolder";
//	private static final String FI_LEFT = MultiChatPlugin.ID + ".leftFolder";
//	private static final String FI_RIGHT = MultiChatPlugin.ID + ".rightFolder";
//	

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		//there is no editor
		layout.setEditorAreaVisible(false);


		//adding the ChatRoomView (participants view)
		layout.addView(ChatRoomView.ID, IPageLayout.LEFT, 0.95f, editorArea);  

		//adding the whiteboard view
		layout.addView(WhiteBoardView.ID, IPageLayout.RIGHT,0.4f,ChatRoomView.ID);  

		//adding the backlog view (this view has to be located under the ChatRoomView and the WhiteBoardView)
		layout.addView(BacklogView.ID, IPageLayout.BOTTOM, 0.25f, ChatRoomView.ID);  

		//adding the folder for card deck and the estimates view
		IFolderLayout bottomLeft = layout.createFolder(BOTTOM_LEFT_FOLDER, IPageLayout.BOTTOM, 0.65f, BacklogView.ID);
		bottomLeft.addView(EstimatesView.ID);
		bottomLeft.addView(DeckView.ID);
		

		//adding the MultiChat View
		layout.addView( MultiChatTalkView.ID, IPageLayout.RIGHT, 0.45f, WhiteBoardView.ID );


		// Bottom left: Outline view and Property Sheet view
		//			IFolderLayout topLeft = layout.createFolder(TOP_LEFT_FOLDER, IPageLayout.LEFT, 0.50f,
		//					editorArea);		
		//			topLeft.addView(ChatRoomView.ID);
		//			topLeft.addView(WhiteBoardView.ID);

		//			layout.addView(BacklogView.ID, IPageLayout.BOTTOM, 0.25f, TOP_LEFT_FOLDER);




		//        layout.createPlaceholderFolder( FI_LEFT, IPageLayout.LEFT, 0.30f, editorArea );
		//        layout.createPlaceholderFolder( FI_RIGHT, IPageLayout.RIGHT, 0.60f, editorArea );  
		//layout.addPlaceholder( ChatRoomView.ID + ":*", IPageLayout.LEFT, 0.2f, editorArea );
		//layout.addView( ChatRoomView.ID, IPageLayout.LEFT, 2.0f, editorArea );
		//layout.addView(WhiteBoardView.ID, IPageLayout.RIGHT, 0.4f, ChatRoomView.ID);
		//leftBottomFolder.addView(EstimatesView.ID);	

	}

}
