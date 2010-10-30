/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import static it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus.STARTED;
import static it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus.STOPPED;
import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.econference.planningpoker.actions.AddUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.DeleteUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EditUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EstimateStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.ExportBacklogAction;
import it.uniba.di.cdg.econference.planningpoker.actions.ImportBacklogAction;
import it.uniba.di.cdg.econference.planningpoker.actions.ImportBacklogFromCDEAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModelListener;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModelListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.utils.AutoResizeTableLayout;
import it.uniba.di.cdg.econference.planningpoker.utils.XMLUtils;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.EConferencePlugin;
import it.uniba.di.cdg.xcore.econference.IEConferenceHelper;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.m2m.model.IParticipant.Role;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

public class BacklogView extends ViewPart implements IBacklogView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.views.StoriesListView";

	private TableViewer viewer;
	private MenuManager  menuMgr;
	
	protected Button startStopButton = null;
	
	private static final String DEFAULT_LOG_FILENAME = "backlog.xml";

	private EditUserStoryAction editStoryAction;
	private DeleteUserStoryAction deleteStoryAction;
	private AddUserStoryAction addStoryAction;
	private EstimateStoryAction estimateStoryAction;
	private ImportBacklogAction importBacklogAction;
	private ExportBacklogAction exportBacklogAction;
	private ImportBacklogFromCDEAction importBacklogFromCDEAction;

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
		@SwtAsyncExec
		public void statusChanged() {
			//System.out.println( "statusChanged()" );
			changeButtonStatus( getModel().getStatus() );
			if(getModel().getStatus().equals(STARTED)){
				if(!isReadOnly()){   
					estimateStoryAction.setAccessible(true);
					enableDoubleClickListener(true);
				}
			}else{ 
				estimateStoryAction.setAccessible(false);
				enableDoubleClickListener(false);  
				getManager().getService().notifyCurrentAgendaItemChanged(String.valueOf(IItemList.NO_ITEM_SELECTED));
			}
		}
        
        public void backlogChanged() {
        	 System.out.println( "itemListChanged()" );
        	 getModel().getBacklog().addListener(backlogListener);
             changeItemList( getModel().getBacklog() );  
        };
        
        public void whiteBoardChanged() {
        	int currItemIndex = getModel().getBacklog().getCurrentItemIndex();
        	if(currItemIndex!=IItemList.NO_ITEM_SELECTED){
        		IUserStory story = (IUserStory) getModel().getBacklog().getUserStory(currItemIndex);	
        		story.setNotes(getModel().getWhiteBoardText());
        	}
        };
    };
    

	private IBacklogListener backlogListener = new IBacklogListenerAdapter() {
		
		@SwtAsyncExec
		@Override
		public void currentSelectionChanged( int currItemIndex ) {
			setCurrentStory(currItemIndex);
			setWhiteBoardText(currItemIndex);
		}

		@Override
		public void contentChanged(Backlog newBacklog) {
			changeItemList(newBacklog);	
			
		}
	};

	


	public BacklogView() {
	}

	
	protected void setWhiteBoardText(int currItemIndex) {		
		if(!isReadOnly()){
			if(currItemIndex!=IItemList.NO_ITEM_SELECTED){				
				IUserStory story = (IUserStory) getModel().getBacklog().getUserStory(currItemIndex);	
				getManager().notifyWhiteBoardChanged(story.getNotes());	
			}else{
				getManager().notifyWhiteBoardChanged("");				
			}
		}		
	}

	private void makeActions(){
		editStoryAction = new EditUserStoryAction(this);
		deleteStoryAction = new DeleteUserStoryAction(this);
		addStoryAction = new AddUserStoryAction(this);		
		estimateStoryAction = new EstimateStoryAction(this);
		estimateStoryAction.setAccessible(false);
		
		importBacklogAction = new ImportBacklogAction(this);
		exportBacklogAction = new ExportBacklogAction(this);
		
		importBacklogFromCDEAction = new ImportBacklogFromCDEAction(this);
	}
	
    /**
     * Add actions to the action and menu bars (local to this view).
     * @param bars
     */
    protected void contributeToActionBars( IActionBars bars ) {
        bars.getToolBarManager().add( exportBacklogAction );
        bars.getToolBarManager().add( importBacklogAction );
        bars.getToolBarManager().add(importBacklogFromCDEAction);
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
        startStopButton.setText("Start meeting");
        startStopButton.setLayoutData(gridData);
        startStopButton.setEnabled(false);
        
        GridData gridDataTable = new org.eclipse.swt.layout.GridData();
        gridDataTable.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridDataTable.grabExcessHorizontalSpace = true;
        gridDataTable.grabExcessVerticalSpace = true;
        gridDataTable.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        
		viewer = new TableViewer(top, SWT.FULL_SELECTION | SWT.BORDER );	
		viewer.getTable().setLayoutData(gridDataTable);
		
		//Adding the auto-resize layout
		AutoResizeTableLayout layout = new AutoResizeTableLayout(viewer.getTable());
		viewer.getTable().setLayout(layout);
		
		getSite().setSelectionProvider(viewer);		
		        
		createContextMenu();
		makeActions();
		
		contributeToActionBars( getViewSite().getActionBars() ); 
		
		
		startStopButton.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent e ) {
                if (STARTED.equals( getModel().getStatus())) {                	
                	getManager().setStatus( STOPPED );                    
                } else {                 	
                	getManager().setStatus( STARTED );
                }
            }
        });
	}


	 @SwtAsyncExec
    private void changeButtonStatus( ConferenceStatus status ) {
        if (STARTED.equals( status )) {
        	startStopButton.setText( "Stop meeting" );
            startStopButton.setToolTipText( "Press to stop the meeting" );
            startStopButton.setSelection(true);           
        }
        else {
            startStopButton.setText( "Start meeting" );
            startStopButton.setSelection(false);
            startStopButton.setToolTipText( "Press to start the meeting" );         
        }
    }


	public void setManager(IPlanningPokerManager manager) {
		// Switching conference, so deregister from previous manager ...
		if (this.manager != null) {
			getModel().removeListener( ppModelListener );
			getModel().getBacklog().removeListener( backlogListener );
		}
		this.manager = manager;

		IBacklogViewUIProvider provider = PlanningPokerPlugin.getDefault().getHelper().getBacklogViewUIProvider();
		viewer.setContentProvider(provider);
		//viewer.setLabelProvider(provider);
		provider.createColumns(viewer);
		
		
		getModel().addListener( ppModelListener );
		//The backlog could be null if no backlog was specified in the PP XML file        
		getModel().getBacklog().addListener( backlogListener );

		changeItemList( getModel().getBacklog() );		
		updateActionsAccordingToRole();

	}

	@SwtAsyncExec
	private void changeItemList(Backlog backlog) {		
		setCurrentStory(backlog.getCurrentItemIndex());
		viewer.setInput(backlog.getUserStories());
		refreshView();
	}
	
	
	 /**
     * Refresh the whole view. 
     */
    @SwtAsyncExec
    private void refreshView() {
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
		importBacklogAction.setEnabled(!readOnly);
		exportBacklogAction.setEnabled(!readOnly);
		importBacklogFromCDEAction.setEnabled(!readOnly);
		
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
		if(getManager()!=null && getManager().getService()!=null)
			return getManager().getService().getModel();
		else
			return null;
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
	
	public void doSave( IProgressMonitor monitor ) {

        String fileName = UiPlugin.getUIHelper().requestFileNameForSaving( "*.xml" );
        if (fileName == null)
            return;

        writeBacklogContent( monitor, fileName );
    }


	private void writeBacklogContent(IProgressMonitor monitor, String fileName) throws IllegalArgumentException {
		if(getModel()!=null && getModel().getBacklog()!=null){
			String textToSave = XMLUtils.convertDefaultBacklogToStandardXML(getModel().getBacklog());

			monitor.beginTask( "Saving backlog ...", 1 );

			OutputStreamWriter osw = null;
			try {
				osw = new OutputStreamWriter( new FileOutputStream( fileName ) );

				osw.write( textToSave );
				monitor.worked( 1 );

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (osw != null)
						osw.close();
				} catch (IOException e) { 
				}
				monitor.done();
			}
		}

	}


	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub		
	}


	@Override
	public boolean isDirty() {
		return true;
	}


	@Override
	public boolean isSaveAsAllowed() {		
		return true;
	}


	@Override
	public boolean isSaveOnCloseNeeded() {
		  try {
	            IEConferenceHelper helper = EConferencePlugin.getDefault().getHelper();
	            if (helper.getBooleanPreference( IEConferenceHelper.AUTO_SAVE_LOGS ) == true) {
	                System.out.println( "autosave decisions log" );
	                String defaultDir = helper
	                        .getStringPreference( IEConferenceHelper.AUTO_SAVE_LOGS_DIR )
	                        + File.separator
	                        // FIXME calling getName on context returns NULL!!!!!
	                        + manager.getService().getContext().getRoom() + File.separator;
	                File targetDir = new File( defaultDir );
	                if (!targetDir.exists()) {
	                    if (!targetDir.mkdirs()) {
	                        throw new IOException( "Unable to create target dir: "
	                                + targetDir.getAbsolutePath() );
	                    }
	                }
	                System.out.println( "saving " + targetDir );
	                File targetFile = new File( defaultDir, DEFAULT_LOG_FILENAME );
	                if (!targetFile.exists()) {
	                    if (!targetFile.createNewFile()) {
	                        throw new IOException( "Unable to create target file: "
	                                + targetFile.getAbsoluteFile() );
	                    }
	                }
	                writeBacklogContent( new NullProgressMonitor(), defaultDir + DEFAULT_LOG_FILENAME );
	                return false;
	            } else {
	                System.out.println( "must save decisions log manually" );
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println( "must save decisions log manually" );
	            return true;
	        }
	}


	public TableViewer getViewer() {
		return viewer;
	}


	@Override
	public void refresh() {
		// empty method
	}


}
