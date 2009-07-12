package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;

public interface IPlanningPokerModel extends IConferenceModel {

	void setCardValue(StoryPoints point);
	
	void setBacklog(IBacklog backlog);
	
	void setBacklogFactory(IBacklogAbstractFactory factory);
	
	StoryPoints getCardValue();
	
	IBacklog getBacklog();
	
	IBacklogAbstractFactory getBacklogFactory();
	
	
	
}
