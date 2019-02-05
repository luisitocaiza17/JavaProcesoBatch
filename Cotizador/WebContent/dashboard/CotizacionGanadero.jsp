<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="../static/css/jquery-steps/normalize.css">
	<link rel="stylesheet" href="../static/css/jquery-steps/main.css">
	<link rel="stylesheet" href="../static/css/jquery-steps/jquery.steps.css">
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" href="../static/css/select2/select2.css">
	<script src="../static/js/jquery-steps/modernizr-2.6.2.min.js"></script>
	<script src="../static/js/jquery-steps/jquery.cookie-1.3.1.js"></script>
	<script src="../static/js/jquery-steps/jquery.steps.min.js"></script>
	<script src="../static/js/jquery.validate.js"></script>

	<script id="tipoObjetoCargaInicial" src="../static/js/ganadero/carga.inicial.cotizador.ganadero.js" tipoObjetoValor="Ganadero"></script>
	<script id="tipoObjetoMetodos" src="../static/js/ganadero/metodos.ganadero.js" tipoObjetoValor="Ganadero"></script>
	<script src="../static/js/cotizador/comun.js"></script>

	<script src="../static/js/util.js"></script> 
	<script	src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script	src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/jquery-dynamic-url/jquery.dynamic-url.js"></script>
	<script src="../static/js/select2.js"></script>
	<!--  	para el tooltipster -->
	<script src="../static/js/jquery-ui/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css"	href="../static/js/jquery-ui/jquery-ui.theme.css" />
	<link rel="stylesheet" type="text/css"	href="../static/css/tooltipster.css" />
	<script type="text/javascript" src="../static/js/jquery.tooltipster.js"></script>
	<script type="text/javascript"	src="../static/js/jquery.tooltipster.min.js"></script>
	<!-- Para el Datepicker-->
	<link href="../static/css/datepicker.css" rel="stylesheet">
	<script src="../static/js/bootstrap-datepicker.js"></script>
	<style type="text/css">
	
		.pillbox {
			border: 1px solid #bbb;
			/* margin-bottom: 10px;*/
			-webkit-border-radius: 4px;
			-moz-border-radius: 4px;
			border-radius: 4px;
			width: 251px;
		}

		.container>div,.container>table {
			margin: 20px;
		}

		.tree {
			width: 430px;
			height: 350px;
		}

		.static-loader {
			margin-left: 30px;
		}

		.step-content {
			border: 1px solid #D4D4D4;
			border-top: 0;
			border-radius: 0 0 4px 4px;
			padding: 10px;
			margin-bottom: 10px;
		}

		fieldset.border {
			border: 1px solid #ddd !important;
			padding: 0 1.4em 1.4em 1.4em !important;
			margin: 0 0 1.5em 0 !important;
			-webkit-box-shadow: 0px 0px 0px 0px #ddd;
			box-shadow: 0px 0px 0px 0px #ddd;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			border-radius: 5px;
		}

		legend.border {
			width: inherit; /* Or auto */
			padding: 0 10px; /* To give a bit of padding on the left and right */
			border-bottom: none;
			font-size: medium;
		}

		.demo-wrap {
			/* margin: 40px auto;*/
			display: block;
			position: relative;
			/* max-width:500px;*/
		}

		a {
			text-decoration: underline;
			color: #00F;
			cursor: pointer;
		}

		.seleccionado {
			background-color: #E0E0E0;
			color: black;
		}

		table {
			width: 100%;
		}

		select {
			width: 90%;
		}

		input[type="text"] {
			width: 90%;
		}

		.marca_modelo {
			width: 90%;
		}

		.no-close {
			display: none
		}

		.ui-dialog-titlebar {
			width: 0%;
		}

		.ui-dialog-titlebar-close {
			visibility: hidden;
		}

		a {
			text-decoration: none !important;
		}

		.col-md-3,.col-sm-3 {
			padding-left: 0px;
			padding-right: 0px;
		}

		.obligatoriosTarifacion {
			border-width: 1px;
			border-style: solid;
			border-color: #46b8da;
			background: #c3e4ff;
		}

		#tablaFinalVehiculos tr td {
			width: 10%;
			white-space: nowrap;
		}
		
		.fondo_botones {
			font-family:Helvetica Neue, Helvetica, Arial, sans-serif;
			font-weight:bold;
		}
		</style>
		<script>

	//eventos de objetos
	var editoVehiculo = false;
	var tieneDescuento = false;
	var cargadoDatosUPLA = false;
	var solicitarInspeccionArr = [];	
	$(document).ready(function() {
		cargar();
	});
	
</script>

