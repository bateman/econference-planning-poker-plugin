package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardDeck implements IItemList{

	private Set<IItemListListener> listeners;
	
	private IPokerCard selectedCard;
	private List<IPokerCard> cards = new ArrayList<IPokerCard>();	
	
	public static final String UNKNOWN ="Unknown";
	public static final String ONE ="1";
	public static final String TWO ="2";
	public static final String THREE ="3";
	public static final String FIVE ="5";
	public static final String EIGHT ="8";
	public static final String TWENTY ="20";
	public static final String FOURTY ="40";
	public static final String HUNDRED ="100";
	
	private static final String IMAGE_ONE = "icons/deck/1.ico";
	private static final String IMAGE_TWO = "icons/deck/2.ico";
	private static final String IMAGE_THREE = "icons/deck/3.ico";
	private static final String IMAGE_FIVE = "icons/deck/5.ico";
	private static final String IMAGE_EIGHT = "icons/deck/8.ico";
	private static final String IMAGE_TWENTY = "icons/deck/as.gif";
	private static final String IMAGE_UNKNOWN = "icons/deck/0.ico";
	
	public CardDeck(){
		cards.add(new DefaultPokerCard(UNKNOWN, IMAGE_UNKNOWN));
		cards.add(new DefaultPokerCard(ONE, IMAGE_ONE));
		cards.add(new DefaultPokerCard(TWO, IMAGE_TWO));
		cards.add(new DefaultPokerCard(THREE, IMAGE_THREE));
		cards.add(new DefaultPokerCard(FIVE, IMAGE_FIVE));
		cards.add(new DefaultPokerCard(EIGHT, IMAGE_EIGHT));
		cards.add(new DefaultPokerCard(TWENTY, IMAGE_TWENTY));
		cards.add(new DefaultPokerCard(FOURTY, null));
		cards.add(new DefaultPokerCard(HUNDRED, null));
		
		listeners = new HashSet<IItemListListener>();
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
	 * Return the selected card from deck
	 * 
	 * @return selected card
	 */
	public IPokerCard getSelectedCard() {
		return selectedCard;
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
		if(this.selectedCard==cards)
			setCurrentItemIndex(NO_ITEM_SELECTED);		
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

	@Override
	public void addItem(Object item) {
		addCard((IPokerCard) item);
		
	}

	@Override
	public void addItem(String itemText) {
		// Not used: we cannot add string item		
	}

	@Override
	public void addListener(IItemListListener listener) {
		listeners.add(listener);
		
	}

	// Not used
	@Override
	public void decode(String encodedItems) {
	}

	// Not used
	@Override
	public String encode() {		
		return null;
	}

	@Override
	public int getCurrentItemIndex() {	
		int index = NO_ITEM_SELECTED;
		if(selectedCard!=null)
			cards.indexOf(selectedCard);
		return index;
	}

	@Override
	public Object getItem(int itemIndex) {
		return cards.get(itemIndex);
	}

	@Override
	public void removeItem(int itemIndex) {
		for(IItemListListener l: listeners)
			l.itemRemoved(cards.get(itemIndex));
		cards.remove(itemIndex);
	}

	@Override
	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);		
	}

	@Override
	public void setCurrentItemIndex(int itemIndex) {
		this.selectedCard = cards.get(itemIndex);
		for(IItemListListener l: listeners)
			l.currentSelectionChanged(itemIndex);
	}

	@Override
	public int size() {
		return cards.size();
	}





}
