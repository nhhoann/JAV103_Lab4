<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Form Đăng Ký Người Dùng</title>
    <style>
        * { box-sizing: border-box; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; }
        body { background-color: #f4f7f6; display: flex; justify-content: center; align-items: center; min-height: 100vh; padding: 20px; }
        .container { background: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 100%; max-width: 450px; border-top: 5px solid #f26f21; }
        h2 { color: #333; margin-bottom: 20px; text-align: center; font-size: 24px; font-weight: 600; }
        .form-group { margin-bottom: 18px; }
        label { display: block; margin-bottom: 6px; color: #555; font-weight: 500; font-size: 14px; }
        input[type="text"], select { width: 100%; padding: 10px 12px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px; transition: all 0.3s; }
        input[type="text"]:focus, select:focus { border-color: #f26f21; outline: none; box-shadow: 0 0 5px rgba(242, 111, 33, 0.2); }
        .radio-group { display: flex; gap: 15px; margin-top: 5px; }
        .radio-group label { display: inline-flex; align-items: center; gap: 5px; cursor: pointer; }
        .error { color: #e74c3c; font-size: 12px; margin-top: 4px; font-weight: 500; }
        button { width: 100%; padding: 12px; background: #2f9f45; border: none; color: white; font-size: 16px; font-weight: bold; border-radius: 4px; cursor: pointer; transition: background 0.3s; margin-top: 10px; }
        button:hover { background: #248235; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Form Đăng Ký Người Dùng</h2>
        <form action="${pageContext.request.contextPath}/RegistrationServlet" method="POST">
            <div class="form-group">
                <label>Họ tên:</label>
                <input type="text" name="hoTen" value="${param.hoTen}">
                <div class="error">${errors.hoTen}</div>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="text" name="email" value="${param.email}">
                <div class="error">${errors.email}</div>
            </div>

            <div class="form-group">
                <label>Tuổi:</label>
                <input type="text" name="tuoi" value="${param.tuoi}">
                <div class="error">${errors.tuoi}</div>
            </div>

            <div class="form-group">
                <label>Giới tính:</label>
                <div class="radio-group">
                    <label><input type="radio" name="gioiTinh" value="Nam" ${param.gioiTinh == 'Nam' ? 'checked' : ''}> Nam</label>
                    <label><input type="radio" name="gioiTinh" value="Nữ" ${param.gioiTinh == 'Nữ' ? 'checked' : ''}> Nữ</label>
                </div>
                <div class="error">${errors.gioiTinh}</div>
            </div>

            <div class="form-group">
                <label>Chuyên ngành:</label>
                <select name="chuyenNganh">
                    <option value="">Chọn chuyên ngành</option>
                    <option value="Công nghệ thông tin" ${param.chuyenNganh == 'Công nghệ thông tin' ? 'selected' : ''}>Công nghệ thông tin</option>
                    <option value="Kinh doanh quốc tế" ${param.chuyenNganh == 'Kinh doanh quốc tế' ? 'selected' : ''}>Kinh doanh quốc tế</option>
                    <option value="Ngôn ngữ Anh" ${param.chuyenNganh == 'Ngôn ngữ Anh' ? 'selected' : ''}>Ngôn ngữ Anh</option>
                </select>
                <div class="error">${errors.chuyenNganh}</div>
            </div>

            <button type="submit">Đăng ký</button>
        </form>
    </div>
</body>
</html>