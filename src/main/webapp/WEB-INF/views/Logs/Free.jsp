<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../../../css/general.css">
		<link rel="styleSheet" type="text/css" href="../../../css/AdminHome.css">
		<link rel="styleSheet" type="text/css" href="../../../css/garden.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
	<meta charset="ISO-8859-1">
	<title>Plant log</title>
</head>
<body>

	<nav>
				<a class = "logout" href="/logout">logout</a>
				<a class = "logout" href="/u/log/home">logs</a>
		<input type="checkbox" id="check" name="check">
		<label for="check" class="checkbtn"> <i class="fas fa-bars"></i></label>
		<label class="logo" ><a href="/Userloggedin"><img src="../../../images/horted-logo.png" alt="Horted Logo"></a></label>
		
		<ul class="text">
			<li> <a href="/u/garden" id="garden"> Your garden</a></li>
			<li> <a href="/u/account" id="account"> Your account</a></li>
		</ul>
		<label class="welcome"> <span class="username">${plant.name}</span>'s Logs</label>
	</nav>

		<div class="container_login">
		
			<div class="container2_login">
					<span class="Form_title">
						${plant.name}'s Log Entries <br/>
					</span>
					<table>
	<tr>
		<th>Date</th>
		<th>Happiness</th>
	</tr>
	<c:forEach items="${happyList}" var="Entries">
	<tr>
	<td style="font-weight:550; font-size:24px;"> ${Entries.dateOfEntry} </td>
	<c:if test="${Entries.happy == 'true' }">
		<td> <img src="../../../images/happy_plant.png" style="height:75px; width:75px;"/> </td>
	</c:if>
		<c:if test="${Entries.happy == 'false' }">
		<td> <img src="../../../images/sad_plant.png" style="height:75px; width:75px;"/> </td>
	</c:if>
	</tr>
	</c:forEach>
</table>
					
			</div>
		</div>
		
		<div class="footer">
			<label class="Footlogo" ><a href="/Userloggedin"><img src="../../../images/horted-logo.png" alt="Horted Logo"></a></label>
			
			<label class="socials">Visit our social media for updates and more about us</label>
			<ul class="icons">
				<li> <a class="icon" href=""><i id="facebook" class="fab fa-facebook-square fa-2x"></i> </a> </li>
				<li> <a class="icon" href=""><i id="twitter" class="fab fa-twitter fa-2x"></i></a> </li>
				<li> <a class="icon" href=""><i id="instagram" class="fab fa-instagram fa-2x"></i></a> </li>
				<li> <a class="icon" href=""><i id="youtube" class="fab fa-youtube fa-2x"></i></a> </li>
			</ul>
				<div class="leftFoot">
					<ul>
						<li> <a class="links" href="/u/contactUs">Contact us</a> </li>
					</ul>
					
				</div>
				<div class="tleftFoot">
					<ul>
						<li> <a class="links" href="/u/OurWork">Our work</a> </li>
					</ul>
				</div>
				
			<div class="rightFoot">
				<ul>
				<li> <a class="links" href="/u/AboutUs">About us </a> </li>
			</ul>
		</div>
		<div class="trightFoot">
			<ul>
				<li> <a class="links" href="https://www.horted.co.uk" target="_blank">Horted</a> </li>
			</ul>
		</div>
		
	</div>
		
			<c:if test='${User.role != "Premium"}'>
	<script data-ad-client="ca-pub-1996540736427532" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>
</body>
</html>