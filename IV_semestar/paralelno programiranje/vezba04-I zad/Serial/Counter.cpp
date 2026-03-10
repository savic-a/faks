#include "Counter.h"
#include "Globals.h"

//extern concurrent_vector<unsigned char> clip2CounterVector;
//extern unsigned int couterValues[256];

struct Tally {
    HashMapa& table;
    Tally(HashMapa& table_) : table(table_) {}
    void operator()(const blocked_range<size_t>& range) const {
        HashMapa::accessor a;
        for(int p = range.begin(); p < range.end(); ++p)
        {
            table.insert(a, clip2CounterVector[p]);
            a->second += 1;
            a.release();
        }
    }
};

RetVal Counter()
{
    parallel_for(blocked_range<size_t>(0, clip2CounterVector.size()), Tally(mapa));
    //unsigned char data;

    // array initialization
    //memset(couterValues, 0x00, 256*sizeof(unsigned int));

    // count each value
    /*while(!clip2CounterVector.empty())
    {
        data = clip2CounterVector.back();
        clip2CounterVector.pop_back();

        couterValues[data]++;
    }*/
    return RET_OK;
}
