#include "Counter.h"
#include <iostream>
#include "Globals.h"

struct Tally {
	HashMapa& table;
	Tally(HashMapa& table_) : table(table_) {}
	void operator()(const blocked_range<size_t> range) const {
		HashMapa::accessor a;
		for (int p = range.begin(); p < range.end(); ++p) {
			table.insert(a, clip2CounterVector[p]);
			a->second += 1;
			a.release(); //the lock on object
		}
	}
};

RetVal Counter()
{
	int n = clip2CounterVector.size();

	parallel_for(blocked_range<size_t>(0, n), Tally(mapa));

    return RET_OK;
}
