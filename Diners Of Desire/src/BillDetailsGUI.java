import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BillDetailsGUI extends JFrame {

    private JTable table;

    public BillDetailsGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Order Details");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        JLabel headingLabel = new JLabel("Order Details");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(Color.BLACK);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headingLabel, BorderLayout.NORTH);

        table = new JTable();
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        String[] columns = { "ID", "First Name", "Last Name", "Food Item", "Price", "Date" };
        DefaultTableModel model = new BillTableModel(columns, getBillDetails());
        table.setModel(model);
    }

    private Object[][] getBillDetails() {
        Object[][] rows = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gui", "root",
                    "Sh@101103");
            String sql = "SELECT * FROM bills";
            PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            rows = new Object[rowCount][6];

            int i = 0;
            while (resultSet.next()) {
                rows[i][0] = resultSet.getInt("id");
                rows[i][1] = resultSet.getString("first_name");
                rows[i][2] = resultSet.getString("last_name");
                rows[i][3] = resultSet.getString("food_item");
                rows[i][4] = resultSet.getDouble("price");
                rows[i][5] = resultSet.getTimestamp("datetime");
                i++;
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    public static void main(String[] args) {
        BillDetailsGUI gui = new BillDetailsGUI();
        gui.setVisible(true);
    }
}

class BillTableModel extends DefaultTableModel {

    public BillTableModel(String[] columns, Object[][] rows) {
        super(rows, columns);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 4) {
            return Double.class;
        } else if (columnIndex == 5) {
            return java.util.Date.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
