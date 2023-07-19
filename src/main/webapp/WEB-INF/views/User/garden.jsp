<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../css/garden.css">
		<link rel="styleSheet" type="text/css" href="../css/general.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css">
	<meta charset="ISO-8859-1">
	<title>Your garden</title>
</head>
<body>
	<nav>
				<a class = "logout" href="/logout">logout</a>
				<a class = "logout" id="help">Help</a>
				<a class = "logout" href="/u/log/home">logs</a>
		<input type="checkbox" id="check" name="check">
		<label for="check" class="checkbtn"> <i class="fas fa-bars"></i></label>
		<label class="logo" ><a href="/Userloggedin"><img src="../images/horted-logo.png" alt="Horted Logo"></a></label>
		
		<ul class="text">
			<li> <a href="/u/garden" id="garden"> Your garden</a></li>
			<li> <a href="/u/account" id="account"> Your account</a></li>
		</ul>
		<label class="welcome"> <span class="username">${User.username}</span>'s Garden</label>
	</nav>

		<div class="background">
				<div class = "topplant">
					<div class="plantDisplay">
								<c:set var ="i" scope="session" value="1"/>
								<c:set var ="y" scope="session" value="1"/>
								<c:forEach items="${UserPlants}" var="Plant">
									<c:if test='${Plant.border=="top"}'>
										<table class="border_table" style="grid-column-start:${i= i +1}; grid-column-end:${i}; ">
											<c:choose>
												<c:when test="${ y==1}"> 
													<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image" style="width:250px;height:250px;"> <img src='${Plant.imageURL}'> </th></tr>
												</c:when>
											<c:otherwise>
																						<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image"> <img src='${Plant.imageURL}'> </th></tr>
											</c:otherwise>
											</c:choose>
											
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-size:24px;">name: ${Plant.name}</td></tr>
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}','${role}')"><td style="font-weight:550; font-size:24px;"><i class="fas fa-tint fa-lg" id="water"></i>  ${Plant.wateringFrequency} </td></tr>
											<c:if test='${Plant.happinessToday=="false"}'>
												<tr><td> <a href="/u/happy/${Plant.id}"><img src="../images/happy_plant.png" style="height:75px; width:75px;"/> </a> <a href="/u/sad/${Plant.id}"><img src="../images/sad_plant.png" style="height:75px; width:75px; margin-left:20px;"/> </a></td></tr>
											</c:if>
						
</table>
		</c:if>
	</c:forEach>
					</div>
		<label class="border-text">top plant border ( ${borderCount.get("top")} )</label>
		<a class = "button" id="top" Onclick="addPlant('${role}' , ${borderCount.get('top')} ,this.id)">+</a>
		</div>
		<div class="container">

		<div class="leftplant">
							<div class="plantDisplay">
								<c:set var ="i" scope="session" value="1"/>
								<c:set var ="y" scope="session" value="1"/>
								<c:forEach items="${UserPlants}" var="Plant">
									<c:if test='${Plant.border=="left"}'>
										<table class="border_table" style="grid-column-start:${i= i +1}; grid-column-end:${i}; ">
											<c:choose>
												<c:when test="${ y==1}"> 
													<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image" style="width:250px;height:250px;"> <img src='${Plant.imageURL}'> </th></tr>
												</c:when>
											<c:otherwise>
																						<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image"> <img src='${Plant.imageURL}'> </th></tr>
											</c:otherwise>
											</c:choose>
											
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-size:24px;">name: ${Plant.name}</td></tr>
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-weight:550; font-size:24px;"><i class="fas fa-tint fa-lg" id="water"></i>  ${Plant.wateringFrequency} </td></tr>
											<c:if test='${Plant.happinessToday=="false"}'>
												<tr><td> <a href="/u/happy/${Plant.id}"><img src="../images/happy_plant.png" style="height:75px; width:75px;"/> </a> <a href="/u/sad/${Plant.id}"><img src="../images/sad_plant.png" style="height:75px; width:75px; margin-left:20px;"/> </a></td></tr>
											</c:if>
						
