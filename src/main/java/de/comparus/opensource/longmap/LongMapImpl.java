package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * There is no need to use long in custom implementation, because we use array
     * as an inner data structure, so it`s length restricted with max integer value
     */
    private int size;
    private final float loadFactor;
    /**
     * The table is resized when its size exceeds this threshold.
     */
    private int threshold;
    private Node<V>[] nodes;
    /**
     * The type of values that are in the map
     */
    private Class<?> type;

    /**
     * I wanted to use optional parameter, so I chose vararg
     */
    public LongMapImpl(V... type) {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, type);
    }

    public LongMapImpl(Integer initialCapacity, V... type) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, type);
    }

    public LongMapImpl(Integer initialCapacity, Float loadFactor, V... type) {
        if (initialCapacity == null || initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor == null || loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        initialCapacity = Math.min(initialCapacity, MAXIMUM_CAPACITY);
        this.nodes = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.type = type.getClass().getComponentType();
        this.threshold = (int) (this.loadFactor * initialCapacity);
    }

    public LongMapImpl(LongMap<V> map, V... type) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        int initialCapacity = map != null ? Math.max((int) map.size(), DEFAULT_INITIAL_CAPACITY) : DEFAULT_INITIAL_CAPACITY;
        this.nodes = new Node[initialCapacity];
        this.type = type.getClass().getComponentType();
        this.threshold = (int) (this.loadFactor * initialCapacity);
        if (map != null && map.size() > 0) {
            for (long key : map.keys()) {
                this.put(key, map.get(key));
            }
        }
    }

    static class Node<V> {
        private final long key;
        private V value;
        private Node<V> nextNode;
        public Node(long key, V value) {
            this.key = key;
            this.value = value;
        }

        public long getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<V> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<V> nextNode) {
            this.nextNode = nextNode;
        }
        @Override
        public final String toString() {
            return key + "=" + value;
        }
    }
    public V put(long key, V value) {
        int hash = getIndex(key);
        Node<V> node = new Node<>(key, value);
        if(nodes[hash] == null){
            nodes[hash] = node;
            size++;
            resize();
        }else {
            Node<V> previousNode = null;
            Node<V> currentNode = nodes[hash];
            while(currentNode != null){
                if(currentNode.getKey() == key){
                    currentNode.setValue(value);
                    size++;
                    resize();
                    return value;
                }
                previousNode = currentNode;
                currentNode = currentNode.getNextNode();
            }
            if(previousNode != null) {
                previousNode.setNextNode(node);
            }
        }
        return value;
    }

    public V get(long key) {
        int hash = getIndex(key);
        if (nodes[hash] != null) {
            Node<V> temp = nodes[hash];
            while (temp != null) {
                if (temp.key == key)
                    return temp.value;
                temp = temp.getNextNode();
            }
        }
        return null;
    }

    public V remove(long key) {
        int index = getIndex(key);
        Node<V> entry = nodes[index];
        if(entry == null) {
            return null;
        }
        if(entry.getNextNode() == null) {
            V tempValue = entry.getValue();
            nodes[index] = null;
            size--;
            return tempValue;
        }
        Node<V> previousEntry = null;
        while (entry != null) {
            if (entry.getKey() == key) {
                V returnedValue = entry.getValue();
                if (previousEntry != null) {
                    previousEntry.setNextNode(entry.getNextNode());
                } else {
                    nodes[index] = entry.getNextNode();
                }
                entry.setNextNode(null);
                size--;
                return returnedValue;
            }
            previousEntry = entry;
            entry = entry.getNextNode();
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        if(isEmpty()){
            return false;
        }

        return get(key) != null;
    }

    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        return Arrays.stream(nodes).anyMatch(node -> isContainsValue(node, value));
    }

    private boolean isContainsValue(Node<V> entry, V value) {
        if (entry == null) {
            return false;
        }
        if (entry.getNextNode() == null) {
            return entry.getValue().equals(value);
        } else {
            while (entry != null) {
                if (entry.getValue().equals(value)) {
                    return true;
                }
                entry = entry.getNextNode();
            }
        }
        return false;
    }

    public long[] keys() {
        long[] keys = new long[size];

        for (int i = size; i-- > 0;) {
            for (Node<V> e = nodes[i]; e != null ; e = e.getNextNode()) {
                keys[i] = e.getKey();
            }
        }

        return keys;
    }

    public V[] values() {
        V[] values = (V[]) Array.newInstance(type, size);

        for (int i = size; i-- > 0;) {
            for (Node<V> e = nodes[i]; e != null ; e = e.getNextNode()) {
                values[i] = e.getValue();
            }
        }

        return values;
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        threshold = 0;
        nodes = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    private int getIndex(long key) {
        return Math.abs((int) key % nodes.length);
    }

    private void resize(){
        if (size < MAXIMUM_CAPACITY && size > threshold) {
            int updatedSize = nodes.length * 2;
            nodes = Arrays.copyOf(nodes, Math.min(updatedSize, MAXIMUM_CAPACITY));
            threshold = (int) (nodes.length * loadFactor);
        }
    }
}
