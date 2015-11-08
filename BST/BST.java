import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * The Class of Binary Search Tree
 *
 * @author Lovissa Winyoto (lwinyoto3)
 * @param <T> generic type of the data in the binary search tree
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {

    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the BST with the data in the collection. The data in the BST
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else {
            data.forEach(this::add);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else if (root == null) {
            size++;
            root = new BSTNode<T>(data);
        } else {
            add(root, data);
        }
    }

    /**
     * Recursive helper method for the add(T data) method with a running time
     * of O(log n) or worst case of O(n)
     * Duplicate data will be ignored
     * @param current a non-null BSTNode
     * @param data the data to be added in the BST
     */
    private void add(BSTNode<T> current, T data) {
        if (current.getData().compareTo(data) < 0) {
            if (current.getRight() == null) {
                current.setRight(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getRight(), data);
            }
        } else if (current.getData().compareTo(data) > 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                add(current.getLeft(), data);
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else if (root == null) {
            throw new NoSuchElementException("No Such Element!");
        } else {
            BSTNode<T> dummy = new BSTNode<T>(root.getData());
            root = remove(root, dummy, data);
            size--;
            return dummy.getData();
        }
    }

    /**
     * Recursive helper method for the remove(T data) method with a running time
     * of O(log n) or worst case of O(n)
     * @param current the current BSTNode
     * @param dummy a node to keep track of the removed node
     * @param data the data to be removed
     * @return the data removed
     */
    private BSTNode<T> remove(BSTNode<T> current, BSTNode<T> dummy, T data) {
        if (current == null) {
            throw new NoSuchElementException("No Such Element!");
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(remove(current.getLeft(), dummy, data));
            return current;
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(remove(current.getRight(), dummy, data));
            return current;
        } else {
            dummy.setData(current.getData());
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() == null
                    && current.getRight() != null) {
                return current.getRight();
            } else if (current.getLeft() != null
                    && current.getRight() == null) {
                return current.getLeft();
            } else {
                T successor = successor(current);
                T dataRemove = current.getData();
                current.setData(successor);
                return current;
            }
        }
    }

    /**
     * Recursive helper method for the remove method to find the successor
     * of the data to be removed if it had two children
     * @param toRemove node that is searching for its successor
     * @return the data of the successor
     */
    private T successor(BSTNode<T> toRemove) {
        BSTNode<T> nodeS = toRemove.getRight();
        BSTNode<T> beforeS = toRemove;
        while (nodeS.getLeft() != null) {
            beforeS = nodeS;
            nodeS = nodeS.getLeft();
        }
        T data = nodeS.getData();
        BSTNode<T> dummy2 = new BSTNode<T>(nodeS.getData());
        if (beforeS == root) {
            beforeS.setRight(nodeS.getRight());
        } else {
            beforeS.setLeft(remove(nodeS, dummy2, data));
        }
        return data;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else {
            if (root == null) {
                throw new NoSuchElementException("No Such Element!");
            } else {
                return get(root, data).getData();
            }
        }
    }

    /**
     * Recursive helper method for the get(T data) method with a running time
     * of O(log n) or worst case of O(n)
     * @param current the current BSTNode to be checked
     * @param data Data to search and to return for in the tree
     * @return the node containing the data
     */
    private BSTNode<T> get(BSTNode<T> current, T data) {
        if (current.getData().compareTo(data) == 0) {
            return current;
        } else {
            if (current.getData().compareTo(data) < 0) {
                if (current.getRight() == null) {
                    throw new NoSuchElementException("No Such Element!");
                } else {
                    return get(current.getRight(), data);
                }
            } else {
                if (current.getLeft() == null) {
                    throw new NoSuchElementException("No Such Element!");
                } else {
                    return get(current.getLeft(), data);
                }
            }
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument Exception!");
        }
        return ((root == null) ? false : contains(root, data));
    }

    /**
     * Recursive helper method for the contains(T data) method with a
     * running time of O(log n) or worst case of O(n)
     * @param current a non-null BSTNode
     * @param data the data that is compared to check whether it is in
     *             the BST
     * @return whether the left or right child of current BSTNode
     *     contains the data
     */
    private boolean contains(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        } else if (current.getData().compareTo(data) == 0) {
            return true;
        } else {
            return (((current.getData().compareTo(data) < 0))
                    ? contains(current.getRight(), data)
                    : contains(current.getLeft(), data));
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        //preorder: data, left, right
        List<T> toReturn = new ArrayList<T>();
        return preorder(toReturn, root);
    }

    /**
     * Recursive helper method for the preorder() method with a running time of
     * O(n)
     * @param current the list that accumulates the BST in preorder manner
     * @param currentNode the current node that is being checked and added
     * @return the updated current list (added the data from currentNode)
     */
    private List<T> preorder(List<T> current, BSTNode<T> currentNode) {
        if (currentNode != null) {
            current.add(currentNode.getData());
            preorder(current, currentNode.getLeft());
            preorder(current, currentNode.getRight());
        }
        return current;
    }

    @Override
    public List<T> postorder() {
        //postorder: left, right, data
        List<T> toReturn = new ArrayList<T>();
        return postorder(toReturn, root);
    }

    /**
     * Recursive helper method for the postorder() method with a running
     * time of O(n)
     * @param current the list that accumulates the BST in postorder manner
     * @param currentNode the current node that is being checked and added
     * @return the updated current list (added the data from currentNode)
     */
    private List<T> postorder(List<T> current, BSTNode<T> currentNode) {
        if (currentNode != null) {
            postorder(current, currentNode.getLeft());
            postorder(current, currentNode.getRight());
            current.add(currentNode.getData());
        }
        return current;
    }

    @Override
    public List<T> inorder() {
        //inorder: left, data, right
        List<T> toReturn = new ArrayList<T>();
        return inorder(toReturn, root);
    }

    /**
     *  Recursive helper method for the inorder() method with a running time of
     * O(n)
     * @param current the list that accumulates the BST in inorder manner
     * @param currentNode the current node that is being checked and added
     * @return the updated current list (added the data from currentNode)
     */
    private List<T> inorder(List<T> current, BSTNode<T> currentNode) {
        if (currentNode != null) {
            inorder(current, currentNode.getLeft());
            current.add(currentNode.getData());
            inorder(current, currentNode.getRight());
        }
        return current;
    }

    @Override
    public List<T> levelorder() {
        List<T> toReturn = new ArrayList<T>(size);
        if (root == null) {
            return toReturn;
        } else {
            LinkedList<BSTNode<T>> aQueue = new LinkedList<BSTNode<T>>();
            aQueue.addLast(root);
            toReturn = levelorder(toReturn, root, aQueue);
            return toReturn;
        }
    }

    /**
     * Recursive helper method for the levelorder() method with a running
     * time of O(n)
     * @param current the list that accumulates the BST in levelorder manner
     * @param currentNode the current node that is being checked and added
     * @param aQueue a queue to keep track of the level order
     * @return the updated current list (added the data from currentNode)
     */
    private List<T> levelorder(List<T> current, BSTNode<T> currentNode,
                               LinkedList<BSTNode<T>> aQueue) {
        if (aQueue.peek() != null) {
            BSTNode<T> x = aQueue.pop();
            current.add(x.getData());
            if (x.getLeft() != null) {
                aQueue.addLast(currentNode.getLeft());
            }
            if (x.getRight() != null) {
                aQueue.addLast(currentNode.getRight());
            }
            current = levelorder(current, aQueue.peek(), aQueue);
        }
        return current;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int  height() {
        if (root == null) {
            return -1;
        } else {
            return height(root, 0) - 1;
        }
    }

    /**
     * Recursive helper method of height() method to count the height of
     * the sub-BST with a running time of O(n)
     * @param current the root of the subtree
     * @param counter the accumulator number that represents the height
     * @return the updated counter
     */
    private int height(BSTNode<T> current, int counter) {
        int left;
        int right;
        if (current.getLeft() == null) {
            left = 0;
        } else {
            left = height(current.getLeft(), counter + 1);
        }
        if (current.getRight() == null) {
            right = 0;
        } else {
            right = height(current.getRight(), counter + 1);
        }
        return (Math.max(left, right) + 1);
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else if (root == null) {
            return -1;
        } else {
            return depth(root, data, 1);
        }
    }

    /**
     * Recursive helper method of height() method to count the depth of the
     * sub-BST with a running time of O(n)
     * @param current the root of the subtree
     * @param data the data that tells the recursion to stop when it is found
     * @param count the accumulator number that represents the depth
     * @return the updated count
     */
    private int depth(BSTNode<T> current, T data, int count) {
        if (current.getData().compareTo(data) == 0) {
            return count;
        } else {
            if (current.getData().compareTo(data) > 0) {
                return (current.getLeft() == null) ? -1
                        : depth(current.getLeft(), data, ++count);
            } else {
                return (current.getRight() == null) ? -1
                        : depth(current.getRight(), data, ++count);
            }
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}