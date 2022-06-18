package persistence;

import model.AccountBook;
import model.Bill;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountBook ab = new AccountBook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountBook() {
        try {
            AccountBook ab = new AccountBook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountBook.json");
            writer.open();
            writer.write(ab);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountBook.json");
            ab = reader.read();
            assertEquals(0, ab.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountBook() {
        try {
            AccountBook ab = new AccountBook();
            ab.addBill(new Bill(98.22,2021,1,31,2,"BILL1"));
            ab.addBill(new Bill(100.02,2021,8,28,2,"BILL2"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountBook.json");
            writer.open();
            writer.write(ab);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountBook.json");
            ab = reader.read();
            List<Bill> book = ab.getBook();
            assertEquals(2, book.size());
            checkBill(98.22,2021,1,31,2,"BILL1",book.get(0));
            checkBill(100.02,2021,8,28,2,"BILL2",book.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
