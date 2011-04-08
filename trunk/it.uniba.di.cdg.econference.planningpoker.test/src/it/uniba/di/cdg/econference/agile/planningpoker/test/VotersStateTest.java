package it.uniba.di.cdg.econference.agile.planningpoker.test;

import it.uniba.di.cdg.econference.planningpoker.model.estimates.Voters;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VotersStateTest {

	String id1;
	String id2;
	String id3;
	
	@Before
	public void setUp() throws Exception {
		id1 = "id1";
		id2 = "id2";
		id3 = "id3";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddVoter() {
		Voters voters = new Voters();
		IItemListListener listener = mock(IItemListListener.class);
		
		voters.addListener(listener);
		
		voters.addVoter(id1);
		
		assertEquals(1, voters.size());
		verify(listener).itemAdded(id1);
	}

	@Test
	public void testGetVoter() {
		Voters voters = new Voters();
		voters.addVoter(id1);
		voters.addVoter(id2);
		voters.addVoter(id3);
		
		assertEquals(id1, voters.getVoter(0));
		assertEquals(id2, voters.getVoter(1));
		assertEquals(id3, voters.getVoter(2));
	}

	@Test
	public void testRemoveItem() {
		Voters voters = new Voters();
		voters.addVoter(id1);
		voters.addVoter(id2);
		IItemListListener listener = mock(IItemListListener.class);
		
		voters.addListener(listener);
		
		voters.removeVoter(id1);
		
		assertEquals(1, voters.size());
		verify(listener).itemRemoved(id1);
	}

	@Test
	public void testIsVoter() {
		Voters voters = new Voters();
		voters.addVoter(id1);
		voters.addVoter(id3);
		
		assertEquals(false, voters.isVoter("Id1"));
		assertEquals(true, voters.isVoter(id3));
		
		assertEquals(false, voters.isVoter(id2));
	}

}
