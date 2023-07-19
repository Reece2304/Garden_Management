<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/general.css">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Reset Link</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
				<div class="login_Background" data-tilt>
					<img src="../images/horted-logo.png" alt="Horted-logo">
				</div>
					<span class="Form_title">
						Email sent <br/>
						Check ${User.email} for your link
					</span>
					<a class="button" href="/resendReset/${User.username}">Resend Email</a>
			</div>
		</div>
</body>
</html>