package it.uniba.di.cdg.econference.planningpoker;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.multichat.InvitationEvent;
import it.uniba.di.cdg.xcore.multichat.service.Invitee;

public class PlanningPokerContext extends EConferenceContext {
	public static final String ROLE_VOTER = "voter";
	
	private Backlog backlog;
	private CardDeck deck;
	
	private List<Invitee> voters;
	
	
	/**
     * Default constructor that builds an unitialized context (require caller depedency injection).
     */
    public PlanningPokerContext() {
        super();
        voters = new ArrayList<Invitee>();
    }
	
	  /**
     * @param nickName
     * @param invitation
     */
    public PlanningPokerContext( String nickName, InvitationEvent invitation ) {
        super( nickName, invitation );
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
			result[0]  = voters.get(0);
		}
		return result;
	}
	
}
