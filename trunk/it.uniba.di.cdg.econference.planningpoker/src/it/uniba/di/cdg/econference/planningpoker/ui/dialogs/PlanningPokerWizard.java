/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2010 Collaborative Development Group - Dipartimento di Informatica, 
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
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerContextWriter;
import it.uniba.di.cdg.xcore.econference.ui.dialogs.GenInfoPage;
import it.uniba.di.cdg.xcore.econference.ui.dialogs.InvitePage;
import it.uniba.di.cdg.xcore.econference.ui.dialogs.InviteWizard;
import it.uniba.di.cdg.xcore.econference.ui.dialogs.LastPage;
import it.uniba.di.cdg.xcore.econference.util.MailFactory;
import it.uniba.di.cdg.xcore.network.NetworkPlugin;
import it.uniba.di.cdg.xcore.ui.wizards.IConfigurationConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.INewWizard;
import org.osgi.service.prefs.Preferences;

import com.google.gdata.GoogleDocManager;

public class PlanningPokerWizard extends InviteWizard implements INewWizard {

    public PlanningPokerWizard() {
        super();
    }

    @Override
    public void addPages() {
        context = new PlanningPokerContext();
        genInfoPage = new GenInfoPage( "" , context);
        invitePage = new InvitePage( "" , context);
        lastOnePage = new LastPage( "", context );
        addPage( genInfoPage );
        addPage( invitePage );
        addPage( lastOnePage );
    }
    
    @Override
    public PlanningPokerContext getContext() {
        return (PlanningPokerContext) this.context;
    }

    @Override
    public boolean performFinish() {
        lastOnePage.saveData();
        this.context = (PlanningPokerContext) lastOnePage.getContext();

        Preferences preferences = new ConfigurationScope().getNode(IConfigurationConstant.CONFIGURATION_NODE_QUALIFIER);
		Preferences pathPref = preferences.node(IConfigurationConstant.PATH);
		String preferredFilePath = pathPref.get(IConfigurationConstant.DIR, "");
        
        String filepath = genInfoPage.getFilePath();
        new File(filepath.substring(0, filepath.lastIndexOf(System.getProperty("file.separator")))).mkdirs();
        try {

            PlanningPokerContextWriter writer = new PlanningPokerContextWriter( filepath, (PlanningPokerContext) context );
            writer.serialize();            
            
            // if we save the ecx file not in the default location
            // we store a copy there
            if (!filepath.startsWith( preferredFilePath )) {
            	String filecopy = genInfoPage.computeFilePath();
                writer = new PlanningPokerContextWriter( filecopy, (PlanningPokerContext) context );
                writer.serialize();
            }

            Preferences gmailPref = preferences.node(IConfigurationConstant.GMAIL);
    		Preferences smtpPref = preferences.node(IConfigurationConstant.SMTP);

    		/*
            if (canSendInvitation()) {

                String user = NetworkPlugin.getDefault().getRegistry().getDefaultBackend()
                        .getUserId();
                String passwd = NetworkPlugin.getDefault().getRegistry().getDefaultBackend()
                        .getUserAccount().getPassword();
                String title = genInfoPage.getConferenceName() + ".ecx";
                String subject = context.getRoom().split( "@" )[0];
                Vector<String> toRecipients = lastOnePage.recipients();
                String mailBody;

                if (!toRecipients.isEmpty()) {
                    GoogleDocManager manager = new GoogleDocManager( user, passwd );
                    String googleDocLink = manager.uploadFile( filepath, title, toRecipients );
                    mailBody = MailFactory.createMailBody( context, googleDocLink );

                    manager.sendMail( subject, toRecipients, mailBody, filepath );
                }
            }
            */
    		
            if (canSendInvitation()) {
            	
            	String gUser = gmailPref.get(IConfigurationConstant.USERNAME, "");
                String gPasswd = gmailPref.get(IConfigurationConstant.PASSWORD, "");
            	String server = smtpPref.get(IConfigurationConstant.SERVER, "");
            	int port = smtpPref.getInt(IConfigurationConstant.PORT, -1);
                final String user = smtpPref.get(IConfigurationConstant.USERNAME, "");
                final String passwd = smtpPref.get(IConfigurationConstant.PASSWORD, "");
                String title = genInfoPage.getConferenceName() + ".ecx";
                String subject = context.getRoom().split( "@" )[0];
                Vector<String> toRecipients = lastOnePage.recipients();
                String mailBody;
                String googleDocLink = "";

                if (!toRecipients.isEmpty()) {
                	if (getCanCreateGoogleDoc()){
                		try{
	                		GoogleDocManager manager = new GoogleDocManager( gUser, gPasswd );
	                    	googleDocLink = manager.uploadFile( filepath, title, toRecipients );
                		}catch (Exception e){
                			MessageBox mb = new MessageBox(getShell(), SWT.ERROR | SWT.OK);
                            mb.setText("Google Docs Error");
                            mb.setMessage("An error has occurred in creating Google document\n"
                            		+ "Please review the GMail settings from the menu Options > Configuration Wizard");
                            mb.open();
                		}
                	}
                	
                    mailBody = MailFactory.createMailBody( context, googleDocLink );

                    Properties props = System.getProperties();
                    props.put("mail.smtp.host", server);
                    props.put("mail.smtp.user", user);
                    props.put("mail.smtp.password", passwd);
                    props.put("mail.smtp.port", port);
                    props.put("mail.smtp.auth", "true");
                    if(smtpPref.get(IConfigurationConstant.SECURE, "").compareTo("SSL/TLS") == 0){
                        props.put("mail.smtp.ssl.enable", "true");
                    }
                    if(smtpPref.get(IConfigurationConstant.SECURE, "").compareTo("STARTTLS") == 0){
                        props.put("mail.smtp.starttls.enable", "true");
                    }

                    Session session = Session.getDefaultInstance(props, null);
                    
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(user));

                    for( int i=0; i < toRecipients.size(); i++ ){
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toRecipients.get(i)));
                    }
                    message.setSubject(subject);
                    
                    MimeBodyPart body = new MimeBodyPart();
                    body.setText(mailBody);

                    MimeBodyPart attachment = new MimeBodyPart();
                    attachment.attachFile(filepath);
                    attachment.setFileName(new File(filepath).getName().replace(' ','_'));

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(body);
                    multipart.addBodyPart(attachment);
                    message.setContent(multipart);
                    
                    Transport transport = session.getTransport("smtp");
                    transport.connect(server, port, user, passwd);
                    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                    transport.close();
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
		}catch (NoSuchProviderException e) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR | SWT.OK);
            mb.setText("Emails not sent");
            mb.setMessage("Emails were not sent correctly to recipients.\n"
            		+ "Please review the SMTP settings from the menu Options > Configuration Wizard");
            mb.open();
		} catch (MessagingException e) {
			MessageBox mb = new MessageBox(getShell(), SWT.ERROR | SWT.OK);
            mb.setText("Emails not sent");
            mb.setMessage("Emails were not sent correctly to recipients.\n"
            		+ "Please review the SMTP settings from the menu Options > Configuration Wizard");
            mb.open();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return true;
    }
}
