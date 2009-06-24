package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.xcore.econference.IEConferenceHelper;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class LoadPPFromFileActionDelegate implements
		IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;

	}

	@Override
	public void run(IAction action) {
		
		//FIXME uncomment following lines
		/*if (NetworkPlugin.getDefault().getHelper().getOnlineBackends().size() == 0) {
            UiPlugin.getUIHelper().showErrorMessage( "Please, connect first!" );
            return;
        }*/

        PlanningPokerPlugin defualt = PlanningPokerPlugin.getDefault();
        
        IEConferenceHelper helper = defualt.getHelper();
        
        helper.openFromFile();

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
