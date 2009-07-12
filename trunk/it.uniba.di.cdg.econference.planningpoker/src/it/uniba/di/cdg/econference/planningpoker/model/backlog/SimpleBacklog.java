package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.StoryPoints;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.SimpleUserStory.PRIORITY;
import it.uniba.di.cdg.xcore.econference.model.IDiscussionItem;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class SimpleBacklog implements IBacklog {


	private Set<IItemListListener> listeners;
	
	private List<SimpleUserStory> stories;

	private int current;

	
	public SimpleBacklog() {
		 this(NO_ITEM_SELECTED);
	}
	
	public SimpleBacklog(int initialIndex) {
		 current = initialIndex;
		 listeners  = new HashSet<IItemListListener>();
		 stories = new ArrayList<SimpleUserStory>();
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		SimpleUserStory story = (SimpleUserStory)element;
		String label ="";
		switch(columnIndex){
		case 0: // Name 
			label = story.getName();
		break;
		case 1: {//PRIORITY
			SimpleUserStory.PRIORITY priority = story.getPriority();
			if(priority == SimpleUserStory.PRIORITY.HIGH){
				label = "High";
			}else if(priority == SimpleUserStory.PRIORITY.MEDIUM){
				label = "Medium";
			}else if(priority == SimpleUserStory.PRIORITY.LOW){
				label = "Low";
			}else if(priority == SimpleUserStory.PRIORITY.UNKNOWN){
				label = "Unknow";
			}
		}
		break;
		case 2:{ //Description
			label = story.getDescription();
		}
		break;
		case 3:{
			StoryPoints estimate = story.getEstimate();
			if(estimate==StoryPoints.UNKNOW)
				label = "Unknow";
			else
				label = String.valueOf(estimate.getPoints());
		}			
		}
		return label;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public IUserStory[] getUserStories() {
		IUserStory[] result = new IUserStory[stories.size()];
		for (int i = 0; i < stories.size(); i++) {
			result[i] = stories.get(i);
		}
		return result;
	}


	@Override
	public void createColumns(TableViewer viewer) {
		String[] titles = new String[] {"Name","Priority","Description","Estimate"};
		int[] bounds = new int[] {50,50,200,50};

		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			//column.setEditingSupport(new SimpleBacklogEditingSupport(viewer,i));
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
	}
	
	@Override
	public IUserStoryDialog getDialog(Shell shell) {
		return new SimpleUserStoryDialog(shell);
	}



	public void initializeTestData() {
		addItem(new SimpleUserStory("Load Backlog", PRIORITY.MEDIUM,
				"As a Product Owner " +
				"I would like to load a list of stories in the " +
				"application as the Backlog so that I can estabilish" +
				" which user stories should be estimate in the " +
				"meeting", StoryPoints.UNKNOW));
		addItem(new SimpleUserStory("Start Planning Poker Session", 
				PRIORITY.HIGH,
				"As a moderator I would like to start a new " +
				"Planning Poker session so that all participants " +
				"can join in", StoryPoints.UNKNOW));
		
	}


	@Override
	public void addItem(Object item) {
		stories.add((SimpleUserStory) item);
		 for (IItemListListener l : listeners)
	            l.contentChanged( this );
	}

	@Override
	public void addItem(String itemText) {
		// TODO Auto-generated method stub
		throw new IllegalArgumentException("Cannot add a String item to the Backlog");
	}
	
	@Override
	public void setBacklogContent(IUserStory[] stories) {
		this.stories.clear();
		for(IUserStory story : stories)
			this.stories.add((SimpleUserStory) story);
		for (IItemListListener l : listeners)
            l.contentChanged( this );		
	}

	@Override
	public void addListener(IItemListListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void decode(String encodedItems) {
		// TODO Inserire il metodo per decodificare le User Story in arrivo
		//Vedere metdodo decode della classe it.uniba.di.cdg.xcore.econference.model.internal.ItemList
	}

	@Override
	public String encode() {
		// TODO Inserire il metodo per codificare le User Story presenti nel backlog
		//Vedere metodo encode della classe it.uniba.di.cdg.xcore.econference.model.internal.ItemList
		return null;
	}

	@Override
	public int getCurrentItemIndex() {
		return current;
	}

	@Override
	public Object getItem(int itemIndex) {
		return stories.get(itemIndex);
	}

	@Override
	public void removeUserStory(IUserStory story) {
		stories.remove(story);
		
		for (IItemListListener l : listeners)
            l.contentChanged(this);
		
	}
	
	@Override
	public void removeItem(int itemIndex) {
		IUserStory item = stories.get( itemIndex );       
		removeUserStory(item);
        
	}

	@Override
	public void removeListener(IItemListListener listener) {
		listeners.remove(listener);
		
	}

	@Override
	public void setCurrentItemIndex(int itemIndex) {
		if (itemIndex >= size() || itemIndex < -1)
            throw new IllegalArgumentException( "itemIndex out of range" );
        this.current = itemIndex;
        
        for (IItemListListener l : listeners)
            l.currentSelectionChanged( current );
		
	}

	@Override
	public int size() {		
		return stories.size();
	}






}
