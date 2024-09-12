package TaskManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class RegistrationUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    private static final Configuration CONFIGURATION = new Configuration().configure().addAnnotatedClass(User.class);
    private static final SessionFactory SESSION_FACTORY = CONFIGURATION.buildSessionFactory();

    public RegistrationUI() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define light green color used in TaskManagementSystemHomePage
        Color lightGreen = new Color(250, 219, 216);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(lightGreen); // Set background to light green
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username field
        JLabel lblUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password field
        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPassword, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Role selection
        JLabel lblRole = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblRole, gbc);

        String[] roles = {"student", "admin"};
        roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        // Register button
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size
        btnRegister.setPreferredSize(new Dimension(160, 50)); // Larger button size
        btnRegister.setBackground(new Color(231, 76, 60)); // Dark green background for button
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Adjust padding
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255))); // White border

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnRegister, gbc);

        add(panel);

        // Register button action
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                Session session = SESSION_FACTORY.openSession();
                Transaction tx = session.beginTransaction();

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);

                session.save(user);
                tx.commit();
                session.close();

                JOptionPane.showMessageDialog(null, "User registered successfully!");
                new LoginUI().setVisible(true);
                dispose();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationUI().setVisible(true));
    }
}
