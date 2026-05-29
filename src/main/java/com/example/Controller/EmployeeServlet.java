package com.example.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.DAO.EmployeeDAO;
import com.example.Model.Employee;

@WebServlet("/employee/*")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO empDAO;

    @Override
    public void init() { empDAO = new EmployeeDAO(); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getPathInfo();

        try {
            if (null == action) {
                listEmployees(request, response);
            } else switch (action) {
                case "/":
                    listEmployees(request, response);
                    break;
                case "/create":
                    if (request.getMethod().equalsIgnoreCase("POST")) {
                        insertEmployee(request, response);
                    } else {
                        request.getRequestDispatcher("/employee-form.jsp").forward(request, response);
                    }   break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEmployee(request, response);
                    break;
                case "/delete":
                    deleteEmployee(request, response);
                    break;
                case "/view":
                    viewEmployee(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException ex) { throw new ServletException(ex); }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> list = empDAO.selectAllEmployees();
        request.setAttribute("listEmployees", list);
        request.getRequestDispatcher("/employee-list.jsp").forward(request, response);
    }

    private void viewEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        Employee existingEmp = empDAO.selectEmployee(code);
        request.setAttribute("employee", existingEmp);
        request.getRequestDispatcher("/employee-detail.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        Employee existingEmp = empDAO.selectEmployee(code);
        request.setAttribute("employee", existingEmp);
        request.getRequestDispatcher("/employee-form.jsp").forward(request, response);
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String code = request.getParameter("empCode");
        String name = request.getParameter("fullName");
        String email = request.getParameter("email");
        empDAO.insertEmployee(new Employee(code, name, email));
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String code = request.getParameter("empCode");
        String name = request.getParameter("fullName");
        String email = request.getParameter("email");
        empDAO.updateEmployee(new Employee(code, name, email));
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String code = request.getParameter("code");
        empDAO.deleteEmployee(code);
        response.sendRedirect(request.getContextPath() + "/employees");
    }
}
