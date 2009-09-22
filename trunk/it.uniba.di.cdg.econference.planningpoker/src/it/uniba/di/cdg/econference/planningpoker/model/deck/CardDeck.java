package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardDeck{

	private Set<IItemListListener> listeners;
	
	/**
	 * The list of the cards that actually are visibile in the deck
	 */
	private List<IPokerCard> cards;	
	
	/**
	 * The list of the cards that actually are hidden
	 */
	private List<IPokerCard> hiddenCards; 
	
	
	public CardDeck(){				
		listeners = new HashSet<IItemListListener>();
		cards = new ArrayList<IPokerCard>();	
		hiddenCards = new ArrayList<IPokerCard>();	
	}
	

	/**
	 * Get the actual list of visible cards in the deck.
	 * 
	 * @return the array containing all cards
	 */
	public IPokerCard[] getCards() {
		IPokerCard[] result = new IPokerCard[cards.size()];
		for (int i = 0; i < cards.size(); i++) {
			result[i] = cards.get(i);
		}
		return result;
	}
	
	/**
	 * Get the actual list of hidden cards.
	 * 
	 * @return the array containing all cards
	 */
	public IPokerCard[] getHiddenCards(){
		IPokerCard[] result = new IPokerCard[hiddenCards.size()];
		for (int i = 0; i < hiddenCards.size(); i++) {
			result[i] = hiddenCards.get(i);
		}
		return result;
	}


	/**
	 * Add one new card to deck. This method should be used to 
	 * edit the deck before starting Planning Poker
	 * 
	 * @param card the card to add 
	 */
	public void addCard(IPokerCard card) {
		cards.add(card);		
		for(IItemListListener l : listeners){
			l.itemAdded(card);
		}
	}
	
	
	public void addCard(IPokerCard card, int index){
		cards.add(index, card);
		for(IItemListListener l : listeners){
			l.itemAdded(card);
		}
	}
	
	
	public void addHiddenCard(IPokerCard card){
		hiddenCards.add(card);
	}
	
	/**
	 * Remove one card from the list of visible cards. This method should be used to 
	 * edit the deck before starting Planning Poker
	 * 
	 * @param card the card to remove 
	 */
	public void removeCard(IPokerCard card) {	
		cards.remove(card);
		for(IItemListListener l: listeners)
			l.itemRemoved(card);
	}
	
	/**
	 * Remove one card from the list of hidden cards.
	 * 
	 * @param card the card to remove 
	 */
	public void removeHiddenCard(IPokerCard card){
		hiddenCards.remove(card);
	}

	/**
	 * <p>Get the visible card with that string value</p> 
	 * 
	 * @param cardValue the string value of the card
	 * @return the card in the deck if exists or null otherwise
	 */
	public IPokerCard getCardFromStringValue(String cardValue) {
		for(IPokerCard card : cards){
			if(card.getStringValue().equalsIgnoreCase(cardValue))
				return card; 
		}
		return null;
	}

	public void addListener(IItemListListener listener) {
		listeners.add(listener);
		
	}
	
	/**
	 * Get the visible card at specific position in the deck
	 * @param itemIndex the position of the card
	 * @return the card 
	 */
	public IPokerCard getCard(int itemIndex) {
		return cards.get(itemIndex);
	}


	/**
	 * <p>Remove the visible card at specific position in the deck</p>
	 * <p>Warning: this method does not add the removed card to the list of hidden cards</p>
	 * @param itemIndex the position of the card
	 * 
	 */
	public void removeCard(int itemIndex) {
		for(IItemListListener l: listeners)
			l.itemRemoved(cards.get(itemIndex));
		cards.remove(itemIndex);
	}


	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);		
	}

	/**	
	 * @return the number of visible cards in the deck
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Perform clean up operation
	 */
	public void dispose() {		
		listeners.clear();		
	}



	




}
