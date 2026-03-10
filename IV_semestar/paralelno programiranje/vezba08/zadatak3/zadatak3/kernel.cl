__kernel void zadatak3(__global int *A, __global int *B, __global int *C)												{												
	int idx_row = get_global_id(0);				
	int idx_col = get_global_id(1);				
	int col_size = get_global_size(1);			
	int address = idx_row*col_size + idx_col;	
	int a_address = 0;
	int b_address = 0;
	for(int i = 0; i < col_size; i++)
	{
		a_address = idx_row*col_size + i;
		b_address = i * col_size + idx_col;
		C[address] += A[a_address] * B[b_address];					

	}
	return;										
};