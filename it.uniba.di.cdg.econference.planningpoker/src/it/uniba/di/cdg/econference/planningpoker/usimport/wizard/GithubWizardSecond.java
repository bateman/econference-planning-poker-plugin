package it.uniba.di.cdg.econference.planningpoker.usimport.wizard;

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

public class GithubWizardSecond extends WizardPage{

	public static Button openButton;
	public static Button closedButton;
	public static Button allButton;
	private Text otherText;

	public GithubWizardSecond(){
		super("GithubSecond");
		setTitle("Github status of tickets");
		setDescription("Select status of tickets you want to import");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);

		new Label(composite, SWT.LEFT).setText("Status of tickets");

		Composite selection = new Composite(composite, SWT.NONE);
		selection.setLayout(new FillLayout(SWT.VERTICAL));

		openButton=new Button(selection,SWT.CHECK);
		openButton.setText("Open");

		closedButton=new Button(selection,SWT.CHECK);
		closedButton.setText("Closed");



		allButton=new Button(selection,SWT.CHECK);
		allButton.setText("All");
		allButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				openButton.setSelection(true);
				closedButton.setSelection(true);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		otherText=new Text(selection,SWT.BORDER);
		otherText.setVisible(false);

		openButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!openButton.getSelection() && !closedButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(openButton.getSelection() || closedButton.getSelection());
			}
		});
		
		closedButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!openButton.getSelection() && !closedButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(openButton.getSelection() || closedButton.getSelection());
			}
		});

		allButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!openButton.getSelection() && !closedButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete(openButton.getSelection() || closedButton.getSelection());
			}
		});

		setControl(composite);
	}

	public String makeURL(String projectName, String username, boolean openButton, boolean closedButton){
		
		if (openButton && !closedButton){
			String url="http://github.com/api/v2/xml/issues/list/"+username+"/"+projectName+"/open";
			return url;
		}
		else
			if (!openButton && closedButton)
			{
				String url="http://github.com/api/v2/xml/issues/list/"+username+"/"+projectName+"/closed";
				return url;
			}
			else
				if (openButton && closedButton)
			{
				String url="http://github.com/api/v2/xml/issues/list/"+username+"/"+projectName+"/@alltickets@";
				return url;
			}
				else return null;
	}

	public IWizardPage getNextPage() {
		return getWizard().getPage("SaveAs"); 	    
	}
}
