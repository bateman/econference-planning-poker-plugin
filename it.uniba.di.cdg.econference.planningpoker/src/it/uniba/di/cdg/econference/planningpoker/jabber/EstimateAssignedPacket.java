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
package it.uniba.di.cdg.econference.planningpoker.jabber;

import static it.uniba.di.cdg.smackproviders.SmackCommons.CDG_NAMESPACE;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import it.uniba.di.cdg.jabber.IPacketExtension;


public class EstimateAssignedPacket implements IPacketExtension {

	
	public static String ELEMENT_NS = CDG_NAMESPACE;
	
	public static String ELEMENT_NAME = "estimate-assigned";
	
	public static String ELEMENT_STORY_ID = "storyId";
	public static String ELEMENT_ESTIMATE = "estimate";
	
	
	private String storyId;
	private String estimate;
	
	  /**
     * Filter this kind of packets.
     */
    public static final PacketFilter FILTER = new PacketFilter() {
        public boolean accept( Packet packet ) {
            return packet.getExtension( ELEMENT_NAME, ELEMENT_NS ) != null;
        }
    };
    
    
	public EstimateAssignedPacket() {
		
	}

	public EstimateAssignedPacket(String storyId, String estimate) {
		super();
		this.storyId = storyId;
		this.estimate = estimate;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
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
		String xml = String.format("<%s xmlns=\"%s\">" +
				"<%s>%s</%s>" +
				"<%s>%s</%s>" +
				"</%s>", ELEMENT_NAME, ELEMENT_NS, 
				ELEMENT_STORY_ID, storyId, ELEMENT_STORY_ID,
				ELEMENT_ESTIMATE, estimate , ELEMENT_ESTIMATE, 
				ELEMENT_NAME);
		System.out.println(xml);
		return xml;
	}

	@Override
	public Object getProvider() {
		return EstimateAssignedPacket.class;
	}

}
