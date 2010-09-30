/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker.ui.dialogs;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerContext;
import it.uniba.di.cdg.econference.planningpoker.model.PPContextLoader;
import it.uniba.di.cdg.xcore.econference.ui.dialogs.LoadConferenceFileDialogUI;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.UiPlugin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class LoadPPFileDialogUI extends LoadConferenceFileDialogUI {

	private CLabel roleLabel = null;
	private Text statusText = null;
		
	public LoadPPFileDialogUI(Composite parent, int style, String fileName) {
		super(parent, style);
		setFileName(fileName);
		context = new PlanningPokerContext();
		context.setBackendId(NetworkPlugin.getDefault().getRegistry()
				.getDefaultBackend().getBackendId());
		initialize();
	}

	private void initialize() {
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = org.eclipse.swt.SWT.VERTICAL;
		createComposite();
		this.setLayout(fillLayout);
		createGroup();
		setSize(new org.eclipse.swt.graphics.Point(390, 133));
		if (fileName != null && !fileName.equals(""))
			setupContext(fileName);
	}

	@Override
	protected void createComposite() {
		super.createComposite();
		// remove the eConference listener, then add the PP listenere
		selectFileButton.removeSelectionListener(listener);
		selectFileButton
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				final String fileName = UiPlugin.getUIHelper()
						.requestFile(FILE_EXT, FILE_PATH);
				if (fileName == null) // CANCEL pressed
					return;

				setupContext(fileName);
			}
		});
		roleLabel = new CLabel(fileNameComposite, SWT.NONE);
		roleLabel.setText("Role:");
		statusText = new Text(fileNameComposite, SWT.BORDER);
		statusText.setLayoutData(gridData11);
		statusText
				.setToolTipText("Enter your personal role in the Planning Poker team");	
	}

	private void setupContext(String fileName) {		
		PPContextLoader loader = new PPContextLoader((PlanningPokerContext) context);
		try {
			selectedFileNameText.setText(fileName);
			loader.load(fileName);			
		} catch (Exception ex) {
			UiPlugin.getUIHelper().showErrorMessage(
					"Sorry, the specified PlanningPoker .ecx file seems invalid: "
							+ ex.getMessage());
			return;
		}
		updateNickName();

		// Check if we can enable, disable the UI
		boolean isModerator = checkUserCanSendInvitations();
		sendInvitationsCheckBox.setEnabled(isModerator);
		// Avoid leaving the checkbox checked ;)
		sendInvitationsCheckBox.setSelection(isModerator);
	}
	
	public String getPersonalStatus() {
		return statusText.getText();
	}

	public PlanningPokerContext getContext() {
		return (PlanningPokerContext) context;
	}

}
