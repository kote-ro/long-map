package de.comparus.opensource.longmap;


import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Comparing performance of LongMapImpl and HashMap
 * TestMethodOrder is used for simplifying test results representation
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PerformanceTests {

    private static LongMap<Long> longMap = new LongMapImpl<>();
    private static Map<Long, Long> hashMap = new HashMap<>();
    private static List<Long> testData = new ArrayList<>();
    private static final long size = 1_000_000L;
    private static final Logger LOGGER = Logger.getLogger(PerformanceTests.class.getName());

    private static void generateTestData(){
        long leftLimit = 1L;
        long rightLimit = 1_000_000L;
        for (int i = 0; i < size; i++) {
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            assert testData != null;
            testData.add(generatedLong);
        }
    }

    @BeforeAll
    public static void setUp() {
        longMap.clear();
        generateTestData();
    }

    /**
     * Test put(...) methods
     */
    @Test
    @Order(1)
    void testLongMapPut(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            longMap.put(data, data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("LongMap put(...) time: "+ (end-start));
    }

    @Test
    @Order(2)
    void testHashMapPut(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            hashMap.put(data, data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("HashMap put(...) time: "+ (end-start));
    }
    /**
     * Test get(...) methods
     */
    @Test
    @Order(3)
    void testLongMapGet(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            longMap.get(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("LongMap get(...) time: "+ (end-start));
    }

    @Test
    @Order(4)
    void testHashMapGet(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            hashMap.get(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("HashMap get(...) time: "+ (end-start));
    }
    /**
     * Test remove(...) methods
     */
    @Test
    @Order(5)
    void testLongMapRemove(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            longMap.remove(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("LongMap remove(...) time: "+ (end-start));
    }

    @Test
    @Order(6)
    void testHashMapRemove(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            hashMap.remove(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("HashMap remove(...) time: "+ (end-start));
    }
    /**
     * Test containsKey(...) methods
     */
    @Test
    @Order(7)
    void testLongMapContainsKey(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            longMap.containsKey(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("LongMap containsKey(...) time: "+ (end-start));
    }

    @Test
    @Order(8)
    void testHashMapContainsKey(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            hashMap.containsKey(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("HashMap containsKey(...) time: "+ (end-start));
    }
    /**
     * Test containsValue(...) methods
     */
    @Test
    @Order(9)
    void testLongMapContainsValue(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            longMap.containsValue(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("LongMap containsValue(...) time: "+ (end-start));
    }

    @Test
    @Order(10)
    void testHashMapContainsValue(){
        long start = System.currentTimeMillis();
        for (Long data : testData) {
            hashMap.containsValue(data);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("HashMap containsValue(...) time: "+ (end-start));
    }

}
