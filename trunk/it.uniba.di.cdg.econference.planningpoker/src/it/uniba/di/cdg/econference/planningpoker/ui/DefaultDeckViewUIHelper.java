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
package it.uniba.di.cdg.econference.planningpoker.ui;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardSelectionListener;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class DefaultDeckViewUIHelper implements IDeckViewUIHelper {

	private Set<ICardSelectionListener> listeners;
	private Composite parent;
	private CardDeck deck;
	
	private List<CardButton> cardWidgets;
	private Canvas emptySpace;
	private CardTarget dropTarget;
	
	public DefaultDeckViewUIHelper(Composite parent, CardDeck deck) {
		this.parent = parent;
		this.deck = deck;
		listeners = new HashSet<ICardSelectionListener>();
		
		this.cardWidgets = new ArrayList<CardButton>();
		createPartControl();
	}
	
	@Override
	public void setCardDeck(CardDeck deck){
		this.deck = deck;
		
		paintDeck();
	}
	
	private void createPartControl() {
		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;
		parent.setLayout(layout);
		paintDeck();
	}

	private void paintDeck() {
		cleanPartControl();
		
		// Cards
		IPokerCard[] cards = deck.getCards();
		for (int i = 0; i < cards.length; i++) {
			createWidgetFromCard(cards[i]);
		}
		
		// Empty space
		emptySpace = new Canvas(parent, SWT.NONE);
		
		// Drop target
		dropTarget = new CardTarget(parent);
		createDropTarget(dropTarget);
		
		parent.layout();
	}
	
	public void cleanPartControl() {
		Iterator<CardButton> itr = cardWidgets.iterator();
	    while (itr.hasNext()) {
	    	CardButton cardButton = itr.next();
	    	itr.remove();
	    	//cardWidgets.remove(cardButton);
	    	cardButton.dispose();
	    }

	    if (emptySpace != null)
	    	emptySpace.dispose();
	    
	    if (dropTarget != null)
	    	dropTarget.dispose();
	}
	
	private CardButton createWidgetFromCard(IPokerCard card) {
		CardButton cardButton = new CardButton(parent, card.getStringValue());
		createDragSource(cardButton, card);
		cardWidgets.add(cardButton);
		
		return cardButton;
	}
	
	private void createDragSource(final CardButton widget, final IPokerCard card) {
		DragSource ds = new DragSource(widget, DND.DROP_MOVE);
	    ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
	    ds.addDragListener(new DragSourceAdapter() {
		    public void dragSetData(DragSourceEvent event) {
		    	event.data = card.getStringValue();
		    	
		    	for(ICardSelectionListener l : listeners){
					l.cardSelected(card);
				}
		    }
	    });
	}
	
	private void createDropTarget(final CardTarget object) {
		DropTarget dt = new DropTarget(object, DND.DROP_MOVE);
	    
	    dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
	    dt.addDropListener(new DropTargetAdapter() {
		    public void drop(DropTargetEvent event) {
		    	String draggedCardValue = (String)event.data;
		    	cardDragged(draggedCardValue);
		    	object.setEstimate(draggedCardValue);
		    }
	    });
	}
	
	private void cardDragged(String draggedCardValue) {
		Iterator<CardButton> itr = cardWidgets.iterator();
	    while (itr.hasNext()) {
	    	CardButton card = itr.next();
	    	if (card.getValue().equals(draggedCardValue)) {
	    		card.setSelected(true);
	    	}
	    	else {
	    		card.setSelected(false);
	    	}
	    }
	}
	
	public void removeWidgetFromCard(IPokerCard card) {
		Iterator<CardButton> itr = cardWidgets.iterator();
	    while (itr.hasNext()) {
	    	CardButton cardButton = itr.next();
	    	if (cardButton.getValue().equals(card.getStringValue())) {
	    		cardWidgets.remove(cardButton);
	    		cardButton.dispose();
	    	}
	    }
	}	
	
	@Override
	public void addCardSelectionListener(ICardSelectionListener listener){
		listeners.add(listener);		
	}

	@Override
	public void removeCardSelectionListener(ICardSelectionListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setDeckEnable(boolean enable) {
		resetDeck();
		parent.setEnabled(enable);
		dropTarget.setEnabled(enable);
	}

	@Override
	public boolean isReadOnly() {
		return !parent.isEnabled();
	}
	
	@Override
	public void setFocus(){
		parent.setFocus();
	}

	@Override
	public void resetDeck() {
		Iterator<CardButton> itr = cardWidgets.iterator();
	    while (itr.hasNext()) {
	    	CardButton card = itr.next();
	    	card.setSelected(false);
	    }
	    
	    dropTarget.setEstimate("");
	}

}


