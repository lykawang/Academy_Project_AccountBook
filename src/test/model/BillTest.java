package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillTest {
    private Bill testBill;

    @BeforeEach
    void runBefore() {
        testBill = new Bill(200.89, 2021, 10, 12, 2, "Bill1");
    }

    @Test
    void testConstructor() {
        assertEquals("Bill1", testBill.getDescription());
        assertEquals(200.89, testBill.getAmount());
        assertEquals(2021, testBill.getYear());
        assertEquals(10, testBill.getMonth());
        assertEquals(12, testBill.getDay());
        assertEquals(2, testBill.getCategoryNum());
    }

    @Test
    void testGetAmount() {
        assertEquals(200.89, testBill.getAmount());
    }

    @Test
    void testGetCategoryNum() {
        assertEquals(2,testBill.getCategoryNum());
    }

    @Test
    void testGetYear() {
        assertEquals(2021,testBill.getYear());
    }

    @Test
    void testGetMonth() {
        assertEquals(10,testBill.getMonth());
    }

    @Test
    void testGetDay() {
        assertEquals(12,testBill.getDay());
    }

    @Test
    void testGetDescription() {
        assertEquals("Bill1",testBill.getDescription());
    }

    @Test
    void testToString() {
        assertEquals("Bill {description='Bill1', amount=200.89, categoryType=2, date=2021-10-12}",
                testBill.toString());
    }

}
