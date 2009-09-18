package it.uniba.di.cdg.econference.planningpoker.dialogs;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerContext;
import it.uniba.di.cdg.econference.planningpoker.model.internal.PPContextLoader;
import it.uniba.di.cdg.xcore.multichat.service.Invitee;
import it.uniba.di.cdg.xcore.network.IBackend;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class LoadPPFileDialogUI extends Composite {

	
	 public static final String[] FILE_EXT = new String[] { "*.ecx" };   

	    private Composite fileNameComposite = null;
	    private Group conferenceOptionsGroup = null;
	    private CLabel labelFileName = null;
	    private Button selectFileButton = null;
	    private Text selectedFileNameText = null;
	    private Button sendInvitationsCheckBox = null;
	    private CLabel cLabel = null;	   
	    private Text nickNameText = null;
	    private CLabel cLabel1;
	    private Text statusText;

	    private PlanningPokerContext context;

	
	public LoadPPFileDialogUI(Composite parent, int style) {
		super(parent, style);
		 initialize();
		 context = new PlanningPokerContext();
	}

	private void initialize() {
		FillLayout fillLayout = new FillLayout();
        fillLayout.type = org.eclipse.swt.SWT.VERTICAL;
        createComposite();
        this.setLayout(fillLayout);
        createGroup();
        setSize(new org.eclipse.swt.graphics.Point(390,133));
		
	}

    /**
     * This method initializes group	
     *
     */
    private void createGroup() {
        GridData gridData3 = new org.eclipse.swt.layout.GridData();
        gridData3.grabExcessVerticalSpace = false;
        gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData3.grabExcessHorizontalSpace = true;
        conferenceOptionsGroup = new Group( this, SWT.NONE );
        conferenceOptionsGroup.setText("Options");
        conferenceOptionsGroup.setLayout(new GridLayout());
        sendInvitationsCheckBox = new Button(conferenceOptionsGroup, SWT.CHECK);
        sendInvitationsCheckBox.setText("Send invitations");
        sendInvitationsCheckBox.setEnabled(false);
        sendInvitationsCheckBox.setLayoutData(gridData3);
    }

	private void createComposite() {
		 GridData gridData11 = new org.eclipse.swt.layout.GridData();
	        gridData11.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData11.grabExcessHorizontalSpace = true;
	        gridData11.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridData gridData1 = new org.eclipse.swt.layout.GridData();
	        gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData1.grabExcessHorizontalSpace = true;
	        gridData1.grabExcessVerticalSpace = true;
	        gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridData gridData = new org.eclipse.swt.layout.GridData();
	        gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	        gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
	        GridLayout gridLayout = new GridLayout();
	        gridLayout.numColumns = 3;
	        gridLayout.horizontalSpacing = 5;
	        gridLayout.marginWidth = 1;
	        gridLayout.marginHeight = 1;
	        gridLayout.verticalSpacing = 2;
	        fileNameComposite = new Composite( this, SWT.NONE );
	        fileNameComposite.setLayout(gridLayout);
	        labelFileName = new CLabel(fileNameComposite, SWT.NONE);
	        labelFileName.setText("Filename:");
	        labelFileName.setLayoutData(gridData);
	        selectedFileNameText = new Text(fileNameComposite, SWT.BORDER | SWT.READ_ONLY);
	        selectedFileNameText.setLayoutData(gridData1);
	        selectFileButton = new Button(fileNameComposite, SWT.NONE);
	        selectFileButton.setText("Select ...");
	        selectFileButton.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
	            public void widgetSelected( org.eclipse.swt.events.SelectionEvent e ) {
	                final String fileName = UiPlugin.getUIHelper().requestFile( FILE_EXT );
	                if (fileName == null) // CANCEL pressed
	                    return;
	                PPContextLoader loader = new PPContextLoader( context );
	                try {
	                    loader.load( fileName );
	                    selectedFileNameText.setText( fileName );
	                } catch (Exception ex) {
	                    UiPlugin.getUIHelper().showErrorMessage( "Sorry, the specified file seems invalid: " 
	                            + ex.getMessage() );
	                    ex.printStackTrace();
	                    return;
	                } 
	                
	                updateNickName();
	                
	                // Check if we can enable, disable the UI
	                boolean isModerator = checkUserCanSendInvitations();
	                sendInvitationsCheckBox.setEnabled( isModerator );
	                // Avoid leaving the checkbox checked ;)
	                sendInvitationsCheckBox.setSelection( isModerator );
	            }
	        } );
	        cLabel = new CLabel(fileNameComposite, SWT.NONE);
	        cLabel.setText("Nickname:");
	        nickNameText = new Text(fileNameComposite, SWT.BORDER);
	        nickNameText.setLayoutData(gridData11);	 
	        //Empty label to leave the grid data cell blank
	        Label emptyLabel = new Label(fileNameComposite, SWT.NONE);
	        emptyLabel.setLayoutData(gridData11);
	        
	        cLabel1 = new CLabel(fileNameComposite, SWT.NONE);
	        cLabel1.setText("Role:");
	        statusText = new Text(fileNameComposite, SWT.BORDER);
	        statusText.setLayoutData(gridData11);
	        statusText.setToolTipText("Insert your personal role in the Planning Poker team");
		
	}
	
	 /**
     * @return <code>true</code> if the currently connected user is the moderator for conference file,
     *         <code>false</code> otherwise
     */
    private boolean checkUserCanSendInvitations() {
        String moderatorId = context.getModerator().getId();

        // XXX We should let the user to choice its own backend
        IBackend b = NetworkPlugin.getDefault().getRegistry().getBackend( 
                "it.uniba.di.cdg.jabber.jabberBackend" );
        String currentUserId = b.getUserAccount().getId();
        
        boolean eq = moderatorId.indexOf( currentUserId ) > -1;
        // XXX Avoid sending an invitation to ourselves if we are moderators!!
        if (eq) {
            for (Iterator<Invitee> it = context.getInvitees().iterator(); it.hasNext(); ) {
                Invitee i = (Invitee) it.next();
                if (moderatorId.equals( i.getId() )) {
                    it.remove();
                    break;
                }
            }
        }
        
        return eq;
    }
	
    /**
     * Replace our id with the fullname found in the invitation.
     */
    private void updateNickName() {
        IBackend b = NetworkPlugin.getDefault().getRegistry().getBackend( 
            "it.uniba.di.cdg.jabber.jabberBackend" );
        String myId = b.getUserAccount().getId();
        
        String myNickName = myId;
        
        for (Iterator<Invitee> it = context.getInvitees().iterator(); it.hasNext(); ) {
            Invitee i = (Invitee) it.next();
            if (i.getId().indexOf( myId ) > -1) {
                myNickName = i.getFullName();
                break;
            }
        }
        
        nickNameText.setText( myNickName );
    }

	public String getFileName() {
		 return selectedFileNameText.getText();
	}

	public String getNickName() {
		 return nickNameText.getText();
	}
	
	public String getPersonalStatus(){
		return statusText.getText();
	}

	public boolean sendInvitations() {
		 return sendInvitationsCheckBox.getSelection();
	}

	public PlanningPokerContext getContext() {
			return context;
	}

}
