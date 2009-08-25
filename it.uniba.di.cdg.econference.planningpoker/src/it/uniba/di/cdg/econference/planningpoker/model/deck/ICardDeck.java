package it.uniba.di.cdg.econference.planningpoker.model.deck;


public interface ICardDeck {
	
	/**
	 * Get all cards in the deck.
	 * 
	 * @return the array containing all cards
	 */
	IPokerCard[] getCards();
	
	/**
	 * Add one new card to deck. This method should be used to 
	 * edit the deck before starting Planning Poker
	 * 
	 * @param card the card to add 
	 */
	void addCard(IPokerCard card);
	
	
	/**
	 * Remove one card from deck. This method should be used to 
	 * edit the deck before starting Planning Poker
	 * 
	 * @param card the card to remove 
	 */
	void removeCard(IPokerCard card);
	
	
	/**
	 * Return the selected card from deck
	 * 
	 * @return selected card
	 */
	IPokerCard getSelectedCard();
	
	
	/**
	 * Set the selected card in the deck
	 * 
	 * @param index the 0-based index of the cards array
	 * that represents the selected card
	 * 
	 */
	void setSelectedCard(int index);
		

}
