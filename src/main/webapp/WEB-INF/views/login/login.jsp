<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/general.css">
<link rel="stylesheet" href="../css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Login</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
				<div class="login_Background" data-tilt>
					<img src="../images/horted-logo.png" alt="Horted-logo">
				</div>
				<form action="/login" method="post" class="Form">
					<span class="Form_title">
						Login
					</span>
					<div class="container_input">
						<input class="input" type="text" name="username" placeholder="username">
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-envelope fa-lg" aria-hidden="true"></i>
						</span>
					</div>

					<div class="container_input">
						<input class="input" type="password" name="password" placeholder="Password">
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-lock fa-2x"></i>
						</span>

					</div>
						<div class="text-center">
						<span class="error"> ${error} </span> <br/>
						<a class="text" href="ChangeCredentials">
							Forgot Username / Password?
						</a>
					</div>
					<div class="container_button">
							<input type="submit" class="button" id="button" value="Sign In" />
					</div>
							<a class="button" href="/Signup">Sign up</a>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
</body>
</html>



