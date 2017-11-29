<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="resources/css/web.css" rel="stylesheet">
		<title>Evaluar Metodologias - dondeInvierto</title>
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
								<li><a href="consultarCuentas.html">Consultar cuentas</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Indicadores<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="evaluarIndicadores.html">Evaluar indicadores</a></li>
								<li><a href="gestionIndicadores.html">Gestión indicadores</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Metodologías<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="evaluarMetodologias.html">Evaluar metodologías</a></li>
								<li><a href="gestionMetodologias.html">Gestión metodologías</a></li>
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
			    	<div class="alert alert-success" role="success">
						<strong>Metodologia superada</strong> La empresa paso satisfactoriamente todas las condiciones de la metodologia.
					</div>
			    </c:when>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-danger" role="danger">
						<strong>Metodologia no superada</strong> La empresa no paso satisfactoriamente las distintas condiciones de la metodologia.
					</div>
			    </c:when>
			    <c:when test="${msg == 2}">
			    	<div class="alert alert-danger" role="warning">
						<strong>Advertencia</strong> No se puede calcular por la falta de datos.
					</div>
			    </c:when>
			</c:choose>
			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Evaluar Metodologias</h1>
				<p>Aplique alguna de las metodologias creadas sobre alguna de las empresas para saber si la cumple.</p>
			</div>
			  <div class="container">
				<h3>Filtro de evaluacion</h3>
			    <form:form method="post" action="generarConsultaMetodologia.html">
			      <div class="form-group-sm row col-sm-4">
			      
			        <label for="metodologia">Metodologia</label>
			        <form:select path="metodologia" id="metodologia" cssClass="form-control" style="margin:5px 5px 5px 10px;">
							<c:forEach items="${metodologias}" var="metodologia">     
								<form:option value="${metodologia.getNombre()}" label="${metodologia.getNombre()}"/>
							</c:forEach>
					</form:select>
			        
			       	<label for="empresa">Empresa</label>
			        <form:select path="empresa" id="empresa" cssClass="form-control" style="margin:5px 5px 5px 10px;">
							<c:forEach items="${empresas}" var="empresa">     
								<form:option value="${empresa}" label="${empresa}"/>
							</c:forEach>
					</form:select>
					
			        <label for="anio">Año</label>
			     	<form:select path="anio" id="año" cssClass="form-control" style="margin:5px 5px 5px 10px;">
						<c:forEach items="${anios}" var="anio">     
							<form:option value="${anio}" label="${anio}"/>
						</c:forEach>
					</form:select>
					
				  	 <button type="submit" name="button" class="btn btn-primary" style="margin:5px 5px 5px 10px;">Evaluar</button>
				  
				  </div>
			    </form:form>
			 </div>
			

			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>
