package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerParticipantsSpecialRoles;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.ui.actions.popup.AbstractParticipantActionDelegate;

import org.eclipse.jface.action.IAction;

public class UnMakeVoterAction extends AbstractParticipantActionDelegate {

	@Override
	public void run(IAction action) {
		// This action is for planning poker only ... 
        if (!(getManager() instanceof IPlanningPokerManager))
            return;
        final IPlanningPokerManager manager = (IPlanningPokerManager) getManager();
        final IParticipant p = getFirstParticipant();
        if(p!=null)       
        	manager.notifySpecialRoleChanged( p, PlanningPokerParticipantsSpecialRoles.NONE );        
	}

}
