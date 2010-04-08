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
package it.uniba.di.cdg.econference.planningpoker.usimport.wizard;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SaveAsWizard extends WizardPage implements MouseListener{

	public static Text saveText;
	private Button browse;
	private String filenameText;
    private String[] extensions = { "*.xml", "*.XML" };
    public static String filename;

	
	public SaveAsWizard() {
		super("SaveAs");
		setTitle("Save results");
		setDescription("Enter file name to save results");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		new Label(composite, SWT.LEFT).setText("File name:");
		saveText=new Text(composite,SWT.BORDER);
		saveText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Font f=new Font(composite.getDisplay(), "Arial", 9, SWT.ITALIC);
		saveText.setFont(f);
		saveText.setText("Enter file name...");
		saveText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {

				filenameText = saveText.getText();
				if (filenameText.length()==0)
					setErrorMessage("Type file name is empty");
				else setErrorMessage(null);
				setPageComplete(filenameText.length()>0);

			}
		});
		
		saveText.addMouseListener(this);
		
		browse=new Button(composite,SWT.PUSH);
		browse.setText("Browse...");
		browse.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				String path = null;
		    	String fileName;
		    	
		        FileDialog dialog = new FileDialog(new Shell(), SWT.SAVE);      
		         dialog.setFilterPath("/");
		        dialog.setFilterExtensions(extensions);

		        if (dialog.open() != null) {
		            path = dialog.getFilterPath();

		            fileName = dialog.getFileName();
		            String compleFileName =  path+File.separator + fileName;
		            getFile(compleFileName);
			}
			}
		});
		setControl(composite);
		
	}
	
	 private void getFile(String completeFilename) {
		 HandleWizard wizard = (HandleWizard)getWizard();
	        try {
	            File file = new File(completeFilename);
	            if (file.exists()) {
	                MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.CANCEL | SWT.ICON_WARNING);
	                messageBox.setMessage("File already exists, \n Do you want to owerwrite it?");
	                messageBox.setText("Save As");
	                if (messageBox.open() != SWT.OK) {
	                    return;
	                }
	                else {
	                    file.delete();
			            saveText.setText(completeFilename);
			            
			            wizard.setFileName(completeFilename);
	                }
	            }
	            else {
		            saveText.setText(completeFilename);
		            wizard.setFileName(completeFilename);
	            }
	        }
	        catch (Exception e) {
	        }
	    }
	

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseUp(MouseEvent e) {
		saveText.selectAll();
		
	}

}
