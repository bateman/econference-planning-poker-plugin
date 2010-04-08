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

import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.jabber.IPacketExtension;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;



public class DefaultCardSelectionPacket implements IPacketExtension {

	/**
     * Namespace of the packet extension.
     */
	public static final String ELEMENT_NS = "http://cdg.di.uniba.it/xcore/jabber/card-selection";
	
	 /**
     * Elements in this packet.
     */
    public static final String ELEMENT_NAME = "card-selection";
	
	private static final String ELEMENT_CARD = "cardValue";
	
	private static final String ELEMENT_WHO = "who";
	
	private static final String ELEMENT_STORY = "storyId";

    /**
     * Filter this kind of packets.
     */
    public static final PacketFilter FILTER = new PacketFilter() {
        public boolean accept( Packet packet ) {
            return packet.getExtension( ELEMENT_NAME, ELEMENT_NS ) != null;
        }
    };
    
    
    private String who;
    private IPokerCard card;
    private String storyId;
    
    public DefaultCardSelectionPacket() {		
	}
    
	public DefaultCardSelectionPacket(String who, String storyId, IPokerCard card) {
		super();
		this.who = who;
		this.card = card;
		this.storyId = storyId;
	}

	public String getWho() {
		return who;
	}


	public void setWho(String who) {
		this.who = who;
	}

	public IPokerCard getCard() {
		return card;
	}

	public void setCard(IPokerCard card) {
		this.card = card;
	}

	public String getStoryId() {
		return storyId;
	}


	public void setStoryId(String storyId) {
		this.storyId = storyId;
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
		 String xml = String.format( 
	                "<%s xmlns=\"%s\">" +
	                "<%s>%s</%s>" +
	                "<%s>%s</%s>" +
	                "<%s>%s</%s>" +
	                "</%s>", ELEMENT_NAME, ELEMENT_NS,
	                ELEMENT_WHO, getWho(),ELEMENT_WHO,
	                ELEMENT_STORY, getStoryId(),ELEMENT_STORY,
	                ELEMENT_CARD, getCard().getStringValue(),ELEMENT_CARD, 
	                ELEMENT_NAME );
		 	System.out.println(xml);
	        return xml;
	}
	
	@Override
	public Object getProvider() {		
		return new Provider();
	}
	
	public static class Provider implements PacketExtensionProvider {
		
		public Provider() {	
		}
		
		public PacketExtension parseExtension(XmlPullParser xpp) throws Exception {
			String who ="";
			String storyId = "";
			DefaultPokerCard card = null;
			int eventType = xpp.getEventType();
			while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_NAME))) {
				if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_WHO)){
					xpp.next();					
					who = xpp.getText();											
				}else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_STORY)) {
					xpp.next();	
					storyId = xpp.getText();	
				}else if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_CARD)) {
					xpp.next();	
					card = new DefaultPokerCard(xpp.getText());
				}
				xpp.next();
				eventType = xpp.getEventType();
			}			
			return new DefaultCardSelectionPacket(who, storyId, card);
		}

	}



}
