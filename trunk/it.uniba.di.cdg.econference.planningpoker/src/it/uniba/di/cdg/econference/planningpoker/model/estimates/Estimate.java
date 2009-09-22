package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

/**
 * <p>This class represents an estimate made by a participant that has selected a Poker Card</p>
 * @author Alessandro Brucoli
 *
 */
public class Estimate {
	
	private String participantId;
	private IPokerCard card;

	public Estimate(String participantId, IPokerCard card) {
		super();
		this.participantId = participantId;
		this.card = card;
	}
	
	/**
	 * Get the id of the participant that has made the estimation
	 * @return the participant id
	 */
	public String getParticipantId() {
		return participantId;
	}
	
	
	/**
	 * Get the card selected as estimate
	 * @return the selected card deck
	 */
	public IPokerCard getCard() {
		return card;
	}

	
	

}
