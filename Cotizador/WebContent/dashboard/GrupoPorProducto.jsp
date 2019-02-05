<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Grupo Por Producto - CotizadorQBE</title>

<!-- Core CSS - Include with every page -->
<!-- Core CSS - Include with every page -->
<link
	href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css"
	rel="stylesheet">
<link href="../static/css/loading.css" rel="stylesheet">

<script
	src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
<script
	src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
<script src="../static/js/util.js"></script>
<script src="../static/js/jquery.nicescroll.min.js"></script>
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	var codigo = "";
	var nombre = "";
	var matriz = 0;
	var activo = 0;
	var tipoConsulta = "";
	var arrProducto = new Array();
	var arrCodigoProducto = new Object();
	var arrGrupoProducto = new Array();
	var arrCodigoGrupoProducto = new Object();
	var arrTipoProducto = new Array();
	var arrCodigoTipoProducto = new Object();
	var arrTipoUso = new Array();
	var arrCodigoTipoUso= new Object();
	var arrTipoGrupo = new Array();
	var arrCodigoTipoGrupo= new Object();

	$(document)
			.ready(
					function() {
						var nicesx = $("#tabla").niceScroll({
							touchbehavior : false,
							cursorcolor : "#0000FF",
							cursoropacitymax : 0.6,
							cursorwidth : 8
						});
						activarMenu("GrupoPorProducto");
						$('.well').hide();
						$
								.ajax({
									url : '../GrupoPorProductoController',
									data : {
										"tipoConsulta" : "encontrarTodos"
									},
									type : 'POST',
									datatype : 'json',
									success : function(data) {
										$("#loading").remove();
										if (data.numRegistros > 0) {
											var listadoGrupoPorProducto = data.listadoGrupoPorProducto;

											//Para combos
											var listadoProducto = data.listadoProducto;
											$
													.each(
															listadoProducto,
															function(index) {
																arrProducto
																		.push(listadoProducto[index].nombre);
																arrCodigoProducto[listadoProducto[index].nombre] = listadoProducto[index].codigo;
																$("#producto")
																		.append(
																				"<option value='"+listadoProducto[index].nombre+"'>"
																						+ listadoProducto[index].nombre
																						+ "</option>");
															});
											var listadoTipoUso = data.listadoTipoUso;
											$
													.each(
															listadoTipoUso,
															function(index) {
																arrTipoUso.push(listadoTipoUso[index].nombre);
																arrCodigoTipoUso[listadoTipoUso[index].nombre] = listadoTipoUso[index].codigo;
																$("#tipoUsoListado")
																		.append(
																				"<option value='"+listadoTipoUso[index].nombre+"'>"
																						+ listadoTipoUso[index].nombre
																						+ "</option>");
															});

											var listadoGrupoProducto = data.listadoGrupoProducto;
											$
													.each(
															listadoGrupoProducto,
															function(index) {
																arrGrupoProducto
																		.push(listadoGrupoProducto[index].nombre);
																arrCodigoGrupoProducto[listadoGrupoProducto[index].nombre] = listadoGrupoProducto[index].codigo;
																$(
																		"#grupoProducto")
																		.append(
																				"<option value='"+listadoGrupoProducto[index].nombre+"'>"
																						+ listadoGrupoProducto[index].nombre
																						+ "</option>");
															});

											var listadoTipoGrupo = data.listadoTipoGrupo;
											$
													.each(
															listadoTipoGrupo,
															function(index) {
																arrTipoGrupo
																		.push(listadoTipoGrupo[index].nombre);
																arrCodigoTipoGrupo[listadoTipoGrupo[index].nombre] = listadoTipoGrupo[index].codigo;
																$("#tipoGrupo")
																		.append(
																				"<option value='"+listadoTipoGrupo[index].nombre+"'>"
																						+ listadoTipoGrupo[index].nombre
																						+ "</option>");
															});
											

														$.each(listadoGrupoPorProducto,function(index) {
																$("#dataTableContent").append(
																						"<tr class='odd gradeX'>"
																						+ " <td relation='nombreComercialProducto'>"
																						+ listadoGrupoPorProducto[index].nombreComercialProducto
																						+ "</td>"
																						+ " <td relation='producto'>"
																						+ listadoGrupoPorProducto[index].producto
																						+ "</td>"
																						+ " <td relation='grupoProducto'>"
																						+ listadoGrupoPorProducto[index].grupoProducto
																						+ "</td>"
																						+ " <td relation='tipoGrupo'>"
																						+ listadoGrupoPorProducto[index].tipoGrupo
																						+ "</td>"
																						+ " <td relation='tasaFija'>"
																						+ listadoGrupoPorProducto[index].tasaFija
																						+ "</td>"
																						+ " <td relation='formulada'>"
																						+ listadoGrupoPorProducto[index].formulada
																						+ "</td>"
																						+ " <td relation='activo'>"
																						+ listadoGrupoPorProducto[index].activo
																						+ "</td>"
																						+ " <td relation='porcentajeTasaFija'>"
																						+ listadoGrupoPorProducto[index].porcentajeTasaFija
																						+ "</td>"
																						+ " <td relation='porcentajeExtrasTasaFija' >"
																						+ listadoGrupoPorProducto[index].porcentajeExtrasTasaFija
																						+ "</td>"
																						+ " <td relation='tieneSumaAsegurada' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneSumaAsegurada
																						+ "</td>"
																						+ " <td relation='sumaAseguradaInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].sumaAseguradaInicio
																						+ "</td>"
																						+ " <td relation='sumaAseguradaFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].sumaAseguradaFin
																						+ "</td>"
																						+ " <td relation='tieneAntiguedadVh' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneAntiguedadVh
																						+ "</td>"
																						+ " <td relation='antiguedadInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].antiguedadInicio
																						+ "</td>"
																						+ " <td relation='antiguedadFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].antiguedadFin
																						+ "</td>"
																						+ " <td relation='tieneDispositivoRastreo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneDispositivoRastreo
																						+ "</td>"
																						+ " <td relation='tieneTipoVehiculo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneTipoVehiculo
																						+ "</td>"
																						+ " <td relation='tipoVehiculoNombre' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tipoVehiculoNombre
																						+ "</td>"
																						+ " <td relation='tieneTonelaje' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneTonelaje
																						+ "</td>"
																						+ " <td relation='valorTonelajeInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorTonelajeInicio
																						+ "</td>"
																						+ " <td relation='valorTonelajeFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorTonelajeFin
																						+ "</td>"
																						+ " <td relation='tieneRegion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneRegion
																						+ "</td>"
																						+ " <td relation='valorRegion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorRegion
																						+ "</td>"
																						+ " <td relation='tieneDeducible' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneDeducible
																						+ "</td>"
																						+ " <td relation='deduciblePorcentajeSiniestro' style='display: none'>"
																						+ listadoGrupoPorProducto[index].deduciblePorcentajeSiniestro
																						+ "</td>"
																						+ " <td relation='deduciblePorcentajeValorAsegurado' style='display: none'>"
																						+ listadoGrupoPorProducto[index].deduciblePorcentajeValorAsegurado
																						+ "</td>"
																						+ " <td relation='deducibleMinimo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].deducibleMinimo
																						+ "</td>"
																						+ " <td relation='tieneDeduciblePerdidaTotalSiniestro' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneDeduciblePerdidaTotalSiniestro
																						+ "</td>"
																						+ " <td relation='deduciblePerdidaTotalSiniestro' style='display: none'>"
																						+ listadoGrupoPorProducto[index].deduciblePerdidaTotalSiniestro
																						+ "</td>"
																						+ " <td relation='tieneAnoFabricacion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneAnoFabricacion
																						+ "</td>"
																						+ " <td relation='anoFabricacionInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].anoFabricacionInicio
																						+ "</td>"
																						+ " <td relation='anoFabricacionFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].anoFabricacionFin
																						+ "</td>"
																						+ " <td relation='tieneEdadConductor' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneEdadConductor
																						+ "</td>"
																						+ " <td relation='edadConductorInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].edadConductorInicio
																						+ "</td>"
																						+ " <td relation='edadConductorFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].edadConductorFin
																						+ "</td>"
																						+ " <td relation='tieneMarca' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneMarca
																						+ "</td>"
																						+ " <td relation='marcaListado' style='display: none'>"
																						+ listadoGrupoPorProducto[index].marcaListado
																						+ "</td>"
																						+ " <td relation='tieneModelo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneModelo
																						+ "</td>"
																						+ " <td relation='modeloListado' style='display: none'>"
																						+ listadoGrupoPorProducto[index].modeloListado
																						+ "</td>"
																						+ " <td relation='ceroKilometros' style='display: none'>"
																						+ listadoGrupoPorProducto[index].ceroKilometros
																						+ "</td>"
																						+ " <td relation='tieneConductorGenero' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneConductorGenero
																						+ "</td>"
																						+ " <td relation='conductorGeneroValor' style='display: none'>"
																						+ listadoGrupoPorProducto[index].conductorGeneroValor
																						+ "</td>"
																						+ " <td relation='tieneNumeroHijos' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneNumeroHijos
																						+ "</td>"
																						+ " <td relation='numeroHijos' style='display: none'>"
																						+ listadoGrupoPorProducto[index].numeroHijos
																						+ "</td>"
																						+ " <td relation='tieneZona' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneZona
																						+ "</td>"
																						+ " <td relation='valorZona' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorZona
																						+ "</td>"
																						+ " <td relation='tieneGuardaGarage' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneGuardaGarage
																						+ "</td>"
																						+ " <td relation='tieneKilometrosRecorridos' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneKilometrosRecorridos
																						+ "</td>"
																						+ " <td relation='kilometrosRecorridosInicio' style='display: none'>"
																						+ listadoGrupoPorProducto[index].kilometrosRecorridosInicio
																						+ "</td>"
																						+ " <td relation='kilometrosRecorridosFin' style='display: none'>"
																						+ listadoGrupoPorProducto[index].kilometrosRecorridosFin
																						+ "</td>"
																						+ " <td relation='tieneCombustibleUtilizado' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneCombustibleUtilizado
																						+ "</td>"
																						+ " <td relation='combustibleUtilizadoValorId' style='display: none'>"
																						+ listadoGrupoPorProducto[index].combustibleUtilizadoValorId
																						+ "</td>"
																						+ " <td relation='tieneTipoUso' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneTipoUso
																						+ "</td>"
																						+ " <td relation='tipoUsoListado' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tipoUsoListado
																						+ "</td>"
																						+ " <td relation='tieneCargaPasajeros' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneCargaPasajeros
																						+ "</td>"
																						+ " <td relation='cargaPasajerosValor' style='display: none'>"
																						+ listadoGrupoPorProducto[index].cargaPasajerosValor
																						+ "</td>"
																						+ " <td relation='tieneAdquisicion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneAdquisicion
																						+ "</td>"
																						+ " <td relation='nombreAdquisicion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].nombreAdquisicion
																						+ "</td>"
																						+ " <td relation='esFlotaIndividual' style='display: none'>"
																						+ listadoGrupoPorProducto[index].esFlotaIndividual
																						+ "</td>"
																						+ " <td relation='valorFlotaIndividual' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorFlotaIndividual
																						+ "</td>"
																						+ " <td relation='tieneTipoObjetoVehiculo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneTipoObjetoVehiculo
																						+ "</td>"
																						+ " <td relation='valorTipoObjetoVehiculo' style='display: none'>"
																						+ listadoGrupoPorProducto[index].valorTipoObjetoVehiculo
																						+ "</td>"
																						+ " <td relation='tieneRenovacion' style='display: none'>"
																						+ listadoGrupoPorProducto[index].tieneRenovacion
																						+ "</td>"
																						+ " <td relation='isInspeccionRequerida' style='display: none'>"
																						+ listadoGrupoPorProducto[index].isInspeccionRequerida
																						+ "</td>"
																						+ " <td width='175px'>"
																						+ " <input type='hidden' value='"+ listadoGrupoPorProducto[index].codigo +"'/>"
																						+ " <button type='button' class='btn btn-success btn-xs actualizar-btn'>"
																						+ " <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar"
																						+ " </button>"
																						+ " <button type='button' class='btn btn-danger btn-xs eliminar-btn'>"
																						+ "<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar"
																						+ " </button>"
																						+ "</td>"
																						+ "</tr>");

															});

										/* Inicio Controles Actualizar Registro*/
										$(".actualizar-btn").bind({
															click : function() {
																$("#addButton").trigger("click");
																$("#codigo").val($(this).parent().children().first().val());
																var elem = $(this).parent();
																var bandera = 1;
																do {
																	elem = elem.prev();
																	if (elem.is("td")) {
																		var elemCode = elem.attr("relation");
																		elementType(
																				elem.text(),
																				elemCode,
																				$("#" + elemCode).attr("type"));
																	} else {
																		bandera = 0;
																	}
																} while (bandera == 1);
															}
														});
										/* Fin Controles Actualizar Registro*/

											/* Inicio Controles Eliminar Registro */
											$(".eliminar-btn").bind({
																click : function() {
																	var r = confirm("Seguro que desea eliminar?"
																			+ $(this).parent().parent().children().first().text());
																	if (r == true) {
																		codigo = $(this).parent().children().first().val();
																		nombreComercialProducto = "";																		
																		producto = "";
																		grupoProducto = "";
																		tipoGrupo = "";
																		tasaFija = "";
																		formulada = "";
																		activo = "";
																		porcentajeTasaFija = "";
																		porcentajeExtrasTasaFija = "";
																		tieneSumaAsegurada = "";
																		sumaAseguradaInicio = "";
																		sumaAseguradaFin = "";
																		tieneAntiguedadVh = "";
																		antiguedadInicio = "";
																		antiguedadFin = "";
																		tieneDispositivoRastreo = "";
																		tieneTipoVehiculo = "";
																		tipoVehiculoNombre = "";
																		tieneTonelaje = "";
																		valorTonelajeInicio = "";
																		valorTonelajeFin = "";
																		tieneRegion = "";
																		valorRegion = "";
																		tieneDeducible = "";
																		deduciblePorcentajeSiniestro = "";
																		deduciblePorcentajeValorAsegurado = "";
																		deducibleMinimo = "";
																		tieneDeduciblePerdidaTotalSiniestro = "";
																		deduciblePerdidaTotalSiniestro = "";
																		tieneAnoFabricacion = "";
																		anoFabricacionInicio = "";
																		anoFabricacionFin = "";
																		tieneEdadConductor = "";
																		edadConductorInicio = "";
																		edadConductorFin = "";
																		tieneMarca = "";
																		marcaListado = "";
																		tieneModelo = "";
																		modeloListado = "";
																		ceroKilometros = "";
																		tieneConductorGenero = "";
																		conductorGeneroValor = "";
																		tieneNumeroHijos = "";
																		numeroHijos = "";
																		tieneZona = "";
																		valorZona = "";
																		tieneGuardaGarage = "";
																		tieneKilometrosRecorridos = "";
																		kilometrosRecorridosInicio = "";
																		kilometrosRecorridosFin = "";
																		tieneCombustibleUtilizado = "";
																		combustibleUtilizadoValorId = "";
																		tieneTipoUso = "";
																		tipoUsoListado = "";
																		tieneCargaPasajeros = "";
																		cargaPasajerosValor = "";
																		tieneAdquisicion = "";
																		nombreAdquisicion = "";
																		esFlotaIndividual = "";
																		valorFlotaIndividual = "";
																		tieneTipoObjetoVehiculo = "";
																		valorTipoObjetoVehiculo = "";
																		isInspeccionRequerida = "";
																		tipoConsulta = "buscarIdEnPoliza";
																		var auxExisteEnPoliza = existeEnPoliza(codigo, tipoConsulta);
																		if(auxExisteEnPoliza == "0"){
																		tipoConsulta = "eliminar";
																		enviarDatos(
																				nombreComercialProducto,
																				codigo,
																				producto,
																				grupoProducto,
																				tipoGrupo,
																				tasaFija,
																				activo,
																				formulada,																				
																				porcentajeTasaFija,
																				porcentajeExtrasTasaFija,
																				tieneSumaAsegurada,
																				sumaAseguradaInicio,
																				sumaAseguradaFin,
																				tieneAntiguedadVh,
																				antiguedadInicio,
																				antiguedadFin,
																				tieneDispositivoRastreo,
																				tieneTipoVehiculo,
																				tipoVehiculoNombre,
																				tieneTonelaje,
																				valorTonelajeInicio,
																				valorTonelajeFin,
																				tieneRegion,
																				valorRegion,
																				tieneDeducible,
																				deduciblePorcentajeSiniestro,
																				deduciblePorcentajeValorAsegurado,
																				deducibleMinimo,
																				tieneDeduciblePerdidaTotalSiniestro,
																				deduciblePerdidaTotalSiniestro,
																				tieneAnoFabricacion,
																				anoFabricacionInicio,
																				anoFabricacionFin,
																				tieneEdadConductor,
																				edadConductorInicio,
																				edadConductorFin,
																				tieneMarca,
																				marcaListado,
																				tieneModelo,
																				modeloListado,
																				ceroKilometros,
																				tieneConductorGenero,
																				conductorGeneroValor,
																				tieneNumeroHijos,
																				numeroHijos,
																				tieneZona,
																				valorZona,
																				tieneGuardaGarage,
																				tieneKilometrosRecorridos,
																				kilometrosRecorridosInicio,
																				kilometrosRecorridosFin,
																				tieneCombustibleUtilizado,
																				combustibleUtilizadoValorId,
																				tieneTipoUso,
																				tipoUsoListado,
																				tieneCargaPasajeros,
																				cargaPasajerosValor,
																				tieneAdquisicion,
																				nombreAdquisicion,
																				esFlotaIndividual,
																				valorFlotaIndividual,
																				tieneTipoObjetoVehiculo,
																				valorTipoObjetoVehiculo,
																				isInspeccionRequerida,
																				tipoConsulta);
																		$(this).parent().parent().remove();
																		}else{
																			alert("El Grupo Por Producto no pueder ser eliminado\nConsulte al Administrador.");																			
																		}
																	}
																}
															});
											/* Fin Controles Eliminar Registro */
										} else {
											$("#dataTableContent").append("<tr><td colspan='4'>No existen Registros</td></tr>");
										}
										$(".hideColumn").hide();
									}
								});

						/* Inicio Controles Grabar Registro*/
						$("#save-record").click(function() {
											$(".required").css("border","1px solid #ccc");
											$(".required").each(function(index) {
																var cadena = $(this).val();
																if (cadena.length <= 0) {
																	$(this).css("border","1px solid red");
																	alert("Por favor ingrese el campo requerido");
																	$(this).focus();
																	return false;
																}
															});

											if ($("#activo").is(':checked')){
												activo = 1;
											}else{
												activo = 0;
											}
											codigo = $("#codigo").val();
											nombreComercialProducto = $("#nombreComercialProducto").val();										
											producto = $("#producto").val();
											grupoProducto = $("#grupoProducto").val();
											tipoGrupo = $("#tipoGrupo").val();
											if ($("#tasaFija").is(':checked')){
												tasaFija = 1;
											}else{
												tasaFija = 0;
											}
											if ($("#formulada").is(':checked')){
												formulada = 1;
											}else{
												formulada = 0;
											}
											porcentajeTasaFija = $("#porcentajeTasaFija").val();
											porcentajeExtrasTasaFija = $("#porcentajeExtrasTasaFija").val();
											if ($("#tieneSumaAsegurada").is(':checked')){
												tieneSumaAsegurada = 1;
											}else{
												tieneSumaAsegurada = 0;
											}
											sumaAseguradaInicio = $("#sumaAseguradaInicio").val();
											sumaAseguradaFin = $("#sumaAseguradaFin").val();
											if ($("#tieneAntiguedadVh").is(':checked')){
												tieneAntiguedadVh = 1;
											}else{
												tieneAntiguedadVh = 0;
											}
											antiguedadInicio = $("#antiguedadInicio").val();
											antiguedadFin = $("#antiguedadFin").val();
											if ($("#tieneDispositivoRastreo").is(':checked')){ 
												tieneDispositivoRastreo = 1;
											}else{
												tieneDispositivoRastreo = 0;
											}
											if ($("#tieneTipoVehiculo").is(':checked')){
												tieneTipoVehiculo = 1;
											}else{
												tieneTipoVehiculo = 0;
											}
											tipoVehiculoNombre = $("#tipoVehiculoNombre").val();
											if ($("#tieneTonelaje").is(':checked')){
												tieneTonelaje = 1;
											}else{
												tieneTonelaje = 0;
											}
											valorTonelajeInicio = $("#valorTonelajeInicio").val();
											valorTonelajeFin = $("#valorTonelajeFin").val();
											if ($("#tieneRegion").is(':checked')){
												tieneRegion = 1;
											}else{
												tieneRegion = 0;
											}
											valorRegion = $("#valorRegion").val();
											if ($("#tieneDeducible").is(':checked')){
												tieneDeducible = 1;
											}else{
												tieneDeducible = 0;
											}
											deduciblePorcentajeSiniestro = $("#deduciblePorcentajeSiniestro").val();
											deduciblePorcentajeValorAsegurado = $("#deduciblePorcentajeValorAsegurado").val();
											deducibleMinimo = $("#deducibleMinimo").val();
											if ($("#tieneDeduciblePerdidaTotalSiniestro").is(':checked')){
												tieneDeduciblePerdidaTotalSiniestro = 1;
											}else{
												tieneDeduciblePerdidaTotalSiniestro = 0;
											}
											deduciblePerdidaTotalSiniestro = $("#deduciblePerdidaTotalSiniestro").val();
											if ($("#tieneAnoFabricacion").is(':checked')){
												tieneAnoFabricacion = 1;
											}else{
												tieneAnoFabricacion = 0;
											}
											anoFabricacionInicio = $("#anoFabricacionInicio").val();
											anoFabricacionFin = $("#anoFabricacionFin").val();
											if ($("#tieneEdadConductor").is(':checked')){
												tieneEdadConductor = 1;
											}else{
												tieneEdadConductor = 0;
											} 
											edadConductorInicio = $("#edadConductorInicio").val();
											edadConductorFin = $("#edadConductorFin").val();
											if ($("#tieneMarca").is(':checked')){
												tieneMarca = 1;
											}else{
												tieneMarca = 0;
											}
											marcaListado = $("#marcaListado").val();
											if ($("#tieneModelo").is(':checked')){
												tieneModelo = 1;
											}else{
												tieneModelo = 0;
											}
											modeloListado = $("#modeloListado").val();											
											if ($("#ceroKilometros").is(':checked')){
												ceroKilometros = 1;
											}else{
												ceroKilometros = 0;
											} 
											if ($("#tieneConductorGenero").is(':checked')){
												tieneConductorGenero = 1;
											}else{
												tieneConductorGenero = 0;
											}
											conductorGeneroValor = $("#conductorGeneroValor").val();
											if ($("#tieneNumeroHijos").is(':checked')){
												tieneNumeroHijos = 1;
											}else{
												tieneNumeroHijos = 0;
											}
											numeroHijos = $("#numeroHijos").val();
											if ($("#tieneZona").is(':checked')){
												tieneZona = 1;
											}else{
												tieneZona = 0;
											}
											valorZona = $("#valorZona").val();
											if ($("#tieneGuardaGarage").is(':checked')){
												tieneGuardaGarage = 1;
											}else{
												tieneGuardaGarage = 0;
											}
											if ($("#tieneKilometrosRecorridos").is(':checked')){
												tieneKilometrosRecorridos = 1;
											}else{
												tieneKilometrosRecorridos = 0;
											}
											kilometrosRecorridosInicio = $("#kilometrosRecorridosInicio").val();
											kilometrosRecorridosFin = $("#kilometrosRecorridosFin").val();
											if ($("#tieneCombustibleUtilizado").is(':checked')){
												tieneCombustibleUtilizado = 1;
											}else{
												tieneCombustibleUtilizado = 0;
											}
											combustibleUtilizadoValorId = $("#combustibleUtilizadoValorId").val();
											if ($("#tieneTipoUso").is(':checked')){
												tieneTipoUso = 1;
											}else{
												tieneTipoUso = 0;
											}
											tipoUsoListado = $("#tipoUsoListado").val();
											if ($("#tieneCargaPasajeros").is(':checked')){
												tieneCargaPasajeros = 1;
											}else{
												tieneCargaPasajeros = 0;
											}
											cargaPasajerosValor = $("#cargaPasajerosValor").val();
											if ($("#tieneAdquisicion").is(':checked')){
												tieneAdquisicion = 1;
											}else{
												tieneAdquisicion = 0;
											}
											nombreAdquisicion = $("#nombreAdquisicion").val();											
											if ($("#esFlotaIndividual").is(':checked')){
												esFlotaIndividual = 1;
											}else{
												esFlotaIndividual = 0;
											}
											valorFlotaIndividual = $("#valorFlotaIndividual").val();
											if ($("#tieneTipoObjetoVehiculo").is(':checked')){
												tieneTipoObjetoVehiculo = 1;
											}else{
												tieneTipoObjetoVehiculo = 0;
											}
											valorTipoObjetoVehiculo = $("#valorTipoObjetoVehiculo").val();
											if ($("#isInspeccionRequerida").is(':checked')){
												isInspeccionRequerida = 1;
											}else{
												isInspeccionRequerida = 0;
											}
											if (codigo == ""){
												tipoConsulta = "crear";
											}else{
												tipoConsulta = "actualizar";
											}
											enviarDatos(
													nombreComercialProducto,
													codigo,
													producto,
													grupoProducto,
													tipoGrupo,
													tasaFija,
													activo,
													formulada,
													porcentajeTasaFija,
													porcentajeExtrasTasaFija,
													tieneSumaAsegurada,
													sumaAseguradaInicio,
													sumaAseguradaFin,
													tieneAntiguedadVh,
													antiguedadInicio,
													antiguedadFin,
													tieneDispositivoRastreo,
													tieneTipoVehiculo,
													tipoVehiculoNombre,
													tieneTonelaje,
													valorTonelajeInicio,
													valorTonelajeFin,
													tieneRegion,
													valorRegion,
													tieneDeducible,
													deduciblePorcentajeSiniestro,
													deduciblePorcentajeValorAsegurado,
													deducibleMinimo,
													tieneDeduciblePerdidaTotalSiniestro,
													deduciblePerdidaTotalSiniestro,
													tieneAnoFabricacion,
													anoFabricacionInicio,
													anoFabricacionFin,
													tieneEdadConductor,
													edadConductorInicio,
													edadConductorFin,
													tieneMarca,
													marcaListado,
													tieneModelo,
													modeloListado,
													ceroKilometros,
													tieneConductorGenero,
													conductorGeneroValor,
													tieneNumeroHijos,
													numeroHijos,
													tieneZona,
													valorZona,
													tieneGuardaGarage,
													tieneKilometrosRecorridos,
													kilometrosRecorridosInicio,
													kilometrosRecorridosFin,
													tieneCombustibleUtilizado,
													combustibleUtilizadoValorId,
													tieneTipoUso,
													tipoUsoListado,
													tieneCargaPasajeros,
													cargaPasajerosValor,
													tieneAdquisicion,
													nombreAdquisicion,
													esFlotaIndividual,
													valorFlotaIndividual,
													tieneTipoObjetoVehiculo,
													valorTipoObjetoVehiculo,
													isInspeccionRequerida,
													tipoConsulta);
										});
						/* Fin Controles Grabar Registro*/

						$("#close-popup").click(function() {
							location.reload();
						});

						function existeEnPoliza(codigo, tipoConsulta) {
							var retorno;
								$.ajax({
										url : '../GrupoPorProductoController',
										data : {											
											"codigo" : codigo,											
											"tipoConsulta" : tipoConsulta
										},
										type : 'POST',
										async: false,
										datatype : 'json',
										success : function(data) {
											var auxExiste = data.existeEnPoliza.toString();											
											if(auxExiste == "1"){
												retorno = "1";
											}else{
												retorno = "0";
											}
										}
									});
								return retorno;
						}						
								
						function enviarDatos(nombreComercialProducto, codigo,
								producto, grupoProducto, tipoGrupo, tasaFija,activo,
								formulada, porcentajeTasaFija,
								porcentajeExtrasTasaFija, tieneSumaAsegurada,
								sumaAseguradaInicio, sumaAseguradaFin,
								tieneAntiguedadVh, antiguedadInicio,
								antiguedadFin, tieneDispositivoRastreo,
								tieneTipoVehiculo, tipoVehiculoNombre,
								tieneTonelaje, valorTonelajeInicio,
								valorTonelajeFin, tieneRegion, valorRegion,
								tieneDeducible, deduciblePorcentajeSiniestro,
								deduciblePorcentajeValorAsegurado,
								deducibleMinimo,
								tieneDeduciblePerdidaTotalSiniestro,
								deduciblePerdidaTotalSiniestro,
								tieneAnoFabricacion, anoFabricacionInicio,
								anoFabricacionFin, tieneEdadConductor,
								edadConductorInicio, edadConductorFin,
								tieneMarca, marcaListado, tieneModelo,
								modeloListado, ceroKilometros,
								tieneConductorGenero, conductorGeneroValor,
								tieneNumeroHijos, numeroHijos, tieneZona,
								valorZona, tieneGuardaGarage,
								tieneKilometrosRecorridos,
								kilometrosRecorridosInicio,
								kilometrosRecorridosFin,
								tieneCombustibleUtilizado,
								combustibleUtilizadoValorId, tieneTipoUso,
								tipoUsoListado, tieneCargaPasajeros,
								cargaPasajerosValor, tieneAdquisicion,
								nombreAdquisicion, esFlotaIndividual,
								valorFlotaIndividual, tieneTipoObjetoVehiculo,
								valorTipoObjetoVehiculo, isInspeccionRequerida,
								tipoConsulta) {
								$.ajax({
										url : '../GrupoPorProductoController',
										data : {
											"nombreComercialProducto" : nombreComercialProducto,
											"codigo" : codigo,
											"producto" : producto,
											"grupoProducto" : grupoProducto,
											"tipoGrupo" : tipoGrupo,
											"tasaFija" : tasaFija,
											"activo" : activo,
											"formulada" : formulada,
											"porcentajeTasaFija" : porcentajeTasaFija,
											"porcentajeExtrasTasaFija" : porcentajeExtrasTasaFija,
											"tieneSumaAsegurada" : tieneSumaAsegurada,
											"sumaAseguradaInicio" : sumaAseguradaInicio,
											"sumaAseguradaFin" : sumaAseguradaFin,
											"tieneAntiguedadVh" : tieneAntiguedadVh,
											"antiguedadInicio" : antiguedadInicio,
											"antiguedadFin" : antiguedadFin,
											"tieneDispositivoRastreo" : tieneDispositivoRastreo,
											"tieneTipoVehiculo" : tieneTipoVehiculo,
											"tipoVehiculoNombre" : tipoVehiculoNombre,
											"tieneTonelaje" : tieneTonelaje,
											"valorTonelajeInicio" : valorTonelajeInicio,
											"valorTonelajeFin" : valorTonelajeFin,
											"tieneRegion" : tieneRegion,
											"valorRegion" : valorRegion,
											"tieneDeducible" : tieneDeducible,
											"deduciblePorcentajeSiniestro" : deduciblePorcentajeSiniestro,
											"deduciblePorcentajeValorAsegurado" : deduciblePorcentajeValorAsegurado,
											"deducibleMinimo" : deducibleMinimo,
											"tieneDeduciblePerdidaTotalSiniestro" : tieneDeduciblePerdidaTotalSiniestro,
											"deduciblePerdidaTotalSiniestro" : deduciblePerdidaTotalSiniestro,
											"tieneAnoFabricacion" : tieneAnoFabricacion,
											"anoFabricacionInicio" : anoFabricacionInicio,
											"anoFabricacionFin" : anoFabricacionFin,
											"tieneEdadConductor" : tieneEdadConductor,
											"edadConductorInicio" : edadConductorInicio,
											"edadConductorFin" : edadConductorFin,
											"tieneMarca" : tieneMarca,
											"marcaListado" : marcaListado,
											"tieneModelo" : tieneModelo,
											"modeloListado" : modeloListado,
											"ceroKilometros" : ceroKilometros,
											"tieneConductorGenero" : tieneConductorGenero,
											"conductorGeneroValor" : conductorGeneroValor,
											"tieneNumeroHijos" : tieneNumeroHijos,
											"numeroHijos" : numeroHijos,
											"tieneZona" : tieneZona,
											"valorZona" : valorZona,
											"tieneGuardaGarage" : tieneGuardaGarage,
											"tieneKilometrosRecorridos" : tieneKilometrosRecorridos,
											"kilometrosRecorridosInicio" : kilometrosRecorridosInicio,
											"kilometrosRecorridosFin" : kilometrosRecorridosFin,
											"tieneCombustibleUtilizado" : tieneCombustibleUtilizado,
											"combustibleUtilizadoValorId" : combustibleUtilizadoValorId,
											"tieneTipoUso" : tieneTipoUso,
											"tipoUsoListado" : tipoUsoListado,
											"tieneCargaPasajeros" : tieneCargaPasajeros,
											"cargaPasajerosValor" : cargaPasajerosValor,
											"tieneAdquisicion" : tieneAdquisicion,
											"nombreAdquisicion" : nombreAdquisicion,
											"esFlotaIndividual" : esFlotaIndividual,
											"valorFlotaIndividual" : valorFlotaIndividual,
											"tieneTipoObjetoVehiculo" : tieneTipoObjetoVehiculo,
											"valorTipoObjetoVehiculo" : valorTipoObjetoVehiculo,
											"isInspeccionRequerida" : isInspeccionRequerida,
											"tipoConsulta" : tipoConsulta
										},
										type : 'POST',
										datatype : 'json',
										success : function(data) {
											var auxID = data.id.toString();
											var auxTasaFija = data.tasaFijaFinal.toString();
											var auxFormuladaFinal = data.formuladaFinal.toString();
											
											if(auxTasaFija == "false" && auxFormuladaFinal == "false" ){
												alert("Debe Ingresar Tasa Por Producto");												
 												window.location = "TasaProducto.jsp?id=" + auxID;
											}
											
											if (data.success){
												$("#msgPopup").show();												
											}else{
												alert(data.error);
												}
										}
									});
						}
		
					});

	// 		metodos para mostrar u ocultar opciones
	//SumaAsegurada
	$(function() {
		$('#tieneSumaAsegurada').change(function() {
			if ($("#tieneSumaAsegurada").is(
			':checked')) {
				$('#sumasAseguradas').show();
			} else {
				$('#sumasAseguradas').hide();
			}
		});
	});
	//Tasa Fija
		$(function() {
		$('#tasaFija').change(function() {
			if ($("#tasaFija").is(
			':checked')) {
				$('#tasasFijas').show();
			} else {
				$('#tasasFijas').hide();
			}
		});
	});
		//tieneAntiguedadVh
		$(function() {
		$('#tieneAntiguedadVh').change(function() {
			if ($("#tieneAntiguedadVh").is(
			':checked')) {
				$('#antiguedades').show();
			} else {
				$('#antiguedades').hide();
			}
		});
	});
		//tieneTipoVehiculo
		$(function() {
		$('#tieneTipoVehiculo').change(function() {
			if ($("#tieneTipoVehiculo").is(
			':checked')) {
				$('#vehiculos').show();
			} else {
				$('#vehiculos').hide();
			}
		});
	});
		//tieneTonelaje
		$(function() {
		$('#tieneTonelaje').change(function() {
			if ($("#tieneTonelaje").is(
			':checked')) {
				$('#tonelajes').show();
			} else {
				$('#tonelajes').hide();
			}
		});
	});
		
		//tieneRegion
		$(function() {
		$('#tieneRegion').change(function() {
			if ($("#tieneRegion").is(
			':checked')) {
				$('#regiones').show();
			} else {
				$('#regiones').hide();
			}
		});
	});
		//tieneDeducible
		$(function() {
		$('#tieneDeducible').change(function() {
			if ($("#tieneDeducible").is(
			':checked')) {
				$('#deducibles').show();
			} else {
				$('#deducibles').hide();
			}
		});
	});
		
		//tieneDeduciblePerdidaTotalSiniestro
			$(function() {
		$('#tieneDeduciblePerdidaTotalSiniestro').change(function() {
			if ($("#tieneDeduciblePerdidaTotalSiniestro").is(
			':checked')) {
				$('#perdidasTotal').show();
			} else {
				$('#perdidasTotal').hide();
			}
		});
	});
			//tieneAnoFabricacion
			$(function() {
		$('#tieneAnoFabricacion').change(function() {
			if ($("#tieneAnoFabricacion").is(
			':checked')) {
				$('#aniosFabricacion').show();
			} else {
				$('#aniosFabricacion').hide();
			}
		});
	});
			//tieneEdadConductor
			$(function() {
		$('#tieneEdadConductor').change(function() {
			if ($("#tieneEdadConductor").is(
			':checked')) {
				$('#edadesConductor').show();
			} else {
				$('#edadesConductor').hide();
			}
		});
	});
			//tieneMarca
			$(function() {
		$('#tieneMarca').change(function() {
			if ($("#tieneMarca").is(
			':checked')) {
				$('#marcas').show();
			} else {
				$('#marcas').hide();
			}
		});
	});
			//tieneModelo
			$(function() {
		$('#tieneModelo').change(function() {
			if ($("#tieneModelo").is(
			':checked')) {
				$('#modelos').show();
			} else {
				$('#modelos').hide();
			}
		});
	});
			//tieneConductorGenero
						$(function() {
		$('#tieneConductorGenero').change(function() {
			if ($("#tieneConductorGenero").is(
			':checked')) {
				$('#generos').show();
			} else {
				$('#generos').hide();
			}
		});
	});
						//tieneNumeroHijos
						$(function() {
		$('#tieneNumeroHijos').change(function() {
			if ($("#tieneNumeroHijos").is(
			':checked')) {
				$('#numerosHijos').show();
			} else {
				$('#numerosHijos').hide();
			}
		});
	});
						//tieneZona
						
