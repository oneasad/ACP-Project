package UI;

import dataBase.DataBase;
import dataStructure.EventClass;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateEventPage extends JFrame implements ActionListener{

    // <editor-fold desc="Global Declarations">
    JButton submitButton;
    JTextField eventTitle_text, eventDate_text, eventTime_text, eventLocation_text, eventDescription_text;
    JLabel eventTitle_label, eventDate_label, eventTime_label, eventLocation_label, eventDescription_label;
    EventClass eventClass;
    DataBase db;
    JTextField[] textList;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public CreateEventPage() {
        db = new DataBase();
        eventTitle_text = new JTextField();
        eventDate_text = new JTextField();
        eventTime_text = new JTextField();
        eventLocation_text = new JTextField();
        eventDescription_text = new JTextField();
        eventTitle_label = new JLabel("Title");
        eventDate_label = new JLabel("Date");
        eventTime_label = new JLabel("Time");
        eventLocation_label = new JLabel("Location");
        eventDescription_label = new JLabel("Description");
        submitButton = new JButton("Submit");

        textList = new JTextField[]{eventTitle_text, eventDate_text, eventTime_text, eventLocation_text, eventDescription_text};
        JLabel[] labelList = { eventTitle_label, eventDate_label, eventTime_label, eventLocation_label, eventDescription_label };

        eventTitle_text.setBounds(217, 166, 194, 36);
        eventDate_text.setBounds(217, 213, 194, 33);
        eventTime_text.setBounds(217, 256, 194, 33);
        eventLocation_text.setBounds(217, 300, 194, 32);
        eventDescription_text.setBounds(217, 343, 194, 33);
        submitButton.setBounds(217, 393, 194, 33);

        eventTitle_label.setBounds(180, 166, 194, 36);
        eventDate_label.setBounds(180, 213, 194, 33);
        eventTime_label.setBounds(180, 256, 194, 33);
        eventLocation_label.setBounds(160, 300, 194, 32);
        eventDescription_label.setBounds(143, 343, 194, 33);

        JLabel lblBankingSystem = new JLabel("Creating an Event");
        lblBankingSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankingSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblBankingSystem.setBounds(0, 69, 613, 59);

        //private static final long serialVersionUID = 1L;
        JPanel contentPane = new JPanel();

        for(int i=0; i<5; i++){
            contentPane.add(textList[i]);
            contentPane.add(labelList[i]);
        }

        contentPane.add(submitButton);
        submitButton.addActionListener(this);
        contentPane.add(lblBankingSystem);

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

            // <editor-fold desc="Data Retrieval & Pre-processing">
            String eventTitle = eventTitle_text.getText();
            String eventDate = eventDate_text.getText();
            String eventTime = eventTime_text.getText();
            String eventLocation = eventLocation_text.getText();
            String eventDescription = eventDescription_text.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date convertedDate = null;
            Time convertedTime = null;
            Boolean result = false;
            try {
                java.util.Date parsedTime = timeFormat.parse(eventTime);
                java.util.Date parsedDate = dateFormat.parse(eventDate);
                convertedDate = new Date(parsedDate.getTime());
                convertedTime = new Time(parsedTime.getTime());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            // </editor-fold>

            eventClass = new EventClass(eventTitle, convertedDate, convertedTime, eventLocation, eventDescription);

            try {
                result = db.createData(eventClass);
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            if (result){
                JOptionPane.showMessageDialog(null, "Event Created");
                for (JTextField tf:textList)
                    tf.setText(null);
            }
        }
    }
}