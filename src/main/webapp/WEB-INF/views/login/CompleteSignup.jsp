<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/general.css">
<link rel="stylesheet" href="../css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Signup Complete</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
					<img src="../images/horted-logo.png" alt="Horted-logo">
					<span class="Form_title">
						Hello ${Users.fullName}, thank you for signing up! <br/>
					</span>
					<span class="error"> ${sent} </span> <br/>
					<span style="font-size:20px; margin-left: 20%;">Check ${Users.email} for further details :)</span>
					<div class="container_button">
					<a class="button" href="/">Home</a>
					<br/>
					<a class="button" href="/resendVerification/${Users.username}">Resend Email</a>
					</div>
			</div>
		</div>
</body>
</html>