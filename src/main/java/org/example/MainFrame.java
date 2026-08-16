package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Office Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen

        // Header banner
        JLabel header = new JLabel("OFFICE MANAGEMENT SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(41, 128, 185)); // nice blue
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(236, 240, 241));

        tabbedPane.addTab("👤 Employees", new EmployeePanel());
        tabbedPane.addTab("🏢 Departments", new DepartmentPanel());
        tabbedPane.addTab("🕒 Attendance", new AttendancePanel());

        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
