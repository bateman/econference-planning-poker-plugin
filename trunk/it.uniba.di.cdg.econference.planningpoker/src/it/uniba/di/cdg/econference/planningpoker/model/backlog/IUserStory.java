package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.model.StoryPoints;

public interface IUserStory {

	public void setEstimate(StoryPoints points);
	
	public StoryPoints getEstimate();
}
