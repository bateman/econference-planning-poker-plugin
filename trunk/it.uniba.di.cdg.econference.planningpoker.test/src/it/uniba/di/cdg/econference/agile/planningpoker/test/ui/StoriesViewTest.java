package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;

import junit.framework.AssertionFailedError;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
/**
 * The class <code>FavoritesViewTest</code> contains tests
 * for the class {@link
 *    com.qualityeclipse.favorites.views.FavoritesView}.
 *
 * @pattern JUnit Test Case
 * @generatedBy CodePro Studio
 */
public class StoriesViewTest extends TestCase
{
   private static final String VIEW_ID =
      "it.uniba.di.cdg.econference.agile.planningpoker.views.StoriesView";
private IViewPart testView;

   /**
    * Construct new test instance.
    *
    * @param name the test name
    */
   public StoriesViewTest(String name) {
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
      //testView = ()
         PlatformUI
            .getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView(VIEW_ID);

      // Delay for 3 seconds so that
      // the Favorites view can be seen.
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
      PlatformUI
         .getWorkbench()
         .getActiveWorkbenchWindow()
         .getActivePage()
         .hideView(testView);

      // Add additional teardown code here.
   }
   /**
    * Run the view test.
    */
   public void testView() {
	   
    /*  TableViewer viewer = testView.getViewer();
      Object[] expectedContent =
         new Object[] { to1, to2 };
      Object[] expectedLabels =
         new String[] { "Iteration 1", "Iteration 2" };

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
            labelProvider.getColumnText(expectedContent[i], 1));*/
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
      while (Platform.getJobManager().currentJob() != null)
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


