package it.uniba.di.cdg.econference.planningpoker.utils;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jivesoftware.smack.util.StringUtils;
import org.w3c.dom.Document;

public class XMLUtils {
	
	
	public static String ELEMENT_BACKLOG = "backlog";
	public static String ELEMENT_STORY = "story";
	public static String ELEMENT_STORY_ID = "ID";
	public static String ELEMENT_STORY_CREATED_ON = "Created-on";
	public static String ELEMENT_STORY_LAST_UPDATE = "Last-update";
	public static String ELEMENT_STORY_TEXT = "StoryText";
	public static String ELEMENT_STORY_PRIORITY = "Priority";
	public static String ELEMENT_STORY_NOTES = "Notes";
	public static String ELEMENT_STORY_ESTIMATE = "Estimate";
	
	
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
    
    
    public static String convertDefaultBacklogToStandardXML(Backlog backlog){    	
    	
    	String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml+=String.format("<%s>", ELEMENT_BACKLOG);
		for(int i=0; i < backlog.getUserStories().length; i++){
			DefaultUserStory story = (DefaultUserStory) backlog.getUserStories()[i];
			xml+=String.format("<%s>", ELEMENT_STORY);			
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ID, StringUtils.escapeForXML(story.getId()), ELEMENT_STORY_ID );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_CREATED_ON, 
					StringUtils.escapeForXML(formatDate(story.getCreatedOn())), ELEMENT_STORY_CREATED_ON );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_LAST_UPDATE,
					StringUtils.escapeForXML(formatDate(story.getLastUpdate())), ELEMENT_STORY_LAST_UPDATE );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_TEXT, StringUtils.escapeForXML(story.getStoryText()), ELEMENT_STORY_TEXT );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_NOTES, StringUtils.escapeForXML(story.getNotes()), ELEMENT_STORY_NOTES);
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_PRIORITY, StringUtils.escapeForXML(story.getPriority().toString()), ELEMENT_STORY_PRIORITY);
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ESTIMATE, StringUtils.escapeForXML(story.getEstimate().toString()), ELEMENT_STORY_ESTIMATE);
			xml+=String.format("</%s>", ELEMENT_STORY);
		}
		xml += String.format("</%s>", ELEMENT_BACKLOG);
    	return xml;
    	
    }
    
    public static String formatDate(Date date){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	return formatter.format(date);
    }


    public static Date getDateFromString(String createdOn) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	try
    	{
    		Date date = formatter.parse(createdOn); 
    		return date;
    	} catch (ParseException e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }

}
