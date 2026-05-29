<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách nhân viên</title>
    <script>
        function confirmDelete() { return confirm("Bạn chắc chắn muốn xóa nhân viên này?"); }
    </script>
</head>
<body>
    <h2>Danh sách nhân viên</h2>
    <a href="${pageContext.request.contextPath}/employees/create">Thêm mới</a>
    <table border="1" cellpadding="5">
        <tr>
            <th>Code</th><th>Họ tên</th><th>Email</th><th>Actions</th>
        </tr>
        <c:forEach var="emp" items="${listEmployees}">
            <tr>
                <td>${emp.empCode}</td>
                <td>${emp.fullName}</td>
                <td>${emp.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/employees/view?code=${emp.empCode}">View</a> | 
                    <a href="${pageContext.request.contextPath}/employees/edit?code=${emp.empCode}">Edit</a> | 
                    <a href="${pageContext.request.contextPath}/employees/delete?code=${emp.empCode}" onclick="return confirmDelete();">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>