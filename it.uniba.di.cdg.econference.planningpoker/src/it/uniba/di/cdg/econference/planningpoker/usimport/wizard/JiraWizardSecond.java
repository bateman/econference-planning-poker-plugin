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

public class JiraWizardSecond extends WizardPage{
	public static Button activeButton;
	public static Button closedButton;
	public static Button readyButton;
	public static Button resolvedButton;
	public static Button allButton;

	private Text otherText;

	public JiraWizardSecond(){
		super("JiraSecond");
		setTitle("Jira status of tickets");
		setDescription("Select status of tickets you want to import");
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);

		new Label(composite, SWT.LEFT).setText("Status of tickets");

		Composite selection = new Composite(composite, SWT.NONE);
		selection.setLayout(new FillLayout(SWT.VERTICAL));

		activeButton=new Button(selection,SWT.RADIO);
		activeButton.setSelection(true);
		activeButton.setText("Open");

		readyButton=new Button(selection,SWT.RADIO);
		readyButton.setText("Reopened");

		closedButton=new Button(selection,SWT.RADIO);
		closedButton.setText("Closed");
		
		resolvedButton=new Button(selection,SWT.RADIO);
		resolvedButton.setText("Resolved");
		
		allButton=new Button(selection,SWT.RADIO);
		allButton.setText("All");

		otherText=new Text(selection,SWT.BORDER);
		otherText.setVisible(false);


		setControl(composite);
	}

	public String grabStatus(){
		String status="";
		if (activeButton.getSelection()) status="Open";
		if (readyButton.getSelection()) status="Reopened";
		if (closedButton.getSelection()) status="Closed";
		if (resolvedButton.getSelection()) status="Resolved";
		if (allButton.getSelection()) status="0";
		return status;
	}
	public String makeURL(String name, String status){
		
			String url="http://jira.atlassian.com/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"+name+"+AND+reporter+%3D+luigi87&tempMax=200";	
			String url2="http://jira.atlassian.com/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"+name+"+AND+reporter+%3D+luigi87+AND+status+%3D+"+status+"&tempMax=200";
		
			
if (status.equals("0")){
	return url;
		}
		else {
			return url2;
	}
}

	public IWizardPage getNextPage() {
		return getWizard().getPage("SaveAs"); 	    
	}

}