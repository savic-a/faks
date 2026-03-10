using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace PrimerCas4._2DG
{
    /// <summary>
    /// Interaction logic for Prikaz.xaml
    /// </summary>
    public partial class Prikaz : Window
    {
        public Prikaz()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Uri myUri = new Uri("map.jpg", UriKind.RelativeOrAbsolute);
            JpegBitmapDecoder decoder2 = new JpegBitmapDecoder(myUri, BitmapCreateOptions.PreservePixelFormat, BitmapCacheOption.Default);
            BitmapSource bitmapSource2 = decoder2.Frames[0];

            // Draw the Image
            myImage2.Source = bitmapSource2;
            myImage2.Stretch = Stretch.Uniform;
            myImage2.Margin = new Thickness(20);

        }
    }
}
