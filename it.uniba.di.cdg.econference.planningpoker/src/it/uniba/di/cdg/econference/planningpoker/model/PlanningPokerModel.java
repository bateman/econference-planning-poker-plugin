package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModel;

public class PlanningPokerModel extends ConferenceModel implements IPlanningPokerModel {

	/**
	 * The value of the selected card
	 */
	private Object cardValue;
	
	/**
	 * The list of user stories in the backlog
	 */
	private IBacklog backlog;
	
	
	private IModelAbstractFactory factory;
	
	private ICardDeck deck;

	
	
	 public PlanningPokerModel() {
	        super();
	        /* TODO The abstract factory is initialized with the Default 
			 * Factory which is the base factory
			 */		
	        this.factory = new DefaultModelFactory();
	        this.backlog = factory.createBacklog();
	        this.deck = factory.createCardDeck();
	    }
	
	@Override
	public void setCardValue(Object cardValue) {
		this.cardValue = cardValue;		
	}

	@Override
	public void setBacklog(IBacklog backlog) {
		this.backlog.setBacklogContent(backlog.getUserStories());		
	}

	@Override
	public IBacklog getBacklog() {
		return backlog;
	}

	@Override
	public Object getCardValue() {
		return cardValue;		
	}

	@Override
	public IModelAbstractFactory getFactory() {
		return factory;
	}

	@Override
	public void setModelFactory(IModelAbstractFactory factory) {
		this.factory = factory;
		
	}

	@Override
	public ICardDeck getCardDeck() {
		return deck;
	}

	@Override
	public void setCardDeck(ICardDeck deck) {
		this.deck = deck;		
	}



}
