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
		<title>Inicio - dondeInvierto</title>
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
								<li><a href="consultarCuenta.html">Consultar cuentas</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Indicadores<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="consultarIndicador.html">Consultar indicadores</a></li>
								<li><a href="gestionIndicadores.html">Gestión indicadores</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Metodologías<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="consultarMetodologia.html">Consultar metodologías</a></li>
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

			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Bienvenido, <c:out value="${usuario.username}"/>.</h1>
				<p>Seleccione alguna opción desde el menu o desde aquí mismo para comenzar a trabajar.</p>
			</div>
			
			<c:if test="${usuario.getRango()==0}">
				<div class="col-sm-4">
						<a class="col" href="proyecto.html">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 class="panel-title">Proyecto</h3>
								</div>
								<div class="panel-body">
									<center><img src="resources/images/proyecto.png" class="img-responsive"></center>
									<p class="info">Edite el proyecto cargando un archivo ".csv" con los datos para poder comenzar a trabajar.</p>
								</div>
							</div>
						</a>
				</div>
			</c:if>
			<div class="col-sm-4">
				<a class="col" href="#">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Cuentas</h3>
						</div>
						<div class="panel-body">
							<center><img src="resources/images/consultas.png" class="img-responsive"></center>
							<p class="info">Haga consultas específicas usando filtros para ver los valores de las distintas cuentas cargadas.</p>
						</div>
					</div>
				</a>
			</div>
			<div class="col-sm-4">
				<a class="col" href="gestionIndicadores.html">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Indicadores</h3>
						</div>
						<div class="panel-body">
							<center><img src="resources/images/indicadores.png" class="img-responsive"></center>
							<p class="info">Gestione los indicadores económicos.</p>
						</div>
					</div>
				</a>
			</div>
			<div class="col-sm-4">
				<a class="col" href="#">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Metodologías</h3>
						</div>
						<div class="panel-body">
							<center><img src="resources/images/metodologias.png" class="img-responsive"></center>
							<p class="info">Gestione las metodologías.</p>
						</div>
					</div>
				</a>
			</div>
			
			<!-- Tabla datos -->
			<div class="page-header" style="clear:both; padding-top:20px;">
				<h1>Tabla de Datos</h1>
			</div>
					
			<c:choose>
			    <c:when test="${database.esVacio()}">
			    	<div class="alert alert-info" role="alert">
						<strong>Informando!</strong> No se han detectado datos cargados, por favor cargue un archivo ".csv" desde el panel "Proyecto".
					</div>
			    </c:when>
			    <c:otherwise>
				    <table class="table">
			            <thead>
			              <tr>
			                <th>Empresa</th>
			                <th>Cuenta</th>
			                <th>Año</th>
			                <th>Valor</th>
			              </tr>
			            </thead>
			            <tbody>
				        <c:forEach items="${cotizaciones}" var="cotizacion">     
						  	<tr>
				                <td><c:out value="${(cotizacion.getEmpresa()).getNombreEmpresa()}"/></td>
				                <td><c:out value="${(cotizacion.getCuenta()).getNombre()}"/></td>
				                <td><c:out value="${cotizacion.getAnio()}"/></td>
				                <td><c:out value="${cotizacion.getValor()}"/></td>
			              	</tr>
						</c:forEach>
						</tbody>
					</table>
			    </c:otherwise>
			</c:choose>			

			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="bootstrap/js/bootstrap.min.js"></script>

		</div>
			

			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>
