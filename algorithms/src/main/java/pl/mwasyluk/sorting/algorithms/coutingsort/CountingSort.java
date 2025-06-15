package pl.mwasyluk.sorting.algorithms.coutingsort;

import pl.mwasyluk.sorting.algorithms.SortingAlgorithm;

public class CountingSort extends SortingAlgorithm {
    @Override
    protected void execute_sorting(int[] tab) {
        // Find the max value in the given array
        int max = tab[0];
        for (int value : tab) {
            if (value > max)
                max = value;
        }

        // Use the max value to determine the size of the helper array, which will hold
        // number of occurrences of every value
        int[] count = new int[max + 1];

        // Count occurrences of every value by incrementing the corresponding index in
        // the helper array
        for (int value : tab) {
            count[value]++;
        }

        // Accumulate counts so each index indicates the position of the last value
        // occurance in the sorted array
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Move elements from the source array to their positions in the result array
        // indicated by the cumulative count
        // Iterate from the end to ensure the sort stability
        int[] result = new int[tab.length];
        for (int i = tab.length - 1; i >= 0; i--) {
            int value = tab[i];
            int position = count[value] - 1;

            result[position] = value;
            count[value]--;
        }

        // Copy the sorted values back into the source array
        System.arraycopy(result, 0, tab, 0, tab.length);
    }
}
