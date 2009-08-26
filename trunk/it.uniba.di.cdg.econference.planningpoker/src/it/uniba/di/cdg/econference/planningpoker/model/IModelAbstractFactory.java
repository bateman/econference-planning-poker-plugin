package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIHelper;

import org.eclipse.swt.widgets.Shell;

public interface IModelAbstractFactory {
	
	public IBacklog createBacklog();

	public IUserStoryDialog createUserStoryDialog(Shell shell);
	
	public IBacklogUIProvider createBacklogUIProvider();
	
	public ICardDeck createCardDeck();
	
	/**
	 * The UI Helper takes an ICardDeck implementation, in order to create
	 * the graphic component n the card that are actually in the
	 * card deck
	 * 
	 * @param deck
	 * @return
	 */
	public ICardDeckUIHelper createCardDeckUIHelper();
	
	/*FIXME: the factory should create the packet product too,
	/* but this plugin has to be independent of the implementation
	 * plugin, like jabber or smack?
	 */
	//public IBacklogPacket createBacklogJabberPacket();

}
