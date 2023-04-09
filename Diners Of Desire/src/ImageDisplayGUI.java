import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.*;

public class ImageDisplayGUI extends JFrame {

    private JPanel mainPanel;

    public ImageDisplayGUI() {
        // Set the frame properties
        setTitle("Image Display");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the components
        mainPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the components to the frame
        add(scrollPane);

        // Fetch the images from the database and display them in the panel
        try {
            String url = "jdbc:mysql://localhost:3306/gui";
            String user = "root";
            String password = "Sh@101103";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT image FROM images");

            while (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image");
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
                Image resizedImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(resizedImage));
                mainPanel.add(label);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        new ImageDisplayGUI();
    }
}
