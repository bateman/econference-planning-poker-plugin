package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import java.util.Calendar;
import java.util.Date;



public class DefaultUserStory implements IUserStory {
	
//	public enum STATUS { UNKNOWN("Unknow"), OPEN("Open"), CLOSED("Closed"), 
//		LOW("Low");
//	
//	private String status;
//	
//	private STATUS(String name){
//		this.status = name;
//	}
//	
//	public String getName() {
//		return status;
//	} };
	
	
	
	private String id;
	private Date createdOn;
	private Date lastUpdate;
	private String storyText;
	private String status;
	private String 	notes;
	private Object points;
	
	public DefaultUserStory(String id, String storyText, String status, String notes,
			Object points) {
		this.id = id;
		this.createdOn = Calendar.getInstance().getTime();
		this.lastUpdate = createdOn;
		this.storyText = storyText;
		this.status = status;
		this.notes = notes;
		this.points = points;			
	}
	
	public DefaultUserStory(String id, Date createdOn, Date lastUpdate,
			String storyText, String status, String notes, Object points) {
		super();
		this.id = id;
		this.createdOn = createdOn;
		this.lastUpdate = lastUpdate;
		this.storyText = storyText;
		this.status = status;
		this.notes = notes;
		this.points = points;
	}

	@Override
	public String getStoryText() {
		return storyText;
	}

	@Override
	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}


	public String getStatus() {
		return status;
	}
	

	public void setStatus(String status) {
		this.status = status;
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
		return getId();
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
