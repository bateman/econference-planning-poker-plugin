package it.uniba.di.cdg.econference.planningpoker.model.deck;

import java.util.ArrayList;
import java.util.List;

public class DefaultCardDeck implements ICardDeck{

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
	
	public DefaultCardDeck(){
		cards.add(new DefaultPokerCard(UNKNOWN));
		cards.add(new DefaultPokerCard(ONE));
		cards.add(new DefaultPokerCard(TWO));
		cards.add(new DefaultPokerCard(THREE));
		cards.add(new DefaultPokerCard(FIVE));
		cards.add(new DefaultPokerCard(EIGHT));
		cards.add(new DefaultPokerCard(TWENTY));
		cards.add(new DefaultPokerCard(FOURTY));
		cards.add(new DefaultPokerCard(HUNDRED));;
	}
	
	@Override
	public void addCard(IPokerCard card) {
		cards.add(card);

	}

	@Override
	public IPokerCard[] getCards() {
		IPokerCard[] result = new IPokerCard[cards.size()];
		for (int i = 0; i < cards.size(); i++) {
			result[i] = cards.get(i);
		}
		return result;
	}

	@Override
	public IPokerCard getSelectedCard() {
		return selectedCard;
	}

	@Override
	public void setSelectedCard(int index) {
		this.selectedCard = cards.get(index);

	}

	@Override
	public void removeCard(IPokerCard card) {
		cards.remove(card);
	}
}
