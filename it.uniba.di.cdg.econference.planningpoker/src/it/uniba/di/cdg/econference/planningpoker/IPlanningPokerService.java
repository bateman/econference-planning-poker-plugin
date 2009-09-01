package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.econference.IEConferenceService;
import it.uniba.di.cdg.xcore.network.services.Capability;
import it.uniba.di.cdg.xcore.network.services.ICapability;

public interface IPlanningPokerService extends IEConferenceService {
    /**
     * Constant indicating that the backend supports multi-peers chat.
     */
    public static final ICapability PLANNINGPOKER_SERVICE = new Capability( "planning-poker" );
  
    
    /**
     * Covariance enforcement.
     * 
     * @return the planning poker model
     */
    IPlanningPokerModel getModel();
    
    
    void notifyCardSelection( IPokerCard card );
    
    /**
     * Notify the current voter list to remote clients: this method is 
     * intended to be used if the local user is a moderator and wants to synchronize 
     * the list of remote clients.
     */
    void notifyVoterListToRemote();

}
