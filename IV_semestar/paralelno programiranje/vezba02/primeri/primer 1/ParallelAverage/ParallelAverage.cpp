#include "tbb/blocked_range.h"
#include "tbb/parallel_for.h"
#include "tbb/tick_count.h"
#include <iostream>

using namespace tbb;
using namespace std;

//TODO: Create a class/structure for paralelization

// Note: The input must be padded such that input[-1] and 
// input[n] can be used to calculate the first and last 
// output values.
struct Average
{
	double* input; double* output;
	void operator()(const blocked_range<int>& range) const
	{
		for (int i = range.begin(); i != range.end(); ++i)
		{
			output[i] = (input[i] + input[i + 1] + input[i + 2]) * (1 / 3.0f);
		}
	}
};

//TODO: Create a funcion for paralelization
void ParallelAverage(double* output, double* input, size_t n)
{
	Average avg;
	avg.input = input;
	avg.output = output;
	parallel_for(blocked_range<int>(0, n, 500), avg);
}


// Sets output[i] to the average of input[i-1], input[i] and input[i+1]
int main() {
	double input[1002], output_serial[1000], output_parallel[1000];
	int test = 0;

	input[0] = 0;
	input[1001] = 0;
	for (int i = 1; i < 1001; i++) {
		input[i] = i * 3.14;
	}

	tick_count startTime1 = tick_count::now();
	for (int i = 0; i < 1000; i++) {
		output_serial[i] = (input[i] + input[i + 1] + input[i + 2])*(1 / 3.0f);
	}

	//TODO: Paralell average
	//TODO: Check if the results are equal

	tick_count endTime1 = tick_count::now();
	cout << (endTime1 - startTime1).seconds() << endl;

	tick_count startTime = tick_count::now();
	ParallelAverage(output_parallel, input, 1000);
	tick_count endTime = tick_count::now();
	cout << (endTime - startTime).seconds() << endl;

	bool greska = false;
	for (int i = 0; i < 1000; i++) {
		if (output_serial[i] != output_parallel[i])
		{
			cout << "Greska! " << input[i] << " " << output_parallel[i] << " " << output_serial[i] << endl;
			greska = true;
		}
	}
	if (!greska)
	{
		cout << "Nema greske" << endl;
	}



	return 0;
}
