#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include <time.h>
#include <string>
#include <iostream>
#include <fstream>

#define SIZE 16777216
#define MASTER 0

using namespace std;

void quicksort(int* array, int start, int length) {

	if (length <= 1) {
		return;
	}

	int pivot = array[start + length / 2];
	swap(array[start], array[start + length / 2]);

	int partition = start;
	for (int index = start + 1; index < start + length; index++) {
		if (array[index] < pivot) {
			partition++;
			swap(array[index], array[partition]);
		}
	}

	swap(array[start], array[partition]);
	quicksort(array, start, partition - start);
	quicksort(array, partition + 1, start + length - partition - 1);
}

int* merge(int* left, int left_size, int* right, int right_size) {

	auto result = (int*)malloc((left_size + right_size) * sizeof(int));
	int i = 0, j = 0;

	for (int k = 0; k < left_size + right_size; k++) {
		if (i >= left_size) {
			result[k] = right[j];
			j++;
		}
		else if (j >= right_size || left[i] < right[j]) {
			result[k] = left[i];
			i++;
		}
		else {
			result[k] = right[j];
			j++;
		}
	}
	return result;
}

int main(int argc, char** argv) {

	int n = SIZE;
	int* data = NULL;
	int* chunk;
	int* received_chunk;
	int chunk_size, local_chunk_size, received_chunk_size, size, rank;
	MPI_Status status;
	double elapsed_time;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == MASTER) {
		ifstream fin("..\\input.txt");

		if (SIZE % size != 0) {
			chunk_size = SIZE / size + 1;
		}
		else {
			chunk_size = SIZE / size;
		}
		data = (int*)malloc(size * chunk_size * sizeof(int));
		for (int i = 0; i < SIZE; i++) {
			fin >> data[i];
		}
		for (int i = SIZE; i < size * chunk_size; i++) {
			data[i] = 0;
		}
		elapsed_time = -MPI_Wtime();
	}

	MPI_Barrier(MPI_COMM_WORLD);
	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

	if (SIZE % size != 0) {
		chunk_size = SIZE / size + 1;
	}
	else {
		chunk_size = SIZE / size;
	}
	chunk = (int*)malloc(chunk_size * sizeof(int));

	MPI_Scatter(data, chunk_size, MPI_INT, chunk, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);
	free(data);
	data = NULL;

	if (rank == size - 1) {
		local_chunk_size = SIZE - chunk_size * rank;
	}
	else {
		local_chunk_size = chunk_size;
	}
	quicksort(chunk, 0, local_chunk_size);

	for (int step = 1; step < size; step = 2 * step) {

		if (rank % (2 * step) != 0) {
			MPI_Send(chunk, local_chunk_size, MPI_INT, rank - step, 0, MPI_COMM_WORLD);
			break;
		}

		if (rank + step < size) {
			if (SIZE >= chunk_size * (rank + 2 * step)) {
				received_chunk_size = chunk_size * step;
			}
			else {
				received_chunk_size = SIZE - chunk_size * (rank + step);
			}
			received_chunk = (int*)malloc(received_chunk_size * sizeof(int));
			MPI_Recv(received_chunk, received_chunk_size, MPI_INT, rank + step, 0, MPI_COMM_WORLD, &status);
			data = merge(chunk, local_chunk_size, received_chunk, received_chunk_size);
			free(chunk);
			free(received_chunk);
			chunk = data;
			local_chunk_size = local_chunk_size + received_chunk_size;
		}
	}

	if (rank == MASTER) {
		elapsed_time += MPI_Wtime();
		ofstream fout("..\\output.txt");
		for (int i = 0; i < local_chunk_size; i++) {
			if (i % 100 == 0 && i != 0) {
				fout << endl;
			}
			fout << chunk[i] << " ";
		}
		printf("Quicksort %d integers on %d procs: %f miliseconds\n", SIZE, size, elapsed_time * 1000);
	}
	MPI_Finalize();
	return 0;
}