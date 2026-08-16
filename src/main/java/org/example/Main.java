package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static EmployeeDAO employeeDAO = new EmployeeDAO();
    static DepartmentDAO departmentDAO = new DepartmentDAO();
    static AttendanceDAO attendanceDAO = new AttendanceDAO();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== OFFICE MANAGEMENT SYSTEM =====");
            System.out.println("1. Employee Menu");
            System.out.println("2. Department Menu");
            System.out.println("3. Attendance Menu");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> employeeMenu();
                case 2 -> departmentMenu();
                case 3 -> attendanceMenu();
                case 0 -> System.out.println("Exiting... Bye!");
                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // ---------------- EMPLOYEE MENU ----------------
    static void employeeMenu() {
        int choice;

        do {
            System.out.println("\n--- Employee Menu ---");
            System.out.println("1. View All Employees");
            System.out.println("2. Add Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Mark Employee as Resigned");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewAllEmployees();
                case 2 -> addEmployee();
                case 3 -> updateEmployee();
                case 4 -> resignEmployee();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 0);
     while (choice != 0);
    }

    static void viewAllEmployees() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        System.out.println("\n--- All Employees ---");
        for (Employee e : employees) {
            System.out.println(e.getEmpId() + " | " + e.getName() + " | " + e.getEmail() + " | " +
                    e.getPhone() + " | " + e.getDesignation() + " | " + e.getSalary());
        }
    }

    static void addEmployee() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Designation: ");
        String designation = sc.nextLine();
        System.out.print("Salary: ");
        double salary = Double.parseDouble(sc.nextLine());

        Employee emp = new Employee(0, name, email, phone, designation, salary);
        boolean success = employeeDAO.insertEmployee(emp);
        System.out.println(success ? "Employee added successfully!" : "Failed to add employee.");
    }

    static void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Email: ");
        String email = sc.nextLine();
        System.out.print("New Phone: ");
        String phone = sc.nextLine();
        System.out.print("New Designation: ");
        String designation = sc.nextLine();
        System.out.print("New Salary: ");
        double salary = Double.parseDouble(sc.nextLine());

        Employee emp = new Employee(empId, name, email, phone, designation, salary);
        boolean success = employeeDAO.updateEmployee(emp);
        System.out.println(success ? "Employee updated successfully!" : "Update failed. Check Employee ID.");
    }

    static void resignEmployee() {
        System.out.print("Enter Employee ID to mark as resigned: ");
        int empId = Integer.parseInt(sc.nextLine());

        boolean success = employeeDAO.resignEmployee(empId);
        System.out.println(success ? "Employee marked as resigned!" : employeeDAO.getLastError());
    }

    // ---------------- DEPARTMENT MENU ----------------
    static void departmentMenu() {
        int choice;

        do {
            System.out.println("\n--- Department Menu ---");
            System.out.println("1. View All Departments");
            System.out.println("2. Add Department");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewAllDepartments();
                case 2 -> addDepartment();
                case 3 -> updateDepartment();
                case 4 -> resignEmployee();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 0);
    }

    static void viewAllDepartments() {
        List<Department> departments = departmentDAO.getAllDepartments();
        System.out.println("\n--- All Departments ---");
        for (Department d : departments) {
            System.out.println(d.getDeptId() + " | " + d.getDeptName() + " | " + d.getDeptHead());
        }
    }

    static void addDepartment() {
        System.out.print("Department Name: ");
        String name = sc.nextLine();
        System.out.print("Department Head: ");
        String head = sc.nextLine();

        Department dept = new Department(0, name, head);
        boolean success = departmentDAO.insertDepartment(dept);
        System.out.println(success ? "Department added successfully!" : "Failed to add department.");
    }

    static void updateDepartment() {
        System.out.print("Enter Department ID to update: ");
        int deptId = Integer.parseInt(sc.nextLine());

        System.out.print("New Department Name: ");
        String name = sc.nextLine();
        System.out.print("New Department Head: ");
        String head = sc.nextLine();

        Department dept = new Department(deptId, name, head);
        boolean success = departmentDAO.updateDepartment(dept);
        System.out.println(success ? "Department updated successfully!" : "Update failed. Check Department ID.");
    }

    static void deleteDepartment() {
        System.out.print("Enter Department ID to delete: ");
        int deptId = Integer.parseInt(sc.nextLine());

        boolean success = departmentDAO.deleteDepartment(deptId);
        System.out.println(success ? "Department deleted successfully!" : employeeDAO.getLastError());
    }

    // ---------------- ATTENDANCE MENU ----------------
    static void attendanceMenu() {
        int choice;

        do {
            System.out.println("\n--- Attendance Menu ---");
            System.out.println("1. Mark Login");
            System.out.println("2. Mark Logout");
            System.out.println("3. View Currently Logged In");
            System.out.println("4. View Attendance History (by Employee)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> markLogin();
                case 2 -> markLogout();
                case 3 -> viewCurrentlyLoggedIn();
                case 4 -> viewAttendanceHistory();
                case 0 -> System.out.println("Going back...");
                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 0);
    }

    static void markLogin() {
        System.out.print("Enter Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());
        boolean success = attendanceDAO.markLogin(empId);
        if (success) System.out.println("Login marked successfully!");
    }

    static void markLogout() {
        System.out.print("Enter Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());
        boolean success = attendanceDAO.markLogout(empId);
        if (success) System.out.println("Logout marked successfully!");
    }

    static void viewCurrentlyLoggedIn() {
        List<Attendance> list = attendanceDAO.getCurrentlyLoggedIn();
        System.out.println("\n--- Currently Logged In ---");
        if (list.isEmpty()) {
            System.out.println("No one is currently logged in.");
        }
        for (Attendance a : list) {
            System.out.println("Emp ID: " + a.getEmpId() + " | Login Time: " + a.getLoginTime());
        }
    }

    static void viewAttendanceHistory() {
        System.out.print("Enter Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        List<Attendance> list = attendanceDAO.getAttendanceByEmployee(empId);
        System.out.println("\n--- Attendance History ---");
        for (Attendance a : list) {
            String hours = attendanceDAO.calculateHoursWorked(a);
            System.out.println(a.getWorkDate() + " | Login: " + a.getLoginTime() +
                    " | Logout: " + a.getLogoutTime() + " | Hours Worked: " + hours);
        }
    }
}