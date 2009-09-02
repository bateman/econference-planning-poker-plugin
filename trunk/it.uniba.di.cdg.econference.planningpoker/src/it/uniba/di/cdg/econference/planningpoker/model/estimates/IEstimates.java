package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

/**
 * Created following the IHandRaisingModel, this class
 * provides a model for handling the incoming estimates made by participants. 
 * 
 * Since an estimation could be repeated for the same User Story, the Estimates 
 * is identified by:
 * <ul>
 * <li>a User Story id that could be the position of the Story or a Story's field</li>
 * <li>an Object id that could be the date of the estimation, or the number
 * of re-estimation of the same story and</li>
 * </ul> 
 * 
 * @author Alex
 *
 */
public interface IEstimates{
	
	/**
	 * The state of the estimation
	 * <li>CREATED: means that a voter can add her estimate</li>
	 * <li>CLOSED: means that estimate was closed and nobady 
	 * can add estimation</li>
	 * <li>COMPLETED: when all voters made their estimation. 
	 * Only, in this case participant can see the estimate</li>
	 * 
	 */
	public enum EstimateStatus  {CREATED, CLOSED, COMPLETED};

	void addEstimate(String userId, IPokerCard card);

	IPokerCard getEstimate(String userId);

	int numberOfEstimates();

	void addListener( IEstimateListener l );

	void removeListener( IEstimateListener l );

	void removeUserEstimate(String userId);

	void setTotalVoters(int totalVoters);

	Object[] getAllEstimates();

	void setUserStoryId(String storyId);    

	String getUserStoryId();

	void setId(String date);

	String getId();
	
	EstimateStatus getStatus();
	
	void setStatus(EstimateStatus status);
	
	/**
	 * 
	 * Check if passed session estimate is the same session estimate
	 * 
	 * @param estimates 
	 * 
	 * @return true if estimates have same Story Id and Estimate Id
	 */
	boolean equals(IEstimates estimates);

	/**
	 * Perform clean up operation
	 */
	 void dispose();

}
