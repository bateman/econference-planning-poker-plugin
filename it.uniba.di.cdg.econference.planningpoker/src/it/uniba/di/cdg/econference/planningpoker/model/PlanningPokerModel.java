package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleFactory;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModel;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.econference.model.internal.ItemList;

public class PlanningPokerModel extends ConferenceModel implements IPlanningPokerModel {

	/**
	 * The value of the selected card
	 */
	private StoryPoints cardValue;
	
	/**
	 * The list of user stories in the backlog
	 */
	private IBacklog backlog;
	
	
	private IBacklogAbstractFactory factory;

	
	
	 public PlanningPokerModel() {
	        super();
	        /* TODO The abstract factory is initialized with the Simple 
			 * Factory which is the base factory
			 */		
	        this.factory = new SimpleFactory();
	        this.backlog = factory.createBacklog();
	    }
	
	@Override
	public void setCardValue(StoryPoints cardValue) {
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
	public StoryPoints getCardValue() {
		return cardValue;		
	}

	@Override
	public IBacklogAbstractFactory getBacklogFactory() {
		return factory;
	}

	@Override
	public void setBacklogFactory(IBacklogAbstractFactory factory) {
		this.factory = factory;
		
	}



}
