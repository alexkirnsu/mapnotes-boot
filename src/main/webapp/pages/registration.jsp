<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <title>Spring Security</title>

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
        <form action="/registration" method="post">
            <h2 class="form-signin-heading" style = "text-align: center;">Create an account</h2>
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

            <div class="form-group">
                <input type="text" class="form-control" maxlength="20" id = "login" name="login"  placeholder="Login" required>
                <span class="help-block">Less then 20 symbols</span>
            </div>

            <div class="form-group">
                <input type="password" class="form-control" maxlength="20" name="password" placeholder="Password" required>
                <span class="help-block">Less then 20 symbols</span>
            </div>

            <div class="form-group">
                <input type="text" class="form-control" maxlength="255" id = "firstName" name="firstName" placeholder="First Name" required>
            </div>

            <div class="form-group">
                 <input type="text" class="form-control" maxlength="255" id="secondName" name="secondName" placeholder="Second Name" required>
            </div>

            <div class="form-group">
                 <input type="email" class="form-control" maxlength="255" id ="email" name="email" placeholder="Email" required>
            </div>

            <button class="btn btn-primary btn-block" type="submit">Submit</button>
            <p class= "option">
                <a href = "/login">Login</a>
            </p>
        </form>
    </div>

    <script type="text/javascript">
        let user = '${user}';
         if (user != '') {
             alert("Current login already exists. Change it, please.");
             document.getElementById('login').value = '${user.login}';
             document.getElementById('email').value = '${user.email}';
             document.getElementById('firstName').value = '${user.firstName}';
             document.getElementById('secondName').value = '${user.secondName}';
        }
    </script>

</body>
</html>
