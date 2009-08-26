package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.actions.SelectCardAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardSelectionListener;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class DeckView extends ViewPart implements IDeckView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.DeckView";
	
	//public TableViewer viewer;
	public Composite parent;
	public IPlanningPokerManager manager;
	
	private ICardDeckUIHelper uiHelper;
	private ICardDeck deck;
	
	private IPokerCard selectedCard;
	private SelectCardAction selectCardAction; 

	public DeckView() {
		// TODO Auto-generated constructor stub
	}
	
	private IItemListListener deckListener = new ItemListListenerAdapter(){
		
		// Fired when a new card is added to deck
		@Override
		public void itemAdded(Object item) {
			System.out.println( "pokerCardAdded()" );
			addPokerCard((IPokerCard) item);
		}		

		//Fired when a card was removed from deck
		@Override
		public void itemRemoved(Object item) {
			System.out.println( "pokerCardRemoved()" );
			removePokerCard((IPokerCard) item);
		}
	};
	
	
	private ICardSelectionListener cardSelectionListener = new ICardSelectionListener(){

		@Override
		public void cardSelected(IPokerCard card) {
			selectedCard = card;
			selectCardAction.run();	
		}
		
	};
	
	@SwtAsyncExec
	private void addPokerCard(IPokerCard card) {
		uiHelper.addWidgetFromCard(card,parent);		
	}
	
	@SwtAsyncExec
	private void removePokerCard(IPokerCard card) {
		uiHelper.removeWidgetFromCard(card,parent);		
	}

	@Override
	public void createPartControl(Composite parent) {
		//viewer = new TableViewer(parent, SWT.HORIZONTAL);	
		this.parent = parent;
		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;
		parent.setLayout(layout);
		
		makeActions();
	}
	
	private void makeActions(){
		selectCardAction = new SelectCardAction(this);
	}
	
	@Override
	public IPokerCard getSelectedCard() {
		return selectedCard;
	}

	@Override
	public void setSelectedCard(IPokerCard selectedCard) {
		this.selectedCard = selectedCard;

	}


	@Override
	@SwtAsyncExec
	public void setManager(IPlanningPokerManager manager) {
		if(this.manager != null){
			getModel().getCardDeck().removeListener(deckListener);
			uiHelper.removeCardSelectionListener(cardSelectionListener);
		}
		this.manager = manager;
		getModel().getCardDeck().addListener(deckListener);
		
		deck = getModel().getCardDeck();
		uiHelper = getModel().getFactory().createCardDeckUIHelper();	
		uiHelper.addCardSelectionListener(cardSelectionListener);
		
		createCardDeck();
	}

	@SwtAsyncExec
	private void createCardDeck() {
		IPokerCard[] cards = deck.getCards();
		for (IPokerCard card : cards) {
			uiHelper.addWidgetFromCard(card,parent);
		}		
	}

	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

	@Override
	public boolean isReadOnly() {
		return getManager().getStatus().equals(ConferenceStatus.STOPPED);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		parent.setEnabled(!readOnly);
//		selectCardAction.setAccessible(!readOnly);
		if(readOnly){
//			if(doubleClickListener != null){
//				viewer.getTable().removeListener(SWT.MouseDoubleClick, doubleClickListener );
//				doubleClickListener = null;
//			}

		}else{
//			if(doubleClickListener == null)
//				doubleClickListener = new Listener() {
//				public void handleEvent(Event event) {
//					selectCardAction.run();
//				} 
//			};
//			viewer.getTable().addListener(SWT.MouseDoubleClick, doubleClickListener );			
		}		
	}

	@Override
	public void setFocus() {
		parent.setFocus();		
	}

	@Override
	public IPlanningPokerManager getManager() {
		return manager;
	}


}
