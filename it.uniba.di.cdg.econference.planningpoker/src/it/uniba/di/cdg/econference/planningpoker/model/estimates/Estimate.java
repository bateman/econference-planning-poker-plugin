package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;


public class Estimate implements IEstimate{
	
	private String participantId;
	private IPokerCard card;

	
	public Estimate(String participantId, IPokerCard card) {
		this.participantId = participantId;
		this.card = card;
	}
	

	public String getParticipantId() {
		return participantId;
	}
	
	
	
	public IPokerCard getCard() {
		return card;
	}


	@Override
	public void setCard(IPokerCard card) {
		this.card = card;
		
	}


	@Override
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
		
	}

	
	

}
