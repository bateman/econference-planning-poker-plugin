package it.uniba.di.cdg.econference.planningpoker.model.backlog;

public interface IUserStory {

	public void setEstimate(Object points);
	
	public Object getEstimate();
	
	public String getTextForMultiChatSubject();
}
