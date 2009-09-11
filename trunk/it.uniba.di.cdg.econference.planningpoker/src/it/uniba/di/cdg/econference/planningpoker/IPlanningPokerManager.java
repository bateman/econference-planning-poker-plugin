package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates;
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
     * Notify that estimate session status has changed
     * 
     * @param estimates the estimate session
     */
	void notifyEstimateSessionStatusChange(IEstimates estimates);
	
	
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
	

}
