package java_miniproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class ActionCnfm {
    JFrame frame;
    private String user;
    private JPasswordField paswordField;
    Object view;

    ActionCnfm(String u, Object v) {
        user = u;
        view = v;
        frame = new JFrame("Confirm");
        frame.setSize(400, 250);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel4.setBackground(Styles.primary_white);
        JLabel label1 = new JLabel("Confirm by entering privacy key: ");
        label1.setFont(new Font("Calibri", Font.PLAIN, 18));
        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Styles.primary_white);
        paswordField = new JPasswordField(20);
        paswordField.setEchoChar('*');
        paswordField.setBorder(Styles.text_field_border);
        paswordField.setFont(Styles.text_field_font);
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel3.setBackground(Styles.primary_white);
        JButton enterbtn = new JButton("Verify");
        enterbtn.setBackground(Styles.primary_button_color);
        enterbtn.setForeground(Color.WHITE);
        enterbtn.setBorder(Styles.white_button_border);
        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.setBackground(Styles.primary_white);
        
        panel4.add(Box.createHorizontalStrut(80));
        panel4.add(label1);
        panel2.add(paswordField);
        panel1.add(panel2);
        panel3.add(Box.createHorizontalStrut(50));
        panel3.add(enterbtn);
        frame.add(panel4);
        frame.add(panel1);
        frame.add(panel3);
        frame.setVisible(true);
        frame.getRootPane().setDefaultButton(enterbtn);
        enterbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String p = String.valueOf(paswordField.getPassword());
                if(verify(p)) {
                    sendResult();
                    frame.setVisible(false);
                } else {
                    new ErrorDialog("Wrong pin", "Privacy pin is incorrect !");
                }
            }
        });

        frame.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {

            }
            public void windowLostFocus(WindowEvent e) {
                String oppName = e.getOppositeWindow().getName();
                if(e.getOppositeWindow() != null) {
                    if(oppName.equals("CompleteView") || oppName.equals("Home")) {
                        frame.requestFocus();
                        java.awt.Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
        });
    }

    private boolean verify(String p) {
        try {
            UserDBUtilities user_db = new UserDBUtilities();
            PrivateDB private_db = new PrivateDB();
            String pin_e = user_db.getAccountPin(user);
            String pin_pk = private_db.getAccPinKey(user);
            EncryptedData eData = new EncryptedData();
            eData.privateKey = pin_pk;
            eData.encryptedPassword = pin_e;
            String pin = Encryption.decrypt(eData);
            user_db.endConnection();
            private_db.endConnection();
            if(pin.equals(p)) {
                return true;
            }
        } catch(Exception e) {
            System.out.println("Exception at privacy confirm");
        }
        return false;
    }

    private void sendResult() {
        if(view.getClass().equals(NewPassword.class)) {
            ((NewPassword) view).action();
        } else if(view.getClass().equals(ListItem.class)) {
            ((ListItem) view).action();
        } else if(view.getClass().equals(ChangeProperty.class)) {
            ((ChangeProperty) view).action();
        }
    }
}