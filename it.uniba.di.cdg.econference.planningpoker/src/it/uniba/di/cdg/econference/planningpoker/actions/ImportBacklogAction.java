package it.uniba.di.cdg.econference.planningpoker.actions;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.IBacklogContextLoader;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.LoadBacklogDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.w3c.dom.Document;

public class ImportBacklogAction extends Action {

	private IViewPart view;
	
	public ImportBacklogAction(IViewPart view) {
		super();
		this.view = view;	
		setText("Import Backlog");
		setToolTipText("Import a new Backlog");
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
		BacklogView backlogView = (BacklogView) page.findView(BacklogView.ID);
		
		if(backlogView.getModel()==null){
			UiPlugin.getUIHelper().showErrorMessage( "Please, start a Planning Poker session first!" );
			return;
		}		
		
		final LoadBacklogDialog dlg = new LoadBacklogDialog( null );
		if (dlg.open() == Dialog.OK) {
			
			String fileName = dlg.getFileName();
			if(fileName!=""){
				FileInputStream is;
				Document doc;
				Backlog backlog;
				try {
					is = new FileInputStream( fileName );
					IBacklogContextLoader backlogLoader = PlanningPokerPlugin
					.getDefault().getModelFactory()
					.createBacklogContextLoader();						
					doc = XMLUtils.loadDocument(is);
					backlog = backlogLoader.load(doc);
					backlogView.getModel().setBacklog(backlog);
					backlogView.getManager().notifyItemListToRemote();
				} catch (FileNotFoundException ex) {
					UiPlugin.getUIHelper().showErrorMessage( "Sorry, the specified file seems invalid: " 
                            + ex.getMessage() );
					ex.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					UiPlugin.getUIHelper().showErrorMessage( "Could not import this Backlog file: " + e.getMessage() );
				}				
			}
			
		}
	}
}
