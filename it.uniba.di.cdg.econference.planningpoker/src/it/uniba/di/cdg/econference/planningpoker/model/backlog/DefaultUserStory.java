package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import java.util.Date;



public class DefaultUserStory implements IUserStory {
	
	
	private String id;
	private String storyText;	
	private String 	notes;
	private String points;
	private String milestoneId;
	private String milestoneName;
	private Date milestoneCreationDate;

	

	
	public DefaultUserStory(String id, String milestoneId, String milestoneName,
			Date milestoneCreationDate, String storyText, String notes, String points) {
		super();
		this.id = id;
		this.milestoneId = milestoneId;
		this.milestoneCreationDate = milestoneCreationDate;
		this.storyText = storyText;
		this.milestoneName = milestoneName;
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
	
	@Override
	public void setEstimate(String points) {
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
	
	public String getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(String milestoneId) {
		this.milestoneId = milestoneId;
	}
	
	
	public String getMilestoneName() {
		return milestoneName;
	}

	public void setMilestoneName(String milestoneDescription) {
		this.milestoneName = milestoneDescription;
	}

	public Date getMilestoneCreationDate() {
		return milestoneCreationDate;
	}

	public void setMilestoneCreationDate(Date milestoneCreationDate) {
		this.milestoneCreationDate = milestoneCreationDate;
	}




	
	

}
