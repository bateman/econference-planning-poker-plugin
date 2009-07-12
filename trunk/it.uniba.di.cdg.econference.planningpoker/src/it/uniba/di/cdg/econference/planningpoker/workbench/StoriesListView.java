package it.uniba.di.cdg.econference.planningpoker.workbench;

import java.awt.MenuItem;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;
import it.uniba.di.cdg.econference.planningpoker.actions.AddUserStoryActionDelegate;
import it.uniba.di.cdg.econference.planningpoker.actions.DeleteUserStoryActionDelegate;
import it.uniba.di.cdg.econference.planningpoker.actions.EditUserStoryActionDelegate;
import it.uniba.di.cdg.econference.planningpoker.actions.EstimateStoryActionDelegate;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.BacklogListenerAdapter;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleFactory;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

public class StoriesListView extends ViewPart implements IStoriesListView {
	
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.views.StoriesListView";
	
	private TableViewer viewer;
	private IBacklog backlog;
	private MenuManager  menuMgr;

	private IPlanningPokerManager manager;

	
	public StoriesListView() {
	}
	
	
	    

	private void createContextMenu(){		
		menuMgr = new MenuManager("StoriesListViewActionPopup");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener(){
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(new Separator("content"));
				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));			
			}		
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);	
		getSite().registerContextMenu(menuMgr, viewer);		
	}
	

	@Override
	public void createPartControl(Composite parent) {	
			
		//FIXME: remove the following line
		//((SimpleBacklog)getModel().getBacklog()).initializeTestData();			
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);		
		getSite().setSelectionProvider(viewer);		
		createContextMenu();
		//refreshBacklogContent();
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

	private void refreshBacklogContent() {
		viewer.setInput(getModel().getBacklog().getUserStories());
		viewer.refresh();
	}
	
	
	public IPlanningPokerManager getManager(){
		return manager;
	}
	
	public IPlanningPokerModel getModel(){
		return getManager().getService().getModel();
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public TableViewer getViewer() {
		return viewer;
	}
	
	 private IItemListListener backlogListener = new BacklogListenerAdapter() {
	        @SwtAsyncExec
	        @Override
	        public void currentSelectionChanged( int currItemIndex ) {
	            //getManager().notifyCurrentAgendaItemChanged( Integer.toString( currItemIndex ) );
	            viewer.getTable().setSelection( currItemIndex );
	        }
	        
	        @Override
	    	public void contentChanged(IItemList newBacklog) {
	    		changeItemList((IBacklog) newBacklog);
	    	}
	    };
	    

	public void setManager(IPlanningPokerManager manager) {
		 // Switching conference, so deregister from previous manager ...
        if (this.manager != null) {
            //getModel().removeListener( conferenceModelListener );
            getModel().getBacklog().removeListener( backlogListener );
        }
        this.manager = manager;
        
        backlog = getModel().getBacklog();
		backlog.createColumns(viewer);
		viewer.setContentProvider(backlog);
		viewer.setLabelProvider(backlog);
        //getModel().addListener( conferenceModelListener );
        //The backlog could be null if no backlog was specified in the PP XML file        
        getModel().getBacklog().addListener( backlogListener );

        changeItemList( getModel().getBacklog() );
        //changeButtonStatus( getModel().getStatus() );
        updateActionsAccordingToRole();
		
	}

	private void updateActionsAccordingToRole() {
		setReadOnly(getManager() != null && // There is a manager
		    Role.MODERATOR.equals( getModel().getLocalUser().getRole() ) // and we are moderators 
			         );
		
	}

	private void changeItemList(IBacklog backlog) {
		this.backlog = backlog;
		refreshBacklogContent();
	}

	@Override
	public boolean isReadOnly() {
		return !viewer.getTable().isEnabled();
	}

	@Override
	 @SwtAsyncExec
	public void setReadOnly(boolean readOnly) {	
		viewer.getControl().getMenu().setVisible(!readOnly);
		}



}
