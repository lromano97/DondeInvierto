<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/css/login.css" rel="stylesheet">
		<title>Login - dondeInvierto</title>
	</head>
	
	<body>

		<div class="container">

			<c:choose>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-warning" role="alert">
						<strong>Advertencia!</strong> Debes iniciar sesión para hacer eso.
					</div>
			    </c:when>
			    <c:when test="${msg == 2}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Un campo se encontraba vacio. Intentelo nuevamente.
					</div>
			    </c:when>
			     <c:when test="${msg == 3}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Se ha detectado el uso de caracteres ilegales. Intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 4}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Usuario incorrecto. Intentelo nuevamente.
					</div>
			    </c:when>
			</c:choose>
					
			<form:form class="form-signin" action="login.html" method="POST">
				<center><h2 class="form-signin-heading">Donde Invierto</h2></center>
				<label for="inputUsername" class="sr-only">Nombre de usuario</label>
				<form:input path="username" type="username" id="inputUsername" class="form-control" placeholder="Nombre de usuario"/>
				<label for="inputPassword" class="sr-only">Password</label>
				<form:input path="password" type="password" id="inputPassword" class="form-control" placeholder="Password"/>
				<center><button class="btn btn-lg btn-primary btn-block submit" type="submit">Iniciar sesion</button></center>
			</form:form>

		</div>

	</body>

</html>