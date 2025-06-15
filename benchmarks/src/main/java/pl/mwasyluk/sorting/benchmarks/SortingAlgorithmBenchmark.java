package pl.mwasyluk.sorting.benchmarks;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import pl.mwasyluk.sorting.algorithms.SortingAlgorithm;
import pl.mwasyluk.sorting.algorithms.bubblesort.BubbleSort;
import pl.mwasyluk.sorting.algorithms.coutingsort.CountingSort;
import pl.mwasyluk.sorting.algorithms.heapsort.HeapSort;
import pl.mwasyluk.sorting.algorithms.insertionsort.InsertionSort;
import pl.mwasyluk.sorting.algorithms.mergesort.MergeSort;
import pl.mwasyluk.sorting.algorithms.quicksort.QuickSort;
import pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl.HighestPivotStrategy;
import pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl.MedianPivotStrategy;
import pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl.RandomPivotStrategy;
import pl.mwasyluk.sorting.algorithms.selectionsort.SelectionSort;
import pl.mwasyluk.sorting.util.IntArrayFactory;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 50, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 3)
@State(Scope.Benchmark)
public class SortingAlgorithmBenchmark {

    @Param({ "10", "100", "1_000", "100_000", "1_000_000" })
    private String aSize;

    @Param({
            "random",
            "sorted",
            "reversed",
            "sorted_with_shift_size/100->5",
            "sorted_with_shift_size/100->99",
            "sorted_with_random_end_1",
            "sorted_with_random_mid_1",
            "sorted_with_random_start_1",
            "sorted_with_random_end_half",
            "sorted_with_random_mid_half",
            "sorted_with_random_start_half",
    })
    private String bArrayType;

    @Param({
            // O(n^2)
            "SelectionSort",
            "BubbleSort",
            "InsertionSort",
            // O(n log n)
            "MergeSort",
            "QuickSort_HighestPivot",
            "QuickSort_MedianPivot",
            "QuickSort_RandomPivot",
            "HeapSort",
            // O(n + k)
            "CountingSort"
    })
    private String cAlgorithmType;

    private IntArrayFactory arrayFactory = new IntArrayFactory();
    private int size;
    private int[] originalArray;
    private SortingAlgorithm algorithm;

    private void validateSize(int size, String algorithmTypeString) {
        boolean valid;
        switch (algorithmTypeString) {
            case "SelectionSort":
            case "BubbleSort":
            case "InsertionSort":
            case "QuickSort_HighestPivot": // StackOverflow risk for large sizes
                valid = size <= 1000;
                break;
            default:
                valid = true;
        }
        assumeTrue(valid, "Size " + size + " is not valid for the algorithm: " + algorithmTypeString);
    }

    private void setUpSize(String sizeString) {
        try {
            this.size = Integer.parseInt(sizeString.replace("_", ""));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid size: " + sizeString);
        }
    }

    private void setUpAlgorithm(String algorithmTypeString) {
        switch (algorithmTypeString) {
            case "SelectionSort":
                algorithm = new SelectionSort();
                break;
            case "BubbleSort":
                algorithm = new BubbleSort();
                break;
            case "InsertionSort":
                algorithm = new InsertionSort();
                break;
            case "MergeSort":
                algorithm = new MergeSort();
                break;
            case "QuickSort_HighestPivot":
                algorithm = new QuickSort(new HighestPivotStrategy());
                break;
            case "QuickSort_MedianPivot":
                algorithm = new QuickSort(new MedianPivotStrategy());
                break;
            case "QuickSort_RandomPivot":
                algorithm = new QuickSort(new RandomPivotStrategy());
                break;
            case "HeapSort":
                algorithm = new HeapSort();
                break;
            case "CountingSort":
                algorithm = new CountingSort();
                break;
            default:
                throw new IllegalStateException("Unknown algorithm type: " + algorithmTypeString);
        }
    }

    private void setUpArray(String arrayTypeString, int size) {
        int interval;

        switch (arrayTypeString) {
            case "random":
                originalArray = arrayFactory.random(size);
                break;
            case "sorted":
                originalArray = arrayFactory.sorted(size);
                break;
            case "reversed":
                originalArray = arrayFactory.sortedReversed(size);
                break;
            case "sorted_with_shift_size/100->5":
                interval = size / 100;
                if (interval < 1) {
                    interval = 10;
                }
                originalArray = arrayFactory.sortedWithShiftInterval(size, interval, 5);
                break;
            case "sorted_with_shift_size/100->99":
                interval = size / 100;
                if (interval < 1) {
                    interval = 10;
                }
                originalArray = arrayFactory.sortedWithShiftInterval(size, interval, 99);
                break;
            case "sorted_with_random_end_1":
                originalArray = arrayFactory.sortedWithRandomPart(size, size - 1, size - 1);
                break;
            case "sorted_with_random_mid_1":
                originalArray = arrayFactory.sortedWithRandomPart(size, size / 2, size / 2);
                break;
            case "sorted_with_random_start_1":
                originalArray = arrayFactory.sortedWithRandomPart(size, 0, 0);
                break;
            case "sorted_with_random_end_half":
                originalArray = arrayFactory.sortedWithRandomPart(size, size / 2, size - 1);
                break;
            case "sorted_with_random_mid_half":
                originalArray = arrayFactory.sortedWithRandomPart(size, size / 4, size * 3 / 4);
                break;
            case "sorted_with_random_start_half":
                originalArray = arrayFactory.sortedWithRandomPart(size, 0, size / 2);
                break;
            default:
                throw new IllegalStateException("Unknown array type: " + arrayTypeString);
        }
    }

    @Setup(Level.Trial)
    public void setUp() {
        setUpSize(aSize);
        validateSize(size, cAlgorithmType);
        setUpArray(bArrayType, size);
        setUpAlgorithm(cAlgorithmType);
    }

    @Benchmark
    public void sort(Blackhole bh) {
        int[] copy = originalArray.clone();
        algorithm.sort(copy);
        bh.consume(copy);
    }
}
