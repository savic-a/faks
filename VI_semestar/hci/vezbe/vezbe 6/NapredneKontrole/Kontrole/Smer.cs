using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using System.Collections.ObjectModel;
using System.Windows.Input;

namespace NapredneKontrole.Kontrole
{
    public class AddCommand : ICommand
    {
        private Smer smer;
        public AddCommand(Smer s)
        {
            smer = s;
        }

        public bool CanExecute(object parameter)
        {
            return true;
        }

        public event EventHandler CanExecuteChanged;

        public void Execute(object parameter)
        {
            smer.Studenti.Add(new Student() { Indeks = "XY 101/2020", Ime="Xyzzy", Prezime="Grueson", Email="xyzzy@brasslantern.co.zk", Upisan=true});
        }
    }
    
    public class Smer : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged(string name)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(name));
            }
        }

        private string _naziv;
        public string Naziv
        {
            get
            {
                return _naziv;
            }
            set
            {
                if (_naziv != value)
                {
                    _naziv = value;
                    OnPropertyChanged("Naziv");
                }
            }
        }

        private AddCommand _add;
        public AddCommand Add
        {
            get
            {
                return _add;
            }
            set
            {
                if (_add != value)
                {
                    _add = value;
                    OnPropertyChanged("Add");
                }
            }
        }

        public ObservableCollection<Student> Studenti
        {
            get;
            set;
        }

        public Smer()
        {
            Studenti = new ObservableCollection<Student>();
            Add = new AddCommand(this);
        }
    }
}
