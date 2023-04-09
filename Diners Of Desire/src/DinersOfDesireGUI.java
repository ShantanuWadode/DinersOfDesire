import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DinersOfDesireGUI extends JFrame implements ActionListener {

    public DinersOfDesireGUI() {
        JLabel heading = new JLabel("Diners Of Desire");
        heading.setFont(new Font("Arial", Font.BOLD, 50));
        heading.setForeground(Color.decode("#E8A0BF")); // set hex code color
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setBounds(0, 50, 800, 100); // set custom width

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Shantanu\\IdeaProjects\\Diners Of Desire\\src\\images\\backgound.jpg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(800, 600, Image.SCALE_DEFAULT); // set custom width and height
        imageIcon = new ImageIcon(scaledImage);
        JLabel backgroundImage = new JLabel(imageIcon);
        backgroundImage.setBounds(0, 0, 800, 600); // set custom width and height

        //Customer button
        JButton customerButton = new JButton("Customer Login");
        customerButton.setBounds(300, 250, 200, 50); // set custom position and size
        customerButton.setFont(new Font("Arial", Font.BOLD, 20));
        customerButton.addActionListener(this);

        //Admin button
        JButton adminButton = new JButton("Admin Login");
        adminButton.setBounds(300, 325, 200, 50); // set custom position and size
        adminButton.setFont(new Font("Arial", Font.BOLD, 20));
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AdminPanel.main(null);
            }
        });

        add(heading);
        add(customerButton);
        add(adminButton);
        add(backgroundImage);

        setSize(800, 600); // set custom width and height
        setLocationRelativeTo(null); // center the window on the screen
        setLayout(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        foodOrderForm.main(null);

    }



    public static void main(String[] args) {
        new DinersOfDesireGUI();
    }
}
