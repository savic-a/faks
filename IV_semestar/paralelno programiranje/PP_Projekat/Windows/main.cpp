#include <iostream>
#include <stdlib.h>
#include "BitmapRawConverter.h"
#include "tbb/tick_count.h"
#include "tbb/task_group.h"

#define __ARG_NUM__			6
#define THRESHOLD			128

using namespace tbb;
using namespace std;

static long cutOff = 500;

// Prewitt operators
const int FILTER_HOR_3x3[] = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
const int FILTER_VER_3x3[] = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

const int FILTER_HOR_5x5[] = { 9, 9, 9, 9, 9,
							   9, 5, 5, 5, 9, 
							   -7, -3, 0, -3, -7, 
							   -7, -3, -3, -3, -7, 
							   -7, -7, -7, -7, -7 };

const int FILTER_VER_5x5[] = { 9, 9, -7, -7, -7,
						       9, 5, -3, -3, -7,
						       9, 5, 0, -3, -7,
						       9, 5, -3, -3, -7,
						       9, 9, -7, -7, -7 };

const int FILTER_HOR_7x7[] = { 1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1,
							   1, 1, 1, 0, -1, -1, -1 };

const int FILTER_VER_7x7[] = { 1, 1, 1, 1,  1,  1,  1,
							   1, 1, 1, 1,  1,  1,  1,
							   1, 1, 1, 1,  1,  1,  1,
							   0, 0, 0, 0,  0,  0,  0,
							   -1, -1, -1, -1, -1, -1, -1,
							   -1, -1, -1, -1, -1, -1, -1,
							   -1, -1, -1, -1, -1, -1, -1 };

struct Dimension
{
	int width_start;
	int width_end;
	int height_start;
	int height_end;

	Dimension(int width_start, int width_end, int height_start, int height_end) :
		width_start(width_start), width_end(width_end), height_start(height_start), height_end(height_end) {}
	Dimension() : width_start(0), width_end(0), height_start(0), height_end(0) {}
};

struct Filter
{
	int size;
	int distance;
	const int* filterHor;
	const int* filterVer;

	Filter(int size, int distance, const int* filterHor, const int* filterVer) : size(size), distance(distance), filterHor(filterHor), filterVer(filterVer) {}
};

/**
* @brief Serial version of edge detection algorithm implementation using Prewitt operator
* @param inBuffer buffer of input image
* @param outBuffer buffer of output image
* @param width image width
* @param filter Filter struct
* @param dimension image boundary structure
*/
void filter_serial_prewitt(int *inBuffer, int *outBuffer, int width, Filter filter, Dimension dimension) 
{
	for (int i = dimension.width_start; i < dimension.width_end; i++)
	{
		for (int j = dimension.height_start; j < dimension.height_end; j++)
		{
			int Gx = 0, Gy = 0, G = 0;
			int offset = (filter.size - 1) / 2;

			for (int m = 0; m < filter.size; m++) {				            
				for (int n = 0; n < filter.size; n++) {			
					Gx += filter.filterHor[m * filter.size + n] * inBuffer[(j - offset + m) * width + (i - offset + n)];
					Gy += filter.filterVer[m * filter.size + n] * inBuffer[(j - offset + m) * width + (i - offset + n)];
				}
			}
			G = abs(Gx) + abs(Gy);

			if (G < THRESHOLD) { 
				outBuffer[j * width + i] = 0;
			}
			else 
				outBuffer[j * width + i] = 255; 
		}
	}
}


/**
* @brief Serial version of edge detection algorithm
* @param inBuffer buffer of input image
* @param outBuffer buffer of output image
* @param width image width
* @param filter Filter struct
* @param dimension image boundary structure
*/
void filter_serial_edge_detection(int* inBuffer, int* outBuffer, int width, Filter filter, Dimension dimension)
{
	for (int i = dimension.width_start; i < dimension.width_end; i++)
	{
		for (int j = dimension.height_start; j < dimension.height_end; j++)
		{
			int P = 0, O = 0, R = 0;
			int offset = (filter.distance - 1) / 2;

			for (int m = 0; m < filter.distance; m++) {
				for (int n = 0; n < filter.distance; n++) {
					if (inBuffer[(j - offset + m) * width + (i - offset + n)] > THRESHOLD) P = 1;
					if (inBuffer[(j - offset + m) * width + (i - offset + n)] < THRESHOLD) O = 1;
				}
			}
			R = abs(P) - abs(O);

			if (R == 0)
				outBuffer[j * width + i] = 255;
			else
				outBuffer[j * width + i] = 0;

		}
	}
}


