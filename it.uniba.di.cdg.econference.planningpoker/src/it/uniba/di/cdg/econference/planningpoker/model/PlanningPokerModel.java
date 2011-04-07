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
package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.aspects.ThreadSafetyAspect;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModel;
import it.uniba.di.cdg.xcore.m2m.model.IChatRoomModelListener;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

public class PlanningPokerModel extends ConferenceModel implements IPlanningPokerModel {
	
	/**
	 * The list of user stories in the backlog
	 */
	private Backlog backlog;
	
	private CardDeck deck;
	
	
	private IEstimatesList estimates;
	
	private Voters voters; 
	

	 public PlanningPokerModel() {
	        super();
	        new DefaultModelFactory();	
	        
	        this.deck = new CardDeck();
	        this.backlog = new Backlog();
	        
	        this.voters = new Voters();	        
	    }

	
	@Override
	public void setBacklog(Backlog backlog) {
//		if(this.backlog!=null)
//			this.backlog.dispose();
//		this.backlog = backlog;
//		for (IChatRoomModelListener l : listeners()) {
//            if (l instanceof IPlanningPokerModelListener) {
//                ((IPlanningPokerModelListener) l).backlogChanged();
//            }
//        }
		/* We do not set the backlog to the new object to avoid 
		 * adding the backlogChanged method listener in all views 
		 */
		this.backlog.setBacklogContent(backlog.getUserStories());	
		this.backlog.setCurrentItemIndex(backlog.getCurrentItemIndex());
	}

	@Override
	public Backlog getBacklog() {
		return backlog;
	}

	@Override
	public CardDeck getCardDeck() {
		return deck;
	}

	@Override
	public void setCardDeck(CardDeck deck) {
		if(this.deck!=null)
			this.deck.dispose();
		this.deck = deck;	
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).cardDeckChanged();
            }
        }
	}


	@Override
	public IEstimatesList getEstimateSession() {		
		return estimates;
	}


	@Override
	public void openEstimateSession(IEstimatesList estimates) {
		if(this.estimates!=null)
			this.estimates.dispose();
		this.estimates = estimates;
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).estimateSessionOpened();
            }
        }
	}
	
	@Override
	public void setVoters(Voters voters) {
		if(this.voters!=null)
			this.voters.dispose();
		this.voters = voters;
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).votersListChanged();
            }
        }
	}
	
	@Override
	public Voters getVoters(){
		return voters;
	}

	/**
	 * Provides internal thread synchronization.
	 */
	@Aspect
	public static class OwnThreadSafety extends ThreadSafetyAspect {

		@Pointcut("execution( public * PlanningPokerModel.get*(..) )")
		protected void readOperations() {
		}

		@Pointcut("execution( public void PlanningPokerModel.set*(..) )")
		protected void writeOperations() {
		}
	}

}
