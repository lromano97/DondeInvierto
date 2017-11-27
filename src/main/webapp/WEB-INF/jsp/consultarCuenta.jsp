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
		<title>Consultar Cuenta - dondeInvierto</title>
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
			    	<div class="alert alert-info" role="alert">
						La cuenta tiene el valor .
					</div>
			    </c:when>
			</c:choose>
			<!-- DivInfo -->
			<div class="jumbotron">
				<h1>Consultar Cuenta</h1>
				<p>Consulte las distintas cuentas que fueron cargadas por usted</p>
			</div>
			<div class="container">
				<h3>Filtro de busqueda</h3>
				
				<form:form action="generarConsultaCuenta.html" method="POST">
			      <div class="form-group-sm row col-sm-4">
			        
			        <label for="empresa">Empresa</label>
			        <form:select path="empresa" id="empresa" cssClass="form-control" style="margin:5px 5px 5px 10px;">
							<form:option value="Todos" label="Todos"/>
							<c:forEach items="${empresas}" var="empresa">     
								<form:option value="${empresa}" label="${empresa}"/>
							</c:forEach>
					</form:select>
			        
			        <label for="cuenta">Cuenta</label>
					<form:select path="cuenta" id="cuenta" cssClass="form-control" style="margin:5px 5px 5px 10px;">
							<form:option value="Todos" label="Todos"/>
							<c:forEach items="${cuentas}" var="cuenta">     
								<form:option value="${cuenta}" label="${cuenta}"/>
							</c:forEach>
					</form:select>   

			     	<label for="anio">Año</label>
			     	<form:select path="anio" id="año" cssClass="form-control" style="margin:5px 5px 5px 10px;">
						<form:option value="Todos" label="Todos"/>
						<c:forEach items="${anios}" var="anio">     
							<form:option value="${anio}" label="${anio}"/>
						</c:forEach>
					</form:select>
					
					<button class="btn btn-primary" class="submitIndicador form-control" type="submit" style="margin:5px 5px 5px 10px;">Consultar</button>
				  
				  </div>
			    </form:form>
			 </div>
			
			<div class="page-header">
				<h1>Tabla de resultados</h1>
			</div>
			
			<c:choose>
				<c:when test="${resultados.isEmpty()}">
					<div class="alert alert-info" role="alert">
						<strong>Que pena!</strong> No se han encontrado resultados sobre tu consulta.
					</div>
				</c:when>
				<c:when test="${not(resultados.isEmpty())}">
					
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Empresa</th>
								<th>Cuenta</th>
								<th>Año</th>
								<th>Valor</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${resultados}" var="resultado">     
						  	<tr>
				                <td><c:out value="${resultado.getEmpresa()}"/></td>
				                <td><c:out value="${resultado.getCuenta()}"/></td>
				                <td><c:out value="${resultado.getAnio()}"/></td>
				                <td><c:out value="${resultado.getValor()}"/></td>
			              	</tr>
						</c:forEach>
						</tbody>
					</table>		
				
				</c:when>
			</c:choose>
			<!-- jQuery -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
			<!-- Bootstrap js -->
			<script src="resources/bootstrap/js/bootstrap.min.js"></script>

		</div>

	</body>

</html>
