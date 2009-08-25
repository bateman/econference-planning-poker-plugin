package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultBacklogUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultCardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultCardDeckUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIProvider;

import org.eclipse.swt.widgets.Shell;

public class DefaultModelFactory implements IModelAbstractFactory {

	@Override
	public IBacklog createBacklog() {
		return new DefaultBacklog();
	}

	@Override
	public IUserStoryDialog createUserStoryDialog(Shell shell) {
		return new SimpleUserStoryDialog(shell);
	}

	@Override
	public IBacklogUIProvider createBacklogUIProvider() {
		return new DefaultBacklogUIProvider();
	}

	@Override
	public ICardDeck createCardDeck() {
		return new DefaultCardDeck();
	}
	
	@Override
	public ICardDeckUIProvider createCardDeckUIProvider() {
		return new DefaultCardDeckUIProvider();
	}


}
