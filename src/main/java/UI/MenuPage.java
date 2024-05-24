package UI;

import dataBase.DataBase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;

public class MenuPage extends JFrame implements ActionListener, MouseListener {

    // <editor-fold desc="Global Declarations">
    JButton createButton, readButton, updateButton, deleteButton, exitButton, delButton, upButton, refreshButton;
    JTable dataTable;
    JFrame SecondFrame;
    String id_selected;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public MenuPage() {

        createButton = new JButton("Add a New Event");
        readButton = new JButton("View All Events");
        updateButton = new JButton("Update an Event");
        deleteButton = new JButton("Delete an Event");
        exitButton = new JButton("Exit");

        JButton[] buttonList = { createButton, readButton, updateButton,
                deleteButton, exitButton
        };

        createButton.setBounds(217, 166, 194, 36);
        readButton.setBounds(217, 213, 194, 33);
        updateButton.setBounds(217, 256, 194, 33);
        deleteButton.setBounds(217, 300, 194, 32);
        exitButton.setBounds(217, 343, 194, 33);

        JLabel lblBankingSystem = new JLabel("Events Registry");
        lblBankingSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblBankingSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblBankingSystem.setBounds(0, 69, 613, 59);

        //private static final long serialVersionUID = 1L;
        JPanel contentPane = new JPanel();

        for(JButton btn : buttonList){
            btn.addActionListener(this);
            contentPane.add(btn);
        }
        contentPane.add(lblBankingSystem);

        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setTitle("Event Organizer App");
        setContentPane(contentPane);
        setBounds(100, 100, 649, 474);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    // </editor-fold>

    // <editor-fold desc="Creating a second page for JTable">
    void createSecondPage(DefaultTableModel model) {
        dataTable = new JTable(model);
        SecondFrame = new JFrame("View Data");
        delButton = new JButton("Delete this Event");
        upButton = new JButton("Update this Event");
        refreshButton = new JButton("Refresh");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(upButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(delButton);
        delButton.setEnabled(false);
        upButton.setEnabled(false);
        SecondFrame.setVisible(true);
        SecondFrame.setSize(700, 300);
        SecondFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        SecondFrame.add(new JScrollPane(dataTable));
        SecondFrame.add(buttonPanel, BorderLayout.SOUTH);
        dataTable.addMouseListener(this);
        delButton.addActionListener(this);
        upButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }
    // </editor-fold>

    @Override
    public void actionPerformed(ActionEvent e) {

        // <editor-fold desc="Create Mechanism">
        if (e.getSource() == createButton) {
            new CreateEventPage();
        }
        // </editor-fold>

        // <editor-fold desc="Read Mechanism">
        if (e.getSource() == readButton || e.getSource() == updateButton) {
            try {
                DataBase obj = new DataBase();
                DefaultTableModel model = obj.readData();
                createSecondPage(model);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        // </editor-fold>

        // <editor-fold desc="Dedicated Deleting Frame">
        else if (e.getSource() == deleteButton) {
            new DeleteEventPage();
        }
        // </editor-fold>

        // <editor-fold desc="Delete Mechanism">
        else if(e.getSource() == delButton){
            DataBase db = new DataBase();
            int res = 0, result = 0;
            try {
                result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this event?");
                if(result == JOptionPane.YES_OPTION)
                    res = db.deleteData(Integer.parseInt(id_selected));
                if(res>0){
                    JOptionPane.showMessageDialog(null, "Event deleted successfully");
                    delButton.setEnabled(false);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SecondFrame, ex.getMessage());
            }
        }
        // </editor-fold>

        // <editor-fold desc="Update Mechanism">
        else if(e.getSource() == upButton){
            System.out.println("This id was selected: " + id_selected);
            new UpdateEventPage(id_selected);
        }

        else if(e.getSource() == refreshButton){
            SecondFrame.dispose();
            readButton.doClick();
        }
        // </editor-fold>

        // <editor-fold desc="Exit Mechanism">
        else if (e.getSource() == exitButton) {
            //JOptionPane.showMessageDialog(getComponent(0), "Thanks For Using");
            System.exit(0);
        }
        // </editor-fold>
    }

    // <editor-fold desc="Mouse click Handling">
    @Override
    public void mouseClicked(MouseEvent e) {
        id_selected = dataTable.getValueAt(dataTable.getSelectedRow(),0).toString();
        delButton.setEnabled(true);
        upButton.setEnabled(true);
    }
    // </editor-fold>

    // <editor-fold desc="Unnecessary MouseListener Functions">
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    // </editor-fold>
}