package ui;

import javax.swing.*;
import java.awt.*;

public class Background extends JComponent {
    private Image image;

    public Background(Image image) {
        this.image = image;
    }

    // EFFECTS: draw with graphics g
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
