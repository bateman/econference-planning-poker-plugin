package it.uniba.di.cdg.econference.planningpoker.model.deck;


/**
 * This class helps Deck View to create the deck graphically.
 *  
 * @author Alex
 *
 */

public interface IDeckViewUIHelper {

	void addWidgetFromCard(IPokerCard card);

	void removeWidgetFromCard(IPokerCard card);
	
	void addCardSelectionListener(ICardSelectionListener listener);
	
	void removeCardSelectionListener(ICardSelectionListener listener);

	void setReadOnly(boolean readOnly);

	boolean isReadOnly();
	
	void setFocus();

}
