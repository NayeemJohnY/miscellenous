<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<link rel="stylesheet" href="style.css">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hisaab Kitab</title>
</head>

<body>
    <h1>அஸ்ஸலாமு அலைக்கும் வரஹ்மத்துல்லாஹி வபரகாத்துஹு</h1>

    <div class="login-box">
        <form action="/login" method="post"></form>
        <div class='textbox'>
            <i class="fa fa-user"></i>
            <input type="text" placeholder="பயனர் பெயர்">
        </div>
        <div class='textbox'>
            <i class="fa fa-lock"></i>
            <input type="password" placeholder="கடவுச்சொல்">
        </div>
        <input type="button" class="button" value="உள்நுழைக" />
        </form>
        <div class='split-border' style="text-align:center; padding:10px 10px">அல்லது</div>
        <a href="/createAccount" method="get">புதிய கணக்கை உருவாக்க</a>
    </div>

</body>

</html>