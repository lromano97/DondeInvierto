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
								<li><a href="#">Consultar cuentas</a></li>
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
						<strong>Bien hecho!</strong> El indicador se ha creado corretamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 1}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Un campo estaba vac�o, por favor intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 2}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Se han usado caracteres ilegales en el nombre. Por favor intentelo nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 3}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> El nombre para el indicador ya existe. Por favor intentelo nuevamente usando otro.
					</div>
			    </c:when>
			    <c:when test="${msg == 4}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Se ha intentado generar un indicador recursivo, por favor pruebe nuevamente.
					</div>
			    </c:when>
			    <c:when test="${msg == 5}">
			    	<div class="alert alert-danger" role="alert">
						<strong>Error!</strong> Hubo un error sint�ctico, por favor intentelo nuevamente.
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
						<form:input path="nombre" cssClass="form-control" class="inputNombre col-sm-2" style="max-width:250px; margin:5px 5px 5px 10px;"/>
					</div>
						<div class="row">
							<label for="inputFormula">F�rmula</label>
							<form:input id="inputExpresion" data-toggle="tooltip" title="Aiuda" data-placement="right" path="expresion" autocomplete="off" cssClass="form-control" class="inputExpresion col-sm-4" style="max-width:500px; margin:5px 5px 5px 10px;"/>
						</div>
					</div>
					<button class="btn btn-primary" class="submitIndicador form-control" type="submit" style="margin:5px 5px 5px 10px;">Crear indicador</button>
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
				              	<c:forEach items="${cuentas}" var="cuenta">
										<button style="outline:0;" class="btn btn-success" onClick="cuentaToInput(this)"><c:out value='${cuenta}'/></button>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>

				<div class="table-responsive">
					<h3>Edite sus indicadores</h3>
					<hr>
			        <table class="table table-striped">
			          <thead>
			            <tr class="">
			              <th class="headerTable">Nombre</th>
			              <th class="headerTable">Formula</th>
			              <th class="headerTable">Eliminar</th>
			            </tr>
			          </thead>
			          <tbody>
			         	<c:forEach items="${indicadores}" var="indicador">								
				            <tr>
				              <th><input type="text" name="" placeholder="Indicador" class="form-control" value="<c:out value='${indicador.getNombre()}'/>"></th>
				              <th><input type="text" name="" placeholder="Formula" class="form-control" value="<c:out value='${indicador.getExpresion()}'/>"></th>
				              <th><button type="button" name="button<c:out value='${indicador.getIdIndicador()}'/>" class="btn"><i class="fa fa-times" aria-hidden="true" style="margin: 3px 2px 2px 2px;"></i></button></th>
				            </tr>
			            </c:forEach>
			          </tbody>
			        </table>
				</div>
			</div>
			<!-- jQuery -->
			<script src="resources/js/autocompleteIndicador.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>
