package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardDeck{

	private Set<IItemListListener> listeners;
	
	private List<IPokerCard> cards;	
	private List<IPokerCard> hiddenCards; 
	
	
	public CardDeck(){				
		listeners = new HashSet<IItemListListener>();
		cards = new ArrayList<IPokerCard>();	
		hiddenCards = new ArrayList<IPokerCard>();	
	}
	

	/**
	 * Get all cards in the deck.
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
	
	/**
	 * Remove one card from deck. This method should be used to 
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
	 * Get the card with that string value 
	 * 
	 * @param cardValue the string value of the card
	 * @return the card from deck
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
	
	public IPokerCard getCard(int itemIndex) {
		return cards.get(itemIndex);
	}


	public void removeCard(int itemIndex) {
		for(IItemListListener l: listeners)
			l.itemRemoved(cards.get(itemIndex));
		cards.remove(itemIndex);
	}


	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);		
	}

	public int size() {
		return cards.size();
	}

	/**
	 * Perform clean up operation
	 */
	public void dispose() {		
		listeners.clear();		
	}

	public void addHiddenCard(IPokerCard card){
		hiddenCards.add(card);
	}
	
	public void removeHiddenCard(IPokerCard card){
		hiddenCards.remove(card);
	}
	
	public IPokerCard[] getHiddenCards(){
		IPokerCard[] result = new IPokerCard[hiddenCards.size()];
		for (int i = 0; i < hiddenCards.size(); i++) {
			result[i] = hiddenCards.get(i);
		}
		return result;
	}



}
