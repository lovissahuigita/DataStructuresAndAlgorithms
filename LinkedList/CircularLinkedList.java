/**
 * CircularLinkedList implementation
 * @author Lovissa Winyoto
 * @version 1.0
 */
public class CircularLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if ((index > size) || (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            LinkedListNode<T> current = head;
            while (index-- != 0) {
                current = current.getNext();
            }
            LinkedListNode<T> newNode = new LinkedListNode<T>(data);
            LinkedListNode<T> before = current.getPrevious();
            before.setNext(newNode);
            newNode.setPrevious(before);
            newNode.setNext(current);
            current.setPrevious(newNode);
            size++;
        }
    }

    @Override
    public T get(int index) {
        if ((index > size) || (index < 0) || (head == null)) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size) {
            return head.getPrevious().getData();
        } else {
            LinkedListNode<T> current = head;
            while (index-- != 0) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return this.removeFromFront();
        } else if (index == (size - 1)) {
            return this.removeFromBack();
        } else {
            LinkedListNode<T> current = head;
            while (index-- != 0) {
                current = current.getNext();
            }
            LinkedListNode<T> toRemove = current;
            LinkedListNode<T> before = current.getPrevious();
            LinkedListNode<T> after = current.getNext();
            before.setNext(after);
            after.setPrevious(before);
            toRemove.setNext(null);
            toRemove.setPrevious(null);
            size--;
            return toRemove.getData();
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (null == head) {
            newNode.setNext(newNode);
            newNode.setPrevious(newNode);
        } else {
            LinkedListNode<T> before = head.getPrevious();
            LinkedListNode<T> after = head;
            newNode.setNext(after);
            newNode.setPrevious(before);
            before.setNext(newNode);
            after.setPrevious(newNode);
        }
        head = newNode;
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (null == head) {
            newNode.setNext(newNode);
            newNode.setPrevious(newNode);
            head = newNode;
        } else {
            LinkedListNode<T> before = head.getPrevious();
            LinkedListNode<T> after = head;
            newNode.setNext(after);
            newNode.setPrevious(before);
            before.setNext(newNode);
            after.setPrevious(newNode);
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        if (head == null) {
            return null;
        } else if (size == 1) {
            LinkedListNode<T> toRemove = head;
            toRemove.setPrevious(null);
            toRemove.setNext(null);
            head = null;
            size--;
            return toRemove.getData();
        } else {
            LinkedListNode<T> toRemove = head;
            LinkedListNode<T> toBeHead = head.getNext();
            LinkedListNode<T> beforeHead = head.getPrevious();
            toRemove.setPrevious(null);
            toRemove.setNext(null);
            beforeHead.setNext(toBeHead);
            toBeHead.setPrevious(beforeHead);
            head = toBeHead;
            size--;
            return toRemove.getData();
        }
    }

    @Override
    public T removeFromBack() {
        if (head == null) {
            return null;
        } else if (size == 1) {
            LinkedListNode<T> toRemove = head;
            toRemove.setPrevious(null);
            toRemove.setNext(null);
            head = null;
            size--;
            return toRemove.getData();
        } else {
            LinkedListNode<T> toRemove = head.getPrevious();
            LinkedListNode<T> toBeLast = toRemove.getPrevious();
            toRemove.setPrevious(null);
            toRemove.setNext(null);
            toBeLast.setNext(head);
            head.setPrevious(toBeLast);
            size--;
            return toRemove.getData();
        }

    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        if (head == null) {
            return array;
        } else {
            LinkedListNode<T> current = head;
            for (int index = 0; index < this.size; index++) {
                array[index] = current.getData();
                current = current.getNext();
            }
//            int index = 0;
//            while (current.getNext() != null) {
//                array[index++] = current.getData();
//                current = current.getNext();
//            }
            return array;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        if (head != null) {
            head.setNext(null);
            head.setPrevious(null);
            head = null;
            size = 0;
        }
    }

    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }

}
