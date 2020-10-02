<#ftl encoding="utf-8">
<!DOCTYPE html>
<html lang="en">
<head>
<#--    <meta charset="UTF-8">-->
    <title>login</title>
</head>
<body>
<#if message?has_content>
    <p>${message}</p>
</#if>
<form method="post" action="/login">
    <label>Welcome</label> <br>
    <input type="text" name="username" placeholder="enter name"/>
    <input type="password" name="password" placeholder="enter password"/>
    <input type="email" name="email" placeholder="enter email"/>
    <input type="submit">
</form>

</body>
</html>