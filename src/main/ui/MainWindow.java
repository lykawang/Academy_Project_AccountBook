package ui;

import model.AccountBook;
import model.Bill;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JFrame implements ActionListener {

    private AccountBook book;
    private Bill bill;
    private PopUpWindow popUpWindow;
    private AccountBookWindow accountBookWindow;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/accountbook.json";

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BUT_WIDTH = 300;
    private static final int BUT_HEIGHT = 20;
    private static final int BUT_X_POS = 200;
    private static final int FIRST_BUT_Y_POS = 60;
    private static final int BUTTONS_GAP = 35;

    // EFFECTS: run the application
    public MainWindow() throws FileNotFoundException {
        super("My Account Book");
        book = new AccountBook();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
        setBackgroundImage();
        setActionLabelsAndButtons();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: set up the background of the window
    private void setBackgroundImage() {
        try {
            Image background = ImageIO.read(new File("src/main/ui/images/background.jpeg"));
            setContentPane(new Background(background));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: set up all the action labels and buttons inside the window
    private void setActionLabelsAndButtons() {
        JLabel labelWelcome = new JLabel("Welcome! Please Select from:");
        labelWelcome.setBounds(260, 20, 300, 10);
        add(labelWelcome);

        setButtonAddABill();
        setButtonDeleteABill();
        setButtonViewAllBills();
        setButtonViewOneMonthBill();
        setButtonViewOneCategoryBill();
        setAlert();
        setButtonSave();
        setButtonLoad();
        setButtonQuit();
    }

    // MODIFIES: this
    // EFFECTS: set up the "add a bill" button
    private void setButtonAddABill() {
        JButton buttonAddABill = new JButton("add a bill");
        buttonAddABill.setBounds(BUT_X_POS, FIRST_BUT_Y_POS, BUT_WIDTH, BUT_HEIGHT);
        add(buttonAddABill);
        buttonAddABill.setActionCommand("ADD_A_BILL");
        buttonAddABill.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "delete a bill" button
    private void setButtonDeleteABill() {
        JButton buttonDeleteABill = new JButton("delete a bill");
        buttonDeleteABill.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonDeleteABill);
        buttonDeleteABill.setActionCommand("DELETE_A_BILL");
        buttonDeleteABill.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "view all bills" button
    private void setButtonViewAllBills() {
        JButton buttonViewAllBills = new JButton("view all your bill");
        buttonViewAllBills.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 2 * BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonViewAllBills);
        buttonViewAllBills.setActionCommand("VIEW_ALL_BILLS");
        buttonViewAllBills.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "view one month bill" button
    private void setButtonViewOneMonthBill() {
        JButton buttonViewOneMonthBill = new JButton("view one month's bill");
        buttonViewOneMonthBill.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 3 * BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonViewOneMonthBill);
        buttonViewOneMonthBill.setActionCommand("VIEW_ONE_MONTH_BILL");
        buttonViewOneMonthBill.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "view one category bill" button
    private void setButtonViewOneCategoryBill() {
        JButton buttonViewOneCategoryBill = new JButton("view one category's bill");
        buttonViewOneCategoryBill.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 4 * BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonViewOneCategoryBill);
        buttonViewOneCategoryBill.setActionCommand("VIEW_ONE_CATEGORY_BILL");
        buttonViewOneCategoryBill.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the alert function with one label and two buttons "on" and "off"
    private void setAlert() {
        JLabel labelAlert = new JLabel("Alerting:");
        labelAlert.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 5 * BUTTONS_GAP, 80, BUT_HEIGHT);
        add(labelAlert);

        JButton buttonTurnOnAlert = new JButton("turn on");
        buttonTurnOnAlert.setBounds(BUT_X_POS + 110, FIRST_BUT_Y_POS + 5 * BUTTONS_GAP, 80, BUT_HEIGHT);
        add(buttonTurnOnAlert);
        buttonTurnOnAlert.setActionCommand("TURN_ON_ALERT");
        buttonTurnOnAlert.addActionListener(this);

        JButton buttonTurnOffAlert = new JButton("turn off");
        buttonTurnOffAlert.setBounds(BUT_X_POS + 220, FIRST_BUT_Y_POS + 5 * BUTTONS_GAP, 80, BUT_HEIGHT);
        add(buttonTurnOffAlert);
        buttonTurnOffAlert.setActionCommand("TURN_OFF_ALERT");
        buttonTurnOffAlert.addActionListener(this);

    }

    // MODIFIES: this
    // EFFECTS: set up the "save file" button
    private void setButtonSave() {
        JButton buttonSave = new JButton("save account book to file");
        buttonSave.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 7 * BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonSave);
        buttonSave.setActionCommand("SAVE");
        buttonSave.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "load file" button
    private void setButtonLoad() {
        JButton buttonLoad = new JButton("load account book from file");
        buttonLoad.setBounds(BUT_X_POS, FIRST_BUT_Y_POS + 8 * BUTTONS_GAP, BUT_WIDTH, BUT_HEIGHT);
        add(buttonLoad);
        buttonLoad.setActionCommand("LOAD");
        buttonLoad.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: set up the "quit" button
    private void setButtonQuit() {
        JButton buttonQuit = new JButton("quit");
        buttonQuit.setBounds(550, FIRST_BUT_Y_POS + 10 * BUTTONS_GAP, 80, BUT_HEIGHT);
        add(buttonQuit);
        buttonQuit.setActionCommand("QUIT");
        buttonQuit.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: all the actions when someone clicks a button
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ADD_A_BILL")) {
            addABill();
        } else if (e.getActionCommand().equals("DELETE_A_BILL")) {
            deleteABill();
        } else if (e.getActionCommand().equals("VIEW_ALL_BILLS")) {
            accountBookWindow = new AccountBookWindow(book);
        } else if (e.getActionCommand().equals("VIEW_ONE_MONTH_BILL")) {
            viewBillByMonth();
        } else if (e.getActionCommand().equals("VIEW_ONE_CATEGORY_BILL")) {
            viewBillByCategory();
        } else if (e.getActionCommand().equals("TURN_ON_ALERT")) {
            turnOnAlert();
        } else if (e.getActionCommand().equals("TURN_OFF_ALERT")) {
            turnOffAlert();
        } else if (e.getActionCommand().equals("SAVE")) {
            save();
        } else if (e.getActionCommand().equals("LOAD")) {
            load();
        } else if (e.getActionCommand().equals("QUIT")) {
            popUpWindow = new PopUpWindow("See you next time :)");
            dispose();
            printLog();
        }
    }

    public void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    // REQUiRES: user input valid value in expected types or conditions
    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "add a bill"
    private void addABill() {
        String inputDescription = JOptionPane.showInputDialog(null,
                "Please enter a unique description for this bill:");
        String inputYear = JOptionPane.showInputDialog(null,
                "Please enter the year you generated this bill:");
        String inputMonth = JOptionPane.showInputDialog(null,
                "Please enter the month you generated this bill:");
        String inputDay = JOptionPane.showInputDialog(null,
                "Please enter the day you generated this bill:");
        String inputAmount = JOptionPane.showInputDialog(null,
                "Please enter the amount of this bill:");
        String inputCategory = JOptionPane.showInputDialog(null,
                "Please choose its category type: "
                        + "(1: food; 2: cloth; 3: entertainment; 4: transportation; 5:other)");

        int year = Integer.parseInt(inputYear);
        int month = Integer.parseInt(inputMonth);
        int day = Integer.parseInt(inputDay);
        double amount = Double.parseDouble(inputAmount);
        int category = Integer.parseInt(inputCategory);

        bill = new Bill(amount, year, month, day, category, inputDescription);
        book.addBill(bill);
        popUpWindow = new PopUpWindow("successfully added");

        alert();
    }

    // EFFECTS: when the most recent month expense reach the set value, an alert will pop up.
    private void alert() {
        if (book.getAlertingStatus() && book.reachExpectedExpense()) {
            popUpWindow = new PopUpWindow("reached your set amount!");
        }
    }

    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "delete a bill";
    //          delete the bill user input; otherwise, do nothing.
    private void deleteABill() {
        String inputDescription = JOptionPane.showInputDialog(null,
                "Please enter the description of the bill you want to delete:");
        if (book.containBill(inputDescription)) {
            book.deleteBill(inputDescription);
            popUpWindow = new PopUpWindow("successfully deleted");
        } else {
            popUpWindow = new PopUpWindow("found no bill");
        }

    }

    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "view bill by month"
    private void viewBillByMonth() {
        String input = JOptionPane.showInputDialog(null,
                "Do you want to view the bills of the most recent month? (T/F)");
        if (input.equals("T")) {
            viewMostRecentMonthBill();
        } else if (input.equals("F")) {
            viewSelectedMonthBill();
        } else {
            popUpWindow = new PopUpWindow("can't identify your input");
        }
    }

    // MODIFIES: this
    // EFFECTS: a popup window shows with bills from the most recent month
    private void viewMostRecentMonthBill() {
        ArrayList<Bill> recentMonthBill = book.selectByMonth(book.getCurrentYear(), book.getCurrentMonth());
        AccountBook recentMonthBook = new AccountBook();
        for (Bill i : recentMonthBill) {
            recentMonthBook.addBill(i);
        }
        accountBookWindow = new AccountBookWindow(recentMonthBook);
    }

    // MODIFIES: this
    // EFFECTS: a popup window shows with bills from the specific input month
    private void viewSelectedMonthBill() {
        String inputYear = JOptionPane.showInputDialog(null,
                "please input the year you want to check:");
        String inputMonth = JOptionPane.showInputDialog(null,
                "please input the month you want to check:");
        int year = Integer.parseInt(inputYear);
        int month = Integer.parseInt(inputMonth);
        ArrayList<Bill> selectedMonthBill = book.selectByMonth(year, month);
        AccountBook selectedMonthBook = new AccountBook();
        for (Bill i : selectedMonthBill) {
            selectedMonthBook.addBill(i);
        }
        if (0 == selectedMonthBill.size()) {
            popUpWindow = new PopUpWindow("found no bill");
        } else {
            accountBookWindow = new AccountBookWindow(selectedMonthBook);
        }
    }

    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "view bill by month"
    private void viewBillByCategory() {
        String inputCategory = JOptionPane.showInputDialog(null,
                "Please input category type number you want to check: "
                        + "(1: food; 2: cloth; 3: entertainment; 4: transportation; 5:other)");
        int category = Integer.parseInt(inputCategory);
        ArrayList<Bill> categoryBill = book.selectByCategory(category);
        AccountBook selectedCategoryBook = new AccountBook();
        for (Bill i : categoryBill) {
            selectedCategoryBook.addBill(i);
        }
        int s = categoryBill.size();
        if (0 == s) {
            popUpWindow = new PopUpWindow("found no bill");
        } else {
            accountBookWindow = new AccountBookWindow(selectedCategoryBook);
        }
    }

    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "on" of the alert function
    public void turnOnAlert() {
        if (book.getAlertingStatus()) {
            popUpWindow = new PopUpWindow("already on!");
        } else {
            book.switchAlerting();
            String inputAlertAmount = JOptionPane.showInputDialog(null,
                    "Please enter the amount you want to receive alert when reach in the most recent month");
            double expectedExpense = Double.parseDouble(inputAlertAmount);
            book.setExpectedExpense(expectedExpense);
            popUpWindow = new PopUpWindow("It is on now.");
        }
    }

    // MODIFIES: this
    // EFFECTS: the actions when someone clicks "on" of the alert function
    public void turnOffAlert() {
        if (book.getAlertingStatus()) {
            book.switchAlerting();
            popUpWindow = new PopUpWindow("It is off now.");
        } else {
            popUpWindow = new PopUpWindow("already off!");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the account book to file
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();
            popUpWindow = new PopUpWindow("It is saved now.");
            EventLog.getInstance().logEvent(new Event("the account book has been saved"));
        } catch (FileNotFoundException e) {
            popUpWindow = new PopUpWindow("Unable to write");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the account book to file
    public void load() {
        try {
            book = jsonReader.read();
            popUpWindow = new PopUpWindow("finish loading");
            EventLog.getInstance().logEvent(new Event("the saved account book has been loaded"));
        } catch (IOException e) {
            popUpWindow = new PopUpWindow("Unable to read");
        }
    }

    // EFFECTS: run the application
    public static void main(String[] args) {
        try {
            new AccountBookApp();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}