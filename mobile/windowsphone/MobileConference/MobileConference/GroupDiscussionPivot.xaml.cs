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
using Microsoft.Phone.Shell;
using System.Windows.Navigation;
using System.Text.RegularExpressions;

using Matrix;
using Matrix.Xmpp;
using Matrix.Xmpp.Client;
using Uri = System.Uri;
using Matrix.Xml;



namespace MobileConference
{
    public partial class GroupDiscussionPivot : PhoneApplicationPage
    {
        public App GloApp = (App.Current as App);
        private Jid roomJid;
        private bool moderator = false;
        private Dictionary<string, string> propertiesList = new Dictionary<string, string>();

        private static void RegisterCustomElements()
        {
            Factory.RegisterElement<backlog>("MobileConfXMPP", "backlog");
            Factory.RegisterElement<userstory>("MobileConfXMPP", "userstory");
            Factory.RegisterElement<estimation>("MobileConfXMPP", "estimation");
            Factory.RegisterElement<deck>("MobileConfXMPP", "deck");
            Factory.RegisterElement<card>("MobileConfXMPP", "card");
            Factory.RegisterElement<startPlanningPoker>("MobileConfXMPP", "startPlanningPoker");

            Factory.RegisterElement<properties>("http://www.jivesoftware.com/xmlns/xmpp/properties", "properties");
            Factory.RegisterElement<property>("http://www.jivesoftware.com/xmlns/xmpp/properties", "property");
            Factory.RegisterElement<value>("http://www.jivesoftware.com/xmlns/xmpp/properties", "value");
        }

        #region extensionOnlyWP

        public class backlog : XmppXElement
        {
            public backlog()
                : base("MobileConfXMPP", "backlog")
            {
            }

            public void Addstory(userstory story)
            {
                Add(story);
            }

            public IEnumerable<userstory> Getstory()
            {
                return Elements<userstory>();
            }
        }

        public class userstory : XmppXElement
        {
            public userstory()
                : base("MobileConfXMPP", "userstory")
            {
            }

            public string storytext
            {
                get { return GetTag("storytext"); }
                set { SetTag("storytext", value); }
            }

            public string estimate
            {
                get { return GetTag("estimate"); }
                set { SetTag("estimate", value); }
            }
        }

        public class startPlanningPoker : XmppXElement
        {
            public startPlanningPoker()
                : base("MobileConfXMPP", "startPlanningPoker")
            {
            }
        }

        public class deck : XmppXElement
        {
            public deck()
                : base("MobileConfXMPP", "deck")
            {
            }

            public void AddCard(card card)
            {
                Add(card);
            }

            public IEnumerable<card> GetCard()
            {
                return Elements<card>();
            }
        }

        public class card : XmppXElement
        {
            public card()
                : base("MobileConfXMPP", "card")
            {
            }
        }

        public class estimation : XmppXElement
        {
            public estimation()
                : base("MobileConfXMPP", "estimation")
            {
            }

            public string type
            {
                get { return GetAttribute("type"); }
                set { SetAttributeValue("type", value); }
            }

            public string estimate
            {
                get { return GetTag("estimate"); }
                set { SetTag("estimate", value); }
            }
        }

        #endregion

        #region extensionOnlyeConference

        public class properties : XmppXElement
        {
            public properties()
                : base("http://www.jivesoftware.com/xmlns/xmpp/properties", "properties")
            {
            }

            public void Addproperty(property pro)
            {
                Add(pro);
            }

            public IEnumerable<property> Getproperties()
            {
                return Elements<property>();
            }  
        }

        public class property : XmppXElement
        {
            public property()
                : base("http://www.jivesoftware.com/xmlns/xmpp/properties", "property")
            {           
            }

            public string name
            {
                get { return GetTag("name"); }
                set { SetTag("name", value); }
            }

            public value value
            {
                get { return Element<value>(); }
                set { Replace<value>(value); }
            }
        }

        public class value : XmppXElement
        {
            public value()
                : base("http://www.jivesoftware.com/xmlns/xmpp/properties", "value")
            {
            }
           
            public string type
            {
               get { return GetAttribute("type"); }
               set { SetAttributeValue("type", value); } 
            }     
        }

        #endregion

        public class PropertiesMsg : Message
        {
            public PropertiesMsg()
            {
                GenerateId();
            }

