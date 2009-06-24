package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.multichat.MultiChatPlugin;
import it.uniba.di.cdg.xcore.multichat.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.multichat.ui.views.MultiChatTalkView;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PlanningPokerPerspective implements IPerspectiveFactory {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.PPPerspective";
	
	private static final String BOTTOM_FOLDER = MultiChatPlugin.ID + ".bottomFolder";
	private static final String FI_LEFT = MultiChatPlugin.ID + ".leftFolder";
	private static final String FI_RIGHT = MultiChatPlugin.ID + ".rightFolder";
	

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();		
		layout.setEditorAreaVisible(false);
		
//        layout.createPlaceholderFolder( FI_LEFT, IPageLayout.LEFT, 0.30f, editorArea );
//        layout.createPlaceholderFolder( FI_RIGHT, IPageLayout.RIGHT, 0.60f, editorArea );
        
        //layout.addPlaceholder( ChatRoomView.ID + ":*", IPageLayout.LEFT, 0.2f, editorArea );
       

        layout.addView( MultiChatTalkView.ID, IPageLayout.RIGHT, 0.2f, editorArea );
      
        layout.addView( ChatRoomView.ID, IPageLayout.LEFT, 0.1f, editorArea );
        layout.addShowViewShortcut( ChatRoomView.ID );
        
        layout.addView(WhiteBoardView.ID, IPageLayout.RIGHT, 0.4f, ChatRoomView.ID);
		
        layout.addView(StoriesListView.ID, IPageLayout.BOTTOM, 0.4f, ChatRoomView.ID);
		
		IFolderLayout leftBottomFolder = layout.createFolder(BOTTOM_FOLDER, IPageLayout.BOTTOM, 0.2f, StoriesListView.ID);
		leftBottomFolder.addView(DeckView.ID);
		leftBottomFolder.addView(EstimatesView.ID);	
		
	}

}
