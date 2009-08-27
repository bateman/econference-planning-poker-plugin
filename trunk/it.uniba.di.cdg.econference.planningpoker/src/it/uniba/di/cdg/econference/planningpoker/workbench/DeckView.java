package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.actions.SelectCardAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardSelectionListener;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;

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
	
	private PlanningPokerModelListenerAdapter ppModelListener = new PlanningPokerModelListenerAdapter(){
		
		@Override
		public void statusChanged() {
			if(getModel().getStatus().equals(ConferenceStatus.STOPPED))
				setReadOnly(true);
			else
				updateActionsAccordingToRole();
		}		
		
		// We have to check if this participant is still in voter list
		@Override
		public void voterListChanged() {
			updateActionsAccordingToRole();
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
			getModel().removeListener(ppModelListener);
			getModel().getCardDeck().removeListener(deckListener);
			uiHelper.removeCardSelectionListener(cardSelectionListener);
		}
		this.manager = manager;
		getModel().getCardDeck().addListener(deckListener);
		getModel().addListener(ppModelListener);
		
		deck = getModel().getCardDeck();
		uiHelper = getModel().getFactory().createCardDeckUIHelper();	
		uiHelper.addCardSelectionListener(cardSelectionListener);
		
		createCardDeck();
		
		updateActionsAccordingToRole();
	}

	private void updateActionsAccordingToRole() {
		setReadOnly(!(getModel()!=null && getModel().getVoters().isVoter(getModel().getLocalUser())));	
		
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
		return parent.isEnabled();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		parent.setEnabled(!readOnly);
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
