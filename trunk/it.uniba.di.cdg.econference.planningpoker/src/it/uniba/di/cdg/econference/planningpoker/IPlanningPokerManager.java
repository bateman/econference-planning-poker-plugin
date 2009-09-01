package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;

public interface IPlanningPokerManager extends IEConferenceManager{
 
    /**
     * Notify that this client has selected a card
     * 
     * @param card 
     * 
     */
    void notifyCardSelected(IPokerCard card );
   
    /**
     * 
     * @return the planning poker service
     */
    IPlanningPokerService getService();
    
    
    /**
     * 
     * Notify that voter list has changed
     * 
     */
    void notifyVoterListToRemote();
	

}
