<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/general.css">
<link rel="stylesheet" href="../css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Forgot Username/Password</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
				<div class="login_Background" data-tilt>
					<img src="../images/horted-logo.png" alt="Horted-logo">
				</div>
				<form action= /resetPasswordEmail method="post" class="Form">
					<span class="Form_title">
						Reset Password
					</span>
					<div class="container_input">
						<span class="error"> ${email} </span>
						<input class="input" type="email" name="email" placeholder="email">
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-envelope fa-lg" aria-hidden="true"></i>
						</span>
					</div>
					<div class="container_button">
					<input type="submit" class="button" id="button" value="Send Reset Link" />
					</div>
						<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
</body>
</html>