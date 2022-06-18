package ui;

import model.AccountBook;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PopUpWindow extends JFrame implements ActionListener {

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 130;

    //EFFECTS: construct a pop-up window
    public PopUpWindow(String string) {
        super("");
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
        setBackgroundImage();
        JLabel label = new JLabel(string);
        label.setBounds(20, 20, 170, 30);
        add(label);
        JButton buttonAddABill = new JButton("OK.");
        buttonAddABill.setBounds(10, 50, 170, 30);
        add(buttonAddABill);
        buttonAddABill.setActionCommand("CLICK");
        buttonAddABill.addActionListener(this);
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

    //EFFECTS: when user click on the button, the window will close
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("CLICK")) {
            dispose();
        }
    }
}
