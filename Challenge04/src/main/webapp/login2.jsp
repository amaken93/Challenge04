<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="LoginServlet" method="post">
<input type="text" id="id" name="loginId">
<label for="id">Login ID</label>
<input type="password" id="password" name="password">
<label for="password">Password</label>
<button type="submit">ログイン</button>
</form>
</body>
</html>