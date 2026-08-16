package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendancePanel extends JPanel {

    AttendanceDAO attendanceDAO = new AttendanceDAO();
    DefaultTableModel tableModel;
    JTable table;
    JTextField empIdField, historyEmpIdField;

    public AttendancePanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Emp ID", "Login Time", "Logout Time", "Work Date", "Hours Worked"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(174, 214, 241));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        empIdField = new JTextField(5);
        JButton loginBtn = styledButton("Mark Login", new Color(46, 204, 113));
        JButton logoutBtn = styledButton("Mark Logout", new Color(231, 76, 60));
        JButton currentBtn = styledButton("Show Currently Logged In", new Color(52, 152, 219));

        topPanel.add(new JLabel("Emp ID:"));
        topPanel.add(empIdField);
        topPanel.add(loginBtn);
        topPanel.add(logoutBtn);
        topPanel.add(currentBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        historyEmpIdField = new JTextField(5);
        JButton historyBtn = styledButton("View History for this Emp ID", new Color(155, 89, 182));
        bottomPanel.add(new JLabel("Emp ID:"));
        bottomPanel.add(historyEmpIdField);
        bottomPanel.add(historyBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            int empId = Integer.parseInt(empIdField.getText());
            boolean success = attendanceDAO.markLogin(empId);
            JOptionPane.showMessageDialog(this, success ? "Login marked!" : "Already logged in today or error.");
        });

        logoutBtn.addActionListener(e -> {
            int empId = Integer.parseInt(empIdField.getText());
            boolean success = attendanceDAO.markLogout(empId);
            JOptionPane.showMessageDialog(this, success ? "Logout marked!" : "No active login found today.");
        });

        currentBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            List<Attendance> list = attendanceDAO.getCurrentlyLoggedIn();
            for (Attendance a : list) {
                tableModel.addRow(new Object[]{a.getEmpId(), a.getLoginTime(), a.getLogoutTime(),
                        a.getWorkDate(), attendanceDAO.calculateHoursWorked(a)});
            }
        });

        historyBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            int empId = Integer.parseInt(historyEmpIdField.getText());
            List<Attendance> list = attendanceDAO.getAttendanceByEmployee(empId);
            for (Attendance a : list) {
                tableModel.addRow(new Object[]{a.getEmpId(), a.getLoginTime(), a.getLogoutTime(),
                        a.getWorkDate(), attendanceDAO.calculateHoursWorked(a)});
            }
        });
    }

    JButton styledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }
}