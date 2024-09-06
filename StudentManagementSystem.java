import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {

    Connection conn;
    Statement stmt;
    PreparedStatement pstmt;

    public StudentManagementSystem() {
    	 connectDatabase();

         // Create main menu
         setTitle("Student Management System");
         setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(new BorderLayout());

         // Create a panel for the content
         JPanel contentPanel = new JPanel();
         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
         contentPanel.setOpaque(false);

         // Add title
         JLabel titleLabel = new JLabel("Student Management System");
         titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
         titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
         titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
         contentPanel.add(titleLabel);

         // Create buttons
         JButton addButton = new JButton("Add Student");
         JButton viewButton = new JButton("View Students");
         JButton searchButton = new JButton("Search Student");
         JButton updateButton = new JButton("Update Student");

         // Customize buttons
         Dimension buttonSize = new Dimension(200, 40);
         addButton.setPreferredSize(buttonSize);
         viewButton.setPreferredSize(buttonSize);
         searchButton.setPreferredSize(buttonSize);
         updateButton.setPreferredSize(buttonSize);

         addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
         updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

         contentPanel.add(addButton);
         contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
         contentPanel.add(viewButton);
         contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
         contentPanel.add(searchButton);
         contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
         contentPanel.add(updateButton);

         // Center the content panel
         add(contentPanel, BorderLayout.CENTER);

         // Add ActionListeners
         addButton.addActionListener(e -> openAddStudentWindow());
         viewButton.addActionListener(e -> openViewStudentsWindow());
         searchButton.addActionListener(e -> openSearchStudentWindow());
         updateButton.addActionListener(e -> openUpdateStudentWindow());

         setVisible(true);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_records", "root", "admin@123");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openAddStudentWindow() {
        JFrame frame = new JFrame("Add Student");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 100, 30);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120, 20, 150, 30);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 30);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 60, 150, 30);
        frame.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 100, 30);
        frame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(120, 100, 150, 30);
        frame.add(ageField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(20, 140, 100, 30);
        frame.add(rollLabel);

        JTextField rollField = new JTextField();
        rollField.setBounds(120, 140, 150, 30);
        frame.add(rollField);

        JLabel enrollLabel = new JLabel("Enrollment No:");
        enrollLabel.setBounds(20, 180, 100, 30);
        frame.add(enrollLabel);

        JTextField enrollField = new JTextField();
        enrollField.setBounds(120, 180, 150, 30);
        frame.add(enrollField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(20, 220, 100, 30);
        frame.add(courseLabel);

        JTextField courseField = new JTextField();
        courseField.setBounds(120, 220, 150, 30);
        frame.add(courseField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(20, 260, 100, 30);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setBounds(120, 260, 150, 30);
        frame.add(yearField);

        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setBounds(20, 300, 100, 30);
        frame.add(sectionLabel);

        JTextField sectionField = new JTextField();
        sectionField.setBounds(120, 300, 150, 30);
        frame.add(sectionField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(100, 340, 100, 30);
        frame.add(addButton);

        addButton.addActionListener(e -> {
            try {
                pstmt = conn.prepareStatement("INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                pstmt.setString(2, nameField.getText());
                pstmt.setInt(3, Integer.parseInt(ageField.getText()));
                pstmt.setInt(4, Integer.parseInt(rollField.getText()));
                pstmt.setInt(5, Integer.parseInt(enrollField.getText()));
                pstmt.setString(6, courseField.getText());
                pstmt.setString(7, yearField.getText());
                pstmt.setString(8, sectionField.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Student Added Successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }

    private void openViewStudentsWindow() {
    	JFrame frame = new JFrame("View Students");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Define column names
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Age", "Roll No", "Enrollment No", "Course", "Year", "Section"});

        // Fetch data and populate the table
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("roll_number"),
                    rs.getInt("enrollment_number"),
                    rs.getString("course"),
                    rs.getString("year"),
                    rs.getString("section")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Add table to a scroll pane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openSearchStudentWindow() {
    	JFrame frame = new JFrame("Search Student");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("Enter Student ID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(searchButton);

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Define column names
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Age", "Roll No", "Enrollment No", "Course", "Year", "Section"});

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        searchButton.addActionListener(e -> {
            try {
                pstmt = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                ResultSet rs = pstmt.executeQuery();
                tableModel.setRowCount(0); // Clear previous results
                if (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("roll_number"),
                        rs.getInt("enrollment_number"),
                        rs.getString("course"),
                        rs.getString("year"),
                        rs.getString("section")
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openUpdateStudentWindow() {
        JFrame frame = new JFrame("Update Student");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setLayout(null);

        JLabel idLabel = new JLabel("Enter Student ID:");
        idLabel.setBounds(20, 20, 150, 30);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(150, 20, 100, 30);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 30);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(120, 60, 150, 30);
        frame.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 100, 30);
        frame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(120, 100, 150, 30);
        frame.add(ageField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(20, 140, 100, 30);
        frame.add(rollLabel);

        JTextField rollField = new JTextField();
        rollField.setBounds(120, 140, 150, 30);
        frame.add(rollField);

        JLabel enrollLabel = new JLabel("Enrollment No:");
        enrollLabel.setBounds(20, 180, 100, 30);
        frame.add(enrollLabel);

        JTextField enrollField = new JTextField();
        enrollField.setBounds(120, 180, 150, 30);
        frame.add(enrollField);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(20, 220, 100, 30);
        frame.add(courseLabel);

        JTextField courseField = new JTextField();
        courseField.setBounds(120, 220, 150, 30);
        frame.add(courseField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(20, 260, 100, 30);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setBounds(120, 260, 150, 30);
        frame.add(yearField);

        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setBounds(20, 300, 100, 30);
        frame.add(sectionLabel);

        JTextField sectionField = new JTextField();
        sectionField.setBounds(120, 300, 150, 30);
        frame.add(sectionField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(100, 340, 100, 30);
        frame.add(updateButton);

        updateButton.addActionListener(e -> {
            try {
                pstmt = conn.prepareStatement("UPDATE students SET name=?, age=?, roll_number=?, enrollment_number=?, course=?, year=?, section=? WHERE id=?");
                pstmt.setString(1, nameField.getText());
                pstmt.setInt(2, Integer.parseInt(ageField.getText()));
                pstmt.setInt(3, Integer.parseInt(rollField.getText()));
                pstmt.setInt(4, Integer.parseInt(enrollField.getText()));
                pstmt.setString(5, courseField.getText());
                pstmt.setString(6, yearField.getText());
                pstmt.setString(7, sectionField.getText());
                pstmt.setInt(8, Integer.parseInt(idField.getText()));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Student Updated Successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StudentManagementSystem();
    }
}
