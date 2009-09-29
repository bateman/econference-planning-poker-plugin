package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

/**
 * <p>This class represents an estimate made by a participant that has selected a Poker Card</p>
 * @author Alessandro Brucoli
 *
 */
public interface IEstimate {
	
	/**
	 * Get the card selected as estimate
	 * @return the selected card deck
	 */
	IPokerCard getCard();
	
	
	/**
	 * Set the card selected as estimate
	 * @param card 
	 * 
	 */
	void setCard(IPokerCard card);
	
	/**
	 * Get the id of the participant that has made the estimation
	 * @return the participant id
	 */
	String getParticipantId();
	
	/**
	 * Set the id of the participant that has made the estimation
	 * @param participantId 
	 */
	void setParticipantId(String participantId);

}
