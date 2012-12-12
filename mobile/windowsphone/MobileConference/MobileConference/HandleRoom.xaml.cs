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
using System.Windows.Navigation;
using System.Windows.Controls.Primitives;

using Matrix;
using Matrix.Xmpp;
using Matrix.Xmpp.Client;
using Uri = System.Uri;


namespace MobileConference
{
    public partial class HandleRoom : PhoneApplicationPage
    {
        private Jid _roomJid;
        private string _nickname;
        private DiscoManager dm;
        public App GloApp = (App.Current as App);
        private MucManager mm;
        private string service;

        public HandleRoom()
        {
            InitializeComponent();
            createPopUp();
        }
  
        protected override void OnNavigatedTo(NavigationEventArgs e) 
        {
            base.OnNavigatedTo(e);
            service = this.NavigationContext.QueryString["Service"];

            try
            {
                mm.ExitRoom(_roomJid, _nickname);
                // Remove the Message Callback in the MessageFilter
                GloApp.xmppClient.MessageFilter.Remove(_roomJid);
                
                // Remove the Presence Callback in the MessageFilter
                GloApp.xmppClient.PresenceFilter.Remove(_roomJid);
            }
            catch (NullReferenceException) { }
            
            roomListBox.Items.Clear();
            dm = new DiscoManager(GloApp.xmppClient);
            dm.DiscoverItems(service, new EventHandler<IqEventArgs>(DiscoItemsResult));
        }


        #region PopUp
        Popup p = new Popup();
        StackPanel panel1 = new StackPanel();
        StackPanel panel2 = new StackPanel();
        TextBlock textblock1 = new TextBlock();
        TextBox roomName = new TextBox();
        Border border = new Border();
        Button okBut = new Button();
        Button cancBut = new Button();

        private void createPopUp()
        {
            border.BorderBrush = new SolidColorBrush(Colors.Black);
            border.BorderThickness = new Thickness(5.0);

            panel1.Background = new SolidColorBrush(Color.FromArgb(255, 50, 50, 50));
            panel1.Height = 200;
            panel1.Width = 400;

            panel2.VerticalAlignment = VerticalAlignment.Bottom;
            panel2.HorizontalAlignment = HorizontalAlignment.Center;
            panel2.Orientation = System.Windows.Controls.Orientation.Horizontal;

            okBut.Content = "OK";
            okBut.VerticalAlignment = VerticalAlignment.Bottom;
            okBut.HorizontalAlignment = HorizontalAlignment.Left;
            okBut.Margin = new Thickness(10, 10, 10, 10);
            okBut.Click += new RoutedEventHandler(Ok_Click);

            cancBut.Content = "Cancel";
            cancBut.VerticalAlignment = VerticalAlignment.Bottom;
            cancBut.HorizontalAlignment = HorizontalAlignment.Right;
            cancBut.Margin = new Thickness(10, 10, 10, 10);
            cancBut.Click += new RoutedEventHandler(Cancel_Click);

            textblock1.Foreground = new SolidColorBrush(Colors.White);
            textblock1.FontSize = 25;
            textblock1.Text = "Enter room name";
            textblock1.Margin = new Thickness(5.0);

            panel1.Children.Add(textblock1);
            panel1.Children.Add(roomName);
            panel1.Children.Add(panel2);

            border.Child = panel1;

            panel2.Children.Add(okBut);
            panel2.Children.Add(cancBut);

            // Set the Child property of Popup to the border 
            p.Child = border;
        }

        private void showPopup(object sender, System.EventArgs e)
        {
            roomName.Text = "econference";
            // Set where the popup will show up on the screen.
            p.VerticalOffset = 20;
            p.HorizontalOffset = 20;

            // Open the popup.
            p.IsOpen = true;
        }

        void Ok_Click(object sender, RoutedEventArgs e)
        {
            // Close the popup.
            p.IsOpen = false;

            if (roomName.Text != string.Empty)
                this.ConnectToRoom(GloApp.xmppClient, roomName.Text + "@" + service, GloApp.xmppClient.Username, true);
            else
                MessageBox.Show("You must enter the room name");
        }

        void Cancel_Click(object sender, RoutedEventArgs e)
        {
            p.IsOpen = false;
        }

        #endregion


        void DiscoItemsResult(object sender, IqEventArgs e)
        {
            var query = e.Iq.Element<Matrix.Xmpp.Disco.Items>();
            if (query != null)
            {
                foreach (var itm in query.GetItems())
                {
                    if (itm.Jid.ToString().StartsWith("econference"))
                    {
                        System.Diagnostics.Debug.WriteLine(itm.Jid);
                        roomListBox.Items.Add((string)itm.Jid);
                    }
                }
            }
        }

        private void joinRoom_Click(object sender, RoutedEventArgs e)
        {
            if (roomListBox.SelectedItem != null)
                this.ConnectToRoom(GloApp.xmppClient, (string)roomListBox.SelectedItem, GloApp.xmppClient.Username, false);
            else 
                MessageBox.Show("You must select a chat room");
        }

        public void ConnectToRoom(XmppClient xmppClient, Jid roomJid, string nickname, bool createRoom)
        {
            _roomJid = roomJid;
            _nickname = nickname;

            mm = new MucManager(xmppClient);
            
            if (roomJid != null)
            {
                mm.EnterRoom(roomJid, nickname);
                if(createRoom)
                    mm.RequestInstantRoom(roomJid);
                NavigationService.Navigate(new Uri("/GroupDiscussionPivot.xaml?Room=" + roomJid, UriKind.Relative));
            }
        }

        private void refreshRoom(object sender, System.EventArgs e)
        {
            roomListBox.Items.Clear();
            dm = new DiscoManager(GloApp.xmppClient);
            dm.DiscoverItems(service, new EventHandler<IqEventArgs>(DiscoItemsResult));
        }
    }
}