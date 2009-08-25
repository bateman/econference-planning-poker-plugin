package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.multichat.InvitationEvent;

public class PlanningPokerContext extends EConferenceContext {
	
	
	private IBacklog backlog;
	private ICardDeck deck;
	
	
	/**
     * Default constructor that builds an unitialized context (require caller depedency injection).
     */
    public PlanningPokerContext() {
        super();
    }
	
	  /**
     * @param nickName
     * @param invitation
     */
    public PlanningPokerContext( String nickName, InvitationEvent invitation ) {
        super( nickName, invitation );
    }

	public IBacklog getBacklog() {
		return backlog;
	}

	public void setBacklog(IBacklog backlog) {
		this.backlog = backlog;
	}
	
	public ICardDeck getCardDeck() {
		return deck;
	}

	public void setCardDeck(ICardDeck deck) {
		this.deck = deck;
	}
	
	
}
