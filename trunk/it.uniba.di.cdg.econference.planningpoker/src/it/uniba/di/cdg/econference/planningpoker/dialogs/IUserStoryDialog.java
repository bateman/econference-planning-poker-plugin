package it.uniba.di.cdg.econference.planningpoker.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;

public interface IUserStoryDialog {

	public void show();
	
	//set a Story to edit
	public void setStory(IUserStory story);
	
	//return created or modified story 
	public IUserStory getStory();

}
