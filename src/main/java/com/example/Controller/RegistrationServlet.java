package com.example.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.Util.ValidationUtil;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    response.sendRedirect(request.getContextPath() + "/register.jsp");
}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String tuoiStr = request.getParameter("tuoi");
        String gioiTinh = request.getParameter("gioiTinh");
        String chuyenNganh = request.getParameter("chuyenNganh");

        Map<String, String> errors = new HashMap<>();

        if (hoTen == null || hoTen.trim().isEmpty()) {
            errors.put("hoTen", "Họ tên không được để trống!");
        }
        if (email == null || !ValidationUtil.isValidEmail(email)) {
            errors.put("email", "Email không đúng định dạng (phải chứa @)!");
        }
        
        if (tuoiStr != null && !tuoiStr.trim().isEmpty()) {
            try {
                int tuoi = Integer.parseInt(tuoiStr);
                if (tuoi < 18 || tuoi > 60) {
                    errors.put("tuoi", "Tuổi phải từ 18 đến 60!");
                }
            } catch (NumberFormatException e) {
                errors.put("tuoi", "Tuổi phải là một số hợp lệ!");
            }
        }

        if (gioiTinh == null) {
            errors.put("gioiTinh", "Vui lòng chọn giới tính!");
        }
        if (chuyenNganh == null || chuyenNganh.isEmpty()) {
            errors.put("chuyenNganh", "Vui lòng chọn chuyên ngành!");
        }

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.setAttribute("hoTen", hoTen);
            request.setAttribute("email", email);
            request.setAttribute("gioiTinh", gioiTinh);
            request.setAttribute("chuyenNganh", chuyenNganh);
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
    }
}