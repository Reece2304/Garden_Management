<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../../css/general.css">
<link rel="styleSheet" type="text/css" href="../../css/home.css">
<link rel="stylesheet" href="../../css/user.css">
<link rel="stylesheet" href="../../css/upgrade.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
<meta charset="ISO-8859-1">
<title>Your account</title>
</head>
<body>
	<nav>
		<a class="logout" href="/logout">logout</a> <input type="checkbox"
			id="check" name="check"> <label for="check" class="checkbtn">
			<i class="fas fa-bars"></i>
		</label> <label class="logo"><a href="/Userloggedin"><img
				src="../../images/horted-logo.png" alt="Horted Logo"></a></label>

		<ul class="text">
			<li><a href="/u/garden" id="garden"> Your garden</a></li>
			<li><a href="/u/account" id="account"> Your account</a></li>
		</ul>
		<label class="welcome"> Payment Success <span class="username"></span></label>
	</nav>
	<div class="container_login">
		<div class="container2_login">
				<div class="checkout">
					<label class="baskethead" style="text-align: center;"></label>
					<label style="color:Red; font-weight:500; text-align:center; font-size:25px;">Payment Failed </label>
					<label style="text-align:center; font-size:22px;">Payment not accepted, please try again.</label>
					<label class="basketbottom" style="text-align: center;"></label>
				</div>


				<div class="container_button">
					<a class="button" id="button" href="/u/upgradeUser">Upgrade </a>
				</div>
		</div>
	</div>
	<div class="footer">
		<label class="Footlogo"><a href="/Userloggedin"><img
				src="../images/horted-logo.png" alt="Horted Logo"></a></label> <label
			class="socials">Visit our social media for updates and more
			about us</label>
		<ul class="icons">
			<li><a class="icon" href=""><i id="facebook"
					class="fab fa-facebook-square fa-2x"></i> </a></li>
			<li><a class="icon" href=""><i id="twitter"
					class="fab fa-twitter fa-2x"></i></a></li>
			<li><a class="icon" href=""><i id="instagram"
					class="fab fa-instagram fa-2x"></i></a></li>
			<li><a class="icon" href=""><i id="youtube"
					class="fab fa-youtube fa-2x"></i></a></li>
		</ul>
		<div class="leftFoot">
			<ul>
				<li><a class="links" href="/u/contactUs">Contact us</a></li>
			</ul>

		</div>
		<div class="tleftFoot">
			<ul>
				<li><a class="links" href="/u/OurWork">Our work</a></li>
			</ul>
		</div>

		<div class="rightFoot">
			<ul>
				<li><a class="links" href="/u/AboutUs">About us </a></li>
			</ul>
		</div>
		<div class="trightFoot">
			<ul>
				<li><a class="links" href="https://www.horted.co.uk"
					target="_blank">Horted</a></li>
			</ul>
		</div>

	</div>
	<c:if test='${user.role != "Premium"}'>
		<script data-ad-client="ca-pub-1996540736427532" async
			src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>

</body>
