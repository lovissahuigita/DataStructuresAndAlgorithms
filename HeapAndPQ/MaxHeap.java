import java.util.NoSuchElementException;

/**
 * This is a Max Heap class
 * @author Lovissa Winyoto
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] arr;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a MaxHeap.
     */
    public MaxHeap() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        if (size >= arr.length - 1) {
            T[] arr2 = (T[]) new Comparable[arr.length * 2];
            for (int index = 1; index < arr.length; index++) {
                arr2[index] = arr[index];
            }
            arr = arr2;
        }
        arr[++size] = item;
        bubbleUp(size);
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("No Such Element!");
        } else {
            int index = 1;
            T root = arr[index];
            arr[1] = arr[size];
            arr[size] = null;
            bubbleDown(index);
            size--;
            return root;
        }
    }

    /**
     * Private helper method that helps maintain the maxheap
     * property. This method brings a value that is smaller
     * than its children down the heap
     * @param parent index of the value that needs to be bubbled down
     */
    private void bubbleDown(int parent) {
        int left = parent * 2;
        int right = parent * 2 + 1;
        if (right <= arr.length) {
            if ((arr[left] == null) && (arr[right] == null)) {
                left = parent * 2;
                right = parent * 2 + 1;
            } else if ((arr[left] != null) && (arr[right] == null)) {
                if (arr[left].compareTo(arr[parent]) > 0) {
                    switchData(left, parent);
                    bubbleDown(left);
                }
            } else if ((arr[right] != null) && (arr[left] != null)) {
                if (arr[left].compareTo(arr[right]) > 0) {
                    if (arr[left].compareTo(arr[parent]) > 0) {
                        switchData(left, parent);
                        bubbleDown(left);
                    }
                } else {
                    if (arr[right].compareTo(arr[parent]) > 0) {
                        switchData(right, parent);
                        bubbleDown(right);
                    }
                }
            }
        }
    }

    /**
     * Private helper method that switch values between two data
     * in the maxheap
     * @param a the first data
     * @param b the second data
     */
    private void switchData(int a, int b) {
        T data = arr[a];
        arr[a] = arr[b];
        arr[b] = data;
    }

    /**
     * Private helper method that helps maintain the maxheap
     * property. This method brings a value that is greater
     * than its children up the heap
     * @param childIndex index of the value that needs to be bubbled up
     */
    private void bubbleUp(int childIndex) {
        while (childIndex > 1) {
            int parentIndex = childIndex / 2;
            if (arr[childIndex].compareTo(arr[parentIndex]) > 0) {
                switchData(childIndex, parentIndex);
            }
            childIndex = parentIndex;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        return arr;
    }
}
