package de.comparus.opensource.longmap;

import org.junit.Before;

public class FunctionalTests {

    LongMap<Integer> longMap = new LongMapImpl<>();

    @Before
    public void setUp() {
        longMap.clear();
    }
}
