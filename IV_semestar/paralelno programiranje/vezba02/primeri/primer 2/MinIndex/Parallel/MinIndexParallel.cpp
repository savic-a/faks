#include <stdio.h>
#include <float.h>

#include "tbb\tbb.h"
#include "tbb\parallel_reduce.h"
#include "tbb/blocked_range.h"

using namespace tbb;
//
//float Foo(float a) {
//	return a;
//}
//
//   //TODO: Create a class called MinIndexFoo with constructors, operator() and the join method
//class MinIndexFoo {
//	const float* const my_a;
//public:
//	float value_of_min;
//	long index_of_min;
//	void operator() (const blocked_range<size_t>& r) {
//		const float* a = my_a;
//		for (size_t i = r.begin(); 1 != r.end(); ++i) {
//			float value = Foo(a[i]);
//			if (value < value_of_min) {
//				value_of_min = value;
//				index_of_min = i;
//			}
//		}
//	}
//
//	MinIndexFoo(MinIndexFoo& x, split) :
//		my_a(x.my_a), value_of_min(FLT_MAX), index_of_min(-1) {}
//
//	MinIndexFoo(const float a[]) : my_a(a), value_of_min(FLT_MAX), index_of_min(-1) {}
//
//	void join(const MinIndexFoo& y) {
//		if (y.value_of_min < value_of_min)
//		{
//			value_of_min = y.value_of_min;
//			index_of_min = y.index_of_min;
//		}
//	}
//	
//};
//
////TODO: Create a function for parallel minimum
//long ParallelMinIndexFoo(float a[], size_t n) {
//	MinIndexFoo mif(a);
//	parallel_reduce(blocked_range<size_t>(0, n, 20), mif);
//	return mif.index_of_min;
//}
//
//int main () {
//	float a[100];
//
//	for (int i=0; i<100; ++i) {
//		a[i] = -i;
//	}
//	
//	long ind = ParallelMinIndexFoo(a, 100);
//	printf("%d\n", ind);
//
//	return 0;
//}

float Foo(float a) {
	return a;
}

//TODO: Create a class called MinIndexFoo with constructors, operator() and the join method
class MinIndexFoo {
	float* my_a;
	int size;
public:
	float min = FLT_MAX;
	long ind;

	void operator()(const blocked_range<int>& r)
	{
		float* a = my_a;
		for (size_t i = r.begin(); i != r.end(); ++i)
		{
			if (min > a[i])
			{
				ind = i;
				min = a[i];
			}
		}
	}

	MinIndexFoo(MinIndexFoo& x, split) : my_a(x.my_a), size(size) {}


	MinIndexFoo(float* a) : my_a(a) {};

	void join(const MinIndexFoo& y)
	{
		if (min > y.min)
		{
			ind = y.ind;
			min = y.min;
		}
	}

};

//TODO: Create a function for parallel minimum

long ParallelMinIndexFoo(float* a, int size)
{
	MinIndexFoo mf(a);

	parallel_reduce(blocked_range<int>(0, size, 20), mf);
	return mf.ind;

}

int main() {
	float a[100];

	for (int i = 0; i < 100; ++i) {
		a[i] = -i;
	}

	long ind = ParallelMinIndexFoo(a, 100);
	printf("%d\n", ind);

	return 0;
}
