package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.EstimateSession;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimate;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimateListener;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.econference.planningpoker.utils.AutoResizeTableLayout;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.network.messages.SystemMessage;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

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
import org.eclipse.swt.widgets.Label;
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
		
	private IBacklogListener backlogListener = new IBacklogListenerAdapter() {
		
		@Override
		public void currentSelectionChanged( int currItemIndex ) {
			if(!isReadOnly() //Only the moderator can create the new estimate session
					&& getModel().getBacklog().getCurrentItemIndex()!=IItemList.NO_ITEM_SELECTED //Only if one user story is being estimated
					&& ( getModel().getEstimateSession()==null 
					|| !getModel().getEstimateSession()
					.getUserStoryId().equals(String.valueOf(currItemIndex))))//only if the current estimated story is the same
			
			{	//TODO: sostituire l'index con l'id della story				
				createEstimationList(String.valueOf(currItemIndex));							
			}
		}	
		
		public void estimateAssigned(String storyId, String estimateValue) {
			appendSystemMessage("Story "+storyId+ " HAS BEEN ESTIMATED WITH: "+ estimateValue);			
		};

	};
	
	private IItemListListener voterListener = new ItemListListenerAdapter() {
		
		public void itemAdded(Object item) {
			if(isEstimateSessionValid())
				getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
		};
		
		public void itemRemoved(Object item) {	
			if(isEstimateSessionValid()){
				getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
				getModel().getEstimateSession().removeEstimate((String) item);	
			}
		};
		
		
	};
	

	private IEstimateListener estimatesListener = new IEstimateListener(){

		@Override
		public void estimateAdded(IEstimate estimate) {	
			appendSystemMessage(getModel().getParticipant(estimate.getParticipantId())
					.getNickName()+" made his estimation" );
			System.out.println("estimated added from "+estimate.getParticipantId()+
					" value: "+estimate.getCard().getStringValue());
			changeEstimatesList(getModel().getEstimateSession().getAllEstimates(), false);	
		}

		@Override
		public void estimateRemoved(IEstimate estimate) {
			System.out.println("estimated removed from "+estimate.getParticipantId()+
					" value: "+estimate.getCard().getStringValue());
			changeEstimatesList(getModel().getEstimateSession().getAllEstimates(), false);			
		}
		
		@Override
		public void estimateStatusChanged(EstimateStatus status, String storyId, String estimateId) {
			if(EstimateStatus.COMPLETED.equals(status)){
				appendSystemMessage("All estimates are AVAILABLE");
				//TODO: implementare il metodo per memorizzare queste stime nella history
				changeEstimatesList(getModel().getEstimateSession().getAllEstimates(), true);
				suggestFinalEstimate();
				setRestimateButtonEnable(true);	
				setAcceptButtonEnable(true);
				appendSystemMessage("Card selection is DISABLED");
			}else if(EstimateStatus.CLOSED.equals(status)){
				clearTableContent();
				setFinalEstimateText("");
				setRestimateButtonEnable(false);	
				setAcceptButtonEnable(false);
				appendSystemMessage("Card selection is DISABLED");
			}else if(EstimateStatus.REPEATED.equals(status)){
				getManager().getTalkView().appendMessage("Moderator established to RE-ESTIMATE this story");
			}
		};
		
	};
	

	private IPlanningPokerModelListener ppListener = new PlanningPokerModelListenerAdapter(){

		public void estimateSessionOpened() {
			getModel().getEstimateSession().setTotalVoters(getModel().getVoters().size());
			getModel().getEstimateSession().addListener(estimatesListener);		
			setFinalEstimateText("");
			setAcceptButtonEnable(true);
			appendSystemMessage("Card selection is ENABLED");
		};

		public void statusChanged() {
			if(ConferenceStatus.STOPPED.equals(getModel().getStatus()))
				if(getModel().getEstimateSession()!=null){
					getManager().getService().notifyEstimateSessionStatusChange(getModel()
							.getEstimateSession(), EstimateStatus.CLOSED);					
				}
		};

	};



	@SwtAsyncExec
	protected void appendSystemMessage(String string) {
		getManager().getTalkView().appendMessage( new SystemMessage(string));		
	}


	@SwtAsyncExec
	protected void clearTableContent() {
		viewer.getTable().clearAll();	
	}

	/**
	 * <p>Suggest a value for the final estimate performing the average of all the estiamate values.
	 * This method works only if estimate values are numeric</p>
	 * 
	 */
	@SwtAsyncExec
	protected void suggestFinalEstimate() {
		if(!isReadOnly()){
			IEstimate[] estimates = getModel().getEstimateSession().getAllEstimates();
			IPokerCard currCard;
			double sum = 0;
			int total = 0;
			for (IEstimate estimate : estimates) {
				currCard = estimate.getCard();
				try{
					sum+= Double.parseDouble(currCard.getStringValue());
					total++;
				}catch(NumberFormatException e){
					//Do nothing
				}
			}
			if(total!=0 && sum!=0){
				double average = sum/total;
				//Let's get the most near card value
				String cardValue = findMostNearCardValue(average);
				if(cardValue!=null){
					setFinalEstimateText(cardValue);
				}
			}
		}		
	}

	@SwtAsyncExec
	private void setFinalEstimateText(String estimate) {
		if(!isReadOnly())
			finalEstimateText.setText(estimate);		
	}

	/**
	 * <p>Get the most near card value to the parameter value</p> 
	 * @param target the number to which we need to approach
	 * @return 
	 */
	private String findMostNearCardValue(double target) {
		String candidateCardValue = null;
		Double currValue = null;
		Double currDistance;
		Double minDistance;	
		IPokerCard[] cards = getModel().getCardDeck().getCards();		
		for (IPokerCard currCard : cards) {
			try{
				currValue  = Double.parseDouble(currCard.getStringValue());
				candidateCardValue = currCard.getStringValue();
				break;
			}catch(NumberFormatException e){ //Do nothing 	
			}			
		}
		if(currValue!=null){//There are at least one numeric value		
			minDistance = Math.abs(currValue - target);	
			//Let's find the minimum distance
			for (IPokerCard currCard : cards) {
				try{
					currValue  = Double.parseDouble(currCard.getStringValue());
					currDistance = Math.abs(currValue - target);
					if(currDistance<minDistance){
						minDistance = currDistance;
						candidateCardValue = currCard.getStringValue();
					}
				}catch(NumberFormatException e){ //Do nothing 

				}			
			}			
		}
		return candidateCardValue;		
	}


	@SwtAsyncExec
	private void setRestimateButtonEnable(boolean enable) {
		if(!isReadOnly())
			restimationButton.setEnabled(enable);			
	}
	
	@SwtAsyncExec
	protected void setAcceptButtonEnable(boolean enable) {
		if(!isReadOnly())
			acceptButton.setEnabled(enable);		
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
		
		viewer = new TableViewer(root, SWT.FULL_SELECTION | SWT.BORDER);
		viewer.getTable().setLayoutData(gridDataTable);
		
		getSite().setSelectionProvider(viewer);
		
		//Adding auto-resize columns
		AutoResizeTableLayout layout = new AutoResizeTableLayout(viewer.getTable());
		viewer.getTable().setLayout(layout);
	}
	
	private boolean isEstimateSessionValid(){
		return getModel()!=null && getModel().getEstimateSession()!=null 
		&& getModel().getEstimateSession().getStatus().equals(EstimateStatus.CREATED);
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
			
			RowLayout rowLayout = new RowLayout();
			rowLayout.pack = true;
			//topLayout.type = SWT.HORIZONTAL;
			
			Composite topComposite = new Composite(actionsComposite, SWT.PUSH);
			topComposite.setLayout(rowLayout);
			
			Label finalEstimateLabel = new Label(topComposite, SWT.PUSH);
			finalEstimateLabel.setText("Estimate:");
			
			finalEstimateText = new Text(topComposite, SWT.SINGLE | SWT.PUSH | SWT.BORDER);
	        finalEstimateText.setText("");        
	        finalEstimateText.setEnabled(true);
	        finalEstimateText.setEditable(true);	
	        finalEstimateText.setToolTipText("The estimate that should " +
	        		"be assigned to the current story");
	        
	        rowLayout = new RowLayout();
	        rowLayout.pack = false;
	        
	        Composite bottomComposite = new Composite(actionsComposite, SWT.PUSH);
	        bottomComposite.setLayout(rowLayout);
	        
	        acceptButton = new Button(bottomComposite, SWT.PUSH );
	        acceptButton.setText("Accept");
	        acceptButton.setEnabled(false);
	        acceptButton.addSelectionListener(new SelectionAdapter(){
	        	@Override
	        	public void widgetSelected(SelectionEvent e) {
	        		setFinalEstimate(finalEstimateText.getText());
	        	}
	        });	
	        
	        restimationButton = new Button(bottomComposite, SWT.PUSH );
	        restimationButton.setText("Repeat");
	        restimationButton.setEnabled(false);	        
	        restimationButton.addSelectionListener(new SelectionAdapter(){
	        	@Override
	        	public void widgetSelected(SelectionEvent e) {
	        		if(!isReadOnly()){	        				        				
	        			getManager().notifyEstimateSessionStatusChange(getModel().getEstimateSession(),EstimateStatus.REPEATED);
	        			int currItemIndex = getModel().getBacklog().getCurrentItemIndex();
	        			createEstimationList(((IUserStory)getModel().getBacklog().getUserStory(currItemIndex)).getId());
	        		}
	        	}
	        });	
	        
	        
	           	    
	        
		}else{
			if(actionsComposite!=null && !actionsComposite.isDisposed())
				actionsComposite.dispose();
		}
		root.layout(true);
	}
	
	protected void setFinalEstimate(String estimate) {

		boolean contextOk = !isReadOnly() && getModel().getEstimateSession()!=null 
		&& getModel().getEstimateSession().getStatus()!= EstimateStatus.CLOSED;
		if(contextOk){
			if(validateEstimate(estimate)){					
				int index = getModel().getBacklog().getCurrentItemIndex();
				IUserStory currStory = (IUserStory)getModel().getBacklog().getUserStory(index);			
				getManager().notifyEstimateAssigned(currStory.getId(), estimate);	
				getManager().notifyEstimateSessionStatusChange(getModel().getEstimateSession(), EstimateStatus.CLOSED);
			}
		}
	}

	private boolean validateEstimate(String estimate) {
		boolean notEmpty = estimate!=null && estimate!="" ;
		if(notEmpty){
			//let's check if the estimate value is one of card deck values
			IPokerCard card = getModel().getCardDeck().getCardFromStringValue(estimate);			
			if(card!=null){
				return true;
			}else{
				boolean yesPressed = UiPlugin.getUIHelper().askYesNoQuestion(
						"Warning", "Actually, there is not cards in the deck with this estimate value. " +
								"Are you sure want to assign this value?");
				if(yesPressed)
					return true;
				else
					return false;
			}
		}else{
			return false;
		}
	}

	private void createEstimationList(String storyId) {
		String date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).format(new Date());
		IEstimatesList estimates = new EstimateSession(storyId,date);
		getManager().notifyEstimateSessionStatusChange(estimates, EstimateStatus.CREATED);			
	}

	@Override
	public void setFocus() {
		viewer.getTable().setFocus();

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
		
		IEstimatesViewUIProvider provider = PlanningPokerPlugin.getDefault()
			.getHelper().getEstimateViewUIProvider();
		viewer.setContentProvider(provider);	
		//viewer.setLabelProvider(provider);
		provider.createColumns(viewer);	
		
		refreshView();	
		
	}
	
	private void changeEstimatesList(IEstimate[] estimates, boolean visible) {
		Object[] result = new Object[estimates.length];
		if(estimates!=null){
			for (int i = 0; i < estimates.length; i++) {	
				//This array will contains the IParticipant and the IPokerCard
				Object[] singleItem = new Object[2];
				IEstimate estimate = (IEstimate) estimates[i];
				singleItem[0] = getModel().getParticipant(estimate.getParticipantId());		
				if(!visible)
					singleItem[1] = null;
				else
					singleItem[1] = estimate.getCard();
				result[i] = singleItem;
			}	
			setViewerContent(result);
		}
				
	}
		
	@SwtAsyncExec
	private void setViewerContent(Object[] content){
		if(content!=null && content.length>0)
		viewer.setInput(content);
		viewer.refresh();
	}

	 /**
     * Refresh the whole view. 
     */
    @SwtAsyncExec
    private void refreshView() {
    	viewer.refresh();
    }

	@Override
	public boolean isReadOnly() {
		return !(getManager() != null && // There is a manager
				getModel()!= null &&
				Role.MODERATOR.equals( getModel().getLocalUser().getRole())); // and we are moderators 
	}

	@Override
	public void setReadOnly(boolean readOnly) {	
		setActionsCompositeVisibility(!readOnly);
	}
	

	@Override
	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

	/**
	 * For test only
	 * @return
	 */
	public TableViewer getViewer() {
		return viewer;
	}

}
