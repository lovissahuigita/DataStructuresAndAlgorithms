import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a class that will allow you to iterate through the first n
 * Fibonacci elements
 * @author kushagramansingh, Lovissa Winyoto
 *
 */
public class FibonacciIterator implements Iterator<Integer> {

	//Do not add any instance variables, you may not need to use all of them though
	private Integer n;
	private Integer current;
	private Integer runningValue = 1;
	private Integer previousValue = 0;
	
	public FibonacciIterator(Integer n) {
		this.n = n;
		current = 0;
	}
	
	@Override
	public boolean hasNext() {
		return (current < n);
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Integer save = runningValue;
		runningValue += previousValue;
		Integer toReturn = previousValue;
		previousValue = save;
		current++;
		return toReturn;
	}
}
