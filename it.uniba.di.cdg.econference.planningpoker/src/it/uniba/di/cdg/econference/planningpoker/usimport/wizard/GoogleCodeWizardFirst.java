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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GoogleCodeWizardFirst extends WizardPage implements MouseListener{

	public static Text projectNameText;
	String name="";

	public GoogleCodeWizardFirst(){
		super("GoogleCodeFirst");
		setTitle("GoogleCode project name");
		setDescription("Enter project name in GoogleCode");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(2,false);
		composite.setLayout(g);


		new Label(composite, SWT.LEFT).setText("Project name:");

		projectNameText=new Text(composite,SWT.LEFT|SWT.BORDER);
		projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Font f=new Font(composite.getDisplay(), "Arial", 9, SWT.ITALIC);
		projectNameText.setFont(f);
		projectNameText.setText("enter project name...");

		projectNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				name = projectNameText.getText();
				if (name.length()==0)
					setErrorMessage("Type project name is empty");
				else setErrorMessage(null);
				setPageComplete(name.length()>0);
			}
		});

		projectNameText.addMouseListener(this);
		setControl(composite);
	}

	public IWizardPage getNextPage() {


		return super.getNextPage();  

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
		projectNameText.selectAll();		
	}


}
