package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.IEConferenceService;
import it.uniba.di.cdg.xcore.multichat.IMultiChatManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;

public interface IPlanningPokerManager extends IEConferenceManager{
 
    /**
     * Notify that this client has selected a card
     * 
     * @param cardValue
     */
    void notifyCardSelected(String cardValue );
   
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
