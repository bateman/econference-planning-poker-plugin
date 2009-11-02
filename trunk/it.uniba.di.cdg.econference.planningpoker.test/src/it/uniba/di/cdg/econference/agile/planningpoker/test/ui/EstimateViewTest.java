package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;


import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.ui.DefaultEstimatesViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.workbench.EstimatesView;
import it.uniba.di.cdg.xcore.multichat.model.Participant;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
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
public class EstimateViewTest extends TestCase
{
	private IViewPart viewPart;

	private EstimatesView estimatesView;

   /**
    * Construct new test instance.
    *
    * @param name the test name
    */
   public EstimateViewTest(String name) {
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
     
        viewPart =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView( EstimatesView.ID );
        estimatesView = (EstimatesView) viewPart;   
        
        estimatesView.setReadOnly(false);
        
        DefaultEstimatesViewUIProvider provider = new DefaultEstimatesViewUIProvider();
        TableViewer viewer = estimatesView.getViewer();
        viewer.setContentProvider(provider);
        viewer.setLabelProvider(provider);
        provider.createColumns(viewer);
      

      // Delay for 3 seconds so that
      // the Estimate view can be seen.
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
      //PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(viewPart);

      // Add additional teardown code here.
   }
   /**
    * Run the view test.
    */
   public void testView() {
	   
      TableViewer viewer = estimatesView.getViewer();
      Object[] content = new Object[2];
      content[0]= new Participant(null, "Alex", "Anna");
      content[1]= new IPokerCard() {
		
		@Override
		public void setValue(Object value) {		
		}
		
		@Override
		public void setImagePath(String path) {		
		}
		
		@Override
		public boolean hasImagePath() {
			return false;
		}
		
		@Override
		public Object getValue() {
			return "2";
		}
		
		@Override
		public String getStringValue() {
			return "2";
		}
		
		@Override
		public String getImagePath() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
		
      viewer.setInput(content);
      Object[] expectedContent = content;
      Object[] expectedLabels =
         new String[] { "Anna", "2" };
      
      waitForJobs();
      delay(10000);

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
            labelProvider.getColumnText(expectedContent[i], 1));
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
         delay(8000);
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


