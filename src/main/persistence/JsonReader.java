package persistence;

import model.AccountBook;
import model.Bill;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads account book from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account book from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AccountBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account book from JSON object and returns it
    private AccountBook parseAccountBook(JSONObject jsonObject) {
        boolean alerting = jsonObject.getBoolean("alerting");
        double expectedExpense = jsonObject.getDouble("expectedExpense");
        AccountBook ab = new AccountBook();
        if (alerting) {
            ab.switchAlerting();
        }
        ab.setExpectedExpense(expectedExpense);
        addBook(ab, jsonObject);
        return ab;
    }

    // MODIFIES: ab
    // EFFECTS: parses book from JSON object and adds them to account book
    private void addBook(AccountBook ab, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("book");
        for (Object json : jsonArray) {
            JSONObject nextBill = (JSONObject) json;
            addBill(ab, nextBill);
        }
    }

    // MODIFIES: ab
    // EFFECTS: parses bill from JSON object and adds it to account book
    private void addBill(AccountBook ab, JSONObject jsonObject) {

        double amount = jsonObject.getDouble("amount");
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");
        int categoryNum = jsonObject.getInt("categoryNum");
        String description = jsonObject.getString("description");

        Bill bill = new Bill(amount, year, month, day, categoryNum, description);
        ab.addBill(bill);
    }



}
