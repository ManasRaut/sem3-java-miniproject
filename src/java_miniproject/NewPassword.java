package java_miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPassword {
    JFrame frame;
    JTextField webnameField;
    JTextField usrnameField;
    JTextField passwordField;
    private String user;
    NewPassword thisClass;
    Home home;

    NewPassword(String u, Home h) {
        user = u;
        home = h;
        thisClass = this;
        frame = new JFrame("Create New Password");
        frame.setSize(500, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        initUi();
        frame.setVisible(true);
    }

    private void initUi() {
        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("New password");
        panel1.setBackground(Styles.primary_white);
        label1.setFont(Styles.big_label_font);
        label1.setForeground(Styles.primary_violet);

        JPanel panel2 = new JPanel(new FlowLayout());
        JLabel label2 = new JLabel("Website: ");
        panel2.setBackground(Styles.primary_white);
        webnameField = new JTextField(20);
        webnameField.setBorder(Styles.text_field_border);
        webnameField.setFont(Styles.text_field_font);

        JPanel panel3 = new JPanel(new FlowLayout());
        JLabel label3 = new JLabel("Username : ");
        panel3.setBackground(Styles.primary_white);
        usrnameField = new JTextField(20);
        usrnameField.setBorder(Styles.text_field_border);
        usrnameField.setFont(Styles.text_field_font);

        JPanel panel4 = new JPanel(new FlowLayout());
        JLabel label4 = new JLabel("Password : ");
        panel4.setBackground(Styles.primary_white);
        passwordField = new JTextField(20);
        passwordField.setBorder(Styles.text_field_border);
        passwordField.setFont(Styles.text_field_font);

        JPanel panel5 = new JPanel(new FlowLayout());
        JLabel label5 = new JLabel("Suggest new password: ");
        JButton suggestBtn = new JButton("Suggest");
        panel5.setBackground(Styles.primary_white);
        suggestBtn.setBackground(Styles.primary_button_color);
        suggestBtn.setForeground(Color.WHITE);
        suggestBtn.setBorder(Styles.white_button_border);

        JPanel panel6 = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        panel6.setBackground(Styles.primary_white);
        addBtn.setBackground(Styles.primary_button_color);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(Styles.white_button_border);
        cancelBtn.setBackground(Styles.primary_button_color);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBorder(Styles.white_button_border);

        panel1.add(label1);
        panel2.add(label2);
        panel2.add(webnameField);       
        panel3.add(label3);
        panel3.add(usrnameField);      
        panel4.add(label4);
        panel4.add(passwordField);      
        panel5.add(label5);
        panel5.add(suggestBtn);        
        panel6.add(addBtn);
        panel6.add(cancelBtn);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);

        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
            }
        });
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                action();
            }
        });
        suggestBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                passwordField.setText(UserDBUtilities.generatePassword());
            }
        });
    }
    public void action() {
        try {
            UserDBUtilities db = new UserDBUtilities();
            PrivateDB privateDb = new PrivateDB();
            if(!db.checkUserInfo(webnameField.getText(), usrnameField.getText())) {
                String password = passwordField.getText().strip();
                EncryptedData password_e = Encryption.encrypt(password);
                db.insertUserInfo(user, webnameField.getText(), usrnameField.getText(), password_e.encryptedPassword);
                privateDb.insertUserPrivateKeys(webnameField.getText(), usrnameField.getText(), password_e.privateKey);
                frame.setVisible(false);
                db.endConnection();
                privateDb.endConnection();
            }
        } catch(Exception e) {
            new ErrorDialog("Error", "Unable to add new details");
            e.printStackTrace();
        }
        home.populate();
    }
}