#include "WriteOutFile.h"
#include "Globals.h"
//extern unsigned int couterValues[256];


RetVal WriteOutFile(string fileName)
{
    ofstream outputFile(fileName.c_str());

	if (outputFile.is_open() == false)
	{
        cout << "WriteOutFile: Output file " << fileName << " could not be opened." << endl;
		return RET_ERROR;
	}

	HashMapa::const_accessor ac;
	for (int i = 0; i < mapa.size(); i++)
	{
		if (mapa.find(ac, i))
		{
			outputFile << i << ":\t" << ac->second << endl;
		}
	}

	

    outputFile.close();

    return RET_OK;
}
