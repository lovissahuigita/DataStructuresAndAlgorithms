/**
  * Class containing various text processing helper functions
  *
  * @author Kush
  */
public class TextProcessing {
	//private static final int BASE = 742617000027;
	private static final int BASE = 1332;

	/**
	  * The function takes in a string and generates a last function table
	  * for the given string. The string will never be null
	  * 
	  * For each possible character, the table should contain the last
	  * index of that character in the string or -1
	  *
	  * @param the string to generate the table for
	  * @return the table for the given string
	  * @throws IllegalArgumentException if needle is empty
	  */
	public static int[] buildLastTable(String needle) {
		int[] table = new int[Character.MAX_VALUE+1];
		if (needle != "") {
			for (int index = 0; index < table.length; index++) {
				table[index] = -1;
			} //TODO ask: more efficient way?
			for (int index = 0; index < needle.length(); index++) {
				table[needle.charAt(index)] = index;
			}
		} else {
			throw new IllegalArgumentException("Illegal Argument");
		}
		return table;
	}

	/**
	  * This function is a helper function for the Rabin Karp algorithm
	  *
	  * It takes in the old hash value of some given string and returns the new
	  * has based on the parameters
	  * The hash was originally calculated as follows:
	  *
	  * hash(maiden) = m*BASE^5 + a*BASE^4 + i*BASE^3 + d*BASE^2 + e*BASE^1 + n*BASE^0
	  *
	  * DO NOT USE Math.pow 
	  * This function must run in O(1) time 
	  */
	public static int updateHash(int oldHash, char oldChar, char newChar, int length){
		return ((oldHash * BASE) + newChar) -(exp(BASE, length) * oldChar);
	}

	/**
	 * This function raise a number to the power of another number
	 * @param a the number
	 * @param b the power
	 * @return the result
	 */
	private static int exp(int a, int b) {
		int c = a;
		while (b > 1) {
			c *= a;
			b--;
		}
		return c;
	}
}