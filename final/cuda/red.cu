/*
* Sergio Mercado A01020382
* Cuda : normal to soviet image
*/


	#include <stdlib.h>
	#include <stdio.h>
	#include <string>
	#include <opencv/highgui.h>
	//#include "utils/cheader.h"


	typedef enum color {BLUE, GREEN, RED} Color;


	__global__ void red(unsigned char *src, unsigned char *dest, int width, int height, int step, int channels){
	  int ren, col;
		float r, g, b;

		ren = blockIdx.x;
		col = threadIdx.x;
		r = 0; g = 0; b = 0;

		r = (float) src[(ren * step) + (col * channels) + RED];
		g = (float) src[(ren * step) + (col * channels) + GREEN];
		b = (float) src[(ren * step) + (col * channels) + BLUE];

		//Set only the desired rgb value
		dest[(ren * step) + (col * channels) + RED] =  (unsigned char) (0xFF);
		dest[(ren * step) + (col * channels) + GREEN] = (unsigned char) (g);
		dest[(ren * step) + (col * channels) + BLUE] = (unsigned char) (b);
	}


	int main(int argc, char* argv[]) {
		int step, size;
		unsigned char *dev_src, *dev_dest;

		//obtain image from source
		IplImage *src = cvLoadImage(argv[1], CV_LOAD_IMAGE_COLOR);
		IplImage *dest = cvCreateImage(cvSize(src->width, src->height), IPL_DEPTH_8U, 3);

		size = src->width * src->height * src->nChannels * sizeof(uchar);
		step = src->widthStep / sizeof(uchar);

		//Memory allocation on gpu
		cudaMalloc((void**) &dev_src, size);
		cudaMalloc((void**) &dev_dest, size);

		cudaMemcpy(dev_src, src->imageData, size, cudaMemcpyHostToDevice);


		//Write to file
		red<<<src->height, src->width>>>(dev_src, dev_dest, src->width, src->height, step, src->nChannels);
		cudaMemcpy(dest->imageData, dev_dest, size, cudaMemcpyDeviceToHost);
	  cvSaveImage("result.png", dest);

		cudaFree(dev_dest);
		cudaFree(dev_src);

		return 0;
	}
