package TaskManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class AdminTaskManagement extends JFrame {

    private JTextField taskIdField, taskNameField, taskDescriptionField, assignToUsernameField, viewUsernameField;
    private SessionFactory sessionFactory;

    public AdminTaskManagement() {
        setTitle("Task Management System - ADMIN");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up Hibernate
        setUpHibernate();

        // Define colors
        Color backgroundColor = new Color(250, 219, 216); // Updated background color
        Color buttonColor = new Color(231, 76, 60); // Updated button color

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title and subtitle
        JLabel lblTitle = createStyledLabel("Admin Task Management", 24, new Color(0, 0, 0));
        JLabel lblSubtitle = createStyledLabel("Manage tasks for your team!", 16, new Color(120, 120, 120));
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 0;
        mainPanel.add(lblTitle, gbc);
        gbc.gridy = 1;
        mainPanel.add(lblSubtitle, gbc);

        // Task ID
        JLabel taskIdLabel = createStyledLabel("Task ID", 14, Color.BLACK);
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(taskIdLabel, gbc);
        taskIdField = createTextField("Enter Task ID");
        gbc.gridy = 3;
        mainPanel.add(taskIdField, gbc);

        // Task Name
        JLabel taskNameLabel = createStyledLabel("Task Name", 14, Color.BLACK);
        gbc.gridy = 4;
        mainPanel.add(taskNameLabel, gbc);
        taskNameField = createTextField("Enter Task Name");
        gbc.gridy = 5;
        mainPanel.add(taskNameField, gbc);

        // Task Description
        JLabel taskDescriptionLabel = createStyledLabel("Task Description", 14, Color.BLACK);
        gbc.gridy = 6;
        mainPanel.add(taskDescriptionLabel, gbc);
        taskDescriptionField = createTextField("Enter Task Description");
        gbc.gridy = 7;
        mainPanel.add(taskDescriptionField, gbc);

        // Assign To (Username)
        JLabel assignToLabel = createStyledLabel("Assign To (Username)", 14, Color.BLACK);
        gbc.gridy = 8;
        mainPanel.add(assignToLabel, gbc);
        assignToUsernameField = createTextField("Enter Username");
        gbc.gridy = 9;
        mainPanel.add(assignToUsernameField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        JButton btnInsert = createStyledButton("Insert Task", buttonColor);
        JButton btnUpdate = createStyledButton("Update Task", buttonColor);
        JButton btnRemove = createStyledButton("Remove Task", buttonColor);
        JButton btnView = createStyledButton("View Tasks", buttonColor);
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnView);
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        // View Tasks section
        JLabel viewUsernameLabel = createStyledLabel("View Tasks Assigned To (Username)", 14, Color.BLACK);
        gbc.gridy = 11;
        mainPanel.add(viewUsernameLabel, gbc);
        viewUsernameField = createTextField("Enter Username");
        gbc.gridy = 12;
        mainPanel.add(viewUsernameField, gbc);

        // Add main panel to frame
        add(mainPanel);

        // Button actions
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertTask();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewTasks();
            }
        });
    }

    private void setUpHibernate() {
        try {
            // Set up Hibernate
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Hibernate setup successful!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error setting up Hibernate: " + e.getMessage(), "Hibernate Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void insertTask() {
        String taskId = taskIdField.getText();
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        String assignToUsername = assignToUsernameField.getText();

        Task task = new Task();
        task.setTaskId(taskId);
        task.setTaskName(taskName);
        task.setTaskDescription(taskDescription);
        task.setAssignedTo(assignToUsername);

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            JOptionPane.showMessageDialog(this, "Task inserted successfully!", "Insert Task", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(this, "Error inserting task: " + e.getMessage(), "Insert Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void updateTask() {
        String taskId = taskIdField.getText();
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        String assignToUsername = assignToUsernameField.getText();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            if (task != null) {
                task.setTaskName(taskName);
                task.setTaskDescription(taskDescription);
                task.setAssignedTo(assignToUsername);
                session.update(task);
                transaction.commit();
                JOptionPane.showMessageDialog(this, "Task updated successfully!", "Update Task", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Task not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(this, "Error updating task: " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void removeTask() {
        String taskId = taskIdField.getText();

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            if (task != null) {
                session.delete(task);
                transaction.commit();
                JOptionPane.showMessageDialog(this, "Task removed successfully!", "Remove Task", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Task not found.", "Remove Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            JOptionPane.showMessageDialog(this, "Error removing task: " + e.getMessage(), "Remove Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void viewTasks() {
        String username = viewUsernameField.getText();

        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Task WHERE assignedTo = :username";
            Query<Task> query = session.createQuery(hql, Task.class);
            query.setParameter("username", username);
            List<Task> tasks = query.list();

            if (tasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tasks assigned to this user.", "View Tasks", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder taskInfo = new StringBuilder();
                for (Task task : tasks) {
                    taskInfo.append("Task ID: ").append(task.getTaskId())
                            .append(", Name: ").append(task.getTaskName())
                            .append(", Description: ").append(task.getTaskDescription())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, taskInfo.toString(), "Tasks Assigned to " + username, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error retrieving tasks: " + e.getMessage(), "View Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        textField.setToolTipText(placeholder);
        textField.setPreferredSize(new Dimension(300, 40));
        return textField;
    }

    private JLabel createStyledLabel(String text, int fontSize, Color color) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(color);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminTaskManagement().setVisible(true));
    }
}
