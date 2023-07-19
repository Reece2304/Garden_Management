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
				<a class = "button" id="removedButton" href="/a/AdminHome">Admin Home</a>
					<span class="Form_title">
						Removed Users <br/><span class = "error">${error}</span>
					</span>
					<table>
	<tr>
		<th>Username</th>
		<th>email</th>
		<th>verified</th>
		<th>Role</th>
		<th>PERMANENTLY Remove</th>
		<th>Restore</th>
	</tr>
	<c:forEach items="${Users}" var="User">
	<c:if test='${User.removed==true}'>
	<tr>
	<td> ${User.username} </td>
	<td> ${User.email} </td>
	<td> ${User.verified} </td>
	 <td>${User.role} </td>
	<td> <a onclick=remove(this.id) class="link" id="${User.username}">Remove ${User.username} </a></td>
	<td> <a onclick=restore(this.id) class="link" id="${User.username}">Restore ${User.username}</a></td>
	</tr>
	</c:if>
	</c:forEach>
</table>
					
			</div>
		</div>
		
		<script>
				function remove(){
						var remove = confirm("Are you sure you want to remove " + "'"+ arguments[0] + "'" + " ?");
						if (remove == true)
							{
								window.location.href='/a/RemoveUser?Username='+ arguments[0];
							}
					}

				function restore(){
					var restore = confirm("Are you sure you want to restore " + "'" + arguments[0] + "'" + " ?");
					if (restore == true)
						{
							window.location.href='/a/RestoreUser?Username='+ arguments[0];
						}
				}
		</script>
		
</body>
</html>