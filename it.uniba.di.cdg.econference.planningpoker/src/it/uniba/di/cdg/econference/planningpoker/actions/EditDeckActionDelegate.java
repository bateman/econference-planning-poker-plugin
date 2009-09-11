package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.dialogs.DefaultDeckEditorDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.workbench.DeckView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class EditDeckActionDelegate implements IViewActionDelegate {

	private IViewPart view;
	
	@Override
	public void init(IViewPart view) {
		this.view = view;

	}

	@Override
	public void run(IAction action) {
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

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
