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

namespace MobileConference
{
    public partial class DeckConfiguration : PhoneApplicationPage
    {
        public App GloApp = (App.Current as App);

        public DeckConfiguration()
        {
            InitializeComponent();
          
            foreach (var card in GloApp.cardDesk)
                defaultCards.Items.Add(card);
        }

        private void addCards_Click(object sender, RoutedEventArgs e)
        {
            foreach (var card in defaultCards.SelectedItems)
            {
                customCards.Items.Add(card);
                defaultCards.Items.Remove(card);
            }
        }

        private void removeCards_Click(object sender, RoutedEventArgs e)
        {
            foreach (var card in customCards.SelectedItems)
            {
                customCards.Items.Remove(card);
                defaultCards.Items.Add(card);
            }       
        }

        private void addNewCard_Click(object sender, RoutedEventArgs e)
        {
            if (defaultCards.Items.Contains(newCard.Text) || customCards.Items.Contains(newCard.Text))
                MessageBox.Show("Error, the card is already present in the deck");
            else
                customCards.Items.Add(newCard.Text);
            newCard.Text = string.Empty;
        }

        private void moveUp_Click(object sender, RoutedEventArgs e)
        {
            moveItem(-1);
        }

        private void moveDown_Click(object sender, RoutedEventArgs e)
        {
            moveItem(1);
        }

        private void moveItem(int direction)
        {
            if (customCards.SelectedIndex == -1)
                return;

            List<int> indexes = new List<int>();
            //save and sort the index of the items in the Listbox to avoid cycles in the multiple selection
            foreach (var card in customCards.SelectedItems)
                indexes.Add(customCards.Items.IndexOf(card));

            if (direction == -1)
                indexes.Sort();
            else
            {
                indexes.Sort();
                indexes.Reverse();
            }

            if (indexes[0] + direction < 0 || indexes[0] + direction >= customCards.Items.Count)
            {
                customCards.SelectedIndex = -1;
                return; //Index out of range
            }

            foreach (var ind in indexes)
            {
                object selected = customCards.Items.ElementAt(ind);
                customCards.Items.Remove(selected);
                customCards.Items.Insert(ind + direction, selected);
            }
        }

        private void BackDeck(object sender, EventArgs e)
        {
            if (customCards.Items.Count != 0)
            {
                GloApp.cardDesk.Clear();
                foreach (var c in customCards.Items)
                    GloApp.cardDesk.Add(c.ToString());
                GloApp.deckChange = true;
            }
            NavigationService.GoBack();
        }

        private void restoreDeck_Click(object sender, RoutedEventArgs e)
        {
            List<string> DefaultDeck = new List<string>() {"?", "0", "1", "2", "3", "5", "8", "20", "40", "100"};
            defaultCards.Items.Clear();
            customCards.Items.Clear();
            GloApp.cardDesk.Clear();
            GloApp.cardDesk.AddRange(DefaultDeck);
            foreach (var card in GloApp.cardDesk)
                defaultCards.Items.Add(card);
            GloApp.deckChange = true;
        }
    }
}