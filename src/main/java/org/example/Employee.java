package org.example;

public class Employee {
    private int empId;
    private String name;
    private String email;
    private String phone;
    private String designation;
    private double salary;
    private String status;

    public Employee(int empId, String name, String email, String phone, String designation, double salary) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.designation = designation;
        this.salary = salary;
        this.status = "ACTIVE";
    }

    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDesignation() { return designation; }
    public double getSalary() { return salary; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}