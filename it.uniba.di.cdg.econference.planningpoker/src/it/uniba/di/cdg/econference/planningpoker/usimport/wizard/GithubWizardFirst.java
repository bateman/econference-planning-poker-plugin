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

public class GithubWizardFirst extends WizardPage implements MouseListener{

	public static Text projectNameText;
	public static Text usernameText;
	String name="";
	String user="";

	public GithubWizardFirst(){
		super("GithubFirst");
		setTitle("Github project name and username");
		setDescription("Enter project name and username of project owner in Github");

	}


	public void createControl(Composite parent) {

		setPageComplete(false);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		new Label(composite, SWT.LEFT).setText("Project name:");

		projectNameText=new Text(composite,SWT.BORDER);
		projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Font f=new Font(composite.getDisplay(), "Arial", 9, SWT.ITALIC);
		projectNameText.setFont(f);
		projectNameText.setText("enter project name...");

		new Label(composite, SWT.LEFT).setText("Username:");

		usernameText=new Text(composite,SWT.BORDER);
		usernameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		usernameText.setFont(f);

		usernameText.setText("enter username...");


		projectNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				name = projectNameText.getText();
				if (name.length()==0)
					setErrorMessage("Type project name is empty");
				else setErrorMessage(null);
				setPageComplete(name.length()>0 && user.length()>0 && !name.equals("enter project name...") && !user.equals("enter username..."));
			}
		});
		usernameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				user = usernameText.getText();
				if (user.length()==0)
					setErrorMessage("Type username is empty");
				else setErrorMessage(null);
				setPageComplete(name.length()>0 && user.length()>0 && !name.equals("enter project name...") && !user.equals("enter username..."));
			}
		});

		projectNameText.addMouseListener(this);
		usernameText.addMouseListener(this);

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


	@Override
	public void mouseUp(MouseEvent e) {
		if (e.widget==GithubWizardFirst.projectNameText)
			projectNameText.selectAll();
		if (e.widget==GithubWizardFirst.usernameText)
			usernameText.selectAll();

	}



}
