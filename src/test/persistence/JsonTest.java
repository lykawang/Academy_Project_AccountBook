package persistence;

import model.Bill;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBill(double amount, int year, int month, int day, int categoryNum, String description, Bill b) {
        assertEquals(amount, b.getAmount());
        assertEquals(year,b.getYear());
        assertEquals(month,b.getMonth());
        assertEquals(day,b.getDay());
        assertEquals(categoryNum,b.getCategoryNum());
        assertEquals(description,b.getDescription());
    }
}
