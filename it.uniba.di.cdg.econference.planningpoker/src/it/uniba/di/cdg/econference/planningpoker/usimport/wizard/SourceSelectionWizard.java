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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SourceSelectionWizard extends WizardPage{

	public Button assembla;
	public Button github;
	public Button googlecode;
	public Button trac;
	public static boolean assemblaSelected=false;
	public static boolean githubSelected=false;
	public SourceSelectionWizard(){
		super("SourceSelection");
		setTitle("CDE Selection");
		setDescription("Select CDE you want to import tickets");
	}


	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);

		new Label(composite, SWT.LEFT).setText("Select the source");

		Composite selection = new Composite(composite, SWT.NONE);
		FillLayout f=new FillLayout(SWT.VERTICAL);
		selection.setLayout(f);

		assembla = new Button(selection, SWT.RADIO);
		assembla.setText("Assembla");



		github = new Button(selection, SWT.RADIO);
		github.setText("Github");



		googlecode = new Button(selection, SWT.RADIO);
		googlecode.setText("GoogleCode");

		trac = new Button(selection, SWT.RADIO);
		trac.setText("Trac");

		Text otherText = new Text(selection,SWT.BORDER);
		otherText.setVisible(false);
		
		setControl(composite);
	}


	public IWizardPage getNextPage() {
		if (assembla.getSelection()){

			return getWizard().getPage("Assembla");
		}
		else 
			if (github.getSelection()){

				return getWizard().getPage("GithubFirst");

			}
			else 
				if (googlecode.getSelection()) return getWizard().getPage("GoogleCodeFirst");
				else 
					if (trac.getSelection()) return getWizard().getPage("TracFirst");
		return super.getNextPage();
	}
}
