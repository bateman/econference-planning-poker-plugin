package it.uniba.di.cdg.econference.planningpoker;

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
     * @param card 
     * 
     */
    void notifyCardSelected(IPokerCard card );
   
    
    /**
     * Notify that estimate session status has changed
     * 
     * @param estimates the estimate session
     */
	void notifyEstimateSessionStatusChange(IEstimates estimates);
	

}
