import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a square 2D array
 *
 */
public class SquareMatrix<T> implements Iterable<T> {
    public T[][] matrix;

    public SquareMatrix(int n){
        matrix = (T[][]) new Object[n][n];
    }

    /**
     * Gets an element from the matrix (indexed by 0)
     * at a given row and column
     * @param i column
     * @param j row
     * @return item at that index
     */
    public T get(int i, int j){
        return matrix[i][j];
    }

    /**
     * Sets an element in the matrix (indexed by 0)
     * at a given row and column
     * @param i
     * @param j
     * @param data
     */
    public void set(int i, int j, T data){
        matrix[i][j] = data;
    }

    /**
     * Returns an iterator that traverses the matrix row by row
     * @return
     */
    public Iterator<T> iterator(){
        return new SquareMatrixIterator();
    }

    private class SquareMatrixIterator implements Iterator<T> {

        //Do not add any instance variables
        private int row;
        private int column;

        @Override
        public boolean hasNext() {
            return ((row < matrix.length) && (column < (matrix[0].length)));
        }

        @Override
        public T next() {
            int tR = row;
            int tC = column;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (row == matrix.length - 1) {
                row = 0;
                column++;
            } else {
                row++;
            }
            return get(tR, tC);
        }
    }
}
