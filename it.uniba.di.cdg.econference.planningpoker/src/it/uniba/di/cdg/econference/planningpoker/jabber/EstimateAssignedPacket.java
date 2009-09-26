package it.uniba.di.cdg.econference.planningpoker.jabber;

import static it.uniba.di.cdg.smackproviders.SmackCommons.CDG_NAMESPACE;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;

import it.uniba.di.cdg.jabber.IPacketExtension;


public class EstimateAssignedPacket implements IPacketExtension {

	
	public static String ELEMENT_NS = CDG_NAMESPACE;
	
	public static String ELEMENT_NAME = "estimate-assigned";
	
	private static String ELEMENT_STORY_ID = "storyId";
	private static String ELEMENT_ESTIMATE = "estimate";
	
	
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
