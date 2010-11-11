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
package it.uniba.di.cdg.econference.planningpoker;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import it.uniba.di.cdg.econference.planningpoker.model.IModelAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IDeckViewUIHelper;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.econference.IEConferenceHelper;

/**
 * Planning Poker helper provides some useful methods to access planning poker meeting functions (basically
 * setting the factory that create the model, getting the UI Provider to fill views, ecc. ).
 */
public interface IPlanningPokerHelper extends IEConferenceHelper {

    public static final String PLANNINGPOKER_REASON = "planningpoker";

    IPlanningPokerManager open( EConferenceContext context, boolean autojoin );

    /**
	 * Set the factory that create the model. See {@link IModelAbstractFactory}
	 * @param factory
	 */
	void setModelFactory(IModelAbstractFactory factory);
	
	/**
	 * Get the dialog to create and edit user stories in the backlog according with the model factory
	 * @param shell 
	 * @return an implementation of the dialog
	 */
	IUserStoryDialog getUserStoryDialog(Shell shell);
	
	/**
	 * Used to retrieve the method for parsing the xml related to the list of user story
	 * @return an implementation of {@link IBacklogContextLoader}
	 */
	IBacklogContextLoader getBacklogContextLoader();
	
	/**
	 * Get the UI provider (according to the model factory) for the Backlog view. The UI provider will be 
	 * responsible of structuring the table by creating the columns and cell contents  
	 * @return an implementation of {@link IBacklogViewUIProvider}
	 */
	IBacklogViewUIProvider getBacklogViewUIProvider();
	
	/**
	 * Get the UI provider (according to the model factory) for the Estimate view. The UI provider will be 
	 * responsible of structuring the table by creating the columns and cell contents 
	 * @return an implementation of {@link IEstimatesViewUIProvider}
	 */
	IEstimatesViewUIProvider getEstimateViewUIProvider();
	
	/**
	 * Get the UI Helper (according to the model factory) for the DeckView. The helper will be responsible of
	 * drawing the content of Deck view: layout of the cards, shape of cards, etc. 
	 * @param parent
	 * @param deck
	 * @return an implementation of {@link IDeckViewUIHelper}
	 */
	IDeckViewUIHelper getCardDeckViewUIHelper(Composite parent, CardDeck deck);
	
}
