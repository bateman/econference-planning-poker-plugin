package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.model.StoryPoints;


public class SimpleUserStory implements IUserStory {
	
	public enum PRIORITY { UNKNOWN("Unknow"), HIGH("High"), MEDIUM("Medium"), LOW("Low"); 
		private String priority;
			
		private PRIORITY(String priority){
			this.priority = priority;
		}
		
		public String getName(){
			return priority;
		}	
	};

	private String name;
	private PRIORITY priority;
	private String 	description;
	private StoryPoints points;
	
	public SimpleUserStory(String name, PRIORITY priority, String description,
			StoryPoints points) {
		this.name = name;
		this.priority = priority;
		this.description = description;
		this.points = points;		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PRIORITY getPriority() {
		return priority;
	}
	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public void setEstimate(StoryPoints points) {
		this.points = points;		
	}
	@Override
	public StoryPoints getEstimate() {
		return points;
	}

}