            public backlog backlog
            {
                get { return Element<backlog>(); }
                set { Replace(value); }
            }

            public deck deck
            {
                get { return Element<deck>(); }
                set { Replace(value); }
            }

            public startPlanningPoker startPlanningPoker
            {
                get { return Element<startPlanningPoker>(); }
                set { Replace(value); }
            }

            public estimation estimation
            {
                get { return Element<estimation>(); }
                set { Replace(value); }
            }

            public properties properties
            {
                get { return Element<properties>(); }
                set { Replace(value); }
            }
        }


        public GroupDiscussionPivot()
        {
            InitializeComponent();
            RegisterCustomElements();
            GloApp.xmppClient.OnPresence += new EventHandler<PresenceEventArgs>(xmppClient_OnPresence);
            GloApp.xmppClient.OnMessage += new EventHandler<MessageEventArgs>(xmppClient_OnMessage);

            ((Resources["AppBarChat"] as ApplicationBar).Buttons[1] as ApplicationBarIconButton).Click += new EventHandler(ClearChatArea);  
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            roomJid = this.NavigationContext.QueryString["Room"];

            if (moderator)
                //Enable PlanningPoker button in ApplicationBar
                ((Resources["AppBarChat"] as ApplicationBar).Buttons[2] as ApplicationBarIconButton).IsEnabled = true;
            
            GloApp.isInPlanningPoker = false;

            Dispatcher.BeginInvoke(() =>
            {
                lastRowList();
            });
        }

        private void GroupChatPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            switch ((sender as Pivot).SelectedIndex)
            {
                //Group Chat Pivot              
                case 0:
                    this.ApplicationBar = this.Resources["AppBarChat"] as ApplicationBar;               
                    lastRowList();           
                    break;

                //Agenda Pivot
                case 1:
                    if (!moderator)
                    {
                        this.ApplicationBar = this.Resources["Nothing"] as ApplicationBar;       
                    }
                    else
                    {
                        this.ApplicationBar = this.Resources["AppBarAgenda"] as ApplicationBar;
                        SelectItem.Visibility = Visibility.Visible;
                        wrtItem.Visibility = Visibility.Visible;
                        itemToAdd.Visibility = Visibility.Visible;
                        agendaList.ItemContainerStyle = null;
                    }    
                    break;

                //Decisions Place Pivot 
                case 2:
                    this.ApplicationBar = this.Resources["Nothing"] as ApplicationBar;
                    if (moderator)
                    {
                        writeDecision.Visibility = Visibility.Visible;
                        decision.Visibility = Visibility.Collapsed;
                    }
                    break;

                //Who's on Pivot
                case 3:
                    this.ApplicationBar = this.Resources["Nothing"] as ApplicationBar;
                    foreach (UIElement child in PresencePanel.Children.ToList())
                        PresencePanel.Children.Remove(child);
                    
                    PresenceRoom();
                    break;           
            }

            //Check if there are updates on the agenda
            if (itemsToAdd.Count > 0 || itemsToDelete.Count > 0)
                sendAgenda();
          
            //Check if the subject has been changed
            if (agendaList.SelectedItem != null)
            {
                if (moderator)
                {
                    if (agendaList.SelectedItem.ToString() != GloApp.subject)
                        sendSubject(agendaList.SelectedIndex.ToString());        
                }
            }
            
            //Check if there are updates on the Decisions
            if (!writeDecision.Text.Equals(oldDecision))
                sendDecision();

