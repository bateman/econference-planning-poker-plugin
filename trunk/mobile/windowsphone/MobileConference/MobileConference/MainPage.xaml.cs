using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using System.Diagnostics;


using Matrix;
using Matrix.Xmpp;
using Matrix.Xmpp.Client;
using Uri = System.Uri;

namespace MobileConference
{
    public partial class MainPage : PhoneApplicationPage
    {
        public App GloApp = (App.Current as App);
        // Constructor
        public MainPage()
        {
            
            InitializeComponent();
            
            //Eventi Debug XML  
            
            GloApp.xmppClient.OnReceiveXml += new EventHandler<TextEventArgs>(xmppClient_OnReceiveXml);
            GloApp.xmppClient.OnSendXml += new EventHandler<TextEventArgs>(xmppClient_OnSendXml);
            //GloApp.xmppClient.OnIq += new EventHandler<IqEventArgs>(xmppClient_OnIq);
              
            // Fine Eventi Debug XML    
            
            GloApp.xmppClient.OnLogin += new EventHandler<Matrix.EventArgs>(xmppClient_OnLogin);
            GloApp.xmppClient.OnError += new EventHandler<Matrix.ExceptionEventArgs>(xmppClient_OnError);
            GloApp.xmppClient.OnClose += new EventHandler<Matrix.EventArgs>(xmppClient_OnClose);
            GloApp.xmppClient.OnAuthError += new EventHandler<Matrix.Xmpp.Sasl.SaslEventArgs>(xmppClient_OnAuthError);
            GloApp.xmppClient.AutoRoster = false;
        }
        
        //Inizio metodi debug
        
        private void xmppClient_OnReceiveXml(object sender, TextEventArgs e)
        {   
            System.Diagnostics.Debug.WriteLine("REC: " + e.Text);
        }
        
        private void xmppClient_OnSendXml(object sender, TextEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("SEND: " + e.Text);
        }
        
        private void xmppClient_OnIq(object sender, IqEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("OnIq: " + e.Iq.ToString());
        }
        
        // Fine metodi debug  
        
        void xmppClient_OnClose(object sender, Matrix.EventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("OnClose"); 
        }

        void xmppClient_OnError(object sender, Matrix.ExceptionEventArgs e)
        {         
            System.Diagnostics.Debug.WriteLine("OnError");
            MessageBox.Show("The username or password you have entered is incorrect.");
        }

        private void xmppClient_OnAuthError(object sender, Matrix.Xmpp.Sasl.SaslEventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("OnAuthError");
            MessageBox.Show("The username or password you have entered is incorrect.");
            GloApp.xmppClient.Close();
        }

        void xmppClient_OnLogin(object sender, Matrix.EventArgs e)
        {
            System.Diagnostics.Debug.WriteLine("OnLogin");
            System.Threading.Thread.Sleep(1000);
            NavigationService.Navigate(new Uri("/HandleRoom.xaml?Service="+serviceName.Text, UriKind.Relative));           
        }

        private void ApplicationBarIconButton_Click(object sender, System.EventArgs e)
        {
            GloApp.xmppClient.Username = username.Text;
            GloApp.xmppClient.Password = pass.Password.ToString();
            GloApp.xmppClient.XmppDomain = domain.Text.Substring(1);

            //it don't support SRV lookups

            if (GloApp.xmppClient.XmppDomain.Equals("gmail.com"))
            {
                GloApp.xmppClient.Hostname = "talk.google.com";
            }
            else if (GloApp.xmppClient.XmppDomain.Equals("jabber.org"))
            {
                GloApp.xmppClient.Hostname = "jabber.org";
            }
            
            GloApp.xmppClient.Open();                 
        }
    }
}