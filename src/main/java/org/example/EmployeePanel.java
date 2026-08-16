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

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Designation", "Salary","Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form panel
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

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("mark as resigned");

        buttonPanel.add(refreshBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load data initially
        refreshTable();

        // Button actions
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

        // Click a row to auto-fill the form (for easy update/delete)
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

    void refreshTable() {
        tableModel.setRowCount(0); // clear existing rows
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee e : employees) {
            tableModel.addRow(new Object[]{e.getEmpId(), e.getName(), e.getEmail(),
                    e.getPhone(), e.getDesignation(), e.getSalary(), e.getStatus()});
        }
    }

    void showResult(boolean success, String action) {
        String msg = success ? "Employee " + action + " successfully!" : "Operation failed.";
        JOptionPane.showMessageDialog(this, msg);
    }
}