            System.Diagnostics.Debug.WriteLine(GroupChatPivot.SelectedIndex);
            System.Diagnostics.Debug.WriteLine((GroupChatPivot.SelectedItem as PivotItem).Header);
        }

        
        void xmppClient_OnMessage(object sender, MessageEventArgs e)
        {
            if (e.Message.Delay == null)
                TimeMsg = DateTime.Now.ToString("HH:mm:ss");
            else
                TimeMsg = e.Message.Delay.Stamp.TimeOfDay.ToString().Substring(0, 8);

            if (e.Message.Element<backlog>() != null)
                handleBacklogWP(e.Message);

            if (e.Message.Element<startPlanningPoker>() != null)
            {
                if(!moderator)
                    enableCardSelection(e.Message);
            }

            if (e.Message.Element<deck>() != null)
            {
                GloApp.cardDesk.Clear();
                foreach (var child in e.Message.Element<deck>().GetCard())
                    GloApp.cardDesk.Add(child.Value);
            }

            if (e.Message.Element<GroupDiscussionPivot.estimation>() != null)
            {
                // Disable PlanningPoker button in ApplicationBar
                ((Resources["AppBarChat"] as ApplicationBar).Buttons[2] as ApplicationBarIconButton).IsEnabled = false;

                var es = e.Message.Element<estimation>();
                if (es.type == "accepted")
                    infoEvaluation(e.Message, true);
                else
                    infoEvaluation(e.Message, false);

                if (GloApp.subject == null)
                    return;

                if (!GloApp.isInPlanningPoker)
                {
                    // Estimates Accepted --> Save finalEstimate in Backlog
                    if (es.type == "accepted")
                        GloApp.backlogWP[GloApp.subject] = es.estimate;
                    GloApp.estimates.Clear();
                }
            }

            if (e.Message.Element<properties>() != null)
            {
                var pr = e.Message.Element<properties>();
                if (pr.Element<property>() != null)
                {
                   
                    foreach(var child in pr.Getproperties())
                    {
                        System.Diagnostics.Debug.WriteLine(child.name+"--> "+child.GetTag("value"));
                        propertiesList.Add(child.name, child.GetTag("value"));
                    }

                    if (propertiesList["ExtensionName"] == "ItemList")
                        handleAgenda(propertiesList["Items"]);

                    if (propertiesList["ExtensionName"] == "WhiteBoardChanged")
                        handleDecision(propertiesList["WhiteBoardText"]);

                    if (propertiesList["ExtensionName"] == "CurrentAgendaItem")
                        handleSubject(e.Message);

                    if (propertiesList["ExtensionName"] == "ChangedSpecialPrivilege")
                        handleChangeSpecialPrivilege();


                    if (propertiesList["ExtensionName"] == "backlog")
                        handleBacklog(propertiesList["backlog"]);

                    if (propertiesList["ExtensionName"] == "estimate-session")
                    {
                        if (propertiesList["status"] == "REPEATED")
                            infoEvaluation(e.Message,false);
                        
                        if (propertiesList["status"] == "CREATED")
                        {
                            GloApp.storyId = propertiesList["storyId"];
                            enableCardSelection(e.Message);
                        }
                    }

                    if (propertiesList["ExtensionName"] == "card-selection")
                    {
                        if (!GloApp.isInPlanningPoker)
                        {
                            if (GloApp.estimates.ContainsKey(e.Message.From.Resource))
                                GloApp.estimates[e.Message.From.Resource].estimate = propertiesList["cardValue"];
                            else
                                GloApp.estimates.Add(e.Message.From.Resource, new App.rowTable((short)(GloApp.estimates.Count), propertiesList["cardValue"]));   
                        }
                    }

                    if (propertiesList["ExtensionName"] == "estimate-assigned")
                    {
                        infoEvaluation(e.Message,true);
                        // Estimates Accepted --> Save finalEstimate in Backlog

                        // Disable PlanningPoker button in ApplicationBar
                        ((Resources["AppBarChat"] as ApplicationBar).Buttons[2] as ApplicationBarIconButton).IsEnabled = false;

                        if (!GloApp.isInPlanningPoker)
                        {
                            try
                            {
                                GloApp.backlog[propertiesList["storyId"]].estimate = propertiesList["estimate"];
                            }
                            catch (KeyNotFoundException)
                            {
                                System.Diagnostics.Debug.WriteLine("Key is not found");
                            }
                            GloApp.estimates.Clear();
                        }
                    }

                    if (propertiesList["ExtensionName"] == "deck")
                        handleCardDeck();

                    propertiesList.Clear();

                    return;   
                }   
            }
   
            if (e.Message.Body != null)
            {
                if (GloApp.xmppClient.Username.Equals(e.Message.From.Resource))
                {
                    MyMessage m = new MyMessage();
                    m.setName(e.Message.From.Resource);
                    m.setText(e.Message.Body);
                    m.setColour(BrushFromColorName(getColor(e.Message.From.Resource)));
                    m.setTime(TimeMsg);
                    GroupChat.Items.Add(m);
                }
                else
                {
                    YourMessage m = new YourMessage();
                    m.setName(e.Message.From.Resource);
                    m.setText(e.Message.Body);
                    m.setColour(BrushFromColorName(getColor(e.Message.From.Resource)));
                    m.setTime(TimeMsg);
                    GroupChat.Items.Add(m);
                }
                lastRowList();
            }
        }

