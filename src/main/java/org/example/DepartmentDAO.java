package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Departments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department dept = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("dept_head")
                );
                departments.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public boolean insertDepartment(Department dept) {
        String sql = "INSERT INTO Departments (dept_name, dept_head) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dept.getDeptName());
            pstmt.setString(2, dept.getDeptHead());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(Department dept) {
        String sql = "UPDATE Departments SET dept_name = ?, dept_head = ? WHERE dept_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dept.getDeptName());
            pstmt.setString(2, dept.getDeptHead());
            pstmt.setInt(3, dept.getDeptId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String lastError = "";

    public String getLastError() {
        return lastError;
    }

    public boolean deleteDepartment(int deptId) {
        String sql = "DELETE FROM Departments WHERE dept_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, deptId);
            lastError = "";
            return pstmt.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            lastError = "Cannot delete: employees are still assigned to this department. Reassign or remove them first.";
            return false;

        } catch (SQLException e) {
            lastError = "Delete failed due to a database error.";
            e.printStackTrace();
            return false;
        }
    }
}