package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class contains the list of the participants that are able
 * to do an estimation. In other words there are all participants
 * that can use card deck
 * 
 * Every item is model as a String item that represents the id of the participant
 * 
 * @author Alex
 *
 */
public class Voters {

	private List<String> voters;
	private Set<IItemListListener> listeners;
	
	
	public Voters() {
		voters = new ArrayList<String>();
		listeners = new HashSet<IItemListListener>();
	}
	
	
	public void addVoter(String itemText) {
		System.out.println("Voters added " + itemText);
		if(!voters.contains(itemText)){
			voters.add(itemText);
			for(IItemListListener l : listeners){
				l.itemAdded(itemText);
			}	
		}
	}
	

	public void addListener(IItemListListener listener) {
		listeners.add(listener);
	}


	public Object getVoter(int itemIndex) {
		return voters.get(itemIndex);
	}
	
	public void removeVoter(String id){
		System.out.println("Voters removed " + id);
		for(IItemListListener l : listeners){
			l.itemRemoved(id);
		}
		voters.remove(id);
	}


	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);
	}
	
	public int size() {
		return voters.size();
	}
	
	/**
	 * Return <b>true<b> if the participant is present in the 
	 * voter list,so he can estimate, return <b>false<b> 
	 * if the participant is not present in the voter list
	 * 
	 * @param id the id of the participant
	 * @return true if participant can estimate
	 */
	public boolean isVoter(String id){
		return voters.contains(id);
	}


	/**
	 * Perform clean up operation
	 */
	public void dispose() {
		listeners.clear();		
	}



}
