package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represent the account book of bills
public class AccountBook implements Writable {
    private ArrayList<Bill> book;

    private boolean alerting; // whether the user want to turn on the function of managing expense and getting alert
    private double expectedExpense; // the amount of money which the user hope to spend less than it in a month
    private int currentYear; //the most recent year of all bills in the book
    private int currentMonth; //the most recent month of the most recent year of all bills in the book
    private double currentMonthTotalExpense; // the total amount of expense of the most recent month

    // MODIFIES: this
    // EFFECTS: constructs an empty collection of tasks
    public AccountBook() {
        book = new ArrayList<>();
        alerting = false;
        expectedExpense = 0;
        currentYear = 0;
        currentMonth = 1;
        currentMonthTotalExpense = 0;
    }

    // EFFECTS: return the alert status
    public boolean getAlertingStatus() {
        return alerting;
    }

    // EFFECTS: return the amount of expected expense
    public double getExpectedExpense() {
        return expectedExpense;
    }

    // EFFECTS: return the number of most current year
    public int getCurrentYear() {
        return currentYear;
    }

    // EFFECTS: return the number of the most current month
    public int getCurrentMonth() {
        return currentMonth;
    }

    // EFFECTS: return the amount of the most current month's total expense
    public double getCurrentMonthTotalExpense() {
        return currentMonthTotalExpense;
    }

    // EFFECTS: return the size of list book
    public int size() {
        return book.size();
    }

    // MODIFIES: this
    // EFFECTS: add a bill to the account book; update the expense of the most recent month; update the year and
    //          month if come to a new month
    public void addBill(Bill b) {
        book.add(b);
        if (b.getYear() > currentYear) {
            currentYear = b.getYear();
            currentMonth = b.getMonth();
            currentMonthTotalExpense = b.getAmount();
        } else if (b.getYear() == currentYear) {
            if (b.getMonth() > currentMonth) {
                currentMonth = b.getMonth();
                currentMonthTotalExpense = b.getAmount();
            } else if (b.getMonth() == currentMonth) {
                currentMonthTotalExpense += b.getAmount();
            }
        }
        EventLog.getInstance().logEvent(new Event(b + " is added"));
    }

    // REQUIRES: one of the object in the list should have this description
    // EFFECTS: return the Bill with the input description
    public boolean containBill(String description) {
        for (Bill b: book) {
            if (b.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }


    // REQUIRES: one of the object in the list should have this description
    // MODIFIES: this
    // EFFECTS: delete a bill from the account book
    public void deleteBill(String description) {
        for (Bill b : book) {
            if (b.getDescription().equals(description)) {
                book.remove(b);
                break;
            }
        }
        EventLog.getInstance().logEvent(new Event("the bill " + description + " is deleted"));
    }


    // EFFECTS: return a list of Bill with the same input month
    public ArrayList<Bill> selectByMonth(int year, int month) {
        ArrayList<Bill> monthBook = new ArrayList<>();
        for (Bill b : book) {
            if (b.getYear() == year && b.getMonth() == month) {
                monthBook.add(b);
            }
        }
        return monthBook;
    }

    // REQUIRES: categoryNum should be in the range of [1,5]
    // EFFECTS: return a list of Bill with the same input category
    public ArrayList<Bill> selectByCategory(int categoryNum) {
        ArrayList<Bill> categoryBook = new ArrayList<>();
        for (Bill b : book) {
            if (b.getCategoryNum() == categoryNum) {
                categoryBook.add(b);
            }
        }
        return categoryBook;
    }

    // MODIFIES: this
    // EFFECTS:  turn on the function of managing expense and getting alert if it is initial off; vice versa
    public void switchAlerting() {
        alerting = !alerting;
        EventLog.getInstance().logEvent(new Event("alert has been switched to " + alerting));
    }

    // REQUIRES: expense should be positive
    // MODIFIES: this
    // EFFECTS: modify the value of expense which the user hope to spend less than it in a month
    public void setExpectedExpense(double expense) {
        expectedExpense = expense;
        EventLog.getInstance().logEvent(new Event("alert amount " + expense + " is set"));
    }

    // EFFECTS: turn true if the amount of money spent this month reached the expected amount
    public boolean reachExpectedExpense() {
        return (currentMonthTotalExpense >= expectedExpense);
    }

    // EFFECTS: return the list book
    public ArrayList<Bill> getBook() {
        return book;
    }

    // EFFECTS: return json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("book", bookToJson());
        json.put("alerting", alerting);
        json.put("expectedExpense", this.expectedExpense);
        return json;
    }

    // EFFECTS: returns book in this account book as a JSON array
    private JSONArray bookToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Bill b : book) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}
