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
<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="../static/js/jquery-dynamic-url/jquery.dynamic-url.js"></script>
<script src="../static/js/select2.js"></script>

<!--  	para el tooltipster -->
<script src="../static/js/jquery-ui/jquery-ui.js"></script>
<link href="../static/js/jquery-ui/jquery-ui.theme.css" rel="stylesheet" type="text/css" />
<link href="../static/css/tooltipster.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../static/js/jquery.tooltipster.js"></script>
<script type="text/javascript" src="../static/js/jquery.tooltipster.min.js"></script>

<!-- Para el Datepicker-->
<link href="../static/css/datepicker.css" rel="stylesheet">
<script src="../static/js/bootstrap-datepicker.js"></script>

<!-- KENDO -->
<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
<script src="../static/js/Kendo/kendo.all.min.js"></script>
<script src="../static/js/Kendo/kendo.web.min.js"></script>


<script id="tipoObjetoCargaInicial" src="../static/js/pymes/carga.inicial.cotizador.pymes.js" tipoObjetoValor="PYMES"></script>
<script id="tipoObjetoMetodos" src="../static/js/pymes/metodos.pymes.js" tipoObjetoValor="PYMES"></script>
<script src="../static/js/cotizador/comun.js"></script>

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

.fondo_botones {
	font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
	font-weight: bold;
}

.tab_strip_direccion {
	height: 350px;
}

#wizard>.content .k-datepicker .k-input {
	display: inline-block;
	border: none;
}

#wizard>.content .k-combobox .k-input {
	display: inline-block;
	border: none;
}

#wizard .k-numerictextbox .k-state-focused .k-input {
	display: inline-block !important;
}

