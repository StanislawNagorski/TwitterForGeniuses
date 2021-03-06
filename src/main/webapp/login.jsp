<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <meta name="google-signin-client_id" content="464947380957-4his5r75p6q5i9oj3u4vl7jusg5lq3a1.apps.googleusercontent.com">

    <style>
        body {
            background: #f5f5f5
        }

        .simplebox {
            outline: none;
            border: none !important;
            -webkit-box-shadow: none !important;
            -moz-box-shadow: none !important;
            box-shadow: none !important;
        }
    </style>
</head>
<body>
<main role="main" class="container">

    <div class="jumbotron">
        <h1 class="display-4">Hello, Westeros!</h1>
        <p class="lead">Let`s learn Dothraki together!</p>
        <hr class="my-4">
        <p>We all know that unless horses get wings Dothraki Hordes will NEVER swim across Narrow See. But if during your travels You will find yourself in contact with dothraki horde it is better to be good with sword and speak their language. This app is to help you with language learning! (but if you need to improve your swordsmanship contact me! :)</p>
        <p class="lead">
            <a class="btn btn-dark btn-lg" href="https://www.linkedin.com/in/stanislawnagorski/" role="button">Contact</a>
        </p>
    </div>

    <div class="my-3 p-3 bg-white rounded box-shadow">
        <h4 class="text-center mb-4 mt-1">Sign in</h4>
        <hr>
        <c:if test="${errors != null}">
            <c:forEach items="${errors}" var="error">
                <div class="alert alert-warning">
                    <strong>${error.header}</strong> <br>
                    <p>${error.message}<p>
                </div>
            </c:forEach>
        </c:if>
        <form action="login" method="POST">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="login" class="form-control" placeholder="Login" type="text" required>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                    </div>
                    <input name="password" class="form-control" placeholder="Password" type="password" required>
                </div>
            </div>
            <div class="form-group">
                <div class="form-check">
                    <input id="remember" name="remember" class="form-check-input" type="checkbox">
                    <label for="remember" class="form-check-label">Remember?</label>
                </div>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-dark btn-block"> Sign in</button>
            </div>
            <p class="text-center"><a href="register" class="btn">Sign up</a></p>
        </form>

        <%@include file="googleLogin.jsp" %>
    </div>
</main>
</body>
</html>
