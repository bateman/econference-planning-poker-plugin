package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.dialogs.DefaultDeckEditorDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EditDeckAction extends Action {

	private IViewPart view;
	
	public EditDeckAction(IViewPart view) {
		super();
		this.view = view;
		setText("Edit Deck");
		setToolTipText("Add/Remove card in the deck");
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/deck/editDeck16x16.png" ));
	}

	@Override	
	public void run() {
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		DeckView deckView = (DeckView) page.findView(DeckView.ID);
		CardDeck deck = deckView.getModel().getCardDeck();
		DefaultDeckEditorDialog de = new DefaultDeckEditorDialog(window.getShell(),deck);
		if(de.show() == Dialog.OK){			
			System.out.println("notifyCardDeckToRemote()");
			deckView.getManager().notifyCardDeckToRemote(de.getFinalDeck());										
		}
	}

}
