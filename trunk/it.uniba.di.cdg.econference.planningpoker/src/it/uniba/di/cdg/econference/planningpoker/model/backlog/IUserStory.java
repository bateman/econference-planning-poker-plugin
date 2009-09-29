package it.uniba.di.cdg.econference.planningpoker.model.backlog;

public interface IUserStory {

	void setId(String id);
	
	String getId();
	
	void setStoryText(String storyText);
	
	String getStoryText();
	
	void setEstimate(String points);
	
	Object getEstimate();
	
	String getTextForMultiChatSubject();
	
	void setNotes(String notes);
	
	String getNotes();
}