$(function() {
		$('#tieneZona').change(function() {
			if ($("#tieneZona").is(
			':checked')) {
				$('#zonas').show();
			} else {
				$('#zonas').hide();
			}
		});
	});
						
//tieneKilometrosRecorridos
$(function() {
		$('#tieneKilometrosRecorridos').change(function() {
			if ($("#tieneKilometrosRecorridos").is(
			':checked')) {
				$('#kilometros').show();
			} else {
				$('#kilometros').hide();
			}
		});
	});
//tieneCombustibleUtilizado						
$(function() {
		$('#tieneCombustibleUtilizado').change(function() {
			if ($("#tieneCombustibleUtilizado").is(
			':checked')) {
				$('#combustibles').show();
			} else {
				$('#combustibles').hide();
			}
		});
	});
//tieneTipoUso						
$(function() {
		$('#tieneTipoUso').change(function() {
			if ($("#tieneTipoUso").is(
			':checked')) {
				$('#tiposUsos').show();
			} else {
				$('#tiposUsos').hide();
			}
		});
	});
//tieneCargaPasajeros
$(function() {
		$('#tieneCargaPasajeros').change(function() {
			if ($("#tieneCargaPasajeros").is(
			':checked')) {
				$('#cargasPasajeros').show();
			} else {
				$('#cargasPasajeros').hide();
			}
		});
	});
