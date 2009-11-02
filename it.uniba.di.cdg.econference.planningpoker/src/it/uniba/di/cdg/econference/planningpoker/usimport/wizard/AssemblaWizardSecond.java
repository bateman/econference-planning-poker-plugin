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

public class AssemblaWizardSecond extends WizardPage{
	public static Button activeButton;
	public static Button closedButton;
	public static Button readyButton;
	public static Button allButton;

	private Text otherText;

	public AssemblaWizardSecond(){
		super("AssemblaSecond");
		setTitle("Assembla status of tickets");
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
		activeButton.setText("Active");

		readyButton=new Button(selection,SWT.RADIO);
		readyButton.setText("Ready to test");

		closedButton=new Button(selection,SWT.RADIO);
		closedButton.setText("Closed");
		
		allButton=new Button(selection,SWT.RADIO);
		allButton.setText("All");

		otherText=new Text(selection,SWT.BORDER);
		otherText.setVisible(false);


		setControl(composite);
	}

	public String grabStatus(){
		String status="";
		if (activeButton.getSelection()) status="1";
		if (readyButton.getSelection()) status="4";
		if (closedButton.getSelection()) status="5";
		if (allButton.getSelection()) status="0";
		return status;
	}

	public String makeURL(String name, String status){
		String url="http://www.assembla.com/spaces/"+name+"/tickets?batch=false&tickets_report_id="+status+"&ticket_id=&commit=Go+%C2%BB";
		return url;
	}

	public IWizardPage getNextPage() {
		return getWizard().getPage("SaveAs"); 	    
	}


}
