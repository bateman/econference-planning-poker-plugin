package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.utils.DateUtils;
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

							String status = xPath.evaluate( XMLUtils.ELEMENT_STORY_STATUS, storyNode );												

							//STATUS status = STATUS.valueOf(statusString.toUpperCase());	   

							String storyText = xPath.evaluate( XMLUtils.ELEMENT_STORY_TEXT, storyNode );

							String notes = xPath.evaluate( XMLUtils.ELEMENT_STORY_NOTES, storyNode );

							String estimate = xPath.evaluate( XMLUtils.ELEMENT_STORY_ESTIMATE, storyNode );
							if(estimate == null || estimate=="")
								estimate ="?";

							result.addUserStory(new DefaultUserStory(id, DateUtils.getDateFromString(createdOn), 
									DateUtils.getDateFromString(lastUpdate), 
									storyText,status,notes,estimate));
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
