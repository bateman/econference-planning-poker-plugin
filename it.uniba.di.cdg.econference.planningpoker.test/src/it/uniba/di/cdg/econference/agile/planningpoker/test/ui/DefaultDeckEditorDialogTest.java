package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;

import junit.framework.TestCase;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.DefaultDeckEditorDialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DefaultDeckEditorDialogTest extends TestCase{
	
	private DefaultDeckEditorDialog de;
	private CardDeck deck;
	private int totalCards;
	
	@Override
	protected void setUp() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		
		
		deck = new CardDeck();        	
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.UNKNOWN, DefaultPokerCard.IMAGE_UNKNOWN));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.ONE, DefaultPokerCard.IMAGE_ONE));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWO, DefaultPokerCard.IMAGE_TWO));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.THREE, DefaultPokerCard.IMAGE_THREE));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.FIVE, DefaultPokerCard.IMAGE_FIVE));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.EIGHT, DefaultPokerCard.IMAGE_EIGHT));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWENTY, null));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.FOURTY, null));
    	deck.addCard(new DefaultPokerCard(DefaultPokerCard.HUNDRED, null));
    	
    	totalCards = deck.getCards().length;
    	
    	de = new DefaultDeckEditorDialog(shell,deck);
	}
	
	
	public void testDialog() {			
		if(de.show() == Dialog.OK){
			CardDeck finalDeck = de.getFinalDeck();
			int cards = finalDeck.getCards().length;
			int hiddenCards = finalDeck.getHiddenCards().length;			
			assertEquals(cards+hiddenCards, totalCards);							
		}else{
			assertEquals(de.getFinalDeck(), deck);
		}
		
	}	
	
	
//	/**
//	 * Remove 2 cards in the dialog
//	 * 
//	 */
//	public void testDialogEdit(){
//		if(de.show() == Dialog.OK){
//			assertEquals(de.getFinalDeck().getHiddenCards().length, 2);
//			assertEquals(de.getFinalDeck().getCards().length, 7);
//		}
//	}

}
