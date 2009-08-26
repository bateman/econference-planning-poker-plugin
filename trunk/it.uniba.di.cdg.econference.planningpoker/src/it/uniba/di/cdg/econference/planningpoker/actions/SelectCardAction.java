package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class SelectCardAction extends SingleSelectionAction {

	public SelectCardAction(IViewPart view) {
		super(view);
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/estimate-story.gif" ));
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
			deckView.getManager().notifyCardSelected(selectedCard.getStringValue());	
		}
//		ISelection selection = deckView.getViewer().getSelection();
//		if(selection!=null && selection instanceof IStructuredSelection){
//			IStructuredSelection sel = (IStructuredSelection)selection;
//			IPokerCard selectedCard = (IPokerCard) sel.getFirstElement();
//			
//						
//		}
	}
	
	

}
