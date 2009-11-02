package it.uniba.di.cdg.econference.planningpoker.usimport.wizard;

import java.util.LinkedList;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class GoogleCodeWizardThird extends WizardPage implements MouseListener{

	public static Button newButton;
	public static Button acceptedButton;
	public static Button startedButton;
	public static Button fixedButton;
	public static Button verifiedButton;
	public static Button invalidButton;
	public static Button duplicateButton;
	public static Button wontfixButton;
	public static Button doneButton;

	public static Text otherText;
	String name="";

	public GoogleCodeWizardThird(){
		super("GoogleCodeThird");
		setTitle("GoogleCode status of tickets");
		setDescription("Enter status of tickets you want to import");
	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);

		new Label(composite, SWT.LEFT).setText("Status of tickets");

		Composite selection = new Composite(composite, SWT.NONE);
		selection.setLayout(new FillLayout(SWT.VERTICAL));

		newButton=new Button(selection,SWT.CHECK);
		newButton.setText("New");

		acceptedButton=new Button(selection,SWT.CHECK);
		acceptedButton.setText("Accepted");

		startedButton=new Button(selection,SWT.CHECK);
		startedButton.setText("Started");

		fixedButton=new Button(selection,SWT.CHECK);
		fixedButton.setText("Fixed");

		verifiedButton=new Button(selection,SWT.CHECK);
		verifiedButton.setText("Verfied");

		invalidButton=new Button(selection,SWT.CHECK);
		invalidButton.setText("Invalid");

		duplicateButton=new Button(selection,SWT.CHECK);
		duplicateButton.setText("Duplicate");

		wontfixButton=new Button(selection,SWT.CHECK);
		wontfixButton.setText("WontFix");

		doneButton=new Button(selection,SWT.CHECK);
		doneButton.setText("Done");

		Button allButton=new Button(selection,SWT.CHECK);
		allButton.setText("All");
		allButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				newButton.setSelection(true);
				acceptedButton.setSelection(true);
				startedButton.setSelection(true);
				fixedButton.setSelection(true);
				verifiedButton.setSelection(true);
				invalidButton.setSelection(true);
				duplicateButton.setSelection(true);
				wontfixButton.setSelection(true);
				doneButton.setSelection(true);

			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		otherText=new Text(selection,SWT.BORDER);
		Font f=new Font(composite.getDisplay(), "Arial", 9, SWT.ITALIC);
		otherText.setFont(f);
		otherText.setText("custom status...");
		otherText.addMouseListener(this);

		otherText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				name = otherText.getText();
				if ((name.length()==0 || name.equals("custom status...") && !newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection()) && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());

			}
		});


		newButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		acceptedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		fixedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		startedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		verifiedButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		invalidButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		duplicateButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		wontfixButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		doneButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		allButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!newButton.getSelection() && !acceptedButton.getSelection() && !startedButton.getSelection() && !fixedButton.getSelection() && !verifiedButton.getSelection() && !invalidButton.getSelection() && !duplicateButton.getSelection() && !wontfixButton.getSelection() && !doneButton.getSelection())
					setErrorMessage("No status is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom status...")) || (newButton.getSelection() || acceptedButton.getSelection() || startedButton.getSelection() || fixedButton.getSelection() || verifiedButton.getSelection() || invalidButton.getSelection()) || duplicateButton.getSelection() || wontfixButton.getSelection() || doneButton.getSelection());
			}


		});

		setControl(composite);		
	}

	public String makeURL(String projectName,LinkedList<String> status, LinkedList<String> type){
		
		String statusS="",typeS="";

		if (status.size()==1) statusS="status="+status.get(0);
		else {
			while (!status.isEmpty()){
				statusS+="status="+status.poll()+" OR ";
			}
		}

		if (type.size()==1) typeS="type="+type.get(0);
		else {
			while(!type.isEmpty()){
				typeS+="type="+type.poll()+" OR ";
			}
		}

		String url="http://code.google.com/p/"+projectName+"/issues/csv?can=1&q="+statusS+" "+typeS+"&colspec=ID+Type+Status+Milestone+Summary";
		url=url.replace("OR  type", "type");
		url=url.replace("OR &", "&");

		return url;
	}

	public static LinkedList<String> grabStatus(){
		LinkedList<String> status=new LinkedList<String>();
		if (newButton.getSelection()) status.offer("new");
		if (acceptedButton.getSelection()) status.offer("accepted");
		if (startedButton.getSelection()) status.offer("started");
		if (fixedButton.getSelection()) status.offer("fixed");
		if (verifiedButton.getSelection()) status.offer("verified");
		if (invalidButton.getSelection()) status.offer("invalid");
		if (duplicateButton.getSelection()) status.offer("duplicate");
		if (wontfixButton.getSelection()) status.offer("wontfix");
		if (doneButton.getSelection()) status.offer("done");
		if (!otherText.getText().equals("") &&  !otherText.getText().equals("custom status..."))
			status.offer(otherText.getText());

		return status;
	}

	


	public void mouseDoubleClick(MouseEvent e) {	
	}

	public void mouseDown(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseUp(MouseEvent e) {	
		otherText.selectAll();
	}
	public IWizardPage getNextPage() {
		return getWizard().getPage("SaveAs"); 	    
	}
}
