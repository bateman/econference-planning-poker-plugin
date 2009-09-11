package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class SelectCardAction extends SingleSelectionAction {

	public SelectCardAction(IViewPart view) {
		super(view);
		setText("Select this card");
		setToolTipText("Select the card representing your estimate");
	}
	
	@Override
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		DeckView deckView = (DeckView) page.findView(DeckView.ID);
		IPokerCard selectedCard = deckView.getSelectedCard();
		if(selectedCard!=null){
			String storyId = deckView.getModel().getEstimateSession().getUserStoryId();
			deckView.getManager().notifyCardSelected(storyId, selectedCard);	
		}
	}
	
	

}
