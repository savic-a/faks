#include "Clip.h"

extern concurrent_vector<short> highPass2ClipVec;
extern concurrent_vector<unsigned char> clip2CounterVector;

struct Clipper {
    char lowerValue;
    char upperValue;
    void operator() (const blocked_range<size_t>& range) const {
        short data;
        for(int i = range.begin(); i < range.end(); ++i)
        {
            data = highPass2ClipVec[i];

            if (data < lowerValue)
            {
                data = lowerValue;
            }
            else if (data > upperValue)
            {
                data = upperValue;
            }

            clip2CounterVector.push_back(data);
        }
    }
};

RetVal Clip(char lowerValue, char upperValue)
{
    Clipper cl;
    cl.lowerValue;
    cl.upperValue;
    parallel_for(blocked_range<size_t>(0, highPass2ClipVec.size()), cl);
    //short data;

    //// clipping loop
    //while(!highPass2ClipQueue.empty())
    //{
    //    data = highPass2ClipQueue.front();
    //    highPass2ClipQueue.pop();

    //    if(data<lowerValue)
    //    {
    //        data = lowerValue;
    //    }
    //    else if(data>upperValue)
    //    {
    //        data = upperValue;
    //    }

    //    clip2CounterVector.push_back(data);
    //}
    return RET_OK;
}
