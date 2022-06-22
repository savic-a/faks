#include "std_lib_facilities.h"

void func1() {
	short val1, val2;
	cout << endl << "Unesite prvi broj: ";
	cin >> val1;
	cout << "Unesite drugi broj: ";
	cin >> val2;
	if (val1 > val2)
		cout << "Prvi broj je veci od drugog" << endl;
	else if (val1 < val2)
		cout << "Prvi broj je manji od drugog" << endl;
	else
		cout << "Brojevi su jednaki" << endl;
	short sum = val1 + val2;  //sabiranje
	short prod = val1 * val2;  //mnozenje
	short diff = val1 - val2;  //oduzimanje

	cout << "Zbir je " << sum << endl;
	cout << "Proizvod je " << prod << endl;
	cout << "Razlika je " << diff << endl;

	if (val2 == 0) error("Deljenje nulom"); //provera deljenje nulom
	else {
		short div = val1 / val2;
		cout << "Kolicnik je " << div << endl;
	}
}

void func2() {
	int val1, val2;
	cout << endl << "Unesite prvi broj: ";
	cin >> val1;
	cout << "Unesite drugi broj: ";
	cin >> val2;
	if (val1 > val2)
		cout << "Prvi broj je veci od drugog" << endl;
	else if (val1 < val2)
		cout << "Prvi broj je manji od drugog" << endl;
	else
		cout << "Brojevi su jednaki" << endl;
	int sum = val1 + val2;  //sabiranje
	int prod = val1 * val2;  //mnozenje
	int diff = val1 - val2;  //oduzimanje

	cout << "Zbir je " << sum << endl;
	cout << "Proizvod je " << prod << endl;
	cout << "Razlika je " << diff << endl;

	if (val2 == 0) error("Deljenje nulom"); //provera deljenje nulom
	else {
		int div = val1 / val2;
		cout << "Kolicnik je " << div << endl;
	}
}

void func3() {
	double val1, val2;
	cout << endl << "Unesite prvi broj: ";
	cin >> val1;
	cout << "Unesite drugi broj: ";
	cin >> val2;
	if (val1 > val2)
		cout << "Prvi broj je veci od drugog" << endl;
	else if (val1 < val2)
		cout << "Prvi broj je manji od drugog" << endl;
	else
		cout << "Brojevi su jednaki" << endl;
	double sum = val1 + val2;   //sabiranje
	double prod = val1 * val2;  //mnozenje
	double diff = val1 - val2;  //oduzimanje

	cout << "Zbir je " << sum << endl;
	cout << "Proizvod je " << prod << endl;
	cout << "Razlika je " << diff << endl;

	if (val2 == 0) error("Deljenje nulom"); //provera deljenje nulom
	else {
		int div = val1 / val2;
		cout << "Kolicnik je " << div << endl;
	}
}





int main() {
	while (true) {
		string izbor;
		// meni
		cout << endl << "1 short" << endl;
		cout << "2 int" << endl;
		cout << "3 double" << endl;
		cout << "0 KRAJ" << endl;
		cout << "Unesite broj tipa vrednosti koji zelite: ";
		cin >> izbor;
		if (izbor == "1") {
			try
			{
				func1();
			}
			catch (const std::exception&)
			{
				cout << "Deljenje nulom!" << endl;
			}
		}
		else if (izbor == "2") {
			try
			{
				func2();
			}
			catch (const std::exception&)
			{
				cout << "Deljenje nulom!" << endl;
			}
		}
		else if (izbor == "3") {
			try
			{
				func3();
			}
			catch (const std::exception&)
			{
				cout << "Deljenje nulom!" << endl;
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