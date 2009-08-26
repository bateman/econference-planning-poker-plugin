package it.uniba.di.cdg.econference.planningpoker.model.deck;

import org.eclipse.swt.widgets.Composite;

/**
 * This class helps Deck View to create the deck graphically.
 *  
 * @author Alex
 *
 */

public interface ICardDeckUIHelper {

	void addWidgetFromCard(IPokerCard card, Composite parent);

	void removeWidgetFromCard(IPokerCard card, Composite parent);
	
	void addCardSelectionListener(ICardSelectionListener listener);
	
	void removeCardSelectionListener(ICardSelectionListener listener);

}
