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
package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EstimateSession implements IEstimatesList {

public static final int NO_VOTERS = -1;
	
	private Set<IEstimateListener> listeners;
	private HashMap<String, IEstimate> estimates;
		
	private String date;
	private String storyId;
	private int totalVoters;
	private EstimateStatus status;
	
	public EstimateSession(String storyId, String date) {
		this.date = date;
		this.storyId = storyId;
		this.status = EstimateStatus.CREATED;
		listeners = new HashSet<IEstimateListener>();
		estimates = new HashMap<String, IEstimate>();
		totalVoters = NO_VOTERS;
		
	}	
	
	@Override
	public void addEstimate(IEstimate estimate) {
		if(totalVoters!=NO_VOTERS){
			String participantId = estimate.getParticipantId();
			if(estimates.containsKey(participantId)){
				estimates.remove(participantId);
			}
			estimates.put(participantId, estimate);
			for(IEstimateListener l : listeners){
				l.estimateAdded(estimate);
			}
			if(numberOfEstimates() == totalVoters){
				setStatus(EstimateStatus.COMPLETED);	
			}
		}
		
	}

	@Override
	public void addListener(IEstimateListener l) {
		listeners.add(l);
		
	}

	@Override
	public IEstimate getEstimate(String userId) {		
		return estimates.get(userId);
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
	public void removeEstimate(String userId) {
		if(estimates.containsKey(userId)){			
			for(IEstimateListener l : listeners){
				l.estimateRemoved(getEstimate(userId));
			}
			estimates.remove(userId);
			if(numberOfEstimates() == totalVoters){
				setStatus(EstimateStatus.COMPLETED);				
			}
		}
	}

	@Override
	public void setTotalVoters(int totalVoters) {
		this.totalVoters = totalVoters;
		
	}

	//TODO: utilizzare un design pattern strutturale per crare un oggetto composto dallo userId + la IPokerCard
	
	/**
	 * 
	 * @return an object array where each item contains a bidimensional object array in which:
	 * <ul>
	 * <li>in the 0-position there is the participant id</li>
	 * <li>in the 1-position there is the {@link IPokerCard} object</li>
	 * </ul>
	 * 
	 */
	@Override	
	public IEstimate[] getAllEstimates() { 
		IEstimate[] result = new IEstimate[numberOfEstimates()];
		int index = 0;
		for (Iterator<String> iterator = estimates.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			result[index] = estimates.get(key);
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
	public boolean equals(IEstimatesList estimates) {
		return getUserStoryId().equals(estimates.getUserStoryId()) &&
			getId().equals(estimates.getId());
	}

	
}
