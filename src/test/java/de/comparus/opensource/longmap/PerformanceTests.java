package de.comparus.opensource.longmap;


import org.junit.Before;
import org.junit.Test;

/**
 * Comparing performance of LongMapImpl and
 */
public class PerformanceTests {

    static LongMap<Integer> longMap = new LongMapImpl<>();

    @Before
    public void setUp() {
        longMap.clear();
    }

    /**
     * Test put(...) methods
     */
    @Test
    void testLongMapPut(){

    }

    @Test
    void testHashMapPut(){

    }
    /**
     * Test get(...) methods
     */
    @Test
    void testLongMapGet(){

    }

    @Test
    void testHashMapGet(){

    }
    /**
     * Test remove(...) methods
     */
    @Test
    void testLongMapRemove(){

    }

    @Test
    void testHashMapRemove(){

    }
    /**
     * Test containsKey(...) methods
     */
    @Test
    void testLongMapContainsKey(){

    }

    @Test
    void testHashMapContainsKey(){

    }
    /**
     * Test containsValue(...) methods
     */
    @Test
    void testLongMapContainsValue(){

    }

    @Test
    void testHashMapContainsValue(){

    }

}
