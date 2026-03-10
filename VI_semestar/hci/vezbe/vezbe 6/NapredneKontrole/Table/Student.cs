using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

namespace NapredneKontrole.Table
{
    public class Student : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged(string name)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(name));
            }
        }
        private string _ime;
        private string _prezime;
        private string _indeks;
        private string _email;
        private bool _upisan;
        public string Ime
        {
            get
            {
                return _ime;
            }
            set
            {
                if (value != _ime)
                {
                    _ime = value;
                    OnPropertyChanged("Ime");
                }
            }
        }

        public string Prezime
        {
            get
            {
                return _prezime;
            }
            set
            {
                if (value != _prezime)
                {
                    _prezime = value;
                    OnPropertyChanged("Prezime");
                }
            }
        }

        public string Indeks
        {
            get
            {
                return _indeks;
            }
            set
            {
                if (value != _indeks)
                {
                    _indeks = value;
                    OnPropertyChanged("Indeks");
                }
            }
        }

        public string Email
        {
            get
            {
                return _email;
            }
            set
            {
                if (value != _email)
                {
                    _email = value;
                    OnPropertyChanged("Email");
                }
            }
        }

        public bool Upisan
        {
            get
            {
                return _upisan;
            }
            set
            {
                if (_upisan != value)
                {
                    _upisan = value;
                    OnPropertyChanged("Upisan");
                }
            }
        }
    }
}
