package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import java.util.HashMap;

/**
 * 
 * This class contains all estimates for a single User Story
 * The class has an HashMap with the id of a participant as key
 * and the string value of selected card as value
 * 
 * @author Alex
 *
 */

public class EstimatesList {
	
	private HashMap<String,String> estimates;
	
	public EstimatesList() {
		estimates = new HashMap<String, String>();
	}

}
