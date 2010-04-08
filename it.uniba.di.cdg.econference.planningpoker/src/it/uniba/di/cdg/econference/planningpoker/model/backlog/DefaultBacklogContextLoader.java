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

import it.uniba.di.cdg.econference.planningpoker.utils.DateUtils;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.econference.model.InvalidContextException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DefaultBacklogContextLoader implements IBacklogContextLoader {

	@Override
	public Backlog load(Document doc ) throws InvalidContextException{		
		Backlog result = null;
		try {			

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();            

			//Node backlog = (Node) xPath.evaluate( "//"+XMLUtils.ELEMENT_BACKLOG, doc, XPathConstants.NODE );  
			NodeList stories = (NodeList) xPath.evaluate(  "//"+XMLUtils.ELEMENT_STORY, doc, XPathConstants.NODESET );            

			if(stories.getLength()>0){				
				result = new Backlog();
				for (int i = 0; i < stories.getLength(); i++) {            	
					Node storyNode = stories.item( i );
					NamedNodeMap attributes = storyNode.getAttributes();					
					//String id = xPath.evaluate( "[@"+XMLUtils.ELEMENT_STORY_ID+"]", storyNode ); 
					String id = attributes.getNamedItem(XMLUtils.ELEMENT_STORY_ID).getTextContent();

					if(id!=null && id!=""){   

						String milestoneId = attributes.getNamedItem(XMLUtils.ELEMENT_STORY_ITERATION_ID).getTextContent();
						String milestoneName = "";
						String milestoneCreationDate = "";
						
						if (!milestoneId.equals("2")){

							milestoneName = (String) xPath.evaluate("//"+XMLUtils.ELEMENT_ITERATION+
									"[@"+XMLUtils.ELEMENT_ITERATION_ID+"='"+milestoneId+"']/@"+
									XMLUtils.ELEMENT_ITERATION_NAME, doc);
							
							milestoneCreationDate = (String) xPath.evaluate("//"+XMLUtils.ELEMENT_ITERATION+
									"[@"+XMLUtils.ELEMENT_ITERATION_ID+"='"+milestoneId+"']/@"+
									XMLUtils.ELEMENT_ITERATION_START_DATE, doc);
														
						}
															

						//String milestoneDescription = attributes.getNamedItem( XMLUtils.ELEMENT_ITERATION_NAME).getTextContent();										

						//STATUS status = STATUS.valueOf(statusString.toUpperCase());	   

						String storyText = attributes.getNamedItem(XMLUtils.ELEMENT_STORY_TEXT).getTextContent();

						String notes = attributes.getNamedItem( XMLUtils.ELEMENT_STORY_NOTES).getTextContent();

						String estimate = attributes.getNamedItem( XMLUtils.ELEMENT_STORY_ESTIMATE).getTextContent();
						if(estimate == null || estimate=="")
							estimate ="?";

						result.addUserStory(new DefaultUserStory(id,milestoneId, milestoneName,
								DateUtils.getDateFromString(milestoneCreationDate),storyText,notes,estimate));
					}else{
						System.err.println("Parsed User Story without id");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidContextException(e);
		}
		return result;
	}

}
