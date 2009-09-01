package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;

public interface IPlanningPokerModel extends IConferenceModel {
		
	void setBacklog(Backlog backlog);
	
	void setModelFactory(IModelAbstractFactory factory);
	
	void setCardDeck(CardDeck deck);
	
	void setEstimates(IEstimates estimates);
	
	void setVoters(Voters voters);
	
	Backlog getBacklog();
	
	IModelAbstractFactory getFactory();	

	CardDeck getCardDeck();

	IEstimates getEstimates();

	Voters getVoters();
	
	
	
}
