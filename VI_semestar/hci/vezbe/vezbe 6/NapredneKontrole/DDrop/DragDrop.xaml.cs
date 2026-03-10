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
using NapredneKontrole.Table;
using System.Collections.ObjectModel;

namespace NapredneKontrole.DDrop
{
    /// <summary>
    /// Interaction logic for DragDrop.xaml
    /// </summary>
    public partial class DragDropWindow : Window
    {
        Point startPoint = new Point();

        public ObservableCollection<Student> Studenti
        {
            get;
            set;
        }

        public ObservableCollection<Student> Studenti2
        {
            get;
            set;
        }

        public DragDropWindow()
        {
            InitializeComponent();
            this.DataContext = this;
            List<Student> l = new List<Student>();
            l.Add(new Student { Ime = "Petar", Prezime = "Petrovic", Indeks = "SW 1\\2061", Email = "ppetrovic@gmail.com", Upisan = true });
            l.Add(new Student { Ime = "Milica", Prezime = "Milicevic", Indeks = "SW 2\\2061", Email = "mmilicevic@gmail.com", Upisan = false });
            l.Add(new Student { Ime = "Zoran", Prezime = "Zoranovic", Indeks = "SW 3\\2061", Email = "zzoranovic@gmail.com", Upisan = true });
            l.Add(new Student { Ime = "Suzana", Prezime = "Suzanic", Indeks = "SW 4\\2061", Email = "ssuzanic@gmail.com", Upisan = false });
            l.Add(new Student { Ime = "Goran", Prezime = "Goranski", Indeks = "SW 5\\2061", Email = "awesomeadressxxXXxx1996@gmail.com", Upisan = true });

            Studenti = new ObservableCollection<Student>(l);
            Studenti2 = new ObservableCollection<Student>();
        }

        private void ListView_PreviewMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            startPoint = e.GetPosition(null);
        }

        private void ListView_MouseMove(object sender, MouseEventArgs e)
        {
            Point mousePos = e.GetPosition(null);
            Vector diff = startPoint - mousePos;

            if (e.LeftButton == MouseButtonState.Pressed &&
                (Math.Abs(diff.X) > SystemParameters.MinimumHorizontalDragDistance ||
                Math.Abs(diff.Y) > SystemParameters.MinimumVerticalDragDistance))
            {
                // Get the dragged ListViewItem
                ListView listView = sender as ListView;
                ListViewItem listViewItem =
                    FindAncestor<ListViewItem>((DependencyObject)e.OriginalSource);

                // Find the data behind the ListViewItem
                Student student = (Student)listView.ItemContainerGenerator.
                    ItemFromContainer(listViewItem);

                // Initialize the drag & drop operation
                DataObject dragData = new DataObject("myFormat", student);
                DragDrop.DoDragDrop(listViewItem, dragData, DragDropEffects.Move);
            } 
        }

        private static T FindAncestor<T>(DependencyObject current) where T : DependencyObject
        {
            do
            {
                if (current is T)
                {
                    return (T)current;
                }
                current = VisualTreeHelper.GetParent(current);
            }
            while (current != null);
            return null;
        }

        private void ListView_DragEnter(object sender, DragEventArgs e)
        {
            if (!e.Data.GetDataPresent("myFormat") || sender == e.Source)
            {
                e.Effects = DragDropEffects.None;
            }
        }

        private void ListView_Drop(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent("myFormat"))
            {
                Student student = e.Data.GetData("myFormat") as Student;
                Studenti.Remove(student);
                Studenti2.Add(student);
            }
        }


    }
}
