package java_miniproject;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends JDialog {
    String title;
    String msg;

    ErrorDialog(String t, String m) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel(m));
        this.setSize(200, 100);
        this.setVisible(true);
    }
}