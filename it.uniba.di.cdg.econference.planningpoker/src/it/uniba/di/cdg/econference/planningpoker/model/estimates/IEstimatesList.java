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



/**
 * Created following the IHandRaisingModel, this class
 * provides a model for handling the incoming estimates made by participants. 
 * 
 * Since an estimation could be repeated for the same User Story, the Estimates 
 * is identified by:
 * <ul>
 * <li>a User Story id that could be the position of the Story or a Story's field</li>
 * <li>an Object id that could be the date of the estimation, or the number
 * of re-estimation of the same story and</li>
 * </ul> 
 * 
 * @author Alex
 *
 */
public interface IEstimatesList{
	
	/**
	 * The state of the estimation
	 * <li>CREATED: means that a voter can add her estimate</li>
	 * <li>CLOSED: means that estimate was closed and nobady 
	 * can add estimation</li>
	 * <li>COMPLETED: when all voters made their estimation. 
	 * Only, in this case participant can see the estimate</li>
	 * <li>REPEATED: when moderator estabilish the need of restimate.</li>
	 * 
	 */
	public enum EstimateStatus  {CREATED, CLOSED, COMPLETED, REPEATED};
	
	
	/**
	 * <p>Set the status of the current estimate session</p>
	 * 
	 * @param status {@link EstimateStatus}
	 */
	void setStatus(EstimateStatus status);
	
	
	/**
	 * <p>Get the status of this estimate session.</p>
	 * 
	 * @return the {@link EstimateStatus}
	 */
	EstimateStatus getStatus();


	/**
	 * <p>Add an estimate to the list of estimates</p>
	 * @param estimate see {@link Estimate}
	 */
	void addEstimate(IEstimate estimate);
	
	/**
	 * 
	 * <p>Remove the estimate of a participant. 
	 * This method was intended to use when a participant lost the voter privilege</p>
	 * @param userId the participant id
	 * 
	 */
	void removeEstimate(String userId);

	/**
	 * 
	 * Get the poker card selected as estimate by a participant
	 * @param userId the participant id
	 * @return the user estimate
	 */
	IEstimate getEstimate(String userId);

	/**
	 * Total number of estimates in the list
	 * 
	 * @return number of estimates
	 */
	int numberOfEstimates();


	/**
	 * Get all the estimates present in the list
	 * 
	 * @return an arrya of {@link Estimate} objects
	 */
	IEstimate[] getAllEstimates();

	

	/**
	 * Set the number of all particiant with the voter privilege
	 * 
	 * @param totalVoters
	 */
	void setTotalVoters(int totalVoters);

	
	
	
	void addListener( IEstimateListener l );

	void removeListener( IEstimateListener l );
	

	void setUserStoryId(String storyId);    

	String getUserStoryId();

	void setId(String date);

	String getId();
	
	
	
	/**
	 * 
	 * Check if passed session estimate is the same session estimate
	 * 
	 * @param estimates 
	 * 
	 * @return true if estimates have same Story Id and Estimate Id
	 */
	boolean equals(IEstimatesList estimates);

	/**
	 * Perform clean up operation
	 */
	 void dispose();

}
