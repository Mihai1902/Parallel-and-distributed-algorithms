#include <iostream>
#include <stdio.h>
#include <string>
#include <fstream>
#include <chrono>

#define SIZE 10000000

using namespace std;

int read_file(std::ifstream& input, int* result) {
	int i = 0;
	while (input >> result[i] && i < SIZE)
	{
		i++;
	}
	return i;
}

void write_file(std::ofstream& output, int arrSize, int* arr) {
	if (output.is_open()) {
		for (int i = 0; i < arrSize; i++)
			output << arr[i] << " ";
		output.close();
	}
	else
		std::cout << "Unable to open an output file.\n";
}

void quickSort(int* array, int low, int high)
{
	int i = low;
	int j = high;
	int pivot = array[(i + j) / 2];
	int temp;

	while (i <= j)
	{
		while (array[i] < pivot)
			i++;
		while (array[j] > pivot)
			j--;
		if (i <= j)
		{
			temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
	}
	if (j > low)
		quickSort(array, low, j);
	if (i < high)
		quickSort(array, i, high);
}

int main() {

	ifstream fin("input.txt");
	ofstream fout("output.txt");

	int* arr = (int*)malloc(SIZE * sizeof(int));
	int arraySize = read_file(fin, arr);

	auto start = std::chrono::high_resolution_clock::now();
	quickSort(arr, 0, SIZE - 1);
	auto finish = std::chrono::high_resolution_clock::now();

	std::cout << double(std::chrono::duration_cast<std::chrono::milliseconds>(finish - start).count()) << std::endl;

	write_file(fout, arraySize, arr);
	free(arr);
	return 0;
}
