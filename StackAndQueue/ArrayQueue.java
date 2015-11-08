import java.util.NoSuchElementException;

/**
 * ArrayQueue
 * Implementation of a queue using
 * an array as the backing structure
 *
 * @author Lovissa Winyoto
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueADT<T> {

    // Do not add instance variables
    private T[] backing;
    private int size;
    private int front;
    private int back;

    /**
     * Construct an ArrayQueue with an
     * initial capacity of INITIAL_CAPACITY
     *
     * Use Constructor Chaining
     */
    public ArrayQueue() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct an ArrayQueue with the specified
     * initial capacity of initialCapacity
     * @param initialCapacity Initial capacity of the backing array.
     */
    public ArrayQueue(int initialCapacity) {
        backing = (T[]) new Object[initialCapacity];
        size = 0;
        front = 0;
        back = 0;
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal argument!");
        }
        if (size == 0) {
            front = 0;
            back = 0;
            backing[back] = data;
        } else if (size >= backing.length) {
            T[] backingCopy = (T[]) new Object[size * 2];
            for (int index = front, indexCopy = 0;
                 index < backing.length; index++, indexCopy++) {
                backingCopy[indexCopy] = backing[index];
            }
            for (int index = 0, indexCopy = (size - front);
                 index < front; index++, indexCopy++) {
                backingCopy[indexCopy] = backing[index];
            }
            front = 0;
            back = size - 1;
            backing = backingCopy;
            backing[++back] = data;
        } else if (back == backing.length - 1 && front != 0) {
            back = 0;
            backing[back] = data;
        } else {
            backing[++back] = data;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("No such element!");
        }
        T toPop = backing[front];
        backing[front++] = null;
        front = (front == backing.length) ? 0 : front;
        size--;
        return toPop;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (front == back && size == 0);
    }

    /**
     * Returns the backing array for your queue.
     * This is for grading purposes only. DO NOT EDIT THIS METHOD.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backing;
    }
}
