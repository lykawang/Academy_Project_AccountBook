package ui;

import model.AccountBook;
import model.Bill;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Account book application
public class AccountBookApp {
    private static final String JSON_STORE = "./data/accountbook.json";
    private AccountBook book;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the application
    public AccountBookApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        book = new AccountBook();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runAccountBook();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runAccountBook() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("0")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThank you for using! See you next time :)");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            doAddBill();
        } else if (command.equals("2")) {
            doDeleteBill();
        } else if (command.equals("3")) {
            doViewAllBill();
        } else if (command.equals("4")) {
            doViewBillByMonth();
        } else if (command.equals("5")) {
            doViewBillByCategory();
        } else if (command.equals("6")) {
            doTurnOnAlert();
        } else if (command.equals("7")) {
            doTurnOffAlert();
        } else if (command.equals("8")) {
            saveAccountBook();
        } else if (command.equals("9")) {
            loadAccountBook();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add a bill");
        System.out.println("\t2 -> delete a bill");
        System.out.println("\t3 -> view all your bill");
        System.out.println("\t4 -> view one month's bill");
        System.out.println("\t5 -> view one category's bill");
        System.out.println("\t6 -> turn on spending alert");
        System.out.println("\t7 -> turn off spending alert");
        System.out.println("\t8 -> save account book to file");
        System.out.println("\t9 -> load account book from file");
        System.out.println("\t0 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add a bill to the book
    private void doAddBill() {
        input.nextLine();
        System.out.println("Please enter a unique description for this bill:");
        String description = input.nextLine();

        System.out.println("Please enter the year you generated this bill:");
        int year = input.nextInt();

        System.out.println("Please enter the month you generated this bill:");
        int month = input.nextInt();

        System.out.println("Please enter the day you generated this bill:");
        int day = input.nextInt();

        System.out.println("Please enter the amount of this bill:");
        double amount = input.nextDouble();

        System.out.println("Please choose its category type:");
        System.out.println("1: food; 2: cloth; 3: entertainment; 4: transportation; 5:other");
        int categoryNum = input.nextInt();

        Bill b = new Bill(amount, year, month, day, categoryNum, description);
        System.out.println("The " + b.toString() + " is successfully added");
        book.addBill(b);

        if (book.getAlertingStatus() && book.reachExpectedExpense()) {
            System.out.println("You have already reached your set amount!");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete a bill from the book
    private void doDeleteBill() {
        System.out.println("Please enter the description of the bill you want to delete:");
        input.nextLine();
        String description = input.nextLine();
        if (book.containBill(description)) {
            book.deleteBill(description);
            System.out.println("It has been deleted.");
        } else {
            System.out.println("Sorry, there is no bill with this description.");
        }
    }

    // EFFECTS: return all the bills that the user created
    private void doViewAllBill() {
        System.out.println("Here is all of your bills:");
        int s = book.size();
        if (0 == s) {
            System.out.println("Sorry, you have not made any bill yet");
        } else {
            for (int i = 0; i < s; i++) {
                System.out.println(book.getBook().get(i));
            }
        }
    }

    // EFFECTS: return all the bills in the most recent month that the user created
    private void doViewBillByMonth() {
        input.nextLine();
        System.out.println("Do you want to view the bills of the most recent month? (T/F)");
        String view = input.nextLine();
        if (view.equalsIgnoreCase("T")) {
            ArrayList<Bill> recentMonthBill = book.selectByMonth(book.getCurrentYear(), book.getCurrentMonth());
            for (int i = 0; i < recentMonthBill.size(); i++) {
                System.out.println(recentMonthBill.get(i));
            }
            System.out.println("You have spent " + book.getCurrentMonthTotalExpense() + " in the most recent month");
        } else {
            System.out.println("please input the year:");
            int year = input.nextInt();
            System.out.println("please input the month:");
            int month = input.nextInt();
            ArrayList<Bill> selectedMonthBill = book.selectByMonth(year,month);
            if (0 == selectedMonthBill.size()) {
                System.out.println("Sorry, no bill in that month");
            } else {
                for (int i = 0; i < selectedMonthBill.size(); i++) {
                    System.out.println(selectedMonthBill.get(i));
                }
            }
        }
    }

    // EFFECTS: return all the bills in one specific category
    private void doViewBillByCategory() {
        System.out.println("Please input the number of category type:");
        int categoryNum = input.nextInt();
        System.out.println("Here is all your bills in " + categoryNum + " category type:");
        ArrayList<Bill> recentCategoryBill = book.selectByCategory(categoryNum);
        int s = recentCategoryBill.size();
        if (0 == s) {
            System.out.println("None of your bill is in this category.");
        } else {
            for (int i = 0; i < s; i++) {
                System.out.println(recentCategoryBill.get(i));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: turn of the alert
    private void doTurnOnAlert() {
        if (book.getAlertingStatus()) {
            System.out.println("This is already on.");
        } else {
            book.switchAlerting();
            System.out.println("Please enter the amount you want to receive alert when reach in the most recent month");
            double expectedExpense = input.nextDouble();
            book.setExpectedExpense(expectedExpense);
            System.out.println("It is on now.");
        }
    }

    // MODIFIES: this
    // EFFECTS: turn off the alert
    private void doTurnOffAlert() {
        if (book.getAlertingStatus()) {
            book.switchAlerting();
            System.out.println("This is off now.");
        } else {
            System.out.println("This is already off.");
        }
    }

    // EFFECTS: saves the account book to file
    private void saveAccountBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();
            System.out.println("Saved.");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account book from file
    private void loadAccountBook() {
        try {
            book = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
