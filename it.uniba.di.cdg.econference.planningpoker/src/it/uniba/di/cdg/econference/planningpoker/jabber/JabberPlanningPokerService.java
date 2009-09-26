package it.uniba.di.cdg.econference.planningpoker.jabber;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerService;
import it.uniba.di.cdg.econference.planningpoker.PlanningPokerContext;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Estimate;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.EstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.jabber.IPacketExtension;
import it.uniba.di.cdg.jabber.JabberBackend;
import it.uniba.di.cdg.jabber.PacketExtensionAdapter;
import it.uniba.di.cdg.smackproviders.AgendaOperationPacket;
import it.uniba.di.cdg.smackproviders.CurrentAgendaItemPacket;
import it.uniba.di.cdg.smackproviders.SmackCommons;
import it.uniba.di.cdg.smackproviders.SpecialPrivilegeNotificationPacket;
import it.uniba.di.cdg.xcore.econference.jabber.JabberEConferenceService;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.model.ParticipantSpecialPrivileges;
import it.uniba.di.cdg.xcore.multichat.model.SpecialPrivilegesAction;
import it.uniba.di.cdg.xcore.multichat.service.Invitee;
import it.uniba.di.cdg.xcore.network.model.tv.ITalkModel;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;


public class JabberPlanningPokerService extends JabberEConferenceService implements	IPlanningPokerService {
	
	public JabberPlanningPokerService() {
		super();
	}

	public JabberPlanningPokerService(PlanningPokerContext context,
			JabberBackend backend) {
		super(context, backend);		
	}

	@Override
	public IPlanningPokerModel getModel() {		
		return (IPlanningPokerModel) super.getModel();
	}
	