        private void xmppClient_OnPresence(object sender, PresenceEventArgs e)
        {
            TimeMsg = DateTime.Now.ToString("HH:mm:ss");
            var u = e.Presence.MucUser;

            if (e.Presence.Type == PresenceType.error || e.Presence.From.Resource == null)
            {
                System.Diagnostics.Debug.WriteLine("PresenceError");
                return;
            }

            if (GloApp.pres.ContainsKey(e.Presence.From.Resource))
            {
                if (e.Presence.Type == PresenceType.unavailable)
                {
                    GloApp.pres.Remove(e.Presence.From.Resource);
                    YourMessage m = new YourMessage();
                    m.setName(e.Presence.From.Resource);
                    m.setText("I have just left the room");
                    m.setColour(BrushFromColorName(getColor(e.Presence.From.Resource)));
                    m.setTime(TimeMsg);
                    GroupChat.Items.Add(m);
                    lastRowList();
                }
            }
            else
            {
                try
                {
                    if (GloApp.pres.ContainsKey(GloApp.xmppClient.Username) && !u.Item.Role.ToString().Equals("none"))
                    {
                        YourMessage m = new YourMessage();
                        m.setName(e.Presence.From.Resource);
                        m.setText("I have just entered the room");
                        m.setColour(BrushFromColorName(getColor(e.Presence.From.Resource)));
                        m.setTime(TimeMsg);
                        GroupChat.Items.Add(m);
                        lastRowList();
                    }

                    if (!u.Item.Role.ToString().Equals("none"))
                        GloApp.pres.Add(e.Presence.From.Resource, u.Item.Role);
                }
                catch (NullReferenceException) { }
            }

            if (GloApp.pres.ContainsKey(GloApp.xmppClient.Username))
                if (GloApp.pres[GloApp.xmppClient.Username].ToString().Equals("moderator"))
                {
                    moderator = true;
                    // There is need to enable the PlanningPoker button in ApplicationBar to the moderator
                    ((Resources["AppBarChat"] as ApplicationBar).Buttons[2] as ApplicationBarIconButton).IsEnabled = true;
                }
        }

