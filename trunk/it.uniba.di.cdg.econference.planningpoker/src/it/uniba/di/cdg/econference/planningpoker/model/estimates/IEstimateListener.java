package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates.EstimateStatus;

public interface IEstimateListener {
	
	void estimateAdded(String userId, IPokerCard card);
	
	void estimateRemoved(String userId, IPokerCard card);
	
	
	/**
	 * <p>The estimate was created or closed<p>
	 * 
	 * @param status see {@link EstimateStatus}
	 * 
	 * @see EstimateStatus EstimateStatus
	 * 
	 * @param storyId the unique identifier for the current 
	 * story to estimate
	 * 
	 * @param estimateId the unique identifier for the current
	 * estimation( Remeber that there could be many estimations
	 * for a single User Story)
	 * 
	 */
	void estimateStatusChanged(EstimateStatus status, 
			String storyId, String estimateId);

}
