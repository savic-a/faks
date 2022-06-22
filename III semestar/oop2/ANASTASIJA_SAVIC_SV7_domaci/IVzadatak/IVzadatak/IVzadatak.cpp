#include "std_lib_facilities.h"

void func1() {
	int f1 = 1;
	int f2 = 1;
	int nextNum = f1 + f2;
	int n = 3;
	while (true)
	{
		f1 = f2;
		f2 = nextNum;
		if (f2 > INT_MAX - f1) //provera prekoracenja
		{ 
			cout << "Iteracija se zaustavlja na " << n << ". Fibonacijevom broju, koji iznosi: " << nextNum << endl;
			break;
		}
		nextNum = f1 + f2;
		n++;
	}
}

void func2() {
	long f1 = 1;
	long f2 = 1;
	long nextNum = f1 + f2;
	int n = 3;
	while (true)
	{
		f1 = f2;
		f2 = nextNum;
		if (f2 > LONG_MAX - f1) //provera prekoracenja
		{
			cout << "Iteracija se zaustavlja na " << n << ". Fibonacijevom broju, koji iznosi: " << nextNum << endl;
			break;
		}
		nextNum = f1 + f2;
		n++;
	}
}

void func3() {
	long long f1 = 1;
	long long f2 = 1;
	long long nextNum = f1 + f2;
	int n = 3;
	while (true)
	{
		f1 = f2;
		f2 = nextNum;
		if (f2 > LLONG_MAX - f1) //provera prekoracenja
		{
			cout << "Iteracija se zaustavlja na " << n << ". Fibonacijevom broju, koji iznosi: " << nextNum << endl;
			break;
		}
		nextNum = f1 + f2;
		n++;
	}
}

int main() {
	func1();
	func2();
	func3();
}