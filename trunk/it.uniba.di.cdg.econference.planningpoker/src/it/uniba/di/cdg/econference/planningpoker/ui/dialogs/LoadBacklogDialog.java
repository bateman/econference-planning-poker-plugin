/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker.ui.dialogs;

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

	public static final String[] FILE_EXT = new String[] {"*.xml", "*.ecx" };
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
	                final String fileName = UiPlugin.getUIHelper().requestFile( FILE_EXT, System.getProperty("user.dir") );
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
