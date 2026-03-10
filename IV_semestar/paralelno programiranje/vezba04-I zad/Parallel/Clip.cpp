#include "Clip.h"

extern concurrent_vector<short> highPass2ClipVec;
extern concurrent_vector<unsigned char> clip2CounterVector;

struct Clipper {
	char lowerBound;
	char upperBound;
	void operator()(const blocked_range<size_t>& range) const {
		short data;
		for (int i = range.begin(); i != range.end(); ++i)
		{
			data = highPass2ClipVec[i];

			if (data < lowerBound)
			{
				data = lowerBound;
			}
			else if (data > upperBound)
			{
				data = upperBound;
			}

			clip2CounterVector.push_back(data);
		}
	}
};


RetVal Clip(char lowerValue, char upperValue)
{
    // clipping loop

	Clipper cl;
	cl.lowerBound = lowerValue;
	cl.upperBound = upperValue;

	parallel_for(blocked_range<size_t>(0, highPass2ClipVec.size()), cl);

    return RET_OK;
}
