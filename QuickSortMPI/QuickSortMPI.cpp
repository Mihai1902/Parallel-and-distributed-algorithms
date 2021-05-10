#include "mpi.h"
#include "header.h"

int read_file(std::ifstream& input, int* result);
int partition(int* A, int lo, int hi);
void quicksort(int* A, int lo, int hi);
int lomuto_partition(int* A, int lo, int hi);
int sort_recursive(int* arr, int arrSize, int currProcRank, int maxRank, int rankIndex);
void write_file(std::ofstream& output, int arrSize, int* arr);

int main(int argc, char* argv[]) {

	int size, rank;
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	for (int iter = 0; iter < size; iter++) {
		int rankPower = 0;
		while (pow(2, rankPower) <= rank)
			rankPower++;

		if (rank == 0) {
			auto start = std::chrono::high_resolution_clock::now();

			std::ifstream input("input.txt");
			if (!input) {
				std::cout << "Input file not found.\n"; 
				return 1; 
			}

			int* numbers = (int*)malloc(MAX_SIZE * sizeof(int));
			int arraySize = read_file(input, numbers);

			sort_recursive(numbers, arraySize, rank, size - 1, rankPower);

			std::ofstream output("output.txt");
			write_file(output, arraySize, numbers);
			auto finish = std::chrono::high_resolution_clock::now();
			std::cout << double(std::chrono::duration_cast<std::chrono::milliseconds>(finish - start).count()) << std::endl;
		}
		else {
			MPI_Status status;
			int subarray_size;
			MPI_Probe(MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
			MPI_Get_count(&status, MPI_INT, &subarray_size);

			int source_process = status.MPI_SOURCE;
			int* subarray = (int*)malloc(subarray_size * sizeof(int));

			MPI_Recv(subarray, subarray_size, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			sort_recursive(subarray, subarray_size, rank, size - 1, rankPower);
			MPI_Send(subarray, subarray_size, MPI_INT, source_process, 0, MPI_COMM_WORLD);
		}
	}
	MPI_Finalize();
	return 0;
}