package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeePanel extends JPanel {

    EmployeeDAO employeeDAO = new EmployeeDAO();
    DefaultTableModel tableModel;
    JTable table;
    JTextField idField, nameField, emailField, phoneField, designationField, salaryField;

    public EmployeePanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Designation", "Salary", "Status"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(174, 214, 241));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        designationField = new JTextField();
        salaryField = new JTextField();

        formPanel.add(new JLabel("ID (for Update/Delete):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Designation:"));
        formPanel.add(designationField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);

        JPanel buttonPanel = new JPanel();
        JButton refreshBtn = styledButton("Refresh", new Color(52, 152, 219));
        JButton addBtn = styledButton("Add", new Color(46, 204, 113));
        JButton updateBtn = styledButton("Update", new Color(241, 196, 15));
        JButton deleteBtn = styledButton("Mark as Resigned", new Color(231, 76, 60));

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
            Employee emp = new Employee(0, nameField.getText(), emailField.getText(),
                    phoneField.getText(), designationField.getText(),
                    Double.parseDouble(salaryField.getText()));
            boolean success = employeeDAO.insertEmployee(emp);
            showResult(success, "added");
            refreshTable();
        });

        updateBtn.addActionListener(e -> {
            int empId = Integer.parseInt(idField.getText());
            Employee emp = new Employee(empId, nameField.getText(), emailField.getText(),
                    phoneField.getText(), designationField.getText(),
                    Double.parseDouble(salaryField.getText()));
            boolean success = employeeDAO.updateEmployee(emp);
            showResult(success, "updated");
            refreshTable();
        });

        deleteBtn.addActionListener(e -> {
            int empId = Integer.parseInt(idField.getText());
            boolean success = employeeDAO.resignEmployee(empId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Employee marked as resigned.");
            } else {
                JOptionPane.showMessageDialog(this, employeeDAO.getLastError(),
                        "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
            refreshTable();
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                idField.setText(table.getValueAt(row, 0).toString());
                nameField.setText(table.getValueAt(row, 1).toString());
                emailField.setText(table.getValueAt(row, 2).toString());
                phoneField.setText(table.getValueAt(row, 3).toString());
                designationField.setText(table.getValueAt(row, 4).toString());
                salaryField.setText(table.getValueAt(row, 5).toString());
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
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee e : employees) {
            tableModel.addRow(new Object[]{e.getEmpId(), e.getName(), e.getEmail(),
                    e.getPhone(), e.getDesignation(), e.getSalary(), e.getStatus()});
        }
        clearFields();
    }

    void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        designationField.setText("");
        salaryField.setText("");
    }

    void showResult(boolean success, String action) {
        String msg = success ? "Employee " + action + " successfully!" : "Operation failed.";
        JOptionPane.showMessageDialog(this, msg);
    }
}