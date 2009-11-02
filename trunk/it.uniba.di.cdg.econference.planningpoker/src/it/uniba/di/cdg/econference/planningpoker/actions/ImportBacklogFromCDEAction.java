package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.usimport.wizard.HandleWizard;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.io.FileInputStream;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.w3c.dom.Document;

public class ImportBacklogFromCDEAction extends Action {

	private IViewPart view;
	private BacklogView backlogView;
	
	public ImportBacklogFromCDEAction(IViewPart view) {
		super();
		this.view = view;	
		setText("Import Backlog");
		setToolTipText("Import user stories from CDE");
		setImageDescriptor(PlanningPokerPlugin.imageDescriptorFromPlugin(
				PlanningPokerPlugin.ID, "icons/backlog/import16x16.png" ));
	}

	@Override
	public void run() {
		if (NetworkPlugin.getDefault().getHelper().getOnlineBackends().size() == 0) {
            UiPlugin.getUIHelper().showErrorMessage( "Please, connect first!" );
            return;
        }
		IWorkbenchWindow window = view.getViewSite().getWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		backlogView = (BacklogView) page.findView(BacklogView.ID);
		
		if(backlogView.getModel()==null){
			UiPlugin.getUIHelper().showErrorMessage( "Please, start a Planning Poker session first!" );
			return;
		}		
		HandleWizard wizard = new HandleWizard(backlogView);
		WizardDialog dlg = new WizardDialog(window.getShell(), wizard);
		dlg.open();
		if(wizard.isFinished()){
			if(wizard.getFileName()!=null && wizard.getFileName()!=""){
				refreshBacklogView(wizard.getFileName());			
			}
		}
	}
	
	protected void refreshBacklogView(String fileName) {
		try {
			FileInputStream is = new FileInputStream( fileName );
			System.out.println(fileName);
			IBacklogContextLoader backlogLoader = PlanningPokerPlugin
			.getDefault().getHelper().getBacklogContextLoader();	
			Document doc = XMLUtils.loadDocument(is);
			Backlog backlog = backlogLoader.load(doc);
			backlogView.getModel().setBacklog(backlog);
			backlogView.getManager().notifyItemListToRemote();
		} catch (Exception e) {
			UiPlugin.getUIHelper().showErrorMessage( "It was impossible load user stories in the Backlog View. Error: " + e.getMessage() );
			e.printStackTrace();
		}
		
	}
}
