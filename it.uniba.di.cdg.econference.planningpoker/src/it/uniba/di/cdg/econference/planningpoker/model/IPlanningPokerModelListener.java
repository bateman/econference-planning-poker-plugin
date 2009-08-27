package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.xcore.econference.model.IConferenceModelListener;

public interface IPlanningPokerModelListener extends IConferenceModelListener {
	
	/**
     * The participant list that are able to estimate change
     */
    void voterListChanged();

}
