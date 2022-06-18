package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountBookTest {
    private AccountBook book;
    private Bill bill1;
    private Bill bill2;
    private Bill bill3;
    private Bill bill4;
    private Bill bill5;

    @BeforeEach
    void runBefore() {
        book = new AccountBook();
        bill1 = new Bill(98.22,2021,1,31,2,"BILL1");
        bill2 = new Bill(100.02,2021,8,28,2,"BILL2");
        bill3 = new Bill(400,2021,8,2,4,"BILL3");
        bill4 = new Bill(510.43,2021,7,2,4,"BILL4");
        bill5 = new Bill(210,2020,8,4,3,"BILL5");
    }

    @Test
    void testConstructor() {
        assertFalse(book.getAlertingStatus());
        assertEquals(0,book.getExpectedExpense());
        assertEquals(0,book.getCurrentYear());
        assertEquals(1,book.getCurrentMonth());
        assertEquals(0,book.getCurrentMonthTotalExpense());
    }

    @Test
    void testSize() {
        assertEquals(0,book.size());
        book.addBill(bill1);
        assertEquals(1,book.size());
    }

    @Test
    void testAddBill() {
        assertEquals(0,book.size());
        book.addBill(bill1);
        assertEquals(1,book.size());
        assertTrue(book.containBill("BILL1"));
        assertEquals(bill1.getYear(), book.getCurrentYear());
        assertEquals(bill1.getMonth(), book.getCurrentMonth());
        assertEquals(bill1.getAmount(), book.getCurrentMonthTotalExpense());


        book.addBill(bill2);
        assertEquals(2,book.size());
        assertTrue(book.containBill("BILL2"));
        assertEquals(bill1.getYear(), book.getCurrentYear());
        assertEquals(bill2.getMonth(), book.getCurrentMonth());
        assertEquals(bill2.getAmount(), book.getCurrentMonthTotalExpense());

        book.addBill(bill3);
        assertEquals(3,book.size());
        assertTrue(book.containBill("BILL3"));
        assertEquals(bill1.getYear(), book.getCurrentYear());
        assertEquals(bill2.getMonth(), book.getCurrentMonth());
        assertEquals((bill2.getAmount() + bill3.getAmount()), book.getCurrentMonthTotalExpense());
    }

    @Test
    void testContainBill(){
        book.addBill(bill1);
        assertFalse(book.containBill("bill1"));
        assertTrue(book.containBill("BILL1"));
    }

    @Test
    void testDeleteBill() {
        book.addBill(bill1);
        book.addBill(bill2);
        book.addBill(bill3);
        assertEquals(3,book.size());
        book.deleteBill("bill1");
        assertEquals(3,book.size());
        assertTrue(book.containBill("BILL1"));
        book.deleteBill("BILL1");
        assertEquals(2,book.size());
        assertFalse(book.containBill("BILL1"));
    }

    @Test
    void testSelectByMonth() {
        book.addBill(bill1);
        book.addBill(bill2);
        book.addBill(bill3);
        book.addBill(bill5);
        ArrayList<Bill> b = new ArrayList<>();
        b.add(bill2);
        b.add(bill3);
        assertEquals(b,book.selectByMonth(2021,8));
    }

    @Test
    void testSelectByCategory() {
        book.addBill(bill1);
        book.addBill(bill2);
        book.addBill(bill3);
        ArrayList<Bill> b = new ArrayList<>();
        b.add(bill1);
        b.add(bill2);
        assertEquals(b,book.selectByCategory(2));
    }

    @Test
    void testSwitchAlerting() {
        book.switchAlerting();
        assertTrue(book.getAlertingStatus());
        book.switchAlerting();
        assertFalse(book.getAlertingStatus());
    }

    @Test
    void testSetExpectedExpense() {
        book.setExpectedExpense(50);
        assertEquals(50,book.getExpectedExpense());
    }

    @Test
    void testReachExpectedExpense() {
        book.setExpectedExpense(400.02);
        assertFalse(book.reachExpectedExpense());

        book.addBill(bill1);
        assertFalse(book.reachExpectedExpense());

        book.addBill(bill2);
        assertFalse(book.reachExpectedExpense());

        book.addBill(bill3);
        assertTrue(book.reachExpectedExpense());

        book.addBill(bill4);
        assertTrue(book.reachExpectedExpense());
    }

    @Test
    void testGetBook() {
        book.addBill(bill1);
        ArrayList<Bill> b = new ArrayList<>();
        b.add(bill1);
        assertEquals(b,book.getBook());
    }







}