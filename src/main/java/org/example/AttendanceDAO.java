package org.example;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // Mark login: insert a new row for today, only if not already logged in today
    public boolean markLogin(int empId) {
        if (isAlreadyLoggedInToday(empId)) {
            System.out.println("Employee already logged in today!");
            return false;
        }

        String sql = "INSERT INTO Attendance (emp_id, login_time, work_date) VALUES (?, NOW(), CURDATE())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mark logout: update today's row (the one with no logout_time yet) for this employee
    public boolean markLogout(int empId) {
        String sql = "UPDATE Attendance SET logout_time = NOW() " +
                "WHERE emp_id = ? AND work_date = CURDATE() AND logout_time IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                System.out.println("No active login found for today (already logged out or never logged in).");
            }
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if employee already has a login row for today
    private boolean isAlreadyLoggedInToday(int empId) {
        String sql = "SELECT * FROM Attendance WHERE emp_id = ? AND work_date = CURDATE()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // true if a row exists

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get everyone currently logged in (logged in today, not logged out yet)
    public List<Attendance> getCurrentlyLoggedIn() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE work_date = CURDATE() AND logout_time IS NULL";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getTimestamp("login_time"),
                        rs.getTimestamp("logout_time"),
                        rs.getDate("work_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get full attendance history for one employee
    public List<Attendance> getAttendanceByEmployee(int empId) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE emp_id = ? ORDER BY work_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getTimestamp("login_time"),
                        rs.getTimestamp("logout_time"),
                        rs.getDate("work_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Calculate hours worked for a specific attendance record
    public String calculateHoursWorked(Attendance att) {
        if (att.getLoginTime() == null || att.getLogoutTime() == null) {
            return "Incomplete (still logged in or no login recorded)";
        }

        Duration duration = Duration.between(att.getLoginTime().toInstant(), att.getLogoutTime().toInstant());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return hours + "h " + minutes + "m";
    }
}