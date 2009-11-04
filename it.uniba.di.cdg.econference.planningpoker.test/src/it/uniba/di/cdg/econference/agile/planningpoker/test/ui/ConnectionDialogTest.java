package it.uniba.di.cdg.econference.agile.planningpoker.test.ui;

import it.uniba.di.cdg.xcore.ui.dialogs.ConnectionDialog;
import junit.framework.TestCase;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ConnectionDialogTest extends TestCase{
	
	private ConnectionDialog connDiag;


	
	@Override
	protected void setUp() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
    	
		connDiag = new ConnectionDialog(shell);
	}
	
	
	public void testDialog() {			
		if(connDiag.open() == Dialog.OK){
								
		}else{
			
		}
		
	}	
	
	
//	/**
//	 * Remove 2 cards in the dialog
//	 * 
//	 */
//	public void testDialogEdit(){
//		if(de.show() == Dialog.OK){
//			assertEquals(de.getFinalDeck().getHiddenCards().length, 2);
//			assertEquals(de.getFinalDeck().getCards().length, 7);
//		}
//	}

}
