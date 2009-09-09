package it.uniba.di.cdg.econference.planningpoker.model.internal;

import it.uniba.di.cdg.econference.planningpoker.model.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory.PRIORITY;
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

//			example ex = new example();
//			ex.dump(doc);
			
			Node backlog = (Node) xPath.evaluate( "//backlog", doc, XPathConstants.NODE );  
			
			NodeList stories = (NodeList) xPath.evaluate( "story", backlog, XPathConstants.NODESET );            

			if(stories.getLength()>0){				
				result = new Backlog();
				for (int i = 0; i < stories.getLength(); i++) {            	
					Node storyNode = stories.item( i );

					String id = xPath.evaluate( "ID", storyNode ); 

					if(id!=null && id!=""){   

						String priorityString = xPath.evaluate( "Priority", storyNode );
						
						PRIORITY priority = PRIORITY.valueOf(priorityString.toUpperCase());	   

						String storyText = xPath.evaluate( "StoryText", storyNode );

						String notes = xPath.evaluate( "Notes", storyNode );

						String estimate = xPath.evaluate( "Estimate", storyNode );
						if(estimate == null || estimate=="")
							estimate ="Unknown";

						result.addItem(new DefaultUserStory(id, storyText,priority,notes,estimate));
					}else{
						System.err.println("Parsed User Story without id");
					}
				}
			}
		} catch (Exception e) {
			throw new InvalidContextException(e);
		}
		return result;
	}

}
