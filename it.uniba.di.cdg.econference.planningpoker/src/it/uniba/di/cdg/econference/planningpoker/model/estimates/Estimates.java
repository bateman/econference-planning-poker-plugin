package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Estimates implements IEstimates {

public static final int NO_VOTERS = -1;
	
	private Set<IEstimateListener> listeners;
	private HashMap<String, IPokerCard> estimates;
		
	private String date;
	private String storyId;
	private int totalVoters;
	private EstimateStatus status;
	
	public Estimates(String storyId, String date) {
		this.date = date;
		this.storyId = storyId;
		this.status = EstimateStatus.CREATED;
		listeners = new HashSet<IEstimateListener>();
		estimates = new HashMap<String, IPokerCard>();
		totalVoters = NO_VOTERS;
		
	}	
	
	@Override
	public void addEstimate(String userId,IPokerCard card) {
		if(totalVoters!=NO_VOTERS){
			if(estimates.containsKey(userId)){
				estimates.remove(userId);
			}
			estimates.put(userId, card);
			for(IEstimateListener l : listeners){
				l.estimateAdded(userId, card);
			}
			if(numberOfEstimates() == totalVoters){
				for(IEstimateListener l : listeners){
					l.estimateStatusChanged(EstimateStatus.COMPLETED, getUserStoryId(), getId());
				}
			}
		}
		
	}

	@Override
	public void addListener(IEstimateListener l) {
		listeners.add(l);
		
	}

	@Override
	public IPokerCard getEstimate(String UserId) {		
		return estimates.get(UserId);
	}

	@Override
	public int numberOfEstimates() {
		return estimates.size();
	}

	@Override
	public void removeListener(IEstimateListener l) {
		listeners.remove(l);
		
	}

	@Override
	public String getId() {
		return date;
	}

	@Override
	public void setId(String date) {
		this.date = date;
	}

	@Override
	public void removeUserEstimate(String userId) {
		if(estimates.containsKey(userId)){			
			for(IEstimateListener l : listeners){
				l.estimateRemoved(userId, estimates.get(userId));
			}
			estimates.remove(userId);
			if(numberOfEstimates() == totalVoters){
				for(IEstimateListener l : listeners)
					l.estimateStatusChanged(EstimateStatus.COMPLETED, getUserStoryId(), getId());
			}
		}
	}

	@Override
	public void setTotalVoters(int totalVoters) {
		this.totalVoters = totalVoters;
		
	}

	//TODO: utilizzare un design pattern strutturale per crare un oggetto composto dallo userId + la IPokerCard
	@Override
	public Object[] getAllEstimates() { 
		Object[] result = new Object[numberOfEstimates()];
		int index = 0;
		for (Iterator<String> iterator = estimates.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object[] estimate = new Object[2];
			estimate[0] = key;
			estimate[1] = estimates.get(key);
			result[index] = estimate;
			index++;
		}
		return result;
	}

	@Override
	public String getUserStoryId() {
		return storyId;
	}

	@Override
	public void setUserStoryId(String storyId) {
		this.storyId = storyId;
		
	}

	@Override
	public void dispose() {		
		estimates.clear();
		for(IEstimateListener l : listeners)
			l.estimateStatusChanged(EstimateStatus.CLOSED, getUserStoryId(), getId());
		listeners.clear();		
	}

	@Override
	public EstimateStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(EstimateStatus status) {
		this.status = status;	
		for(IEstimateListener l : listeners)
			l.estimateStatusChanged(status, getUserStoryId(), getId());
	}

	@Override
	public boolean equals(IEstimates estimates) {
		return getUserStoryId().equals(estimates.getUserStoryId()) &&
			getId().equals(estimates.getId());
	}
	
}
