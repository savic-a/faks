package main;

import citanjeIzFajla.Menadzer;
import gui.LoginProzor;
import podesavanje.Ucitavanje;

public class SkolaJezika {
	public static void main(String[] args) {
		
		Ucitavanje ucitavanje = new Ucitavanje("./src/fajlovi/administrator.csv", "./src/fajlovi/sekretari.csv", "./src/fajlovi/predavaci.csv", 
				"./src/fajlovi/ucenici.csv", "./src/fajlovi/jezici.csv", "./src/fajlovi/testovi.csv", "./src/fajlovi/kursevi.csv", 
				"./src/fajlovi/dodatiKursevi.csv", "./src/fajlovi/zahteviReg.csv", "./src/fajlovi/zahteviKurs.csv", "./src/fajlovi/rezultati.csv");
		
		// Primer klase koja ce biti zaduzena za instanciranje Manager klasa za glavne entitete
		Menadzer m = new Menadzer(ucitavanje);
		
		// Metoda ce unutar sebe ucitati podatke za sve potrebne menadzere
		m.ucitava();
		System.out.println("Pokretanje glavnog prozora");
		
		// Instanciranje glavnog prozora aplikacije koji ce dalje otvarati/zatvarati naredne prozore i dijaloge
		LoginProzor main = new LoginProzor(m);
		main.toString();
		
		
	
	}
}
