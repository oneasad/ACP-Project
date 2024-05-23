package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class loginPage extends JFrame implements ActionListener {

    // <editor-fold desc="Global Declarations">
    JTextField userName_text;
    JPasswordField password_text;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public loginPage() {
        JLabel mainHeading_label = new JLabel("Event Registry");
        JLabel subheading_label = new JLabel("Login Screen");
        JLabel userName_label = new JLabel("Username:");
        JLabel password_label = new JLabel("Password:");
        JButton loginButton = new JButton("Login");
        userName_text = new JTextField("admin");
        password_text = new JPasswordField("admin");

        mainHeading_label.setFont(new Font("Tahoma", Font.BOLD, 17));
        subheading_label.setFont(new Font("Tahoma", Font.PLAIN, 13));
        userName_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        password_label.setFont(new Font("Tahoma", Font.PLAIN, 12));


        userName_text.setColumns(10);
        password_text.setColumns(10);

        loginButton.addActionListener(this);

        getContentPane().add(mainHeading_label);
        getContentPane().add(subheading_label);
        getContentPane().add(userName_label);
        getContentPane().add(password_label);
        getContentPane().add(userName_text);
        getContentPane().add(password_text);
        getContentPane().add(loginButton);

        mainHeading_label.setBounds(147, 11, 151, 41);
        subheading_label.setBounds(170, 63, 101, 23);
        userName_label.setBounds(55, 119, 64, 23);
        password_label.setBounds(55, 159, 64, 23);
        userName_text.setBounds(130, 121, 86, 20);
        password_text.setBounds(130, 161, 86, 20);
        loginButton.setBounds(260, 138, 89, 23);
        setBounds(100, 100, 450, 300);


        setTitle("Event Organizer App");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // </editor-fold>

    public void actionPerformed(ActionEvent e) {
        String userName,password;
        userName = userName_text.getText();
        password = new String(password_text.getPassword());

        if(userName.equals("admin") && password.equals("admin"))
        {
            //JOptionPane.showMessageDialog(getComponent(0), "Login Successfully");
            setVisible(false);
            MenuPage obj = new MenuPage();
			obj.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(getComponent(0), "Login Failed");
    }
}
