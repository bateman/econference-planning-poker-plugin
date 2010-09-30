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

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.xcore.m2m.ui.views.ChatRoomView;
import it.uniba.di.cdg.xcore.m2m.ui.views.MultiChatTalkView;

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
        //layout.setFixed( true );

        
        layout.addPlaceholder( ChatRoomView.ID + ":*", IPageLayout.LEFT, 0.2f, editorAreaId );
        layout.addView( ChatRoomView.ID, IPageLayout.LEFT, 0.7f, editorAreaId );
        layout.addShowViewShortcut( ChatRoomView.ID );

        //adding the whiteboard view
        layout.addPlaceholder(PPWhiteBoardView.ID, IPageLayout.RIGHT,0.7f,ChatRoomView.ID); 

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
