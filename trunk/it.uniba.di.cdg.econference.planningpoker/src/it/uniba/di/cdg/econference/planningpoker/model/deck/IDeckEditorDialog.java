package it.uniba.di.cdg.econference.planningpoker.model.deck;


public interface IDeckEditorDialog {
	
	int show();
	
	void setCardDeck(CardDeck deck);
	
	CardDeck getFinalDeck();

}
