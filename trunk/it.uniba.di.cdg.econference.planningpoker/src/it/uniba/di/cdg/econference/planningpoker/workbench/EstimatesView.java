package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class EstimatesView extends ViewPart implements IEstimatesView {

	public static final String ID="it.uniba.di.cdg.econference.planningpoker.EstimatesView";
	
	private IPlanningPokerManager manager;
	private TableViewer viewer;

	public EstimatesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent,SWT.NONE);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public IPlanningPokerManager getManager() {
		return manager;
	}

	@Override
	public void setManager(IPlanningPokerManager manager) {
		if(this.manager != null){
			//TODO: eliminare eventuali listener associati al model
		}
		this.manager = manager;
		
	}


	@Override
	public boolean isReadOnly() {
		return !(getManager() != null && // There is a manager
				Role.MODERATOR.equals( getModel().getLocalUser().getRole())); // and we are moderators 
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		viewer.getTable().setVisible(false);		
	}

	@Override
	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

}
