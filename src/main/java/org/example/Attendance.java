package org.example;

import java.sql.Timestamp;

public class Attendance {
    private int attendanceId;
    private int empId;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private java.sql.Date workDate;

    public Attendance(int attendanceId, int empId, Timestamp loginTime, Timestamp logoutTime, java.sql.Date workDate) {
        this.attendanceId = attendanceId;
        this.empId = empId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.workDate = workDate;
    }

    public int getAttendanceId() { return attendanceId; }
    public int getEmpId() { return empId; }
    public Timestamp getLoginTime() { return loginTime; }
    public Timestamp getLogoutTime() { return logoutTime; }
    public java.sql.Date getWorkDate() { return workDate; }
}
