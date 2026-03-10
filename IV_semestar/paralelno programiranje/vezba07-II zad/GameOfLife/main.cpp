#include "GameOfLife.h"
#include <iostream>
#include <string>
#include <tbb/tick_count.h>
#include <tbb/blocked_range.h>
#include <tbb/parallel_reduce.h>

#define NUM_OF_ITERATIONS 30
#define TABLE_SIZE 128

using namespace std;
using namespace tbb;

class SumFoo {
	unsigned char* next;
	unsigned char* current;
public:
	int sum;
	void operator()(const blocked_range<size_t>& r) {
		unsigned char* a = next;
		unsigned char* b = current;
		for (size_t i = r.begin(); i != r.end(); ++i)
			if (a[i] == 0 && b[i] == 0)
				sum += 1;
	}
	SumFoo(SumFoo& x, split) : next(x.next), current(x.current), sum(0) {}
	void join(const SumFoo& y) { sum += y.sum; }
	SumFoo(unsigned char a[], unsigned char b[]) : next(a), current(b), sum(0) {}
};

int againLiveCells(unsigned char x[], unsigned char y[], size_t n) {
	SumFoo sumFoo = SumFoo(x, y);
	parallel_reduce(blocked_range<size_t>(0, n), sumFoo);

	return sumFoo.sum;
}


static void printTable(GameOfLife* game, int iterNum);

int main(int argc, char* argv[]) {

	unsigned long long startTicks, endTicks;

	GameOfLife game(TABLE_SIZE, TABLE_SIZE, PULSAR);

	// serial version
	cout << "Serial Game of Life initiated..." << endl;
	tick_count startTime = tick_count::now();
	for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
		//printTable(&game, i);
		game.nextIterSerial();
	}
	tick_count endTime = tick_count::now();
	cout << "Serial time: \t\t\t" << (endTime - startTime).seconds() << " seconds\n";
	int sumaSerial = 0;
	for (int i = 0; i < TABLE_SIZE * TABLE_SIZE; i++) {
		if (game.buffer[i] == 0 && game.nextIter[i] == 0)
			sumaSerial += 1;
	}
	cout << "Again live: " << sumaSerial << endl;

	// parallel version
	cout << "Parallel Game of Life initiated..." << endl;
	startTime = tick_count::now();
	for (int i = 0; i < NUM_OF_ITERATIONS; i++) {
		//printTable(&game, i);
		game.nextIterParallel(0, 0, TABLE_SIZE);
	}
	endTime = tick_count::now();
	cout << "Parallel time: \t\t\t" << (endTime - startTime).seconds() << " seconds\n";
	int sumaParallel = 0;
	sumaParallel = againLiveCells(game.nextIter, game.buffer, size_t(TABLE_SIZE * TABLE_SIZE));
	cout << "Again live: " << sumaParallel << endl;

	return 0;
}

static void printTable(GameOfLife* game, int iterNum) {

	cout << "Iteration: " << iterNum << endl;
	game->printIteration();
	system("pause");
	system("cls");

	return;
}