/**
* @brief Parallel version of algorithms
* @param inBuffer buffer of input image
* @param outBuffer buffer of output image
* @param width image width
* @param dimension image boundary structure
* @param filter Filter struct
* @param void (serial_function) 
*/
void parallel(int *inBuffer, int *outBuffer, int width, Dimension dimension, Filter filter,
	void (serial_function)(int*, int*, int, Filter, Dimension))
{
	if (abs(dimension.width_end - dimension.width_start) <= cutOff ||
		abs(dimension.height_end - dimension.height_start) <= cutOff)
		serial_function(inBuffer, outBuffer, width, filter, dimension);

	else {
		task_group t;
		Dimension d;
		t.run([&]() {
			d.width_start = dimension.width_start;
			d.width_end = dimension.width_start + (dimension.width_end - dimension.width_start) / 2;
			d.height_start = dimension.height_start;
			d.height_end = dimension.height_start + (dimension.height_end - dimension.height_start) / 2;
			parallel(inBuffer, outBuffer, width, d, filter, serial_function);
		});
		t.run([&]() {
			d.width_start = dimension.width_start;
			d.width_end = dimension.width_start + (dimension.width_end - dimension.width_start) / 2;
			d.height_start = dimension.height_start + (dimension.height_end - dimension.height_start) / 2;
			d.height_end = dimension.height_end;
			parallel(inBuffer, outBuffer, width, d, filter, serial_function);
		});
		t.run([&]() {
			d.width_start = dimension.width_start + (dimension.width_end - dimension.width_start) / 2;
			d.width_end = dimension.width_end;
			d.height_start = dimension.height_start;
			d.height_end = dimension.height_start + (dimension.height_end - dimension.height_start) / 2;
			parallel(inBuffer, outBuffer, width, d, filter, serial_function);
		});
		t.run([&]() {
			d.width_start = dimension.width_start + (dimension.width_end - dimension.width_start) / 2;
			d.width_end = dimension.width_end;
			d.height_start = dimension.height_start + (dimension.height_end - dimension.height_start) / 2;
			d.height_end = dimension.height_end;
			parallel(inBuffer, outBuffer, width, d, filter, serial_function);
		});
		t.wait();
	}
}


void run_test_nr(int testNr, BitmapRawConverter* ioFile, char* outFileName, int* outBuffer, unsigned int width, unsigned int height)
{
	// initialize values
	Filter filter = Filter(3, 3, FILTER_HOR_3x3, FILTER_VER_3x3);   // filter_size, distance, filter_hor, filter_ver
	int offset = 0; Dimension dimension = Dimension();
	
	tick_count startTime;
	tick_count endTime;
	
	switch (testNr)
	{
		case 1:
			offset = (filter.size - 1) / 2;
			dimension = Dimension(offset, width - offset, offset, height - offset);   // dimension new pictures
			cout << "Running serial version of edge detection using Prewitt operator" << endl;
			startTime = tick_count::now();
			filter_serial_prewitt(ioFile->getBuffer(), outBuffer, width, filter, dimension);
			endTime = tick_count::now();
			cout << "Serial time using Prewitt operator: \t\t\t" << (endTime - startTime).seconds() << " seconds\n";
			break;

		case 2:
			offset = (filter.size - 1) / 2;
			dimension = Dimension(offset, width - offset, offset, height - offset);   // dimension new pictures
			cout << "\nRunning parallel version of edge detection using Prewitt operator" << endl;
			startTime = tick_count::now();
			parallel(ioFile->getBuffer(), outBuffer, width, dimension, filter, &filter_serial_prewitt);
			endTime = tick_count::now();
			cout << "Parallel time using Prewitt operator: \t\t\t" << (endTime - startTime).seconds() << " seconds\n";
			break;

		case 3:
			offset = (filter.distance - 1) / 2;
			dimension = Dimension(offset, width - offset, offset, height - offset);   // dimension new pictures
			cout << "\nRunning serial version of edge detection" << endl;
			startTime = tick_count::now();
			filter_serial_edge_detection(ioFile->getBuffer(), outBuffer, width, filter, dimension);
			endTime = tick_count::now();
			cout << "Serial edge time: \t\t\t" << (endTime - startTime).seconds() << " seconds\n";
			break;

		case 4:
			offset = (filter.distance - 1) / 2;
			dimension = Dimension(offset, width - offset, offset, height - offset);   // dimension new pictures
			cout << "\nRunning parallel version of edge detection" << endl;
			startTime = tick_count::now();
			parallel(ioFile->getBuffer(), outBuffer, width, dimension, filter, &filter_serial_edge_detection);
			endTime = tick_count::now();
			cout << "Parallel edge time: \t\t\t" << (endTime - startTime).seconds() << " seconds\n" << endl;
			break;

		default:
			cout << "ERROR: invalid test case, must be 1, 2, 3 or 4!";
			break;
	}

	ioFile->setBuffer(outBuffer);
	ioFile->pixelsToBitmap(outFileName);
}

