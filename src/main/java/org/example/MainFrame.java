package org.example;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Office Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Employees", new EmployeePanel());
        tabbedPane.addTab("Departments", new DepartmentPanel());
        tabbedPane.addTab("Attendance", new AttendancePanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
