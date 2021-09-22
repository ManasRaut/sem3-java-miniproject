package java_miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn {
    JFrame frame;
    JTextField userField;
    JPasswordField pswordField;

    LogIn() {
        frame = new JFrame("PassWarden");
        frame.setSize(750, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        initUi();
        frame.setVisible(true);
    }

    private void initUi() {

        JPanel sidebanner = new JPanel();
        sidebanner.setLayout(new BoxLayout(sidebanner, BoxLayout.Y_AXIS));
        sidebanner.setBackground(Styles.primary_violet);
        sidebanner.setMaximumSize(new Dimension(375, 450));
        JPanel panel_1 = new JPanel(new FlowLayout());
        JPanel panel_2 = new JPanel(new FlowLayout());
        panel_1.setBackground(Styles.primary_violet);
        panel_2.setBackground(Styles.primary_violet);
        ImageIcon appiconImage = new ImageIcon("src\\java_miniproject\\assets\\app_logo.png");
        frame.setIconImage(appiconImage.getImage());
        JLabel appIcon = new JLabel(appiconImage);
        JLabel appName = new JLabel("PassWarden");
        JLabel slogan = new JLabel("Never forget another password");
        appName.setFont(new Font("Roboto", Font.BOLD, 26));
        slogan.setFont(new Font("Calibri", Font.ITALIC, 16));
        appName.setForeground(Color.WHITE);
        slogan.setForeground(Color.WHITE);

        JPanel panel_3 = new JPanel(new FlowLayout());
        panel_3.setBackground(Styles.primary_violet);
        JLabel label_1 = new JLabel("Don't have a account ? ");
        label_1.setForeground(Color.WHITE);
        JButton signupBtn = new JButton("Sign up");
        signupBtn.setBackground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorder(Styles.black_button_border);
        
        panel_1.add(appIcon);
        panel_1.add(appName);
        panel_2.add(slogan);
        panel_3.add(label_1);
        panel_3.add(signupBtn);

        sidebanner.add(Box.createVerticalStrut(150));
        sidebanner.add(panel_1);
        sidebanner.add(panel_2);
        sidebanner.add(Box.createVerticalStrut(50));
        sidebanner.add(panel_3);

        JPanel parent = new JPanel();
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setBackground(Styles.primary_white);
        parent.setMaximumSize(new Dimension(500, 450));

        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.setBackground(Styles.primary_white);
        JLabel label1 = new JLabel("Log In");
        label1.setFont(Styles.big_label_font);
        label1.setForeground(Styles.primary_violet);

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Styles.primary_white);
        JLabel label2 = new JLabel("Username: ");
        userField = new JTextField(16);
        userField.setBackground(Color.WHITE);
        userField.setFont(Styles.text_field_font);
        userField.setBorder(Styles.text_field_border);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Styles.primary_white);
        JLabel label3 = new JLabel("Password");
        pswordField = new JPasswordField(16);
        pswordField.setEchoChar('*');
        pswordField.setBackground(Color.WHITE);
        pswordField.setFont(Styles.text_field_font);
        pswordField.setBorder(Styles.text_field_border);

        JPanel panel4 = new JPanel(new FlowLayout());
        panel4.setBackground(Styles.primary_white);
        JButton loginBtn = new JButton("LogIn");
        JButton clearBtn = new JButton("Clear");
        loginBtn.setBackground(Styles.primary_button_color);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBorder(Styles.white_button_border);
        loginBtn.setFocusPainted(false);
        clearBtn.setBackground(Styles.primary_button_color);
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setBorder(Styles.white_button_border);
        clearBtn.setFocusPainted(false);

        panel1.add(Box.createHorizontalStrut(150));
        panel1.add(label1);
        panel2.add(label2);
        panel2.add(userField);
        panel3.add(label3);
        panel3.add(pswordField);
        panel4.add(loginBtn);
        panel4.add(clearBtn);
        parent.add(Box.createVerticalStrut(50));
        parent.add(panel1);
        parent.add(panel2);
        parent.add(panel3);
        parent.add(panel4);
        frame.add(sidebanner);
        frame.add(parent);
        frame.getRootPane().setDefaultButton(loginBtn);
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                pswordField.setText("");
                userField.setText("");
                pswordField.setBorder(Styles.text_field_border);
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    userField.setBorder(Styles.text_field_border);
                    UserDBUtilities userDb = new UserDBUtilities();
                    PrivateDB privateDb = new PrivateDB();
                    String text = userField.getText().strip();
                    if(!userDb.checkUser(text)){
                        userField.setBorder(Styles.red_warning_border);
                        return;
                    }
                    String appPswrd_E = userDb.getAccountPassword(text);
                    String appPswrd_pk = privateDb.getAccPasswordKey(text);
                    EncryptedData data = new EncryptedData();
                    data.encryptedPassword = appPswrd_E;
                    data.privateKey = appPswrd_pk;
                    userDb.endConnection();
                    privateDb.endConnection();
                    if(String.valueOf(pswordField.getPassword()).equals(Encryption.decrypt(data))) {
                        new Home(text);
                        frame.setVisible(false); 
                    } else {
                        pswordField.setBorder(Styles.red_warning_border);
                    }
                } catch( Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Exception at login");
                }
            }
        });
        signupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
                new SignUp(frame);
            }
        });
    }

    public static void main(String[] args) {        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LogIn();
            }
        });
    }
}