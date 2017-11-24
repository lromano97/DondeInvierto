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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
				<h1>Metodologias.</h1>
				<p>Cree, edite o elimine metodologias creadas por usted.</p>
			</div>

			<!-- Formulario -->
			<div class="page-header">
				<h1>Nueva Metodologia</h1>
			</div>
			
			<div class="container">
				<div class="row">
      				<input type="text" class="col-sm-2" name="" value="" placeholder="Nombre" style="margin:5px 5px 5px 10px; height:30px;">
    			</div>
    			<form method="post" id="condicionesForm">
	      				<h3>Condiciones</h3>
	        			<div class="form-group row">
	          				<input type="text" class="col-sm-2" placeholder="Parametro" name="parametro1[0]" value="" style="height:30px; margin:5px 5px 5px 10px;">
	          				<select class="col-sm-2" name="comparador[0]" style="height:30px; margin:5px 5px 5px 5px;">
	            				<option value="" selected disabled>Comparador</option>
	          				</select>
	          				<input type="text" class="col-sm-2" placeholder="Parametro" name="parametro2[0]" value="" style="margin:5px 10px 5px 5px; height:30px;">
	          				<div class="col-sm-1">
	          					<button type="button" class="btn btn-default addButton"><i class="fa fa-plus" style="font-size:24px"></i> </button>
	          				</div>
	          			</div>
	        			<div class="form-group row hide" id="condicionTemplate">
	          				<input type="text" class="col-sm-2" placeholder="Parametro" name="parametro1" value="" style="height:30px; margin:5px 5px 5px 10px;">
	          				<select class="col-sm-2" name="comparador" style="height:30px; margin:5px 5px 5px 5px;">
	            				<option value="" selected disabled>Comparador</option>
	          				</select>
	          				<input type="text" class="col-sm-2" placeholder="Parametro" name="parametro2" value="" style="margin:5px 10px 5px 5px; height:30px;">
	          				<div class="col-sm-1">
	          					<button type="button" class="btn btn-default removeButton"><i class="fa fa-minus" style="font-size:24px"></i> </button>
	          				</div>
	        			</div>
	        			 <button type="button" name="button" class="btn btn-primary" style="margin:5px 5px 5px 0px;">Crear metodologia</button>
	    		</form>
			</div>	
			<!-- jQuery -->
			<script src="resources/js/autocompleteIndicador.js"></script>
			<script src="resources/js/addingConditions.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>