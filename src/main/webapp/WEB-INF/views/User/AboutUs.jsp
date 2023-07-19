<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../css/home.css">
	<link rel="styleSheet" type="text/css" href="../css/general.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
    <link rel="icon" href="../images/icon.png">

	<meta charset="ISO-8859-1">
	<title>About Us</title>
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
		<label class="welcome"> About Us</label>

	</nav>

	<div class="container" style="min-height:fit-content;">
		<div class="lefthome" style="margin-left:auto; Margin-right:auto; height:fit-content; Padding:10px;">
		At Horted, we are horticulturalists who maintain gardens of all sizes.
We aim to establish the highest standards for your grounds.
 

		</div>
		</div>
		<div class="container">
		<div class="righthome" style="margin-right:auto;">

We are hands-on gardeners, who enjoy the full process - seeing a landscape design planted out and maturing over time is a great privilege.
We offer horticultural consultation services for large projects, with overview and phasing recommendations.
Available for site visits, to chat through your aspirations and discuss how we can bring these to a reality.
Our garden maintenance plans range from the overhaul of flower beds through to regular weekly gardening.
We will assess current planting palettes and propose planting schemes to ensure maximum seasonal interest.
Our aim is the continuing improvement and development of your outside space, creating and maintaining a garden for living.
 Amongst our many specialist skills, our team are adept at managing all ranges of gardens, from flower to fruit.
We can chat about the maintenance requirements for your outdoor space - be it a residential garden or a corporate environment.
Please call tel 07565 979689
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
	<c:if test='${role != "Premium"}'>
	<script data-ad-client="ca-pub-1996540736427532" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>
	
</body>
</html>