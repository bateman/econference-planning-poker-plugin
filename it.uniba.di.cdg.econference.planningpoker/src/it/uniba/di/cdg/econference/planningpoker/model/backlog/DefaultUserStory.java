/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
