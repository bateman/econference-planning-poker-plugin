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
		//System.out.println("Voters added " + itemText);
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
		//System.out.println("Voters removed " + id);
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
