package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIProvider;

import org.eclipse.swt.widgets.Shell;

public interface IModelAbstractFactory {
	
	public IBacklog createBacklog();

	public IUserStoryDialog createUserStoryDialog(Shell shell);
	
	public IBacklogUIProvider createBacklogUIProvider();
	
	public ICardDeck createCardDeck();
	
	public ICardDeckUIProvider createCardDeckUIProvider();

}