        private void PresenceRoom()
        {
            // ContentPresence grid: it includes table heading, the users and their roles

            Grid ContentPresence = new Grid()
            {
                HorizontalAlignment = HorizontalAlignment.Left,
                VerticalAlignment = VerticalAlignment.Top,
                Margin = new Thickness(10)
            };

            ContentPresence.ColumnDefinitions.Add(new ColumnDefinition());
            ContentPresence.RowDefinitions.Add(new RowDefinition());
            ContentPresence.RowDefinitions.Add(new RowDefinition()
            {
                Height = new GridLength(450)
            });

            // HeadingChatPres grid

            Grid HeadingChatPres = new Grid();

            HeadingChatPres.ColumnDefinitions.Add(new ColumnDefinition()
            {
                Width = new GridLength(200)
            });

            HeadingChatPres.ColumnDefinitions.Add(new ColumnDefinition()
            {
                Width = new GridLength(200)
            });

            HeadingChatPres.RowDefinitions.Add(new RowDefinition());

            Border backgr = new Border()
            {
                Background = new SolidColorBrush(Colors.Gray),
            };

            Grid.SetColumnSpan(backgr, 2);
            Grid.SetRowSpan(backgr, 1);
            HeadingChatPres.Children.Add(backgr);

            Border bh = new Border()
            {
                BorderThickness = new Thickness(3, 3, 1, 3),
                BorderBrush = new SolidColorBrush(Colors.White)
            };

            Border bh2 = new Border()
            {
                BorderThickness = new Thickness(0, 3, 3, 3),
                BorderBrush = new SolidColorBrush(Colors.White)
            };

            Grid.SetColumn(bh, 0);
            Grid.SetColumn(bh2, 1);
            Grid.SetRow(bh, 0);
            Grid.SetRow(bh2, 0);
            HeadingChatPres.Children.Add(bh);
            HeadingChatPres.Children.Add(bh2);

            TextBlock heading = new TextBlock()
            {
                Text = "User",
                FontSize = 22,
                FontWeight = FontWeights.Bold,
                TextAlignment = TextAlignment.Center
            };

            TextBlock heading2 = new TextBlock()
            {
                Text = "Role",
                FontSize = 22,
                FontWeight = FontWeights.Bold,
                TextAlignment = TextAlignment.Center
            };

            Grid.SetRow(heading, 0);
            Grid.SetColumn(heading, 0);
            Grid.SetRow(heading2, 0);
            Grid.SetColumn(heading2, 1);
            HeadingChatPres.Children.Add(heading);
            HeadingChatPres.Children.Add(heading2);

            //Grid chatPres: it includes users and their roles

            Grid chatPres = new Grid()
            {
                HorizontalAlignment = HorizontalAlignment.Center,
                VerticalAlignment = VerticalAlignment.Top
            };


            chatPres.ColumnDefinitions.Add(new ColumnDefinition()
            {
                Width = new GridLength(200)
            });

            chatPres.ColumnDefinitions.Add(new ColumnDefinition()
            {
                Width = new GridLength(200)
            });

            //sv ScrollViewer

            ScrollViewer sv = new ScrollViewer()
            {
                Height = 450,
                Width = 400,
            };

            sv.Content = chatPres;

            Grid.SetColumn(HeadingChatPres, 0);
            Grid.SetColumn(sv, 0);
            Grid.SetRow(HeadingChatPres, 0);
            Grid.SetRow(sv, 1);

            ContentPresence.Children.Add(HeadingChatPres);
            ContentPresence.Children.Add(sv);
            PresencePanel.Children.Add(ContentPresence);
            short i = 0;
            foreach (var pair in GloApp.pres)
            {
                chatPres.RowDefinitions.Add(new RowDefinition());

                Border b = new Border()
                {
                    BorderThickness = new Thickness(1, 0, 0, 1),
                    BorderBrush = new SolidColorBrush(Colors.White)
                };

                Border b2 = new Border()
                {
                    BorderThickness = new Thickness(1, 0, 1, 1),
                    BorderBrush = new SolidColorBrush(Colors.White)
                };

                Grid.SetColumn(b, 0);
                Grid.SetColumn(b2, 1);
                Grid.SetRow(b, i);
                Grid.SetRow(b2, i);
                chatPres.Children.Add(b);
                chatPres.Children.Add(b2);

                ScrollViewer sv1 = new ScrollViewer()
                {
                    HorizontalScrollBarVisibility = ScrollBarVisibility.Visible,
                    VerticalScrollBarVisibility = ScrollBarVisibility.Disabled
                };

                ScrollViewer sv2 = new ScrollViewer()
                {
                    HorizontalScrollBarVisibility = ScrollBarVisibility.Visible,
                    VerticalScrollBarVisibility = ScrollBarVisibility.Disabled
                };

                TextBlock txtU = new TextBlock()
                {
                    Text = pair.Key,
                    FontSize = 22,
                    TextAlignment = TextAlignment.Center
                };

                TextBlock txtR = new TextBlock()
                {
                    Text = pair.Value.ToString(),
                    FontSize = 22,
                    TextAlignment = TextAlignment.Center
                };

                Grid.SetColumn(sv1, 0);
                Grid.SetRow(sv1, i);
                chatPres.Children.Add(sv1);
                sv1.Content = txtU;
                Grid.SetColumn(sv2, 1);
                Grid.SetRow(sv2, i);
                chatPres.Children.Add(sv2);
                sv2.Content = txtR;
                i++;
            }
        }

        #region Group Chat

        Dictionary<string, string> bind = new Dictionary<string, string>();
        List<string> colors = GetColors();
        string TimeMsg;

        private void ClearChatArea(object sender, System.EventArgs e)
        {
            msg.Text = string.Empty;
        }

        private void lastRowList()
        {
            GroupChat.SelectedIndex = GroupChat.Items.Count - 1;
            GroupChat.SelectedIndex = -1;
        }

        private string getColor(string user)
        {
            string col;
            if (colors.Count == 0)
                colors = GetColors();

            if (bind.ContainsKey(user))
                col = bind[user];
            else
            {
                Random r = new Random();
                col = colors.ElementAt(r.Next(colors.Count));
                colors.Remove(col);
                bind.Add(user, col);
            }
            return col;
        }

