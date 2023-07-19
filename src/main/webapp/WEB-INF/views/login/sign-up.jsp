<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/general.css">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Signup</title>
</head>
<body>
		<div class="container_login">
			<div class="container2_login">
				<div class="login_Background" data-tilt>
					<img src="../images/horted-logo.png" alt="Horted-logo">
				</div>
				<form:form action="/SignupComplete" method="POST" modelAttribute="user" class="Form" >
					<span class="Form_title">
						Signup Page
					</span>
					
					<form:errors path="fullName" class="error"></form:errors>
					<div class="container_input">				
						<form:input path="fullName" class="input" type="text" placeholder="Full Name"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-signature fa-lg" aria-hidden="true"></i>
						</span>

					</div>
					
					
					<div class="container_input">
						<form:errors path="username" class="error"></form:errors>
						<form:input path="username" class="input" type="text" placeholder="username"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-user fa-lg" aria-hidden="true"></i>
						</span>
													
					</div>
					
					<form:errors path="email" class="error"></form:errors>
					<div class="container_input">
						<form:input path="email" class="input" type="email" placeholder="Email address"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-at fa-lg" aria-hidden="true"></i>
						</span>
					</div>
											
					<form:errors path="password" class="error"></form:errors>
					<div class="container_input">
						<form:input path="password" class="input" type="password" name="password" placeholder="Password"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-lock fa-lg"></i>
						</span>
					</div>

					    <form:errors path="confirmPass" class="error"></form:errors>
						<div class="container_input">
						<form:input path="confirmPass" class="input" type="password" name="password" placeholder="Confirm Password"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fa fa-lock fa-lg"></i>
						</span>
					</div>

					<div class="container_button">
							<input type="submit" class="button" id="button" value="Sign Up" />
					</div>
				</form:form>
			</div>
		</div>
</body>
</html>
