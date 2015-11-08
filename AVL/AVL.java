import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Creates an AVL Tree
 *
 * @author Lovissa Winyoto
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the AVL with the data in the collection. The data
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        checkIAE(data);
        data.forEach(this::add);
    }

    @Override
    public void add(T data) {
        checkIAE(data);
        root = add(root, data);
    }

    /**
     * Private helper method that recursively adds a data
     * in the AVL tree
     * @param current the current node to be checked
     * @param data the data to be added
     * @return the node returned
     */
    private AVLNode<T> add(AVLNode<T> current, T data) {
        if (current == null) {
            size++;
            return new AVLNode<T>(data);
        } else {
            if (current.getData().compareTo(data) < 0) {
                current.setRight(add(current.getRight(), data));
            } else if (current.getData().compareTo(data) > 0) {
                current.setLeft(add(current.getLeft(), data));
            }
            update(current.getRight());
            update(current.getLeft());
            return checkAVL(update(current));
        }
    }

    /**
     * This is a private helper method that updates the
     * height and balance factor in a node
     * @param current the node to be updated
     * @return the node to be updated with the
     * updated height and balance factor
     */
    private AVLNode<T> update(AVLNode<T> current) {
        if (current != null) {
            current.setHeight(height(current));
            current.setBalanceFactor(calculateBF(current));
        }
        return current;
    }

    /**
     * This is a private helper method that checks if the
     * AVL properties of the tree
     * @param current the tree to be checked
     * @return the tree that follows the AVL properties
     */
    private AVLNode<T> checkAVL(AVLNode<T> current) {
        if (Math.abs(current.getBalanceFactor()) > 1) {
            if (current.getBalanceFactor() > 1) {
                if (current.getLeft().getBalanceFactor() >= 0) {
                    current = update(rotateLeftLeft(current));
                } else {
                    current = update(rotateLeftRight(current));
                }
            } else {
                if (current.getRight().getBalanceFactor() <= 0) {
                    current = update(rotateRightRight(current));
                } else {
                    current = update(rotateRightLeft(current));
                }
            }
        }
        update(current.getLeft());
        update(current.getRight());
        return update(current);
    }

    /**
     * This is a private helper method that rotate the
     * current node using a left-left case
     * @param current the node to be rotated
     * @return rotated nodes
     */
    private AVLNode<T> rotateLeftLeft(AVLNode<T> current) {
        AVLNode<T> max = current;
        AVLNode<T> mid = current.getLeft();
        AVLNode<T> maxLeft = mid.getRight();
        max.setLeft(maxLeft);
        update(max);
        mid.setRight(max);
        update(current);
        return update(mid);
    }

    /**
     * This is a private helper method that rotate the
     * current node using a right-right case
     * @param current the node to be rotated
     * @return rotated nodes
     */
    private AVLNode<T> rotateRightRight(AVLNode<T> current) {
        AVLNode<T> min = current;
        AVLNode<T> mid = current.getRight();
        AVLNode<T> minRight = mid.getLeft();
        min.setRight(minRight);
        update(min);
        mid.setLeft(min);
        update(current);
        return update(mid);
    }

    /**
     * This is a private helper method that rotate the
     * current node using a left-right case
     * @param current the node to be rotated
     * @return rotated nodes
     */
    private AVLNode<T> rotateLeftRight(AVLNode<T> current) {
        AVLNode<T> mid = current.getLeft().getRight();
        AVLNode<T> min = current.getLeft();
        AVLNode<T> midL = mid.getLeft();

        current.setLeft(mid);
        mid.setLeft(min);
        min.setRight(midL);
        return rotateLeftLeft(current);
    }

    /**
     * This is a private helper method that rotate the
     * current node using a right-left case
     * @param current the node to be rotated
     * @return rotated nodes
     */
    private AVLNode<T> rotateRightLeft(AVLNode<T> current) {
        AVLNode<T> mid = current.getRight().getLeft();
        AVLNode<T> max = current.getRight();
        AVLNode<T> midR = mid.getRight();

        current.setRight(mid);
        mid.setRight(max);
        max.setLeft(midR);
        return rotateRightRight(current);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        } else if (root == null) {
            throw new NoSuchElementException("No Such Element!");
        } else {
            AVLNode<T> dummy = new AVLNode<T>(root.getData());
            root = (remove(root, dummy, data));
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
    private AVLNode<T> remove(AVLNode<T> current, AVLNode<T> dummy, T data) {
        if (current == null) {
            throw new NoSuchElementException("No Such Element!");
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(update(remove(current.getLeft(), dummy, data)));
            return update(checkAVL(update(current)));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(update(remove(current.getRight(), dummy, data)));
            return update(checkAVL(update(current)));
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
                T predecessor = predecessor(current);
                current.setData(predecessor);
                update(current.getRight());
                update(current.getLeft());
                return update(checkAVL(current));
            }
        }
    }

    /**
     * Recursive helper method for the remove method to find the successor
     * of the data to be removed if it had two children
     * @param toRemove node that is searching for its successor
     * @return the data of the successor
     */
    private T successor(AVLNode<T> toRemove) {
        AVLNode<T> nodeS = toRemove.getRight();
        AVLNode<T> beforeS = toRemove;
        while (nodeS.getLeft() != null) {
            beforeS = nodeS;
            nodeS = nodeS.getLeft();
        }
        T data = nodeS.getData();
        AVLNode<T> dummy2 = new AVLNode<T>(nodeS.getData());
        if (beforeS == root) {
            beforeS.setRight(update(nodeS.getRight()));
        } else {
            beforeS.setLeft(update(remove(nodeS, dummy2, data)));
        }
        return data;
    }

    /**
     * Recursive helper method for the remove method to find the predecessor
     * of the data to be removed if it had two children
     * @param toRemove node that is searching for its predecessor
     * @return the data of the predecessor
     */
    private T predecessor(AVLNode<T> toRemove) {
        AVLNode<T> nodeP = toRemove.getLeft();
        AVLNode<T> beforeP = toRemove;
        while (nodeP.getRight() != null) {
            beforeP = nodeP;
            nodeP = nodeP.getRight();
        }
        T data = nodeP.getData();
        AVLNode<T> dummy2 = new AVLNode<T>(nodeP.getData());
        if (beforeP == root) {
            beforeP.setLeft(nodeP.getLeft());
        } else {
            remove(data);
            size++;
        }
        return data;
    }

    @Override
    public T get(T data) {
        checkIAE(data);
        if (get(root, data) == null) {
            nsee();
        }
        return get(root, data).getData();
    }

    /**
     * This is a private helper method that gets the data
     * @param current the node to be checked
     * @param data the data to be checked
     * @return the node containing the data
     */
    private AVLNode<T> get(AVLNode<T> current, T data) {
        if (current == null) {
            return current;
        } else {
            if (current.getData().compareTo(data) != 0) {
                if (current.getData().compareTo(data) < 0) {
                    return get(current.getRight(), data);
                } else {
                    return get(current.getLeft(), data);
                }
            } else {
                return current;
            }
        }
    }

    @Override
    public boolean contains(T data) {
        checkIAE(data);
        return ((get(root, data) == null)
                ? false : get(root, data).getData().compareTo(data) == 0);
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
    private List<T> preorder(List<T> current, AVLNode<T> currentNode) {
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
    private List<T> postorder(List<T> current, AVLNode<T> currentNode) {
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
    private List<T> inorder(List<T> current, AVLNode<T> currentNode) {
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
            LinkedList<AVLNode<T>> aQueue = new LinkedList<AVLNode<T>>();
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
    private List<T> levelorder(List<T> current, AVLNode<T> currentNode,
                               LinkedList<AVLNode<T>> aQueue) {
        if (aQueue.peek() != null) {
            AVLNode<T> x = aQueue.pop();
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
    public int height() {
        return height(root);
    }

    /**
     * A private helper method that finds the height of a node
     * @param current node to be checked
     * @return the height of the node
     */
    private int height(AVLNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            if ((current.getLeft() == null) && (current.getRight() == null)) {
                return 0;
            } else {
                return Math.max(height(update(current.getLeft()))
                        , height(update(current.getRight()))) + 1;
            }
        }
    }

    /**
     * A private helper method that calculate the balance factor
     * of a node
     * @param current the node to be calculated
     * @return the balance factor
     */
    private int calculateBF(AVLNode<T> current) {
        if (current == null) {
            return 0;
        } else {
            if ((current.getLeft() == null) && (current.getRight() == null)) {
                return 0;
            } else if ((current.getLeft() == null)
                    && (current.getRight() != null)) {
                return -1 - height(update(current.getRight()));
            } else if ((current.getLeft() != null)
                    && (current.getRight() == null)) {
                return height(update(current.getLeft())) + 1;
            } else {

                return height(current.getLeft()) - height(current.getRight());
            }
        }
    }

    @Override
    public int depth(T data) {
        checkIAE(data);
        return depth(root, data);
    }

    /**
     * A private helper method that finds the depth of a data in an AVL tree
     * @throws java.util.NoSuchElementException
     * @param current the node that is to be checked
     * @param data the data to be checked
     * @return the depth of the data in the AVL
     */
    private int depth(AVLNode<T> current, T data) {
        if (current != null) {
            if (current.getData().compareTo(data) == 0) {
                return 1;
            } else {
                if (current.getData().compareTo(data) < 0) {
                    return 1 + depth(current.getRight(), data);
                } else {
                    return 1 + depth(current.getLeft(), data);
                }
            }
        } else {
            nsee();
        }
        return 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }

    /**
     * A private helper method that throws no such element exception
     * @throws java.util.NoSuchElementException
     */
    private void nsee() {
        throw new NoSuchElementException("No Such Element!");
    }

    /**
     * A private helper method that checks if the data is null
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be checked
     */
    private void checkIAE(Object data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
    }

}