package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;

public interface IEstimateListener {
	
	/**
	 * An estimate was added to the list
	 * 
	 * @param estimate
	 */
	void estimateAdded(Estimate estimate);
	
	
	/**
	 * An estimate was removed from the list
	 * 
	 * @param estimate
	 */
	void estimateRemoved(Estimate estimate);
	
	
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
