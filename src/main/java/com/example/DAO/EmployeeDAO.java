package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Employee;

public class EmployeeDAO {
    @SuppressWarnings("FieldMayBeFinal")
    private String jdbcURL = "jdbc:sqlserver://localhost:1433;encrypt=false;trustServerCertificate=true;characterEncoding=UTF-8";
    @SuppressWarnings("FieldMayBeFinal")
    private String jdbcUsername = "sa";
    @SuppressWarnings("FieldMayBeFinal")
    private String jdbcPassword = "123456Aa@"; 

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            try (Statement s = connection.createStatement()) {
                s.executeUpdate("IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'Lab4') CREATE DATABASE Lab4");
                s.executeUpdate("USE Lab4");
                s.executeUpdate("IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[employees]') AND type in (N'U')) "
                        + "CREATE TABLE employees ("
                        + "emp_code VARCHAR(50) PRIMARY KEY, "
                        + "full_name NVARCHAR(100) NOT NULL, " // Dùng NVARCHAR để không bị lỗi font Tiếng Việt
                        + "email VARCHAR(100) NOT NULL)");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("THIẾU DRIVER SQL SERVER: Kiểm tra lại file pom.xml xem đã update Maven chưa!", e);
        } catch (SQLException e) {
            throw new SQLException("LỖI KẾT NỐI SQL SERVER: Kiểm tra xem đã bật SQL Server (MSSQLSERVER) chưa, hoặc sai user/pass/port! Chi tiết: " + e.getMessage(), e);
        }
        return connection;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public List<Employee> selectAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Employee(rs.getString("emp_code"), rs.getString("full_name"), rs.getString("email")));
            }
        } catch (Exception e) {
            System.err.println("=== LỖI TẠI SELECT ALL EMPLOYEES ===");
            e.printStackTrace();
            throw new RuntimeException("Lỗi truy vấn SQL: " + e.getMessage(), e);
        }
        return list;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Employee selectEmployee(String code) {
        String query = "SELECT * FROM employees WHERE emp_code = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(rs.getString("emp_code"), rs.getString("full_name"), rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void insertEmployee(Employee emp) throws SQLException {
        String query = "INSERT INTO employees (emp_code, full_name, email) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, emp.getEmpCode());
            ps.setString(2, emp.getFullName());
            ps.setString(3, emp.getEmail());
            ps.executeUpdate();
        }
    }

    public boolean updateEmployee(Employee emp) throws SQLException {
        String query = "UPDATE employees SET full_name = ?, email = ? WHERE emp_code = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, emp.getFullName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getEmpCode());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(String code) throws SQLException {
        String query = "DELETE FROM employees WHERE emp_code = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, code);
            return ps.executeUpdate() > 0;
        }
    }
}