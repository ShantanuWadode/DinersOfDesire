import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AdminPanel {
    private JFrame frame;
    private JLabel imageLabel;
    private JButton ordersButton, billsButton, urgentOrderButton;

    public AdminPanel() {
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Background Image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Shantanu\\IdeaProjects\\Diners Of Desire\\src\\images\\Brigh Colorful Light Minimal with 3D Shape Creative Linktree Backgorund.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImage);
        imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 800, 600);

        // Buttons
        ordersButton = new JButton("Show Orders");
        ordersButton.setBounds(550, 200, 200, 50);
        ordersButton.setFont(new Font("Arial", Font.PLAIN, 16));
        ordersButton.setBackground(Color.WHITE);
        ordersButton.setForeground(Color.BLACK);
        ordersButton.setOpaque(false);
        ordersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                BillDetailsGUI.main(null);
            }
        });

        billsButton = new JButton("Show Bills");
        billsButton.setBounds(550, 280, 200, 50);
        billsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        billsButton.setBackground(Color.WHITE);
        billsButton.setForeground(Color.BLACK);
        billsButton.setOpaque(false);
        billsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                ImageDisplayGUI.main(null);
            }
        });


        urgentOrderButton = new JButton("Donate Food");
        urgentOrderButton.setBounds(550, 360, 200, 50);
        urgentOrderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        urgentOrderButton.setBackground(Color.WHITE);
        urgentOrderButton.setForeground(Color.BLACK);
        urgentOrderButton.setOpaque(false);
        // add action listener to the donate food button
        urgentOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // replace "http://example.com" with the URL you want to open
                    Desktop.getDesktop().browse(new URI("http://canteen121.000webhostapp.com/Cinema-Reservation/admin/donate.php"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add components to the frame
        imageLabel.add(ordersButton);
        imageLabel.add(billsButton);
        imageLabel.add(urgentOrderButton);
        frame.add(imageLabel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
