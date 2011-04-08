package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CardDeckStateTest {

	DefaultPokerCard unknown;
	DefaultPokerCard one;
	DefaultPokerCard two;
	DefaultPokerCard three;
	
	@Before
	public void setUp() throws Exception {		
		unknown = new DefaultPokerCard(DefaultPokerCard.UNKNOWN, "");
		one = new DefaultPokerCard(DefaultPokerCard.ONE, "");
		two =  new DefaultPokerCard(DefaultPokerCard.TWO, "");
		three= new DefaultPokerCard(DefaultPokerCard.THREE, "");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCards() {
		CardDeck deck = new CardDeck();
		deck.addCard(unknown);
		deck.addCard(one);
		deck.addCard(two);
		deck.addCard(three);
		
		IPokerCard[] cards = deck.getCards();
		
		assertEquals(cards[0], unknown);
		assertEquals(cards[1], one);
		assertEquals(cards[2], two);
		assertEquals(cards[3], three);
		
		for (int i = 0; i < cards.length; i++) {
			assertEquals(cards[i], deck.getCard(i));
		}
	}


	@Test
	public void testAddCard() {
		CardDeck deck = new CardDeck();	
		IItemListListener listener = mock(IItemListListener.class);		
		deck.addListener(listener);
		
		deck.addCard(one);
		
		assertEquals(1, deck.size());
		verify(listener).itemAdded(one);				
	}

	@Test
	public void testRemoveCard() {
		CardDeck deck = new CardDeck();
		deck.addCard(unknown);
		deck.addCard(one);
		IItemListListener listener = mock(IItemListListener.class);	
		
		deck.addListener(listener);
		
		deck.removeCard(unknown);
		
		assertEquals(1, deck.size());
		verify(listener).itemRemoved(unknown);
	}


}
