<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${employee != null ? 'Sửa nhân viên' : 'Thêm nhân viên'}</title>
</head>
<body>
    <h2>${employee != null ? 'Sửa nhân viên' : 'Thêm nhân viên'}</h2>
    <form action="${pageContext.request.contextPath}/employees/${employee != null ? 'update' : 'create'}" method="POST">
        <p>
            Mã nhân viên:<br>
            <input type="text" name="empCode" value="${employee.empCode}" required ${employee != null ? 'readonly' : ''}>
        </p>
        <p>
            Họ và tên:<br>
            <input type="text" name="fullName" value="${employee.fullName}" required>
        </p>
        <p>
            Email:<br>
            <input type="email" name="email" value="${employee.email}" required>
        </p>
        <button type="submit">${employee != null ? 'Lưu' : 'Thêm'}</button>
        <a href="${pageContext.request.contextPath}/employees">Quay lại</a>
    </form>
</body>
</html>