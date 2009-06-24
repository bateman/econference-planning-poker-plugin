package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel;

public interface IPlanningPokerModel extends IConferenceModel {

	public void setCardValue(StoryPoints point);
	
	public void setBacklog(IBacklog backlog);
	
	public StoryPoints getCardValue();
	
	public IBacklog getBacklog();
	
	
}
