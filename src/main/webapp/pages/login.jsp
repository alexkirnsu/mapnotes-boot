<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Login</title>

    <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/pages/css/signin.css" />" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container" style="width: 300px;">
    <form action="/loginAction" method="post">
        <h2 class="form-signin-heading" style="text-align: center;">MapNotes</h2>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <input type="text" class="form-control" name="username" placeholder="Login" required autofocus value="sasa">
        <input type="password" class="form-control" name="password" placeholder="Password" required value="sasa">
        <button class="btn btn-primary btn-block" type="submit">Login</button>
        <p class="option">
            <a href="/registration">Registration</a>
        </p>
    </form>
</div>

</body>
</html>
