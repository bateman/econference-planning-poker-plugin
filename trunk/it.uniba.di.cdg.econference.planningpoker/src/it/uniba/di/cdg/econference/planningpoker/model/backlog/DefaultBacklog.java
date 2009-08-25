package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory.PRIORITY;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultBacklog implements IBacklog {


	private Set<IItemListListener> listeners;
	
	private List<DefaultUserStory> stories;

	private int current;

	
	public DefaultBacklog() {
		 this(NO_ITEM_SELECTED);
	}
	
	public DefaultBacklog(int initialIndex) {
		 current = initialIndex;
		 listeners  = new HashSet<IItemListListener>();
		 stories = new ArrayList<DefaultUserStory>();
	}
	
	


	@Override
	public IUserStory[] getUserStories() {
		IUserStory[] result = new IUserStory[stories.size()];
		for (int i = 0; i < stories.size(); i++) {
			result[i] = stories.get(i);
		}
		return result;
	}


	public void initializeTestData() {
		addItem(new DefaultUserStory("Load Backlog", PRIORITY.MEDIUM,
				"As a Product Owner " +
				"I would like to load a list of stories in the " +
				"application as the Backlog so that I can estabilish" +
				" which user stories should be estimate in the " +
				"meeting", "UNKNOW"));
		addItem(new DefaultUserStory("Start Planning Poker Session", 
				PRIORITY.HIGH,
				"As a moderator I would like to start a new " +
				"Planning Poker session so that all participants " +
				"can join in", "UNKNOW"));
		
	}


	@Override
	public void addItem(Object item) {
		stories.add((DefaultUserStory) item);
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
			this.stories.add((DefaultUserStory) story);
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
