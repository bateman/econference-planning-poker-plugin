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

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;

public interface IPlanningPokerManager extends IEConferenceManager{
 
	/**
     * 
     * @return the planning poker service
     */
    IPlanningPokerService getService();
	
    /**
     * Notify that this client has selected a card
     * 
     * @param storyId the id of estimated User Story
     * 
     * @param card the selected card
     * 
     */
    void notifyCardSelected(String storyId, IPokerCard card );
   
    
    /**
     * <p>Notify that estimate session status has changed</p>
     * 
     * @param estimates the estimate session
     * @param status the {@link EstimateStatus}
     */
	void notifyEstimateSessionStatusChange(IEstimatesList estimates, EstimateStatus status);
	
	
	 /**
     * Remove a user story from backlog: the remote clients will be notified
     * 
	 * @param itemIndex 
     * 
     */
    void notifyRemoveBacklogItem( String itemIndex );

    /**
     * Notify that deck has changed
     * @param deck the new deck
     * 
     */
	void notifyCardDeckToRemote(CardDeck deck);

	/**
	 * <p>Notify that an estimate value has been assigned to a user story</p>
	 * @param storyId the id of the user story
	 * @param estimateValue the estimate value
	 */
	void notifyEstimateAssigned(String storyId, String estimateValue);
	

}
