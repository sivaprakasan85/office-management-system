package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel extends JPanel {

    DepartmentDAO departmentDAO = new DepartmentDAO();
    DefaultTableModel tableModel;
    JTable table;
    JTextField idField, nameField, headField;

    public DepartmentPanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Head"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(174, 214, 241));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        headField = new JTextField();

        formPanel.add(new JLabel("ID (for Update/Delete):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Department Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Department Head:"));
        formPanel.add(headField);

        JPanel buttonPanel = new JPanel();
        JButton refreshBtn = styledButton("Refresh", new Color(52, 152, 219));
        JButton addBtn = styledButton("Add", new Color(46, 204, 113));
        JButton updateBtn = styledButton("Update", new Color(241, 196, 15));
        JButton deleteBtn = styledButton("Delete", new Color(231, 76, 60));

        buttonPanel.add(refreshBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTable();

        refreshBtn.addActionListener(e -> refreshTable());

        addBtn.addActionListener(e -> {
            Department dept = new Department(0, nameField.getText(), headField.getText());
            boolean success = departmentDAO.insertDepartment(dept);
            JOptionPane.showMessageDialog(this, success ? "Added!" : "Failed.");
            refreshTable();
        });

        updateBtn.addActionListener(e -> {
            int deptId = Integer.parseInt(idField.getText());
            Department dept = new Department(deptId, nameField.getText(), headField.getText());
            boolean success = departmentDAO.updateDepartment(dept);
            JOptionPane.showMessageDialog(this, success ? "Updated!" : "Failed.");
            refreshTable();
        });

        deleteBtn.addActionListener(e -> {
            int deptId = Integer.parseInt(idField.getText());
            boolean success = departmentDAO.deleteDepartment(deptId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Deleted!");
            } else {
                JOptionPane.showMessageDialog(this, departmentDAO.getLastError(),
                        "Delete Failed", JOptionPane.ERROR_MESSAGE);
            }
            refreshTable();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                idField.setText(table.getValueAt(row, 0).toString());
                nameField.setText(table.getValueAt(row, 1).toString());
                headField.setText(table.getValueAt(row, 2).toString());
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

    void refreshTable() {
        tableModel.setRowCount(0);
        List<Department> departments = departmentDAO.getAllDepartments();
        for (Department d : departments) {
            tableModel.addRow(new Object[]{d.getDeptId(), d.getDeptName(), d.getDeptHead()});
        }
        clearFields();
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        headField.setText("");
    }
}