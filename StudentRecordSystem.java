import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentRecordSystem extends JFrame {

    Connection conn;
    Statement stmt;
    PreparedStatement pstmt;

    public StudentRecordSystem() {
        connectDatabase();
        loginScreen(); // Start with login screen
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_records", "root", "admin@123");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loginScreen() {
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userTypeLabel = new JLabel("Select User Type:");
        userTypeLabel.setBounds(50, 50, 150, 30);
        frame.add(userTypeLabel);

        String[] userTypes = {"Admin", "Teacher"};
        JComboBox<String> userTypeDropdown = new JComboBox<>(userTypes);
        userTypeDropdown.setBounds(200, 50, 150, 30);
        frame.add(userTypeDropdown);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 100, 30);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 100, 200, 30);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 30);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 200, 100, 30);
        frame.add(loginButton);

        loginButton.addActionListener(e -> {
            String userType = userTypeDropdown.getSelectedItem().toString();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userType.equals("Admin")) {
                if (adminLogin(username, password)) {
                    frame.dispose();
                    adminDashboard();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Admin Credentials");
                }
            } else if (userType.equals("Teacher")) {
                if (teacherLogin(username, password)) {
                    frame.dispose();
                    teacherDashboard(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Teacher Credentials");
                }
            }
        });

        frame.setVisible(true);
    }

    private boolean adminLogin(String username, String password) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM admin WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean teacherLogin(String username, String password) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM teachers WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void adminDashboard() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        int buttonWidth = 200;  // Set a common width for all buttons
        int buttonHeight = 30;  // Set a common height for all buttons

        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        JButton addTeacherButton = new JButton("Add Teacher");
        addTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTeacherButton.setPreferredSize(buttonSize);  // Set preferred size
        addTeacherButton.setMaximumSize(buttonSize);    // Force maximum size
        addTeacherButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(addTeacherButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space

        JButton viewTeacherButton = new JButton("View Teacher(s)");
        viewTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewTeacherButton.setPreferredSize(buttonSize);  // Set preferred size
        viewTeacherButton.setMaximumSize(buttonSize);    // Force maximum size
        viewTeacherButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(viewTeacherButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space

        JButton searchTeacherButton = new JButton("Search Teacher");
        searchTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchTeacherButton.setPreferredSize(buttonSize);  // Set preferred size
        searchTeacherButton.setMaximumSize(buttonSize);    // Force maximum size
        searchTeacherButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(searchTeacherButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space

        JButton updateTeacherButton = new JButton("Update Teacher(s) Info.");
        updateTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTeacherButton.setPreferredSize(buttonSize);  // Set preferred size
        updateTeacherButton.setMaximumSize(buttonSize);    // Force maximum size
        updateTeacherButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(updateTeacherButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space

        JButton adminLogout = new JButton("Logout (Not Working)");
        adminLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminLogout.setPreferredSize(buttonSize);  // Set preferred size
        adminLogout.setMaximumSize(buttonSize);    // Force maximum size
        adminLogout.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(adminLogout);

        
        frame.add(contentPanel, BorderLayout.CENTER);

        addTeacherButton.addActionListener(e -> openAddTeacherWindow());
        viewTeacherButton.addActionListener(e -> openViewTeacherWindow());
        searchTeacherButton.addActionListener(e -> openSearchTeacherWindow());
        updateTeacherButton.addActionListener(e -> openUpdateTeacherWindow());

        frame.setVisible(true);
    }

    private void teacherDashboard(String username) {
        JFrame frame = new JFrame("Teacher Dashboard");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Teacher Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);

        int buttonWidth = 200;  // Set a common width for all buttons
        int buttonHeight = 30;  // Set a common height for all buttons

        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        
        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudentButton.setPreferredSize(buttonSize);  // Set preferred size
        addStudentButton.setMaximumSize(buttonSize);    // Force maximum size
        addStudentButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(addStudentButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space
        
        JButton viewStudentButton = new JButton("View Student(s)");
        viewStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStudentButton.setPreferredSize(buttonSize);  // Set preferred size
        viewStudentButton.setMaximumSize(buttonSize);    // Force maximum size
        viewStudentButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(viewStudentButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space
        
        JButton searchStudentButton = new JButton("Search Student");
        searchStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchStudentButton.setPreferredSize(buttonSize);  // Set preferred size
        searchStudentButton.setMaximumSize(buttonSize);    // Force maximum size
        searchStudentButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(searchStudentButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space
        
        JButton updateStudentButton = new JButton("Update Student");
        updateStudentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateStudentButton.setPreferredSize(buttonSize);  // Set preferred size
        updateStudentButton.setMaximumSize(buttonSize);    // Force maximum size
        updateStudentButton.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(updateStudentButton);
        contentPanel.add(Box.createVerticalStrut(10));  // Add vertical space
        
        JButton teacherLogout = new JButton("Logout (Not Working)");
        teacherLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        teacherLogout.setPreferredSize(buttonSize);  // Set preferred size
        teacherLogout.setMaximumSize(buttonSize);    // Force maximum size
        teacherLogout.setMinimumSize(buttonSize);    // Force minimum size
        contentPanel.add(teacherLogout);
    
        frame.add(contentPanel, BorderLayout.CENTER);

        addStudentButton.addActionListener(e -> openAddStudentWindow());
        viewStudentButton.addActionListener(e -> openViewStudentsWindow());
        searchStudentButton.addActionListener(e -> openSearchStudentWindow());
        updateStudentButton.addActionListener(e -> openUpdateStudentWindow());

        frame.setVisible(true);
    }

    private void openAddTeacherWindow() {
        JFrame frame = new JFrame("Add Teacher");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(20, 100, 100, 30);
        frame.add(subjectLabel);

        JTextField subjectField = new JTextField();
        subjectField.setBounds(120, 100, 150, 30);
        frame.add(subjectField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(20, 140, 100, 30);
        frame.add(departmentLabel);

        JTextField departmentField = new JTextField();
        departmentField.setBounds(120, 140, 150, 30);
        frame.add(departmentField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 180, 100, 30);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(120, 180, 150, 30);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, 100, 30);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 220, 150, 30);
        frame.add(passwordField);

        JButton addButton = new JButton("Add Teacher");
        addButton.setBounds(120, 260, 150, 30);
        frame.add(addButton);

        addButton.addActionListener(e -> {
            try {
                pstmt = conn.prepareStatement("INSERT INTO teachers VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                pstmt.setString(2, nameField.getText());
                pstmt.setString(3, subjectField.getText());
                pstmt.setString(4, departmentField.getText());
                pstmt.setString(5, usernameField.getText());
                pstmt.setString(6, new String(passwordField.getPassword()));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Teacher Added Successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }

    private void openViewTeacherWindow() {
    	JFrame frame = new JFrame("View Teachers");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Define column names
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Subject", "Department", "Username", "Password"});

        // Fetch data and populate the table
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM teachers");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("subject"),
                    rs.getString("department"),
                    rs.getString("username"),
                    rs.getString("password")
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
    
    private void openSearchTeacherWindow() {
    	JFrame frame = new JFrame("Search Teacher");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("Enter Teacher ID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(searchButton);

        // Create a table model and table
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Define column names
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Name", "Subject", "Department", "Username", "Password"});

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        searchButton.addActionListener(e -> {
            try {
                pstmt = conn.prepareStatement("SELECT * FROM teachers WHERE id = ?");
                pstmt.setInt(1, Integer.parseInt(idField.getText()));
                ResultSet rs = pstmt.executeQuery();
                tableModel.setRowCount(0); // Clear previous results
                if (rs.next()) {
                    tableModel.addRow(new Object[]{
                    		rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("subject"),
                            rs.getString("department"),
                            rs.getString("username"),
                            rs.getString("password")
                    });
                } else {
                    JOptionPane.showMessageDialog(frame, "Teacher not found!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openUpdateTeacherWindow() {
        JFrame frame = new JFrame("Update Teacher");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        frame.setLayout(null);

        JLabel idLabel = new JLabel("Enter Teacher ID:");
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

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(20, 100, 100, 30);
        frame.add(subjectLabel);

        JTextField subjectField = new JTextField();
        subjectField.setBounds(120, 100, 150, 30);
        frame.add(subjectField);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setBounds(20, 140, 100, 30);
        frame.add(deptLabel);

        JTextField deptField = new JTextField();
        deptField.setBounds(120, 140, 150, 30);
        frame.add(deptField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 180, 100, 30);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(120, 180, 150, 30);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, 100, 30);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(120, 220, 150, 30);
        frame.add(passwordField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(100, 340, 100, 30);
        frame.add(updateButton);

        updateButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Teacher ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                pstmt = conn.prepareStatement("UPDATE teachers SET name=?, subject=?, department=?, username=?, password=? WHERE id=?");
                pstmt.setString(1, nameField.getText());
                pstmt.setString(2, subjectField.getText());
                pstmt.setString(3, deptField.getText());
                pstmt.setString(4, usernameField.getText());
                pstmt.setString(5, new String(passwordField.getPassword())); // Converting char[] to String for password
                pstmt.setInt(6, Integer.parseInt(idField.getText())); // Teacher ID
                int rowsUpdated = pstmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(frame, "Teacher Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "No teacher found with the given ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error updating teacher: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
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
        new StudentRecordSystem();
    }
}