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
import it.uniba.di.cdg.xcore.econference.model.ConferenceContextLoader;
import it.uniba.di.cdg.xcore.econference.model.InvalidContextException;
import it.uniba.di.cdg.xcore.econference.model.definition.IServiceContextLoader;
import it.uniba.di.cdg.xcore.m2m.service.Invitee;

import java.io.InputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class PPContextLoader extends ConferenceContextLoader implements IServiceContextLoader {
	protected static final String BACKLOG_KEY = "backlog";
	protected static final String SERVICE_KEY = "service";
	
	protected static final String PLANNING_POKER_SERVICE = "planningpoker";
	
	public PPContextLoader() {
		super();
	}

	public PPContextLoader(PlanningPokerContext context) {
		super(context);
	}

	public void load(InputStream is) throws InvalidContextException {
		try {
			super.load(is);
			
			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			
			// Get the service
			String service = xPath.evaluate("/meeting/service",
					doc);
			if (!PLANNING_POKER_SERVICE.equals(service))
				throw new InvalidContextException("Wrong Planning Poker service.\n"
						+ "Expected: " + PLANNING_POKER_SERVICE + "\n"
						+ "Found: " + service);
			
			IBacklogContextLoader backlogLoader = PlanningPokerPlugin
					.getDefault().getHelper().getBacklogContextLoader();

			Backlog backlog = backlogLoader.load(doc);

			if (backlog != null)
				((PlanningPokerContext) context).setBacklog(backlog);
			// we should have voters as participant - moderator at the beginning	
			for(Invitee i: context.getInvitees()) {
				// TODO we should add role constants somewhere...
				if(!i.getRole().equals("moderator"))
					((PlanningPokerContext) context).addVoter(i);
			}
		} catch (Exception e) {
			throw new InvalidContextException(e);
		}
	}

}
