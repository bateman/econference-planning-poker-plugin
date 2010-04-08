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
package it.uniba.di.cdg.econference.planningpoker.usimport.wizard;

import it.uniba.di.cdg.econference.planningpoker.usimport.Assembla;
import it.uniba.di.cdg.econference.planningpoker.usimport.Github;
import it.uniba.di.cdg.econference.planningpoker.usimport.GoogleCode;
import it.uniba.di.cdg.econference.planningpoker.usimport.Trac;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class HandleWizard extends Wizard{
	static SourceSelectionWizard ssw;
	static AssemblaWizardFirst awF;
	static AssemblaWizardSecond awS;
	static GithubWizardFirst gwF;
	static GithubWizardSecond gwS;
	static GoogleCodeWizardFirst gcF;
	static GoogleCodeWizardSecond gcS;
	static GoogleCodeWizardThird gcT;
	static TracWizardFirst tF;
	static TracWizardSecond tS;
	static TracWizardThird tT;
	static SaveAsWizard sW;
	private boolean finished;
	private String fileName;




	public HandleWizard(){
		super();
		setWindowTitle("User Stories Import");
		setNeedsProgressMonitor(true);
		this.finished = false;

	}


	public void addPages(){
		ssw=new SourceSelectionWizard();
		addPage(ssw);
		awF=new AssemblaWizardFirst();
		addPage(awF);
		awS=new AssemblaWizardSecond();
		addPage(awS);
		gwF=new GithubWizardFirst();
		addPage(gwF);
		gwS=new GithubWizardSecond();
		addPage(gwS);
		gcF=new GoogleCodeWizardFirst();
		addPage(gcF);
		gcS=new GoogleCodeWizardSecond();
		addPage(gcS);
		gcT=new GoogleCodeWizardThird();
		addPage(gcT);
		tF=new TracWizardFirst();
		addPage(tF);
		tS=new TracWizardSecond();
		addPage(tS);
		tT=new TracWizardThird();
		addPage(tT);
		sW=new SaveAsWizard();
		addPage(sW);

	}
	
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	
	public String getFileName(){
		return fileName;
	}
	
	public boolean isFinished(){
		return finished ;
	}

	@Override
	public boolean canFinish() {
		IWizardPage p=getContainer().getCurrentPage();
		//return  (p == awS && awS.isPageComplete()) || (p == gwS &&  gwS.isPageComplete()) || (p == gcT && gcT.isPageComplete()) || (p == tT && tT.isPageComplete());
		return (p == sW && sW.isPageComplete());
	}


	public boolean performFinish() {
		try {
			getContainer().run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask("Retrieving informations...", 100);
						if (ssw.assembla.getSelection()) {
							System.out.println(awS.makeURL(awF.projectNameText.getText(),awS.grabStatus()));
							monitor.worked(25);

							@SuppressWarnings("unused")
							Assembla a=new Assembla(awS.makeURL(awF.projectNameText.getText(),awS.grabStatus()),SaveAsWizard.saveText.getText());
							monitor.worked(25);
						}

						if (ssw.github.getSelection()) {
							System.out.println(gwS.makeURL(GithubWizardFirst.projectNameText.getText(),GithubWizardFirst.usernameText.getText(),GithubWizardSecond.openButton.getSelection(),GithubWizardSecond.closedButton.getSelection()));
							monitor.worked(25);

							@SuppressWarnings("unused")
							Github g=new Github(gwS.makeURL(GithubWizardFirst.projectNameText.getText(),GithubWizardFirst.usernameText.getText(),GithubWizardSecond.openButton.getSelection(),GithubWizardSecond.closedButton.getSelection()),SaveAsWizard.saveText.getText());
							monitor.worked(25);

						}

						if (ssw.googlecode.getSelection()) {
							System.out.println(gcT.makeURL(GoogleCodeWizardFirst.projectNameText.getText(),GoogleCodeWizardThird.grabStatus(),GoogleCodeWizardSecond.grabType()));
							monitor.worked(25);

							@SuppressWarnings("unused")
							GoogleCode gc=new GoogleCode(gcT.makeURL(GoogleCodeWizardFirst.projectNameText.getText(),GoogleCodeWizardThird.grabStatus(),GoogleCodeWizardSecond.grabType()),SaveAsWizard.saveText.getText());
							monitor.worked(25);

						}

						if (ssw.trac.getSelection()) {
							System.out.println(tT.makeURL(TracWizardFirst.projectNameText.getText(),TracWizardThird.grabStatus(),TracWizardSecond.grabType()));
							monitor.worked(25);

							@SuppressWarnings("unused")
							Trac t=new Trac(tT.makeURL(TracWizardFirst.projectNameText.getText(),TracWizardThird.grabStatus(),TracWizardSecond.grabType()),TracWizardFirst.projectNameText.getText(),SaveAsWizard.saveText.getText());
							monitor.worked(25);

						}
											
						


					} finally {
						finished = true;
						monitor.done();
					}
				}});
		} catch (InvocationTargetException e) {

		} catch (InterruptedException e) {

		}

		return true;
	}


}
