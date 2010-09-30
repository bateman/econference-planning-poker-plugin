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

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.m2m.events.InvitationEvent;
import it.uniba.di.cdg.xcore.m2m.service.Invitee;

import java.util.ArrayList;
import java.util.List;

public class PlanningPokerContext extends EConferenceContext {
	public static final String ROLE_VOTER = "voter";
	
	private Backlog backlog;
	private CardDeck deck;
	
	private List<Invitee> voters;
	
	
	/**
     * Default constructor that builds an unitialized context (require caller dependency injection).
     */
    public PlanningPokerContext() {
        super();
        voters = new ArrayList<Invitee>();
    }
	
	  /**
     * @param nickName
	 * @param personalStatus 
	 * @param role 
     * @param invitation
     */
    public PlanningPokerContext( String nickName, String personalStatus, InvitationEvent invitation ) {
        super( nickName, personalStatus, invitation );
        voters = new ArrayList<Invitee>();
    }

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}
	
	public CardDeck getCardDeck() {
		return deck;
	}

	public void setCardDeck(CardDeck deck) {
		this.deck = deck;
	}

	public CardDeck getDeck() {
		return deck;
	}

	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}
	
	public void addVoter(Invitee voter){
		voters.add(voter);
	}
	
	public Invitee[] getVoters(){
		Invitee[]  result = new Invitee[voters.size()];
		for (int i = 0; i < voters.size(); i++) {
			result[i]  = voters.get(i);
		}
		return result;
	}
	
}
