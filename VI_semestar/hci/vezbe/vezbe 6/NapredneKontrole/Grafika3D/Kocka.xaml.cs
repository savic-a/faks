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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Media.Media3D;
namespace NapredneKontrole.Grafika3D
{
    /// <summary>
    /// Interaction logic for Kocka.xaml
    /// </summary>
    public partial class Kocka : Window
    {
        AxisAngleRotation3D ax3d;

        public Kocka()
        {
            InitializeComponent();
            ax3d = new AxisAngleRotation3D(new Vector3D(0, 2, 0), 1);
            RotateTransform3D myRotateTransform = new RotateTransform3D(ax3d);
            kocka.Transform = myRotateTransform;
        }

        private void rotiraj(object sender, RoutedEventArgs e)
        {
            ax3d.Angle += 10;
        }
    }
}
