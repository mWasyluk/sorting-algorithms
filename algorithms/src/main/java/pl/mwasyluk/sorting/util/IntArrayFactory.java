package pl.mwasyluk.sorting.util;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class IntArrayFactory {
    private Random random = new Random();

    private IntStream getStreamOfRandom(int size) {
        return random.ints(size, 0, size);
    }

    public int[] random(int size) {
        return getStreamOfRandom(size).toArray();
    }

    public int[] sorted(int size) {
        return getStreamOfRandom(size).sorted().toArray();
    }

    public int[] sortedWithShiftInterval(int size, int interval, int offset) {
        if (interval > size - 1 || interval < 1) {
            throw new IllegalArgumentException(
                    "interval cannot be bigger than the last element's index or smaller then 1.");
        }

        int[] arr = sorted(size);

        for (int i = interval; i < size; i += interval) {
            int tmp = arr[i];
            int newI = (size + i + offset) % size;
            arr[i] = arr[newI];
            arr[newI] = tmp;
        }

        return arr;
    }

    public int[] sortedWithRandomPart(int size, int firstRandomIndex, int lastRandomIndex) {
        if (firstRandomIndex < 0 || firstRandomIndex > size - 1 || lastRandomIndex < firstRandomIndex
                || lastRandomIndex > size - 1) {
            throw new IllegalArgumentException("Random boundary is out of the array boundary.");
        }

        int[] result, arr1, arr2, arr3;

        int sortedArraySize = firstRandomIndex;
        if (sortedArraySize > 0) {
            arr1 = sorted(firstRandomIndex);
        } else {
            arr1 = new int[0];
        }

        int randomArraySize = lastRandomIndex - firstRandomIndex + 1;
        if (randomArraySize > 0) {
            arr2 = random(lastRandomIndex - firstRandomIndex + 1);
        } else {
            arr2 = new int[0];
        }

        result = Arrays.copyOf(arr1, size);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);

        int currentSize = arr1.length + arr2.length;
        int adjArraySize = size - currentSize;
        if (adjArraySize > 0) {
            int currentMax = IntStream.of(result).max().getAsInt();
            arr3 = random.ints(size - (lastRandomIndex + 1), currentMax, size).sorted().toArray();
            System.arraycopy(arr3, 0, result, currentSize, arr3.length);
        }

        return result;
    }

    public int[] sortedReversed(int size) {
        int[] arr = sorted(size);
        for (int i = 0; i < size / 2; i++) {
            int tmp = arr[i];
            arr[i] = arr[size - 1 - i];
            arr[size - 1 - i] = tmp;
        }

        return arr;
    }
}
