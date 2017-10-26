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
						<strong>Bien hecho!</strong> El archivo se ha cargado correctamente, ya puede empezar a trabajar.
					</div>
			    </c:when>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> El campo o el archivo se encuentra vacio. Intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 2}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> El archivo no es un ".csv", por favor vuelva a intentarlo usando un archivo con este formato.
					</div>
			    </c:when>
			    <c:when test="${msg == 3}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> I/O Exception por favor contacte con algún administrador de la web.
					</div>
			    </c:when>
			    <c:when test="${msg == 4}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> El archivo .csv se encuentra vacio. Intentelo nuevamente con uno que se encuentre completo.
					</div>
			    </c:when>
			    <c:when test="${msg == 5}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> El archivo .csv se encuentra incompleto (inconsistencias en la cantidad de columnas). Por favor completelo e intente nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 6}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Las columnas no concuerdan con el formato acordado. Favor de modificarlas e intentarlo nuevamente.
					</div>
			    </c:when>
			</c:choose>
			
			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Cargar Proyecto.</h1>
				<p>Cargue un archivo ".csv" para reemplazar los datos del proyecto sobre los cuales trabajan los usuarios.</p>
			</div>
			
			<form method="post" action="generarProyecto.html" enctype="multipart/form-data">
				<div class="form-group">
					<label for="exampleInputFile">Archivo</label>
					<input type="file" name="file" id="exampleInputFile">
					<p class="help-block">Debe ser archivo de extensión ".csv" </p>
				</div>
				<input type="submit" value="Cargar CSV">
			</form>

			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>
		
		</div>
		
	</body>

</html>