</table>
		</c:if>
	</c:forEach>
					</div>
		<label class="border-text">left plant border ( ${borderCount.get("left")} )</label>
			<a class = "button" id="left" Onclick="addPlant('${role}' , ${borderCount.get('left')} ,this.id)">+</a>
		</div>
		
		<div class="rightplant">
							<div class="plantDisplay">
								<c:set var ="i" scope="session" value="1"/>
								<c:set var ="y" scope="session" value="1"/>
								<c:forEach items="${UserPlants}" var="Plant">
									<c:if test='${Plant.border=="right"}'>
										<table class="border_table" style="grid-column-start:${i= i +1}; grid-column-end:${i}; ">
											<c:choose>
												<c:when test="${ y==1}"> 
													<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image" style="width:250px;height:250px;"> <img src='${Plant.imageURL}'> </th></tr>
												</c:when>
											<c:otherwise>
																						<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image"> <img src='${Plant.imageURL}'> </th></tr>
											</c:otherwise>
											</c:choose>
											
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-size:24px;">name: ${Plant.name}</td></tr>
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-weight:550; font-size:24px;"><i class="fas fa-tint fa-lg" id="water"></i>  ${Plant.wateringFrequency} </td></tr>
											<c:if test='${Plant.happinessToday=="false"}'>
												<tr><td> <a href="/u/happy/${Plant.id}"><img src="../images/happy_plant.png" style="height:75px; width:75px;"/> </a> <a href="/u/sad/${Plant.id}"><img src="../images/sad_plant.png" style="height:75px; width:75px; margin-left:20px;"/> </a></td></tr>
											</c:if>
						
</table>
		</c:if>
	</c:forEach>
					</div>
		<label class="border-text">Right plant border ( ${borderCount.get("right")} )</label>
		<a class = "button" id="right" Onclick="addPlant('${role}' , ${borderCount.get('right')} ,this.id)">+</a>
		</div>
				
	</div>
			<div class="bottomplant">
								<div class="plantDisplay">
								<c:set var ="i" scope="session" value="1"/>
								<c:set var ="y" scope="session" value="1"/>
								<c:forEach items="${UserPlants}" var="Plant">
									<c:if test='${Plant.border=="bottom"}'>
										<table class="border_table" style="grid-column-start:${i= i +1}; grid-column-end:${i}; ">
											<c:choose>
												<c:when test="${ y==1}"> 
													<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image" style="width:250px;height:250px;"> <img src='${Plant.imageURL}'> </th></tr>
												</c:when>
											<c:otherwise>
																						<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><th class ="Border_image"> <img src='${Plant.imageURL}'> </th></tr>
											</c:otherwise>
											</c:choose>
											
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-size:24px;">name: ${Plant.name}</td></tr>
											<tr onclick="api.showPlant(false,${Plant.apiId}, '${Plant.border}', '${Plant.wateringFrequency }', '${Plant.id}', '${role}')"><td style="font-weight:550; font-size:24px;"><i class="fas fa-tint fa-lg" id="water"></i>  ${Plant.wateringFrequency} </td></tr>
											<c:if test='${Plant.happinessToday=="false"}'>
												<tr><td> <a href="/u/happy/${Plant.id}"><img src="../images/happy_plant.png" style="height:75px; width:75px;"/> </a> <a href="/u/sad/${Plant.id}"><img src="../images/sad_plant.png" style="height:75px; width:75px; margin-left:20px;"/> </a></td></tr>
											</c:if>
						
