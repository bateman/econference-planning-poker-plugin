package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerParticipantsSpecialRoles;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;
import it.uniba.di.cdg.xcore.multichat.ui.actions.popup.AbstractParticipantActionDelegate;

import org.eclipse.jface.action.IAction;

public class MakeVoterAction extends AbstractParticipantActionDelegate {

	@Override
	public void run(IAction action) {
		// This action is for planning poker only ... 
        if (!(getManager() instanceof IEConferenceManager))
            return;
        final IEConferenceManager manager = (IEConferenceManager) getManager();
        final IParticipant p = getFirstParticipant();
        if(p!=null)
        	manager.notifySpecialRoleChanged( p, PlanningPokerParticipantsSpecialRoles.VOTER );

	}

}
