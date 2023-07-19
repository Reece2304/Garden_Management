<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="styleSheet" type="text/css" href="../css/general.css">
	<link rel="styleSheet" type="text/css" href="../css/AdminHome.css">
	
	<meta charset="ISO-8859-1">
	<title>List of Users</title>
</head>
<body>

		<div class="container_login">
		
			<div class="container2_login">
				<a class = "button" href="/logout">logout</a>
				<a class = "button" id="removedButton" href="/a/RemovedUsers">Removed Users</a>
					<span class="Form_title">
						Table of users <br/><span class = "error">${error}</span>
					</span>
					<table>
	<tr>
		<th>Username</th>
		<th>email</th>
		<th>verified</th>
		<th>Role</th>
		<th>Remove user</th>
		<th>Upgrade to Admin</th>
		<th>Upgrade to Premium User</th>
	</tr>
	<c:forEach items="${Users}" var="User">
	<c:if test='${User.removed==false}'>
	<tr>
	<td> ${User.username} </td>
	<td> ${User.email} </td>
	<td> ${User.verified} </td>
	 <td>${User.role} </td>
	<td> <a class="link" href="/a/SoftRemoveUser?Username=${User.username}">Remove ${User.username} </a></td>
	<td><a class="link" onclick=admin(this.id) id=${User.username}>Upgrade ${User.username} to admin </a></td>
	<td><a class="link" href="/a/UpgradePremium?Username=${User.username}">Upgrade ${User.username} to premium</a></td>
	</tr>
	</c:if>
	</c:forEach>
</table>
					
			</div>
		</div>
		
		<script>
		function admin(){
			var admin = confirm("Are you sure you want to promote " + "'"+ arguments[0] + "'" + " to an admin?");
			if (admin == true)
				{
					window.location.href='/a/UpgradeAdmin?Username='+ arguments[0];
				}
		}

		</script>
</body>
</html>