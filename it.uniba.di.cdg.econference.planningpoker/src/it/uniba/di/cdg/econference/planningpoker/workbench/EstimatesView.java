package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Estimates;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimateListener;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import java.util.Date;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class EstimatesView extends ViewPart implements IEstimatesView {

	public static final String ID="it.uniba.di.cdg.econference.planningpoker.EstimatesView";
	
	private IPlanningPokerManager manager;
	private TableViewer viewer;
	private Composite parent;
	
	
	private IItemListListener backlogListener = new ItemListListenerAdapter() {
		
		@SwtAsyncExec
		@Override
		public void currentSelectionChanged( int currItemIndex ) {
			createEstimationList(currItemIndex);
		}		

	};
	
	private IItemListListener voterListener = new ItemListListenerAdapter() {
		
		public void itemAdded(Object item) {
			if(getModel().getEstimates()!=null)
				getModel().getEstimates().setTotalVoters(getModel().getVoters().size());
		};
		
		public void itemRemoved(Object item) {	
			if(getModel().getEstimates()!=null){
				getModel().getEstimates().setTotalVoters(getModel().getVoters().size());
				getModel().getEstimates().removeUserEstimate((String) item);	
			}
		};
		
		
	};
	

	
	
	
	private IEstimateListener estimatesListener = new IEstimateListener(){

		@Override
		public void estimateAdded(String userId, IPokerCard card) {	
			System.out.println("estimated added from "+userId+" value: "+card.getStringValue());
			changeEstimatesList(getModel().getEstimates().getAllEstimates());
			setTableVisible(false);
			
		}

		@Override
		public void estimateRemoved(String userId, IPokerCard card) {
			System.out.println("estimated removed from "+userId+" value: "+card.getStringValue());
			changeEstimatesList(getModel().getEstimates().getAllEstimates());
			setTableVisible(false);
		}

		@Override
		public void estimatesCompleted() {
			setTableVisible(true);			
		}

		@Override
		public void estimateListCreated(Object storyId, Object id) {
			setTableVisible(false);			
			changeEstimatesList(getModel().getEstimates().getAllEstimates());
			
			//TODO: implementare il metodo per memorizzare queste stime nella history
		}
		
	};
	
	public EstimatesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		viewer = new TableViewer(parent,SWT.NONE);
	}
	
	private void createEstimationList(int storyId) {
		if(//getManager().getRole().equals(Role.MODERATOR) &&
				getModel().getStatus().equals(ConferenceStatus.STARTED)&&
					getModel().getBacklog().getCurrentItemIndex()!=IItemList.NO_ITEM_SELECTED){
			getManager().getService().getModel().setEstimates(new Estimates(new Date().toString(),storyId));	
			getModel().getEstimates().setTotalVoters(getModel().getVoters().size());
			getModel().getEstimates().addListener(estimatesListener);
		}
		
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
			getModel().getBacklog().removeListener(backlogListener);
			getModel().getVoters().removeListener(voterListener);
			//getModel().getEstimates().removeListener(estimatesListener);
		}
		this.manager = manager;
				
		getModel().getBacklog().addListener(backlogListener);
		getModel().getVoters().addListener(voterListener);
		
		IEstimatesViewUIProvider provider = 
			getModel().getFactory().createEstimateViewUIHelper(parent);
		provider.createColumns(viewer);
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(provider);
		
		
		
	}
	
	@SwtAsyncExec
	private void changeEstimatesList(Object[] estimates) {
		//trasform the userId in a Participant object
		for (int i = 0; i < estimates.length; i++) {
			Object[] object = (Object[]) estimates[i];
			object[0] = getModel().getParticipant((String) object[0]);			
		}		
		viewer.setInput(estimates);
		viewer.refresh();
	}


	@Override
	public boolean isReadOnly() {
		return !(getManager() != null && // There is a manager
				Role.MODERATOR.equals( getModel().getLocalUser().getRole())); // and we are moderators 
	}

	@Override
	public void setReadOnly(boolean readOnly) {	
		//TODO: inserire i metodi per nascondere gli stimatori
		setTableVisible(!readOnly);
	}
	
	private void setTableVisible(boolean visible){
		if(!Role.MODERATOR.equals( getModel().getLocalUser().getRole())){			
			viewer.getTable().setVisible(visible);			
		}
		
		
	}

	@Override
	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

}