//tieneAdquisicion
$(function() {
		$('#tieneAdquisicion').change(function() {
			if ($("#tieneAdquisicion").is(
			':checked')) {
				$('#adquisiciones').show();
			} else {
				$('#adquisiciones').hide();
			}
		});
	});
	//esFlotaIndividual
$(function() {
		$('#esFlotaIndividual').change(function() {
			if ($("#esFlotaIndividual").is(
			':checked')) {
				$('#flotasIndividual').show();
			} else {
				$('#flotasIndividual').hide();
			}
		});
	});
//tieneTipoObjetoVehiculo
$(function() {
		$('#tieneTipoObjetoVehiculo').change(function() {
			if ($("#tieneTipoObjetoVehiculo").is(
			':checked')) {
				$('#tiposObjetoVehiculos').show();
			} else {
				$('#tiposObjetoVehiculos').hide();
			}
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
	<div class="row crud-nav-bar">
		<!-- Button trigger modal -->
		<button class="btn btn-primary" data-toggle="modal" data-target="#add"
			id="addButton">
			<span class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
		</button>

		<!-- Modal -->
		<div class="modal fade" id="add" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="formCrud">
						<div class="modal-header">							
							<h4 class="modal-title" id="myModalLabel">Grupo por Producto</h4>
						</div>
						<div class="modal-body" style="overflow-y : auto;">
							<div class="alert alert-success" id="msgPopup">Grabado con
								exito.</div>
							<div class="form-group">
								<div class="col-md-4">
									<input type="hidden" class="form-control" id="codigo">
									<label>Nombre Comercial</label>
									<input type="text"class="form-control required" id="nombreComercialProducto">
									<label>Producto</label> <select type="select"
										class="form-control required" id="producto">
										<option value='0'>Seleccione una opcion</option>
									</select> <label>Grupo Producto</label> <select type="select"
										class="form-control required" id="grupoProducto">
										<option value='0'>Seleccione una opcion</option>
									</select> <label>Tipo Grupo</label> <select type="select"
										class="form-control required" id="tipoGrupo">
										<option value='0'>Seleccione una opcion</option>
									</select>
									<div class="checkbox">
										<label> <input type="checkbox" value="tasaFija"
											id="tasaFija">Tasa Fija
										</label>
									</div>
									<div class="checkbox">
										<label> <input type="checkbox" value="formulada"
											id="formulada">Formulada
										</label>
									</div>
									<div class="checkbox">
										<label> <input type="checkbox" value="activo"
											id="activo">Activo
										</label>
									</div>
									<div class="well" id = "tasasFijas">
									<label>Porcentaje Tasa Fija</label> <input type="text"
										class="form-control" id="porcentajeTasaFija">
									<label>Porcentaje Extras Tasa Fija</label> <input type="text"
										class="form-control" id="porcentajeExtrasTasaFija">
										</div>
									<div class="checkbox">									
										<label> <input type="checkbox"
											value="tieneSumaAsegurada" id="tieneSumaAsegurada">Tiene
											Suma Asegurada
										</label>
									</div>
									<div class="well" id = "sumasAseguradas">
									<label>Suma Asegurada Inicio</label> <input type="text"
										class="form-control" id="sumaAseguradaInicio">
									<label>Suma Asegurada Fin</label> <input type="text"
										class="form-control" id="sumaAseguradaFin">
									</div>										
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneAntiguedadVh" id="tieneAntiguedadVh">Tiene
											Antiguedad Vh
										</label>
									</div>
									<div class="well" id = "antiguedades">
									<label>Antiguedad Inicio</label> <input type="text"
										class="form-control" id="antiguedadInicio"> <label>Antiguedad
										Fin</label> <input type="text" class="form-control"
										id="antiguedadFin">	
									</div>							
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneDispositivoRastreo" id="tieneDispositivoRastreo">Tiene
											Dispositivo Rastreo
										</label>
									</div>
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneTipoVehiculo" id="tieneTipoVehiculo">Tiene
											Tipo Vehiculo
										</label>
									</div>
									<div class="well" id = "vehiculos">
									<label>Tipo Vehiculo Nombre</label> <input type="text"
										class="form-control" id="tipoVehiculoNombre">
									</div>	
									<div class="checkbox">
										<label> <input type="checkbox" value="tieneTonelaje"
											id="tieneTonelaje">Tiene Tonelaje
										</label>
									</div>
									<div class="well" id = "tonelajes">
									<label>Valor Tonelaje Inicio</label> <input type="text"
										class="form-control" id="valorTonelajeInicio">
									<label>Valor Tonelaje Fin</label> <input type="text"
										class="form-control" id="valorTonelajeFin">
									</div>																		
								</div>
								<div class="col-md-4">
									<div class="checkbox">
										<label> <input type="checkbox" value="tieneRegion"
											id="tieneRegion">Tiene Region
										</label>
									</div>
									<div class="well" id = "regiones">
									<label>Valor Region</label> <input type="text"
										class="form-control" id="valorRegion">
									</div>
									<div class="checkbox">
										<label> <input type="checkbox" value="tieneDeducible"
											id="tieneDeducible">Tiene Deducible
										</label>
									</div>
									<div class="well" id = "deducibles">
									<label>Deducible Porcentaje Siniestro</label> <input
										type="text" class="form-control"
										id="deduciblePorcentajeSiniestro"> <label>Deducible
										Porcentaje Valor Asegurado</label> <input type="text"
										class="form-control"
										id="deduciblePorcentajeValorAsegurado"> <label>Deducible
										Minimo</label> <input type="text" class="form-control"
										id="deducibleMinimo">
									</div>
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneDeduciblePerdidaTotalSiniestro"
											id="tieneDeduciblePerdidaTotalSiniestro">Tiene
											Deducible Perdida Total Siniestro
										</label>
									</div>
									<div class="well" id = "perdidasTotal">
									<label>Deducible Perdida Total Siniestro</label> <input
										type="text" class="form-control"
										id="deduciblePerdidaTotalSiniestro">
									</div>
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneAnoFabricacion" id="tieneAnoFabricacion">Tiene
											Ano Fabricacion
										</label>
									</div>
									<div class="well" id = "aniosFabricacion">
									<label>Ano Fabricacion Inicio</label> <input type="text"
										class="form-control" id="anoFabricacionInicio">
									<label>Ano Fabricacion Fin</label> <input type="text"
										class="form-control" id="anoFabricacionFin">
								    </div>
									<div class="checkbox">
										<label> <input type="checkbox"
											value="tieneEdadConductor" id="tieneEdadConductor">Tiene
											Edad Conductor
										</label>
									</div>
									<div class="well" id = "edadesConductor">								
									<label>Edad Conductor Inicio</label> <input type="text"
										class="form-control" id="edadConductorInicio">
									<label>Edad Conductor Fin</label> <input type="text"
										class="form-control" id="edadConductorFin">
									</div>
									<div class="checkbox">
										<label><input type="checkbox" value="tieneMarca"
											id="tieneMarca">Tiene Marca</label>
									</div>
									<div class="well" id = "marcas">
									<label>Marca</label> <input type="text"
										class="form-control" id="marcaListado">
									</div>

									<div class="checkbox">
										<label><input type="checkbox" value="tieneModelo"
											id="tieneModelo">Tiene Modelo</label>
									</div>
									<div class="well" id = "modelos">
									<label>Modelo</label> <input type="text"
										class="form-control" id="modeloListado">
									</div>

									<div class="checkbox">
										<label><input type="checkbox" value="ceroKilometros"
											id="ceroKilometros">Cero Kilometros</label>
									</div>

									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneConductorGenero" id="tieneConductorGenero">Tiene
											Conductor Genero</label>
									</div>
									<div class="well" id = "generos">
									<label>Conductor Genero</label> <select type="select"
										class="form-control" id="conductorGeneroValor">
										<option value = '0'>Seleccione una Opcion</option>										
										<option value = 'M'>M</option>
										<option value = 'F'>F</option>
									</select>
									</div>
									<div class="checkbox">
										<label><input type="checkbox" value="tieneNumeroHijos"
											id="tieneNumeroHijos">Tiene Numero Hijos</label>
									</div>
									<div class="well" id = "numerosHijos">
									<label>Numero Hijos</label> <input type="text"
										class="form-control" id="numeroHijos">
									</div>
									<div class="checkbox">
										<label><input type="checkbox" value="tieneZona"
											id="tieneZona">Tiene Zona</label>
									</div>
									<div class="well" id = "zonas">
									<label>Zona</label> <input type="text"
										class="form-control" id="valorZona">
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneGuardaGarage" id="tieneGuardaGarage">Tiene
											Guarda Garage</label>
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneKilometrosRecorridos"
											id="tieneKilometrosRecorridos">Tiene Kilometros
											Recorridos</label>
									</div>
									<div class="well" id = "kilometros">
									<label>Kilometros Recorridos Inicio</label> <input type="text"
										class="form-control" id="kilometrosRecorridosInicio">
									<label>Kilometros Recorridos Fin</label> <input type="text"
										class="form-control" id="kilometrosRecorridosFin">
									</div>
									</div>
									<div class="col-md-4">
									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneCombustibleUtilizado"
											id="tieneCombustibleUtilizado">Tiene Combustible
											Utilizado</label>
									</div>
									<div class="well" id = "combustibles">
									<label>Combustible Utilizado</label> <select type="select"
										class="form-control" id="combustibleUtilizadoValorId">
										<option value='0'>Seleccione una opcion</option>
										<option value="G">&nbsp;&nbsp;Gasolina </option>
										<option value="D">&nbsp;&nbsp;Diesel </option>
										<option value="E">&nbsp;&nbsp;Gasolina/Electricidad</option>										
									</select>
									</div>
									<div class="checkbox">
										<label><input type="checkbox" value="tieneTipoUso"
											id="tieneTipoUso">Tiene Tipo Uso</label>
									</div>
									<div class="well" id = "tiposUsos">
									<label>Tipo Uso</label> <select type="select"
										class="form-control" id="tipoUsoListado">
										<option value='0'>Seleccione una opcion</option>
									</select>
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneCargaPasajeros" id="tieneCargaPasajeros">Tiene
											Carga Pasajeros</label>
									</div>
									<div class="well" id = "cargasPasajeros">
									<label>Carga Pasajeros</label> <input type="text"
										class="form-control" id="cargaPasajerosValor">
									</div>

									<div class="checkbox">
										<label><input type="checkbox" value="tieneAdquisicion"
											id="tieneAdquisicion">Tiene Adquisicion</label>
									</div>
									<div class="well" id = "adquisiciones">
									<label>Adquisicion</label> <input type="text"
										class="form-control" id="nombreAdquisicion">
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="esFlotaIndividual" id="esFlotaIndividual">Es
											Flota Individual</label>
									</div>
									<div class="well" id = "flotasIndividual">
									<label>Valor Flota Individual</label> <input type="text"
										class="form-control" id="valorFlotaIndividual">
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="tieneTipoObjetoVehiculo" id="tieneTipoObjetoVehiculo">Tiene
											Tipo Objeto Vehiculo</label>
									</div>
									<div class="well" id = "tiposObjetoVehiculos">
									<label>Tipo Objeto Vehiculo</label> <input type="text"
										class="form-control" id="valorTipoObjetoVehiculo">
									</div>	
									<div class="checkbox">
										<label><input type="checkbox" value="tieneRenovacion"
											id="tieneRenovacion">Tiene Renovacion</label>
									</div>
									<div class="checkbox">
										<label><input type="checkbox"
											value="isInspeccionRequerida" id="isInspeccionRequerida">Inspeccion
											Requerida</label>
									</div>
								</div>
							</div>
						</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" id="close-popup"
									data-dismiss="modal">Cerrar</button>
								<button type="button" class="btn btn-primary" id="save-record">Guardar</button>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->


	<!-- Datatable -->	
		<div class="row">
			<div class="col-lg-12">
				<div class="table-responsive">
					<br>
					<div class="input-group">
						<span class="input-group-addon">Filtro</span> <input id="filter"
							type="text" class="form-control"
							placeholder="Escriba la palabra a buscar...">
					</div>
					<br>
					<table class="table table-striped table-bordered table-hover"
						id="dataTable">
						<thead>
							<tr>								
								<th>Nombre Comercial</th>
								<th>Producto</th>
								<th>Grupo Producto</th>
								<th>Tipo Grupo</th>
								<th>Tasa Fija</th>
								<th>Formulada</th>
								<th>Activo</th>
								<th>Porcentaje Tasa Fija</th>
								<th>Porcentaje Extras Tasa Fija</th>
								<th style="display: none">Tiene Suma Asegurada</th>
								<th style="display: none">Suma Asegurada Inicio</th>
								<th style="display: none">Suma Asegurada Fin</th>
								<th style="display: none">Tiene Antiguedad Vh</th>
								<th style="display: none">Antiguedad Inicio</th>
								<th style="display: none">Antiguedad Fin</th>
								<th style="display: none">Tiene Dispositivo Rastreo</th>
								<th style="display: none">Tiene Tipo Vehiculo</th>
								<th style="display: none">Tipo Vehiculo Nombre</th>
								<th style="display: none">Tiene Tonelaje</th>
								<th style="display: none">Valor Tonelaje Inicio</th>
								<th style="display: none">Valor Tonelaje Fin</th>
								<th style="display: none">Tiene Region</th>
								<th style="display: none">Valor Region</th>
								<th style="display: none">Tiene Deducible</th>
								<th style="display: none">Deducible Porcentaje Siniestro</th>
								<th style="display: none">Deducible Porcentaje Valor Asegurado</th>
								<th style="display: none">Deducible Minimo</th>
								<th style="display: none">Tiene Deducible Perdida Total Siniestro</th>
								<th style="display: none">Deducible Perdida Total Siniestro</th>
								<th style="display: none">Tiene Ano Fabricacion</th>
								<th style="display: none">Ano Fabricacion Inicio</th>
								<th style="display: none">Ano Fabricacion Fin</th>
								<th style="display: none">Tiene Edad Conductor</th>
								<th style="display: none">Edad Conductor Inicio</th>
								<th style="display: none">Edad Conductor Fin</th>
								<th style="display: none">Tiene Marca</th>
								<th style="display: none">Marca</th>
								<th style="display: none">Tiene Modelo</th>
								<th style="display: none">Modelo</th>
								<th style="display: none">Cero Kilometros</th>
								<th style="display: none">Tiene Conductor Genero</th>
								<th style="display: none">Conductor Genero</th>
								<th style="display: none">Tiene Numero Hijos</th>
								<th style="display: none">Numero Hijos</th>
								<th style="display: none">Tiene Zona</th>
								<th style="display: none">Zona</th>
								<th style="display: none">Tiene Guarda Garage</th>
								<th style="display: none">Tiene Kilometros Recorridos</th>
								<th style="display: none">Kilometros Recorridos Inicio</th>
								<th style="display: none">Kilometros Recorridos Fin</th>
								<th style="display: none">Tiene Combustible Utilizado</th>
								<th style="display: none">Combustible Utilizado</th>
								<th style="display: none">Tiene Tipo Uso</th>
								<th style="display: none">Tipo Uso</th>
								<th style="display: none">Tiene Carga Pasajeros</th>
								<th style="display: none">Carga Pasajeros</th>
								<th style="display: none">Tiene Adquisicion</th>
								<th style="display: none">Adquisicion</th>
								<th style="display: none">Es Flota Individual</th>
								<th style="display: none">Valor Flota Individual</th>
								<th style="display: none">Tiene Tipo Objeto Vehiculo</th>
								<th style="display: none">Tipo Objeto Vehiculo</th>
								<th style="display: none">Tiene Renovacion</th>
								<th style="display: none">Inspeccion Requerida</th>
								<th>Acción</th>
							</tr>
						</thead>
						<tbody id="dataTableContent" class="searchable">

							<div id="loading">
								<div class="loading-indicator">
									<img src="../static/images/ajax-loader.gif" /><br />
									<br /> <span id="loading-msg">Cargando...</span>
								</div>
							</div>

						</tbody>
					</table>
				</div>
			</div>
		</div>	
	<!-- Datatable -->
</body>
</html>