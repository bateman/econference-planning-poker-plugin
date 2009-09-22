package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;

public interface IPlanningPokerManager extends IEConferenceManager{
 
	/**
     * 
     * @return the planning poker service
     */
    IPlanningPokerService getService();
	
    /**
     * Notify that this client has selected a card
     * 
     * @param storyId the id of estimated User Story
     * 
     * @param card the selected card
     * 
     */
    void notifyCardSelected(String storyId, IPokerCard card );
   
    
    /**
     * <p>Notify that estimate session status has changed</p>
     * 
     * @param estimates the estimate session
     * @param status the {@link EstimateStatus}
     */
	void notifyEstimateSessionStatusChange(IEstimatesList estimates, EstimateStatus status);
	
	
	 /**
     * Remove a user story from backlog: the remote clients will be notified
     * 
	 * @param itemIndex 
     * 
     */
    void notifyRemoveBacklogItem( String itemIndex );

    /**
     * Notify that deck has changed
     * @param deck the new deck
     * 
     */
	void notifyCardDeckToRemote(CardDeck deck);

	/**
	 * <p>Notify that an estimate value has been assigned to a user story</p>
	 * @param storyId the id of the user story
	 * @param estimateValue the estimate value
	 */
	void notifyEstimateAssigned(String storyId, String estimateValue);
	

}
