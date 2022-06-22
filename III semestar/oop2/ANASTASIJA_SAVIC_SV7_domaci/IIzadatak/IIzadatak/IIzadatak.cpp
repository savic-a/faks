#include "std_lib_facilities.h"

string funkcija1(string br) {
	if(br[0] == '-') error("negativan broj!");
	unsigned long lrez = stoul(br); //konvertovanje stringa u broj
	unsigned int irez = lrez;  
	char hex_string[20];
	sprintf_s(hex_string, "%X", irez); //konvertovanje dec broja u hex
	return hex_string;
}

string funkcija2(string br1, string br2) {
	long broj1 = stoi(br1, 0, 16); //konvertovanje hex brojeva u dec
	int broj2 = stoi(br2, 0, 16);
	int zbir = broj1 + broj2; 
	char hex_string[20];
	sprintf_s(hex_string, "%X", zbir); //konvertovanje dec broja u hex
	return hex_string;
}

int funkcija3(string heks) {
	int dec = stoi(heks, 0, 16); //konvertovanje hex brojeva u dec
	return dec;
}

int main() {
	while (cin) {
		string izbor;
		// meni
		cout << endl << "1 Konvertovanje neoznacenog celog broja u heksadecimalni" << endl;  
		cout << "2 Sabiranje 2 heksadecimalna broja" << endl;
		cout << "3 Konvertovanje heksadecimalnog broja u decimalni" << endl;
		cout << "0 KRAJ" << endl;
		cout << "Unesite broj opercije koju zelite da izvrsite: ";
		cin >> izbor;
		if (izbor == "1") {
			try
			{
				string broj;
				cout << endl << "Unesite broj: ";
				cin >> broj;
				cout << funkcija1(broj) << endl;
			}
			catch (const std::exception&)
			{
				cout << "Greska, unos nije validan!" << endl;
			}
		}
		else if (izbor == "2") {
			try
			{
				string broj1, broj2;
				cout << endl << "Unesite prvi broj: ";
				cin >> broj1;
				cout << "Unesite drugi broj: ";
				cin >> broj2;
				cout << endl << "Resenje je: " << funkcija2(broj1, broj2) << endl;
			}
			catch (const std::exception&)
			{
				cout << "Greska, unos nije validan!" << endl;
			}
		}
		else if (izbor == "3") {
			try
			{
				string broj;
				cout << endl << "Unesite broj: ";
				cin >> broj;
				cout << endl << "Resenje je: " << funkcija3(broj) << endl;
			}
			catch (const std::exception&)
			{
				cout << "Greska, unos nije validan!" << endl;
			}
		}
		else if (izbor == "0") {
			break;
		}
		else
		{
			cout << "Nevalidan unos!" << endl;
		}
	}
	return 0;
}