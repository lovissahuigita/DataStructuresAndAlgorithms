import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        table = new MapEntry[11];
        size = 0;
    }

    @Override
    public V add(K key, V value) {
        if ((key == null) || (value == null)) {
            throw new IllegalArgumentException("Illegal Argument Exception!");
        } else {
            double compare = (double) (size + 1) / (double) table.length;
            if (compare >= MAX_LOAD_FACTOR) {
                resizeArray();
            }
            int aSpot = Math.abs(key.hashCode()) % table.length;
            int qProbe = 0;
            boolean hasSpot = false;
            boolean hasPossibleSpot = false;
            int iteration = 0;
            int possibleSpot = -1;
            while (!hasSpot) {
                int thisSpot = (aSpot + (qProbe * qProbe)) % table.length;
                if (table[thisSpot] == null) {
                    aSpot = (hasPossibleSpot) ? possibleSpot : thisSpot;
                    hasSpot = true;
                } else {
                    if (iteration >= table.length) {
                        if (!hasPossibleSpot) {
                            resizeArray();
                            add(key, value);
                            hasSpot = true;
                        } else {
                            aSpot = possibleSpot;
                            hasSpot = true;
                        }
                    } else {
                        if (table[thisSpot].isRemoved()) {
                            possibleSpot = (possibleSpot == -1)
                                    ? thisSpot : possibleSpot;
                            hasPossibleSpot = true;
                        } else if (!table[thisSpot].isRemoved()) {
                            if (table[thisSpot].getKey().equals(key)) {
                                V toReturn = table[thisSpot].getValue();
                                table[thisSpot].setValue(value);
                                table[thisSpot].setRemoved(false);
                                return toReturn;
                            }
                        }
                    }
                    iteration++;
                }
                qProbe++;
            }
            table[aSpot] = new MapEntry<K, V>(key, value);
            table[aSpot].setRemoved(false);
            size++;
            return null;
        }
    }

    /**
     * This private method is called by the add method to resize the
     * array when the array has reached its load factor
     */
    private void resizeArray() {
        MapEntry<K, V>[] temp = table;
        table = new MapEntry[temp.length * 2 + 1];
        size = 0;
        for (int index = 0; index < temp.length; index++) {
            if ((temp[index] != null) && !(temp[index].isRemoved())) {
                add(temp[index].getKey(), temp[index].getValue());
            }
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        int index = findIndex(key);
        if (index == -1 || table[index] == null) {
            throw new NoSuchElementException("No Such Element");
        }
        table[index].setRemoved(true);
        size--;
        return table[index].getValue();

    }

    /**
     * Helper method to find the index of the key
     * @param key the key to be searched in the hashmap
     * @return the index where the key is placed in the hashmap
     */
    private int findIndex(K key) {
        int index = Math.abs(key.hashCode()) % table.length;
        int qProbe = 0;
        int iteration = 0;
        boolean isFound = false;
        while (!isFound && (iteration < table.length)) {
            int thisIndex = (index + qProbe * qProbe) % table.length;
            if (table[thisIndex] == null) {
                return thisIndex;
            } else if (table[thisIndex].getKey().equals(key)
                    && !table[thisIndex].isRemoved()) {
                return thisIndex;
            }
            iteration++;
            qProbe++;
        }
        return -1;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        int index = findIndex(key);
        if (index == -1 || table[index] == null) {
            throw new NoSuchElementException("No Such Element");
        }
        return table[index].getValue();
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        int index = findIndex(key);
        return !(table[index] == null || index == -1);
    }

    @Override
    public void clear() {
        table = new MapEntry[11];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> toReturn = new HashSet();
        for (int index = 0; index < table.length; index++) {
            if ((table[index] != null) && (!(table[index].isRemoved()))) {
                toReturn.add(table[index].getKey());
            }
        }
        return toReturn;
    }

    @Override
    public List<V> values() {
        ArrayList<V> toReturn = new ArrayList<V>();
        for (int index = 0; index < table.length; index++) {
            if ((table[index] != null) && (!(table[index].isRemoved()))) {
                toReturn.add(table[index].getValue());
            }
        }
        return toReturn;
    }

}
