

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.awt.event.ActionEvent;

public class foodOrderForm extends JFrame {

    private JPanel contentPane;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JComboBox<String> foodComboBox;
    private JLabel priceLabel;
    private JButton btnSubmit;

    // Define the list of food items and their prices
    private String[] foodItems = {"Pizza", "Burger", "Noodles", "Thali", "Juice"};
    private double[] foodPrices = {100, 70, 100, 70, 50};

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    foodOrderForm frame = new foodOrderForm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public foodOrderForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFirstName.setBounds(10, 28, 91, 17);
        contentPane.add(lblFirstName);

        firstNameTextField = new JTextField();
        firstNameTextField.setBounds(127, 28, 237, 20);
        contentPane.add(firstNameTextField);
        firstNameTextField.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblLastName.setBounds(10, 71, 91, 17);
        contentPane.add(lblLastName);

        lastNameTextField = new JTextField();
        lastNameTextField.setColumns(10);
        lastNameTextField.setBounds(127, 71, 237, 20);
        contentPane.add(lastNameTextField);

        JLabel lblFoodMenu = new JLabel("Food Menu:");
        lblFoodMenu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblFoodMenu.setBounds(10, 114, 91, 17);
        contentPane.add(lblFoodMenu);

        foodComboBox = new JComboBox<String>(foodItems);
        foodComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update the price label based on the selected food item
                int index = foodComboBox.getSelectedIndex();
                double price = foodPrices[index];
                priceLabel.setText("Rs. " + price);
            }
        });
        foodComboBox.setBounds(127, 114, 237, 20);
        contentPane.add(foodComboBox);
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPrice.setBounds(10, 157, 91, 17);
        contentPane.add(lblPrice);
        priceLabel = new JLabel("Rs. " + foodPrices[0]);
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        priceLabel = new JLabel("Rs. " + foodPrices[0]);
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        priceLabel.setBounds(127, 157, 237, 17);
        contentPane.add(priceLabel);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the user input values
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String foodItem = (String) foodComboBox.getSelectedItem();
                double price = foodPrices[foodComboBox.getSelectedIndex()];
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String datetime = formatter.format(date);

                // Insert the data into the database
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gui", "root", "Sh@101103");
                    PreparedStatement ps = con.prepareStatement("INSERT INTO bills (first_name, last_name, food_item, price, datetime) VALUES (?, ?, ?, ?, ?)");
                    ps.setString(1, firstName);
                    ps.setString(2, lastName);
                    ps.setString(3, foodItem);
                    ps.setDouble(4, price);
                    ps.setString(5, datetime);
                    ps.executeUpdate();
                    con.close();

                    // Display bill and ask for payment method
                    String billText = "Name: " + firstName + " " + lastName + "\nFood Item: " + foodItem + "\nPrice: Rs. " + price;
                    JOptionPane.showMessageDialog(null, billText, "Bill", JOptionPane.PLAIN_MESSAGE);
                    String[] paymentMethods = {"Cash", "UPI"};
                    int paymentIndex = JOptionPane.showOptionDialog(null, "Select Payment Method:", "Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, paymentMethods, paymentMethods[0]);
                    if (paymentIndex >= 0) {
                        String selectedPayment = paymentMethods[paymentIndex];
                        if (selectedPayment.equals("Cash")) {
                            JOptionPane.showMessageDialog(null, "Payment Method: " + selectedPayment, "Payment", JOptionPane.PLAIN_MESSAGE);
                        } else if (selectedPayment.equals("UPI")) {
                            UPIScannerGUI upiScanner = new UPIScannerGUI();
                            upiScanner.scanUpi();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        btnSubmit.setBounds(169, 207, 89, 23);
        contentPane.add(btnSubmit);
    }
}