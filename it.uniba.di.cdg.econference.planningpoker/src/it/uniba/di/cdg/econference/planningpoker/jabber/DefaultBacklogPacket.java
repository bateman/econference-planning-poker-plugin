package it.uniba.di.cdg.econference.planningpoker.jabber;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.utils.DateUtils;
import it.uniba.di.cdg.jabber.IPacketExtension;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smack.util.StringUtils;
import org.xmlpull.v1.XmlPullParser;

public class DefaultBacklogPacket implements IPacketExtension{

	 /**
     * Namespace of the packet extension.
     */
	public static final String ELEMENT_NS = "http://cdg.di.uniba.it/xcore/jabber/backlog";
	
	
	/**
     * Element name of the packet extension.
     */
	public static final String ELEMENT_NAME = "backlog";
	
	public static final String ELEMENT_STORY = "story";
	
	public static final String ELEMENT_STORY_ID = "id";
	
	public static final String ELEMENT_STORY_TEXT = "story-text";
	
	public static final String ELEMENT_STORY_NOTES = "notes";
	
	public static final String ELEMENT_STORY_STATUS = "status";
	
	public static final String ELEMENT_STORY_ESTIMATE = "estimate";
	
	public static final String ELEMENT_CURRENT_STORY = "current-story";
	
	public static final String ELEMENT_STORY_CREATED_ON = "created-on";
	
	public static final String ELEMENT_STORY_LAST_UPDATE = "last-update";
	
	
	
	 /**
     * Filter this kind of packets.
     */
    public static final PacketFilter FILTER = new PacketFilter() {
        public boolean accept( Packet packet ) {
            return packet.getExtension(ELEMENT_NAME, ELEMENT_NS ) != null;
        }
    };
    
    private Backlog backlog;
	
    public DefaultBacklogPacket() {
		this(new Backlog());
	}
    
    public DefaultBacklogPacket(Backlog backlog) {
		this.backlog = (Backlog) backlog;
	}
	
	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String getElementName() {
		return ELEMENT_NAME;
	}

	@Override
	public String getNamespace() {
		return ELEMENT_NS;
	}

	@Override
	public String toXML() {
		String xml = String.format("<%s xmlns=\"%s\">" , ELEMENT_NAME, ELEMENT_NS);
		xml+=String.format("<%s>%s</%s>", ELEMENT_CURRENT_STORY, backlog.getCurrentItemIndex(), ELEMENT_CURRENT_STORY );
		for(int i=0; i < backlog.getUserStories().length; i++){
			DefaultUserStory story = (DefaultUserStory) backlog.getUserStories()[i];
			xml+=String.format("<%s>", ELEMENT_STORY);			
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ID, StringUtils.escapeForXML(story.getId()), ELEMENT_STORY_ID );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_CREATED_ON, 
					StringUtils.escapeForXML(DateUtils.formatDate(story.getCreatedOn())), ELEMENT_STORY_CREATED_ON );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_LAST_UPDATE,
					StringUtils.escapeForXML(DateUtils.formatDate(story.getLastUpdate())), ELEMENT_STORY_LAST_UPDATE );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_TEXT, StringUtils.escapeForXML(story.getStoryText()), ELEMENT_STORY_TEXT );
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_NOTES, StringUtils.escapeForXML(story.getNotes()), ELEMENT_STORY_NOTES);
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_STATUS, StringUtils.escapeForXML(story.getStatus().toString()), ELEMENT_STORY_STATUS);
			xml+=String.format("<%s>%s</%s>", ELEMENT_STORY_ESTIMATE, StringUtils.escapeForXML(story.getEstimate().toString()), ELEMENT_STORY_ESTIMATE);
			xml+=String.format("</%s>", ELEMENT_STORY);
		}
		xml += String.format("</%s>", ELEMENT_NAME);
		System.out.println(xml);
		return xml;
	}

	@Override
	public Object getProvider() {	
		Provider provider = new Provider();
		return provider;
	}
	
	public static class Provider implements PacketExtensionProvider {
		
		public Provider() {
		}
		
		public PacketExtension parseExtension(XmlPullParser xpp)
		throws Exception {
			Backlog backlog = new Backlog();
			int index = backlog.getCurrentItemIndex();
			DefaultUserStory currStory;
			int eventType = xpp.getEventType();
			while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_NAME))) {
				if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_CURRENT_STORY)){
					xpp.next();
					try{
						index = Integer.parseInt(xpp.getText());						
					}catch(NumberFormatException ex){
						// no selected item in the backlog
					}					
				}else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_STORY)) {
					//We are in the story tag -> <story>....</story>  
					String storyText = "";
					String id ="";
					String createdOn = "";
					String lastUpdate = "";
					String notes="";
					String status = "";
					String estimate = "";
					boolean noUserStory = true;
					while(!(eventType == XmlPullParser.END_TAG&& xpp.getName().equalsIgnoreCase(ELEMENT_STORY))){
						//System.out.println("Current Tag Name: " +xpp.getName());
						noUserStory = false;
						if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_STORY_TEXT)){	            	 
							xpp.next();
							storyText = xpp.getText();
							//System.out.println("Tag Name "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_STORY_ID)){	            	 
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_ID)))
							id = xpp.getText();
							//System.out.println("Tag Description "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_STORY_CREATED_ON)){	            	 
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_CREATED_ON)))
							createdOn = xpp.getText();
							//System.out.println("Tag Description "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_STORY_LAST_UPDATE)){	            	 
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_LAST_UPDATE)))
							lastUpdate = xpp.getText();
							//System.out.println("Tag Description "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_STORY_NOTES)){	            	 
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_NOTES)))
							notes = xpp.getText();
							//System.out.println("Tag Description "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
								xpp.getName().equalsIgnoreCase(ELEMENT_STORY_STATUS)){							
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_STATUS)))							
							status = xpp.getText();
							//System.out.println("Tag Status "+xpp.getText());
						}else if(eventType == XmlPullParser.START_TAG && 
								xpp.getName().equalsIgnoreCase(ELEMENT_STORY_ESTIMATE)){	            	 
							if(! (xpp.next() == XmlPullParser.END_TAG && 
									xpp.getName().equalsIgnoreCase(ELEMENT_STORY_ESTIMATE)))
							estimate = xpp.getText();
							//System.out.println("Tag Estimate "+xpp.getText());
						}
						xpp.next();
						eventType = xpp.getEventType();
					} 
					if(!noUserStory){
						//System.out.println("Creating User Story...");
						currStory = new DefaultUserStory(id, DateUtils.getDateFromString(createdOn),
								DateUtils.getDateFromString(lastUpdate), storyText, 
								status,notes,estimate);						
						backlog.addUserStory(currStory);						
					}
				}
				xpp.next();
				eventType = xpp.getEventType();
			}
			backlog.setCurrentItemIndex(index);			
			return new DefaultBacklogPacket(backlog);
		}

	}
}
