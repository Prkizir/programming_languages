/*
*	Sergio Isaac Mercado Silvano
*	A01020382
*	CUDA - Lab 1
*	12/11/2019
*/

#include <stdio.h>
#include <stdlib.h>
#include "cuda_runtime.h"

#define MIN(a,b)(a<b?a:b)

#define NUM_RECTS 1e9
#define THREADS 256
#define BLOCKS MIN(32,(NUM_RECTS + THREADS - 1)/THREADS)

__global__ void kernel(float width, float *results){
	__shared__ long cache[THREADS]; //Shared array between threads for all blocks

	int i, cacheIndex;
	float acum, mid, height;

	i = threadIdx.x + (blockIdx.x * blockDim.x); //Id for current thread
	cacheIndex = threadIdx.x;

	acum = 0;	//Acumulated results

	//Actual approximation calculations
	while(i < NUM_RECTS){
		mid = (i + 0.5) * width;
		height = 4.0/(1.0 + mid * mid);
		acum += height;

		i+= blockDim.x * gridDim.x;
	}

	cache[cacheIndex] = acum; //Adding the result for current block (cacheIndex)

	__syncthreads(); //Wait for all threads to finish up previous operations (mutex)

	//Reduction for adding up all partial results in every block

	i = blockDim.x/2;

	while(i > 0){
		if(cacheIndex < i){
			cache[cacheIndex] += cache[cacheIndex + i];
		}

		__syncthreads(); //Same as previous syncthreads()
		i /= 2;
	}

	//End Reduction

	if(cacheIndex == 0){ //Base case
		results[blockIdx.x] = cache[cacheIndex]; //Store results in corresponding index
	}
}

int main(void){
	float acum, width, area;
	float *results, *dr;
	int i;

	width = 1.0/ (double) NUM_RECTS;

	/*
	*	Malloc for result array where we will save all the calculated values in
	*	kernel call
	*/
	results = (float*) malloc(BLOCKS * sizeof(float));

	/*
	*	cudaMalloc for working array that will let us work with the kernel
	*	environment
	*/
	cudaMalloc((void**) &dr, BLOCKS * sizeof(float));



	kernel<<<BLOCKS,THREADS>>> (width, dr); //kernel call for GPU calculation


  /*
  * cudaMemcpy for storing the results from the dr* array (which was used)
  * in the kernel to store the results into the results* array for further use
  */
	cudaMemcpy(results, dr, BLOCKS * sizeof(float), cudaMemcpyDeviceToHost);

	/*
	*	CPU operation for adding every value stored in the previously calculated
	* results from the kernel call to an acumulator variable.
	*/

	acum = 0;
	for(i = 0; i < BLOCKS; i++){
		acum += results[i];
	}

	area = width * acum; //Final calculation for the area (pi)

	printf("PI = %.15lf\n", area);


	//IMPORTANT: Free up device and host memory

	cudaFree(dr);
	free(results);

	return 0;
}

//
