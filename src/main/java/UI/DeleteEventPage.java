package UI;

import dataBase.DataBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DeleteEventPage extends JFrame implements ActionListener{

    // <editor-fold desc="Global Declarations">
    JButton submitButton;
    JTextField eventID_text;
    DataBase db;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public DeleteEventPage() {

        db = new DataBase();
        eventID_text = new JTextField();
        submitButton = new JButton("Delete");

        eventID_text.setBounds(217, 166, 194, 36);
        submitButton.setBounds(217, 213, 194, 33);

        JLabel lblBankingSystem = new JLabel("Deleting an Event");
        lblBankingSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankingSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblBankingSystem.setBounds(0, 69, 613, 59);

        //private static final long serialVersionUID = 1L;
        JPanel contentPane = new JPanel();

        contentPane.add(eventID_text);
        contentPane.add(submitButton);
        contentPane.add(lblBankingSystem);

        submitButton.addActionListener(this);

        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setTitle("Events Registry");
        setContentPane(contentPane);
        setVisible(true);
        setBounds(100, 100, 649, 474);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // </editor-fold>

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submitButton){
            String eventID = eventID_text.getText();
            Boolean result = false;
            try {
                int res = db.deleteData(Integer.parseInt(eventID));
                if(res>0){
                    JOptionPane.showMessageDialog(null, "Event deleted successfully");
                    eventID_text.setText(null);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}