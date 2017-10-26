<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/css/web.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		<title>Gestion de Indicadores - dondeInvierto?</title>
	</head>

	<body>

		<!-- Menu -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
						<!-- Icon Navbar Collapsed -->
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
						</button>
						<!-- NavBar Logo-->
						<a class="navbar-brand" href="#">Menu principal</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">					
						<li><a href="inicio.html">Inicio</a></li>
						<c:if test="${usuario.getRango()==0}">
							<li><a href="proyecto.html">Proyecto</a></li>
						</c:if>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Cuentas<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Consultar cuentas</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Indicadores<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Consultar indicadores</a></li>
								<li><a href="gestionIndicadores.html">Gestión indicadores</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Metodologías<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#">Consultar metodologías</a></li>
								<li><a href="#">Gestión metodologías</a></li>
							</ul>
						</li>
						<li><a href="logout.html">Cerrar sesión</a></li>
					</ul>
				</div>
			</div>
		</nav>
		
		<!-- Container -->
		<div class="container theme-showcase" role="main">
				
			<c:choose>
			    <c:when test="${msg == 0}">
			    	<div class="alert alert-success" role="alert">
						<strong>Bien hecho!</strong> El indicador se ha creado corretamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Un campo estaba vacío, por favor intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 2}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Hubo un error sintáctico, por favor intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 3}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Ya existe un indicador con ese nombre. Por favor elija otro.
					</div>
			    </c:when>
			</c:choose>
			
			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Indicadores.</h1>
				<p>Cree, edite o elimine indicadores creados por usted.</p>
			</div>

			<!-- Formulario -->
			<div class="page-header">
				<h1>Nuevo Indicador</h1>
			</div>
			
			<div class="container">
					
				<form:form action="generarIndicador.html" method="POST">
					<div class="form-group">
					<div class="row">
						<label for="inputNombre">Nombre</label>
						<form:input path="nombre" cssClass="form-control" class="inputNombre col-sm-2" style="max-width:250px;"/> 
					</div>
						<div class="row">
							<label for="inputFormula">Fórmula</label>
							<form:input id="inputExpresion" data-toggle="tooltip" title="Aiuda" data-placement="right" path="expresion" autocomplete="off" cssClass="form-control" class="inputExpresion col-sm-4" style="max-width:500px;"/>
						</div>	
					</div>
					<button class="btn btn-primary" class="submitIndicador form-control" type="submit">Crear indicador</button>
				</form:form>
				
				<div class="row">
					<div class="col-sm-4" style="width:50%;">
						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Tus indicadores</h3>
							</div>
							<div class="panel-body">
				              	<c:forEach items="${indicadores}" var="indicador">     
										<button style="outline:0;" class="btn btn-info" onClick="indicadorToInput(this)"><c:out value='${indicador.getNombre()}'/></button>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="col-sm-4" style="width:50%;">
						<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title">Tus cuentas</h3>
							</div>
							<div class="panel-body">
				              	<!-- <c:forEach items="${indicadores}" var="indicador">     
										<button style="outline:0;" class="btn btn-success" onClick="indicadorToInput(this)"><c:out value='${indicador.getNombre()}'/></button>
								</c:forEach> -->
							</div>
						</div>
					</div>
				</div>
				 	
			</div>	
			<!-- jQuery -->
			<script src="resources/js/autocompleteIndicador.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>