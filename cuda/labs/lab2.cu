/*
*	Sergio Isaac Mercado Silvano
*	A01020382
*	CUDA - Lab 2
*	19/11/2019
*/

#include "cuda_runtime.h"
#include <stdio.h>

#define N 10000

__global__ void kernel(float * a, float * b, float * c, int n){
  int row = blockIdx.y * blockDim.y + threadIdx.y;
  int col = blockIdx.x * blockDim.x + threadIdx.x;

  float temp = 0.0;

  while(row < n && col < n){
    for(int i = 0; i < n; i++){
      temp += a[row * n + i] * b[i * n + col];
    }
  }

  c[row * n + col] = temp;
}

void fill(float * arr, int n, float val){
  for(int i = 0; i < n; i++){
    arr[i] = val/(float)i;
  }
}

void display(float * arr, int n){
  for(int i = 0; i < n; i++){
    printf("%5f ", arr[i]);
  }
}

int main(void){
  float * a, *b, *c;
  float *d_a, *d_b, *d_c;

  a = (float *)malloc(sizeof(float) * N * N);
  b = (float *)malloc(sizeof(float) * N * N);
  c = (float *)malloc(sizeof(float) * N * N);

  cudaMalloc((void**) &d_a, sizeof(float) * N * N);
  cudaMalloc((void**) &d_b, sizeof(float) * N * N);
  cudaMalloc((void**) &d_c, sizeof(float) * N * N);

  fill(a, N * N, 1.0);
  fill(b, N * N, 2.0);

  dim3 threadsPerBlock(N,N);
  dim3 blocksPerGrid(1,1);

  cudaMemcpy(d_a, &a, N * N * sizeof(float), cudaMemcpyHostToDevice);
  cudaMemcpy(d_b, &b, N * N * sizeof(float), cudaMemcpyHostToDevice);

  kernel <<<blocksPerGrid,threadsPerBlock>>>(d_a,d_b,d_c, N * N);

  cudaMemcpy(c, &d_c, N * N * sizeof(float), cudaMemcpyDeviceToHost);

  display(c, N * N);

  free(a);
  free(b);
  free(c);

  cudaFree(d_a);
  cudaFree(d_b);
  cudaFree(d_c);

  return 0;
}
