package it.uniba.di.cdg.econference.planningpoker.dialogs;


import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory.PRIORITY;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DefaultUserStoryDialog extends TitleAreaDialog implements IUserStoryDialog {

	private DefaultUserStory story = null;
	
	private CardDeck deck = null;
	
	private Text txt_name;
	private Text txt_description;
	private Combo cb_priority;
	private Combo cb_estimate;

	public DefaultUserStoryDialog(Shell parentShell) {		
		super(parentShell);
		
	}
	
	public void setStory(IUserStory story){
		this.story = (DefaultUserStory) story;
	}
	
	public void setCardDeck(CardDeck deck){
		this.deck = deck;
	}

	public DefaultUserStory getStory() {
		return story;
	}


	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("Default User Story Editor");
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {	
		setTitle("Create new User Story");

		Composite root = (Composite) super.createDialogArea(parent);
		root.setLayout(new GridLayout(2, false));
				
		GridData gd = new GridData();
		
		//Couse of strange behaviour of Grid layout in the Title Area Dialog
		Label emptyLabel = new Label(root, SWT.NONE);		
		emptyLabel.setLayoutData(gd);
				
		gd = new GridData();
		
		Label label1 = new Label(root, SWT.NONE);
		label1.setText("Name:");
		label1.setLayoutData(gd);
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;

		
		txt_name = new Text(root, SWT.BORDER | SWT.SINGLE);
		txt_name.setLayoutData(gd);
		
		gd = new GridData();
		
		Label label2 = new Label(root, SWT.NONE);
		label2.setText("Priority:");
		label2.setLayoutData(gd);
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		cb_priority = new Combo(root, SWT.READ_ONLY);
		//Adding item to the priority combo
		for (PRIORITY value : PRIORITY.values()) {
			cb_priority.add(value.getName());
			
		}		
		cb_priority.setLayoutData(gd);
		
		gd = new GridData();
		
		Label label3 = new Label(root, SWT.NONE);
		label3.setText("Description:");		
		label3.setLayoutData(gd);		
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gd.heightHint = 80;
		gd.grabExcessVerticalSpace = true;
				
		txt_description = new Text(root, SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		txt_description.setLayoutData(gd);
		
		gd = new GridData();
		
		Label label4 = new Label(root, SWT.NONE);
		label4.setText("Estimate:");
		label4.setLayoutData(gd);
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		cb_estimate = new Combo(root, SWT.READ_ONLY);
		//Adding item to the estimation combo
		if(deck!=null)
		for (IPokerCard card : deck.getCards()) {
			cb_estimate.add(card.getStringValue());
		}
		cb_estimate.setLayoutData(gd);
		
		if(story!=null){
			//fill the dialog area with data of the Story to edit
			fillForm();
		}else{
			//Select the first element of the Combos
			cb_priority.select(0);
			cb_estimate.select(0);
		}
		
		root.pack();
		
		return root;
	}
	
	
	private void fillForm() {
		setTitle("Edit the User Story");
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
		createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	public void show() {
		this.open();		
	}
	
	@Override
	protected void okPressed() {
		DefaultUserStory.PRIORITY priority;
		Object points;
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
			super.okPressed();
		} else {
			setErrorMessage("Please enter all data");
		}
		
	}


}
