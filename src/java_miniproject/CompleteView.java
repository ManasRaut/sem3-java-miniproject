package java_miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class CompleteView {
    private String user;
    private String website;
    private String pass;
    String acc;
    JFrame frame;
    JTextField webnameField;
    JTextField usrnameField;
    JPasswordField passwordField;
    Clipboard clipboard;
    CompleteView thisClass;
    JButton copyBtn;
    Home home;

    CompleteView(Home h, String a, String w, String u, String p) {
        user = u;
        website = w;
        pass = p;
        acc = a;
        thisClass = this;
        home = h;
        clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        frame = new JFrame("All Details");
        frame.setName("CompleteView");
        frame.setSize(600, 400);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
            }
			public void windowLostFocus(WindowEvent e) {
                if(e.getOppositeWindow() != null) {
                    if(e.getOppositeWindow().getName().equals("Home")) {
                        frame.requestFocus();
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }
                }
			}
        });
        initUi();
        loadDetails();
        frame.setVisible(true);
    }

    public void initUi() {
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setBackground(Styles.primary_white);
        JLabel label1 = new JLabel("Password details");
        label1.setFont(Styles.big_label_font);
        label1.setForeground(Styles.primary_violet);

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Styles.primary_white);
        JLabel label2 = new JLabel("Website: ");
        webnameField  = new JTextField(20);
        webnameField.setFont(Styles.text_field_font);
        webnameField.setBorder(Styles.text_field_border);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Styles.primary_white);
        JLabel label3 = new JLabel("Username: ");
        usrnameField = new JTextField(20);
        usrnameField.setFont(Styles.text_field_font);
        usrnameField.setBorder(Styles.text_field_border);

        JPanel panel4 = new JPanel(new FlowLayout());
        panel4.setBackground(Styles.primary_white);
        JLabel label4 = new JLabel("Password: ");
        copyBtn = new JButton("Copy");
        copyBtn.setBackground(Styles.primary_button_color);
        copyBtn.setBorder(Styles.white_button_border);
        copyBtn.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);
        passwordField.setFont(Styles.text_field_font);
        passwordField.setBorder(Styles.text_field_border);

        JPanel panel5 = new JPanel(new FlowLayout());
        panel5.setBackground(Styles.primary_white);
        JButton removeBtn = new JButton("delete");
        JButton savebtn = new JButton("Save");
        savebtn.setBackground(Styles.primary_button_color);
        savebtn.setBorder(Styles.white_button_border);
        savebtn.setForeground(Color.WHITE);
        removeBtn.setBackground(Styles.primary_button_color);
        removeBtn.setBorder(Styles.white_button_border);
        removeBtn.setForeground(Color.WHITE);

        panel1.add(label1);
        panel2.add(label2);
        panel2.add(webnameField);
        panel3.add(label3);
        panel3.add(usrnameField);
        panel4.add(Box.createHorizontalStrut(70));
        panel4.add(label4);
        panel4.add(passwordField);
        panel4.add(copyBtn);
        panel5.add(savebtn);
        panel5.add(removeBtn);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);

        copyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String s = String.valueOf(passwordField.getPassword());
                clipboard.setContents(new StringSelection(s), null);
            }
        });
        removeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    UserDBUtilities userdb = new UserDBUtilities();
                    PrivateDB privatedb = new PrivateDB();
                    UserInfo entry = new UserInfo(acc, website, user, pass);
                    userdb.deleteUserInfo(entry);
                    privatedb.deleteUserPrivateKey(website, user);
                    userdb.endConnection();
                    privatedb.endConnection();
                } catch(Exception e) {
                    System.out.println("Exception at remove password");
                    new ErrorDialog("Error", "Could'nt delete password");
                    e.printStackTrace();
                }
                frame.setVisible(false);
                home.populate();
                home.frame.setSize(651,501);
                home.frame.setSize(650,501);
//                sgR&L5dh*8
            }
        });
        savebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    UserDBUtilities userDb = new UserDBUtilities();
                    PrivateDB privateDb = new PrivateDB();
                    EncryptedData data_e = Encryption.encrypt(String.valueOf(passwordField.getPassword()));
                    userDb.updateUserPassword(website, user, usrnameField.getText(), acc, data_e.encryptedPassword);
                    privateDb.updateUserPrivateKey(website, user, usrnameField.getText(), data_e.privateKey);
                    privateDb.endConnection();
                    userDb.endConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                frame.setVisible(false);
                home.populate();
                home.frame.setSize(651,501);
                home.frame.setSize(650,501);
            }
        });
    }

    public void loadDetails() {
        try {
            PrivateDB private_db = new PrivateDB();
            EncryptedData data_e = new EncryptedData();
            data_e.encryptedPassword = pass;
            data_e.privateKey = private_db.getUserPrivateKey(website, user);
            String password = Encryption.decrypt(data_e);
            passwordField.setText(password);
            webnameField.setText(website);
            usrnameField.setText(user);
            private_db.endConnection();
        } catch(Exception e) {
            new ErrorDialog("Error", "Could'nt load! please try again");
        }
    }
}