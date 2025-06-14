package pl.mwasyluk.sorting.algorithms.heapsort;

import pl.mwasyluk.sorting.algorithms.SortingAlgorithm;

public class HeapSort extends SortingAlgorithm {
    @Override
    protected void execute_sorting(int[] tab) {
        int n = tab.length;
        // Build heap by iterating through all non-leaf nodes, from bottom to top
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(tab, n, i);
        }

        // Swap the first value (the biggest in the max-heap) with the last unsorted
        // element, and fix the heap
        for (int i = n - 1; i > 0; i--) {
            swap(tab, i, 0);

            heapify(tab, i, 0);
        }
    }

    private void heapify(int[] arr, int heapSize, int rootIndex) {
        // Determine indexes of the left and right children of the given root
        int largest = rootIndex;
        int left = 2 * rootIndex + 1;
        int right = 2 * rootIndex + 2;

        // Find the root successor (the highest value among root, left, and right child)
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }

        // Recursively restore the heap if the root was replaced
        if (largest != rootIndex) {
            int tmpLargest = arr[largest];
            arr[largest] = arr[rootIndex];
            arr[rootIndex] = tmpLargest;

            heapify(arr, heapSize, largest);
        }
    }

}
