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
package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;

public interface IUserStoryDialog {

	int show();
	
	/**
	 * 
	 * Set a Story to edit
	 * @param story
	 */
	void setStory(IUserStory story);
	
	/**
	 * Return the created or modified story 
	 * 
	 * @return created or modified story 
	 */
	IUserStory getStory();
	
	/**
	 * <p>Set the current CardDeck</p>
	 * CardDeck is useful to add all card values to the UI
	 * in order to allow user to select one of these.
	 * 
	 * @param deck the card deck
	 */
	void setCardDeck(CardDeck deck);

	/**
	 * 
	 * <p>Set the current backlog</p>
	 * In the case of User Story add the backlog is useful to 
	 * check if new inserted id already exists
	 * 
	 * @param backlog the backlog
	 */
	void setBacklog(Backlog backlog);

}
