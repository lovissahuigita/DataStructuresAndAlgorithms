import java.util.Comparator;
import java.util.Random;

/**
 * @author Lovissa Winyoto
 * v 1.3
 */
public class Sorting {

    /**
     * Implement bubble sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void bubblesort(T[] arr, Comparator<T> comparator) {
        if ((arr != null) && (comparator != null)) {
            boolean isSorted = false;
            for (int index = 0; !isSorted && index < arr.length; index++) {
                isSorted = true;
                for (int x = 0; (x + 1) < arr.length - index; x++) {
                    if (comparator.compare(arr[x], arr[x + 1]) > 0) {
                        swap(arr, x, x + 1);
                        isSorted = false;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal Argument!");
        }
    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionsort(T[] arr, Comparator<T> comparator) {
        insertionsort(arr, comparator, 1);
    }

    /**
     * insertion sort with interval
     * @param arr the array
     * @param comparator the comparator type T
     * @param interval every how many data you are going to check
     * @param <T> generic method
     */
    private static <T> void insertionsort(T[]arr, Comparator<T> comparator,
                                          int interval) {
        if ((arr != null) && (comparator != null)) {
            for (int index = interval; index < arr.length; index++) {
                boolean isSorted = false;
                for (int count = index; !isSorted
                        && count >= interval; count -= interval) {
                    isSorted = true;
                    if (comparator.compare(arr[count],
                            arr[count - interval]) < 0) {
                        swap(arr, count, count - interval);
                        isSorted = false;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal Argument");
        }
    }
    /**
     * Implement shell sort.
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log(n))
     * <p>
     * Note that there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void shellsort(T[] arr, Comparator<T> comparator) {
        if ((arr != null) && (comparator != null)) {
            for (int interval = arr.length / 2; interval > 0; interval /= 2) {
                insertionsort(arr, comparator, interval);
            }
        } else {
            throw new IllegalArgumentException("Illegal Argument");
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     * <p>
     * int pivotIndex = r.nextInt(b - a) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Note that there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws IllegalArgumentException if the array or comparator or rand is
     *                                  null
     */
    public static <T> void quicksort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if ((arr != null) && (comparator != null) && (rand != null)) {
            quicksort(arr, comparator, rand, 0, arr.length - 1);
        } else {
            throw new IllegalArgumentException("Illegal Argument!");
        }
    }

    /**
     * private static helper method that quicksort data
     *
     * @param arr        the array to be sorted
     * @param comparator comparator type T
     * @param rand       random
     * @param indexA     starting index
     * @param indexB     ending index
     * @param <T>        generic method
     */
    private static <T> void quicksort(T[] arr, Comparator<T> comparator,
                                      Random rand, int indexA, int indexB) {
        if (indexB > indexA) {
            int wall = indexA;
            int pivotIndex = rand.nextInt(indexB - indexA) + indexA;
            // put the pivot in the end of the array
            swap(arr, pivotIndex, indexB);
            for (int current = indexA; current < indexB; current++) {
                // compare current ... ... ... ... ... pivot
                if (comparator.compare(arr[current], arr[indexB]) < 0) {
                    swap(arr, wall, current);
                    wall++;
                }
            }
            swap(arr, wall, indexB);
            quicksort(arr, comparator, rand, indexA, wall - 1);
            quicksort(arr, comparator, rand, wall + 1, indexB);
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * <p>
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergesort(T[] arr, Comparator<T> comparator) {
        if ((arr != null) && (comparator != null)) {
            mergesort(arr, comparator, 0, arr.length - 1);
        } else {
            throw new IllegalArgumentException("Illegal Argument");
        }
    }

    /**
     * private helper method that mergesort data
     *
     * @param arr        the array containing data
     * @param comparator comparator type T
     * @param indexA     starting index
     * @param indexB     ending index
     * @param <T>        generic method
     */
    private static <T> void mergesort(T[] arr, Comparator<T> comparator,
                                      int indexA, int indexB) {
        if ((indexB - indexA) > 0) {
            int cutOff = ((indexB - indexA) / 2) + indexA;
            mergesort(arr, comparator, indexA, cutOff);
            mergesort(arr, comparator, cutOff + 1, indexB);
            merge(arr, comparator, indexA, indexB, cutOff);
        }

    }

    /**
     * private helper method that merges two mergesorted array
     *
     * @param arr        the array that contains all data
     * @param comparator comparator of T
     * @param indexA     starting index
     * @param indexB     ending index
     * @param cutOff     index that divides the array into two
     * @param <T>        generic method
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator,
                                  int indexA, int indexB, int cutOff) {
        T[] arrA = (T[]) new Object[cutOff - indexA + 1]; //TODO possible bug
        T[] arrB = (T[]) new Object[indexB - cutOff];

        // put the elements in the temporary array
        int a = 0;
        int b = 0;
        for (int index = indexA; index <= indexB; index++) {
            if (index <= cutOff) {
                arrA[a++] = arr[index];
            } else {
                arrB[b++] = arr[index];
            }
        }

        a = 0;
        b = 0;
        int count = indexA;
        while (count <= indexB) {
            if (a < arrA.length) {
                if (b < arrB.length) {
                    if (comparator.compare(arrA[a], arrB[b]) <= 0) {
                        arr[count++] = arrA[a++];
                    } else {
                        arr[count++] = arrB[b++];
                    }
                } else {
                    arr[count++] = arrA[a++];
                }
            } else {
                if (b < arrB.length) {
                    arr[count++] = arrB[b++];
                }
            }
        }
    }

    /**
     * Implement radix sort.
     * <p>
     * Remember you CANNOT convert the ints to strings.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     * <p>
     * You may use an ArrayList or LinkedList if you wish,
     * but it may only be used inside radixsort and any radix sort helpers
     * Do NOT use these classes with other sorts.
     *
     * @param arr the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the array is null
     */
    public static int[] radixsort(int[] arr) {
        if (arr != null) {
            // separate the negatives and positives >> negatives in dummy[]
            int[] dummy = new int[arr.length];
            int dummyIndex = 0;
            int maxNeg = 0;
            for (int index = 0; index < arr.length; index++) {
                if (arr[index] < 0) {
                    maxNeg = Math.min(maxNeg, arr[index]);
                    dummy[dummyIndex++] = arr[index];
                }
            }
            int maxDigitNeg = maxDigit(maxNeg);

            // positives in arr[] from the back
            int fromBack = arr.length - 1;
            int maxPos = 0;
            for (int index = arr.length - 1; index >= 0; index--) {
                if (arr[index] >= 0) {
                    maxPos = Math.max(maxPos, arr[index]);
                    arr[fromBack--] = arr[index];
                }
            }
            int maxDigitPos = maxDigit(maxPos);

            // combine positives and negatives in arr[]
            for (int index = 0; index < dummyIndex; index++) {
                arr[index] = dummy[index];
            }
            radixsort(arr, dummy, dummyIndex, arr.length - 1, maxDigitPos);
            radixsort(arr, dummy, 0, dummyIndex - 1, maxDigitNeg);

            // swap the negative
            for (int index = 0; index < dummyIndex - 1; index++) {
                dummy[index] = arr[index];
            }
            for (int index = dummyIndex - 1, counter = 0;
                 index >= 0; index--, counter++) {
                arr[index] = dummy[counter];
            }
        } else {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        return arr;
    }

    /**
     * Private helper method that takes in a number,
     * then counts the number of digits that build
     * the number
     *
     * @param number to be checks
     * @return the number of digits that build the number
     */
    private static int maxDigit(int number) {
        number = Math.abs(number);
        return (number > 9999) ? ((number > 999999)
                ? ((number > 99999999) ? ((number > 999999999) ? 10 : 9)
                : ((number > 9999999) ? 8 : 7)) : (number > 99999) ? 6 : 5)
                : ((number > 99) ? ((number > 999) ? 4 : 3)
                : ((number > 9) ? 2 : 1));
    }

    /**
     * Private helper method that do radix sort from certain index
     * and put the sorted elements back in the array
     *
     * @param arr   the array needs to be sorted
     * @param dummy dummy array
     * @param front starting index
     * @param end   ending index
     * @param k     number of maximum digit
     */
    private static void radixsort(int[] arr, int[] dummy,
                                  int front, int end, int k) {
        int iteration = 1;
        while (iteration <= k) {
            int[] totals = new int[10];

            // count the number of items in each bucket
            for (int index = front; index <= end; index++) {
                totals[getAt(arr[index], iteration)]++;
            }

            // find the cumulative number of items for each bucket
            for (int index = 1; index < totals.length; index++) {
                totals[index] = totals[index - 1] + totals[index];
            }

            //sort it in the dummy
            for (int index = end; index >= front; index--) {
                int totalsIndex = getAt(arr[index], iteration);
                dummy[front + (totals[totalsIndex]) - 1] = arr[index];
                totals[totalsIndex]--;
            }
            iteration++;

            // transfer the data in dummy to arr
            for (int index = front; index <= end; index++) {
                arr[index] = dummy[index];
            }
        }
    }

    /**
     * Exponential helper method
     *
     * @param pow the power
     * @return 10^power
     */
    private static int tenPower(int pow) {
        int ans = 10;
        if (pow > 0) {
            while (pow-- > 1) {
                ans *= 10;
            }
            return ans;
        } else {
            return 1;
        }
    }

    /**
     * Private static helper method that a digit in a number
     * based on the specified position
     *
     * @param theInt the number
     * @param at     position
     * @return the digit at the position in the number
     */
    private static int getAt(int theInt, int at) {
        return ((Math.abs(theInt)) / tenPower(at - 1)) % 10;
    }

    /**
     * This is a private helper method that swap 2 values in
     * an array
     *
     * @param arr the array
     * @param x   the 1st index
     * @param y   the 2nd index
     * @param <T> data type of the array
     */
    private static <T> void swap(T[] arr, int x, int y) {
        T temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}