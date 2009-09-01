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
	
	void addEstimate(String userId, IPokerCard card);
	
	IPokerCard getEstimate(String userId);
	
	int numberOfEstimates();
	
	void addListener( IEstimateListener l );
	    
    void removeListener( IEstimateListener l );
    
    void setUserStoryId(Object storyId);    
    
    Object getUserStoryId();
        
    void setId(Object date);
    
    Object getId();
    
    void removeUserEstimate(String userId);
    
    void setTotalVoters(int totalVoters);
    
    Object[] getAllEstimates();

    /**
     * Perform clean up operation
     */
	void dispose();

}
