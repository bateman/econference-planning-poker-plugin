package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListenerAdapter;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.ui.views.IWhiteBoard;
import it.uniba.di.cdg.xcore.econference.ui.views.WhiteBoardView;

public class PPWhiteBoardView extends WhiteBoardView implements IWhiteBoard {
		
	public static final String ID ="it.uniba.di.cdg.econference.planningpoker.views.PPWhiteBoardView";
	
	private IPlanningPokerManager manager;

	private boolean readOnly;

	private IBacklogListener backlogListener = new IBacklogListenerAdapter() {

		public void currentSelectionChanged( int currItemIndex ) {
			if(!readOnly){
				if(currItemIndex!=IItemList.NO_ITEM_SELECTED){
					setAccessible(true);
				}else{
					setAccessible(false);
				}
			}
		}
	};

	public void setManager(IEConferenceManager manager) {
		this.manager = (IPlanningPokerManager) manager;
		super.setManager(manager);
		setAccessible(false);
		getModel().getBacklog().addListener(backlogListener);
	};
	

	@SwtAsyncExec
	public void setAccessible(boolean accessible){
		whiteBoardText.setEditable(accessible);
		refreshRemoteAction.setEnabled(accessible);
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		if(!readOnly){
			if(manager ==null || getModel()==null || getModel().getBacklog()==null ||
					getModel().getBacklog().getCurrentItemIndex()==IItemList.NO_ITEM_SELECTED){
				setAccessible(false);
			}else{
				setAccessible(true);
			}
		}else{
			super.setReadOnly(true);
		}
	}	
	
	private IPlanningPokerModel getModel(){
		return manager.getService().getModel();
	}
		

}
