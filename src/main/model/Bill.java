package model;

import org.json.JSONObject;
import persistence.Writable;

//Represent a bill have the generation date, category, amount of money, and a unique description
public class Bill implements Writable {

    private String description; // Every bill has its description
    private double amount; // the amount of money of the bill
    private int categoryNum; // the number of different category
    //1: food; 2: cloth; 3: entertainment; 4: transportation; 5:other
    private int year; // the year of the bill generated
    private int month; // the month of the bill generated
    private int day; // the day of date of the bill generated

    // REQUIRES: month should between 1 and 12;
    //           day should between 1 and 31;
    //           the combination of the year, month and day should really exist
    //           categoryNum should be in the range of [1,5]
    //           the description should be unique
    // MODIFIES: this
    // EFFECTS: constructs a bill
    public Bill(double amount, int year, int month, int day, int categoryNum, String description) {
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.day = day;
        this.categoryNum = categoryNum;
        this.description = description;
    }

    // EFFECTS: return the amount of money of the bill
    public double getAmount() {
        return amount;
    }

    // EFFECTS: return the bill's category number
    public int getCategoryNum() {
        return categoryNum;
    }

    // EFFECTS: return the year when the bill generated
    public int getYear() {
        return year;
    }

    // EFFECTS: return the month when the bill generated
    public int getMonth() {
        return month;
    }

    // EFFECTS: return the day when the bill generated
    public int getDay() {
        return day;
    }

    // EFFECTS: return the description of the bill
    public String getDescription() {
        return description;
    }

    // EFFECTS: return a string with all the information of the bill is included
    @Override
    public String toString() {
        return "Bill {" + "description='" + description + '\'' + ", amount=" + amount + ", categoryType=" + categoryNum
                + ", date=" + year + "-" + month + "-" + day + '}';
    }

    // EFFECTS: return json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("year", year);
        json.put("month", month);
        json.put("day", day);
        json.put("categoryNum", categoryNum);
        json.put("description", description);
        return json;
    }
}
