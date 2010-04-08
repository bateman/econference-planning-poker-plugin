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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class DefaultDeckViewUIHelper implements IDeckViewUIHelper {

//	private String selectedValue;
	private static final String KEY_VALUE = "value";
	private static final int SELECTION_COLOR = SWT.COLOR_RED;
	private static final int DEFAULT_COLOR = SWT.COLOR_GRAY;
	
	private Set<ICardSelectionListener> listeners;
	private Composite parent;
	private CardDeck deck;
	
	public DefaultDeckViewUIHelper(Composite parent, CardDeck deck) {
		this.parent = parent;
		this.deck = deck;
		listeners = new HashSet<ICardSelectionListener>();
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
		for (int i = 0; i < deck.getCards().length; i++) {
			addWidgetFromCard(deck.getCards()[i]);
		}		
	}
	

	public void cleanPartControl() {
		Control[] controls = parent.getChildren();
		for(Control control: controls){
			if(control instanceof Button){
				control.dispose();
			}
		}		
	}

	private void addWidgetFromCard(final IPokerCard card) {		
		Button button = new Button(parent, SWT.TOGGLE );
		button.setData(KEY_VALUE, card.getStringValue());
		button.setBackground(parent.getDisplay().getSystemColor(DEFAULT_COLOR));
		button.setText(card.getStringValue());
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {		
				setSelectedButton((Button) event.widget);
//				selectedValue = card.getStringValue();	
				for(ICardSelectionListener l : listeners){
					l.cardSelected(card);
				}
			}
				
		});
				
//		if(card.hasImagePath()){
//			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(card.getImagePath());
//			image = ImageDescriptor.createFromURL(url).createImage();			
//			button.setImage(image);
//		}
		parent.layout();
		
	}
	
	private void setSelectedButton(Button button) {								
		Control[] controls = parent.getChildren();
		for(Control control: controls){
			if(control instanceof Button){
				Button current = (Button) control;
				current.setBackground(parent.getDisplay().getSystemColor(DEFAULT_COLOR));
				current.setSelection(false);
			}
		}
		if(button!=null){
			button.setBackground(parent.getDisplay().getSystemColor(SELECTION_COLOR));
			button.setSelection(true);
		}
	}
	
	
	private void clearSelection(){
		setSelectedButton(null);
	}
		

//	@Override
//	public void removeWidgetFromCard(IPokerCard card) {
//		Control[] controls = parent.getChildren();
//		for(Control control: controls){
//			if(control.getData(KEY_VALUE).equals(card.getStringValue())){
//				control.dispose();
//			}
//		}		
//	}	
	
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
		parent.setEnabled(enable);	
		clearSelection();
	}

	@Override
	public boolean isReadOnly() {
		return !parent.isEnabled();
	}
	
	@Override
	public void setFocus(){
		parent.setFocus();
	}

}


