package java_miniproject;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class ChangeProperty {
    JFrame frame;
    String user;
    String property;
    JTextField cnfproperty;
    JTextField newproperty;
    JButton changebtn;
    boolean permitted;
    ChangeProperty thisClass;

    ChangeProperty(String u, String p) {
        property = p;
        permitted = false;
        user = u;
        thisClass = this;
        frame = new JFrame("Change " + p);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(500, 250);
        initUi();
        frame.setVisible(true);
    }

    public void initUi() {
        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("Change " + property);
        panel1.setBackground(Styles.primary_white);
        label1.setFont(Styles.big_label_font);
        label1.setForeground(Styles.primary_violet);

        JPanel panel2 = new JPanel(new FlowLayout());
        JLabel label2 = new JLabel("Enter new " + property + ": ");
        newproperty = new JTextField(20);
        panel2.setBackground(Styles.primary_white);
        newproperty.setBorder(Styles.text_field_border);
        newproperty.setFont(Styles.text_field_font);

        JPanel panel3 = new JPanel(new FlowLayout());
        JLabel label3 = new JLabel("Confirm " + property + " :");
        cnfproperty = new JTextField(20);
        panel3.setBackground(Styles.primary_white);
        cnfproperty.setBorder(Styles.text_field_border);
        cnfproperty.setFont(Styles.text_field_font);

        JPanel panel4 = new JPanel(new FlowLayout());
        changebtn = new JButton("Change");
        JButton cancelbtn = new JButton("Cancel");
        panel4.setBackground(Styles.primary_white);
        changebtn.setBackground(Styles.primary_button_color);
        changebtn.setForeground(Color.WHITE);
        changebtn.setBorder(Styles.white_button_border);
        cancelbtn.setBackground(Styles.primary_button_color);
        cancelbtn.setForeground(Color.WHITE);
        cancelbtn.setBorder(Styles.white_button_border);
        
        panel1.add(label1);
        panel2.add(label2);
        panel2.add(newproperty);
        panel3.add(label3);
        panel3.add(cnfproperty);
        panel4.add(changebtn);
        panel4.add(cancelbtn);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(panel4);

        cancelbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false);
            }
        });
        changebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!permitted) {
                    new ActionCnfm(user, thisClass);
                    return;
                }

                if(newproperty.getText().equals(cnfproperty.getText())) {
                    try {
                        UserDBUtilities user_db = new UserDBUtilities();
                        PrivateDB private_db = new PrivateDB();
                        EncryptedData data_e = Encryption.encrypt(newproperty.getText());
                        if (property.equals("password")) {
                            user_db.updateAccountPassword(user, data_e.encryptedPassword);
                            private_db.updateAccountPrivateKey(user, data_e.privateKey);
                        } else {
                            user_db.updateAccountPin(user, data_e.encryptedPassword);
                            private_db.updateAccountPinKey(user, data_e.privateKey);
                        }
                        user_db.endConnection();
                        private_db.endConnection();
                    } catch(Exception e) {
                        System.out.println("Exception at change property");
                    }
                    frame.setVisible(false);
                } else {
                    cnfproperty.setBorder(Styles.red_warning_border);
                }
            }
        });
        frame.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {

            }
            public void windowLostFocus(WindowEvent e) {
                String oppName = e.getOppositeWindow().getName();
                if(e.getOppositeWindow() != null) {
                    if(oppName.equals("Home")) {
                        frame.requestFocus();
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
        });
    }

    public void action() {
        permitted = true;
        changebtn.doClick();
    }
}