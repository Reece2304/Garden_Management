<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../css/OurWork.css">
	<link rel="styleSheet" type="text/css" href="../css/general.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
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
		<label class="welcome"> Our Work</label>

	</nav>
<div class="Slidecontainer">

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2018/04/387.jpg">
  </div>

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2018/04/2006-2009-162.jpg">
  </div>

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2018/04/2006-2009-173.jpg">
  </div>

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2020/03/IMG_20190511_153649608-scaled.jpg" >
  </div>

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2018/04/IMG00245-20100430-0809.jpg">
  </div>

  <div class="Slides">
      <img src="https://horted.co.uk/wp-content/uploads/2020/03/IMG_20190419_175200111-scaled.jpg">
  </div>

	  <a class="next" onclick="changeSlides(1)">&#10095;</a>
  <a class="prev" onclick="changeSlides(-1)">&#10094;</a>
  
  <div class="caption-container">
    <p id="caption"></p>
  </div>

  <div class="row">
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2018/04/387.jpg" onclick="Slide(1)" alt="">
    </div>
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2018/04/2006-2009-162.jpg" onclick="Slide(2)" alt="">
    </div>
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2018/04/2006-2009-173.jpg" onclick="Slide(3)" alt="">
    </div>
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2020/03/IMG_20190511_153649608-scaled.jpg" onclick="Slide(4)" alt="">
    </div>
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2018/04/IMG00245-20100430-0809.jpg" onclick="Slide(5)" alt="">
    </div>
    <div class="column">
      <img class="inactive" src="https://horted.co.uk/wp-content/uploads/2020/03/IMG_20190419_175200111-scaled.jpg" onclick="Slide(6)" alt="">
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
	
	<script src="../js/slide.js"></script>
	<c:if test='${role != "Premium"}'>
	<script data-ad-client="ca-pub-1996540736427532" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>
</body>
</html> 