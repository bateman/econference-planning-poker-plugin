package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModel;

public class PlanningPokerModel extends ConferenceModel implements IPlanningPokerModel {

	/**
	 * The value of the selected card
	 */
	private StoryPoints cardValue;
	
	/**
	 * The list of user stories in the backlog
	 */
	private IBacklog backlog;
	
	
	@Override
	public void setCardValue(StoryPoints cardValue) {
		this.cardValue = cardValue;		
	}

	@Override
	public void setBacklog(IBacklog backlog) {
		this.backlog = backlog;		
	}

	@Override
	public IBacklog getBacklog() {
		return backlog;
	}

	@Override
	public StoryPoints getCardValue() {
		return cardValue;		
	}



}
