<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/general.css">
<link rel="stylesheet" href="../css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Resend Complete</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
					<img src="../images/horted-logo.png" alt="Horted-logo">
					<span class="Form_title">
						Hello ${Users.fullName}, your email has been resent! <br/>
					</span>
					<span style="font-size:20px; margin-left: 20%;">Check ${Users.email} for further details :)</span>
					<div class="container_button">
					<a class="button" href="/">Home</a>
					<br/> <span class="resent"> ${sent} </span> <br/>
					<br/>
					</div>
			</div>
		</div>
</body>
</html>