</table>
		</c:if>
	</c:forEach>
					</div>
				<label class="border-text">Bottom plant border ( ${borderCount.get("bottom")} )</label>
			<a class = "button" id="bottom" Onclick="addPlant('${role}' , ${borderCount.get('bottom')} , this.id)">+</a>
		</div>
	
	
	</div><div class="footer">
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
	<div class="popup" id="popup">
		  <div class="popup-help">
    <span class="close">&times;</span>
    <ul class ="helpList">
    <li> <p>Click on the '+' to add a plant</p> </li>
    <li> <p> You can then search for the plant you are after and add it to your garden</p> </li>
    <li> <p>Or you can upload an image of your plant and we'll try and identify it for you </p> </li>
    <li> <p> You can change the watering times to get notifications of when your plants need watering</p> </li>
    <li> Free users can only add up to 2 plants per border</li>
    </ul>
   
      </div>
		
	</div>
	
		<div class="error" id="error">
		  <div class="error-text">
    <span class="close">&times;</span>
    <p> Free users can only add 2 plants per border.</p>
    <p style="color:black; margin-top:5%; margin-bottom:5%;">Click below to upgrade your account</p>
    <a class = "error_button" id="upgrade" href="/u/upgradeUser">Upgrade</a>
  </div>
		
	</div>
	
	
	<div class="plant_container">
		<div id="individual">
		      <span class="closeDisplay">&times;</span>
		  	<div class="soloplantimg">
    			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    		<div class= "details">
    			<label> Plant name: </label>
    			<label> Family: </label>
    			<a id="log"> </a>
    		</div>

	</div>
	</div>
		<div class="plant_container">
		<div id="edit">
		      <span class="closeDisplay">&times;</span>
		  	<div class="soloplantimg">
    			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    		<div class= "details">
    			<label> Plant name: </label>
    			<label> Family: </label>
    		</div>

	</div>
	</div>
	
			<div class="plant_container" id="createLog">
		<div id="edit">
		      <span class="closeDisplay" onclick="CreateLog.style.display='none';">&times;</span>
		    <div class="soloplantimg">
    			<img id="logimg" src='${Plant.imageURL}'>
    		</div>
    		<form:form action="/u/log/createLog/" method="POST" modelAttribute="log" class="Form" id="form" >
					<div class="container_input">				
						<form:input path="Pruning" class="input" type="text" placeholder="Pruning info"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fas fa-cut fa-lg"></i>
						</span>
    			</div>
    								<div class="container_input">				
						<form:input path="nutrition" class="input" type="text" placeholder="nutritional info"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fas fa-utensils fa-lg"></i>
						</span>
    			</div>
    								<div class="container_input">				
						<form:input path="pesticide" class="input" type="text" placeholder="pesticide info"/>
						<span class="animate_input"></span>
						<span class="input_icon">
							<i class="fas fa-spray-can fa-lg"></i>
						</span>
    			</div>
    			
    							<div class="container_button" style="margin:auto;">
							<input type="submit" class="Submit" id="button" value="Submit Log" />
					</div>
    		</form:form>

	</div>
	</div>
		<div class="search_container">
		  <div class="search">
		   <span class="closeSearch">&times;</span>
		   <span class="closeSearch" style="margin-right:20px;" onclick="document.getElementById('imageUpload').click()" onchange="identify()"><i class="fas fa-camera"></i></span>
		   <input type="file" name="files" id="imageUpload" style="display:none;" onchange="identify()" accept="image/png, image/jpeg">
    <div id="searchBarContainer">
    	<input type="text" name="searchbar" id="searchbar" placeholder="search for a plant..." autocomplete="off"/>
    </div>
    <img src="" width="100%" height="100%" style="display:none; margin-top:20px;" id="DisplayImageUpload">
     <span style="margin-left:40%;"><a class="back" id="identifyButton" onclick="identifyImage()" >Identify</a></span>
     <div id="Percent">
  		<div id="Bar">10%</div>
	</div>
    <ul id="plants">
    
    	<li class="plant" id="0" onclick="api.showPlant(true,this.id)">
			    		 <div class="plantimg">
    		 			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    		<div class= "details">
    		<label> Plant name: </label>
    		<label > Family: </label>
    		</div>
    	</li>
    	<li class="plant" id="1" onclick="api.showPlant(true ,this.id)">
			    		 <div class="plantimg">
    		 			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    	</li>
    	
    	<li class="plant" id="2" onclick="api.showPlant(true,this.id)">
			    		 <div class="plantimg">
    		 			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    	</li>
    	
    	<li class="plant" id="3" onclick="api.showPlant(true,this.id)"> 
    		 	<div class="plantimg">
    		 			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		 	</div>
    	</li>
    	
    	<li class="plant" id="4" onclick="api.showPlant(true,this.id)">
			    		 <div class="plantimg">
    		 			<img src="https://bs.plantnet.org/image/o/afc9f4d7ce137f04746413f629330948b73e79d3">
    		</div>
    	</li>
    </ul>
    			  <a class="next" id="next" onclick="api.changePage(1)">Next</a>
  			<a class="back" id="back" onclick="api.changePage(-1)">Back</a>
  </div>
	</div>
	
	
	<script src="../js/addPlant.js"></script>
	<c:if test='${role != "Premium"}'>
	<script data-ad-client="ca-pub-1996540736427532" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	</c:if>
	
	
</body>
</html>