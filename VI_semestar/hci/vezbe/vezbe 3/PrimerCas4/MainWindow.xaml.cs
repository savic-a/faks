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
using PrimerCas4.Layouts;
using PrimerCas4.Binding;
using PrimerCas4.Validation;
using PrimerCas4.Table;
using PrimerCas4._2DG;

namespace PrimerCas4
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            var s = new PrimerCas4.Layouts.StackPanelExample();
            s.Show();
        }

        private void MenuItem_Click_1(object sender, RoutedEventArgs e)
        {
            var s = new GridExample();
            s.Show();
        }

        private void MenuItem_Click_2(object sender, RoutedEventArgs e)
        {
            var s = new DockPanelExample();
            s.ShowDialog();
            
        }

        private void MenuItem_Click_3(object sender, RoutedEventArgs e)
        {
            var s = new WrapPanelExample();
            s.ShowDialog();
        }

        private void MenuItem_Click_4(object sender, RoutedEventArgs e)
        {
            var s = new ComplexLayoutExample();
            s.Show();
        }

        private void MenuItem_Click_5(object sender, RoutedEventArgs e)
        {
            var s = new SimpleBinding();
            s.Show();
        }

        private void MenuItem_Click_6(object sender, RoutedEventArgs e)
        {
            var s = new ValidationExample();
            s.Show();
        }

        private void MenuItem_Click_7(object sender, RoutedEventArgs e)
        {
            var s = new TableExample();
            s.Show();
        }

        private void MenuItem_Click_8(object sender, RoutedEventArgs e)
        {
            var s = new Prikaz();
            s.Show();
        }

        private void MenuItem_Click_9(object sender, RoutedEventArgs e)
        {
            var s = new CustomRender();
            s.Show();
        }
    }
}
