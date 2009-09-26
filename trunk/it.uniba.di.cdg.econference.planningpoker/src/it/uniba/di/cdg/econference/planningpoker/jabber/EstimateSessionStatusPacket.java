package it.uniba.di.cdg.econference.planningpoker.jabber;

import static it.uniba.di.cdg.smackproviders.SmackCommons.CDG_NAMESPACE;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.EstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.jabber.IPacketExtension;


public class EstimateSessionStatusPacket implements IPacketExtension {

	
	public static String ELEMENT_NS = CDG_NAMESPACE;
	
	public static String ELEMENT_NAME = "estimate-session";
	private static String ELEMENT_STATUS = "status";
	private static String ELEMENT_STORY_ID = "storyId";
	private static String ELEMENT_ESTIMATE_ID = "id";
	
	
	private String storyId;
	private String id;
	private String status;
	
	  /**
     * Filter this kind of packets.
     */
    public static final PacketFilter FILTER = new PacketFilter() {
        public boolean accept( Packet packet ) {
            return packet.getExtension( ELEMENT_NAME, ELEMENT_NS ) != null;
        }
    };
    
    
	public EstimateSessionStatusPacket() {
		
	}

	public EstimateSessionStatusPacket(String storyId, String id, String status) {
		super();
		this.storyId = storyId;
		this.id = id;
		this.status = status;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
				"<%s>%s</%s>" +
				"</%s>", ELEMENT_NAME, ELEMENT_NS, 
				ELEMENT_STATUS, status,ELEMENT_STATUS,
				ELEMENT_STORY_ID, storyId, ELEMENT_STORY_ID,
				ELEMENT_ESTIMATE_ID, id , ELEMENT_ESTIMATE_ID, 
				ELEMENT_NAME);
		//System.out.println(xml);
		return xml;
	}

	@Override
	public Object getProvider() {
		return EstimateSessionStatusPacket.class;
	}

}