/**
* @brief Print program usage.
*/
void usage()
{
	cout << "\n\ERROR: call program like: " << endl << endl; 
	cout << "ProjekatPP.exe";
	cout << " input.bmp";
	cout << " outputSerialPrewitt.bmp";
	cout << " outputParallelPrewitt.bmp";
	cout << " outputSerialEdge.bmp";
	cout << " outputParallelEdge.bmp" << endl << endl;
}

int main(int argc, char * argv[])
{
	if(argc != __ARG_NUM__)
	{
		usage();
		return 0;
	}
	
	BitmapRawConverter inputFile(argv[1]);
	BitmapRawConverter outputFileSerialPrewitt(argv[1]);
	BitmapRawConverter outputFileParallelPrewitt(argv[1]);
	BitmapRawConverter outputFileSerialEdge(argv[1]);
	BitmapRawConverter outputFileParallelEdge(argv[1]);

	unsigned int width, height;

	int test;
	
	width = inputFile.getWidth();
	height = inputFile.getHeight();

	int* outBufferSerialPrewitt = new int[width * height];
	int* outBufferParallelPrewitt = new int[width * height];

	memset(outBufferSerialPrewitt, 0x0, width * height * sizeof(int));
	memset(outBufferParallelPrewitt, 0x0, width * height * sizeof(int));

	int* outBufferSerialEdge = new int[width * height];
	int* outBufferParallelEdge = new int[width * height];

	memset(outBufferSerialEdge, 0x0, width * height * sizeof(int));
	memset(outBufferParallelEdge, 0x0, width * height * sizeof(int));

	// serial version Prewitt
	run_test_nr(1, &outputFileSerialPrewitt, argv[2], outBufferSerialPrewitt, width, height);

	// parallel version Prewitt
	run_test_nr(2, &outputFileParallelPrewitt, argv[3], outBufferParallelPrewitt, width, height);

	// serial version special
	run_test_nr(3, &outputFileSerialEdge, argv[4], outBufferSerialEdge, width, height);

	// parallel version special
	run_test_nr(4, &outputFileParallelEdge, argv[5], outBufferParallelEdge, width, height);

	// verification
	cout << "Verification: ";
	test = memcmp(outBufferSerialPrewitt, outBufferParallelPrewitt, width * height * sizeof(int));

	if(test != 0)
	{
		cout << "Prewitt FAIL!" << endl;
	}
	else
	{
		cout << "Prewitt PASS." << endl;
	}

	test = memcmp(outBufferSerialEdge, outBufferParallelEdge, width * height * sizeof(int));

	if(test != 0)
	{
		cout << "Edge detection FAIL!" << endl;
	}
	else
	{
		cout << "Edge detection PASS." << endl;
	}

	// clean up
	delete outBufferSerialPrewitt;
	delete outBufferParallelPrewitt;

	delete outBufferSerialEdge;
	delete outBufferParallelEdge;

	return 0;
} 