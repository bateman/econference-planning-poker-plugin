package it.uniba.di.cdg.econference.planningpoker.workbench;

import static it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus.STARTED;
import static it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus.STOPPED;
import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.actions.AddUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.DeleteUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EditUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EstimateStoryAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.ConferenceModelListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModelListener;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.econference.model.ItemListListenerAdapter;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerRow;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class BacklogView extends ViewPart implements IBacklogView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.views.StoriesListView";

	private TableViewer viewer;
	private MenuManager  menuMgr;
	
	protected Button startStopButton = null;

	private EditUserStoryAction editStoryAction;
	private DeleteUserStoryAction deleteStoryAction;
	private AddUserStoryAction addStoryAction;
	private EstimateStoryAction estimateStoryAction;

	private IPlanningPokerManager manager;

	private Listener doubleClickListener = null;

	private IMenuListener viewActionsMenuListener = new IMenuListener(){

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			manager.add(estimateStoryAction);
			//manager.add(new Separator("content"));			
			manager.add(editStoryAction);	
			manager.add(deleteStoryAction);
			manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));	
			manager.add(addStoryAction);

		}

	};


    private IPlanningPokerModelListener ppModelListener = new PlanningPokerModelListenerAdapter() {
        @Override
        public void statusChanged() {
            System.out.println( "statusChanged()" );
            changeButtonStatus( getModel().getStatus() );
        }
        
        public void backlogChanged() {
        	 System.out.println( "itemListChanged()" );
        	 getModel().getBacklog().addListener(backlogListener);
             changeItemList( getModel().getBacklog() );  
        };
    };
    

	private IItemListListener backlogListener = new ItemListListenerAdapter() {
		
		@SwtAsyncExec
		@Override
		public void currentSelectionChanged( int currItemIndex ) {
			setCurrentStory(currItemIndex);		
		}

		@Override
		public void contentChanged(IItemList newBacklog) {
			changeItemList((Backlog) newBacklog);	
			
		}
	};


	public BacklogView() {
	}

	private void makeActions(){
		editStoryAction = new EditUserStoryAction(this);
		deleteStoryAction = new DeleteUserStoryAction(this);
		addStoryAction = new AddUserStoryAction(this);
		estimateStoryAction = new EstimateStoryAction(this);
		estimateStoryAction.setAccessible(false);
	}

	private void createContextMenu(){		
		menuMgr = new MenuManager("StoriesListViewActionPopup");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(viewActionsMenuListener);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());				
		viewer.getControl().setMenu(menu);	
		getSite().registerContextMenu(menuMgr, viewer);	
	}

	@Override
	public void createPartControl(Composite parent) {		          
		Composite top = new Composite( parent, SWT.NONE );
        top.setLayout(new GridLayout(1,false));
 
		GridData gridData = new org.eclipse.swt.layout.GridData();
        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		
        startStopButton = new Button(top, SWT.TOGGLE);
        startStopButton.setText("Start conference");
        startStopButton.setLayoutData(gridData);
        startStopButton.setEnabled(false);
        
        GridData gridDataTable = new org.eclipse.swt.layout.GridData();
        gridDataTable.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridDataTable.grabExcessHorizontalSpace = true;
        gridDataTable.grabExcessVerticalSpace = true;
        gridDataTable.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        
		viewer = new TableViewer(top, SWT.FULL_SELECTION | SWT.BORDER );	
		viewer.getTable().setLayoutData(gridDataTable);
		getSite().setSelectionProvider(viewer);		
		        
		createContextMenu();
		makeActions();
		makeStyledCellTable();  
		
		
		startStopButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                if (STARTED.compareTo( getModel().getStatus()) == 0) {                	
                	getManager().setStatus( STOPPED );                    
                } else {                 	
                	getManager().setStatus( STARTED );
                }
            }
        });
	}

	private void makeStyledCellTable() {
		/*
		 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
		 * Therefore, it is critical for performance that these methods be as
		 * efficient as possible.
		 */
		Listener paintListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.MeasureItem: {
					TableItem item = (TableItem) event.item;
					String text = item.getText(event.index);
					Point size = event.gc.textExtent(text);
					event.width = size.x;
					event.height = Math.max(event.height, size.y);
					break;
				}
				case SWT.PaintItem: {
					TableItem item = (TableItem) event.item;
					String text = item.getText(event.index);
					Point size = event.gc.textExtent(text);
					int offset2 = event.index == 0 ? Math.max(0, (event.height - size.y) / 2) : 0;
					event.gc.drawText(text, event.x, event.y + offset2, true);
					break;
				}
				case SWT.EraseItem: {
					event.detail &= ~SWT.FOREGROUND;
					break;
				}
				}
			}
		};
		viewer.getTable().addListener(SWT.MeasureItem, paintListener);
		viewer.getTable().addListener(SWT.PaintItem, paintListener);
		viewer.getTable().addListener(SWT.EraseItem, paintListener);	

	}


	 @SwtAsyncExec
    private void changeButtonStatus( ConferenceStatus status ) {
        if (STARTED.compareTo( status ) == 0) {
            startStopButton.setText( "Stop conference" );
            startStopButton.setToolTipText( "Press to stop the conference" );
            if(!isReadOnly()){   
            	estimateStoryAction.setAccessible(true);
            	enableDoubleClickListener(true);
            }
        }
        else {
            startStopButton.setText( "Start conference" );
            startStopButton.setToolTipText( "Press to start the conference" );
            getManager().notifyCurrentAgendaItemChanged(String.format("%d", IItemList.NO_ITEM_SELECTED));
            estimateStoryAction.setAccessible(false);
            enableDoubleClickListener(false);            
        }
    }


	public void setManager(IPlanningPokerManager manager) {
		// Switching conference, so deregister from previous manager ...
		if (this.manager != null) {
			getModel().removeListener( ppModelListener );
			getModel().getBacklog().removeListener( backlogListener );
		}
		this.manager = manager;

		getModel().getFactory().createBacklogUIProvider().createColumns(viewer);
		IBacklogViewUIProvider provider = getModel().getFactory().createBacklogUIProvider();
		viewer.setContentProvider(provider);
		viewer.setLabelProvider(provider);
		
		getModel().addListener( ppModelListener );
		//The backlog could be null if no backlog was specified in the PP XML file        
		getModel().getBacklog().addListener( backlogListener );

		changeItemList( getModel().getBacklog() );
		//changeButtonStatus( getModel().getStatus() );
		updateActionsAccordingToRole();

		//remove following line
		this.setTitle(getModel().getLocalUser().getRole().toString());

	}

	@SwtAsyncExec
	private void changeItemList(Backlog backlog) {		
		setCurrentStory(backlog.getCurrentItemIndex());
		viewer.setInput(backlog.getUserStories());
		viewer.refresh();
	}
	
	/**
	 * Highlight the table row of the current user story to estimate
	 * @param currItemIndex the index of the User Story
	 */
	@SwtAsyncExec
	private void setCurrentStory(int currItemIndex){
		//Coloring the table row of the current story
		if(currItemIndex!=IItemList.NO_ITEM_SELECTED){
			clearTableSelection();
			TableItem item = viewer.getTable().getItem(currItemIndex);
			//item.setBackground(viewer.getTable().getDisplay().getSystemColor(CURRENT_SOTORY_COLOR));
			FontData fontData = viewer.getTable().getFont().getFontData()[0];		
			fontData.setStyle(SWT.BOLD);
			item.setFont(new Font(getSite().getShell().getDisplay(),fontData));
		}else{
			clearTableSelection();
		}
	}
	
	private void clearTableSelection(){
		for(TableItem item : viewer.getTable().getItems()){
			//item.setBackground(viewer.getTable().getDisplay().getSystemColor(DEFAULT_COLOR));
			item.setFont(viewer.getTable().getFont());
		}
	}

	private void updateActionsAccordingToRole() {
		setReadOnly(getManager() != null && // There is a manager
				!Role.MODERATOR.equals( getModel().getLocalUser().getRole() ) // and we are moderators 
		);

	}

	@Override
	public boolean isReadOnly() {
		return !startStopButton.isEnabled();
		//return !(getManager() != null && // There is a manager
				//Role.MODERATOR.equals( getModel().getLocalUser().getRole())); // and we are moderators 
	}


	@Override
	@SwtAsyncExec
	public void setReadOnly(boolean readOnly) {	
		startStopButton.setEnabled( !readOnly );
		editStoryAction.setAccessible(!readOnly);
		deleteStoryAction.setAccessible(!readOnly);		
		addStoryAction.setEnabled(!readOnly);
		
		//only if planning poker has been already started, it is possible estimate a story
		if(getModel()!=null && getModel().getStatus().equals(ConferenceStatus.STARTED)){
			estimateStoryAction.setAccessible(!readOnly);
			enableDoubleClickListener(!readOnly);
		}
	}

	private void enableDoubleClickListener(boolean enable) {
		if(!enable){
			if(doubleClickListener != null){
				viewer.getTable().removeListener(SWT.MouseDoubleClick, doubleClickListener );
				doubleClickListener = null;
			}

		}else{
			if(doubleClickListener == null)
				doubleClickListener = new Listener() {
				public void handleEvent(Event event) {						
					estimateStoryAction.run();					
				} 
			};
			viewer.getTable().addListener(SWT.MouseDoubleClick, doubleClickListener );			
		}

	}

	public IPlanningPokerManager getManager(){
		return manager;
	}

	public IPlanningPokerModel getModel(){
		return getManager().getService().getModel();
	}

	@Override
	public void setFocus() {
		startStopButton.setFocus();
	}

	@Override
	public IUserStory getSelectedStory() {
		ISelection selection = this.viewer.getSelection();
		if(selection!=null && selection instanceof IStructuredSelection){
			IStructuredSelection sel = (IStructuredSelection)selection;
			return (IUserStory) sel.getFirstElement();
		}else{
			return null;
		}
	}


}
