package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;
import it.uniba.di.cdg.xcore.ui.views.BuddyListView;
import it.uniba.di.cdg.xcore.ui.views.TalkView;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PlanningPokerPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		
		layout.addView(BuddyListView.ID, IPageLayout.LEFT, 0.1f, editorArea);
		layout.addView(StoriesListView.ID, IPageLayout.LEFT, 0.3f, editorArea);
		layout.addView(WhiteBoardView.ID, IPageLayout.LEFT, 0.2f, editorArea);
		layout.addView(TalkView.ID, IPageLayout.LEFT, 0.3f, editorArea);
		
		layout.addView(DeckView.ID, IPageLayout.BOTTOM, 0.3f, editorArea);
		layout.addView(EstimatesView.ID, IPageLayout.RIGHT, 0.3f, editorArea);
		
	}

}
