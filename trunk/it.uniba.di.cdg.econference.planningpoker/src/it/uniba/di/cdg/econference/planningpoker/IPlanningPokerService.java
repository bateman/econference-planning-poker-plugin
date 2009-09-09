package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates.EstimateStatus;
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
    
    
    /**
     * Notify that participant has selected a card that represents 
     * his own estimate for related User Story
     * 
     * @param storyId the id of estimated User Story
     * @param card the selected Card
     */
    void notifyCardSelection( String storyId, IPokerCard card );


    /**
     * <p>Notify that the estimate session status changed.
     * See {@link EstimateStatus}</p>
     *
     * @param estimates the {@link IEstimates} object
     *   
     */
	void notifyEstimateSessionStatusChange(IEstimates estimates);
    
//    
//    /**
//     * Notify the current voter list to remote clients: this method is 
//     * intended to be used if the local user is a moderator and wants to synchronize 
//     * the list of remote clients.
//     */
//    void notifyVoterListToRemote();

}
