package ui;

import java.io.FileNotFoundException;

public class Main {

    // EFFECTS: run the application
    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
