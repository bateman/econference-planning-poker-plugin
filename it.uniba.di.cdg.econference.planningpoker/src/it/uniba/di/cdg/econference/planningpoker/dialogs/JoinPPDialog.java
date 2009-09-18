package it.uniba.di.cdg.econference.planningpoker.dialogs;

import static it.uniba.di.cdg.xcore.util.Misc.isEmpty;
import it.uniba.di.cdg.xcore.econference.EConferenceContext;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class JoinPPDialog extends Dialog {

	
	private LoadPPFileDialogUI ui;
	private String fileName;
	private String nickName;
	private String status;
	private boolean sendInvitations;

	public JoinPPDialog(Shell parentShell) {
		super(parentShell);	
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		 Composite composite = (Composite) super.createDialogArea( parent );

	        FillLayout layout = new FillLayout( SWT.VERTICAL );
	        composite.setLayout( layout );
	        
	        ui = new LoadPPFileDialogUI( composite, SWT.NONE );

	        return composite;
	}
	
    @Override
    protected void okPressed() {
        fileName = ui.getFileName();
        nickName = ui.getNickName();
        status = ui.getPersonalStatus();
        sendInvitations = ui.sendInvitations();
        
        if (validate()) {
            getContext().setNickName( nickName );
            getContext().setPersonalStatus(status);
            super.okPressed();
        } else {
            UiPlugin.getUIHelper().showErrorMessage( "Please fill all required fields" );
        }
    }

	private boolean validate() {
        return !isEmpty( getFileName() ) && !isEmpty( getNickName() ) && getContext() != null;
	}


	public EConferenceContext getContext() {
		return ui.getContext();
	}

	public boolean isSendInvitations() {		
		return sendInvitations;
	}


	public String getFileName() {
		return fileName;
	}


	public String getNickName() {
		return nickName;
	}
	

}
