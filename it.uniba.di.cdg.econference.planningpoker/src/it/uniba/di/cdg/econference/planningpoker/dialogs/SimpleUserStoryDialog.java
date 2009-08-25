package it.uniba.di.cdg.econference.planningpoker.dialogs;


import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory.PRIORITY;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimpleUserStoryDialog extends TitleAreaDialog implements IUserStoryDialog {

	private DefaultUserStory story = null;
	
	private ICardDeck deck = null;
	
	private Text txt_name;
	private Text txt_description;
	private Combo cb_priority;
	private Combo cb_estimate;

	public SimpleUserStoryDialog(Shell parentShell) {		
		super(parentShell);
		
	}
	
	public void setStory(IUserStory story){
		this.story = (DefaultUserStory) story;
	}
	
	public void setCardDeck(ICardDeck deck){
		this.deck = deck;
	}

	public DefaultUserStory getStory() {
		return story;
	}


	@Override
	protected Control createContents(Composite parent) {	
		Control control = super.createContents(parent);
		setTitle("New User Story");
		return control;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout(2,false);
		layout.verticalSpacing = 8;
		parent.setLayout(layout);
		Label label1 = new Label(parent, SWT.NONE);
		label1.setText("Name:");
		txt_name = new Text(parent, SWT.BORDER | SWT.SINGLE);
		Label label2 = new Label(parent, SWT.NONE);
		label2.setText("Priority:");				
		cb_priority = new Combo(parent, SWT.READ_ONLY);
		//Adding item to the priority combo
		for (PRIORITY value : PRIORITY.values()) {
			cb_priority.add(value.getName());
			
		}		
		Label label3 = new Label(parent, SWT.NONE);
		label3.setText("Description:");		
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 80;
		txt_description = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		txt_description.setLayoutData(gd);
		Label label4 = new Label(parent, SWT.NONE);
		label4.setText("Estimate:");
		cb_estimate = new Combo(parent, SWT.READ_ONLY);
		//Adding item to the estimation combo
		for (IPokerCard card : deck.getCards()) {
			cb_estimate.add(card.getStringValue());
		}
		
		if(story!=null){
			//fill the dialog area with data of the Story to edit
			fillForm();
		}else{
			//Select the first element of the Combos
			cb_priority.select(0);
			cb_estimate.select(0);
		}
		
		return parent;
	}
	
	
	private void fillForm() {
		DefaultUserStory userStory = (DefaultUserStory)story;
		txt_name.setText(userStory.getName());
		
		PRIORITY priority = userStory.getPriority();
		for (int i = 0; i < PRIORITY.values().length; i++) {
			if(PRIORITY.values()[i]==priority){
				cb_priority.select(i);
			}
		}
		txt_description.setText(userStory.getDescription());
		Object points = userStory.getEstimate();		
			
		if(points==null){
			//Select the first element of the combo
			cb_estimate.select(0);
		}else{
			for (int i = 0; i < deck.getCards().length; i++) {
				IPokerCard card = deck.getCards()[i];
				if(card.getValue().equals(points)){
					cb_estimate.select(i);
				}
			}
		}
		
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createOkButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}


	private Button createOkButton(Composite parent, int okId, String okLabel,
			boolean defaultButton) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.NONE);
		button.setText(okLabel);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(Integer.valueOf(okId));
		button.addSelectionListener(new SelectionAdapter() {
			private DefaultUserStory.PRIORITY priority;
			private Object points;

			public void widgetSelected(SelectionEvent e) {
				if (txt_name.getText().length() != 0
						&& txt_description.getText().length() != 0) {
					
					priority = PRIORITY.values()[cb_priority.getSelectionIndex()];
					points = deck.getCards()[cb_estimate.getSelectionIndex()].getValue();											
					
					
					if(story==null){
						story = new DefaultUserStory(txt_name.getText(),
								priority, 
								txt_description.getText(), 
								points);		
					}else{
						story.setName(txt_name.getText());
						story.setPriority(priority);
						story.setDescription(txt_description.getText());
						story.setEstimate(points);
					}
					buttonPressed(((Integer) e.widget.getData()).intValue());


				} else {
					setErrorMessage("Please enter all data");
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;	
	}

	@Override
	public void show() {
		this.open();		
	}


}
