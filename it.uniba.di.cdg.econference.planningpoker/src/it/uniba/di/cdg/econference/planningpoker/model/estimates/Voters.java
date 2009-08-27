package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;


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
public class Voters implements IItemList {

	private List<String> voters;
	private Set<IItemListListener> listeners;
	
	
	public Voters() {
		voters = new ArrayList<String>();
		listeners = new HashSet<IItemListListener>();
	}
	
	public Voters(IParticipant[] participants) {
		this();
		for(IParticipant participant : participants){
			voters.add(participant.getId());
		}
			
	}
	
	@Override
	public void addItem(String itemText) {
		voters.add(itemText);
		for(IItemListListener l : listeners){
			l.itemAdded(itemText);
		}	
	}
	
	@Override
	public void addItem(Object item) {
		voters.add((String) item);
	}


	@Override
	public void addListener(IItemListListener listener) {
		listeners.add(listener);
	}

	@Override
	public void decode(String encodedItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object getItem(int itemIndex) {
		return voters.get(itemIndex);
	}

	@Override
	public void removeItem(int itemIndex) {		
		for(IItemListListener l : listeners){
			l.itemRemoved(voters.get(itemIndex));
		}
		voters.remove(itemIndex);
	}

	@Override
	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public int size() {
		return voters.size();
	}
	
	public boolean isVoter(IParticipant participant){
		return voters.contains(participant.getId());
	}


	
	@Override
	public void setCurrentItemIndex(int itemIndex) {
		// Not used

	}
	
	@Override
	public int getCurrentItemIndex() {
		//Not used
		return 0;
	}



}
