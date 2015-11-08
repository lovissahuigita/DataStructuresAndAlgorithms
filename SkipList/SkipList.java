import java.util.HashSet;
import java.util.Set;
import java.util.NoSuchElementException;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    // Do not add any additional instance variables
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order.
     * When an item is inserted, the flipper is called until it returns a tails.
     * If, for an item, the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        head = new Node<T>(null, 1);
        size = 0;
        this.coinFlipper = coinFlipper;
    }

    @Override
    public void put(T data) {
        checkIllegalArgument(data);
        put(find(head, data), data);
    }

    /**
     * A recursive helper method that traverse the skip list
     * to find the appropriate spot to put the data
     * @param thisNode the node that is currently looked at
     * @param data the data to be put
     */
    private void put(Node<T> thisNode, T data) {
        if (thisNode.getNext() != null) {
            //if thisNode is not the last node in the skiplist
            if (thisNode.getNext().getData().compareTo(data) == 0) {
                thisNode = thisNode.getNext();
                do {
                    thisNode.setData(data);
                    thisNode = thisNode.getUp();
                } while (thisNode != null);
            } else {
                place(thisNode, data);
                size++;
            }
        } else {
            place(thisNode, data);
            size++;
        }
    }

    /**
     * A recursive private helper method that finds the
     * appropriate node where the data is found or the
     * data should be put at
     * @param current the current node that is looked at
     * @param data the data that is being compared
     * @return the node where the data is found or
     * the data should be put at
     */
    private Node<T> find(Node<T> current, T data) {
        if (current.getNext() != null) {
            if (current.getNext().getData().compareTo(data) < 0) {
                return find(current.getNext(), data);
            } else {
                return down(current, data);
            }
        } else {
            return down(current, data);
        }
    }

    /**
     * A recursive private helper method of find(current, data)
     * that traverse the skip list downward when necessary
     * @param current the node needed to be traversed
     * @param data the data to be compared
     * @return the node to be found
     */
    private Node<T> down(Node<T> current, T data) {
        return (current.getDown() != null)
                ? find(current.getDown(), data) : current;
    }

    /**
     * A private helper method that actually puts
     * the data in the appropriate spot in the
     * skip list
     * @param current reference to the node before
     *                the appropriate space
     * @param data the data to be compared
     */
    private void place(Node<T> current, T data) {
        Node<T> newNode = new Node<T>(data, current.getLevel());
        newNode.setNext(current.getNext());
        if (newNode.getNext() != null) {
            newNode.getNext().setPrev(newNode);
        }
        current.setNext(newNode);
        newNode.setPrev(current);

        buildUp(newNode);
    }

    /**
     * A private helper method that randomly builds
     * a node in an upward direction
     * @param current the node to be built up
     */
    private void buildUp(Node<T> current) {
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            // make a new node on top of the current node
            current.setUp(new Node<T>(current.getData(),
                    current.getLevel() + 1));
            current.getUp().setDown(current);
            checkNeighbor(current.getUp());
            current = current.getUp();
        }
    }

    /**
     * A private helper method that checks where a newly
     * built up node should link its previous and next
     * pointer. If necessary, update the head of the skip
     * list
     * @param current the node that is looking for its
     *                neighbors
     */
    private void checkNeighbor(Node<T> current) {
        Node<T> pointer = current.getDown();
        boolean foundPrev = false;
        while (pointer.getPrev() != null && !foundPrev) {
            if (pointer.getPrev().getUp() != null) {
                current.setPrev(pointer.getPrev().getUp());
                current.setNext(pointer.getPrev().getUp().getNext());
                pointer.getPrev().getUp().setNext(current);
                if (current.getNext() != null) {
                    current.getNext().setPrev(current);
                }
                foundPrev = true;
            } else {
                pointer = pointer.getPrev();
            }
        }
        if (pointer == head) {
            pointer.setUp(new Node<T>(null, pointer.getLevel() + 1));
            pointer.getUp().setDown(pointer);
            pointer.getUp().setNext(current);
            current.setPrev(pointer.getUp());
            head = pointer.getUp();
        }
    }

    @Override
    public T first() {
        if (isEmpty()) {
            nsee();
        }
        return first(head).getData();
    }

    /**
     * A private recursive helper method that finds the
     * first data in the skip list
     * @param current the node that is being looked at
     * @return the first node in the skip list
     */
    private Node<T> first(Node<T> current) {
        if (current.getDown() != null) {
            return first(current.getDown());
        } else {
            return current.getNext();
        }
    }

    @Override
    public T last() {
        if (isEmpty()) {
            nsee();
        }
        return last(head).getData();
    }

    /**
     * A private recursive helper method that finds the
     * last data in the skip list
     * @param current the node that is being looked at
     * @return the last node in the skip list
     */
    private Node<T> last(Node<T> current) {
        if (current.getNext() != null) {
            return last(current.getNext());
        } else {
            if (current.getDown() != null) {
                return last(current.getDown());
            } else {
                return current;
            }
        }
    }

    /**
     * This method checks whether the data is null and throw "Illegal
     * Argument Exception" if the data is null
     *
     * @param data the data to be checks
     */
    private void checkIllegalArgument(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument Exception");
        }
    }

    /**
     * This method throws a no such element exception
     * when it is called
     */
    private void nsee() {
        throw new NoSuchElementException("No Such Element!");
    }

    /**
     * This method checks whether the skip list is empty or not
     * @return true if the list is empty, false otherwise
     */
    private boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public T remove(T data) {
        checkIllegalArgument(data);
        if (isEmpty()) {
            nsee();
        }
        Node<T> current = find(head, data).getNext();
        if (current.getData().compareTo(data) == 0) {
            while (current.getUp() != null) {
                current = current.getUp();
            }
            remove(current);
            size--;
        } else {
            nsee();
        }
        return current.getData();
    }

    /**
     * A private recursive method that removes a data
     * from a skip list
     * @param current the current node to be looked at
     */
    private void remove(Node<T> current) {
        if (current.getPrev().getData() == null) {
            if (current.getNext() == null) {
                if (current.getPrev().getDown() != null) {
                    head = current.getPrev().getDown();
                    head.setUp(null);
                } else {
                    // remove a size 1 skiplist
                    head.setNext(null);
                }
            } else {
                current.getNext().setPrev(current.getPrev());
                current.getPrev().setNext(current.getNext());
            }
        } else {
            current.getPrev().setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrev(current.getPrev());
            }
        }
        if (current.getDown() != null) {
            remove(current.getDown());
        }
    }

    @Override
    public boolean contains(T data) {
        checkIllegalArgument(data);
        if (isEmpty()) {
            return false;
        } else {
            Node<T> toReturn = find(head, data);
            if (toReturn.getNext() == null) {
                return false;
            } else {
                return (toReturn.getNext().getData().compareTo(data) == 0);
            }
        }
    }

    @Override
    public T get(T data) {
        checkIllegalArgument(data);
        if (isEmpty()) {
            nsee();
        }
        Node<T> toReturn = find(head, data);
        if (toReturn.getNext() == null) {
            nsee();
        } else {
            if (toReturn.getNext().getData().compareTo(data) != 0) {
                nsee();
            }
        }
        return toReturn.getNext().getData();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = new Node<T>(null, 1);
        size = 0;
    }

    @Override
    public Set<T> dataSet() {
        HashSet<T> toReturn = new HashSet<T>();
        if (isEmpty()) {
            return toReturn;
        }
        Node<T> current = first(head);
        do {
            if (current.getData() != null) {
                toReturn.add(current.getData());
            }
            current = current.getNext();
        } while (current != null);
        return toReturn;
    }

    @Override
    public Node<T> getHead() {
        return head;
    }
}