#wizard .k-numerictextbox .k-state-default .k-formatted-value.k-input {
	display: none !important;
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

		$('#add').on('show', function() {
			$('.modal-body', this).css({
				width : 'auto',
				height : 'auto',
				'max-height' : '100%'
			});
		});
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


	<!-- ************************************************************
	*      I N I C I O   D E   L A   V E N T A N A    M O D A L   R E S U M E N
	 *****************************************************************-->
	<button hidden="hidden" class="btn btn-primary" data-toggle="modal"
		data-target="#add" id="addButton">
		<span hidden="hidden" class="glyphicon glyphicon-plus"></span> &nbsp;
		Nuevo
	</button>
	<!-- MODAL RESUMEN -->
	<div class="modal fade" id="resumen" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="formCrudResumen">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Cerrar</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Resumen de la
							Cotización</h4>
					</div>
					<div class="form-group">
						<table class="table table-bordered table-hover">
							<tr style="display: none;" id="resumen_inspeccion_filaDescuento">
								<td style="text-align: right">Descuento %</td>
								<td><input id="resumen_inspeccion_porcentaje_descuento"
									disabled="disabled" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">Prima sin Impuestos :</td>
								<td><input id="resumen_inspeccion_prima_sin_impuestos"
									style="text-align: right;" disabled="disabled" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">Superintendencia Bancos 3.50%
									:</td>
								<td><input id="resumen_inspeccion_super_bancos"
									disabled="disabled" style="text-align: right;" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">Seguro Campesino 0.50% :</td>
								<td><input id="resumen_inspeccion_seguro_campesino"
									style="text-align: right;" disabled="disabled" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">Derechos de Emisi&oacute;n :</td>
								<td><input id="resumen_inspeccion_derecho_emision"
									style="text-align: right;" disabled="disabled" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">Subtotal :</td>
								<td><input id="resumen_inspeccion_subtotal"
									disabled="disabled" style="text-align: right;" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">IVA 12.00% :</td>
								<td><input id="resumen_inspeccion_iva" disabled="disabled"
									style="text-align: right;" type="text"></input></td>
							</tr>
							<tr>
								<td style="text-align: right">TOTAL :</td>
								<td><input id="resumen_inspeccion_total"
									style="text-align: right;" disabled="disabled" type="text"></td>
							</tr>

						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- FINAL MODAL RESUMEN -->


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
						<h4 class="modal-title" id="myModalLabel">
							<table>
								<tr>
									<td width="60%" style="font-weight: bold;">Riesgos y
										Coberturas de la Direcci&oacute;n <input type="hidden"
										id="numeroDireccion"> <input type="hidden"
										id="cotizacionDetalleId">
									</td>
									<td width="40%" style="font-size: medium; font-weight: bold;">Prima
										Neta en esta dirección:<span id="prima_neta_por_direccion"></span>
									</td>
								</tr>
							</table>
						</h4>
					</div>
					<div class="modal-body">
						<div class="alert alert-danger" id="msgPopup">La
							configuración se grabo con éxito.</div>
						<div class="form-group">
							<div id="tabstrip">
								<ul>
									<li class="k-state-active">Información del Riesgo</li>
									<li>Valores Asegurados</li>
									<li>Coberturas</li>
								</ul>
								<div class="tab_strip_direccion">
									<br />
									<div class="panel panel-primary">
										<div class="panel-body">
											<table style="width: 100%">
												<tr>
													<td>Actividad Económica:</td>
													<td colspan="5"><select name="actividad_economica"
														id="actividad_economica" required="required"
														class="datosPymes"></select></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td>Sector:</td>
													<td><input type="text" name="ubicacion_sector"
														id="ubicacion_sector" required="required"
														class="datosPymes"></input></td>
													<td>Nombre Edificio:</td>
													<td><input type="text"
														name="ubicacion_nombre_edificio"
														id="ubicacion_nombre_edificio" required="required"
														class="datosPymes"></input></td>
													<td>No. Oficina/Piso:</td>
													<td><input type="text" name="ubicacion_numero_oficina"
														id="ubicacion_numero_oficina" required="required"
														class="datosPymes"></input></td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td>Más de 2 años de constitución:</td>
													<td colspan="5">Si<input type="radio"
														name="tiene_mas_dos_anios" id="tiene_mas_dos_anios_si"
														class="datosPymes"></input> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														No<input type="radio" name="tiene_mas_dos_anios"
														id="tiene_mas_dos_anios_no" class="datosPymes"></input>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td>Contabilidad Formal:</td>
													<td colspan="5">Si<input type="radio"
														id="contabilidad_formal_si" class="datosPymes"
														name="contabilidad_formal"></input>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; No <input type="radio"
														id="contabilidad_formal_no" class="datosPymes"
														name="contabilidad_formal"></input>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td>Requiere Ispección:</td>
													<td colspan="5">Si<input type="radio"
														id="requiere_inspeccion_si" class="datosPymes"
														name="requiere_inspeccion"></input>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; No <input type="radio"
														id="requiere_inspeccion_no" class="datosPymes"
														name="requiere_inspeccion"></input>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
								<div class="tab_strip_direccion" align="center">
									<table style="width: 50%">
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_estructuras" hidden="true">
											<td style="width: 70%">Estructuras:</td>
											<td style="width: 30%"><input type="text"
												name="valor_estructuras" id="valor_estructuras"
												class="datosPymes" onchange="calcularTotalValorAsegurado()"></input></td>

										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_muebles_enseres" hidden="true">
											<td>Muebles, enseres y equipos de oficina:</td>
											<td><input type="text" name="valor_muebles_enseres"
												id="valor_muebles_enseres" class="datosPymes"
												onchange="calcularTotalValorAsegurado()"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_maquinaria" hidden="true">
											<td>Maquinarias:</td>
											<td><input type="text" name="valor_maquinarias"
												id="valor_maquinarias" class="datosPymes"
												onchange="calcularTotalValorAsegurado()"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_mercaderia" hidden="true">
											<td>Mercadería:</td>
											<td><input type="text" name="valor_mercaderia"
												id="valor_mercaderia" class="datosPymes"
												onchange="calcularTotalValorAsegurado()"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_equipos_herramienta" hidden="true">
											<td>Equipos y herramientas:</td>
											<td><input type="text" name="valor_herramientas"
												id="valor_herramientas" class="datosPymes"
												onchange="calcularTotalValorAsegurado()"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr id="fila_insumos_medicos" hidden="true">
											<td>Insumos Médicos y/o de Laboratorio (no
												electrónicos):</td>
											<td><input type="text" name="valor_insumos_medicos"
												id="valor_insumos_medicos" class="datosPymes"
												onchange="calcularTotalValorAsegurado()"></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>Total Valor Asegurado:</td>
											<td><input type="text" name="total_valor_asegurado"
												id="total_valor_asegurado" required="required"
												class="datosPymes" disabled></input></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td colspan="2" align="center"><button
													id="ingresarNuevoValores" class="k-primary"
													style="visibility: hidden;">Ingresar Nuevo Valores</button></td>
										</tr>
									</table>
								</div>
								<div class="tab_strip_direccion">
									<table>
										<tr>
											<td style="width: 40%; vertical-align: top;">
												<table style="width: 100%">
													<tr>
														<td>
															<table id="coberturas_por_direccion" style="width: 100%">
															</table>
														</td>
													</tr>
													<tr>
														<td><br /> <br /> <img alt="Obligatorio"
															src="../static/images/circle_orange.png">Cobertura
															incluida en el producto</td>
													</tr>
													<tr>
														<td><img alt="Obligatorio"
															src="../static/images/Circle_Green.png">Cobertura
															opcional en el producto</td>
													</tr>
												</table>

											</td>
											<td style="width: 60%; vertical-align: top;">
												<div style="height: 350px; overflow-y: scroll;">
													<table id="detalle_cobertura_direccion">
													</table>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" id="cerrar-popup"
							data-dismiss="modal">Cerrar</button>
						<button type="button" class="btn btn-primary" id="guardar"
							onclick="grabarDireccion()" data-dismiss="modal">Guardar</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- *******  F I N ****** -->

	<div class="content">
		<div
			style="position: absolute; margin-top: 15px; margin-left: 10px; font-size: larger; color: #003da5;">
			<b>Cotizaci&oacute;n # <span id="cotizacion_id"></span></b> <input
				type="hidden" value="" id="GrupoProductoID"><br> <input
				type="hidden" value="" id="ProductoID"><br> <input
				type="hidden" value="" id="usuarioRol">
		</div>

		<form id="wizard">
			<h2>
				Cliente <i class="fa fa-child fa-2x"></i>
			</h2>
			<section>
				<!-- **************************************************************************************************************************
		        *                   F O R M U L A R I O   P A R A   E L   I N G R E S O  D A T O S   G E N E R A L E S
		        ****************************************************************************************************************************-->
				<fieldset class="border">
					<legend class="border">Datos sobre Producto, P&oacute;liza
						y Cliente</legend>
					<div class="alert alert-danger alert-error"
						id="mensaje_exito_input_vehiculo" style="display: none;">Por
						favor ingrese los datos faltantes.</div>
					<!-- Inicio datos del producto cerrado -->
					<div class="panel panel-primary">
						<div class="panel-heading">Seleccione el producto</div>
						<div class="panel-body">
							<table>
								<tr>
									<td style="width: 15%"><label><b>Grupo de
												Productos:*</b></label></td>
									<td style="width: 35%"><select name="grupo_productos"
										id="grupo_productos" required="required" class="datosPymes"
										disabled="disabled"></select></td>
									<td style="width: 15%"><label><b>Producto:*</b></label></td>
									<td style="width: 35%"><select name="productos"
										id="productos" required="required" class="datosPymes"
										disabled="disabled"></select></td>
									<td style="width: 35%"><input type="hidden"
										id="codigoProductos" name=""></td>

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
									<td style="width: 15%"><label><b>Punto de
												Venta:*</b></label></td>
									<td style="width: 35%"><select name="punto_venta"
										id="punto_venta" required="required"
										class="datosPymes obligatoriosTarifacion"></select></td>
									<td style="width: 15%"><label><b>Vigencia de
												la p&oacute;liza:*</b></label></td>
									<td style="width: 35%"><select name="vigencia"
										id="vigencia" required="required" class="datosPymes"
										disabled="disabled"></select> <input type="hidden"
										id="puntoVentaSeleccionado"></td>
								</tr>
								<tr>
									<td style="width: 15%" colspan="1"><label><b>Agente
												de Seguros:*</b></label></td>
									<td style="width: 85%" colspan="3"><select name="agentes"
										id="agentes" class="datosPymes" required="required"></select></td>
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
									<td style="width: 15%"><label><b>Tipo
												Identificaci&oacute;n:*</b></label></td>
									<td style="width: 35%"><select
										class="tipo_identificacion datosPymes"
										id="tipo_identificacion_principal" required="required"></select></td>
									<td style="width: 15%"><label><b>Identificaci&oacute;n:*</b></label></td>
									<td style="width: 35%"><input type="text"
										id="identificacion" class="datosPymes" name="identificacion"
										required="required" maxlength="20"
										onchange="cargarPorIdentificacion('datosClienteInicio', this.value)">
										<div id="loading_identificacion"
											class="loading_identificacion" hidden="">
											<span id="loading-msg">Cargando...</span><img
												src="../static/images/ajax-loader.gif" />
										</div></td>
									<td style="width: 35%"><input type="hidden"
										id="codigoClienteEnsurance" name="codigoClienteEnsurance"></td>
									<td style="width: 35%"><input type="hidden"
										id="codigoEntidadEnsurance" name="codigoEntidadEnsurance"></td>
								</tr>
								<tr id="filaNatural">
									<td style="width: 15%"><label><b>Nombres:*</b></label></td>
									<td style="width: 35%"><input type="text" id="nombres"
										class="datosPymes" name="nombres" required="required"></td>
									<td style="width: 15%"><label><b>Apellidos:*</b></label></td>
									<td style="width: 35%"><input type="text" id="apellidos"
										class="datosPymes" name="apellidos" required="required"></td>
								</tr>
								<tr id="filaJuridica" hidden="true">
									<td style="width: 15%" colspan="1"><label><b>Nombres
												Empresa:*</b></label></td>
									<td style="width: 35%" colspan="3"><input type="text"
										id="nombre_completo" class="datosPymes" name="nombre_completo"
										required="required"></td>
								</tr>
								<tr>
									<td style="width: 15%"><label><b>E-mail:*</b></label></td>
									<td style="width: 35%"><input type="email" id="email"
										class="datosPymes" name="email"></td>
									<td style="width: 15%"><label><b>No. Teléfono:</b></label></td>
									<td style="width: 35%"><input type="text" id="telefono"
										class="datosPymes" name="telefono"></td>
								</tr>
								<tr>
									<td style="width: 15%"><label><b>No. Celular:*</b></label></td>
									<td style="width: 35%"><input type="text" id="celular"
										class="datosPymes" name="celular" required="required"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin datos cliente -->
				</fieldset>
			</section>

			<h2>
				Productos <i class="fa fa-home fa-2x"></i>
			</h2>
			<section>

				<!--***********************************************************************************************
                             G E N E R A C I O N   D E   L A  F I C H A   D E  P Y M E S
            	************************************************************************************************-->
				<fieldset class="border">
					<div class="alert alert-danger" id="msgPopupFichaPymeError" hidden>
						<button type="button" class="close"
							onclick="cerrarAlertFichaPymeError()">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<label id="lblContenidoMensaje"></label>
					</div>
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
													class="datosPymes"></select></td>
												<td style="width: 5%"><label><b>Ciudad:*</b></label></td>
												<td style="width: 15%"><select id="ubicacion_canton_1"
													required="required" class="datosPymes"></select></td>
												<td style="width: 25%"></td>
												<td style="width: 20%"></td>
												<!-- <td style="width: 10%"><label><b>Parroquia:*</b></label></td>
												<td style="width: 25%"><select
													id="ubicacion_parroquia1" required="required"
													class="datosPymes"></select></td> -->
												<td style="width: 20%" class="sorting">
													<button type="button"
														class="btn btn-success btn-xs actualizar-btn"
														onclick="obtenerConfiguracionDireccion(1)"
														data-toggle="modal" data-target="#add">
														<span class="glyphicon glyphicon glyphicon-edit"></span>
														Informaci&oacute;n para cotizar
													</button> <input type="hidden" id="cotizacionDetalleId_1" value="">
												</td>
											</tr>
											<tr>
												<td><label><b>C. Principal:*</b></label></td>
												<td><input type="text" id="ubicacion_calle_principal_1"
													required="required" class="datosPymes"></input></td>
												<td><label><b>N&uacute;mero:*</b></label></td>
												<td><input type="text" id="ubicacion_numero_1"
													required="required" class="datosPymes"></input></td>
												<td><label><b>C. Secundaria:*</b></label></td>
												<td><input type="text"
													id="ubicacion_calle_secundaria_1" required="required"
													class="datosPymes"></input></td>
											</tr>
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td align="center" style="width: 80%">
								<button type="button" id="btnAgregarDireccion"
									class="btn btn-primary btn-xs datosPymes">
									<span class="glyphicon glyphicon glyphicon-plus"></span>
									Agregar Nueva Dirección
								</button>
							</td>
							<td align="right" style="width: 20%">
								<button type="button" id="btnMostrarResumen"
									class="btn btn-success btn-xs actualizar-btn"
									onclick="mostrarResumen()" data-toggle="modal"
									data-target="#resumen">
									<span class="glyphicon glyphicon glyphicon-edit"></span>
									Mostrar Resumen de la Cotizazión
								</button>
							</td>
						</tr>
					</table>
				</fieldset>
				<div id="tabstrip_coberturas_generales">
					<ul id="tab_coberturas_generales" class="nav nav-tabs"
						data-tabs="tabs">
						<li class="active"><a href="#tab_asistencias"
							data-toggle="tab">Asistencia PYMES</a></li>
						<li><a href="#tab_coberturasgenerales" data-toggle="tab">Ramos
								Adicionales</a></li>
						<li><a href="#tab_coberturasadicionales" data-toggle="tab">Coberturas
								Adicionales</a></li>
						<li><a href="#tab_deducibles" data-toggle="tab">Deducibles</a></li>
					</ul>
					<div id="my-tab-content" class="tab-content">
						<div id="tab_asistencias" class="tab-pane active"
							style="background-color: white; height: 310px">
							<br />
							<table style="width: 95%" align="center">
								<tr>
									<td width="70%" style="vertical-align: top;">
										<table id="asistencias_generales">
										</table>
									</td>
									<td width="30%" style="vertical-align: top;">
										<table>
											<tr>
												<td colspan="2"><img alt="Obligatorio"
													src="../static/images/circle_orange.png"> Beneficios
													incluidos en el producto</td>
											</tr>
											<tr>
												<td><img alt="Obligatorio"
													src="../static/images/Circle_Green.png"> Beneficios
													opcional para el producto</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<div id="tab_coberturasgenerales" class="tab-pane"
							style="background-color: white; height: 310px">
							<br />
							<table style="width: 95%" align="center">
								<tr>
									<td style="width: 40%; vertical-align: top;">
										<table id="coberturas_generales" style="width: 100%">
										</table>
									</td>
									<td style="width: 60%; vertical-align: top;">
										<table id="detalle_cobertura_general">
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="2"><br /> <br /> <img alt="Obligatorio"
										src="../static/images/circle_orange.png">Cobertura
										incluida en el producto &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img
										alt="Obligatorio" src="../static/images/Circle_Green.png">Cobertura
										opcional en el producto</td>
								</tr>
							</table>
						</div>
						<div id="tab_coberturasadicionales" class="tab-pane"
							style="background-color: white; height: 310px;">
							<br />
							<table style="width: 95%" align="center">
								<tr>
									<td style="width: 40%; vertical-align: top;">
										<table style="width: 100%">
											<tr>
												<td>
													<table id="coberturas_adicionales" style="width: 100%">
													</table>
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td><img alt="Obligatorio"
													src="../static/images/circle_blue.png">Clausula sin
													costo</td>
											</tr>
											<tr>
												<td><img alt="Obligatorio"
													src="../static/images/Circle_Green.png"> Clausula
													opcional para el producto</td>
											</tr>
										</table>
									</td>
									<td style="width: 60%; vertical-align: top;">
										<div style="height: 290px; overflow-y: scroll;">
											<table id="detalle_cobertura_adicionales">
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<div id="tab_deducibles" class="tab-pane"
							style="background-color: white; height: 310px">
							<br />
							<div style="height: 290px; overflow-y: scroll;">
								<table id="deducibles_general" style="width: 95%" align="center">

								</table>
							</div>
						</div>
					</div>
				</div>
			</section>

			<h2>
				Inspecci&oacute;n <i class="fa fa-user-secret fa-2x"></i>
			</h2>
			<section>
				<div class="alert alert-success" id="msgInspeccionAprobada"
					style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					La cotización ha sido enviada para inspección.
				</div>

				<div class="alert alert-danger" id="msgPopupFichaInspeccionError" hidden>
					<button type="button" class="close"
						onclick="cerrarAlertFichaInspeccionError()">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<label id="lblContenidoMensajeInspeccion"></label>
				</div>

				<div>
					<div style="width: 100%">
						<div class="thumbnail" style="padding: 20px;">
							<table id="inspeccion_tabla_detalle_pagos"
								class="table table-bordered table-hover">
								<tr>
									<td colspan="2" style="text-align: center;">Detalle Valor
										a Pagar</td>
								</tr>
								<tr style="display: none;" id="inspeccion_filaDescuento">
									<td style="text-align: right">Descuento %</td>
									<td><input id="inspeccion_porcentaje_descuento"
										disabled="disabled" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">Prima sin Impuestos :</td>
									<td><input id="inspeccion_prima_sin_impuestos"
										style="text-align: right;" disabled="disabled" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">Superintendencia Bancos
										3.50% :</td>
									<td><input id="inspeccion_super_bancos"
										disabled="disabled" style="text-align: right;" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">Seguro Campesino 0.50% :</td>
									<td><input id="inspeccion_seguro_campesino"
										style="text-align: right;" disabled="disabled" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">Derechos de Emisi&oacute;n :</td>
									<td><input id="inspeccion_derecho_emision"
										style="text-align: right;" disabled="disabled" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">Subtotal :</td>
									<td><input id="inspeccion_subtotal" disabled="disabled"
										style="text-align: right;" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">IVA 12.00% :</td>
									<td><input id="inspeccion_iva" disabled="disabled"
										style="text-align: right;" type="text"></input></td>
								</tr>
								<tr>
									<td style="text-align: right">TOTAL :</td>
									<td><input id="inspeccion_total" class="total_vh"
										style="text-align: right;" disabled="disabled" type="text"><input
										id="inspeccion_total" disabled="disabled" type="hidden"></td>
								</tr>

							</table>
						</div>
					</div>
				</div>
				<div>
					<table>
						<tr>
							<td style="text-align: center">
								<button type="button" id="registrarParaInspeccion"
									class='btn btn-success btn-xs'>
									<span class='glyphicon glyphicon glyphicon-check'></span>Aceptar
									Cotización
								</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" id="procesarReporte"
									class='btn btn-success btn-xs'>
									<span class='glyphicon glyphicon glyphicon-check'></span>Reporte
									de la Cotización
								</button>
							</td>
						</tr>
					</table>
				</div>
			</section>


			<h2>
				Pago <i class="fa fa-credit-card fa-2x"></i>
			</h2>
			<section>

				<div class="panel panel-primary">
					<div class="panel-heading">
						Informaci&oacute;n de la Prima <span id="resumenTotalPago">Total
							a Pagar: <b></b>
						</span>
					</div>
					<div class="panel-body">
						<input type="hidden" id="fechaInicialPagos" readonly="readonly" />
						<input type="hidden" id="codigoPagoRegistrado" value="-1"
							readonly="readonly" /> <input type="hidden"
							id="estadoCotizacion" value="" readonly="readonly" />
						<div id="tabbable" class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#primero" data-toggle="tab">Valores
										a Pagar</a></li>
								<li><a href="#segundo" data-toggle="tab" id="tabFormasPago">Formas
										de Pago</a></li>
								<li><a href="#tercero" data-toggle="tab">Asegurado y
										Beneficiario </a></li>
							</ul>
							<div class="tab-content">
								<!-- Inicio tab valor a pagar -->
								<div class="tab-pane active" id="primero">
									<br>
									<div>
										<div style="width: 80%">
											<div class="thumbnail" style="padding: 20px;">
												<table id="tabla_detalle_pagos" style="width: 100%"
													class="table table-bordered table-hover">
													<tr>
														<td colspan="2" style="text-align: center;">Detalle
															Valor a Pagar</td>
													</tr>
													<!-- <tr>
														<td style="text-align: right">Total Suma Asegurada :</td>
														<td><input id="total_suma_asegurada"
															style="text-align: right; width: 200px"
															disabled="disabled" type="text"></input></td>
													</tr> -->
													<tr style="display: none;" id="filaDescuento">
														<td style="text-align: right">Descuento % :</td>
														<td><input id="porcentaje_descuento"
															style="text-align: right;" disabled="disabled"
															type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Prima sin Impuestos :</td>
														<td><input id="prima_sin_impuestos"
															style="text-align: right;" disabled="disabled"
															type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Superintendencia Bancos
															3.50% :</td>
														<td><input id="super_bancos" disabled="disabled"
															style="text-align: right;" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Seguro Campesino 0.50%
															:</td>
														<td><input id="seguro_campesino" disabled="disabled"
															style="text-align: right;" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Derechos de
															Emisi&oacute;n :</td>
														<td><input id="derecho_emision" disabled="disabled"
															style="text-align: right;" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">Subtotal :</td>
														<td><input id="subtotal" disabled="disabled"
															style="text-align: right;" type="text"></input></td>
													</tr>
													<tr>
														<td style="text-align: right">IVA 12.00% :</td>
														<td><input id="iva" disabled="disabled" type="text"
															style="text-align: right;"> </input></td>
													</tr>
													<tr>
														<td style="text-align: right">TOTAL :</td>
														<td><input id="total" class="total_vh"
															style="text-align: right;" disabled="disabled"
															type="text"><input id="total_vh_origin"
															disabled="disabled" type="hidden"></td>
													</tr>

												</table>
											</div>
										</div>
									</div>


								</div>
								<!--  Fin tab valor a pagar -->


								<!-- inicio tab forma de pago-->
								<div class="tab-pane" id="segundo">
									<br>
									<!--  F O R M A S   D E   P A G O   -->
									<table style="width: 300px;" align="center">
										<tr>
											<td><label><b>Forma de Pago:*</b></label></td>
											<td><select id="cboFpFormaPago" name="cboFpFormaPago" class="datosVehiculo"
												onchange="cambioFormaPago(this.value);" style="width: 150px">
											</select></td>
										</tr>
										<tr>
										<div class="alert" id="msgPopupPago" hidden="hidden">
											<div id='mensajeErrorEndosoBeneficiario'></div>
										</div>
										</tr>
										<tr style="height: 20px;"></tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago bloquearEmitido" id="save-pagoContado"
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
											<td>Tipo de identificaci&oacute;n del titular Cuenta<b>*</b></td>
											<td><select id="tipo_identificacion_banco"
												class="tipo_identificacion datosVehiculo"
												name="cboFpTipoIdentificacionBanco" required="required"></select></td>
										</tr>
										
										<tr>
											<td>Cédula/Ruc del titular Cuenta<b>*</b></td>
											<td><input id="bancoIdentificacion" type="text" class="datosVehiculo"
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
											<td>Titular Cuenta<b>*</b></td>
											<td><input id="bancoTitular" type="text" placeholder="" class=""
												required="required"></td>
										</tr>
										<tr>
											<td style="text-align: left;">Banco<b>*</b></td>
											<td><select id="bancoId" name="cboFpBanco" class="datosVehiculo"></select></td>
										</tr>
										<tr>
											<td>Tipo Cuenta<b>*</b></td>
											<td><select id="bancoTipoCuenta" name="cboFpTipo"
												required="required" class="datosVehiculo">
													<option value="A">Ahorro</option>
													<option value="C">Corriente</option>
											</select></td>
										</tr>
										<tr>
											<td>Número Cuenta<b>*</b></td>
											<td><input id="bancoNumeroCuenta" type="text"
												class="camporequerido validate-not-first datosVehiculo"
												onkeypress="return justNumbers(event);"
												onchange="validacionBanco(); validaCuenta();"
												required="required" name="txtFpNumero"></td>
										</tr>
										<tr>
											<td>Fecha inicio de pagos<b>*</b></td>
											<td><input id="txtfechaInicialpago"
														name="txtfechaInicialpago" type="date"></td>
										</tr>
										<tr>
											<td>Cuota Inicial<b>*</b></td>
											<td><input id="txtcuotaInicialbancoPlazo" class="datosVehiculo" type="number" value="0" required="required" onkeypress="return justNumbers(event);" name="txtcuotaInicial" onchange="validarValoresPagos()" aria-required="true"></td>
										</tr>
										<tr>
											<td>Plazo<b>*</b></td>
											<td><select id="bancoPlazo" name="cboFpPlazo" onchange="validarValoresPagos()" class="datosVehiculo"
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
													class="btn btn-primary save-pago bloquearEmitido" id="save-pagoDebitoBanco"
													onclick='guardarPago("banco")'>Guardar</button></td>
										</tr>
									</table>


									<!-- *********************************************************************************************************************
													     F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   D E B I T O S   C O N   T A R J E T A S
											     ******************************************************************************************************************-->
									<table class='table table-bordered table-hover'
										style="display: none;" id="forma_tarjeta">
										<tr>
											<td>Tipo de identificaci&oacute;n del titular Tarjeta<b>*</b></td>
											<td><select id="tipo_identificacion_tarjeta"
												class="tipo_identificacion datosVehiculo"
												name="cboFpTipoIdentificacionTarjeta" required="required"></select>
											</td>
										</tr>
										<tr>
											<td>Cédula/Ruc del titular Tarjeta<b>*</b></td>
											<td><input id="tarjetaIdentificacion" type="text" class="datosVehiculo"
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
											<td>Titular Tarjeta<b>*</b></td>
											<td><input id="tarjetaTitular" type="text"
												required="required"
												class="camporequerido validate-not-first datosVehiculo"
												title="Ingrese el titular de la Tarjeta" value=""
												name="txtTitularTarjeta"></td>
										</tr>
										<tr>
											<td>Número Tarjeta<b>*</b></td>
											<td><input id="tarjetaNumero" type="text" class="datosVehiculo"
												required="required" onpaste="return false"
												placeholder="Ingrese el número de tarjeta"
												onkeypress="return validarEmisorTarjeta(event, this.value);"
												onchange="comprobarTarjetaValida(this.value)"
												name="txtFpNumero" maxlength="16"> <br> <span></span>
											</td>
										</tr>										
										<tr>
											<td>Tarjeta<b>*</b></td>
											<td><select id="tarjetaId" name="cboFpBanco" class="datosVehiculo"
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
											<td>Mes Expiraci&oacute;n<b>*</b></td>
											<td><select id="tarjetaMesExp" name="cboFpMes" class="datosVehiculo"
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
											<td>A&ntilde;o Expiraci&oacute;n<b>*</b></td>
											<td><select id="tarjetaAnioExp" name="cboFpAnio" class="datosVehiculo"
												required="required"></select></td>
										</tr>
										<tr>
											<td>Fecha inicio de pagos<b>*</b></td>
											<td><input id="txtfechaInicialpagoTarjeta"
												name="txtfechaInicialpagoTarjeta" type="date"></td>
										</tr>
										<tr>
										<td>Cuota Inicial<b>*</b></td>
											<td><input id="txtcuotaInicialtarjetaPlazo" class="datosVehiculo" type="number" min="0" value="0" required="required" onkeypress="return justNumbers(event);" name="txtcuotaInicial" onchange="validarValoresPagos() " aria-required="true"></td>
										</tr>
										<tr>
											<td>Plazo<b>*</b></td>
											<td><select id="tarjetaPlazo" name="cboFpPlazo" class="datosVehiculo"
												required="required" onchange=" validarValoresPagos()">
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
													<!-- <optgroup label="Credito con Interes">
														<option value="12" class="plazoCreditoTarjeta">12
															meses</option>
													</optgroup> -->

											</select></td>
										</tr>
										<tr>
											<td colspan="2" align="center"><button type="button"
													class="btn btn-primary save-pago bloquearEmitido"
													id="save-pagoDebitoTarjeta"
													onclick='guardarPago("tarjeta")'>Guardar</button></td>
										</tr>
									</table>

									<!-- *********************************************************************************************************************
													     		F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   C U O T A S
											     ******************************************************************************************************************-->
									<table id='forma_cuotas' hidden=''
										class='table table-bordered table-hover'>
										<tr></tr>
										<tr>
											<td>Cuota Inicial<b>*</b></td>
											<td><input id="txtcuotaInicial" type="number" class="datosVehiculo"
												required="required" onkeypress="return justNumbers(event);"
												name="txtcuotaInicial" min="0" value="0"
												onchange="validarValoresPagos()"></td>
										</tr>
										<tr>
											<td>Plazo<b>*</b></td>
											<td><select id="cboFpPlazo" name="cboFpPlazo" class="datosVehiculo"
												onchange="validarValoresPagos()"
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
													class="btn btn-primary save-pago bloquearEmitido" id="save-pagoCuotas"
													onclick='guardarPago("cuota")'>Guardar</button></td>
										</tr>
									</table>
									<!-- F O R M A S   D E   P A G O  -->
								</div>
								<!-- Fin tab forma de pago  -->

								<!-- Inicio Beneficiario y Asegurado  -->
								<div class="tab-pane" id="tercero">
									<div class="alert alert-danger"
										id="msgPopupEndosoBeneficiarioError" hidden="hidden">
										<!--  <button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button> -->
										<div id='mensajeErrorEndosoBeneficiario'></div>
									</div>

									<div class="alert alert-success"
										id="msgPopupEndosoBeneficiarioGrabo" hidden="hidden">
										<!-- <button type="button" class="close" data-dismiss="alert">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button> -->
										<div id='mensajeGraboEndosoBeneficiario'></div>
									</div>
									<div class="panel panel-primary">
										<div class="panel-heading">Datos del Asegurado</div>
										<div class="panel-body">
											<table class="table table-bordered table-hover"
												id="formaAsegurado">
												<input id="asegurado_id" type="hidden" />
												<tr>
													<td>Tipo de identificaci&oacute;n del Asegurado</td>
													<td><select id="tipo_identificacion_asegurado"
														class="tipo_identificacion datosPymes datosPymesPosterior"
														name="tipo_identificacion_asegurado" required="required"></select>
													</td>

													<td>Identificaci&oacute;n del Asegurado</td>
													<td><input id="identificacion_asegurado"
														class="identificacion datosPymes datosPymesPosterior"
														onChange="cargarPorIdentificacion('datosAsegurado',this.value)"
														name="identificacion_asegurado" required="required">
													</td>
												</tr>
												<tr id='filaNaturalAsegurado'>
													<td>Nombres:</td>
													<td><input id="nombres_asegurado" class="datosPymes datosPymesPosterior"
														name="nombres_asegurado" required="required"></td>

													<td>Apellidos:</td>
													<td><input id="apellidos_asegurado"
														class=" datosPymes datosPymesPosterior" name="apellidos_asegurado"
														required="required"></td>
												</tr>
												<tr id='filaJuridicaAsegurado' hidden='hidden'>
													<td>Nombre Completo:</td>
													<td><input id="nombre_completo_asegurado"
														class="datosPymes datosPymesPosterior" name="nombre_completo_asegurado"
														required="required"></td>
												</tr>
												<tr>
													<td colspan='4'>
														<div align="center">
															<button type="button" class="btn btn-primary"
																id="guardar_asegurado"
																onClick="guardarAsegurado('guardar');">Guardar</button>
														</div>
													</td>

												</tr>
											</table>

										</div>
									</div>
									<div class="panel panel-primary">
										<div class="panel-heading">Datos del Beneficiario</div>
										<div class="panel-body">
											<table class='table table-bordered table-hover'
												id="formaBeneficiario">
												<input id="endoso_beneficiario_id" type="hidden">
												<tr>
													<td>Beneficiario</td>
													<td><input style="width: 90%" type="select"
														id="beneficiario" name="beneficiario" class=""></td>
												</tr>
												<tr>
													<td>Monto</td>
													<td><input type="number" value='0' min='0'
														id="valor_endoso_beneficiario" class="datosPymes datosPymesPosterior"
														name="valor_endoso_beneficiario"></td>
												</tr>
												<tr>
													<td>
														<div align="center">
															<button type="button" class="btn btn-primary"
																id="guardar_beneficiario"
																onClick="guardarBeneficiario('guardar');">Guardar</button>
														</div>
													</td>
													<td>
														<div align="center">
															<button type="button" class="btn btn-danger"
																id="eliminar_beneficiario"
																onClick="guardarBeneficiario('eliminar');">Eliminar</button>
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

			<h2>
				Datos Factura<i class="fa fa-edit fa-2x"></i>
			</h2>
			<section>
				<!-- *********************************************************************************************************************
									F O R M U L A R I O   P A R A   E L   I N G R E S O   D E   D A T O S   D E   U P L A
				******************************************************************************************************************-->

				<fieldset class="border">
					<legend class="border">Datos del Asegurado y de
						Facturación</legend>
					<!-- Inicio datos poliza - 4 columnas -->

					<!-- Inicio Ubicación Persona Natural -->
					<div class="panel panel-primary" id="ubicacionPersonaNatural"
						hidden="true">
						<div class="panel-heading">Ubicación Persona Natural</div>
						<div class="panel-body">
							<table>
								<tr>
									<td colspan="1"><label><b>Zona:*</b></label></td>
									<td colspan="1"><select id="zona_direccion_natural"
										class="datosPymes datosPymesPosterior" onChange="cambiaZonaDireccion(event,'N');">
											<option value="">Seleccione</option>
											<option value="U">Urbano</option>
											<option value="R">Rural</option>
									</select></td>
									<td colspan="1"><label><b>Provincia:*</b></label></td>
									<td colspan="1"><select
										id="provincia_direccion_cliente_natural"
										class="provincia datosPymes datosPymesPosterior" required="required"></select></td>
								</tr>
								<tr>
									<td id="canton_cliente_label" colspan="1" hidden=""><label><b>Canton:*</b></label></td>
									<td id="canton_cliente_select" colspan="1" hidden=""><select
										id="canton_direccion_cliente_natural"
										name="canton_direccion_cliente_natural"
										class="canton datosPymes datosPymesPosterior"></select></td>
									<td id="parroquia_cliente_label" colspan="1" hidden=""><label><b>Parroquia:*</b></label></td>
									<td id="parroquia_cliente_select" colspan="1" hidden=""><select
										id="parroquia_direccion_cliente_natural"
										name="parroquia_direccion_cliente_natural"
										class="parroquia datosPymes datosPymesPosterior"></select></td>
									<td id="ciudad_cliente_label" colspan="1" hidden=""><label><b>Ciudad:*</b></label></td>
									<td id="ciudad_cliente_select" colspan="1" hidden=""><select
										id="ciudad_direccion_cliente_natural"
										name="ciudad_direccion_cliente_natural"
										class="ciudad datosPymes datosPymesPosterior"></select></td>

								</tr>
								<tr>
									<td colspan="1"><label><b>Calle Principal:*</b></label></td>
									<td colspan="1"><input type="text"
										name="calle_principal_direccion_natural"
										id="calle_principal_direccion_natural" class="datosPymes datosPymesPosterior"
										required="required"></td>
									<td colspan="1"><label><b>Número:*</b></label></td>
									<td colspan="1"><input type="text"
										id="numero_direccion_natural" class="datosPymes datosPymesPosterior"
										name="numero_direccion_natural" required="required"></td>
								</tr>
								<tr>
									<td colspan="1"><label><b>Calle Secundaria:*</b></label></td>
									<td colspan="1"><input type="text"
										id="calle_secundaria_direccion_natural" class="datosPymes datosPymesPosterior"
										name="calle_secundaria_direccion_natural" required="required"></td>
									<td colspan="1"><label><b>Datos Referencia:*</b></label></td>
									<td colspan="3"><input type="text"
										id="referencia_direccion_natural" class="datosPymes datosPymesPosterior"
										name="referencia_direccion_natural"></td>
								</tr>
								<tr>
									<td><label><b>Lugar Nacimiento:</b></label></td>
									<td><input type="text" id="lugar_nacimiento_natural"
										class="datosPymes datosPymesPosterior" name="lugar_nacimiento_natural"></td>
									<td><label><b>Fecha de Nacimiento:*</b></label></td>
									<td><input type="date" id="fecha_nacimiento_natural"
										class="datosPymes datosPymesPosterior" name="fecha_nacimiento_natural"
										required="required"></td>
								</tr>
								<tr>
									<td colspan="1"><label><b>Telefono:*</b></label></td>
									<td colspan="1"><input type="text"
										id="telefono_cliente_natural"
										name="telefono_direccion_solicitante" maxlength="10"
										class="telefono datosPymes datosPymesPosterior"
										onkeypress="validarKeyPress(event,/[0-9]/)"
										required="required"></td>
									<td colspan="1"><label><b>Celular:*</b></label></td>
									<td colspan="1"><input type="text"
										id="celular_cliente_natural" class="datosPymes datosPymesPosterior" maxlength="10"
										name="celular_direccion_solicitante"
										onkeypress="validarKeyPress(event,/[0-9]/)"
										required="required"></td>

								</tr>
								<tr>
									<td><label><b>Email:</b></label></td>
									<td><input type="text" id="mail_cliente_natural"
										name="mail_cliente_natural" class="mail datosPymes datosPymesPosterior"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin Ubicación Persona Natural -->

					<!-- Fin Ubicación Persona Juridica -->
					<div class="panel panel-primary" id="ubicacionPersonaJuridica"
						hidden="true">
						<div class="panel-heading">Ubicacion Persona Juridica</div>
						<div class="panel-body">
							<table>
								<tr>
									<td colspan="5"><label><b>Dirección de la
												Matriz</b></label></td>
								</tr>
								<tr>
									<td colspan="1"><label><b>Zona:*</b></label></td>
									<td colspan="1"><select
										id="zona_direccion_matriz_juridica"
										name="zona_direccion_matriz_juridica"
										onChange="cambiaZonaDireccion(event,'J');" required="required"
										class="datosPymes datosPymesPosterior">
											<option value="">Seleccione</option>
											<option value="U">Urbano</option>
											<option value="R">Rural</option>
									</select></td>
									<td colspan="1"><label><b>Provincia:*</b></label></td>
									<td colspan="1"><select
										id="provincia_direccion_matriz_juridica"
										name="provincia_direccion_matriz_juridica"
										class="provincia datosPymes datosPymesPosterior" required="required"></select></td>
								</tr>
								<tr>
									<td id="canton_matriz_label" colspan="1" hidden=""><label><b>Canton:*</b></label></td>
									<td id="canton_matriz_select" colspan="1" hidden=""><select
										id="canton_direccion_matriz_juridica"
										name="canton_direccion_matriz_juridica"
										class="canton datosPymes datosPymesPosterior"></select></td>
									<td id="parroquia_matriz_label" colspan="1" hidden=""><label><b>Parroquia:*</b></label></td>
									<td id="parroquia_matriz_select" colspan="1" hidden=""><select
										id="parroquia_direccion_matriz_juridica"
										name="parroquia_direccion_matriz_juridica"
										class="parroquia datosPymes datosPymesPosterior"></select></td>
									<td id="ciudad_matriz_label" colspan="1" hidden=""><label><b>Ciudad:*</b></label></td>
									<td id="ciudad_matriz_select" colspan="1" hidden=""><select
										id="ciudad_direccion_matriz_juridica"
										name="ciudad_direccion_matriz_juridica"
										class="ciudad datosPymes datosPymesPosterior"></select></td>

								</tr>
								<tr>
									<td><label><b>Calle Principal:*</b></label></td>
									<td colspan='3'><input type="text"
										id="calle_principal_direccion_juridica"
										name="calle_principal_direccion_juridica" required="required"
										class="datosPymes datosPymesPosterior"></td>
								</tr>
								<tr>
									<td><label><b>Número:*</b></label></td>
									<td><input type="text" id="numero_direccion_juridica"
										name="numero_direccion_juridica" required="required"
										class="datosPymes datosPymesPosterior"></td>
									<td><label><b>Calle Secundaria:*</b></label></td>
									<td><input type="text"
										id="calle_secundaria_direccion_juridica"
										name="calle_secundaria_direccion_juridica" required="required"
										class="datosPymes datosPymesPosterior"></td>
								</tr>
								<tr>
									<td><label><b>Datos Referencia:*</b></label></td>
									<td colspan="3"><input type="text"
										id="referencia_direccion_juridica"
										name="referencia_direccion_juridica" class="datosPymes datosPymesPosterior"></td>
								</tr>
								<tr>
									<td colspan="1"><label><b>Telefono:*</b></label></td>
									<td colspan="1"><input type="text" id="telefono_juridica"
										name="telefono_direccion_solicitante" maxlength="10"
										class="telefono datosPymes datosPymesPosterior"
										onkeypress="validarKeyPress(event,/[0-9]/)"
										required="required"></td>
									<td colspan="1"><label><b>Celular:*</b></label></td>
									<td colspan="1"><input type="text"
										id="celular_representante_legal" class="datosPymes datosPymesPosterior"
										maxlength="10" name="celular_direccion_solicitante"
										onkeypress="validarKeyPress(event,/[0-9]/)"
										required="required"></td>

								</tr>
								<tr>
									<td><label><b>Email:</b></label></td>
									<td><input type="text" id="mail_representante_juridico"
										name="mail_direccion_cliente" class="mail datosPymes datosPymesPosterior"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin Ubicación Persona Juridica -->

					<!-- Inicio datos politica Natural -->
					<div class="panel panel-primary" id="datosPoliticaNatural"
						hidden="true">
						<div class="panel-heading">Actividad Politica</div>
						<div class="panel-body">
							<table>
								<tr>
									<td colspan='2'><label><b>Usted es una Persona
												Expuesta Politicamente :</b></label></td>
								</tr>
								<tr>
									<td colspan='2'><select id="expuesto_cliente_natural"
										class="datosPymes datosPymesPosterior">
											<option value="">Seleccione</option>
											<option value="1">SI</option>
											<option value="0">NO</option>
									</select></td>
								</tr>
								<tr>
									<td colspan><label><b>Cargo que desempeña:</b></label></td>
									<td><input type="text" id="cargo_expuesto_cliente_natural"
										class="datosPymes datosPymesPosterior" name="cargoDesempena">
								</tr>
								<div class="well">
									<b>Persona Expuesta Politicamente:</b> Es la persona que
									desempe&ntilde;a o ha desempe&ntilde;ado funciones publicas
									destacadas en el pais o en el exterior, que por su perfil pueda
									exponer en mayor grado a la entidad al riesgo de lavado de
									activos y financiamiento de delitos, por ejemplo, Jefe de
									Estado o de un gobierno, politico de alta jerarquia,
									funcionario importante de partidos politicos. Las relaciones
									comerciales con los parientes dentro del segundo grado de
									consaguinidad o primero de afinidad y los colaboradores
									cercanos de una persona politicamente expuesta, implica que las
									instituciones del sistema financiero apliquen procedimientos de
									debida diligencia ampliados.
								</div>
							</table>
						</div>
					</div>
					<!-- Fin datos politica Natural -->

					<!-- Inicio datos politica Juridica -->
					<div class="panel panel-primary" id="datosPoliticaJuridica"
						hidden="true">
						<div class="panel-heading">Actividad Politica</div>
						<div class="panel-body">
							<table>
								<tr>
									<td colspan='4'><label><b>Usted es una Persona
												Expuesta Politicamente :</b></label></td>
								</tr>
								<tr>
									<td colspan='4'><select
										id="expuesto_representante_juridica"
										placeholder="Escoja una opción" class="datosPymes datosPymesPosterior">
											<option value="">Seleccione</option>
											<option value="1">SI</option>
											<option value="0">NO</option>
									</select></td>
								</tr>
								<tr>
									<td><label><b>Cargo que desempe&ntilde;a:</b></label></td>
									<td><input type="text"
										id="cargo_expuesta_representante_juridica"
										name="cargoDesempena" class="datosPymes datosPymesPosterior">
								</tr>
								<div class="well">
									<b>Persona Expuesta Politicamente:</b> Es la persona que
									desempe&ntilde;a o ha desempe&ntilde;ado funciones publicas
									destacadas en el pais o en el exterior, que por su perfil pueda
									exponer en mayor grado a la entidad al riesgo de lavado de
									activos y financiamiento de delitos, por ejemplo, Jefe de
									Estado o de un gobierno, politico de alta jerarquia,
									funcionario importante de partidos politicos. Las relaciones
									comerciales con los parientes dentro del segundo grado de
									consaguinidad o primero de afinidad y los colaboradores
									cercanos de una persona politicamente expuesta, implica que las
									instituciones del sistema financiero apliquen procedimientos de
									debida diligencia ampliados.
								</div>
							</table>
						</div>
					</div>
					<!-- Fin datos politica Juridica -->

					<!-- Inicio datos Situacion Financiera Natural-->
					<div class="panel panel-primary" id="situacionFinancieraNatural"
						hidden="true">
						<div class="panel-heading">Situacion Financiera: Total de
							activos y pasivos</div>
						<div class="panel-body">
							<table>
								<tr>
									<td><label><b>Salario Mensual:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="salario_mensual_natural" name="" class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosNatural()"></td>
									<td><label><b>Activos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="activos_natural" name="" class="datosPymes datosPymesPosterior"
										onchange="calcularPatrimonioNatural()">
								</tr>
								<tr>
									<td><label><b>Otros Ingresos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="otros_ingresos_natural" name="" class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosNatural()"></td>
									<td><label><b>Pasivos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="pasivos_natural" name="" class="datosPymes datosPymesPosterior"
										onchange="calcularPatrimonioNatural()"></td>
								</tr>
								<tr>
									<td><label><b>Egresos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="egresos_natural" name="egresos" class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosNatural()"></td>
									<td><label><b>A-P= Patrimonio:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="patrimonio_natural" disabled="disabled" name="patrimonio"
										class="datosPymes"></td>
								</tr>
								<tr>
									<td><label><b>V/Neto Ingreso - Egreso:</b></label></td>
									<td><input type="number" id="ingresos_egresos_natural"
										disabled="disabled" name="" class="datosPymes datosPymesPosterior"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin datos Situacion Financiera Natural-->

					<!-- Inicio datos Situacion Financiera Juridica-->
					<div class="panel panel-primary" id="situacionFinancieraJuridica"
						hidden="true">
						<div class="panel-heading">Situacion Financiera: Total de
							activos y pasivos</div>
						<div class="panel-body">
							<table>
								<tr>
									<td><label><b>Ingresos Mensual:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="salario_mensual_juridica" name="salarioMensual"
										class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosJuridica()"></td>
									<td><label><b>Activos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="activos_juridica" name="activosFinanciero"
										class="datosPymes datosPymesPosterior" onchange="calcularPatrimonioJuridica()">
								</tr>
								<tr>
									<td><label><b>Otros Ingresos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="otros_ingresos_juridica" name="otrosIngresos"
										class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosJuridica()"></td>
									<td><label><b>Pasivos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="pasivos_juridica" name="pasivosFinanciero"
										class="datosPymes datosPymesPosterior" onchange="calcularPatrimonioJuridica()"></td>
								</tr>
								<tr>
									<td><label><b>Egresos:</b></label></td>
									<td><input type="number" min="0" value="0"
										id="egresos_juridica" name="egresos" class="datosPymes datosPymesPosterior"
										onchange="calcularIngresosEgresosJuridica()"></td>
									<td><label><b>A-P = Patrimonio:</b></label></td>
									<td><input type="number" min="0" value="0"
										disabled="disabled" id="patrimonio_juridica" name="patrimonio"
										class="datosPymes datosPymesPosterior"></td>
								</tr>
								<tr>
									<td><label><b>V/Neto Ingreso - Egreso:</b></label></td>
									<td><input type="number" min="0" value="0"
										disabled="disabled" id="ingresos_egresos_juridica"
										name="vNeto" class="datosPymes datosPymesPosterior"></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- Fin datos Situacion Financiera Juridica-->

				</fieldset>
			</section>
			<h2>
				Emision <i class="fa fa-check-circle-o fa-2x"></i>
			</h2>
			<section>
				<div class="alert alert-success"
					id="msgPopupValidacionFecha" style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					Debe ingresar la fecha de inicio de vigencia de la poliza.
				</div>

				<div class="alert alert-success" id="msgPopupEmitidoCorrectamente"
					style="display: none;">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					La poliza se ha emitido correctamento.
				</div>

				<div class="panel panel-primary" id="datosFinales">
					<div class="panel-heading">Datos Finales</div>
					<div class="panel-body">
						<table id="tablaFinalVehiculos">
							<tr>
								<td colspan="4"><label>Seleccione la fecha con la
										que desea que se emita la poliza de esta cotizaci&oacute;n: </label></td>
								<td colspan="3"><input type="text"
									id="fecha_inicio_vigencia" name="fecha_inicio_vigencia"
									required="required"></td>
							</tr>
						</table>
						<div id="loading_tablaFinal" class="loading_identificacion"
							hidden="">
							<span id="loading-msgTablaFinal"></span><img
								src="../static/images/ajax-loader.gif" />
						</div>
					</div>
				</div>
			</section>
		</form>
	</div>
</body>
</html>
