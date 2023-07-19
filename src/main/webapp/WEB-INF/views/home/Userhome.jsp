<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../css/home.css">
	<link rel="styleSheet" type="text/css" href="../css/general.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
    <link rel="icon" href="../images/icon.png">
    <script src="../js/weather.js"></script>
	<c:if test='${role != "Premium"}'>
	<script data-ad-client="ca-pub-1996540736427532" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>

	<meta charset="ISO-8859-1">
	<title>Home page</title>
</head>
<body>
	<nav>
				<a class = "button" href="/logout">logout</a>
		<input type="checkbox" id="check" name="check">
		<label for="check" class="checkbtn"> <i class="fas fa-bars"></i></label>
		<label class="logo" ><a href="/Userloggedin"><img src="../images/horted-logo.png" alt="Horted Logo"></a></label>
		
		<ul class="text">
			<li> <a href="/u/garden" id="garden"> Your garden</a></li>
			<li> <a href="/u/account" id="account"> Your account</a></li>
		</ul>
		<label class="welcome"> Welcome <span class="username"> ${name} </span></label>
	</nav>

<div class="container">
		<div class="lefthome">
			<label class="noti">notifications</label> 
			<c:set var ="i" scope="session" value="1"/>
			<c:forEach items="${plants}" var="Plant">
				<c:if test='${Plant.daysToNextWater==0}'>
				<ul style="grid-row-start:${i +1}; grid-row-end:${i+1};">
			 		<li class="list"> <img src='${Plant.imageURL }'></li>
				</ul>
				
				<a class="waterButton" href="/u/water/${Plant.apiId}" style="grid-row-start:${i +1}; grid-row-end:${i+1}; ">I've Watered this plant</a>
				<label class="water" style="grid-row-start:${i +1}; grid-row-end:${i= i+1}; "> ${Plant.name }  : ${Plant.daysToNextWater} days </label>
				</c:if>
				
			<c:if test='${Plant.daysToNextWater==1}'>
				<ul style="grid-row-start:${i +1}; grid-row-end:${i+1};">
			 		<li class="list"> <img src='${Plant.imageURL }'></li>
				</ul>
				<label class="water" style="grid-row-start:${i +1}; grid-row-end:${i= i+1}; "> ${Plant.name }  : ${Plant.daysToNextWater} day </label>
			</c:if>
			<c:if test='${Plant.daysToNextWater > 1  && Plant.daysToNextWater <=3}'>
				<ul style="grid-row-start:${i +1}; grid-row-end:${i+1};">
			 		<li class="list" > <img src='${Plant.imageURL }'>  </li>
				</ul>
				<label class="water" style="grid-row-start:${i +1}; grid-row-end:${i= i+1}; "> ${Plant.name }  : ${Plant.daysToNextWater} days </label>
			</c:if>
			
			</c:forEach>
		</div>
		
		<div class="righthome">
			<div class="weather">
				<h2 class="city"> Weather in London</h2>
				<h2 class = "temp"> 25°C</h2>
				<img src="https://openweathermap.org/img/wn/10d@2x.png" class="image"/>
				<div class="description">Rainy</div>
				<div class="humidity"> Humidity: 50%</div>
				<div class="wind">Wind Speed: 7km/h</div>
			</div>
			<div class="hourlyContainer">
				<div class="hourly">
					<h3 class="time0" id ="0"> 2pm</h3>
					<img src="https://openweathermap.org/img/wn/10d@2x.png" class="hourlyimage0"/>
					<h3 class = "hourlytemp0"> 25°C</h3>
					<div class="hourlyDesc0" id="desc0">Rainy</div>
				</div>
				<div class="hourly">

					<h3 class="time1" id ="1"> 4pm</h3>
					<img src="https://openweathermap.org/img/wn/10d@2x.png" class="hourlyimage1"/>
					<h3 class = "hourlytemp1"> 20°C</h3>
					<div class="hourlyDesc1" id="desc1">Rainy</div>
					
				</div>
				<div class="hourly">
					<h3 class="time2" id ="2"> 6pm</h3>
					<img src="https://openweathermap.org/img/wn/10d@2x.png" class="hourlyimage2"/>
					<h3 class = "hourlytemp2"> 15°C</h3>
					<div class="hourlyDesc2" id="desc2">Rainy</div>
				
				</div>
		
			</div>
		</div>
				
	</div>
		<div class="footer">
			<label class="Footlogo" ><a href="/Userloggedin"><img src="../images/horted-logo.png" alt="Horted Logo"></a></label>
			
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
</body>
</html>