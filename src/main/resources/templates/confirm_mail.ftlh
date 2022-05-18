<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirm mail</title>
</head>
<body>
    <p>Уважаемый(ая) ${first_name} ${last_name}</p>
    <p>Для подтверждения вашего адреса электронной почты</p>
        <a href="http://localhost:8080/confirm-account?code=${confirm_code}">перейдите по ссылке</a>
</body>
</html>