package it.uniba.di.cdg.econference.planningpoker.views;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogAbstractFactory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleBacklog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleFactory;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.ui.views.IAgendaView;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

public class StoriesListView extends ViewPart implements IAgendaView {
	
	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.views.StoriesListView";
	
	private TableViewer viewer;
	private IBacklogAbstractFactory backlogFactory;	
	private IBacklog backlog;

	private IEConferenceManager manager;

	
	public StoriesListView() {
	}
	
	
	public void setBacklogFactory(IBacklogAbstractFactory factory){
		this.backlogFactory = factory;
	}
	
	public IBacklogAbstractFactory getBacklogFactory(){
		return backlogFactory;
	}
	
	public IBacklog getBacklog(){
		return backlog;
	}
		
		
	
	private void createContextMenu(){		
		MenuManager menuMgr = new MenuManager("StoriesListViewActionPopup");
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
		/* TODO The abstract factory is initialized with the Simple 
		 * Factory which is the base factory
		 */		
		backlogFactory = new SimpleFactory();
		backlog = backlogFactory.createBacklog();
		//FIXME: remove the following line
		((SimpleBacklog)backlog).initializeTestData();			
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		createContextMenu();
		backlog.createColumns(viewer);
		getSite().setSelectionProvider(viewer);
		viewer.setContentProvider(backlog);
		viewer.setLabelProvider(backlog);
		refreshBacklogContent();

	}

	public void refreshBacklogContent() {
		viewer.setInput(backlog.getUserStories());
		viewer.refresh();
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	@Override
	public IEConferenceManager getManager() {
		return manager;
	}

	@Override
	public void setManager(IEConferenceManager manager) {
		this.manager = manager;
		
	}

	@Override
	public boolean isReadOnly() {
		return !viewer.getTable().isEnabled();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		viewer.getTable().setEnabled(!readOnly);		
	}


}