        public static List<string> GetColors()
        {
            return new List<string>() { "#1BA1E2", "#A05000", "#339933", "#E671B8", "#A200FF", "#E51400", "#4b4b4b", "#00ABA9", "#A2C139", "#D80073", "#F09609", "#1080DD", "#FF6600" };
        }

        public SolidColorBrush BrushFromColorName(string colorName)
        {
            string s = "<SolidColorBrush xmlns='http://schemas.microsoft.com/winfx/2006/xaml/presentation' Color='" + colorName + "'/>";
            return System.Windows.Markup.XamlReader.Load(s) as SolidColorBrush;
        }

        private void sendmsg(object sender, System.EventArgs e)    
        {
            if (msg.Text.Length > 0)
            {
                var mess = new Matrix.Xmpp.Client.Message
                {
                    Type = MessageType.groupchat,
                    To = roomJid,
                    Body = msg.Text
                };

                GloApp.xmppClient.Send(mess);
            }      
        }

        private void exitRoom(object sender, System.EventArgs e)
        {
            GloApp.pres.Clear();
            GloApp.backlogWP.Clear();
            GloApp.backlog.Clear();
            GloApp.decisionsPlace.Clear();
            GloApp.cardDesk.Clear();
            GloApp.cardDesk.AddRange(new List<string>() { "?", "0", "1", "2", "3", "5", "8", "20", "40", "100" });
            GloApp.subject = null;
            GloApp.xmppClient.OnMessage -= new EventHandler<MessageEventArgs>(xmppClient_OnMessage);
            GloApp.xmppClient.OnPresence -= new EventHandler<PresenceEventArgs>(xmppClient_OnPresence);
            NavigationService.GoBack();
        }

        private void planningPoker(object sender, System.EventArgs e)
        {
            NavigationService.Navigate(new Uri("/PlanningPokerPivot.xaml?Room=" + roomJid + "&Mod=" + moderator, UriKind.Relative));
        }

        #endregion

        #region Agenda

        List<string> itemsToDelete = new List<string>();
        List<string> itemsToAdd = new List<string>();
        List<string> ItemList = new List<string>(); //Added it just for eConference compatibility

        private void addItem(object sender, System.EventArgs e)
        {
            if (!itemToAdd.Text.Equals(string.Empty))
                if (GloApp.backlogWP.ContainsKey(itemToAdd.Text))
                    MessageBox.Show("You have already added this item");
                else
                {
                    agendaList.Items.Add(itemToAdd.Text);
                    GloApp.backlogWP.Add(itemToAdd.Text, "?");
                    ItemList.Add(itemToAdd.Text);

                    if (itemsToDelete.Contains(itemToAdd.Text))
                        itemsToDelete.Remove(itemToAdd.Text);
                    else
                        itemsToAdd.Add(itemToAdd.Text);
                    itemToAdd.Text = string.Empty;
                }
            else
                MessageBox.Show("You must enter an item");
        }

        private void deleteItem(object sender, System.EventArgs e)
        {
            if (agendaList.SelectedItem != null)
            {
                if (itemsToAdd.Contains(agendaList.SelectedItem.ToString()))
                    itemsToAdd.Remove(agendaList.SelectedItem.ToString());
                else
                    itemsToDelete.Add(agendaList.SelectedItem.ToString());

               
                ItemList.Remove(agendaList.SelectedItem.ToString());
                GloApp.backlogWP.Remove(agendaList.SelectedItem.ToString());
                agendaList.Items.Remove(agendaList.SelectedItem);
            }
            else
                MessageBox.Show("You must select an item to delete");
        }
        
        private void handleAgenda(string agendaItems)
        {
            string[] split;
            split = agendaItems.Split(new string[] { "//" }, StringSplitOptions.RemoveEmptyEntries);
            agendaList.Items.Clear();
            ItemList.Clear();

            foreach (string s in split)
            { 
                agendaList.Items.Add(s);
                ItemList.Add(s);        
            }

            if (ItemList.Contains(GloApp.subject))
                agendaList.SelectedIndex = ItemList.IndexOf(GloApp.subject);
            else
                itemDisc.Text = string.Empty;
        }