</head>
	<body>
    		<%
			// Permitimos el acceso si la session existe		
				if(session.getAttribute("login") == null){
				    response.sendRedirect("/CotizadorWeb");
				}
			%>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->

	<div class="content">
		<div
			style="position: absolute; margin-top: 15px; margin-left: 10px; font-size: larger; color: #003da5;"><b>Cotizaci&oacute;n # <span id="cotizacion_id"></span></b>
		</div>
		<form id="wizard" action="" method="post">
			<h2>Cliente <i class="fa fa-child fa-2x"></i></h2>
			<section>
				<!-- **************************************************************************************************************************
		        *                   F O R M U L A R I O   P A R A   E L   I N G R E S O  D A T O S   G E N E R A L E S
		        ****************************************************************************************************************************-->
				<fieldset class="border">
					<legend class="border">Datos sobre Producto, P&oacute;liza y Cliente</legend>
					<div class="alert alert-danger alert-error" id="mensaje_exito_input_vehiculo" style="display: none;">Por favor ingrese los datos faltantes.</div>
					<!-- Inicio datos del producto cerrado -->
					<div class="panel panel-primary">
						<div class="panel-heading">Seleccione el producto</div>
						<div class="panel-body">
							<table>
								<tr>
									<td style="width: 15%"><label><b>Grupo de Productos:*</b></label></td>
									<td style="width: 35%"><select name="grupo_productos" id="grupo_productos" required="required" class="datosGanadero"></select></td>
									<td style="width: 15%"><label><b>Producto:*</b></label></td>
									<td style="width: 35%"><select name="productos" id="productos" required="required" class="datosGanadero"></select></td>
									<td style="width: 35%"><input type="hidden" id="codigoProductos" name=""></td>
									
								</tr>
								<!--  <tr>
									<td style="width: 15%"><label><b>Tasa:*</b></label></td>
									<td style="width: 35%"><input type="text" name="tasa" id="tasa" disabled="disabled"><select name="tasas" id="tasas" style="display: none;"></select></td>
									<td style="width: 15%"></td>
									<td style="width: 35%"></td>
									
								</tr> -->
							</table>
						</div>
					</div>

					<!-- Inicio datos poliza - 4 columnas -->
					<div class="panel panel-primary">
						<div class="panel-heading">Datos de la P&oacute;liza</div> 
						<div class="panel-body">
							<table>
								<tr>
									<td style="width: 15%"><label><b>Canal:*</b></label></td>
									<td style="width: 35%"><select name="punto_venta" id="punto_venta" required="required" class="datosGanadero obligatoriosTarifacion"></select></td>
									<td style="width: 15%"><label><b>Vigencia de la p&oacute;liza:*</b></label></td>
									<td style="width: 35%"><select name="vigencia" id="vigencia" required="required" class="datosGanadero"></select></td>
									<input type="hidden" id="puntoVentaSeleccionado">
								</tr>
								<tr>
									<td style="width: 15%" colspan="1"><label><b>Agente de Seguros:*</b></label></td>
									<td style="width: 85%" colspan="3"><select name="agentes" id="agentes" class="datosGanadero" required="required"></select></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin datos poliza-->
					<!-- Inicio datos cliente - 4 columnas -->
					<div class="panel panel-primary">
						<div class="panel-heading">Datos del Solicitante</div>
						<div class="panel-body">
							<table>
								<tr>
									<td style="width: 15%"><label><b>Tipo Identificaci&oacute;n:*</b></label></td>
									<td style="width: 35%"><select class="tipo_identificacion datosGanadero" id="tipo_identificacion_principal" required="required"></select>
									<br/>
									<label><b>Contribuyente Especial:*</b></label>
									<input type="checkbox" id="es_contribuyente" style="display:none;"/>									
									</td>
									<td style="width: 15%"><label><b>Identificaci&oacute;n:*</b></label></td>
									<td style="width: 35%"><input type="text" id="identificacion" class="datosGanadero" name="identificacion" required="required" maxlength="20" onchange="cargarPorIdentificacion('datosClienteInicio', this.value)"> <div id="loading_identificacion" class="loading_identificacion" hidden="">
											<span id="loading-msg">Cargando...</span><img src="../static/images/ajax-loader.gif" />
										</div></td>
									<td style="width: 35%"><input type="hidden" id="codigoClienteEnsurance" name="codigoClienteEnsurance"></td>
									<td style="width: 35%"><input type="hidden" id="codigoEntidadEnsurance" name="codigoEntidadEnsurance"></td>
								</tr>
								<tr id="filaNatural">
									<td style="width: 15%"><label><b>Nombres:*</b></label></td>
									<td style="width: 35%"><input type="text" id="nombres" class="datosGanadero" name="nombres" required="required"></td>
									<td style="width: 15%"><label><b>Apellidos:*</b></label></td>
									<td style="width: 35%"><input type="text" id="apellidos" class="datosGanadero" name="apellidos" required="required"></td>
								</tr>
								<tr id="filaJuridica" hidden="true">
									<td style="width: 15%" colspan="1"><label><b>Nombres Empresa:*</b></label></td>
									<td style="width: 35%" colspan="3"><input type="text" id="nombre_completo" class="datosGanadero" name="nombre_completo" required="required"></td>
								</tr>
								<tr>
									<td style="width: 15%"><label><b>E-mail:*</b></label></td>
									<td style="width: 35%"><input type="email" id="email" class="datosGanadero" name="email" ></td>
									<td style="width: 15%"><label><b>No. Teléfono:*</b></label></td>
									<td style="width: 35%"><input type="text" id="telefono" class="datosGanadero" name="telefono"></td>
								</tr>
								<tr>
									<td style="width: 15%"><label><b>No. Celular:*</b></label></td>
									<td style="width: 35%"><input type="text" id="celular" class="datosGanadero" name="celular" required="required"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin datos cliente -->
				</fieldset>
			</section>
			<h2> Productos <i class="fa fa-github-alt fa-2x"></i></h2>
			
			<section>
			<!--***********************************************************************************************
                             G E N E R A C I O N   D E   L A  F I C H A   D E  G A N A D E R O
            ************************************************************************************************-->
			<fieldset class="border">
				<legend>Datos para seguro Ganadero - Vacuno</legend>
				<div class="panel panel-primary">
					<div class="panel-heading">1 - Ubicación</div>
					<div class="panel-body">
						<table>
							<tr>
								<td><label><b>Provincia:*</b></label></td>
								<td style="width: 35%"><select name="ubicacion_provincia" id="ubicacion_provincia"  required="required" class="datosGanadero"></select></td>
								<td><label><b>Cantón:*</b></label></td>
								<td style="width: 35%"><select name="ubicacion_Canton" id="ubicacion_Canton" required="required" class="datosGanadero"></select></td>
								<td><label><b>Parroquia:*</b></label></td>
								<td style="width: 30%"><select name="ubicacion_Parroquia" id="ubicacion_Parroquia" required="required" class="datosGanadero"></select></td>
							</tr>
							<tr>
								
								<td colspan="6">
								<table style="width: 100%">
									<tr>
										<td style="width: 20%"><label><b>Sitio(Ubicación del Ganado):</b></label></td>
										<td style="width: 80%"><input type="text" id="ubicacion" class="datosGanadero" name="ubicacion" required="required"></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								
								<td colspan="6">
								<table style="width: 100%">
									<tr>
										<td style="width: 20%"><label><b>Recinto(Ubicación del Ganado):</b></label></td>
										<td style="width: 80%"><input type="text" id="recinto" class="datosGanadero" name="recinto"></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td colspan="6">
								<table style="width: 50%">
									<tr>
										<td style="width: 25%"><label><b>Región:</b></label></td>
										<td style="width: 7%">Costa:</td>
										<td style="width: 3%"><input type="radio"  id="region_costa" class="datosGanadero" name="region"></td>
										<td style="width: 7%">Sierra:</td>
										<td style="width: 3%"><input type="radio" id="region_sierra" class="datosGanadero" name="region"></td>
										<td style="width: 17%">Oriente:</td>
										<td style="width: 3%"><input type="radio" id="region_oriente" class="datosGanadero" name="region"></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td colspan="6">
								<table style="width: 50%">
									<tr>
										<td style="width: 25%"><label><b>Tipo de Producción:</b></label></td>
										<td style="width: 7%">Carne:</td>
										<td style="width: 3%"><input type="radio"  id="produccion_carne" class="datosGanadero" name="Produccion"></td>
										<td style="width: 7%">Leche:</td>
										<td style="width: 3%"><input type="radio" id="produccion_leche" class="datosGanadero" name="Produccion"></td>
										<td style="width: 17%"></td>
										<td style="width: 3%"></td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">2 - Características Generales de la Finca</div>
					<div class="panel-body">
						<table style="width: 100%">
							<tr>
								<td style="width: 50%; vertical-align: top;">
									<table style="width: 100%">
										<tr>
											<td style="width: 25%">
												<b>Altitud (m.s.n.m.):</b>
											</td>
											<td style="width: 200px">
												0 - 500
											</td>
											<td>
												<input type="checkbox"  id="Altitud1" class="datosGanadero" name="Altitud1">
											</td>
										</tr>
										<tr>
											<td></td>
											<td>501 - 1000</td>
											<td><input type="checkbox"  id="Altitud1" class="datosGanadero" name="Altitud1"></td>
										</tr>
										<tr>
											<td></td>
											<td>1001 - 2000</td>
											<td><input type="checkbox"  id="Altitud1" class="datosGanadero" name="Altitud1"></td>
										</tr>
										<tr>
											<td></td>
											<td>2001 - 3000</td>
											<td><input type="checkbox"  id="Altitud1" class="datosGanadero" name="Altitud1"></td>
										</tr>
									</table>
								</td>
								<td style="width: 50%; vertical-align: top;">
									<table style="width: 100%">
										<tr>
											<td style="width: 20%"><b>Topografía:</b></td>
											<td style="width: 150px">Plana (0 - 10 %)</td>
											<td style="width: 100px"><input type="number" style="width: 60px" id="topografia_pendiente1" class="datosGanadero" name="topografia_pendiente1" onchange="calcularTotalHectareas()"></td>
											<td>Has.</td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 150px">Pendiente (10 - 25 %)</td>
											<td style="width: 100px"><input type="number" style="width: 60px" id="topografia_pendiente2" class="datosGanadero" name="topografia_pendiente2" onchange="calcularTotalHectareas()"></td>
											<td>Has.</td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 150px">Pendiente (+ 25 %)</td>
											<td style="width: 100px"><input type="number" style="width: 60px" id="topografia_pendiente3" class="datosGanadero" name="topografia_pendiente3" onchange="calcularTotalHectareas()"></td>
											<td>Has.</td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 150px">TOTAL</td>
											<td style="width: 100px"><input type="number" style="width: 60px" id="topografia_total" class="datosGanadero" name="topografia_total" required="required" disabled="disabled"></td>
											<td>Has.</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">3 - Características específicas de los pastizales</div>
					<div class="panel-body">
						<table style="width: 100%">
							<tr>
								<td>
									<table style="width: 100%">
										<tr>
											<td style="width: 30%"><label><b>Tipo pasto predominante:*</b></label></td>
											<td style="width: 40%"><input type="text" id="tipo_pasto" class="datosGanadero" name="tipo_pasto" required="required"></td>
											<td style="width: 10%"><label><b>HA de Pasto:*</b></label></td>
											<td style="width: 30%"><input type="number" id="hectareas_pasto" style="width: 100px" class="datosGanadero" name="hectareas_pasto" required="required"></td>		
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 40%"><label><b>Producción de pasto verde al año por HA en toneladas:</b></label></td>
											<td style="width: 70%"><input type="text" id="volumen_pasto" onkeypress="return justNumbers(event);" class="datosGanadero" name="volumen_pasto" required="required"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 30%"><label><b>Observaciones:</b></label></td>
											<td style="width: 70%"><input type="text" id="observaciones_pasto" class="datosGanadero" name="observaciones_pasto"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">4 - Generalidades de la Ganadería</div>
					<div class="panel-body">
						<table>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 40%"><label><b>Cuántos animales vacunos mantiene en la propiedad:</b></label></td>
											<td><input type="number" id="animales_vacunos" class="datosGanadero" name="animales_vacunos" style="width: 100px" required="required"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 15%"><b>Mortalidad Anual:</b></td>
											<td style="width: 150px">Vacas</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_vacas" class="datosGanadero" name="mortalidad_vacas"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_vacas_causa" class="datosGanadero" name="mortalidad_vacas_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Vaconas Vientre</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_vaconasv" class="datosGanadero" name="mortalidad_vaconasv"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_vaconasv_causa" class="datosGanadero" name="mortalidad_vaconasv_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Vaconas Fierro (12 – 18 months)</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_vaconasf" class="datosGanadero" name="mortalidad_vaconasf"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_vaconasf_causa" class="datosGanadero" name="mortalidad_vaconasf_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Vaconas Media (6 – 12 months)</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_vaconasm" class="datosGanadero" name="mortalidad_vaconasm"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_vaconasm_causa" class="datosGanadero" name="mortalidad_vaconasm_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Toros Adultos</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_toros" class="datosGanadero" name="mortalidad_toros"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_toros_causa" class="datosGanadero" name="mortalidad_toros_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Toretes</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_toretes" class="datosGanadero" name="mortalidad_toretes"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_toretes_causa" class="datosGanadero" name="mortalidad_toretes_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Terneros</td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_terneros" class="datosGanadero" name="mortalidad_terneros"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_terneros_causa" class="datosGanadero" name="mortalidad_terneros_causa"></td>
										</tr>
										<tr>
											<td style="width: 15%"></td>
											<td style="width: 150px">Terneras </td>
											<td style="width: 100px"><input type="number" style="width: 100px" id="mortalidad_terneras" class="datosGanadero" name="mortalidad_terneras"></td>
											<td style="width: 50px">Causa</td>
											<td style="width: 300px"><input type="text" id="mortalidad_terneras_causa" class="datosGanadero" name="mortalidad_terneras_causa"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 20%"><b>Método de alimentación:</b></td>
											<td style="width: 100px">Pastoreo</td>
											<td><input type="checkbox"  id="alimentacion_pastoreo" class="datosGanadero" name="alimentacion_pastoreo"></td>
										</tr>
										<tr>
											<td style="width: 20%"></td>
											<td style="width: 100px">Corte</td>
											<td><input type="checkbox"  id="alimentacion_corte" class="datosGanadero" name="alimentacion_corte"></td>
										</tr>
										<tr>
											<td style="width: 20%"></td>
											<td style="width: 100px">Sogueo</td>
											<td><input type="checkbox"  id="alimentacion_sogueo" class="datosGanadero" name="alimentacion_sogueo"></td>
										</tr>
										<tr>
											<td style="width: 20%"></td>
											<td style="width: 100px">Otros</td>
											<td><input type="checkbox"  id="alimentacion_otros" class="datosGanadero" name="alimentacion_otros"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td style="width: 30%"><label><b>Acceso de los animales al agua:</b></label></td>
											<td><input type="text" id="acceso_al_agua" class="datosGanadero" name="acceso_al_agua" required="required"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">5 - Sanidad</div>
					<div class="panel-body">
						<table>
							<tr>
								<td style="width: 50%">
									<table style="width: 100%">
										<tr>
											<td style="width: 25%"><b>Vacunaciones:</b></td>
											<td style="width: 100px">Fiebre Aftosa</td>
											<td><input type="checkbox"  id="vacunaciones_aftosa" class="datosGanadero" name="vacunaciones_aftosa"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Brucelosis</td>
											<td><input type="checkbox"  id="vacunaciones_brucelosis" class="datosGanadero" name="vacunaciones_brucelosis"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Triple</td>
											<td><input type="checkbox"  id="vacunaciones_triple" class="datosGanadero" name="vacunaciones_triple"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Leptospirosis</td>
											<td><input type="checkbox"  id="vacunaciones_leptospirosis" class="datosGanadero" name="vacunaciones_leptospirosis"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">IBR - BVD</td>
											<td><input type="checkbox"  id="vacunaciones_ibrbvd" class="datosGanadero" name="vacunaciones_ibrbvd"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Otras</td>
											<td><input type="text" id="vacunaciones_otras" class="datosGanadero" name="vacunaciones_otras" style="width: 100px"></td>
										</tr>
										<tr>
											<td colspan="3">NOTA: En caso de siniestro deberá presentar los certificados declarados.</td>
										</tr>
									</table>
								</td>
								<td style="width: 50%">
									<table style="width: 100%">
										<tr>
											<td style="width: 25%"><b>Enfermedades:</b></td>
											<td style="width: 80px"></td>
											<td></td>
										</tr>
										<tr>
											<td colspan="5">Llenar del 1 al 6, siendo 1 la más frecuente y 6 la menos frecuente en el hato.</td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Mastitis</td>
											<td style="width: 50px"><input type="number" style="width: 50px" id="enfermedad_mastitis" class="datosGanadero" name="enfermedad_mastitis"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Panadizo</td>
											<td><input type="number"  id="enfermedad_panadizo" style="width: 50px" class="datosGanadero" name="enfermedad_panadizo"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Fiebre de Leche</td>
											<td><input type="number"  id="enfermedad_fiebreleche" style="width: 50px" class="datosGanadero" name="enfermedad_fiebreleche"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Lesión de Ubres</td>
											<td><input type="number"  id="enfermedad_lesionubres" style="width: 50px" class="datosGanadero" name="enfermedad_lesionubres"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Neumonías</td>
											<td><input type="number" id="enfermedad_neumonias" style="width: 50px" class="datosGanadero" name="enfermedad_neumonias"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Otras</td>
											<td><input type="number" id="enfermedad_otras" style="width:50px" class="datosGanadero" name="enfermedad_otras"></td>
											<td style="width: 100px">Cuál</td>
											<td><input type="text" id="enfermedad_cual" class="datosGanadero" name="enfermedad_cual" style="width: 150px"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<br/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table style="width: 100%">
										<tr>
											<td style="width: 15%"><b>Control de Parásitos:</b></td>
											<td></td>
											<td></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Internos</td>
											<td style="width: 50px"><input type="checkbox" id="parasitos_internos" class="datosGanadero" name="parasitos_internos"></td>
											<td style="width: 110px">Control o Dosis</td>
											<td><input type="text"  id="parasitos_internos_trata" class="datosGanadero" name="parasitos_internos_trata"></td>
											<td style="width: 110px">Frecuencia</td>
											<td><input type="text"  id="parasitos_internos_frecu" class="datosGanadero" name="parasitos_internos_frecu" maxlength="100"></td>
										</tr>
										<tr>
											<td></td>
											<td style="width: 100px">Externos</td>
											<td style="width: 50px"><input type="checkbox" id="parasitos_externos" class="datosGanadero" name="parasitos_externos"></td>
											<td style="width: 110px">Control o Dosis</td>
											<td><input type="text"  id="parasitos_externos_trata" class="datosGanadero" name="parasitos_externos_trata"></td>
											<td style="width: 110px">Frecuencia</td>
											<td><input type="text"  id="parasitos_externos_frecu" class="datosGanadero" name="parasitos_externos_frecu"  maxlength="100"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<br/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table style="width: 100%">
										<tr>
											<td style="width: 20%"><b>Tiene asistencia veterinaria:</b></td>
											<td style="width: 20px">Si</td>
											<td style="width: 30px"><input type="radio" id="asistencia_veterinaria_si" class="datosGanadero" name="asistencia_veterinaria"></td>
											<td style="width: 20px">No</td>
											<td style="width: 30px"><input type="radio" id="asistencia_veterinaria_no" class="datosGanadero" name="asistencia_veterinaria"></td>
											<td style="width: 100px">Frecuencia:</td>
											<td><input type="text"  id="asistencia_veterinaria_frecu" class="datosGanadero" name="asistencia_veterinaria_frecu" style="width: 200px" maxlength="100"></td>
										</tr>
										<tr>
											<td colspan="6">
												<table style="width: 100%">
													<tr>
														<td style="width: 30%">Nombre del Profesional:</td>
														<td><input type="text"  id="asistencia_veterinaria_prof" class="datosGanadero" name="asistencia_veterinaria_prof"></td>
													</tr>
													<tr>
														<td>Teléfono:</td>
														<td><input type="text"  id="asistencia_veterinaria_tele" class="datosGanadero" name="asistencia_veterinaria_tele"></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table style="width: 100%">
										<tr>
											<td colspan="6">
												<table style="width: 100%">
													<tr>
														<td style="width: 25%">Experiencia del ganadero en años:</td>
														<td><input type="number"  id="experiencia_ganadero_anios" style="width: 100px" required="required" class="datosGanadero" name="experiencia_ganadero_anios"></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table>
										<tr>
											<td style="width: 35%">Su principal ingreso económico es la ganadería:</td>
											<td style="width: 2%">Si</td>
											<td style="width: 2%"><input type="radio" id="esprincipal_ingreso_si" class="datosGanadero" name="esprincipal_ingreso"></td>
											<td style="width: 2%">No</td>
											<td><input type="radio" id="esprincipal_ingreso_no" class="datosGanadero" name="esprincipal_ingreso"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">6 - Lista de Animales a Asegurar</div>
					<div class="alert alert-danger" id="msgPopupErrorRangoAnimales" hidden="hidden">
						<div id='mensajeErrorRangoAnimales'></div>
					</div>
					<div class="panel-body">
						<label>Llene los datos del animal y despues presione el boton azul</label>
		                    <input type="hidden" value="0" id="contadorAnimales"><br>
		                    <table>
		                    	<tr>
		                            <td style="width: 20%; text-align: center;" class="sorting">
		                                    Tipo
		                            </td>
		                            <td style="width: 10%; text-align: center;" class="sorting">
		                                    #Arete/Nombre
		                            </td>
		                            <td style="width: 7%; text-align: center;" class="sorting">
		                                    No. Chip
		                            </td>
		                            <td style="width: 20%; text-align: center;" class="sorting">
		                                    Raza
		                            </td>
		                            <td style="width: 10%; text-align: center;" class="sorting">
		                                    Origen
		                            </td>
		                            <td style="width: 10%; text-align: center;" class="sorting">
		                                    Procedencia
		                            </td>
		                            <td style="width: 10%; text-align: center;">
		                                    Color
		                            </td>
		                            <td style="width: 100px; text-align: center;">
		                                    Edad (Meses)
		                            </td>
		                            <td style="width: 10%; text-align: center;" class="sorting">
		                                    Val. Asegurar $
		                            </td>
		                            <td style="width: 15%">
		                            </td>
		                        </tr>
		                        <tr id="AnimalForm" value="">
		                            <td>
		                                <select id="tipoAnimalOrigin" class="datosGanadero">
		                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>
		                                </select>
		                            </td>
		                            <td>
		                                <input type="text" id="numeroAreteOrigin" class="requirednumero datosGanadero cambioVehiculo" />
		                            </td>
		                            <td>
		                                <input type="text" id="numeroChipOrigin" class="requirednumero datosGanadero cambioVehiculo" />
		                            </td>
		                            <td>
		                                <select id="razaAnimalOrigin" class="datosGanadero">
		                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>
		                                </select>
		                            </td>
		                            <td>
		                                <select id="origenAnimalOrigin" class="datosGanadero">
		                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>
		                                    <option value="NACIONAL">NACIONAL</option>
		                                    <option value="IMPORTADA">IMPORTADA</option>
		                                </select>
		                            </td>
		                            <td>
		                                <input type="text" id="procedenciaAnimalOrigin" class="requirednumero datosGanadero cambioVehiculo" />
		                            </td>
		                            <td>
		                                <select id="colorAnimalOrigin" class="datosGanadero">
		                                    <option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>
		                                    <option value="BLANCO CON NEGRO">BLANCO CON NEGRO</option>
		                                    <option value="CAFE">CAFE</option>
		                                    <option value="NEGRO">NEGRO</option>
		                                </select>
		                            </td>
		                            <td>
		                                <input type="number" style="width: 60px" id="edadAnimalOrigin" class="requirednumero datosGanadero cambioVehiculo"
		                                    onkeypress="return justNumbers(event);" />
		                            </td>
		                            <td>
		                                <input type="text" id="valorAseguradoOrigin" class="requirednumero datosGanadero cambioVehiculo"
		                                    onkeypress="return justNumbers(event);" />
		                            </td>
		                            <td>
		                                <button type="button" class="btn btn-primary btn-xs actualizar-btn" id="btnAnadirAnimal"
		                                    onclick="agregarNuevoAnimal();">
		                                    <span class="glyphicon glyphicon glyphicon-plus"></span>A&ntilde;adir Animal
		                                </button>
		                            </td>
		                        </tr>
		                    </table>
		                    <table id="animales" style="margin-top: 20px;">
		                        
		                    </table>
					</div>
				</div>
			</fieldset> 	

			</section>

			<h2> Pago <i class="fa fa-credit-card fa-2x"></i></h2>
			<section>

				<div class="panel panel-primary">
					<div class="panel-heading">
						Informaci&oacute;n de la Prima
						<span id="resumenTotalPago">Total a Pagar: <b></b></span>
					</div>
					<div class="panel-body">
						<input type="hidden" id="fechaInicialPagos" readonly="readonly" />
						<input type="hidden" id="codigoPagoRegistrado" value="-1"
							readonly="readonly" />
						<input type="hidden" id="estadoCotizacion" value=""
							readonly="readonly" />
						<div id="loading">
							<div class="loading-indicator">
								<img src="../static/images/ajax-loader.gif" /><br /> <br /> <span
									id="loading-msg">Cargando...</span>
							</div>
						</div>
						
						<table>
							<tbody><tr>
								<td colspan="2"><select id="selectDescargas" onchange="cambioDescargaCertificados();">
									<!-- <option value='3'> El formato de débito bancario. </option>
									<option value='4'> El formato del formulario de la UPLA </option>
									<option value='5'> Formato de la Póliza </option> -->
								</select>
								</td>
								<td colspan="2"> 
								
								<button type="button" class="btn btn-success btn-xs descargaCertificado" id="descargar_FichaCotizacion" onclick="abrirFichaCotizacion();"> <span class="glyphicon glyphicon glyphicon-download"></span>Descargar </button>
								<button type="button" class="btn btn-success btn-xs descargaCertificado" id="descargar_certificadoCotizacion" onclick="abrirCertificadoCotizacion();" hidden="hidden"> <span class="glyphicon glyphicon glyphicon-download"></span>Descargar </button>
								<button type="button" class="btn btn-success btn-xs descargaCertificado" id="descargar_certificadoNormasParticulares" onclick="abrirCertificadoNormaParticulares();"  hidden="hidden"> <span class="glyphicon glyphicon glyphicon-download"></span>Descargar </button>
								<!-- <button hidden="hidden" type='button' class='btn btn-success btn-xs descargaCertificado' id='descargar_certificadoDebito' onclick="abrirCertificadoDebito();"> <span class='glyphicon glyphicon glyphicon-download'></span>Descargar </button>
								<button hidden="hidden" type='button' class='btn btn-success btn-xs descargaCertificado' id='descargar_certificadoUPLA' onclick="abrirCertificadoUPLA();"> <span class='glyphicon glyphicon glyphicon-download'></span>Descargar </button>
								<button hidden="hidden" type='button' class='btn btn-success btn-xs descargaCertificado' id='descargar_certificadoPoliza' onclick="abrirCertificadoPoliza();"> <span class='glyphicon glyphicon glyphicon-download'></span>Descargar </button>-->
								
								</td>
								<!-- <td colspan="2">
								
								<button type='button' class='btn btn-success btn-xs descargaCertificado' id='enviar_certificadoCotizacion' onclick="$('#correos_certificado').dialog( 'open' );"> <span class='glyphicon glyphicon glyphicon-send'></span>Enviar Cotización </button></td>
								 -->
								</tr>
							
							<tr height="30px"></tr>
							<tr></tr></tbody></table>
						
						
							<div id="tabbable" class="tabbable">
								<ul class="nav nav-tabs">
								<li class="active"><a href="#primero" data-toggle="tab">Valores a Pagar</a></li>
								<li><a href="#segundo" data-toggle="tab" id="tabFormasPago">Formas
										de Pago</a></li>
								<li><a href="#tercero" data-toggle="tab">Asegurado y Beneficiario
										</a></li>
								</ul>
								<div class="tab-content">
								<!-- Inicio tab valor a pagar -->
								<div class="tab-pane active" id="primero">
									<br>
									<div class="row">
										<div class="col-md-4">
											<div class="thumbnail" style="padding: 20px;">
												<table id="tabla_detalle_pagos"
													class="table table-bordered table-hover">
													<tr>
														<td colspan="2"><center>Detalle Valor a Pagar</center></td>
													</tr>
													<tr>
														<td style="text-align: right">Total Suma Asegurada $</td>
														<td><input id="total_suma_asegurada_vh"
															disabled="disabled" type="text"></input></td>
													</tr>
													<tr style="display: none;" id="filaDescuento">
														<td style="text-align: right">Descuento %</td>
														<td><input id="porcentaje_descuento_vh"
															disabled="disabled" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Prima sin Impuestos $</td>
														<td><input id="prima_sin_impuestos_vh"
															disabled="disabled" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Superintendencia Bancos
															3.50%</td>
														<td><input id="super_bancos_vh" disabled="disabled"
															type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Seguro Campesino 0.50%</td>
														<td><input id="seguro_campesino_vh"
															disabled="disabled" type="text"></input></td>
													</tr>
													<!--tr>
														<td style="text-align: right">Recargo Seguro
															Campesino</td>
														<td><input id="recargo_seguro_campesino_vh"
															disabled="disabled" type="text"></input></td>
													</tr-->
													<tr>
														<td style="text-align: right">Derechos de
															Emisi&oacute;n $</td>
														<td><input id="derecho_emision_vh"
															disabled="disabled" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Subtotal $</td>
														<td><input id="subtotal_vh" disabled="disabled"
															type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">IVA 12.00%</td>
														<td><input id="iva_vh" disabled="disabled"
															type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">TOTAL $</td>
														<td><input id="total_vh" class="total_vh"
															disabled="disabled" type="text"><input
															id="total_vh_origin" disabled="disabled" type="hidden"></td>
													</tr>

												</table>
											</div>
										</div>
										<div class="col-md-8">
											<div class="thumbnail" style="padding: 20px;">
												<table id="tabla_animales_cotizacion"
													class="table table-bordered table-hover dataTable"></table>												
											</div>
											<!-- div class="thumbnail" style="padding: 20px;">
							  		
							    </div-->
										</div>
									</div>


								</div>
								<!--  Fin tab valor a pagar -->

								
								<!-- inicio tab forma de pago-->
								<div class="tab-pane" id="segundo">
									<div class="alert alert-danger" id="msgPopupPagoError"
										style="display: none;">
										<button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										Por favor seleccione su forma de pago.
									</div>

									<div class="alert alert-success" id="msgPopupPagoGrabo"
										style="display: none;">
										<button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										La forma de pago se ha registrado correctamente.
									</div>

									<br>
									<!--  F O R M A S   D E   P A G O   -->
									<table style="width: 300px;" align="center">
										<tr>
											<td><label><b>Forma de Pago:*</b></label></td>
											<td><select id="cboFpFormaPago" name="cboFpFormaPago" class="datosGanadero"
												onchange="" style="width: 150px">
											</select></td>
										</tr>
										<tr style="height: 20px;"></tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago" id="save-pagoContado"
													onclick='guardarPago("contado")'>Guardar</button></td>
										</tr>
									</table>
									<br>

									<!-- *********************************************************************************************************************
														F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   D E B I T O S   B A N C A R I O S 
											     ******************************************************************************************************************-->
									<table class='table table-bordered table-hover'
										style="display: none;" id="forma_debitos">
										<tr>
											<td>Tipo de identificaci&oacute;n del titular Cuenta</td>
											<td><select id="tipo_identificacion_banco"
												class="tipo_identificacion datosGanadero"
												name="cboFpTipoIdentificacionBanco" required="required"></select></td>
										</tr>
										
										<tr>
											<td>Cédula/Ruc del titular Cuenta</td>
											<td><input id="bancoIdentificacion" type="text" class="datosGanadero"
												required="required" onkeypress="return justNumbers(event);"
												onchange="cargarPorIdentificacion('formasPagoDebitoBancario', this.value);"
												name="txtIdentificacionBanco">
												<div class="loading_identificacion" hidden="">
													<span id="loading-msg">Cargando...</span><img
														src="../static/images/ajax-loader.gif" />
												</div></td>
											</td>
										</tr>
										<tr>
											<td>Titular Cuenta</td>
											<td><input id="bancoTitular" type="text" placeholder="" class=""
												required="required"></td>
										</tr>
										<tr>
											<td style="text-align: left;">Banco</td>
											<td><select id="bancoId" name="cboFpBanco" class="datosGanadero"></select></td>
										</tr>
										<tr>
											<td>Tipo Cuenta</td>
											<td><select id="bancoTipoCuenta" name="cboFpTipo"
												required="required" class="datosGanadero">
													<option value="A">Ahorro</option>
													<option value="C">Corriente</option>
											</select></td>
										</tr>
										<tr>
											<td>Número Cuenta</td>
											<td><input id="bancoNumeroCuenta" type="text"
												class="camporequerido validate-not-first datosGanadero"
												onkeypress="return justNumbers(event);"
												onchange="validacionBanco(); validaCuenta();"
												required="required" name="txtFpNumero"></td>
										</tr>
										<tr>
													<td>Fecha inicio de pagos</td>
													<td><input id="txtfechaInicialpago"
														name="txtfechaInicialpago" type="date"></td>
												</tr>
												<tr>
											<td>Cuota Inicial</td>
											<td><input id="txtcuotaInicialbancoPlazo" class="datosGanadero" type="number" value="0" required="required" onkeypress="return justNumbers(event);" name="txtcuotaInicial" onchange="validarCuotaInicial(event);" aria-required="true"></td>
										</tr>
										<tr>
											<td>Plazo</td>
											<td><select id="bancoPlazo" name="cboFpPlazo" onchange="validarCuotaMinima(event);" class="datosGanadero"
												required="required">
													<option value="1">1 mes</option>
													<option value="2">2 meses</option>
													<option value="3">3 meses</option>
													<option value="4">4 meses</option>
													<option value="5">5 meses</option>
													<option value="6">6 meses</option>
													<option value="7">7 meses</option>
													<option value="8">8 meses</option>
													<option value="9">9 meses</option>
													<option value="10">10 meses</option>
											</select></td>
										</tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago" id="save-pagoDebitoBanco"
													onclick='guardarPago("banco")'>Guardar</button></td>
										</tr>
									</table>


									<!-- *********************************************************************************************************************
													     F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   D E B I T O S   C O N   T A R J E T A S
											     ******************************************************************************************************************-->
									<table class='table table-bordered table-hover'
										style="display: none;" id="forma_tarjeta">
										<div class="alert alert-info" id="msgPopupPagoTarjeta"
											style="display: none;">Grabado con exito.</div>
										<tr>
											<td>Tipo de identificaci&oacute;n del titular Tarjeta</td>
											<td><select id="tipo_identificacion_tarjeta"
												class="tipo_identificacion datosGanadero"
												name="cboFpTipoIdentificacionTarjeta" required="required"></select>
											</td>
										</tr>
										<tr>
											<td>Cédula/Ruc del titular Tarjeta</td>
											<td><input id="tarjetaIdentificacion" type="text" class="datosGanadero"
												required="required" onkeypress="return justNumbers(event);"
												title="Ingrese la Cédula/RUC del titular"
												onchange="cargarPorIdentificacion('formasPagoDebitoTarjetas', this.value);"
												name="txtIdentificacionTarjeta">
												<div id="loading_identificacion"
													class="loading_identificacion" hidden="">
													<span id="loading-msg">Cargando...</span><img
														src="../static/images/ajax-loader.gif" />
												</div></td>
											</td>
										</tr>
										<tr>
											<td>Titular Tarjeta</td>
											<td><input id="tarjetaTitular" type="text"
												required="required"
												class="camporequerido validate-not-first datosGanadero"
												title="Ingrese el titular de la Tarjeta" value=""
												name="txtTitularTarjeta"></td>
										</tr>
										<tr>
											<td>Número Tarjeta</td>
											<td><input id="tarjetaNumero" type="text" class="datosGanadero"
												required="required" onpaste="return false"
												placeholder="Ingrese el número de tarjeta"
												onkeypress="return validarEmisorTarjeta(event, this.value);"
												onchange="comprobarTarjetaValida(this.value)"
												name="txtFpNumero" maxlength="16"> <br> <span></span>
											</td>
										</tr>										
										<tr>
											<td>Tarjeta</td>
											<td><select id="tarjetaId" name="cboFpBanco" class="datosGanadero"
												required="required" disabled></select> <br> <img
												id="dinersIcon" class="tarjetasIconos" nemotecnico="diners"
												src="../static/images/diners_club_32.png"
												style="opacity: 0.2; filter: alpha(opacity = 20);" /> <img
												id="discoverIcon" class="tarjetasIconos"
												nemotecnico="discover"
												src="../static/images/discover_32.png"
												style="opacity: 0.2; filter: alpha(opacity = 20);" /> <img
												id="visaIcon" class="tarjetasIconos" nemotecnico="visa"
												src="../static/images/visa_32.png"
												style="opacity: 0.2; filter: alpha(opacity = 20);" /> <img
												id="mastercardIcon" class="tarjetasIconos"
												nemotecnico="mastercard"
												src="../static/images/mastercard_32.png"
												style="opacity: 0.2; filter: alpha(opacity = 20);" /> <img
												id="amexIcon" class="tarjetasIconos" nemotecnico="amex"
												src="../static/images/american_express_32.png"
												style="opacity: 0.2; filter: alpha(opacity = 20);" /></td>
										</tr>
										<tr>
											<td>Mes Expiraci&oacute;n</td>
											<td><select id="tarjetaMesExp" name="cboFpMes" class="datosGanadero"
												required="required">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
											</select></td>
										</tr>
										<tr>
											<td>A&ntilde;o Expiraci&oacute;n</td>
											<td><select id="tarjetaAnioExp" name="cboFpAnio" class="datosGanadero"
												required="required"></select></td>
										</tr>

										<!--tr>
													<td>Fecha inicio de pagos</td>
													<td><input id="txtfechaInicialpago" name="txtfechaInicialpago"  type="date"></td>
												</tr-->
												<td>Cuota Inicial</td>
											<td><input id="txtcuotaInicialtarjetaPlazo" class="datosGanadero" type="number" min="0" value="0" required="required" onkeypress="return justNumbers(event);" name="txtcuotaInicial" onchange="validarCuotaInicial(event); " aria-required="true"></td>
										<tr>
											<td>Plazo</td>
											<td><select id="tarjetaPlazo" name="cboFpPlazo" class="datosGanadero"
												required="required" onchange=" validarCuotaMinima(event); cambioPlazoPago(this.value);">
													<optgroup label="Debito sin Interes">
														<option value="1" class="plazoDebitoTarjeta">1
															mes</option>
														<option value="2" class="plazoDebitoTarjeta">2
															meses</option>
														<option value="3" class="plazoDebitoTarjeta">3
															meses</option>
														<option value="4" class="plazoDebitoTarjeta">4
															meses</option>
														<option value="5" class="plazoDebitoTarjeta">5
															meses</option>
														<option value="6" class="plazoDebitoTarjeta">6
															meses</option>
														<option value="7" class="plazoDebitoTarjeta">7
															meses</option>
														<option value="8" class="plazoDebitoTarjeta">8
															meses</option>
														<option value="9" class="plazoDebitoTarjeta">9
															meses</option>
														<option value="10" class="plazoDebitoTarjeta">10
															meses</option>
													</optgroup>
													<optgroup label="Credito con Interes">
														<option value="12" class="plazoCreditoTarjeta">12
															meses</option>
													</optgroup>

											</select></td>
										</tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago"
													id="save-pagoDebitoTarjeta"
													onclick='guardarPago("tarjeta")'>Guardar</button></td>
										</tr>
									</table>

									<!-- *********************************************************************************************************************
													     		F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   C U O T A S
											     ******************************************************************************************************************-->
									<table id='forma_cuotas' hidden=''
										class='table table-bordered table-hover'>
										<!--tr>
															<td>Fecha inicio de pagos</td>
															<td>
																<input type="date" name="txtfechaInicialpago" id="txtfechaInicialpago" >
															</td>
														</tr-->
										<tr></tr>
										<tr>
											<td>Cuota Inicial</td>
											<td><input id="txtcuotaInicial" type="number" class="datosGanadero"
												required="required" onkeypress="return justNumbers(event);"
												name="txtcuotaInicial" min="0" value="0"
												onchange="validarCuotaInicial(event);"></td>
										</tr>
										<tr>
											<td>Plazo</td>
											<td><select id="cboFpPlazo" name="cboFpPlazo" class="datosGanadero"
												onchange="javascript:calculaCuotas(this.value);"
												required="required">
													<option value="0">Seleccione una opción</option>
													<option value="1">1 mes</option>
													<option value="2">2 meses</option>
													<option value="3">3 meses</option>
													<option value="4">4 meses</option>
													<option value="5">5 meses</option>
													<option value="6">6 meses</option>
													<option value="7">7 meses</option>
													<option value="8">8 meses</option>
													<option value="9">9 meses</option>
													<option value="10">10 meses</option>
											</select></td>
										</tr>
										<tr id="rowDetallePagos">
											<td colspan="2" align='center'>
												<table border="1"
													style="width: 500px; margin: 10px; padding: 5px;">
													<tr>
														<td align="center"><b>Nro.</b></td>
														<td align="center"><b>Número de Cheque</b></td>
														<td align="center"><b>Valor</b></td>
														<td align="center"><b>Fecha Cobro</b></td>
													</tr>
													<tbody id="detallePagoCuotas">

													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago" id="save-pagoCuotas"
													onclick='guardarPago("cuota")'>Guardar</button></td>
										</tr>
									</table>
									<!-- F O R M A S   D E   P A G O  -->
								</div>
								<!-- Fin tab forma de pago  -->
								
								<!-- Inicio Beneficiario y Asegurado  -->
								<div class="tab-pane" id="tercero">
									<div class="alert alert-danger" id="msgPopupEndosoBeneficiarioError" hidden="hidden">
									<!--  <button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button> -->
										<div id='mensajeErrorEndosoBeneficiario'></div>
									</div>

									<div class="alert alert-success" id="msgPopupEndosoBeneficiarioGrabo" hidden="hidden">
										<!-- <button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button> -->
										<div id='mensajeGraboEndosoBeneficiario'></div>
									</div>
								<div class="panel panel-primary">
						     <div class="panel-heading">Datos del Asegurado</div>
								<div class="panel-body">
									<table class="table table-bordered table-hover" id="formaAsegurado">
										<input id="asegurado_id" type="hidden"/>
										<tr>
											<td>Tipo de identificaci&oacute;n del Asegurado</td>
											<td><select id="tipo_identificacion_asegurado"
												class="tipo_identificacion datosGanadero"
												name="tipo_identificacion_asegurado" required="required"></select>
											</td>
											
											<td>Identificaci&oacute;n del Asegurado</td>
											<td><input id="identificacion_asegurado"
												class="identificacion datosGanadero" onChange="cargarPorIdentificacion('datosAsegurado',this.value)"
												name="identificacion_asegurado" required="required">
											</td>
										</tr>
										<tr id='filaNaturalAsegurado'>
											<td>Nombres:</td>
											<td><input id="nombres_asegurado"
												class=" datosGanadero"
												name="nombres_asegurado" required="required">
											</td>
											
											<td>Apellidos:</td>
											<td><input id="apellidos_asegurado"
												class=" datosGanadero"
												name="apellidos_asegurado" required="required">
											</td>
										</tr>
										<tr id='filaJuridicaAsegurado' hidden='hidden'>
											<td>Nombre Completo:</td>
											<td><input id="nombre_completo_asegurado"
												class="datosGanadero"
												name="nombre_completo_asegurado" required="required">
											</td>
										</tr>
												<tr>
													<td colspan='4'>
														<div align="center">
															<button type="button" class="btn btn-primary" id="guardar_asegurado" onClick="guardarAsegurado('guardar');">Guardar</button>
														</div>
													</td>

												</tr>
											</table>
									
								</div>
									</div>
							 <div class="panel panel-primary">
						     <div class="panel-heading">Datos del Beneficiario</div>
								<div class="panel-body">
									<table class='table table-bordered table-hover' id="formaBeneficiario">
										<input id="endoso_beneficiario_id" type="hidden">
										<tr>
											<td>Beneficiario</td>
											<td>
											<input style="width:90%" type="select" id="beneficiario" name="beneficiario" class="">
											</td>
											</tr>
											<!-- <tr>
											<td>Monto</td>
											<td><input type="number" value='0' min='0' id="valor_endoso_beneficiario"
												class="datosGanadero"
												name="valor_endoso_beneficiario">
											</td>
										</tr> -->
										<tr>
													<td>
														<div align="center">
															<button type="button" class="btn btn-primary" id="guardar_beneficiario" onClick="guardarBeneficiario('guardar');">Guardar</button>
														</div>
													</td>
													<td>
														<div align="center">
															<button type="button" class="btn btn-danger" id="eliminar_beneficiario" onClick="guardarBeneficiario('eliminar');">Eliminar</button>
														</div>
													</td>
												</tr>
									</table>
									
								</div>
								</div>
									
								</div>
								
								<!-- Fin Beneficiario y Asegurado  -->
								</div>
							</div>



					</div>
				</div>
			</section>

			<h2>Datos Factura<i class="fa fa-edit fa-2x"></i></h2>
				<section>
				<!-- *********************************************************************************************************************
									F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   D A T O S   D E   U P L A
				******************************************************************************************************************-->

			             <fieldset class="border">
		                	<legend class="border">Datos del Asegurado y de Facturación</legend>
							 <!-- Inicio datos poliza - 4 columnas -->
					
							<!-- Inicio Ubicación Persona Natural -->
							<div class="panel panel-primary" id="ubicacionPersonaNatural" hidden="true">
						     <div class="panel-heading">Ubicación Persona Natural</div>
								<div class="panel-body">		
										<table>
										  <tr>
										    <td colspan="1"><label><b>Zona:*</b></label></td>
										    <td colspan="1">
										    	<select id="zona_direccion_natural" class="datosGanadero" onChange="cambiaZonaDireccion(event,'N');">
										    		<option value="">Seleccione</option>
										    		<option value="U">Urbano</option>
										    		<option value="R">Rural</option>
										    	</select>
										    </td>
										    <td colspan="1"><label><b>Provincia:*</b></label></td>
										    <td colspan="1"><select id="provincia_direccion_cliente_natural" class="provincia datosGanadero" required="required"></select></td>
										    </tr>
										    <tr>
										    <td id="canton_cliente_label" colspan="1" hidden=""><label><b>Canton:*</b></label></td>
										    <td id="canton_cliente_select" colspan="1" hidden=""><select id="canton_direccion_cliente_natural" name="canton_direccion_cliente_natural" class="canton datosGanadero"></select></td>
										    <td id="parroquia_cliente_label" colspan="1" hidden=""><label><b>Parroquia:*</b></label></td>
										    <td id="parroquia_cliente_select" colspan="1" hidden=""><select id="parroquia_direccion_cliente_natural" name="parroquia_direccion_cliente_natural" class="parroquia datosGanadero"></select></td>
										    <td id="ciudad_cliente_label" colspan="1" hidden=""><label><b>Ciudad:*</b></label></td>
										    <td id="ciudad_cliente_select" colspan="1" hidden=""><select id="ciudad_direccion_cliente_natural" name="ciudad_direccion_cliente_natural" class="ciudad datosGanadero"></select></td>
										    
										    </tr>
										    <tr>
										    <td colspan="1"><label><b>Calle Principal:*</b></label></td>
										    <td colspan="1"><input type="text" name="calle_principal_direccion_natural" id="calle_principal_direccion_natural" class="datosGanadero" required="required"></td>
										    <td colspan="1"><label><b>Número:*</b></label></td>
										    <td colspan="1"><input type="text" id="numero_direccion_natural" class="datosGanadero" name="numero_direccion_natural" required="required"></td>
										     </tr>
										    <tr>
										    <td colspan="1"><label><b>Calle Secundaria:*</b></label></td>
										    <td colspan="1"><input type="text" id="calle_secundaria_direccion_natural" class="datosGanadero" name="calle_secundaria_direccion_natural" required="required"></td>
										    <td colspan="1"><label><b>Datos Referencia:*</b></label></td>
										    <td colspan="3"><input type="text" id="referencia_direccion_natural" class="datosGanadero" name="referencia_direccion_natural" ></td>
										  </tr>
											<tr>
											    <td><label><b>Lugar Nacimiento:</b></label></td>
											    <td><input type="text" id="lugar_nacimiento_natural" class="datosGanadero" name="lugar_nacimiento_natural" ></td>
											    <td><label><b>Fecha de Nacimiento:*</b></label></td>
											    <td><input type="date" id="fecha_nacimiento_natural" class="datosGanadero" name="fecha_nacimiento_natural" required="required"></td>
											</tr>
											<tr>
											    <td colspan="1"><label><b>Telefono:*</b></label></td>
											    <td colspan="1"><input type="text" id="telefono_cliente_natural"  name="telefono_direccion_solicitante" maxlength="10" class="telefono datosGanadero" onkeypress="validarKeyPress(event,/[0-9]/)" required="required"></td>
											    <td colspan="1"><label><b>Celular:*</b></label></td>
											    <td colspan="1"><input type="text" id="celular_cliente_natural" class="datosGanadero" maxlength="10" name="celular_direccion_solicitante" onkeypress="validarKeyPress(event,/[0-9]/)"  required="required"></td>
											    
											  </tr>
											  <tr>
											    <td><label><b>Email:</b></label></td>
											    <td><input type="text" id="mail_cliente_natural" name="mail_cliente_natural" class="mail datosGanadero"></td>
											  </tr>
									</table>																	
								</div>
							</div>
							<!-- Fin Ubicación Persona Natural -->
							
							<!-- Fin Ubicación Persona Juridica -->
							<div class="panel panel-primary" id="ubicacionPersonaJuridica" hidden="true">
						     <div class="panel-heading">Ubicacion Persona Juridica</div>
								<div class="panel-body">		
							<table>
							  <tr>
							   <td colspan="5"><label><b>Dirección de la Matriz</b></label></td>
							  </tr>
							  <tr>
							    <td colspan="1"><label><b>Zona:*</b></label></td>
							    <td colspan="1">
							    	<select id="zona_direccion_matriz_juridica" name="zona_direccion_matriz_juridica" onChange="cambiaZonaDireccion(event,'J');" required="required" class="datosGanadero">
							    		<option value="">Seleccione</option>
							    		<option value="U">Urbano</option>
							    		<option value="R">Rural</option>
							    	</select>
							    </td>
							    <td colspan="1"><label><b>Provincia:*</b></label></td>
							    <td colspan="1"><select id="provincia_direccion_matriz_juridica" name="provincia_direccion_matriz_juridica" class="provincia datosGanadero" required="required" ></select></td>
							    </tr>
							    <tr>
							    <td id="canton_matriz_label" colspan="1" hidden=""><label><b>Canton:*</b></label></td>
							    <td id="canton_matriz_select" colspan="1" hidden=""><select id="canton_direccion_matriz_juridica" name="canton_direccion_matriz_juridica" class="canton datosGanadero"></select></td>
							    <td id="parroquia_matriz_label" colspan="1" hidden=""><label><b>Parroquia:*</b></label></td>
							    <td id="parroquia_matriz_select" colspan="1" hidden=""><select id="parroquia_direccion_matriz_juridica" name="parroquia_direccion_matriz_juridica" class="parroquia datosGanadero"></select></td>
							    <td id="ciudad_matriz_label" colspan="1" hidden=""><label><b>Ciudad:*</b></label></td>
							    <td id="ciudad_matriz_select" colspan="1" hidden=""><select id="ciudad_direccion_matriz_juridica" name="ciudad_direccion_matriz_juridica" class="ciudad datosGanadero"></select></td>
							    
							    </tr>
								  <tr>
								    <td ><label><b>Calle Principal:*</b></label></td>
								    <td colspan='3' ><input type="text" id="calle_principal_direccion_juridica" name="calle_principal_direccion_juridica" required="required" class="datosGanadero"></td>
								    </tr>
								  <tr><td ><label><b>Número:*</b></label></td>
								    <td ><input type="text" id="numero_direccion_juridica" name="numero_direccion_juridica" required="required" class="datosGanadero"></td>
								    <td ><label><b>Calle Secundaria:*</b></label></td>
								    <td ><input type="text" id="calle_secundaria_direccion_juridica" name="calle_secundaria_direccion_juridica" required="required" class="datosGanadero"></td>
								     </tr>
								  <tr>
								    <td  ><label><b>Datos Referencia:*</b></label></td>
								    <td colspan="3"><input type="text" id="referencia_direccion_juridica" name="referencia_direccion_juridica" class="datosGanadero"></td>
								  </tr>
								  <tr>
								    <td colspan="1"><label><b>Telefono:*</b></label></td>
								    <td colspan="1"><input type="text" id="telefono_juridica"  name="telefono_direccion_solicitante" maxlength="10" class="telefono datosGanadero" onkeypress="validarKeyPress(event,/[0-9]/)" required="required"></td>
								    <td colspan="1"><label><b>Celular:*</b></label></td>
								    <td colspan="1"><input type="text" id="celular_representante_legal" class="datosGanadero" maxlength="10" name="celular_direccion_solicitante" onkeypress="validarKeyPress(event,/[0-9]/)"  required="required"></td>
								    
								  </tr>
								  <tr>
								    <td><label><b>Email:</b></label></td>
								    <td><input type="text" id="mail_representante_juridico" name="mail_direccion_cliente" class="mail datosGanadero"></td>
								  </tr>
								</table>																	
								</div> 
							</div> 
							<!-- Fin Ubicación Persona Juridica -->
							
							 <!-- Inicio datos politica Natural -->
							<div class="panel panel-primary" id="datosPoliticaNatural" hidden="true">
						     <div class="panel-heading">Actividad Politica</div>
								<div class="panel-body">
								<table>
								  <tr>
								  <td colspan='2'><label><b>Usted es una Persona Expuesta Politicamente :</b></label>
								  </td>
								    </tr>
								    <tr>
								    <td colspan='2'>
								    <select id="expuesto_cliente_natural" class="datosGanadero">
								    	<option value="">Seleccione</option>
							    		<option value="1">SI</option>
							    		<option value="0">NO</option>
								    </select>
  									</td>
								    </tr>
								    <tr>
								    <td colspan><label><b>Cargo que desempeña:</b></label></td>
								    <td><input type="text" id="cargo_expuesto_cliente_natural" class="datosGanadero" name="cargoDesempena">
								    </tr>
								    <div class="well"><b>Persona Expuesta Politicamente:</b> Es la persona que desempe&ntilde;a o ha desempe&ntilde;ado funciones publicas destacadas en el pais o 
								    en el exterior, que por su perfil pueda exponer en mayor grado a la entidad al riesgo de lavado de activos y financiamiento de delitos, por ejemplo,
								    Jefe de Estado o de un gobierno, politico de alta jerarquia, funcionario importante de partidos politicos. Las relaciones comerciales con los parientes
								    dentro del segundo grado de consaguinidad o primero de afinidad y los colaboradores cercanos de una persona politicamente expuesta, implica que las instituciones
								    del sistema financiero apliquen procedimientos de debida diligencia ampliados.</div>
								</table>	
								</div> 
							</div>
							<!-- Fin datos politica Natural -->
							
							<!-- Inicio datos politica Juridica -->
							<div class="panel panel-primary" id="datosPoliticaJuridica" hidden="true">
						     <div class="panel-heading">Actividad Politica</div>
								<div class="panel-body">
								<table>
								  <tr>
								  <td colspan='4'><label><b>Usted es una Persona Expuesta Politicamente :</b></label>
								    </td>
  									</tr>
								    <tr>
								    <td colspan='4'>
								    <select id="expuesto_representante_juridica" placeholder="Escoja una opción" class="datosGanadero">
								    	<option value="">Seleccione</option>
							    		<option value="1">SI</option>
							    		<option value="0">NO</option>
								    </select>
  									</td>
  									</tr>
								    <tr>
								    <td><label><b>Cargo que desempe&ntilde;a:</b></label></td>
								    <td><input type="text" id="cargo_expuesta_representante_juridica" name="cargoDesempena" class="datosGanadero">
								    </tr>
								    <div class="well"><b>Persona Expuesta Politicamente:</b> Es la persona que desempe&ntilde;a o ha desempe&ntilde;ado funciones publicas destacadas en el pais o 
								    en el exterior, que por su perfil pueda exponer en mayor grado a la entidad al riesgo de lavado de activos y financiamiento de delitos, por ejemplo,
								    Jefe de Estado o de un gobierno, politico de alta jerarquia, funcionario importante de partidos politicos. Las relaciones comerciales con los parientes
								    dentro del segundo grado de consaguinidad o primero de afinidad y los colaboradores cercanos de una persona politicamente expuesta, implica que las instituciones
								    del sistema financiero apliquen procedimientos de debida diligencia ampliados.</div>
								</table>	
								</div> 
							</div>
							<!-- Fin datos politica Juridica -->
							
							<!-- Inicio datos Situacion Financiera Natural-->
							<div  class="panel panel-primary" id="situacionFinancieraNatural" hidden="true">
						     <div class="panel-heading">Situacion Financiera: Total de activos y pasivos</div>
								<div class="panel-body">
									<table>
									  <tr>
									  <td><label><b>Salario Mensual:</b></label></td>
									    <td><input type="number" min="0" value="0" id="salario_mensual_natural" name="" class="datosGanadero" onchange="calcularIngresosEgresosNatural()"></td>
	    							    <td><label><b>Activos:</b></label></td>
									    <td><input type="number" min="0" value="0" id="activos_natural" name="" class="datosGanadero"  onchange="calcularPatrimonioNatural()">
									    </tr>
									    <tr>
									    <td><label><b>Otros Ingresos:</b></label></td>
									    <td><input type="number" min="0"  value="0" id="otros_ingresos_natural" name="" class="datosGanadero" onchange="calcularIngresosEgresosNatural()" ></td>
	    							    <td><label><b>Pasivos:</b></label></td>
									    <td><input type="number" min="0"  value="0" id="pasivos_natural" name="" class="datosGanadero" onchange="calcularPatrimonioNatural()"></td>
									    </tr>
									     <tr>
									    <td><label><b>Egresos:</b></label></td>
									    <td><input type="number" min="0" value="0" id="egresos_natural" name="egresos" class="datosGanadero"  onchange="calcularIngresosEgresosNatural()"></td>
	    							    <td><label><b>A-P= Patrimonio:</b></label></td>
									    <td><input type="number" min="0" value="0" id="patrimonio_natural" disabled="disabled" name="patrimonio" class="datosGanadero"></td>
									    </tr>
									    <tr>
									    <td><label><b>V/Neto Ingreso - Egreso:</b></label></td>
									    <td><input type="number" id="ingresos_egresos_natural" disabled="disabled" name="" class="datosGanadero"></td>
									    </tr>
									</table>	
								</div> 
							</div>
							<!-- Fin datos Situacion Financiera Natural-->
							
							<!-- Inicio datos Situacion Financiera Juridica-->
							<div class="panel panel-primary" id="situacionFinancieraJuridica" hidden="true">
						     <div class="panel-heading">Situacion Financiera: Total de activos y pasivos</div>
								<div class="panel-body">
								<table>
								  <tr>
								  <td><label><b>Ingresos Mensual:</b></label></td>
								    <td><input type="number" min="0"  value="0" id="salario_mensual_juridica" name="salarioMensual" class="datosGanadero" onchange="calcularIngresosEgresosJuridica()"></td>
    							    <td><label><b>Activos:</b></label></td>
								    <td><input type="number" min="0"  value="0" id="activos_juridica" name="activosFinanciero" class="datosGanadero" onchange="calcularPatrimonioJuridica()">
								    </tr>
								    <tr>
								    <td><label><b>Otros Ingresos:</b></label></td>
								    <td><input type="number"  min="0"  value="0" id="otros_ingresos_juridica" name="otrosIngresos" class="datosGanadero" onchange="calcularIngresosEgresosJuridica()"></td>
    							    <td><label><b>Pasivos:</b></label></td>
								    <td><input type="number" min="0"  value="0" id="pasivos_juridica" name="pasivosFinanciero" class="datosGanadero" onchange="calcularPatrimonioJuridica()"></td>
								    </tr>
								     <tr>
								    <td><label><b>Egresos:</b></label></td>
								    <td><input type="number" min="0"  value="0" id="egresos_juridica" name="egresos" class="datosGanadero" onchange="calcularIngresosEgresosJuridica()"></td>
    							    <td><label><b>A-P= Patrimonio:</b></label></td>
								    <td><input type="number" min="0"  value="0" disabled="disabled" id="patrimonio_juridica" name="patrimonio" class="datosGanadero"></td>
								    </tr>
								    <tr>
								    <td><label><b>V/Neto Ingreso - Egreso:</b></label></td>
								    <td><input type="number" min="0"  value="0" disabled="disabled" id="ingresos_egresos_juridica" name="vNeto" class="datosGanadero"></td>
								    </tr>
								</table>	
								</div> 
							</div>
							<!-- Fin datos Situacion Financiera Juridica-->
							
		            </fieldset>		       
			</section>
			<h2>Emision <i class="fa fa-check-circle-o fa-2x"></i></h2>
			<section>

				<div class="alert alert-success" id="msgPopupFinalizadoCorrectamente"
					style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					Su cotización ha sido enviada de manera satisfactoria.
				</div>
							
				<div class="alert alert-success" id="msgPopupEmitidoCorrectamente"
					style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					La poliza se ha emitido correctamento.
				</div>
						
				<div class="alert alert-success" id="msgPopupEmitidoRechazado"
					style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					La cotización ha sido rechazada correctamente.
				</div>
						
				<div class="panel panel-primary" id="datosFinales" hidden="true">
					<div class="panel-heading">Datos Finales</div>
					<div class="panel-body">
						<table id="tablaFinalVehiculos">
							<tr>
								<td colspan="4"><label>Seleccione la fecha con la que desea que se emita la poliza de esta cotizaci&oacute;n: </label></td>
								<td colspan="3"><input type="date" id="fecha_inicio_vigencia" name="fecha_inicio_vigencia" required="required"></td>
							</tr>
						</table>
						<div id="loading_tablaFinal" class="loading_identificacion" hidden="" ><span id="loading-msgTablaFinal"></span><img  src="../static/images/ajax-loader.gif" /></div>
					</div>
				</div>

				<table style="width: 100%">
					<tr id="filaEnviarCotizacion">
						<td >
							<table>
								<tr>
									<td style="text-align: center">
										<button type='button' id="finalizar_Cotizacion" class='btn btn-success btn-xs'> <span class='glyphicon glyphicon glyphicon-check'></span>Confirmar y Enviar
										</button>
									</td>
								</tr>
								<tr>
									<td>
										<br/>
										<br/>
										<br/>
										<label>* Declaro que toda la información proporcionada es veraz, estoy en pleno conocimiento de que cualquier
										falsedad u omisión deliberada en esta solicitud, es causa suficiente para nulitar la póliza que se emita a mi
										favor, de acuerdo a las condiciones generales de la misma.</label>
									</td>
								</tr>
							</table>
							
						</td>
					</tr>
					<tr id="filaAprobarCotizacion" hidden="true">
						<td >
							<table>
								<tr>
									<td style="text-align: center">
										<button type="button" id="Aceptar_Cotizacion" class='btn btn-success btn-xs'> <span class='glyphicon glyphicon glyphicon-check'></span>Aceptar Cotización </button>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-success btn-xs" id="Denegar_Cotizacion"> <span class='glyphicon glyphicon glyphicon-send'></span>  Negar Cotización  </button>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="filaAprobadaClienteCotizacion" hidden="true">
						<td >
							<table>
								<tr>
									<td style="text-align: center">
										<button type="button" id="Aprobada_Cliente_Cotizacion" class='btn btn-success btn-xs'> <span class='glyphicon glyphicon glyphicon-check'></span>Aceptada por el Cliente </button>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-success btn-xs" id="Negada_Cliente_Cotizacion"> <span class='glyphicon glyphicon glyphicon-send'></span>  Negada por el Cliente </button>
									</td>
								</tr>
								<tr>
									<td>
										<br/>
										<br/>
										<br/>
										<label>* Luego de que el cliente acepte la cotización, se enviará nuevamente a QBE para proceder con la emisión de la póliza y la facturación de la misma.</label>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="filaEmitirCotizacion" hidden="true">
						<td >
							<table>
								<tr>
									<td style="text-align: center">
										<button type="button" id="Aprobar_Cotizacion" class='btn btn-success btn-xs'> <span class='glyphicon glyphicon glyphicon-check'></span>Emitir Póliza </button>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" class="btn btn-success btn-xs" id="Negar_Cotizacion"> <span class='glyphicon glyphicon glyphicon-send'></span>  NO Emitir Póliza  </button>
									</td>
								</tr>
								<tr>
									<td>
										<br/>
										<br/>
										<br/>
										<label>* Al aprobar la cotización el sistema emitirá una poliza. Si niega la cotización se notificará al solicitante.</label>
									</td>
								</tr>
							</table>
							
						</td>
					</tr>
				</table>								
			</section>
		</form>


	</div>
</body>
</html>
