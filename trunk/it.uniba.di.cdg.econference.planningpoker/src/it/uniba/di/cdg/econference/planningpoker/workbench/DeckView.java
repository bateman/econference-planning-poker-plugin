package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.actions.SelectCardAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerParticipantsSpecialRoles;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardSelectionListener;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class DeckView extends ViewPart implements IDeckView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.DeckView";
	
	public IPlanningPokerManager manager;
	
	private Composite parent;
	
	private IDeckViewUIHelper uiHelper;
	private CardDeck deck;
	
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
	
	private IItemListListener voterListener = new ItemListListenerAdapter() {

		public void itemAdded(Object item) {
			updateActionsAccordingToRole();
		};

		public void itemRemoved(Object item) {	
			updateActionsAccordingToRole();
		};


	};
	
	private IItemListListener backlogListener = new ItemListListenerAdapter() {

		public void currentSelectionChanged(int currItemIndex) {
			updateActionsAccordingToRole();			
		};


	};
	
	
	private ICardSelectionListener cardSelectionListener = new ICardSelectionListener(){

		@Override
		public void cardSelected(IPokerCard card) {
			selectedCard = card;
			selectCardAction.run();	
		}
		
	};
	
	private IPlanningPokerModelListener ppModelListener = new PlanningPokerModelListenerAdapter(){
		
		@Override
		public void cardDeckChanged() {
			System.out.println("Deck View: deck changed ");
			getModel().getCardDeck().addListener(deckListener);
			createCardDeck();
		};
		
		@Override
		public void backlogChanged() {
			getModel().getBacklog().addListener(backlogListener);
		};
		
//		@Override
//		public void localUserChanged() {
//			System.out.println("Deck View: participant changed ");
//			updateActionsAccordingToRole();
//		}
//		
//		@Override
//		public void changed(IParticipant participant) {
//			System.out.println("Deck View: participant changed "+participant.getId());
//			updateActionsAccordingToRole();
//		}
//		
//		@Override
//		public void statusChanged() {
//			if(getModel().getStatus().equals(ConferenceStatus.STOPPED))
//				setReadOnly(true);
//			else
//				updateActionsAccordingToRole();			
//		}		
	
	};


	
	
	@SwtAsyncExec
	private void addPokerCard(IPokerCard card) {
		uiHelper.addWidgetFromCard(card);		
	}
	
	@SwtAsyncExec
	private void removePokerCard(IPokerCard card) {
		uiHelper.removeWidgetFromCard(card);		
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		//Part control is created in the uiHelper in its constructor	
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
			getModel().removeListener(ppModelListener);
			getModel().getCardDeck().removeListener(deckListener);
			getModel().getVoters().removeListener(voterListener);
			getModel().getBacklog().removeListener(backlogListener);
			uiHelper.removeCardSelectionListener(cardSelectionListener);
		}
		this.manager = manager;
		getModel().getCardDeck().addListener(deckListener);
		getModel().getVoters().addListener(voterListener);
		getModel().getBacklog().addListener(backlogListener);
		getModel().addListener(ppModelListener);
		
		uiHelper = getModel().getFactory().createCardDeckViewUIHelper(parent);	
		uiHelper.addCardSelectionListener(cardSelectionListener);
		
		deck = getModel().getCardDeck();
				
		createCardDeck();
		
		//updateActionsAccordingToRole();
	}

	private void updateActionsAccordingToRole() {
		setReadOnly(!(getModel()!=null && 
				getModel().getBacklog().getCurrentItemIndex()!=IItemList.NO_ITEM_SELECTED &&
				getModel().getLocalUser().getSpecialRole().equals(PlanningPokerParticipantsSpecialRoles.VOTER)));	
		
	}

	@SwtAsyncExec
	private void createCardDeck() {
		IPokerCard[] cards = deck.getCards();
		for (IPokerCard card : cards) {
			uiHelper.addWidgetFromCard(card);
		}		
	}

	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

	@Override
	public boolean isReadOnly() {
		return uiHelper.isReadOnly();
	}

	@Override
	@SwtAsyncExec
	public void setReadOnly(boolean readOnly) {
		//System.out.println("SET READ ONLY "+readOnly);
		uiHelper.setReadOnly(readOnly);			
	}

	@Override
	public void setFocus() {
		//uiHelper.setFocus();	
	}

	@Override
	public IPlanningPokerManager getManager() {
		return manager;
	}


}
