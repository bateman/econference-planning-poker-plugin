package it.uniba.di.cdg.econference.planningpoker.dialogs;

import static it.uniba.di.cdg.xcore.util.Misc.isEmpty;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoadBacklogDialog extends Dialog {

	public static final String[] FILE_EXT = new String[] { "*.ecx","*.xml" };
	private Text selectedFileNameText;
	private String fileName;


	public LoadBacklogDialog(Shell parent) {
		super(parent);		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("Load new Backlog");
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		  Composite composite = (Composite) super.createDialogArea( parent );

	        FillLayout layout = new FillLayout( SWT.VERTICAL );
	        composite.setLayout( layout );
	        
	        GridData gridData11 = new org.eclipse.swt.layout.GridData();
	        gridData11.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData11.grabExcessHorizontalSpace = true;
	        gridData11.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridData gridData1 = new org.eclipse.swt.layout.GridData();
	        gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData1.grabExcessHorizontalSpace = true;
	        gridData1.grabExcessVerticalSpace = true;
	        gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridData gridData = new org.eclipse.swt.layout.GridData();
	        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridLayout gridLayout = new GridLayout();
	        gridLayout.numColumns = 3;
	        gridLayout.horizontalSpacing = 5;
	        gridLayout.marginWidth = 1;
	        gridLayout.marginHeight = 1;
	        gridLayout.verticalSpacing = 2;
	        Composite fileNameComposite = new Composite( composite, SWT.NONE );
	        fileNameComposite.setLayout(gridLayout);
	        CLabel labelFileName = new CLabel(fileNameComposite, SWT.NONE);
	        labelFileName.setText("Filename:");
	        labelFileName.setLayoutData(gridData);
	        selectedFileNameText = new Text(fileNameComposite, SWT.BORDER | SWT.READ_ONLY);
	        selectedFileNameText.setLayoutData(gridData1);
	        Button selectFileButton = new Button(fileNameComposite, SWT.NONE);
	        selectFileButton.setText("Select ...");
	        selectFileButton.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
	            public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
	                final String fileName = UiPlugin.getUIHelper().requestFile( FILE_EXT );
	                if (fileName == null) // CANCEL pressed
	                    return;	                
	                try {	                	
	                	selectedFileNameText.setText( fileName );	                	                	
	                    
	                } catch (Exception ex) {
	                    UiPlugin.getUIHelper().showErrorMessage( "Sorry, the specified file seems invalid: " 
	                            + ex.getMessage() );
	                    ex.printStackTrace();
	                    return;
	                } 
	            }
	        } );     

	        return composite;
	}
	
    @Override
    protected void okPressed() {
        fileName = selectedFileNameText.getText();        
        if (validate()) {            
            super.okPressed();
        } else {
            UiPlugin.getUIHelper().showErrorMessage( "Please fill all required fields" );
        }
    }

	private boolean validate() {
        return !isEmpty( getFileName() );
	}


	public String getFileName() {
		return fileName;
	}	

}
