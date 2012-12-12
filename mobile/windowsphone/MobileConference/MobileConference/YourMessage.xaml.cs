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

namespace MobileConference
{
    public partial class YourMessage : UserControl
    {
        public YourMessage()
        {
            InitializeComponent();
        }

        public void setText(string body)
        {
            msgBody.Text = body;
        }

        public void setName(string name)
        {
            msgName.Text = name;
        }

        public void setTime(string time)
        {
            msgTime.Text = time;
        }

        public void setColour(SolidColorBrush colour)
        {
            triangle.Fill = colour;
            rectangle.Background = colour;
        }
    }
}
