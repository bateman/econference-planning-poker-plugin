package it.uniba.di.cdg.econference.planningpoker.jabber;

import org.jivesoftware.smack.filter.PacketFilter;

import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smack.util.StringUtils;
import org.xmlpull.v1.XmlPullParser;

import it.uniba.di.cdg.econference.planningpoker.jabber.DefaultBacklogPacket.Provider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.jabber.IPacketExtension;
import it.uniba.di.cdg.jabber.PacketExtensionAdapter;


public class DefaultDeckPacket implements IPacketExtension{

	 /**
     * Namespace of the packet extension.
     */
	public static final String ELEMENT_NS = "http://cdg.di.uniba.it/xcore/jabber/deck";
	
	
	/**
     * Element name of the packet extension.
     */
	public static final String ELEMENT_NAME = "deck";
	
	private static final String ELEMENT_CARD = "card";
	
	private static final String ELEMENT_HIDDEN_CARD = "hidden-card";
	
	private static final String ELEMENT_VALUE = "card-value";
	
	private static final String ELEMENT_IMAGE = "image-path";

	
	 /**
     * Filter this kind of packets.
     */
    public static final PacketFilter FILTER = new PacketFilter() {
        public boolean accept( Packet packet ) {
            return packet.getExtension(ELEMENT_NAME, ELEMENT_NS ) != null;
        }
    };
    
    private CardDeck deck;

	
    public DefaultDeckPacket() {
		this(new CardDeck());
	}
    
    public DefaultDeckPacket(CardDeck deck) {
		this.deck = (CardDeck) deck;
	}
	
	public CardDeck getCardDeck() {
		return deck;
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
		for(int i=0; i < deck.getCards().length; i++){
			DefaultPokerCard story = (DefaultPokerCard) deck.getCards()[i];
			xml+=String.format("<%s>", ELEMENT_CARD);			
			xml+=String.format("<%s>%s</%s>", ELEMENT_VALUE, StringUtils.escapeForXML(story.getStringValue()), ELEMENT_VALUE );
			xml+=String.format("<%s>%s</%s>", ELEMENT_IMAGE, StringUtils.escapeForXML(story.getImagePath()), ELEMENT_IMAGE );
			xml+=String.format("</%s>", ELEMENT_CARD);
		}
		for(int i=0; i < deck.getHiddenCards().length; i++){
			DefaultPokerCard story = (DefaultPokerCard) deck.getHiddenCards()[i];
			xml+=String.format("<%s>", ELEMENT_HIDDEN_CARD);			
			xml+=String.format("<%s>%s</%s>", ELEMENT_VALUE, StringUtils.escapeForXML(story.getStringValue()), ELEMENT_VALUE );
			xml+=String.format("<%s>%s</%s>", ELEMENT_IMAGE, StringUtils.escapeForXML(story.getImagePath()), ELEMENT_IMAGE );
			xml+=String.format("</%s>", ELEMENT_HIDDEN_CARD);
		}
		xml += String.format("</%s>", ELEMENT_NAME);
		//System.out.println(xml);
		return xml;
	}
	
	@Override
	public Object getProvider() {		
		return new Provider();
	}
	
	public static class Provider implements PacketExtensionProvider {
		public PacketExtension parseExtension(XmlPullParser xpp)
		throws Exception {
			CardDeck deck = new CardDeck();
			DefaultPokerCard currStory;
			int eventType = xpp.getEventType();
			while (!(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_NAME))) {
				//Adding card
				if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_CARD)) {
					String value = "";
					String imagePath ="";
					boolean noCards = true;
					while(!(eventType == XmlPullParser.END_TAG&& xpp.getName().equalsIgnoreCase(ELEMENT_CARD))){
						noCards = false;
						if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_VALUE)){	            	 
							xpp.next();
							value = xpp.getText();
						}else if(eventType == XmlPullParser.START_TAG && 
								xpp.getName().equalsIgnoreCase(ELEMENT_IMAGE)){	            	 
							xpp.next();
							imagePath = xpp.getText();
						}
						xpp.next();
						eventType = xpp.getEventType();
					} 
					if(!noCards){
						//System.out.println("Creating User Story...");
						currStory = new DefaultPokerCard(value,imagePath);						
						deck.addCard(currStory);						
					}
				}
				//Adding hidden card
				if(eventType == XmlPullParser.START_TAG && xpp.getName().equalsIgnoreCase(ELEMENT_HIDDEN_CARD)) {
					String value = "";
					String imagePath ="";
					boolean noCards = true;
					while(!(eventType == XmlPullParser.END_TAG&& xpp.getName().equalsIgnoreCase(ELEMENT_HIDDEN_CARD))){
						noCards = false;
						if(eventType == XmlPullParser.START_TAG && 
							xpp.getName().equalsIgnoreCase(ELEMENT_VALUE)){	            	 
							xpp.next();
							value = xpp.getText();
						}else if(eventType == XmlPullParser.START_TAG && 
								xpp.getName().equalsIgnoreCase(ELEMENT_IMAGE)){	            	 
							xpp.next();
							imagePath = xpp.getText();
						}
						xpp.next();
						eventType = xpp.getEventType();
					} 
					if(!noCards){
						//System.out.println("Creating User Story...");
						currStory = new DefaultPokerCard(value,imagePath);						
						deck.addHiddenCard(currStory);						
					}
				}
				
				
				xpp.next();
				eventType = xpp.getEventType();
			}	
			return PacketExtensionAdapter.adaptToTargetPacketExtension(new DefaultDeckPacket(deck));
		}

	}


}
