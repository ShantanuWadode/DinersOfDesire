
package jdbc;

import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.Statement;
        import javax.swing.*;

public class FoodOrderForm {

    public static void main(String[] args) {

        JLabel heading = new JLabel();
        heading.setText("Food Order Form");
        heading.setBounds(100, 10, 200, 30);
        Font fontHeading = new Font("Times New Roman", Font.BOLD, 24);
        heading.setFont(fontHeading);

        // Creating labels
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 50, 100, 30);
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 100, 100, 30);
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setBounds(50, 150, 120, 30);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel timeSlotLabel = new JLabel("Time Slot:");
        timeSlotLabel.setBounds(50, 200, 100, 30);
        timeSlotLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(50, 250, 100, 30);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel foodLabel = new JLabel("Food Menu:");
        foodLabel.setBounds(50, 300, 100, 30);
        foodLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 350, 100, 30);
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // Creating text fields
        final JTextField firstNameTextField = new JTextField();
        firstNameTextField.setBounds(170, 50, 160, 30);

        final JTextField lastNameTextField = new JTextField();
        lastNameTextField.setBounds(170, 100, 160, 30);

        final JTextField phoneTextField = new JTextField();
        phoneTextField.setBounds(170, 150, 160, 30);

        // Creating time slot drop-down
        final String[] timeSlots = {"10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "12:00 PM - 1:00 PM", "1:00 PM - 2:00 PM", "2:00 PM - 3:00 PM", "3:00 PM - 4:00 PM"};
        final JComboBox<String> timeSlotComboBox = new JComboBox<>(timeSlots);
        timeSlotComboBox.setBounds(170, 200, 160, 30);

        // Creating date drop-down
        final String[] dates = {"2023-04-07", "2023-04-08", "2023-04-09", "2023-04-10"};
        final JComboBox<String> dateComboBox = new JComboBox<>(dates);
        dateComboBox.setBounds(170, 250, 160, 30);

        // Creating food menu drop-down
        final String[] foodMenu = {"Pizza", "Burger", "Sandwich", "Pasta"};
        final JComboBox<String> foodMenuComboBox = new JComboBox<>(foodMenu);
        foodMenuComboBox.setBounds(170, 300, 160, 30);
        // Creating quantity spinner
        SpinnerModel quantityModel = new SpinnerNumberModel(1, 1, 10, 1);
        final JSpinner quantitySpinner = new JSpinner(quantityModel);
        quantitySpinner.setBounds(170, 350, 50, 30);

        // Creating submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 400, 100, 30);

        // Creating reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(220, 400, 100, 30);

        // Adding action listeners to buttons
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Establishing connection to MySQL database
                    String url = "jdbc:mysql://localhost:3306/gui";
                    String username = "root";
                    String password = "Sh@101103";
                    Connection conn = DriverManager.getConnection(url, username, password);

                    // Creating prepared statement to insert data into 'orders' table
                    String insertSql = "INSERT INTO orders (first_name, last_name, phone_number, time_slot, date, food_menu, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(insertSql);

                    // Setting values in prepared statement
                    pstmt.setString(1, firstNameTextField.getText());
                    pstmt.setString(2, lastNameTextField.getText());
                    pstmt.setString(3, phoneTextField.getText());
                    pstmt.setString(4, timeSlotComboBox.getSelectedItem().toString());
                    pstmt.setString(5, dateComboBox.getSelectedItem().toString());
                    pstmt.setString(6, foodMenuComboBox.getSelectedItem().toString());
                    pstmt.setInt(7, (int) quantitySpinner.getValue());

                    // Executing prepared statement
                    pstmt.executeUpdate();

                    // Closing connection and prepared statement
                    pstmt.close();
                    conn.close();

                    // Displaying success message
                    JOptionPane.showMessageDialog(null, "Order placed successfully!");

                    // Resetting form
                    firstNameTextField.setText("");
                    lastNameTextField.setText("");
                    phoneTextField.setText("");
                    timeSlotComboBox.setSelectedIndex(0);
                    dateComboBox.setSelectedIndex(0);
                    foodMenuComboBox.setSelectedIndex(0);
                    quantitySpinner.setValue(1);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resetting form
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                phoneTextField.setText("");
                timeSlotComboBox.setSelectedIndex(0);
                dateComboBox.setSelectedIndex(0);
                foodMenuComboBox.setSelectedIndex(0);
                quantitySpinner.setValue(1);
            }
        });

        // Creating panel and adding components to it
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(heading);
        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(phoneLabel);
        panel.add(timeSlotLabel);
        panel.add(dateLabel);
        panel.add(foodLabel);
        panel.add(quantityLabel);
        panel.add(firstNameTextField);
        panel.add(lastNameTextField);
        panel.add(phoneTextField);
        panel.add(timeSlotComboBox);
        panel.add(dateComboBox);
        panel.add(foodMenuComboBox);
        panel.add(quantitySpinner);
        panel.add(submitButton);
        panel.add(resetButton);

        // Creating frame and adding panel to it
        JFrame frame = new JFrame("Food Order Form");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(panel);

        // Displaying frame
        frame.setVisible(true);
    }

    public void setVisible(boolean b) {
    }
}

