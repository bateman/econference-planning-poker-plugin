package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogListener;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;



public class IBacklogTest extends MockObjectTestCase {
	
	private Backlog backlog;
	
	private DefaultUserStory story1;
	private DefaultUserStory story2;
		// TODO Auto-generated constructor

	protected void setUp() throws Exception {
		this.backlog = new Backlog();
		 
		story1 = new DefaultUserStory("id1","story1","Closed","asfdasd","8");
		story2 = new DefaultUserStory("id2","story2","Unknown","fasdfad","Unknown");
	}

	protected void tearDown() throws Exception {
	}

	public void testRemoveUserStory() {
		  	Mock mock = mock( IItemListListener.class );
		  	mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );	        
	        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        //mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        IBacklogListener listener = (IBacklogListener) mock.proxy();

	        backlog.addListener( listener );
	        
	        assertEquals( 0, backlog.size() ); // Ensure it is empty
	        
	        backlog.addUserStory( story1 );
	        backlog.addUserStory( story2 );
	        
	        assertEquals( 2, backlog.size() );
	        
	        backlog.removeUserStory(story1);
	        
	        assertEquals(1, backlog.size());
	        
	        assertEquals(backlog.getUserStory(0), story2);
	        
	}

	public void testAddItemObject() {
        Mock mock = mock( IItemListListener.class );
        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
        IBacklogListener listener = (IBacklogListener) mock.proxy();

        backlog.addListener( listener );
        
        assertEquals( 0, backlog.size() ); // Ensure it is empty
        
        backlog.addUserStory( story1 );
        backlog.addUserStory( story2 );
        
        assertEquals( 2, backlog.size() );

        IUserStory[] items = new IUserStory[] { story1, story2 };

        int i = 0;
        for (int j=0; j<backlog.size(); j++)
            assertEquals( items[i++], (IUserStory)backlog.getUserStory(j) );
    
	}

	public void testRemoveItem() {
		  	Mock mock = mock( IItemListListener.class );
	        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        mock.expects( once() ).method( "contentChanged" ).with( eq( backlog ) );
	        IBacklogListener listener = (IBacklogListener) mock.proxy();

	        backlog.addListener( listener );
	        
	        assertEquals( 0, backlog.size() ); // Ensure it is empty
	        
	        backlog.addUserStory( story1 );
	        backlog.addUserStory( story2 );
	        
	        assertEquals( 2, backlog.size() );
	        
	        backlog.removeItem(0);
	        
	        assertEquals(1, backlog.size());
	        
	        assertEquals(backlog.getUserStory(0), story2);
	       
	}

	public void testSetCurrentItemIndex() {
		assertEquals( IItemList.NO_ITEM_SELECTED, backlog.getCurrentItemIndex() );
       

        Mock mock = mock( IItemListListener.class );
        mock.stubs().method( "contentChanged" ); // Don't bother 
        mock.expects( once() ).method( "currentSelectionChanged" ).with( eq( 0 ) );
        IBacklogListener listener = (IBacklogListener) mock.proxy();
        
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
	}

}
