package persistence;

import model.AccountBook;
import model.Bill;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountBook ab = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountBook.json");
        try {
            AccountBook ab = reader.read();
            assertEquals(0, ab.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountBook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountBook.json");
        try {
            AccountBook ab = reader.read();
            List<Bill> book = ab.getBook();
            assertEquals(2, book.size());
            checkBill(98.22,2021,1,31,2,"BILL1",book.get(0));
            checkBill(100.02,2021,8,28,2,"BILL2",book.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
