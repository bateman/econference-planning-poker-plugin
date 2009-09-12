package it.uniba.di.cdg.econference.planningpoker.dialogs;


import java.util.Calendar;
import java.util.Date;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
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
	
	private Text txt_story_text;
	private Text txt_notes;
	private Combo cb_priority;
	private Combo cb_estimate;
	private Text txt_id;

	private Backlog backlog;

	public DefaultUserStoryDialog(Shell parentShell) {		
		super(parentShell);
		
	}
	
	public void setStory(IUserStory story){
		this.story = (DefaultUserStory) story;
	}
	
	public void setCardDeck(CardDeck deck){
		this.deck = deck;
	}
	
	public void setBacklog(Backlog backlog){
		this.backlog = backlog;
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
		
		Label labelId = new Label(root, SWT.NONE);
		labelId.setText("Id:");
		labelId.setLayoutData(gd);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gd.grabExcessVerticalSpace = true;
		
		txt_id = new Text(root, SWT.BORDER | SWT.SINGLE);
		txt_id.setLayoutData(gd);
				
		gd = new GridData();
		
		Label label1 = new Label(root, SWT.NONE);
		label1.setText("Story Text:");
		label1.setLayoutData(gd);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gd.heightHint = 50;
		gd.grabExcessVerticalSpace = true;

		
		txt_story_text = new Text(root, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
		txt_story_text.setLayoutData(gd);
		
		gd = new GridData();
		
		Label label2 = new Label(root, SWT.NONE);
		label2.setText("Priority:");
		label2.setLayoutData(gd);
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		
		cb_priority = new Combo(root, SWT.READ_ONLY);
		//Adding item to the priority combo
		for (PRIORITY value : PRIORITY.values()) {
			cb_priority.add(value.getName());
			
		}		
		cb_priority.setLayoutData(gd);
		
		Label notesLabel = new Label(root, SWT.NONE);
		notesLabel.setText("Notes:");
		notesLabel.setLayoutData(gd);
		
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | 
				GridData.VERTICAL_ALIGN_FILL);
		gd.heightHint = 90;
		gd.widthHint = 350;
		gd.grabExcessVerticalSpace = true;

		
		txt_notes = new Text(root, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL );
		txt_notes.setLayoutData(gd);		
		
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
		txt_id.setEditable(false);
		txt_id.setText(userStory.getId());		
		txt_story_text.setText(userStory.getStoryText());		
		
		PRIORITY priority = userStory.getPriority();
		for (int i = 0; i < PRIORITY.values().length; i++) {
			if(PRIORITY.values()[i]==priority){
				cb_priority.select(i);
			}
		}
		txt_notes.setText(userStory.getNotes());
		Object points = userStory.getEstimate();		
			
		//Select Uknown value as default
		cb_estimate.select(0);
		
		if(points!=null && points!=""){
			for (int i = 0; i < deck.getCards().length; i++) {
				IPokerCard card = deck.getCards()[i];
				if(card.getValue().equals(points)){
					cb_estimate.select(i);
				}
			}
		}
		
		txt_story_text.setFocus();
		
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	public int show() {
		return this.open();		
	}
	
	@Override
	protected void okPressed() {
		DefaultUserStory.PRIORITY priority;
		Object points;
		if (validateInput()) {
			
			priority = PRIORITY.values()[cb_priority.getSelectionIndex()];
			points = deck.getCards()[cb_estimate.getSelectionIndex()].getValue();											
			
			
			if(story==null){
				story = new DefaultUserStory(
						txt_id.getText(),
						txt_story_text.getText(),
						priority, 
						txt_notes.getText(), 
						points);		
			}else{
				story.setStoryText(txt_story_text.getText());
				story.setPriority(priority);
				story.setNotes(txt_notes.getText());
				story.setEstimate(points);
				story.setLastUpdate(Calendar.getInstance().getTime());
			}
			super.okPressed();
		}		
	}
	
	private boolean validateInput() {
		if(txt_id.getText().length() != 0 && txt_story_text.getText().length() != 0){
			if(story==null){
				//We have to check if new inserted id doesn't exists in the backlog
				for (int i = 0; i < backlog.getUserStories().length; i++) {
					if(backlog.getUserStories()[i].getId().equals(story.getId())){
						setErrorMessage("Id already exisisting");
						return false;
					}
				}
			}
			return true;
		}else{
			setErrorMessage("Please enter all data");
			return false;
		}
	}


}
