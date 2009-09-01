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
		
	private Object date;
	private Object storyId;
	private int totalVoters;
	
	public Estimates(Object date, Object storyId) {
		this.date = date;
		listeners = new HashSet<IEstimateListener>();
		estimates = new HashMap<String, IPokerCard>();
		totalVoters = NO_VOTERS;
		for(IEstimateListener l : listeners){
			l.estimateListCreated(storyId, date);
		}
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
					l.estimatesCompleted();
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
	public Object getId() {
		return date;
	}

	@Override
	public void setId(Object date) {
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
					l.estimatesCompleted();
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
	public Object getUserStoryId() {
		return storyId;
	}

	@Override
	public void setUserStoryId(Object storyId) {
		this.storyId = storyId;
		
	}

	@Override
	public void dispose() {
		listeners.clear();		
	}
	
}
