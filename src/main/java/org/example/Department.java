package org.example;

public class Department {
    private int deptId;
    private String deptName;
    private String deptHead;

    public Department(int deptId, String deptName, String deptHead) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptHead = deptHead;
    }

    public int getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getDeptHead() { return deptHead; }
}
