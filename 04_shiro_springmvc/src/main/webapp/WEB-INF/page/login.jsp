<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录</title>
</head>
<style>
    body{
        width: 100vw;
    }
    form{
        width: 400px;
        margin:150px auto;
    }
    input{
        margin-bottom: 20px;
    }
</style>
<body>
<form action="/login" method="post">
    <input name="username" placeholder="用户名"/><br/>
    <input type="password" name="password" placeholder="密码"/><br/>
    <button type="submit" >登录</button>
</form>

</body>
<script type="text/javascript">

</script>

</html>