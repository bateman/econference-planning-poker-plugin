package it.uniba.di.cdg.econference.planningpoker.ui.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;

public interface IDeckEditorDialog {
	
	int show();
	
	void setCardDeck(CardDeck deck);
	
	CardDeck getFinalDeck();

}
