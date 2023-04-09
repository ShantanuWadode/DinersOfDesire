package jdbc;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
public class JDBC {
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static DefaultTableModel tableModel;
    private static JTable table;
    private static JFrame frame;
    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/gui";
            String username = "root";
            String password = "Sh@101103";

            conn=DriverManager.getConnection(url,username,password);

            stmt = conn.createStatement();

            tableModel = new DefaultTableModel();
            rs = stmt.executeQuery("select * from student");
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

            table = new JTable(tableModel);

            frame = new JFrame("Student DB");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(table));

            JButton viewButton = new JButton("View");
            JButton deleteButton = new JButton("Delete");
            JButton addButton = new JButton("Add");
            JButton updateButton = new JButton("Update");

            viewButton.addActionListener(e -> viewData());
            deleteButton.addActionListener(e -> deleteData());
            addButton.addActionListener(e -> addData());
            updateButton.addActionListener(e -> updateData());

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(viewButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);

            frame.add(buttonPanel, "East");

            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void viewData() {
        try {
            tableModel.setRowCount(0);

            rs = stmt.executeQuery("select * from student");

            while (rs.next()) {
                Object[] row = new Object[tableModel.getColumnCount()];
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void deleteData() {
        try {
            String idStr = JOptionPane.showInputDialog(frame, "Enter the ID of the row to delete:");
            int id = Integer.parseInt(idStr);

            //integer parseInt(idStr):converts string to integer

            stmt.executeUpdate("delete from student where id = " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void addData() {
        try {
            String sid = JOptionPane.showInputDialog(frame, "Enter id:");
            String name = JOptionPane.showInputDialog(frame, "Enter name:");
            String rating = JOptionPane.showInputDialog(frame, "Enter age:");
            stmt.executeUpdate("insert into student (id,name, age) values ('" + sid + "','" + name + "', " + rating + ")");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void updateData() {
        try {
            String idStr = JOptionPane.showInputDialog(frame, "Enter the ID of the row to update:");
            int id = Integer.parseInt(idStr);

            String newName = JOptionPane.showInputDialog(frame, "Enter new name:");
            String newrating = JOptionPane.showInputDialog(frame, "Enter new age:");
            String new_id = JOptionPane.showInputDialog(frame, "Enter new id:");

            stmt.executeUpdate("update student set name = '" + newName + "', age = " + newrating + ",id="+ new_id +" where id = " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}