package it.uniba.di.cdg.econference.planningpoker.model.backlog;

public interface IUserStory {

	void setEstimate(Object points);
	
	Object getEstimate();
	
	String getTextForMultiChatSubject();
	
	void setNotes(String notes);
	
	String getNotes();	
	
	void setId(String id);
	
	String getId();
	
}
