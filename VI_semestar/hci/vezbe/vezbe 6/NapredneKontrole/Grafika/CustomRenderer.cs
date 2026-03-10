using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Media;
using System.ComponentModel;
using System.Windows.Media.Imaging;
using System.Windows.Threading;


namespace NapredneKontrole.Grafika
{

    public class CustomRenderer : FrameworkElement
    {
        public CustomRenderer()
        {
            if (DesignerProperties.GetIsInDesignMode(this))
            {
                this.BackgroundImage = new BitmapImage(new Uri("map.jpg", UriKind.Relative));
            }
        }

        public ImageSource BackgroundImage
        {
            get
            {
                return base.GetValue(BackgroundImageProperty) as ImageSource;
            }
            set
            {
                base.SetValue(BackgroundImageProperty, value);
                InvalidateVisual();
            }
        }

        public static readonly DependencyProperty BackgroundImageProperty = DependencyProperty.Register("BackgroundImage", typeof(ImageSource), typeof(CustomRenderer), new PropertyMetadata(Changed));

        //Reagujemo kada se property promeni preko binding-a
        private static void Changed(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            var c = d as CustomRenderer;
            c.InvalidateVisual();
        }

        protected override void OnRender(DrawingContext drawingContext)
        {
            drawingContext.DrawImage(BackgroundImage, new Rect(0, 0, ActualWidth, ActualHeight));
        }
    }
}