        private void handleBacklogWP(Matrix.Xmpp.Client.Message msg)
        {
            GloApp.backlogWP.Clear();
            agendaList.Items.Clear();
            ItemList.Clear();
            foreach (var child in msg.Element<backlog>().Getstory())
            {
                GloApp.backlogWP.Add(child.storytext, child.estimate);
                agendaList.Items.Add(child.storytext);
                ItemList.Add(child.storytext);
            }

            if (GloApp.subject != null)
            {
                if (GloApp.backlogWP.ContainsKey(GloApp.subject))
                    agendaList.SelectedItem = GloApp.subject;
                else
                {
                    itemDisc.Text = string.Empty;
                    GloApp.subject = null;            
                }
            }
        }
        
        private void sendAgenda()
        { 
            backlog back = new backlog();
            foreach (var story in GloApp.backlogWP)
                back.Addstory(new userstory()
                {
                    storytext = story.Key,
                    estimate = story.Value
                });

            var mess = new PropertiesMsg
            {
                Type = MessageType.groupchat,
                To = roomJid,
                backlog = back
            };

            GloApp.xmppClient.Send(mess);
            clearAll();
        }

        private void sendSubject(string sub)
        {
            properties ps = new properties();
            ps.Addproperty(new property()
            {
                name = "ItemId",
                value = new value { type = "string", Value = sub }
            });

            ps.Addproperty(new property()
            {
                name = "ExtensionName",
                value = new value { type = "string", Value = "CurrentAgendaItem" }
            });

            var mess = new PropertiesMsg
            {
                Type = MessageType.groupchat,
                To = roomJid,
                properties = ps
            };

            GloApp.xmppClient.Send(mess);

        }

        private void handleSubject(Matrix.Xmpp.Client.Message msg) 
        {
            try
            {
                GloApp.subject = ItemList[Convert.ToInt32(propertiesList["ItemId"])];
                agendaList.SelectedIndex = Convert.ToInt32(propertiesList["ItemId"]);
                //Groupchat
                itemDisc.Text = GloApp.subject;
               
                if (msg.From.Resource != null)
                {
                    if (GloApp.xmppClient.Username.Equals(msg.From.Resource))
                    {
                        MyMessage m = new MyMessage();
                        m.setName(msg.From.Resource);
                        m.setText("I have set up the topic to: " + ItemList[Convert.ToInt32(propertiesList["ItemId"])]);
                        m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                        m.setTime(TimeMsg);
                        GroupChat.Items.Add(m);
                    }
                    else
                    {
                        YourMessage m = new YourMessage();
                        m.setName(msg.From.Resource);
                        m.setText("I have set up the topic to: " + ItemList[Convert.ToInt32(propertiesList["ItemId"])]);
                        m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                        m.setTime(TimeMsg);
                        GroupChat.Items.Add(m);
                    }
                    lastRowList();
                }
            }
            catch (FormatException)
            {
                System.Diagnostics.Debug.WriteLine(propertiesList["ItemId"] + "isn't a number");
            }
            catch (ArgumentOutOfRangeException)
            {
                System.Diagnostics.Debug.WriteLine("Index is out of range");
            }
        }

        private void clearAll()
        {
            itemsToAdd.Clear();
            itemsToDelete.Clear();
        }

        #endregion

        #region Decisions Place

        private string oldDecision=string.Empty;

        private void sendDecision()
        {

            properties ps = new properties();
            ps.Addproperty(new property()
            {
                name = "ExtensionName",
                value = new value { type = "string", Value = "WhiteBoardChanged" }
            });

            ps.Addproperty(new property()
            {
                name = "WhiteBoardText",
                value = new value { type = "string", Value = writeDecision.Text }
            });

            ps.Addproperty(new property()
            {
                name = "From",
                value = new value { type = "string", Value = GloApp.xmppClient.Username.ToString() + "@" + GloApp.xmppClient.XmppDomain.ToString() }
            });

            var mess = new PropertiesMsg
            {
                Type = MessageType.groupchat,
                To = roomJid,
                properties = ps
            };

            GloApp.xmppClient.Send(mess);
        }

        private void handleChangeSpecialPrivilege()
        {
            string userId = GloApp.xmppClient.Username.ToString() + "@" + GloApp.xmppClient.XmppDomain.ToString();
            if (propertiesList["UserId"] == userId && propertiesList["SpecialRole"] == "SCRIBE")
            {
                if (propertiesList["RoleAction"] == "GRANT")
                {
                    writeDecision.Visibility = Visibility.Visible;
                    decision.Visibility = Visibility.Collapsed;
                }
                else
                {
                    writeDecision.Visibility = Visibility.Collapsed;
                    decision.Visibility = Visibility.Visible;
                }
            }
        }

