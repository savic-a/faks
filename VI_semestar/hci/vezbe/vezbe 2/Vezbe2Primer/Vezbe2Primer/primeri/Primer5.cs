using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vezbe2Primer.util;

namespace Vezbe2Primer.primeri
{
    public delegate void IspisiDelegat(string s); //napravili smo delegat, odn. tip koji pokazuje na neku metodu/funkciju koja vraca void a uzima
    //string i sada ga mozemo koristiti

    class E
    {
        public void pozovi(IspisiDelegat d){
            pozovi(d);
        }
    }

   
    public class Primer5 : AbstractPrimer
    {
        public Primer5(frmPrimer2 f)
            : base(f)
        {

        }

        public void NasIspis(string s)
        {
            ispisi("Nas ispis " + s);
        }
        public override void izvrsi()
        {

            ispisi("Primer 5 - delegati.\r\n");
            ispisi("#############\r\n");
            E e = new E();
            e.pozovi(ispisi); //ovde je metoda prosledjena preko delegata u metodu klase E. 
            //u ovome se vidi kako delegat radi kao pokazivač na funkcije u C/C++
            e.pozovi(NasIspis);
            e.pozovi((s) => { ispisi(s); });
            IspisiDelegat ispisiDel = ispisi;
            ispisiDel("delegat kreiran");
            ispisiDel += NasIspis;
            ispisiDel("poz iz del");



            ispisi("\r\n");
        }

    }
}