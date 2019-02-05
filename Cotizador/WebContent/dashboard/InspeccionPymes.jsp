<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../static/js/jquery.min.js"></script>

<link rel="stylesheet" href="../static/css/jquery-steps/normalize.css">
<link rel="stylesheet" href="../static/css/jquery-steps/main.css">
<link rel="stylesheet" href="../static/css/jquery-steps/jquery.steps.css">
<link rel="stylesheet" href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css">

<link rel="stylesheet" href="../static/css/select2/select2.css">
<script src="../static/js/jquery-steps/modernizr-2.6.2.min.js"></script>
<script src="../static/js/jquery-steps/jquery.cookie-1.3.1.js"></script>
<script src="../static/js/jquery-steps/jquery.steps.min.js"></script>
<script src="../static/js/jquery.validate.js"></script>
<script src="../static/js/bootstrap.min.js"></script>

<script src="../static/js/util.js"></script>
<script src="../static/js/jquery-dynamic-url/jquery.dynamic-url.js"></script>
<script src="../static/js/select2.js"></script>
<!--  	para el tooltipster -->
<script src="../static/js/jquery-ui/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="../static/js/jquery-ui/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css"
	href="../static/css/tooltipster.css" />
<script type="text/javascript" src="../static/js/jquery.tooltipster.js"></script>
<script type="text/javascript"
	src="../static/js/jquery.tooltipster.min.js"></script>
<!-- Para el Datepicker-->
<link href="../static/css/datepicker.css" rel="stylesheet"/>
<script src="../static/js/bootstrap-datepicker.js"></script>

<!-- KENDO -->
<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
<script src="../static/js/Kendo/kendo.all.min.js"></script>
<script src="../static/js/Kendo/kendo.web.min.js"></script>

<script id="tipoObjetoMetodos" src="../static/js/pymes/inspeccion.pymes.js"></script>
<script src="../static/js/cotizador/comun.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>

<SCRIPT>
	function initialize() {
		var myLatlng = new google.maps.LatLng(-0.176896, -78.480749);
		if ($("#txt_Latitud").val() != '' && $("#txt_Latitud").val() >= -5.05
				&& $("#txt_Latitud").val() == 1.50
				&& $("#txt_Longitud").val() >= -91.75
				&& $("#txt_Longitud").val() == -75.00) {
			myLatlng = new google.maps.LatLng($("#txt_Latitud").val(), $(
					"#txt_Longitud").val());
		} else {
			changeLocation();
			$("#txt_Latitud").val('');
			$("#txt_Longitud").val('');
			return;
		}

		var mapOptions = {
			zoom : 13,
			center : myLatlng
		}
		var map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
		
		 google.maps.event.addListenerOnce(map, 'idle', function () {
             var currentCenter = map.getCenter();
             google.maps.event.trigger(map, 'resize');
             map.setCenter(currentCenter);

             map.setZoom(13);
         });

		var marker = new google.maps.Marker({
			position : myLatlng,
			map : map,
			title : 'Hello World!',
			draggable : true
		});
		google.maps.event.addListener(marker, 'dragend', function(ev) {
			$("#txt_Latitud").val(marker.getPosition().lat().toFixed(6));
			$("#txt_Longitud").val(marker.getPosition().lng().toFixed(6));
		});
	}

	
	function changeLocation() {
		var myLatlng = new google.maps.LatLng(-0.176896, -78.480749);

		var mapOptions = {
			zoom : 13,
			center : myLatlng
		}
		var map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
		
		google.maps.event.addListenerOnce(map, 'idle', function () {
            var currentCenter = map.getCenter();
            google.maps.event.trigger(map, 'resize');
            map.setCenter(currentCenter);

            map.setZoom(13);
        });

		var marker = new google.maps.Marker({
			position : myLatlng,
			map : map,
			title : 'Hello World!',
			draggable : true
		});

		$("#txt_Latitud").val(marker.getPosition().lat().toFixed(6));
		$("#txt_Longitud").val(marker.getPosition().lng().toFixed(6));

		google.maps.event.addListener(marker, 'dragend', function(ev) {
			$("#txt_Latitud").val(marker.getPosition().lat().toFixed(6));
			$("#txt_Longitud").val(marker.getPosition().lng().toFixed(6));
		});
	}

	function valida() {
		var result = "";
		if ($("#ddl_Pais").val() == -1) {
			result = result + "Seleccione el País \n";
		}
		if ($("#ddl_Provincia").val() == -1) {
			result = result + "Seleccione la Provincia \n";
		}
		if ($("#ddl_Ciudad").val() == -1) {
			result = result + "Seleccione la Ciudad \n";
		}
		if ($("#ddl_Canton").val() == -1) {
			result = result + "Seleccione el Cantón \n";
		}
		if ($("#txt_Latitud").val() == '') {
			result = result
					+ "Seleccione en el mapa la ubicación aproximada \n";
		}
		if (!($("#txt_Latitud").val() >= -5.05
				&& $("#txt_Latitud").val() == 1.50
				&& $("#txt_Longitud").val() >= -91.75 && $("#txt_Longitud")
				.val() == -75.00)) {
			result = result
					+ "Seleccione una ubicación que corresponda al Ecuador \n";
		}

		if (result != '') {
			alert(result);
			return false;
		} else {
			var cadena = $("#txt_UbicacionPrincipal").val();
			$("#txt_UbicacionPrincipal").val(cadena.toUpperCase());
			cadena = $("#txt_UbicacionSecundaria").val();
			$("#txt_UbicacionSecundaria").val(cadena.toUpperCase());

			return true;
		}
	}
