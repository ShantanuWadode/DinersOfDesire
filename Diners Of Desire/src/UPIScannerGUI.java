import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;


public class UPIScannerGUI extends JFrame {

    private JLabel headingLabel, scannerImageLabel, screenshotLabel;
    private JButton uploadButton;
    private JPanel mainPanel, scannerPanel, buttonPanel, screenshotPanel;
    private Connection connection;

    public UPIScannerGUI() {
        // Set the frame properties
        setTitle("Scan and Pay");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the components
        headingLabel = new JLabel("Scan and Pay", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));

        ImageIcon scannerImageIcon = new ImageIcon("C:\\Users\\Shantanu\\IdeaProjects\\Diners Of Desire\\src\\images\\UPI_scanner.jpg");
        Image scannerImage = scannerImageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        scannerImageIcon = new ImageIcon(scannerImage);
        scannerImageLabel = new JLabel(scannerImageIcon);
        uploadButton = new JButton("Upload Screenshot");
        uploadButton.setBackground(Color.BLACK);
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);

        screenshotLabel = new JLabel();

        // Set the layout for the panels
        mainPanel = new JPanel(new BorderLayout());
        scannerPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new FlowLayout());
        screenshotPanel = new JPanel(new BorderLayout());

        // Add the components to the panels
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        scannerPanel.add(scannerImageLabel, c);
        buttonPanel.add(uploadButton);
        screenshotPanel.add(screenshotLabel, BorderLayout.CENTER);
        mainPanel.add(headingLabel, BorderLayout.NORTH);
        mainPanel.add(scannerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(screenshotPanel, BorderLayout.EAST);

        // Add the main panel to the frame
        add(mainPanel);

        // Add ActionListener to the upload button
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();

                // Show the file chooser dialog
                int result = fileChooser.showOpenDialog(UPIScannerGUI.this);

                // If the user selects a file, set the image to the screenshot label and store it in the database
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        FileInputStream fis = new FileInputStream(file);
                        String url = "jdbc:mysql://localhost:3306/gui";
                        String user = "root";
                        String password = "Sh@101103";
                        connection = DriverManager.getConnection(url, user, password);
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO images (image) VALUES (?)");
                        ps.setBinaryStream(1, fis, (int) file.length());
                        ps.executeUpdate();
                        screenshotLabel.setIcon(new ImageIcon(ImageIO.read(file)));
                        JOptionPane.showMessageDialog(null, "Order Placed!");
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new UPIScannerGUI();
    }

    public void scanUpi() {
    }
}