        private void handleDecision(string dec)
        {
            oldDecision = dec;
            writeDecision.Text = dec;
            decisionText.Text = dec;
        }

        #endregion

        #region PlanningPoker

        private void handleBacklog(string userstories)
        {
            GloApp.backlog.Clear();
            for (int i = 0; i < GetSubStrings(userstories, "{id}", "{/id}").Count(); i++)
                GloApp.backlog.Add(GetSubStrings(userstories, "{id}", "{/id}").ElementAt(i),
                    new App.backlogInfo()
                    {
                        storytext = GetSubStrings(userstories, "{story-text}", "{/story-text}").ElementAt(i),
                        estimate = GetSubStrings(userstories, "{estimate}", "{/estimate}").ElementAt(i),
                    });

            agendaList.Items.Clear();
            ItemList.Clear();

            foreach (var s in GloApp.backlog)
            {
                agendaList.Items.Add(s.Value.storytext);
                ItemList.Add(s.Value.storytext);
            }

            if (ItemList.Contains(GloApp.subject))
                agendaList.SelectedIndex = ItemList.IndexOf(GloApp.subject);
            else
                itemDisc.Text = string.Empty;

            foreach (var b in GloApp.backlog)
                System.Diagnostics.Debug.WriteLine(b.Key + " " + b.Value.storytext + " " + b.Value.estimate);
        }

        private IEnumerable<string> GetSubStrings(string input, string start, string end)
        {
            Regex r = new Regex(Regex.Escape(start) + "(.*?)" + Regex.Escape(end));
            MatchCollection matches = r.Matches(input);
            foreach (Match match in matches)
                yield return match.Groups[1].Value;
        }

        private void handleCardDeck()
        {
            string[] split;
            GloApp.cardDesk.Clear();

            split = propertiesList["deck"].Split(new string[] { "{hidden-card}" }, StringSplitOptions.RemoveEmptyEntries);

            foreach (var child in GetSubStrings(split[0], "{card-value}", "{/card-value}"))
                GloApp.cardDesk.Add(child);     
        }

        private void enableCardSelection(Matrix.Xmpp.Client.Message msg)
        {
            //Enable PlanningPoker button in ApplicationBar
            ((Resources["AppBarChat"] as ApplicationBar).Buttons[2] as ApplicationBarIconButton).IsEnabled = true;

            if (GloApp.xmppClient.Username.Equals(msg.From.Resource))
            {
                MyMessage m = new MyMessage();
                m.setName(msg.From.Resource);
                m.setText("Card selection is enabled");
                m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                m.setTime(TimeMsg);
                GroupChat.Items.Add(m);
            }
            else
            {
                YourMessage m = new YourMessage();
                m.setName(msg.From.Resource);
                m.setText("Card selection is enabled");
                m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                m.setTime(TimeMsg);
                GroupChat.Items.Add(m);
            }

            lastRowList();
        }

        private void infoEvaluation(Matrix.Xmpp.Client.Message msg, bool estimateAssigned)
        {
            string info=string.Empty;
            if (GloApp.subject == null)
                return;

            //Check if the meeting isn't with eConference
            if (propertiesList.Count == 0)
            {
                if (estimateAssigned)
                    info = "Story '" + GloApp.subject + "' has been estimated with: " + msg.Element<estimation>().estimate;
                else
                    info = "Moderator established to re-estimate '" + GloApp.subject + "'";
            }
            else
            {
                if (estimateAssigned)
                    info = "Story '" + GloApp.subject + "' has been estimated with: " + propertiesList["estimate"];
                else
                    info = "Moderator established to re-estimate '" + GloApp.subject + "'";
            }

            if (GloApp.xmppClient.Username.Equals(msg.From.Resource))
            {
                MyMessage m = new MyMessage();
                m.setName(msg.From.Resource);
                m.setText(info);
                m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                m.setTime(TimeMsg);
                GroupChat.Items.Add(m);
            }
            else
            {
                YourMessage m = new YourMessage();
                m.setName(msg.From.Resource);
                m.setText(info);
                m.setColour(BrushFromColorName(getColor(msg.From.Resource)));
                m.setTime(TimeMsg);
                GroupChat.Items.Add(m);
            }

            lastRowList();
        }

        #endregion
    }
}
    