</SCRIPT>
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
	font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
	font-weight: bold;
}

.tab_strip_direccion {
	height: 300px;
}

#map-canvas {
    width:100%;
    height:500px;
}
</style>

<script>
	//eventos de objetos
	var editoVehiculo = false;
	var tieneDescuento = false;
	var cargadoDatosUPLA = false;
	var solicitarInspeccionArr = [];
	$(document).ready(function() {
		cargarInicalInspeccion();
		/* $('#add').on('shown', function() {
			$('.modal-body', this).css({
				width : 'auto',
				height : 'auto',
				'max-height' : '100%'
			});
			initialize();
		}); */
		
		$("#add").on("shown.bs.modal", function () {
			initialize();
		}); 
	});
</script>


</head>
<body>
	<%
		// Permitimos el acceso si la session existe		
		if (session.getAttribute("login") == null) {
			response.sendRedirect("/CotizadorWeb");
		}
	%>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->


	<!-- ************************************************************
	*      I N I C I O   D E   L A   V E N T A N A    M O D A L
	 *****************************************************************-->
	<button hidden="hidden" class="btn btn-primary" data-toggle="modal"
		data-target="#add" id="addButton">
		<span hidden="hidden" class="glyphicon glyphicon-plus"></span> &nbsp;
		Nuevo
	</button>
	<!-- Modal -->
	<div class="modal fade" id="add" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px !important;">
			<div class="modal-content">
				<form id="formCrud">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Cerrar</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Inspecci&oacute;n</h4>
					</div>
					<div class="modal-body">
						<div class="alert alert-success" id="msgPopup">La
							configuración se grabo con éxito.</div>
						<div class="form-group">
							<div id="tabstrip">
								<ul>
									<li class="k-state-active">Datos de la Inspección</li>
									<li>Ubicación</li>
									<li>Adjuntos</li>
								</ul>
								<div class="tab_strip_direccion">
									<br />
									<table>
										<tr>
											<td>Tipo de Construcción:</td>
											<td><select name="tipo_construccion"
												id="tipo_construccion" required="required"
												class="datosGanadero"></select></td>
											<td>Tipo de Ocupación:</td>
											<td><select name="tipo_ocupacion" id="tipo_ocupacion" style="width: 300px"
												required="required" class="datosGanadero"></select></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>No. total Pisos:</td>
											<td><input type="text"
												id="numero_total_pisos" required="required"
												class="datosGanadero" min="1" style="width: 70px"></input></td>
												<td>Año de Construcción:</td>
											<td><input type="text"
												id="anio_construccion" required="required"
												class="datosGanadero" min="1" style="width: 70px"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Extintores</td>
											<td><input type="checkbox" id="tiene_extintores"
												class="datosGanadero" name="extintores"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Detectores de humo</td>
											<td><input type="checkbox" id="tiene_detector_humo"
												class="datosGanadero" name="tiene_detector_humo"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Sprinklers</td>
											<td><input type="checkbox" id="tiene_spinklers"
												class="datosGanadero" name="tiene_spinklers"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Alarma Monitoreada</td>
											<td><input type="checkbox" id="tiene_alarma_monitoreada"
												class="datosGanadero" name="tiene_alarma_monitoreada"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Guardian&iacute;a</td>
											<td><input type="checkbox" id="tiene_guardiania"
												class="datosGanadero" name="tiene_guardiania"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Otros</td>
											<td colspan="3"><input type="text" id="tiene_otros"
												class="datosGanadero" name="tiene_otros"></td>
										</tr>
									</table>

								</div>
								<div class="tab_strip_direccion" align="center">
								<br/>
									<table style="width: 90%">
										<tr>
											<TD>Latitud: </TD>
											<TD>
										           <INPUT id="txt_Latitud" TYPE="TEXT" CLASS="required" NAME="gpsX"  />
										       </TD>
											<TD>Longitud: </TD>
											<TD>
												<INPUT id="txt_Longitud" TYPE="TEXT" CLASS="required" NAME="gpsY"/>
											</TD>
											<td>
												<button type="button"
													class="btn btn-success btn-xs actualizar-btn">
													<span class="glyphicon glyphicon glyphicon-edit"></span>
													Ver en el mapa
												</button>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<TR>
									        <TD COLSPAN="5">
									            <div style="height:300px; width:100%" id="map-canvas"></div>
									        </TD>
									    </TR>
									</table>
								</div>
								<div class="tab_strip_direccion">
								<br/>
								<br/>
									<table>
										<tr>
											<td>Informe de Inspección:</td>
										</tr>
										<tr>
											<td><input name="files" id="files" type="file" /></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="cerrar-popup"
							data-dismiss="modal">Cerrar</button>
						<button type="button" class="btn btn-primary" id="guardarNoAprobado"
							onclick='grabarInspeccion("2")' data-dismiss="modal">Riesgo NO sugerido</button>
						<button type="button" class="btn btn-primary" id="guardarAprobado"
							onclick='grabarInspeccion("1")' data-dismiss="modal">Riesgo sugerido</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- *******  F I N ****** -->

	<div class="content">
		<div
			style="position: absolute; margin-top: 15px; margin-left: 10px; font-size: larger; color: #003da5;">
			<b>Cotizaci&oacute;n # <span id="cotizacion_id"></span></b> 
			<input type="hidden" value="" id="cotizacionDetalleId"><br> 
			<input type="hidden" value="" id="numeroDireccion"><br>
		</div>
		<br /> <br /> <br />
		<div>
			<fieldset class="border">
				<legend>Listado de Direcciones</legend>
				<input type="hidden" value="1" id="contadorDirecciones"><br>
				<table id="direcciones">
					<tr>
						<td>
							<div class="panel panel-primary">
								<div class="panel-body">
									<table>
										<tr>
											<td style="width: 10%"><label><b>Provincia:*</b></label></td>
											<td style="width: 25%"><select
												id="ubicacion_provincia_1" required="required"
												class="datosGanadero" disabled="disabled"></select></td>
											<td style="width: 5%"><label><b>Ciudad:*</b></label></td>
											<td style="width: 15%"><select id="ubicacion_canton_1"
												required="required" class="datosGanadero" disabled="disabled"></select></td>
											<td style="width: 25%"></td>
											<td style="width: 20%"></td>
											<!-- <td style="width: 10%"><label><b>Parroquia:*</b></label></td>
												<td style="width: 25%"><select
													id="ubicacion_parroquia1" required="required"
													class="datosGanadero"></select></td> -->
											<td style="width: 20%" class="sorting">
												<button type="button"
													class="btn btn-success btn-xs actualizar-btn"
													onclick="obtenerConfiguracionDireccion(1)"
													data-toggle="modal" data-target="#add">
													<span class="glyphicon glyphicon glyphicon-edit"></span>
													Ingresar datos de inspecci&oacute;n
												</button> <input type="hidden" id="cotizacionDetalleId_1" value="">
											</td>
										</tr>
										<tr>
											<td><label><b>C. Principal:*</b></label></td>
											<td><input type="text" id="ubicacion_calle_principal_1"
												required="required" class="datosGanadero" disabled="disabled"></input></td>
											<td><label><b>N&uacute;mero:*</b></label></td>
											<td><input type="text" id="ubicacion_numero_1"
												required="required" class="datosGanadero" disabled="disabled"></input></td>
											<td><label><b>C. Secundaria:*</b></label></td>
											<td><input type="text" id="ubicacion_calle_secundaria_1"
												required="required" class="datosGanadero" disabled="disabled"></input></td>
										</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</body>
</html>
