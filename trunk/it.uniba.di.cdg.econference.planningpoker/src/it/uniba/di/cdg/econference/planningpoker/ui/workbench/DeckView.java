/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.actions.EditDeckAction;
import it.uniba.di.cdg.econference.planningpoker.actions.SelectCardAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardSelectionListener;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimate;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimateListener;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.multichat.model.ParticipantSpecialPrivileges;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

public class DeckView extends ViewPart implements IDeckView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.DeckView";
	
	public IPlanningPokerManager manager;
	
	private Composite parent;
	
	private IDeckViewUIHelper uiHelper;
	private CardDeck deck;
	
	private IPokerCard selectedCard;
	private SelectCardAction selectCardAction; 
	
	private EditDeckAction editBacklogAction;

	public DeckView() {
		// TODO Auto-generated constructor stub
	}
	
	
	private ICardSelectionListener cardSelectionListener = new ICardSelectionListener(){

		@Override
		public void cardSelected(IPokerCard card) {
			selectedCard = card;
			selectCardAction.run();	
		}
		
	};
	
	private IItemListListener deckListener = new ItemListListenerAdapter(){
		
		// Fired when a new card is added to deck
		@Override
		public void itemAdded(Object item) {
			System.out.println( "DeckView: pokerCardAdded()" );
			changeCardDeck();
		}		

		//Fired when a card was removed from deck
		@Override
		public void itemRemoved(Object item) {
			System.out.println( "DeckView: pokerCardRemoved()" );
			changeCardDeck();
		}
	};
	
	private IItemListListener voterListener = new ItemListListenerAdapter() {

		public void itemAdded(Object item) {
			updateActionsAccordingToContext();
		};

		public void itemRemoved(Object item) {	
			updateActionsAccordingToContext();
		};

	};
	
	
	private IPlanningPokerModelListener ppModelListener = new PlanningPokerModelListenerAdapter(){
		
		@Override
		public void cardDeckChanged() {
			System.out.println("Deck View: deck changed ");
			getModel().getCardDeck().addListener(deckListener);
			deck = getModel().getCardDeck();
			changeCardDeck();
		};
		
		
		public void estimateSessionOpened() {
			getModel().getEstimateSession().addListener(estimatesListener);
			updateActionsAccordingToContext();	
		};		
	
	};

	private IEstimateListener estimatesListener = new IEstimateListener(){

		@Override
		public void estimateAdded(IEstimate estimate) {}

		@Override
		public void estimateRemoved(IEstimate estimate) {}

		@Override
		public void estimateStatusChanged(EstimateStatus status,
				String storyId, String estimateId) {
			if(EstimateStatus.CLOSED.equals(status) ||
					EstimateStatus.COMPLETED.equals(status))
				updateActionsAccordingToContext();
		}

	};

	@Override
	//Part control is created in the uiHelper in its constructor	
	public void createPartControl(Composite parent) {
		this.parent = parent;
		makeActions();
		contributeToActionBars( getViewSite().getActionBars() );
	}
	
	/**
     * Add actions to the action and menu bars (local to this view).
     * @param bars
     */
    protected void contributeToActionBars( IActionBars bars ) {
        bars.getToolBarManager().add( editBacklogAction );       
    }
	
	private void makeActions(){
		selectCardAction = new SelectCardAction(this);
		editBacklogAction = new EditDeckAction(this);
	}
	
	@Override
	public IPokerCard getSelectedCard() {
		return selectedCard;
	}

	@Override
	public void setManager(IPlanningPokerManager manager) {
		if(this.manager != null){
			getModel().removeListener(ppModelListener);
			getModel().getCardDeck().removeListener(deckListener);
			getModel().getVoters().removeListener(voterListener);
			uiHelper.removeCardSelectionListener(cardSelectionListener);
		}
		this.manager = manager;
		getModel().getCardDeck().addListener(deckListener);
		getModel().getVoters().addListener(voterListener);
		getModel().addListener(ppModelListener);
		
		deck = getModel().getCardDeck();
		
		uiHelper = PlanningPokerPlugin.getDefault().getHelper().getCardDeckViewUIHelper(parent, deck);	
		uiHelper.addCardSelectionListener(cardSelectionListener);
	
		changeCardDeck();
		
	}

	private void updateActionsAccordingToContext() {
		setDeckEnable((getModel()!=null &&
				getModel().getEstimateSession()!= null &&
				getModel().getEstimateSession().getStatus()==EstimateStatus.CREATED &&
				getModel().getLocalUser().hasSpecialPrivilege(ParticipantSpecialPrivileges.VOTER)));	
		
	}

	@SwtAsyncExec
	private void changeCardDeck() {
		uiHelper.setCardDeck(deck);
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
		updateActionsAccordingToContext();	
		editBacklogAction.setEnabled(!readOnly);
	}

	@Override
	public void setFocus() {
		//uiHelper.setFocus();	
	}
	
	@SwtAsyncExec
	@Override	
	public void setDeckEnable(boolean enable){
		uiHelper.setDeckEnable(enable);	
	}

	@Override
	public IPlanningPokerManager getManager() {
		return manager;
	}


}
