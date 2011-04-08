package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.xcore.econference.model.IItemList;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;



public class BacklogStateTest {
	
	private Backlog backlog;
	
	private DefaultUserStory story1;
	private DefaultUserStory story2;
		// TODO Auto-generated constructor
	@Before
	public void setUp() throws Exception {
		this.backlog = new Backlog();
		 
		story1 = new DefaultUserStory("id1","milestone1", "Milestone 1", Calendar.getInstance().getTime(), "text", "notes", "8");
		story2 = new DefaultUserStory("id2","milestone2", "Milestone 2", Calendar.getInstance().getTime(), "text2", "notes2", "?");
	}
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testRemoveUserStory() {
			IBacklogListener listener = mock( IBacklogListener.class );
	        backlog.addListener( listener );
	        
	        assertEquals( 0, backlog.size() ); // Ensure it is empty
	        
	        backlog.addUserStory( story1 );
	        backlog.addUserStory( story2 );
	        
	        assertEquals( 2, backlog.size() );
	        
	        backlog.removeUserStory(story1);
	        
	        assertEquals(1, backlog.size());
	        
	        assertEquals(backlog.getUserStory(0), story2);
	        verify(listener, times(2)).contentChanged(eq(backlog));
	        verify(listener).itemRemoved(eq(story1));	        
	}

	@Test
	public void testAddItemObject() {
		IBacklogListener listener = mock( IBacklogListener.class );
        backlog.addListener( listener );
        
        assertEquals( 0, backlog.size() ); // Ensure it is empty
        
        backlog.addUserStory( story1 );
        backlog.addUserStory( story2 );
        
        assertEquals( 2, backlog.size() );

        IUserStory[] items = new IUserStory[] { story1, story2 };

        int i = 0;
        for (int j=0; j<backlog.size(); j++)
            assertEquals( items[i++], (IUserStory)backlog.getUserStory(j) );
        verify(listener, times(2)).contentChanged(backlog);    
	}

	@Test
	public void testRemoveItem() {
		IBacklogListener listener = mock(IBacklogListener.class);
		backlog.addListener(listener);

		assertEquals(0, backlog.size()); // Ensure it is empty

		backlog.addUserStory(story1);
		backlog.addUserStory(story2);

		assertEquals(2, backlog.size());

		backlog.removeItem(0);

		assertEquals(1, backlog.size());

		assertEquals(backlog.getUserStory(0), story2);
		verify(listener, times(2)).contentChanged(backlog);
		verify(listener).itemRemoved(story1); 
	}

	@Test
	public void testSetCurrentItemIndex() {
		assertEquals( IItemList.NO_ITEM_SELECTED, backlog.getCurrentItemIndex() );
		IBacklogListener listener = mock( IBacklogListener.class );       
        
        backlog.addListener( listener );

        backlog.addUserStory( story1 );
        backlog.addUserStory( story2 );
        
        backlog.setCurrentItemIndex( 0 );
        
        backlog.removeListener(listener);

       //if I try to set an index out-of-range the index should become -1
        backlog.setCurrentItemIndex( 4 );
        assertEquals(backlog.getCurrentItemIndex(), IItemList.NO_ITEM_SELECTED);
        //fail( "Setting the current item index to an out-of-range item should throw an exception" );
    
    	backlog.setCurrentItemIndex( -2 );
    	assertEquals(backlog.getCurrentItemIndex(), IItemList.NO_ITEM_SELECTED);
              
        backlog.removeItem( 0 );
        verify(listener).currentSelectionChanged(0);
	}

}