	  /* (non-Javadoc)
     * @see it.uniba.di.cdg.jabber.internal.JabberMultiChatService#createModel()
     */
    @Override
    protected IPlanningPokerModel createModel() {
    	IPlanningPokerModel model = new PlanningPokerModel();
        // Use the backlog provided by the context if it is present
        if (getContext().getBacklog() != null)
            model.setBacklog(getContext().getBacklog()); 
        if (getContext().getCardDeck() != null){
            model.setCardDeck(getContext().getCardDeck());
        }else{
        	//initializing default card
        	CardDeck deck = new CardDeck();        	
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.UNKNOWN, DefaultPokerCard.IMAGE_UNKNOWN));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.ONE, DefaultPokerCard.IMAGE_ONE));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWO, DefaultPokerCard.IMAGE_TWO));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.THREE, DefaultPokerCard.IMAGE_THREE));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.FIVE, DefaultPokerCard.IMAGE_FIVE));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.EIGHT, DefaultPokerCard.IMAGE_EIGHT));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWENTY, null));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.FOURTY, null));
        	deck.addCard(new DefaultPokerCard(DefaultPokerCard.HUNDRED, null));
    		model.setCardDeck(deck);
        }
       
        if (getContext().getVoters().length != 0){
        	 //assuming that all invitees are voter
            for (Invitee invitee : getContext().getVoters()) {
            	model.getVoters().addVoter(invitee.getId());
			}
        }
        	
        return model;
    }
    
    public PlanningPokerContext getContext(){
    	 return (PlanningPokerContext) super.getContext();
    }
	
	 protected void createAndRegisterSmackListeners() {
	        super.createAndRegisterSmackListeners();
	      	       	        
	        registerCardSelectionListener();
	        
	        registerDeckChangesListener();
	        
	        registerBacklogChangesListener();
	        
	        registerEstimateSessionStatusListener();
	        
	        registerEstimateAssignedListener();
	        
	 }

	protected void registerBacklogChangesListener() {
		PacketListener packetListener = new PacketListener() {

			@Override
			public void processPacket(Packet packet) {
				try{
				final DefaultBacklogPacket ext = (DefaultBacklogPacket) packet.getExtension(
						DefaultBacklogPacket.ELEMENT_NAME, DefaultBacklogPacket.ELEMENT_NS);
//				PacketExtension extension = (PacketExtension) packet.getExtension(
//							DefaultBacklogPacket.ELEMENT_NAME, DefaultBacklogPacket.ELEMENT_NS);
//				final DefaultBacklogPacket ext = (DefaultBacklogPacket)extension;
				getModel().setBacklog(ext.getBacklog());	
				System.out.println("Received parsed backlog with " + ext.getBacklog().getUserStories().length 
						+" user stories and the story number "+ext.getBacklog().getCurrentItemIndex()+ " selected");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		};
		addPacketListener(packetListener, DefaultBacklogPacket.FILTER);
	}
		
	
	/**
	 * Adapted for backlog item removing
	 */
	protected void registerAgendaOperationListener() {
		// Listen to incoming packets regarding the item list content
		PacketListener packetListener = new PacketListener() {
			public void processPacket( Packet packet ) {
				final AgendaOperationPacket ext = (AgendaOperationPacket) packet.getExtension(
						AgendaOperationPacket.ELEMENT_NAME, SmackCommons.CDG_NAMESPACE );
				AgendaOperation op = AgendaOperation.valueOf( ext.getOperation() );
				// Every operation has its context: ADD just want a string containing 
				// the text associated to the new item, REMOVE probably will want to 
				// parse the string to an integer index.
				String context = ext.getContext();
				int index;
				try{
					index = Integer.parseInt(context);
				}catch(NumberFormatException ex){
					index = -1;
				}
				if(index!=-1){
					if (AgendaOperation.REMOVE.equals( op )) {
						getModel().getBacklog().removeItem(index);
						System.out.println("Received a remove operation for the User Story " +index);
					} else {
						System.err.println( "Unsupported operation " + ext.getOperation() );
					}
				}else{
					System.err.println( "Unsupported context for backlog" + ext.getContext() );
				}
			}
		};
		addPacketListener( packetListener, AgendaOperationPacket.FILTER );
	}
	
	protected void registerDeckChangesListener() {
		PacketListener packetListener = new PacketListener() {

			@Override
			public void processPacket(Packet packet) {
				final DefaultDeckPacket ext = (DefaultDeckPacket) packet.getExtension(
						DefaultDeckPacket.ELEMENT_NAME, DefaultDeckPacket.ELEMENT_NS);
				getModel().setCardDeck(ext.getCardDeck());
				System.out.println("Received parsed deck with " + ext.getCardDeck().getCards().length 
						+" cards");
			}
			
		};
		addPacketListener(packetListener, DefaultDeckPacket.FILTER);
	}

	protected void registerCardSelectionListener() {
		PacketListener packetListener = new PacketListener() {
		    public void processPacket( Packet packet ) {
		        final DefaultCardSelectionPacket ext = (DefaultCardSelectionPacket) packet.getExtension(
		        		DefaultCardSelectionPacket.ELEMENT_NAME, DefaultCardSelectionPacket.ELEMENT_NS );
		        String who = ext.getWho();
		        String storyId = ext.getStoryId();
		        IPokerCard card = ext.getCard();
		        if(getModel().getEstimateSession()!=null && 
		        		!getModel().getEstimateSession().getStatus().equals(EstimateStatus.CLOSED)){		        	
		        	if(getModel().getEstimateSession().getUserStoryId().equals(storyId)){
		        		getModel().getEstimateSession().addEstimate(new Estimate(who, card));
		        		System.out.println("Received card selection from " + who 
		    					+" for User Story " + storyId + " with value "+card.getStringValue());
		        	}else{
						System.err.println("Received an estimation not related to the current estimated User Story");
		        	}
		        }else{
					System.err
							.println("Received an estimation that cannot be registered becouse the estimate " +
									"session doesn't exists ore it is closed. Who: " +who+ " Value: "
									+ card.getStringValue());
		        }
		    }
		};
		addPacketListener( packetListener, DefaultCardSelectionPacket.FILTER );
	}
	 
	 
	 @Override
	protected void registerItemListChangesListener() {
		 PacketListener packetListener;
			// item list changes
	        packetListener = new PacketListener() {
	            public void processPacket( Packet packet ) {
	                final CurrentAgendaItemPacket ext = (CurrentAgendaItemPacket) packet.getExtension(
	                        CurrentAgendaItemPacket.ELEMENT_NAME, SmackCommons.CDG_NAMESPACE );

	                if (!ITalkModel.FREE_TALK_THREAD_ID.equals( ext.getItemId() )) {
	                    int itemId = Integer.parseInt( ext.getItemId() );
	                    // Note that we update *** independently ***  the agenda item list 
	                    // and talk model (which handles the discussion threads)
	                    try {
	                        getModel().getBacklog().setCurrentItemIndex( itemId );
	                        String newSubject = ((IUserStory)getModel().getBacklog().getUserStory(itemId)).getTextForMultiChatSubject();
	                        getModel().setSubject( newSubject, "[System]" );
	                    } catch (IllegalArgumentException e) {
	                        // the item list will protest if we set an out-of-range index:
	                        // here it is just safe ignoring its cries ...
	                    	e.printStackTrace();
	                    }
	                }
	                getTalkModel().setCurrentThread( ext.getItemId() );
	            }
	        };
	        addPacketListener( packetListener, CurrentAgendaItemPacket.FILTER );
	}
	 
	 @Override
	 protected void registerSpecialPrivilegeChangesListener() {
		 // Special role changes (needed, i.e., when someone is given "scribe" special role.
		 PacketListener packetListener = new PacketListener() {
			 public void processPacket( Packet packet ) {
				 final SpecialPrivilegeNotificationPacket ext = (SpecialPrivilegeNotificationPacket) packet
				 .getExtension( SpecialPrivilegeNotificationPacket.ELEMENT_NAME,
						 SmackCommons.CDG_NAMESPACE );

				 String who = ext.getWho();
				 IParticipant p = getLocalUserOrParticipant( who );
				 String privilegeAction = ext.getAction();

				 if (p == null) {
					 System.out.println( "Received special privilege " +privilegeAction +" "
							 +ext.getSpecialPrivilege()+" for unknown participant id: " + who );
					 return;
				 }

				 System.out.println( "Received special privilege " +privilegeAction +" "
						 +ext.getSpecialPrivilege()+" for participant id: " + who );
				 // The participant will fire an event
				 if(privilegeAction.equals(SpecialPrivilegesAction.GRANT)){
					 p.addSpecialPriviliges( ext.getSpecialPrivilege() );

					 if(ext.getSpecialPrivilege().equals(ParticipantSpecialPrivileges.VOTER))
						 getModel().getVoters().addVoter(p.getId());

				 }else if(privilegeAction.equals(SpecialPrivilegesAction.REVOKE)){
					 p.removeSpecialPrivileges( ext.getSpecialPrivilege() );

					 if(ext.getSpecialPrivilege().equals(ParticipantSpecialPrivileges.VOTER))
						 getModel().getVoters().removeVoter(p.getId());

				 }else{
					 System.err.println( "Received unknown privilege action " +privilegeAction+ " for participant id: " + who );
					 return;
				 }

			 }			 
		 };
		 addPacketListener( packetListener, SpecialPrivilegeNotificationPacket.FILTER );
	 }
	
	private void registerEstimateSessionStatusListener(){
		PacketListener packetListener = new PacketListener(){

			@Override
			public void processPacket(Packet packet) {
				EstimateSessionStatusPacket ext = (EstimateSessionStatusPacket) packet
				.getExtension(EstimateSessionStatusPacket.ELEMENT_NAME, EstimateSessionStatusPacket.ELEMENT_NS); 
				String storyId = ext.getStoryId();
				String id = ext.getId();
				
				System.out.println( "Received estimate status " + ext.getStatus() +
						" for estimate id: " + id+
						" and story id: " + storyId );
				EstimateStatus status = EstimateStatus.valueOf(ext.getStatus());
				if(status != null){
					if(EstimateStatus.CREATED.equals(status)){					
						getModel().openEstimateSession(new EstimatesList(storyId, id));
					}else if(EstimateStatus.CLOSED.equals(status)||
							EstimateStatus.COMPLETED.equals(status) ||
							EstimateStatus.REPEATED.equals(status)){
						if(getModel().getEstimateSession().equals(new EstimatesList(storyId, id)))
							getModel().getEstimateSession().setStatus(status);
					}
				}else{
					System.err.println( "Invalid estimate session status received: " + status );
				}
				
			}
			
		};
		addPacketListener(packetListener, EstimateSessionStatusPacket.FILTER);
	}
	
	
	
	private void registerEstimateAssignedListener(){
		PacketListener packetListener = new PacketListener(){

			@Override
			public void processPacket(Packet packet) {
				EstimateAssignedPacket ext = (EstimateAssignedPacket) packet
				.getExtension(EstimateAssignedPacket.ELEMENT_NAME, EstimateAssignedPacket.ELEMENT_NS); 
				String storyId = ext.getStoryId();
				String estimateValue = ext.getEstimate();
				
				System.out.println( "Received an estimate " + estimateValue +
						" assigned to story id: " + storyId );
						
				getModel().getBacklog().assignEstimateToStory(storyId, estimateValue);
			}
			
		};
		addPacketListener(packetListener, EstimateAssignedPacket.FILTER);
	}
	 

	@Override
	public void notifyCardSelection(String storyId, IPokerCard card) {
		String localId = getModel().getLocalUser().getId();
		System.out.println( String.format( "notifyCardSelection( %s, %s )",localId, card.getStringValue() ) );
        

        sendCustomExtension(new DefaultCardSelectionPacket( localId, storyId, card ));		
	}
	
	
	/**
	 * Override the method that notifies the agenda item list to all participant
	 * In the case of Planning Poker this method is used to notifies the entire
	 * backlog to all participants
	 */
	
	@Override
	public void notifyItemListToRemote() {
		 System.out.println( String.format("%s: notifyItemListToRemote", this.toString() ));
	      sendCustomExtension( new DefaultBacklogPacket(getModel().getBacklog() ) );		 
	}
	
	@Override
	public void notifyCardDeckToRemote(CardDeck deck) {
		 System.out.println( String.format("%s: notifyCardDeckToRemote", this.toString() ));
	      sendCustomExtension( new DefaultDeckPacket(deck ) );		 
	}
	
	@Override
	public void notifyEstimateSessionStatusChange(IEstimatesList estimate, EstimateStatus status){
		System.out.println( String.format("notifyEstimateSessionStatusChange: %s Story: %s Id: %s", 
				status, estimate.getUserStoryId(), estimate.getId()));
		sendCustomExtension(new EstimateSessionStatusPacket( estimate.getUserStoryId(), estimate.getId(), status.name()));
	}
	
	@Override
	public void notifyEstimateAssigned(String storyId, String estimateValue){
		System.out.println( String.format("notifyEstimateAssigned Story: %s Value: %s", 
				storyId, estimateValue));
		sendCustomExtension(new EstimateAssignedPacket(storyId, estimateValue));
	}
	
	@Override
	public String toString() {
		return "Planning Poker Service";
	}
	

}
