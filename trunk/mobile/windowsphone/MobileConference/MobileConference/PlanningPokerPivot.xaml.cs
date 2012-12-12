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
using System.Windows.Media.Imaging;
using System.Windows.Navigation;

using Matrix;
using Matrix.Xmpp;
using Matrix.Xmpp.Client;
using Uri = System.Uri;


namespace MobileConference
{
    public partial class PlanningPokerPivot : PhoneApplicationPage
    {
        public App GloApp = (App.Current as App);
        private Jid roomJid;
        private bool moderator = false;
        private bool inEstimatePivot = false;
        string[] colName = new string[2];
        private Dictionary<string, string> propertiesList = new Dictionary<string, string>();

        public PlanningPokerPivot()
        {
            InitializeComponent();
            GloApp.xmppClient.OnMessage += new EventHandler<MessageEventArgs>(xmppClient_OnMessage);
            colName[0] = "Story Text";
            colName[1] = "Estimate";
            SkeletonTable(backlogEstimate, BacklogPanel, colName);

            colName[0] = "Participant";
            SkeletonTable(Estimates, EstimatesList, colName);

            if (GloApp.estimates.Count != 0)
            {
                foreach (var newRow in GloApp.estimates)
                {
                    addRow(Estimates, newRow.Key, newRow.Value.estimate, false, i);
                    i++;
                }
            }
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            base.OnNavigatedTo(e);
            roomJid = this.NavigationContext.QueryString["Room"];
            itemDisc.Text = GloApp.subject;
            
            if (this.NavigationContext.QueryString["Mod"].Equals("True"))
            {
                moderator = true;
                //Cards Pivot
                Cards.Header = "Card Deck";
                sendEstimate.Visibility = Visibility.Collapsed;
                enableSel.Visibility = Visibility.Visible;

                //Estimates List Pivot
                Estimate.Visibility = Visibility.Visible;
                SetEstimate.Visibility = Visibility.Visible;
                accept.Visibility = Visibility.Visible;
                repeat.Visibility = Visibility.Visible;
            }
            else
            {  //Disable the back button
                ((Resources["AppBarDeck"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = false;
            }

            if (GloApp.deckChange)
                showCard();

            GloApp.isInPlanningPoker = true;      
        }

        private void PlanningPokerPivot_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            inEstimatePivot = false;
            
            switch ((sender as Pivot).SelectedIndex)
            {               
                //Card Deck Pivot              
                case 0: 
                    
                    if (moderator)
                        this.ApplicationBar = this.Resources["AppBarDeckMod"] as ApplicationBar;

                    showCard();
                    break;
                
                //Estimates List Pivot 
                case 1:

                    this.ApplicationBar = this.Resources["AppBarDeck"] as ApplicationBar;
                    inEstimatePivot = true;
                    break;

                //Backlog Pivot
                case 2:

                    this.ApplicationBar = this.Resources["AppBarDeck"] as ApplicationBar;
                    updateBacklogView();
                    break;
            }
        }

        void xmppClient_OnMessage(object sender, MessageEventArgs e)
        {
            if (e.Message.Element<GroupDiscussionPivot.estimation>() != null)
            {
                evaluation(e.Message);
            }

            if (e.Message.Element<GroupDiscussionPivot.properties>() != null)
            {
                var pr = e.Message.Element<GroupDiscussionPivot.properties>();
                if (pr.Element<GroupDiscussionPivot.property>() != null)
                {       
                    foreach (var child in pr.Getproperties())
                    {
                        System.Diagnostics.Debug.WriteLine(child.name + "--> " + child.GetTag("value"));
                        propertiesList.Add(child.name, child.GetTag("value"));       
                    }

                    if (propertiesList["ExtensionName"] == "card-selection") //incoming estimates
                        {
                            if (GloApp.estimates.ContainsKey(e.Message.From.Resource))
                            {
                                GloApp.estimates[e.Message.From.Resource].estimate = propertiesList["cardValue"];
                                if (moderator)
                                    editEstimate(e.Message);
                            }
                            else
                                newEstimate(e.Message);
                            if (moderator)
                                SetEstimate.Text = CalcAverageEst();
                        }

                    if (propertiesList["ExtensionName"] == "estimate-assigned")
                    {
                        displayEstimates();
                        displayFinalEst(true, propertiesList["estimate"]);
                        GloApp.backlog[propertiesList["storyId"]].estimate = propertiesList["estimate"];
                        updateBacklogView();

                        ((Resources["AppBarDeck"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
                        ((Resources["AppBarDeckMod"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
                        sendEstimate.IsEnabled = false;
                    }

                    if (propertiesList["ExtensionName"] == "estimate-session" && propertiesList["status"] == "REPEATED")
                    {
                            displayEstimates();
                            displayFinalEst(false, "We have not reached a consensus");
                            ((Resources["AppBarDeck"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
                            ((Resources["AppBarDeckMod"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
                            sendEstimate.IsEnabled = false;
                    }                
                    propertiesList.Clear();
                }

            }     
        }
        
        #region Estimate and Backlog

        /// <summary>
         /// 
         /// </summary>
         /// <param name="grid"></param>The Grid that cointains the estimates
         /// <param name="colName"></param>The name of the columns

        private void SkeletonTable(Grid grid, Grid panel, string[] colName)
         {
             // ContentEst grid it contains all grids

             Grid ContentEst = new Grid()
             {
                 HorizontalAlignment = HorizontalAlignment.Left,
                 VerticalAlignment = VerticalAlignment.Top,
                 Margin = new Thickness(10),
             };

             ContentEst.ColumnDefinitions.Add(new ColumnDefinition());
             ContentEst.RowDefinitions.Add(new RowDefinition());
             ContentEst.RowDefinitions.Add(new RowDefinition());

             // HeadingEstimate grid

             Grid HeadingEstimate = new Grid();

             HeadingEstimate.ColumnDefinitions.Add(new ColumnDefinition()
             {
                 Width = new GridLength(300)
             });

             HeadingEstimate.ColumnDefinitions.Add(new ColumnDefinition()
             {
                 Width = new GridLength(120)
             });

             HeadingEstimate.RowDefinitions.Add(new RowDefinition());

             Border backgr = new Border()
             {
                 Background = new SolidColorBrush(Colors.Gray),
             };

             Grid.SetColumnSpan(backgr, 2);
             Grid.SetRowSpan(backgr, 1);
             HeadingEstimate.Children.Add(backgr);

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
             HeadingEstimate.Children.Add(bh);
             HeadingEstimate.Children.Add(bh2);

             TextBlock heading = new TextBlock()
             {
                 Text = colName[0],
                 FontSize = 22,
                 FontWeight = FontWeights.Bold,
                 TextAlignment = TextAlignment.Center
             };

             TextBlock heading2 = new TextBlock()
             {
                 Text = colName[1],
                 FontSize = 22,
                 FontWeight = FontWeights.Bold,
                 TextAlignment = TextAlignment.Center
             };

             Grid.SetRow(heading, 0);
             Grid.SetColumn(heading, 0);
             Grid.SetRow(heading2, 0);
             Grid.SetColumn(heading2, 1);
             HeadingEstimate.Children.Add(heading);
             HeadingEstimate.Children.Add(heading2);

             //it contains the estimates

             grid.ColumnDefinitions.Add(new ColumnDefinition()
             {
                 Width = new GridLength(300)
             });

             grid.ColumnDefinitions.Add(new ColumnDefinition()
             {
                 Width = new GridLength(120)
             });

             //sv ScrollViewer

             ScrollViewer sv = new ScrollViewer()
             {
                 Height = 200,
                 Width = 420,
                 BorderThickness = new Thickness(1, 0, 1, 1),
                 BorderBrush = new SolidColorBrush(Colors.White)
             };

             sv.Content = grid;

             Grid.SetColumn(HeadingEstimate, 0);
             Grid.SetColumn(sv, 0);
             Grid.SetRow(HeadingEstimate, 0);
             Grid.SetRow(sv, 1);

             ContentEst.Children.Add(HeadingEstimate);
             ContentEst.Children.Add(sv);
             panel.Children.Add(ContentEst);
         }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="grid"></param> The Grid that cointains the estimates
        /// <param name="name"></param> Backlog's name or participant's name
        /// <param name="est"></param> The estimate
        /// <param name="visibility"></param> It determines if the second column must be seen or not; Moderator can always see all column
        /// <param name="indRow"></param> The index of the table where to place the new row
        
        private void addRow(Grid grid, string name, string est, bool visibility, short indRow)
         {
             System.Diagnostics.Debug.WriteLine("addRow");
             grid.RowDefinitions.Add(new RowDefinition());

             Border b = new Border()
             {
                 BorderThickness = new Thickness(0, 0, 0, 1),
                 BorderBrush = new SolidColorBrush(Colors.White)
             };

             Border b2 = new Border()
             {
                 BorderThickness = new Thickness(1, 0, 0, 1),
                 BorderBrush = new SolidColorBrush(Colors.White)
             };

             Grid.SetColumn(b, 0);
             Grid.SetColumn(b2, 1);
             Grid.SetRow(b, indRow);
             Grid.SetRow(b2, indRow);
             grid.Children.Add(b);
             grid.Children.Add(b2);
            
             ScrollViewer sv = new ScrollViewer()
             {
                 HorizontalScrollBarVisibility = ScrollBarVisibility.Visible,
                 VerticalScrollBarVisibility = ScrollBarVisibility.Disabled
             };
          
             RichTextBox txtUser = new RichTextBox()
             {
                 FontSize = 22,
                 TextWrapping = TextWrapping.Wrap,
                 TextAlignment = TextAlignment.Justify,
                 Xaml=@"<Section xmlns=""http://schemas.microsoft.com/winfx/2006/xaml/presentation"">
                 <Paragraph>
                    <Run Text=""" + name + @"""></Run>
                 </Paragraph>
                 </Section>"
             };
  
             TextBlock txtEst = new TextBlock()
             {
                 Text = est,
                 FontSize = 22,
                 TextAlignment = TextAlignment.Center
             };
            
             Grid.SetColumn(txtUser, 0);
             Grid.SetRow(txtUser, indRow);
             grid.Children.Add(txtUser);
            

             if (visibility || moderator)
             {
                 Grid.SetColumn(sv, 1);
                 Grid.SetRow(sv, indRow);
                 grid.Children.Add(sv);
                 sv.Content = txtEst;
             }
         }
        
        #endregion

        #region Card Deck
        
        private string estimate = string.Empty;

        private void showCard()
        {
            int i = 0;
            int indexCard = card.SelectedIndex;
            if (indexCard != -1)
                estimate = GloApp.cardDesk[indexCard];
            card.Items.Clear();
            foreach (var cd in GloApp.cardDesk)
            {
                Grid cardItem = new Grid();
                Image imgLogo = new Image();
                BitmapImage bitmapLogo = new BitmapImage(new Uri("images/card.png", UriKind.RelativeOrAbsolute));

                imgLogo.Source = bitmapLogo;
                TextBlock numbCard = new TextBlock()
                {
                    Text = cd,
                    Foreground = new SolidColorBrush(Colors.Black),
                    FontWeight = FontWeights.Bold,
                    TextAlignment = TextAlignment.Center,
                    FontSize = 40,
                    Width = 145,
                    Height = 50
                };

                cardItem.Children.Add(imgLogo);
                cardItem.Children.Add(numbCard);
                card.Items.Add(cardItem);
                if (indexCard == i)
                {
                    numbCard.Foreground = new SolidColorBrush(Colors.Yellow);
                    Border b = new Border()
                    {
                        BorderThickness = new Thickness(5),
                        BorderBrush = new SolidColorBrush(Colors.Yellow)
                    };
                    cardItem.Children.Add(b);
                }
                i++;
            }
        }

        private void card_Tap(object sender, GestureEventArgs e)
        {
            showCard();
        }

        private void sendEstimate_Click(object sender, RoutedEventArgs e)
        {
            //If GloApp.storyId is null it means that the meeting is only with wp7 device
            if (GloApp.storyId == null)
                GloApp.storyId = string.Empty;
            
            if (estimate.Length > 0)
            {
                GroupDiscussionPivot.properties ps = new GroupDiscussionPivot.properties();

                ps.Addproperty(new GroupDiscussionPivot.property()
                {
                    name = "storyId",
                    value = new GroupDiscussionPivot.value { type = "string", Value = GloApp.storyId }
                });

                ps.Addproperty(new GroupDiscussionPivot.property()
                {
                    name = "ExtensionName",
                    value = new GroupDiscussionPivot.value { type = "string", Value = "card-selection" }
                });

                ps.Addproperty(new GroupDiscussionPivot.property()
                {
                    name = "cardValue",
                    value = new GroupDiscussionPivot.value { type = "string", Value = estimate }
                });

                ps.Addproperty(new GroupDiscussionPivot.property()
                {
                    name = "who",
                    value = new GroupDiscussionPivot.value { type = "string", Value = GloApp.xmppClient.Username.ToString() + "@" + GloApp.xmppClient.XmppDomain.ToString() }
                });

                var mess = new GroupDiscussionPivot.PropertiesMsg
                {
                    Type = MessageType.groupchat,
                    To = roomJid,
                    properties = ps
                };

                GloApp.xmppClient.Send(mess);
                card.SelectedIndex = -1;
                showCard();
            }
            else
                MessageBox.Show("You must choose a card");
        }

        private void enableSel_Click(object sender, RoutedEventArgs e)
        {

            GroupDiscussionPivot.startPlanningPoker sPP = new GroupDiscussionPivot.startPlanningPoker();
            var mess = new GroupDiscussionPivot.PropertiesMsg()
            {
                Type = MessageType.groupchat,
                To = roomJid,
                startPlanningPoker = sPP
            };

            if (GloApp.subject == null)
            {
                MessageBox.Show("You must set the topic of the planning poker session");
                return;
            }
      
            //Check if the moderator has changed the deck configuration    
            if(GloApp.deckChange)
            {
                GroupDiscussionPivot.deck d = new GroupDiscussionPivot.deck();
                foreach (var card in GloApp.cardDesk)
                {
                    d.AddCard(new GroupDiscussionPivot.card()
                    {
                        Value = card
                    });
                }
                mess.deck = d;
            }     
          
            GloApp.xmppClient.Send(mess);
            enableSel.IsEnabled = false;
            ((Resources["AppBarDeck"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = false;
            ((Resources["AppBarDeckMod"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = false;
            GloApp.deckChange = false;
        }

        private void DeckSettings(object sender, System.EventArgs e)
        {
            NavigationService.Navigate(new Uri("/DeckConfiguration.xaml", UriKind.Relative));
        }

        private void BackGroupChat(object sender, System.EventArgs e)
        {
            GloApp.xmppClient.OnMessage -= new EventHandler<MessageEventArgs>(xmppClient_OnMessage);
            GloApp.estimates.Clear();
            NavigationService.GoBack();
        }

        #endregion
        
        #region Estimates

        private short i = 0;

        private Grid Estimates = new Grid()
        {
            HorizontalAlignment = HorizontalAlignment.Center,
            VerticalAlignment = VerticalAlignment.Top
        };

        private void accept_Click(object sender, RoutedEventArgs e)
        {
            if (GloApp.subject == null)
            {
                MessageBox.Show("You must set the topic of the planning poker session");
                return;
            }

            if (SetEstimate.Text.Equals(string.Empty))
            {
                MessageBox.Show("You have to set the estimate");
                return;
            }
            
            GroupDiscussionPivot.estimation est = new GroupDiscussionPivot.estimation()
            {
                type = "accepted",
                estimate = SetEstimate.Text
            };
            
            var mess = new GroupDiscussionPivot.PropertiesMsg
            {
                Type = MessageType.groupchat,
                To = roomJid,
                estimation = est
            };

            GloApp.xmppClient.Send(mess);
            accept.IsEnabled = false;
            repeat.IsEnabled = false;
        }

        private void repeat_Click(object sender, RoutedEventArgs e)
        {
            if (GloApp.subject == null)
            {
                MessageBox.Show("You must set the topic of the planning poker session");
                return;
            }

            GroupDiscussionPivot.estimation est = new GroupDiscussionPivot.estimation()
            {
                type = "rejected"
            };

            var mess = new GroupDiscussionPivot.PropertiesMsg
            {
                Type = MessageType.groupchat,
                To = roomJid,
                estimation = est
            };

            GloApp.xmppClient.Send(mess);
            accept.IsEnabled = false;
            repeat.IsEnabled = false;
        }

        private void newEstimate(Matrix.Xmpp.Client.Message msg)
        {
            GloApp.estimates.Add(msg.From.Resource, new App.rowTable(i, propertiesList["cardValue"]));
            addRow(Estimates, msg.From.Resource, propertiesList["cardValue"], false, i);
            i++;
        }

        private void editEstimate(Matrix.Xmpp.Client.Message msg)
        {
            ScrollViewer sv = new ScrollViewer()
            {
                HorizontalScrollBarVisibility = ScrollBarVisibility.Visible,
                VerticalScrollBarVisibility = ScrollBarVisibility.Disabled
            };

            TextBlock txtEst = new TextBlock()
            {
                Text = propertiesList["cardValue"],
                FontSize = 22,
                TextAlignment = TextAlignment.Center
            };
            sv.Content = txtEst;

            var control = (from d in Estimates.Children
                           where Grid.GetColumn(d as FrameworkElement) == 1 &&
                                 Grid.GetRow(d as FrameworkElement) == GloApp.estimates[msg.From.Resource].indexTab
                           select d).LastOrDefault();

            Estimates.Children.Remove(control);
            Estimates.Children.Add(sv);
            Grid.SetColumn(sv, 1);
            System.Diagnostics.Debug.WriteLine(GloApp.estimates[msg.From.Resource].indexTab);
            Grid.SetRow(sv, GloApp.estimates[msg.From.Resource].indexTab);
        }

        private string CalcAverageEst()
        {
            int num;
            List<int> numbers = new List<int>();
            foreach (var ee in GloApp.estimates)
            {
                bool isNumeric = int.TryParse(ee.Value.estimate, out num);
                if (isNumeric)
                    numbers.Add(num);
            }
            return (numbers.Count > 0 ? System.Math.Round(numbers.Average()).ToString() : string.Empty);
        }

        private void evaluation(Matrix.Xmpp.Client.Message msg)
        {
            if (GloApp.subject == null)
                return;

            var es = msg.Element<GroupDiscussionPivot.estimation>();
            if(!moderator)
                displayEstimates();

            if (es.type == "accepted")
            {
                if (!moderator)
                    displayFinalEst(true, es.estimate);
                GloApp.backlogWP[GloApp.subject] = es.estimate;
                updateBacklogView();        
            }
            else
            {
                if (!moderator)
                    displayFinalEst(false, "We have not reached a consensus");
            }

            sendEstimate.IsEnabled = false;
            ((Resources["AppBarDeck"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
            ((Resources["AppBarDeckMod"] as ApplicationBar).Buttons[0] as ApplicationBarIconButton).IsEnabled = true;
        }

        private void displayFinalEst(bool accept, string finalEst)
        {
            Estimate.Visibility = Visibility.Visible;

            if (accept)
            {
                Estimate.Width = 200;
                Estimate.Text = Estimate.Text + " " + finalEst;
                if (!inEstimatePivot)
                    MessageBox.Show("We have reached a consensus");
            }
            else
            {
                Estimate.Width = 400;
                Estimate.Margin = new Thickness(0, 310, 0, 0);
                Estimate.Text = finalEst;
                if (!inEstimatePivot)
                      MessageBox.Show("We have not reached a consensus"); 
            }
            Estimate.Foreground = new SolidColorBrush(Colors.Red);  
        }

        private void displayEstimates()
        {
            i = 0;
            foreach (var est in GloApp.estimates)
            {
                ScrollViewer sv = new ScrollViewer()
                {
                    HorizontalScrollBarVisibility = ScrollBarVisibility.Visible,
                    VerticalScrollBarVisibility = ScrollBarVisibility.Disabled
                };

                TextBlock txtEst = new TextBlock()
                {
                    Text = est.Value.estimate,
                    FontSize = 22,
                    TextAlignment = TextAlignment.Center
                };
                sv.Content = txtEst;

                Estimates.Children.Add(sv);
                Grid.SetColumn(sv, 1);
                Grid.SetRow(sv, i);
                i++;
            }
        }

        #endregion

        #region Backlog

        private short j = 0; 

        private Grid backlogEstimate = new Grid()
        {
            HorizontalAlignment = HorizontalAlignment.Center,
            VerticalAlignment = VerticalAlignment.Top,        
        };  

        private void updateBacklogView()
        {
            foreach (UIElement child in backlogEstimate.Children.ToList())
                backlogEstimate.Children.Remove(child);
            j = 0;

            //Check if the meeting isn't with eConference
            if (GloApp.backlog.Count == 0)
            {
                foreach (var newRow in GloApp.backlogWP)
                {
                    addRow(backlogEstimate, newRow.Key, newRow.Value, true, j);
                    j++;
                }
                return;
            }


            foreach (var newRow in GloApp.backlog)
            {
                addRow(backlogEstimate, newRow.Value.storytext, newRow.Value.estimate, true, j);
                j++;
            }
        }
        #endregion
    }
}