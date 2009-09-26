package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;

public interface IPlanningPokerView {

	/**
     * Set the chat to use for the chatroom. View implementations are also expected to register
     * as listener of the embedded model.
     * 
     * @param manager
     */
    void setManager( IPlanningPokerManager manager );

    /**
     * Returns the current multichat controller this view belongs to.
     * 
     * @return the Planning Poker controller
     */
    IPlanningPokerManager getManager();
    
    /**
    * Returns the current model this view belongs to.
    * 
    * @return the Planning Poker model
    */
    IPlanningPokerModel getModel();
	
}
