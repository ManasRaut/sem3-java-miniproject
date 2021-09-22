package java_miniproject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SignUp {
    JFrame loginFrame;
    JFrame frame;
    private JTextField usrField;
    private JPasswordField pswordField;
    private JPasswordField cnfpswordField;
    private JPasswordField privacyField;

    SignUp(JFrame lf) {
        loginFrame = lf;
        frame = new JFrame("Sign Up");
        frame.setSize(650, 600);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        initUi();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                close();
            }
        });
    }

    private void initUi() {
        
        ImageIcon signUpIcon = new ImageIcon("src\\java_miniproject\\assets\\signup_icon.png");
        frame.setIconImage(new ImageIcon("src\\java_miniproject\\assets\\app_logo.png").getImage());
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setBackground(Styles.primary_white);
        JLabel signUpholder = new JLabel(signUpIcon);

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Styles.primary_white);
        JLabel label1 = new JLabel("Enter username");
        usrField = new JTextField(20);
        usrField.setBorder(Styles.text_field_border);
        usrField.setFont(Styles.text_field_font);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Styles.primary_white);
        JLabel label2 = new JLabel("Enter password");
        pswordField = new JPasswordField(20);
        pswordField.setFont(Styles.text_field_font);
        pswordField.setBorder(Styles.text_field_border);

        JPanel panel4 = new JPanel(new FlowLayout());
        panel4.setBackground(Styles.primary_white);
        JLabel label3 = new JLabel("Confirm password: ");
        cnfpswordField = new JPasswordField(20);
        cnfpswordField.setFont(Styles.text_field_font);
        cnfpswordField.setBorder(Styles.text_field_border);

        JPanel panel5 = new JPanel(new FlowLayout());
        panel5.setBackground(Styles.primary_white);
        JLabel label4 = new JLabel("Enter privacy key: ");
        privacyField = new JPasswordField(20);
        privacyField.setFont(Styles.text_field_font);
        privacyField.setBorder(Styles.text_field_border);
        JLabel infoLabel = new JLabel("  i  ");
        infoLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
        infoLabel.setBorder(new LineBorder(Styles.primary_violet, 1));
        infoLabel.setToolTipText("Privacy key will be used to view, edit and access all passwords");

        JPanel panel6 = new JPanel(new FlowLayout());
        panel6.setBackground(Styles.primary_white);
        JButton signUpBtn = new JButton("Sign Up");
        JButton clearBtn = new JButton("Clear");
        JButton cancel = new JButton("Cancel");
        signUpBtn.setBackground(Styles.primary_button_color);
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setBorder(Styles.white_button_border);
        clearBtn.setBackground(Styles.primary_button_color);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setBorder(Styles.white_button_border);
        cancel.setBackground(Styles.primary_button_color);
        cancel.setForeground(Color.WHITE);
        cancel.setBorder(Styles.white_button_border);

        panel1.add(signUpholder);
        panel2.add(label1);
        panel2.add(usrField);
        panel3.add(label2);
        panel3.add(pswordField);
        panel4.add(label3);
        panel4.add(cnfpswordField);
        panel5.add(label4);
        panel5.add(privacyField);
        panel5.add(infoLabel);
        panel6.add(signUpBtn);
        panel6.add(clearBtn);
        panel6.add(cancel);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);
        frame.getRootPane().setDefaultButton(signUpBtn);
        cnfpswordField.addCaretListener(new CaretListener(){
            public void caretUpdate(CaretEvent ce) {
                String p,c;
                p = String.valueOf(pswordField.getPassword());
                c = String.valueOf(cnfpswordField.getPassword());
                if (!c.equals(p) && !c.isEmpty()) {
                    cnfpswordField.setBorder(Styles.red_warning_border);
                    pswordField.setBorder(Styles.red_warning_border);
                } else {
                    cnfpswordField.setBorder(Styles.text_field_border);
                    pswordField.setBorder(Styles.text_field_border);
                }
            }
        }); 
        signUpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                EncryptedData password_data = new EncryptedData();
                EncryptedData privacy_data = new EncryptedData();
                try {
                    UserDBUtilities userDb = new UserDBUtilities();
                    PrivateDB privateDb = new PrivateDB();
                    String text = usrField.getText().strip();
                    if (userDb.checkUser(text)) {
                        usrField.setBorder(Styles.red_warning_border);
                    } else {
                        password_data = Encryption.encrypt(String.valueOf(pswordField.getPassword()));
                        privacy_data = Encryption.encrypt(String.valueOf(privacyField.getPassword()));
                        userDb.insertAccount(text, password_data.encryptedPassword , privacy_data.encryptedPassword);
                        privateDb.insertAccPrivateKeys(text, password_data.privateKey, privacy_data.privateKey);
                    }
                    userDb.endConnection();
                    privateDb.endConnection();
                    close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Exception at sign up");
                }
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                usrField.setText("");
                pswordField.setText("");
                cnfpswordField.setText("");
                privacyField.setText("");
                usrField.setBorder(Styles.text_field_border);
                pswordField.setBorder(Styles.text_field_border);
                privacyField.setBorder(Styles.text_field_border);
                cnfpswordField.setBorder(Styles.text_field_border);
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                close();
            }
        });
    }
    private void close() {
        frame.setVisible(false);
        loginFrame.setVisible(true);
    }
}