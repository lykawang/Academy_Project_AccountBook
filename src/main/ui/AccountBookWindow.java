package ui;

import model.AccountBook;
import model.Bill;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AccountBookWindow extends JFrame {

    private AccountBook book;
    private Bill bill;
    private DefaultTableModel model;
    private JTable table;

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 700;

    // EFFECTS: construction a window with bills
    public AccountBookWindow(AccountBook book) {
        super("Bills");
        this.book = book;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
        setBackgroundImage();

        JLabel label1 = new JLabel("Here is your bills:");
        label1.setBounds(30, 20, 270, 20);
        add(label1);

        bookTable();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
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
    // EFFECTS: present the bills of your selected book in a table view
    private void bookTable() {
        if (book.size() == 0) {
            JLabel label2 = new JLabel("Sorry, no bill has found");
            label2.setBounds(230, 300, 200, 20);
            add(label2);
        } else {
            final String[] tableHeader = new String[]{
                    "Description",
                    "Amount",
                    "Category Type",
                    "Date",
            };
            model = new DefaultTableModel(0, tableHeader.length);
            model.setColumnIdentifiers(tableHeader);
            addRowsToTable();

            table = new JTable(model);
            table.setBounds(30, 50, 540, 600);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(30, 50, 540, 600);
            add(scrollPane);
        }
    }

    // MODIFIES: this
    // EFFECTS: add book data as row to the table
    private void addRowsToTable() {
        for (int i = 0; i < book.size(); i++) {
            bill = book.getBook().get(i);
            String description = bill.getDescription();
            double amount = bill.getAmount();
            String categoryType = categoryTypeName();
            String year = String.valueOf(bill.getYear());
            String month = String.valueOf(bill.getMonth());
            String day = String.valueOf(bill.getDay());
            String date = year + "-" + month + "-" + day;
            Object[] row = new Object[]{
                    description,
                    amount,
                    categoryType,
                    date,
            };
            model.addRow(row);
        }
    }

    private String categoryTypeName() {
        if (bill.getCategoryNum() == 1) {
            return "food";
        } else if (bill.getCategoryNum() == 2) {
            return "cloth";
        } else if (bill.getCategoryNum() == 3) {
            return "entertainment";
        } else if (bill.getCategoryNum() == 4) {
            return "transportation";
        } else if (bill.getCategoryNum() == 5) {
            return "other";
        }
        return "error";
    }
}
