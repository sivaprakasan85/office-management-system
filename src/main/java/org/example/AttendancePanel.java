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
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        empIdField = new JTextField(5);
        JButton loginBtn = new JButton("Mark Login");
        JButton logoutBtn = new JButton("Mark Logout");
        JButton currentBtn = new JButton("Show Currently Logged In");

        topPanel.add(new JLabel("Emp ID:"));
        topPanel.add(empIdField);
        topPanel.add(loginBtn);
        topPanel.add(logoutBtn);
        topPanel.add(currentBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        historyEmpIdField = new JTextField(5);
        JButton historyBtn = new JButton("View History for this Emp ID");
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
}
