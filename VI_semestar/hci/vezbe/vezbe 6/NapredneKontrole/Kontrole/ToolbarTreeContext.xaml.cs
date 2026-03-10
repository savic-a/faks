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

namespace NapredneKontrole.Kontrole
{
    /// <summary>
    /// Interaction logic for ToolbarTreeContext.xaml
    /// </summary>
    public partial class ToolbarTreeContext : Window
    {
        public ObservableCollection<Smer> Smerovi
        {
            get;
            set;
        }

        public ToolbarTreeContext()
        {
            InitializeComponent();
            this.DataContext = this;
            Smerovi = new ObservableCollection<Smer>();
            Smer s = new Smer() { Naziv = "E2" };
            s.Studenti.Add(new Student() { Ime = "Aleksandar", Prezime = "Aleksandrov", Email = "aleksaa@somemail.info", Indeks = "RA 101\\2066", Upisan = true});
            s.Studenti.Add(new Student() { Ime = "Branka", Prezime = "Buzurić", Email = "bbuzuric@somemail.info", Indeks = "RA 107\\2066", Upisan = true });
            Smerovi.Add(s);
            s = new Smer() { Naziv = "SIIT" };
            s.Studenti.Add(new Student() { Ime = "Cenka", Prezime = "Cenkić", Email = "ccenkic@somemail.info", Indeks = "SW 44\\2066", Upisan = true });
            s.Studenti.Add(new Student() { Ime = "Darko", Prezime = "Darkić", Email = "darko@somemail.info", Indeks = "SW 5\\2066", Upisan = true });
            Smerovi.Add(s);
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("YOU HAVE 30 SECONDS TO REACH MINIMUM SAFE DISTANCE.");
        }

    }
}
