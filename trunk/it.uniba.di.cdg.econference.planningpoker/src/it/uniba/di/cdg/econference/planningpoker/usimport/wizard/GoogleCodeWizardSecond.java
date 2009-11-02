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

public class GoogleCodeWizardSecond extends WizardPage implements MouseListener {


	public static Button enhancementButton;
	public static Button taskButton;
	public static Button allButton;
	public static Text otherText;
	String name;

	public GoogleCodeWizardSecond(){
		super("GoogleCodeSecond");
		setTitle("GoogleCode type of tickets");
		setDescription("Enter type of tickets you want to import");
	}

	public void createControl(Composite parent) {
		setPageComplete(false);

		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout g=new GridLayout(1,true);
		g.verticalSpacing=15;
		composite.setLayout(g);

		new Label(composite, SWT.LEFT).setText("Type of tickets");

		Composite selection = new Composite(composite, SWT.NONE);
		selection.setLayout(new FillLayout(SWT.VERTICAL));

		enhancementButton=new Button(selection,SWT.CHECK);
		enhancementButton.setText("Enhancement");

		taskButton=new Button(selection,SWT.CHECK);
		taskButton.setText("Task");

		allButton=new Button(selection,SWT.CHECK);
		allButton.setText("All");
		allButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				enhancementButton.setSelection(true);
				taskButton.setSelection(true);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});


		otherText=new Text(selection,SWT.BORDER);
		Font f=new Font(composite.getDisplay(), "Arial", 9, SWT.ITALIC);
		otherText.setFont(f);
		otherText.setText("custom type...");
		otherText.addMouseListener(this);

		otherText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				name = otherText.getText();
				if ((name.length()==0 || name.equals("custom type...")) && !enhancementButton.getSelection() && !taskButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No type is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom type...")) || (enhancementButton.getSelection() || taskButton.getSelection()));

			}
		});

		enhancementButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!enhancementButton.getSelection() && !taskButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No type is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom type...")) || (enhancementButton.getSelection() || taskButton.getSelection()));
			}


		});


		taskButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!enhancementButton.getSelection() && !taskButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No type is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom type...")) || (enhancementButton.getSelection() || taskButton.getSelection()));
			}


		});

		allButton.addListener(SWT.Selection, new Listener() {


			public void handleEvent(Event event) {
				name = otherText.getText();
				if (!enhancementButton.getSelection() && !taskButton.getSelection() && !allButton.getSelection())
					setErrorMessage("No type is selected");
				else setErrorMessage(null);
				setPageComplete((name.length()>0 && !name.equals("custom type...")) || (enhancementButton.getSelection() || taskButton.getSelection()));
			}


		});
		setControl(composite);
	}

	public static LinkedList<String> grabType(){
		LinkedList<String> type=new LinkedList<String>();
		if (enhancementButton.getSelection()) type.offer("enhancement");
		if (taskButton.getSelection()) type.offer("task");
		if (!otherText.getText().equals("") && !otherText.getText().equals("custom type..."))
			type.offer(otherText.getText());

		return type;

	}
	
	public IWizardPage getNextPage() {
		return super.getNextPage();  
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
}
