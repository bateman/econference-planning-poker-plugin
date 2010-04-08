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
package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerContext;
import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.econference.model.InvalidContextException;
import it.uniba.di.cdg.xcore.multichat.service.Invitee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PPContextLoader {
    public static final String NAME_KEY = "name";
    public static final String TOPIC_KEY = "topic";
    public static final String BACKLOG_KEY = "backlog";
    public static final String DIRECTOR_KEY = "directory";
    public static final String SCRIBE_KEY = "scribe";
    public static final String MODERATOR_KEY = "moderator";
    public static final String VOTER_KEY = "scribe";

    protected PlanningPokerContext context;
    
    public PPContextLoader( PlanningPokerContext context ) {
        this.context = context;
    }
    
    public void load( InputStream is ) throws InvalidContextException {
    	try {
    		Document doc = XMLUtils.loadDocument( is );

    		XPathFactory factory = XPathFactory.newInstance();
    		XPath xPath = factory.newXPath();

    		// Get the fields, one by one

    		String service = xPath.evaluate( "/meeting/service", doc );
    		if ("planningpoker".equals(service)){

    			String name = xPath.evaluate( "/meeting/name", doc );
    			context.setName( name );  // XXX Either of these two ...

    			String topic = xPath.evaluate( "/meeting/topic", doc );
    			context.setTopic( topic );

    			// Platform
    			String platform = xPath.evaluate( "/meeting/platform/@type", doc );
    			if ("jabber".equals( platform )) { 
    				context.setBackendId( "it.uniba.di.cdg.jabber.jabberBackend" );
    				String room = xPath.evaluate( "/meeting/platform/jabber/room", doc );
    				context.setRoom( room );
    			}

    			// Support team
    			List<Invitee> participants = new ArrayList<Invitee>();

    			Node supportTeam = (Node) xPath.evaluate( "/meeting/supportTeam", doc, XPathConstants.NODE );
    			Node node = null;
    			Invitee p = null;

    			node = (Node) xPath.evaluate( "director", supportTeam, XPathConstants.NODE );
    			p = readParticipantFromNode( xPath, node, EConferenceContext.ROLE_DIRECTOR );
    			participants.add( p );
    			context.setDirector( p );

    			node = (Node) xPath.evaluate( "moderator", supportTeam, XPathConstants.NODE );
    			p = readParticipantFromNode( xPath, node, EConferenceContext.ROLE_MODERATOR );
    			context.setModerator( p );
    			participants.add( p );

    			node = (Node) xPath.evaluate( "scribe", supportTeam, XPathConstants.NODE );
    			p = readParticipantFromNode( xPath, node, EConferenceContext.ROLE_SCRIBE  );
    			context.setScribe( p );
    			participants.add( p );

    			NodeList voters = (NodeList) xPath.evaluate( "voter", supportTeam, XPathConstants.NODESET );
    			for (int i = 0; i < voters.getLength(); i++) {
    				Node n = voters.item( i );
    				p = readParticipantFromNode( xPath, n, PlanningPokerContext.ROLE_VOTER  );             
    				context.addVoter( p );
    				participants.add( p );
    			}


    			// Other experts
    			NodeList experts = (NodeList) xPath.evaluate( "/meeting/participants/*", doc, XPathConstants.NODESET );
    			for (int i = 0; i < experts.getLength(); i++) {
    				Node n = experts.item( i );

    				p = readParticipantFromNode( xPath, n, EConferenceContext.ROLE_PARTICIPANT );
    				participants.add( p );
    			}
    			// Add all the people to the conference
    			context.setInvitees( participants );

    			//Setting the model factory
    			String stringFactory = xPath.evaluate( "/meeting/factory/@type", doc );            
    			if (stringFactory!=null && stringFactory!="") { 
    				IModelAbstractFactory modelFactory = PlanningPokerPlugin.getFactoryFromString(stringFactory);
    				if(modelFactory!=null)
    					PlanningPokerPlugin.getDefault().getHelper().setModelFactory(modelFactory);
    			}

    			IBacklogContextLoader backlogLoader = PlanningPokerPlugin
    			.getDefault().getHelper().getBacklogContextLoader();	
    			
    			Backlog backlog = backlogLoader.load(doc);
    			
    			if(backlog!=null)
    				context.setBacklog(backlog);
            }else{
            	throw new InvalidContextException("Wrong Planning Poker file. Invalid service name: "+service);
            }

        } catch (Exception e) {
            throw new InvalidContextException( e );
        } 
    }

    public void load( String fileName ) throws FileNotFoundException, InvalidContextException {
        load( new FileInputStream( fileName ) );
    }
    
    /**
     * Read a participant's info, from a specific node. 
     * 
     * @param xPath
     * @param participantNode
     * @param role 
     * @return the participant
     * @throws Exception if some XPath-related error occur or the required id is <code>null</code>.
     */
    private Invitee readParticipantFromNode( XPath xPath, Node participantNode, String role ) throws Exception {
        String id = xPath.evaluate( "id", participantNode );
//        String passwd = xPath.evaluate( "passwd", participantNode );
        String fullName = xPath.evaluate( "fullname", participantNode ); 
        String email = xPath.evaluate( "email", participantNode );
        String organization = xPath.evaluate( "organization", participantNode );

        if (id == null || id.length() == 0)
            throw new InvalidContextException( "Participant id must be not empty!" );

        Invitee p = new Invitee( id, fullName, email, organization, role );
//        IParticipant p = new Participant( conference, id, passwd, fullName, email, organization, role );
        return p;
    }

}
