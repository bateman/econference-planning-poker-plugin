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
package it.uniba.di.cdg.econference.planningpoker.utils;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;

import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

public class XMLUtils {
	
	
	public static String ELEMENT_BACKLOG = "Backlog";
	public static String ELEMENT_STORY = "StoryCard";
	public static String ELEMENT_STORY_ID = "ID";
	public static String ELEMENT_STORY_TEXT = "Name";
	public static String ELEMENT_STORY_NOTES = "Description";
	public static String ELEMENT_STORY_ESTIMATE = "MostLikely";
	public static String ELEMENT_STORY_ITERATION_ID = "Parent";
	
	
	public static String ELEMENT_ITERATION = "Iteration";
	public static String ELEMENT_ITERATION_ID = "ID";
	public static String ELEMENT_ITERATION_NAME = "Name";
	public static String ELEMENT_ITERATION_START_DATE = "StartDate";
	
	
    /**
     * XML DOM helper.
     * 
     * @param is
     * @return the XML DOM document object
     * @throws Exception
     */
    public static Document loadDocument( InputStream is ) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse( is );
        return doc;
    }
    
    
    /**
     * Convert the Backlog object in the Agile Planner XML Standard
     * 
     * @param backlog
     * @return the serialized xml string
     * @throws TransformerException
     * @throws IllegalArgumentException 
     */
    public static String convertDefaultBacklogToStandardXML(Backlog backlog) throws IllegalArgumentException{    	
    	if(backlog!=null && backlog.size()>0){
	    	LinkedList<String> id = new LinkedList<String>();
	    	LinkedList<String> storyText = new LinkedList<String>();
	    	LinkedList<String> milestoneId = new LinkedList<String>();
	    	LinkedList<String> notes = new LinkedList<String>();
	    	LinkedList<String> estimates = new LinkedList<String>();
	    	LinkedList<String> milestoneName = new LinkedList<String>();
	    	LinkedList<String> milestoneCreationDates = new LinkedList<String>();
	    	for (IUserStory story : backlog.getUserStories()) {	  
	    		if(story instanceof DefaultUserStory){
	    			DefaultUserStory defStory = (DefaultUserStory)story;
					id.add(defStory.getId());
					storyText.add(defStory.getStoryText());
					notes.add(defStory.getNotes());
					milestoneId.add(defStory.getMilestoneId());
					estimates.add(defStory.getEstimate().toString());
					milestoneCreationDates.add(DateUtils.formatDate(defStory.getMilestoneCreationDate()));
					milestoneName.add(defStory.getMilestoneName());
	    		}else{
	    			throw new IllegalArgumentException("Wrong User Story type: only DefaultUserStory type " +
	    					"can be printed with this transformer.");
	    		}
			}
	    	
	    	ConvertToAgilePlannerXMLFormat standardXML = new ConvertToAgilePlannerXMLFormat(storyText,notes,id,estimates,milestoneId, null, 
	    			milestoneCreationDates, milestoneName);
	    	return standardXML.getXMLString();
    	}else{
    		throw new IllegalArgumentException("Backlog is empty");
    	}
    	
//    	String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//		xml+=String.format("<%s>", ELEMENT_BACKLOG);
//		for(int i=0; i < backlog.getUserStories().length; i++){
//			DefaultUserStory story = (DefaultUserStory) backlog.getUserStories()[i];
//			xml+=String.format("<%s>", ELEMENT_STORY);			
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ID, story.getId(), ELEMENT_STORY_ID );
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_CREATED_ON, 
//					formatDate(story.getCreatedOn()), ELEMENT_STORY_CREATED_ON );
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_LAST_UPDATE,
//					formatDate(story.getLastUpdate()), ELEMENT_STORY_LAST_UPDATE );
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_TEXT, story.getStoryText(), ELEMENT_STORY_TEXT );
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_NOTES, story.getNotes(), ELEMENT_STORY_NOTES);
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_STATUS, story.getStatus(), ELEMENT_STORY_PRIORITY);
//			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ESTIMATE, story.getEstimate().toString(), ELEMENT_STORY_ESTIMATE);
//			xml+=String.format("</%s>", ELEMENT_STORY);
//		}
//		xml += String.format("</%s>", ELEMENT_BACKLOG);
//    	return xml;
    	
    }
   

}
