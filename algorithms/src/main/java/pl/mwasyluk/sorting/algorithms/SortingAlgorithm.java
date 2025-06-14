package pl.mwasyluk.sorting.algorithms;

public abstract class SortingAlgorithm {
    private int executionTime;

    // Allows client to sort array with single visible method
    // Displays original array and starts the timer
    public final void sort(int[] tab) {
        long startTime = System.currentTimeMillis();
        execute_sorting(tab);
        long endTime = System.currentTimeMillis();
        this.setExecutionTime((int) (endTime - startTime));
    }

    // Forces subclasses to provide sort algorithm
    abstract protected void execute_sorting(int[] tab);

    protected void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void setExecutionTime(int time) {
        this.executionTime = time;
    }

    public final int getExecutionTime() {
        return this.executionTime;
    }
}
