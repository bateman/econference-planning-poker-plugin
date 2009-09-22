package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
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
     * <p>Notify that the estimate session status changed.</p>
     *
     * @param estimates the {@link IEstimatesList} object
     * @param status the {@link EstimateStatus}
     *   
     */
	void notifyEstimateSessionStatusChange(IEstimatesList estimates, EstimateStatus status);


	/**
	 * <p>Notify that deck has changed</p>
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
    
//    
//    /**
//     * Notify the current voter list to remote clients: this method is 
//     * intended to be used if the local user is a moderator and wants to synchronize 
//     * the list of remote clients.
//     */
//    void notifyVoterListToRemote();

}
