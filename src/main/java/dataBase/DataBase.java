package dataBase;

import dataStructure.EventClass;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DataBase {

    // <editor-fold desc="Global Declarations">
    Connection conn;
    Statement st;
    ResultSet resultSet;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public DataBase() {
        String url = "jdbc:mysql://localhost:3306/acp_project";
        String user = "root";
        String pass = "ItisHard@1234";
        try {
            conn = DriverManager.getConnection(url, user, pass);
            st = conn.createStatement();
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
    }
    // </editor-fold>

    // <editor-fold desc="Create Mechanism">
    public Boolean createData(EventClass event) throws SQLException {
        PreparedStatement pStat = conn.prepareStatement("INSERT INTO events (event_title, event_date, event_time, event_location, event_description) VALUES (?, ?, ?, ?, ?)");
        pStat.setString(1, event.getTitle());
        pStat.setDate(2, event.getDate());
        pStat.setTime(3, event.getTime());
        pStat.setString(4, event.getLocation());
        pStat.setString(5, event.getDescription());
        int rowsAffected = pStat.executeUpdate();
        pStat.close();
        return rowsAffected > 0;
    }
    // </editor-fold>

    // <editor-fold desc="Creating EventClass object from ResultSet">
    private EventClass createEventClass(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Object[] rowData = new Object[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            rowData[i - 1] = resultSet.getObject(i);
            System.out.print(rowData[i - 1] + " ");
        }

        String title = (String) rowData[1];
        Date date = (Date) rowData[2];
        Time time = (Time) rowData[3];
        String location = (String) rowData[4];
        String description = (String) rowData[5];

        return new EventClass(title, date, time, location, description);
    }
    // </editor-fold>

    // <editor-fold desc="Reading a single row from DB">
    public EventClass readSingleRow(String eventId) throws SQLException {
        String query = "SELECT * FROM events WHERE event_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(eventId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next())
                    return createEventClass(resultSet);
                else {
                    System.out.println("No row found for event ID: " + eventId);
                    return null;
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold desc="Read Mechanism">
    public DefaultTableModel readData() throws SQLException {
        resultSet = st.executeQuery("SELECT * FROM events");
        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnCount = rsmd.getColumnCount();
        String[] columnNames = new String[columnCount];
        DefaultTableModel model = new DefaultTableModel();

        for (int i = 0; i < columnCount; i++)
            columnNames[i] = rsmd.getColumnName(i + 1);

        model.setColumnIdentifiers(columnNames);
        Object[] rowData = new Object[columnCount];
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            model.addRow(rowData);
        }
        return model;
    }
    // </editor-fold>

    // <editor-fold desc="Update Mechanism">
    public Boolean updateData(EventClass event, String eventID) throws SQLException {
        PreparedStatement pStat = conn.prepareStatement("UPDATE events SET event_title = ?, event_date = ?, event_time = ?, event_location = ?, event_description = ? WHERE event_id = ?");
        pStat.setString(1, event.getTitle());
        pStat.setDate(2, event.getDate());
        pStat.setTime(3, event.getTime());
        pStat.setString(4, event.getLocation());
        pStat.setString(5, event.getDescription());
        pStat.setInt(6, Integer.parseInt(eventID));
        return pStat.executeUpdate()>0;
    }
    // </editor-fold>

    // <editor-fold desc="Delete Mechanism">
    public int deleteData(int regNumber) throws SQLException {
        PreparedStatement pStat = conn.prepareStatement("DELETE FROM events WHERE event_id = ?");
        pStat.setInt(1, regNumber);
        return pStat.executeUpdate();
    }
    // </editor-fold>

    // <editor-fold desc="Checking User Credentials">
    public Boolean checkUser(String userName, String userPassword) throws SQLException {
        PreparedStatement pstat = conn.prepareStatement("SELECT * FROM users WHERE user_name = ?");
        pstat.setString(1, userName);
        resultSet = pstat.executeQuery();
        String user = "", pswd = "";
        while (resultSet.next()) {
           user = resultSet.getObject(1).toString();
           pswd = resultSet.getObject(2).toString();
        }

        if(!user.isEmpty() && !pswd.isEmpty()){
            return user.equals(userName) && pswd.equals(userPassword);
        }
        else
            return false;
    }
    // </editor-fold>
}

