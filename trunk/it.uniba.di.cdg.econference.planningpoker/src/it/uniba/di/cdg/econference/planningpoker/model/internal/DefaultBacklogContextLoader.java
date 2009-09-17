package it.uniba.di.cdg.econference.planningpoker.model.internal;

import it.uniba.di.cdg.econference.planningpoker.model.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory.PRIORITY;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.econference.model.InvalidContextException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultBacklogContextLoader implements IBacklogContextLoader {

	@Override
	public Backlog load(Document doc ) throws InvalidContextException{		
		Backlog result = null;
		try {			

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();            

			Node backlog = (Node) xPath.evaluate( "//backlog", doc, XPathConstants.NODE );  

			if(backlog!=null){

				NodeList stories = (NodeList) xPath.evaluate( "story", backlog, XPathConstants.NODESET );            

				if(stories.getLength()>0){				
					result = new Backlog();
					for (int i = 0; i < stories.getLength(); i++) {            	
						Node storyNode = stories.item( i );

						String id = xPath.evaluate( XMLUtils.ELEMENT_STORY_ID, storyNode ); 

						if(id!=null && id!=""){   

							String createdOn = xPath.evaluate( XMLUtils.ELEMENT_STORY_CREATED_ON, storyNode );

							String lastUpdate = xPath.evaluate( XMLUtils.ELEMENT_STORY_LAST_UPDATE, storyNode );

							String priorityString = xPath.evaluate( XMLUtils.ELEMENT_STORY_PRIORITY, storyNode );												

							PRIORITY priority = PRIORITY.valueOf(priorityString.toUpperCase());	   

							String storyText = xPath.evaluate( XMLUtils.ELEMENT_STORY_TEXT, storyNode );

							String notes = xPath.evaluate( XMLUtils.ELEMENT_STORY_NOTES, storyNode );

							String estimate = xPath.evaluate( XMLUtils.ELEMENT_STORY_ESTIMATE, storyNode );
							if(estimate == null || estimate=="")
								estimate ="?";

							result.addItem(new DefaultUserStory(id, XMLUtils.getDateFromString(createdOn), 
									XMLUtils.getDateFromString(lastUpdate), 
									storyText,priority,notes,estimate));
						}else{
							System.err.println("Parsed User Story without id");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InvalidContextException(e);
		}
		return result;
	}

}
