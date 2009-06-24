package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.multichat.IMultiChatManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;

public interface IPlanningPokerManager extends IEConferenceManager{
 
    /**
     * Notify that this client has selected a card
     * 
     * @param moderator
     * @param question
     */
    void notifyCardSelected( IParticipant moderator, String cardValue );
   
	

}
