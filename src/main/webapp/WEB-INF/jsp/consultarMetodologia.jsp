<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/css/web.css" rel="stylesheet">
		<title>Consultar Metodologias - dondeInvierto</title>
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
								<li><a href="consultarIndicador.html">Consultar cuentas</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Indicadores<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="consultarIndicador.html">Consultar indicadores</a></li>
								<li><a href="gestionIndicadores.html">Gesti�n indicadores</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Metodolog�as<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="consultarMetodologia.html">Consultar metodolog�as</a></li>
								<li><a href="gestionMetodologias.html">Gesti�n metodolog�as</a></li>
							</ul>
						</li>
						<li><a href="logout.html">Cerrar sesi�n</a></li>
					</ul>
				</div>
			</div>
		</nav>

		<!-- Container -->
		<div class="container theme-showcase" role="main">
			<c:choose>
			    <c:when test="${msg == 0}">
			    	<div class="alert alert-success" role="alert">
						<strong>Metodologia superada</strong> La empresa paso satisfactoriamente todas las condiciones de la metodologia.
					</div>
			    </c:when>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Metodologia no superada</strong> La empresa no paso satisfactoriamente las distintas condiciones de la metodologia.
					</div>
			    </c:when>
			</c:choose>
			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Evaluar Metodologia</h1>
				<p>Aplique alguna de las metodologias creadas sobre alguna de las empresas para saber si la cumple</p>
			</div>
			  <div class="container">
				<h3>Filtro de evaluacion</h3>
			    <form>
			      <div class="form-group-sm row col-sm-4">
			        <label for="cuenta">Metodologia</label>
			        <select class="form-control" id="cuenta" name="" style="margin:5px 5px 5px 10px;">
			          <option value=""></option>
			        </select>
			        <label for="empresa">Empresa</label>
			        <select class="form-control" id="empresa"name="" style="margin:5px 5px 5px 10px;">
			          <option value=""></option>
			        </select>
			        <label for="anio">A�o</label>
			        <select class="form-control" id="empresa"name="" style="margin:5px 5px 5px 10px;">
			          <option value=""></option>
			        </select>
				  	    <button type="button" name="button" class="btn btn-primary" style="margin:5px 5px 5px 10px;">Evaluar</button>
				  </div>
			    </form>
			 </div>
			

			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>
