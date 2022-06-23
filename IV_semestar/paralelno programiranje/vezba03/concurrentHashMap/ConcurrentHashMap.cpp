#include "tbb/concurrent_hash_map.h"
#include "tbb/blocked_range.h"
#include "tbb/parallel_for.h"
#include "tbb/tick_count.h"
#include <iostream>

using namespace std;
using namespace tbb;

// TODO: Create a simple MyHashCompare structure
struct MyHashCompare {
	static size_t hash(const string& x) {
		size_t h = 0;
		for (const char* s = x.c_str(); *s; ++s)
			h = (h*17);
		return h;
	}

	static bool equal(const string& x, const string& y) {
		return x == y;
	}
};

// A concurrent hash table that maps ints to ints
typedef concurrent_hash_map<int, int, MyHashCompare> IntTable;

// TODO: Create a simple Histogram class which will count the number of occurrences
struct Histogram {
	IntTable& table;
	Histogram(IntTable& table_) : table(table_) {}
	void operator() (const blocked_range<string*> range) const {
		for (string* p = range.begin(); p != range.end(); ++p) {
			IntTable::accessor a;
			table.insert(a, *p);
			a->second += 1;
		}
	}
};

const size_t N = 1000000;
int Data[N];

// Count occurrences
void main() {
	// Construct empty table
	IntTable table;
	
	// Create some data to work with
	for (int i = 0; i < N; i++) {
		Data[i] = rand() % 10;
	}

	// TODO: Put occurrences into the table using parallel_for

	// Display the occurrences
	tick_count startTime = tick_count::now();
	parallel_for(blocked_range<size_t>(0, N), Histogram(table));
	tick_count endTime = tick_count::now();

	cout << (endTime - startTime).seconds() << endl;
}
