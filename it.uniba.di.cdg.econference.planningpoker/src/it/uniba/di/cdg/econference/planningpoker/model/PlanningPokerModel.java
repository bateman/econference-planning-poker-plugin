package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModel;
import it.uniba.di.cdg.xcore.multichat.model.IChatRoomModelListener;

public class PlanningPokerModel extends ConferenceModel implements IPlanningPokerModel {
	
	/**
	 * The list of user stories in the backlog
	 */
	private Backlog backlog;
	
	/**
	 * The model factory used to create the UIProviders, the type of IUserStory and IPokerCard used
	 * 
	 */
	private IModelAbstractFactory factory;
	
	private CardDeck deck;
	
	
	private IEstimatesList estimates;
	
	private Voters voters; 
	

	 public PlanningPokerModel() {
	        super();
	        /* TODO The abstract factory is initialized with the Default 
			 * Factory which is the base factory
			 */			       
	        this.factory = new DefaultModelFactory();	
	        
	        this.deck = new CardDeck();
	        this.backlog = new Backlog();
	        
	        this.voters = new Voters();	        
	    }

	
	@Override
	public void setBacklog(Backlog backlog) {
//		if(this.backlog!=null)
//			this.backlog.dispose();
//		this.backlog = backlog;
//		for (IChatRoomModelListener l : listeners()) {
//            if (l instanceof IPlanningPokerModelListener) {
//                ((IPlanningPokerModelListener) l).backlogChanged();
//            }
//        }
		/* We do not set the backlog to the new object to avoid 
		 * adding the backlogChanged method listener in all views 
		 */
		this.backlog.setBacklogContent(backlog.getUserStories());	
		this.backlog.setCurrentItemIndex(backlog.getCurrentItemIndex());
	}

	@Override
	public Backlog getBacklog() {
		return backlog;
	}

	@Override
	public CardDeck getCardDeck() {
		return deck;
	}

	@Override
	public void setCardDeck(CardDeck deck) {
		if(this.deck!=null)
			this.deck.dispose();
		this.deck = deck;	
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).cardDeckChanged();
            }
        }
	}


	@Override
	public IEstimatesList getEstimateSession() {		
		return estimates;
	}


	@Override
	public void openEstimateSession(IEstimatesList estimates) {
		if(this.estimates!=null)
			this.estimates.dispose();
		this.estimates = estimates;
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).estimateSessionOpened();
            }
        }
	}
	
	@Override
	public void setVoters(Voters voters) {
		if(this.voters!=null)
			this.voters.dispose();
		this.voters = voters;
		for (IChatRoomModelListener l : listeners()) {
            if (l instanceof IPlanningPokerModelListener) {
                ((IPlanningPokerModelListener) l).votersListChanged();
            }
        }
	}
	
	@Override
	public Voters getVoters(){
		return voters;
	}



}
