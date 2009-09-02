package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Estimates;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimateListener;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimates.EstimateStatus;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class EstimatesView extends ViewPart implements IEstimatesView {

	public static final String ID="it.uniba.di.cdg.econference.planningpoker.EstimatesView";
	
	private IPlanningPokerManager manager;
	private TableViewer viewer;
	private Composite actionsComposite;
	private Composite root;
	
	private Button restimationButton;
	private Button acceptButton;
	private Text finalEstimateText;
	
	
	private IItemListListener backlogListener = new ItemListListenerAdapter() {
		
		@SwtAsyncExec
		@Override
		public void currentSelectionChanged( int currItemIndex ) {
				createEstimationList(String.valueOf(currItemIndex));
		}		

	};
	
	private IItemListListener voterListener = new ItemListListenerAdapter() {
		
		public void itemAdded(Object item) {
			if(getModel().getEstimateSession()!=null)
				getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
		};
		
		public void itemRemoved(Object item) {	
			if(getModel().getEstimateSession()!=null){
				getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
				getModel().getEstimateSession().removeUserEstimate((String) item);	
			}
		};
		
		
	};
	

	private IEstimateListener estimatesListener = new IEstimateListener(){

		@Override
		public void estimateAdded(String userId, IPokerCard card) {	
			System.out.println("estimated added from "+userId+" value: "+card.getStringValue());
			changeEstimatesList(getModel().getEstimateSession().getAllEstimates());
			setTableVisible(false);
			
		}

		@Override
		public void estimateRemoved(String userId, IPokerCard card) {
			System.out.println("estimated removed from "+userId+" value: "+card.getStringValue());
			changeEstimatesList(getModel().getEstimateSession().getAllEstimates());
			setTableVisible(false);
		}
		
		public void estimateStatusChanged(EstimateStatus status, String storyId, String estimateId) {
			if(EstimateStatus.COMPLETED.equals(status)){
				//TODO: implementare il metodo per memorizzare queste stime nella history
				setTableVisible(true);	
			}else{
				setTableVisible(false);									
			}				
			
		};
		
	};
	
	
	private IPlanningPokerModelListener ppListener = new PlanningPokerModelListenerAdapter(){
		
		public void estimateSessionOpened() {
			getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
			getModel().getEstimateSession().addListener(estimatesListener);			
		};
			
	};

	
	
	public EstimatesView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(2, false));
		
        GridData gridDataTable = new org.eclipse.swt.layout.GridData();
        gridDataTable.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridDataTable.grabExcessHorizontalSpace = true;
        gridDataTable.grabExcessVerticalSpace = true;
        gridDataTable.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		
		viewer = new TableViewer(root, SWT.HIDE_SELECTION | SWT.BORDER);
		viewer.getTable().setLayoutData(gridDataTable);
		
		        
        
	}
	
	@SwtAsyncExec
	private void setActionsCompositeVisibility(boolean visible){
		if(visible){
			RowLayout topLayout = new RowLayout();
			topLayout.pack = false;
			topLayout.type = SWT.VERTICAL;
			
			GridData gridData = new org.eclipse.swt.layout.GridData();
			gridData.horizontalAlignment = SWT.TOP;
			gridData.verticalAlignment = SWT.RIGHT;
			gridData.grabExcessVerticalSpace = true;
			
			actionsComposite = new Composite(root, SWT.NONE);
			//Group top = new Group(root, SWT.NONE);
			actionsComposite.setLayout(topLayout);
			actionsComposite.setLayoutData(gridData);
			
			
			finalEstimateText = new Text(actionsComposite, SWT.PUSH | SWT.BORDER);
	        finalEstimateText.setText("");        
	        finalEstimateText.setEnabled(true);
	        finalEstimateText.setEditable(true);
	        
	        restimationButton = new Button(actionsComposite, SWT.PUSH );
	        restimationButton.setText("Repeat");
	        restimationButton.setEnabled(false);
	        
	        acceptButton = new Button(actionsComposite, SWT.PUSH );
	        acceptButton.setText("Accept");
	        acceptButton.setEnabled(true);
	        acceptButton.addSelectionListener(new SelectionAdapter(){
	        	@Override
	        	public void widgetSelected(SelectionEvent e) {
	        		setReadOnly(false);
	        	}
	        });	      	    
	        
		}else{
			if(actionsComposite!=null)
				actionsComposite.dispose();
		}
		root.layout();
	}
	
	private void createEstimationList(String storyId) {
		if(getManager().getRole().equals(Role.MODERATOR) &&
				getModel().getStatus().equals(ConferenceStatus.STARTED)&&
					getModel().getBacklog().getCurrentItemIndex()!=IItemList.NO_ITEM_SELECTED
					&& ( getModel().getEstimateSession()==null
					|| !getModel().getEstimateSession().getUserStoryId().equals(storyId))){			
			String date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).format(new Date());
			IEstimates estimates = new Estimates(storyId,date);
			getManager().notifyEstimateSessionStatusChange(estimates);			
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
			getModel().removeListener(ppListener);
			getModel().getBacklog().removeListener(backlogListener);
			getModel().getVoters().removeListener(voterListener);
			//getModel().getEstimates().removeListener(estimatesListener);
		}
		this.manager = manager;
			
		getModel().addListener(ppListener);
		getModel().getBacklog().addListener(backlogListener);
		getModel().getVoters().addListener(voterListener);
		
		IEstimatesViewUIProvider provider = 
			getModel().getFactory().createEstimateViewUIHelper();
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
		setActionsCompositeVisibility(!readOnly);
	}
	
	@SwtAsyncExec
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
