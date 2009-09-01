package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.xcore.econference.model.IConferenceModelListener;

public interface IPlanningPokerModelListener extends IConferenceModelListener {
	
	/**
	 * A new instance of backlog has replaced the old one
	 */
	void backlogChanged();
	
	/**
	 * A new instance of voters list has replaced the old one
	 */
	void votersListChanged();
	
	/**
	 * A new instance of card deck has replaced the old one
	 */
	void cardDeckChanged();
	
	/**
	 * A new estimates list has replaced the old one
	 */
	void estimatesChanged();

}

