package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

public interface IEstimateListener {
	
	void estimateAdded(String userId, IPokerCard card);
	
	void estimateRemoved(String userId, IPokerCard card);
		
	/**
	 * 
	 * Fired when all voters made their estimation.
	 * Only, in this case participant can see the estimates
	 * 
	 */
	void estimatesCompleted();
	
	
	/**
	 * Fired when a new Object Estimates is created. This happened
	 * when a new estimation about a User Story has to be done.
	 * 
	 * @param storyId the id of the story associated to this estimate list 
	 * 
	 * @param id the id of this estimate list
	 */
	void estimateListCreated(Object storyId, Object id);

}
