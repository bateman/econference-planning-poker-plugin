package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import java.util.Calendar;
import java.util.Date;



public class DefaultUserStory implements IUserStory {
	
	public enum PRIORITY { UNKNOWN("Unknow"), HIGH("High"), NORMAL("Normal"), 
		LOW("Low");
	
	private String priority;
	
	private PRIORITY(String name){
		this.priority = name;
	}
	
	public String getName() {
		return priority;
	} };
	
	
	
	private String id;
	private Date createdOn;
	private Date lastUpdate;
	private String storyText;
	private PRIORITY priority;
	private String 	notes;
	private Object points;
	
	public DefaultUserStory(String id, String storyText, PRIORITY priority, String notes,
			Object points) {
		this.id = id;
		this.createdOn = Calendar.getInstance().getTime();
		this.lastUpdate = createdOn;
		this.storyText = storyText;
		this.priority = priority;
		this.notes = notes;
		this.points = points;			
	}
	
	public DefaultUserStory(String id, Date createdOn, Date lastUpdate,
			String storyText, PRIORITY priority, String notes, Object points) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.lastUpdate = lastUpdate;
		this.storyText = storyText;
		this.priority = priority;
		this.notes = notes;
		this.points = points;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	
	public PRIORITY getPriority() {
		return priority;
	}
	
	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}
	
	@Override
	public void setEstimate(Object points) {
		this.points = points;		
	}
	
	@Override
	public Object getEstimate() {
		return points;
	}
	
	@Override
	public String getTextForMultiChatSubject() {
		return getStoryText();
	}
	
	@Override
	public String getNotes() {
		return notes;
	}
	
	@Override
	public void setNotes(String notes) {
		this.notes = notes;		
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;		
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	
	

}
