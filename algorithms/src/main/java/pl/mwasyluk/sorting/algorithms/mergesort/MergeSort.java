package pl.mwasyluk.sorting.algorithms.mergesort;

import java.util.Arrays;

import pl.mwasyluk.sorting.algorithms.SortingAlgorithm;

public class MergeSort extends SortingAlgorithm {
    @Override
    protected void execute_sorting(int[] tab) {
        if (tab.length < 2) {
            return;
        }

        int mid = tab.length / 2;
        int[] left = Arrays.copyOfRange(tab, 0, mid);
        int[] right = Arrays.copyOfRange(tab, mid, tab.length);

        execute_sorting(left);
        execute_sorting(right);

        merge(tab, left, right);

    }

    private void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }
    }
}
