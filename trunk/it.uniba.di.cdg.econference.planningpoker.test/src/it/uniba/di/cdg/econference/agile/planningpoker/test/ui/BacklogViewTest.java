package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.ui.DefaultBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.BacklogView;

import java.util.Calendar;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

public class BacklogViewTest extends TestCase
{

	private BacklogView testView;

	/**
	 * Construct new test instance.
	 *
	 * @param name the test name
	 */
	public BacklogViewTest(String name) {
		super(name);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		// Initialize the test fixture for each test
		// that is run.
		waitForJobs();

		testView =  (BacklogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView( BacklogView.ID );
				
		testView.setReadOnly(false);

		DefaultBacklogViewUIProvider provider = new DefaultBacklogViewUIProvider();
		TableViewer viewer = testView.getViewer();
		viewer.setContentProvider(provider);
		//viewer.setLabelProvider(provider);
		provider.createColumns(viewer);
		
		// Delay for 3 seconds so that
	      // the Backlog view can be seen.
	      waitForJobs();
	      delay(3000);

		// Add additional setup code here.
	}

	/**
	 * Perform post-test cleanup.
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		// Dispose of test fixture.
		waitForJobs();

		// Add additional teardown code here.
	}
	
	
	/**
	 * Run the view test.
	 */
	@Test
	public void testView() {

		TableViewer viewer = testView.getViewer();
		Backlog backlog = new Backlog();		
		IUserStory us1 = new DefaultUserStory("01", "2", "Backlog", Calendar.getInstance().getTime(), "Text 1", "Notes 1", "2");
		backlog.addUserStory(us1);
		
		viewer.setInput(backlog.getUserStories());
		Object[] expectedContent =
			new Object[] { us1 };
		Object[] expectedLabels =
			new String[] { "Text 1", "Backlog", "2" };

		// Assert valid content.
		IStructuredContentProvider contentProvider =
			(IStructuredContentProvider)
			viewer.getContentProvider();
		assertEquals(expectedContent,
				contentProvider.getElements(viewer.getInput()));

		// Assert valid labels.
		ITableLabelProvider labelProvider =
			(ITableLabelProvider) viewer.getLabelProvider();
		for (int i = 0; i < expectedLabels.length; i++)
			assertEquals(expectedLabels[i],
					labelProvider.getColumnText(expectedContent[0], i));
	}

	/**
	 * Process UI input but do not return for the
	 * specified time interval.
	 *
	 * @param waitTimeMillis the number of milliseconds
	 */
	private void delay(long waitTimeMillis) {
		Display display = Display.getCurrent();

		// If this is the UI thread,
		// then process input.
		if (display != null) {
			long endTimeMillis =
				System.currentTimeMillis() + waitTimeMillis;
			while (System.currentTimeMillis() < endTimeMillis)
			{
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.update();
		}
		// Otherwise, perform a simple sleep.
		else {
			try {
				Thread.sleep(waitTimeMillis);
			}
			catch (InterruptedException e) {
				// Ignored.
			}
		}
	}
	/**
	 * Wait until all background tasks are complete.
	 */
	public void waitForJobs() {
		while (Job.getJobManager().currentJob() != null)
			delay(1000);
	}

	/**
	 * Assert that the two arrays are equal.
	 * Throw an AssertionException if they are not.
	 *
	 * @param expected first array
	 * @param actual second array
	 */
	private void assertEquals(Object[] expected, Object[] actual) {
		if (expected == null) {
			if (actual == null)
				return;
			throw new AssertionFailedError(
					"expected is null, but actual is not");
		}
		else {
			if (actual == null)
				throw new AssertionFailedError(
						"actual is null, but expected is not");
		}

		assertEquals(
				"expected.length "
				+ expected.length
				+ ", but actual.length "
				+ actual.length,
				expected.length,
				actual.length);

		for (int i = 0; i < actual.length; i++)
			assertEquals(
					"expected[" + i +
					"] is not equal to actual[" +
					i + "]",
					expected[i],
					actual[i]);
	}
}


