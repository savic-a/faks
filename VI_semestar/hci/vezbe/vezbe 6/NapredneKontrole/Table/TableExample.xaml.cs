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
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace NapredneKontrole.Table
{
    /// <summary>
    /// Interaction logic for TableExample.xaml
    /// </summary>
    public partial class TableExample : Window, INotifyPropertyChanged
    {
         public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged(string info)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(info));
            }
        }

        private ICollectionView _View;
        public ICollectionView View
        {
            get
            {
                return _View;
            }
            set
            {
                _View = value;
                OnPropertyChanged("View");
            }
        }

        private bool _GroupView;
        public bool GroupView
        {
            get
            {
                return _GroupView;
            }
            set
            {
                if (value != _GroupView && View != null)
                {
                    View.GroupDescriptions.Clear();
                    if (value)
                    {
                        View.GroupDescriptions.Add(new PropertyGroupDescription("Upisan"));
                    }
                    _GroupView = value;
                    OnPropertyChanged("GroupView");

                    OnPropertyChanged("View");
                }
            }
        }   

        public ObservableCollection<Student> Studenti
        {
            get;
            set;
        }

        public TableExample()
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

            View = CollectionViewSource.GetDefaultView(Studenti); 
            GroupView = false;
            
        }
    }
}
