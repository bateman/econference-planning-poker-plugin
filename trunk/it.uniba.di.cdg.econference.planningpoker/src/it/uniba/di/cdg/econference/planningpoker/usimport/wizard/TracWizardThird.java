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

import java.util.LinkedList;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class TracWizardThird extends WizardPage{

	public static Button newButton;
	public static Button acceptedButton;
	public static Button closedButton;
	public static Button assignedButton;
	public static Button reopenedButton;

	public TracWizardThird(){
		super("TracThird");
		setTitle("Trac status of tickets");
		setDescription("Select status of tickets you want to import");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);
		
		new Label(composite, SWT.LEFT).setText("Status of tickets");;
		
		Composite selection = new Composite(composite, SWT.NONE);
		selection.setLayout(new FillLayout(SWT.VERTICAL));

		acceptedButton=new Button(selection,SWT.CHECK);
		acceptedButton.setText("Accepted");

		assignedButton=new Button(selection,SWT.CHECK);
		assignedButton.setText("Assigned");

		closedButton=new Button(selection,SWT.CHECK);
		closedButton.setText("Closed");

		newButton=new Button(selection,SWT.CHECK);
		newButton.setText("New");

		reopenedButton=new Button(selection,SWT.CHECK);
		reopenedButton.setText("Reopened");

		
		Button allButton=new Button(selection,SWT.CHECK);
		allButton.setText("All");
		allButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				acceptedButton.setSelection(true);
				assignedButton.setSelection(true);
				closedButton.setSelection(true);
				newButton.setSelection(true);
				reopenedButton.setSelection(true);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		Text otherText = new Text(selection,SWT.BORDER);
		otherText.setVisible(false);
		
		newButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});

		acceptedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});

		assignedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});

		closedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});

		reopenedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});

		allButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !assignedButton.getSelection() && !closedButton.getSelection() && !reopenedButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(newButton.getSelection() || acceptedButton.getSelection() || assignedButton.getSelection() || closedButton.getSelection() || reopenedButton.getSelection());
			}


		});


		setControl(composite);		
	}

	public static LinkedList<String> grabStatus(){
		LinkedList<String> status=new LinkedList<String>();
		if (newButton.getSelection()) status.offer("new");
		if (acceptedButton.getSelection()) status.offer("accepted");
		if (assignedButton.getSelection()) status.offer("assigned");
		if (closedButton.getSelection()) status.offer("closed");
		if (reopenedButton.getSelection()) status.offer("reopened");

		return status;
	}

	

	public String makeURL(String projectName,LinkedList<String> status, LinkedList<String> type){
		String statusS="",typeS="";
		if (status.size()==1) statusS="status="+status.get(0)+"&";
		else {
			while (!status.isEmpty()){
				statusS+="status="+status.poll()+"&";
			}
		}

		if (type.size()==1) typeS="type="+type.get(0);
		else {
			while(!type.isEmpty()){
				typeS+="type="+type.poll()+"&";
			}
		}

		String url=projectName+"/query?"+statusS+"order=priority&col=id&col=summary&col=status&col=owner&col=type&col=priority&col=milestone&"+typeS;
		return url;
	}

	public IWizardPage getNextPage() {
		return getWizard().getPage("SaveAs"); 	    
	}

}
