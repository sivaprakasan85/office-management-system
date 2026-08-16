package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private String lastError = "";

    public String getLastError() {
        return lastError;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("designation"),
                        rs.getDouble("salary")
                );
                emp.setStatus(rs.getString("status"));
                employees.add(emp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // Insert new employee
    public boolean insertEmployee(Employee emp) {
        String sql = "INSERT INTO Employees (name, email, phone, designation, salary) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getEmail());
            pstmt.setString(3, emp.getPhone());
            pstmt.setString(4, emp.getDesignation());
            pstmt.setDouble(5, emp.getSalary());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update existing employee by empId
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE Employees SET name = ?, email = ?, phone = ?, designation = ?, salary = ? WHERE emp_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getEmail());
            pstmt.setString(3, emp.getPhone());
            pstmt.setString(4, emp.getDesignation());
            pstmt.setDouble(5, emp.getSalary());
            pstmt.setInt(6, emp.getEmpId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mark employee as resigned instead of deleting (preserves attendance history)
    public boolean resignEmployee(int empId) {
        String sql = "UPDATE Employees SET status = 'RESIGNED' WHERE emp_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            int rows = pstmt.executeUpdate();
            lastError = "";
            return rows > 0;

        } catch (SQLException e) {
            lastError = "Failed to update status.";
            e.printStackTrace();
            return false;
        }
    }

    // Get only active employees (for normal views)
    public List<Employee> getActiveEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("designation"),
                        rs.getDouble("salary")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }
}