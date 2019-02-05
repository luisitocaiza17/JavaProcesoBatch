<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Solicitud Inspección - CotizadorQBE</title>

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



<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
	var codigo = "";
	var cotizacion = "";
	var inspector = "";
	var estado = "";
	var usuario = "";
	var telf = "";
	var origen = "";
	var destino = "";
	var valor = "";
	var fecha = "";

	$(document).ready(function() {
		
		$("#panelNuevoComentario").removeAttr("style").hide();
		$("#panelComentarios").removeAttr("style").hide();
		$("#panelNuevoArchivo").removeAttr("style").hide();
		$("#panelArchivos").removeAttr("style").hide();
		

	activarMenu("InspeccionesRealizadas");
						$("#addButton").hide();

						$
								.ajax({
									url : '../SolicitudInspeccionController',
									data : {
										"tipoConsulta" : "encontrarRealizadas"
									},
									type : 'POST',
									datatype : 'json',
									success : function(data) {
										$("#loading").fadeOut();
										if (data.numRegistros > 0) {
											var listadoSolicitudInspeccion = data.listadoSolicitudInspeccion;
											$.each(listadoSolicitudInspeccion,function(index) {

																$(
																		"#dataTableContent")
																		.append(
																				"	<tr class='odd gradeX'>"
																						+ " <td relation='codigo'>"
																						+ listadoSolicitudInspeccion[index].codigo
																						+ "</td>"
																						+ " <td relation='codigoCotizacion'>"
																						+ listadoSolicitudInspeccion[index].codigoCotizacion
																						+ "</td>"
																						+ " <td relation='inspector'>"
																						+ listadoSolicitudInspeccion[index].inspector
																						+ "</td>"
																						+ " <td relation='estado'>"
																						+ listadoSolicitudInspeccion[index].estado
																						+ "</td>"
																						+ " <td relation='usuario'>"
																						+ listadoSolicitudInspeccion[index].usuario
																						+ "</td>"
																						+ " <td relation='telfContacto'>"
																						+ listadoSolicitudInspeccion[index].telfContacto
																						+ "</td>"
																						+ " <td relation='origen'>"
																						+ listadoSolicitudInspeccion[index].origen
																						+ "</td>"
																						+ " <td relation='destino'>"
																						+ listadoSolicitudInspeccion[index].destino
																						+ "</td>"
																						+ " <td relation='valor'>"
																						+ listadoSolicitudInspeccion[index].valorInspeccion
																						+ "</td>"
																						+ " <td relation='fechaSolicitud'>"
																						+ listadoSolicitudInspeccion[index].fechaSolicitud
																						+ "</td>"
																						+ " <td'>"
																						+" <td width='175px'>" +
																						" <input type='hidden' value='"+ listadoSolicitudInspeccion[index].codigo +"'/>" +
																						" <button type='button' class='btn btn-success btn-xs actualizar-btn' onClick='cargarComentarios("+listadoSolicitudInspeccion[index].codigo+",event);'>" +
														  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Comentarios" +
																						" </button><div heigth='10px'></div>" +
																						" <button type='button' class='btn btn-success btn-xs actualizar-btn' onClick='cargarArchivos("+listadoSolicitudInspeccion[index].codigo+",event);'>" +
													  									" <span class='glyphicon glyphicon glyphicon-folder-open'></span> Archivos" +
																						" </button>" +
																						
																						"</td>" +

																						+ "</tr>");

															});
											
											/* Inicio Controles Actualizar Registro*/
											$(".actualizar-btn").bind({click: function() {
												
												$("#addButton").trigger("click");
													$("#codigo").val($(this).parent().children().first().val());
													
													
												}
											});
											
											$(".archivos-btn").bind({click: function() {
												$("#sampleFile").attr("name",$("#codigoInspeccion").val());
										    	
												$("#addButton").trigger("click");
													$("#codigo").val($(this).parent().children().first().val());
													
													
												}
											});

										} else {
											$("#dataTableContent")
													.append(
															"<tr><td colspan='12'>No existen Registros</td></tr>");
										}

									}
								});
						
						$("#archivoInspeccion").change(function(){
							var aux=this.files[0];
							if(aux!=null)

								$("#uploadBtn").removeAttr("disabled");

							else
								$("uploadBtn").attr("disabled","disabled");
						});
					});

	function cargarComentarios(id, event) {
		$("#panelNuevoComentario").removeAttr("style").hide();
		$("#panelComentarios").removeAttr("style").hide();
		$("#panelNuevoArchivo").removeAttr("style").hide();
		$("#panelArchivos").removeAttr("style").hide();
		$("#loadingComentarios").fadeIn();
		if (event != null) {
			var target = event.target || event.srcElement;
			$("#fechaInspeccionComentario").html(
					$(target).parent().prev().html());
			$("#valorInspeccionComentario").html(
					$(target).parent().prev().prev().html());
			$("#destinoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().html());
			$("#origenInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().html());
			$("#telefonoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.html());
			$("#usuarioInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().html());
			$("#inspectorInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().html());
			$("#cotizacionInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().prev().html());
			$("#codigoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().prev().prev().html());
		}
		$("#dataTableComentarios").empty();
		$("#codigoInspeccion").val(id);
		$
				.ajax({
					url : '../ComentarioSolicitudInspeccionController',
					data : {
						"codigoSolicitudInspecccion" : id,
						"tipoConsulta" : "encontrarPorSolicitudInspeccion"
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						if (data.success) {

							var listadoComentarioSolicitudInspeccion = data.listadoComentarioSolicitudInspeccion;
							$
									.each(
											listadoComentarioSolicitudInspeccion,
											function(index) {
												$("#dataTableComentarios")
														.append(
																"	<tr class='odd gradeX'>"
																		+ " <td relation='usuario'>"
																		+ listadoComentarioSolicitudInspeccion[index].usuario
																		+ "</td>"
																		+ " <td relation='fecha'>"
																		+ listadoComentarioSolicitudInspeccion[index].fecha
																		+ "</td>"
																		+ " <td relation='descripcion'>"
																		+ listadoComentarioSolicitudInspeccion[index].descripcion
																		+ "</td></tr>");
											});
							$("#loadingComentarios").fadeOut();
							$("#panelNuevoComentario").attr("style",
									"display: block;").fadeIn();
							if (listadoComentarioSolicitudInspeccion.length > 0)
								$("#panelComentarios").attr("style",
										"display: block;").fadeIn();
							$("#tablaDatosComentarios").fadeIn();
							$("#msgPopupComentario").hide();
						} else
							alert(data.error);
					}
				});
	}

	function guardarComentario() {
		var inspeccionId = $("#codigoInspeccion").val();
		if ($("#descripcionComentario").val().trim() != "")
			$.ajax({
				url : '../ComentarioSolicitudInspeccionController',
				data : {
					"codigoSolicitudInspecccion" : inspeccionId,
					"descripcion" : $("#descripcionComentario").val().trim(),
					"tipoConsulta" : "crear"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if (data.success) {
						$("#descripcionComentario").val("");
						$("#msgPopupComentario").html("Se guardo el comentario con exito!").fadeIn();
						cargarComentarios(inspeccionId, null);
					} else
						alert(data.error);
				}
			});
	}

	function cargarArchivos(id, event) {

		$("#loadingComentarios").fadeIn();
		if (event != null) {
			var target = event.target || event.srcElement;
			$("#fechaInspeccionComentario").html(
					$(target).parent().prev().html());
			$("#valorInspeccionComentario").html(
					$(target).parent().prev().prev().html());
			$("#destinoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().html());
			$("#origenInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().html());
			$("#telefonoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.html());
			$("#usuarioInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().html());
			$("#inspectorInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().html());
			$("#cotizacionInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().prev().html());
			$("#codigoInspeccionComentario").html(
					$(target).parent().prev().prev().prev().prev().prev()
							.prev().prev().prev().prev().prev().html());
		}
		$("#panelNuevoComentario").removeAttr("style").hide();
		$("#panelComentarios").removeAttr("style").hide();
		$("#dataTableArchivos").empty();
		$("#codigoInspeccion").val(id);
		$.ajax({
			url : '../ArchivoSolicitudInspeccionController',
			data : {
				"codigoSolicitudInspecccion" : id,
				"tipoConsulta" : "encontrarPorSolicitudInspeccion"
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				if (data.success) {

					var listadoArchivosSolicitudInspeccion = data.listadoArchivosSolicitudInspeccion;
					$.each(listadoArchivosSolicitudInspeccion,function(index) {
										$("#dataTableArchivos")
												.append(
														"	<tr class='odd gradeX'>"
																+ " <td relation='nombre'>"
																+ listadoArchivosSolicitudInspeccion[index].nombre
																+ "</td>"
																+" <td width='175px'>" +
																" <input type='hidden' value='"+ listadoArchivosSolicitudInspeccion[index].codigo +"'/>" +
																" <a class='btn btn-success btn-xs ' href='../ArchivoSolicitudInspeccionController?codigo="+ listadoArchivosSolicitudInspeccion[index].codigo +"'>" +
							  									" <span class='glyphicon glyphicon glyphicon-download'></span> Descargar" +
																" </a>" +
															"</td></tr>");
									});
					$("#loadingComentarios").fadeOut();
					$("#panelNuevoArchivo").attr("style","display: block;").fadeIn();
					if (listadoArchivosSolicitudInspeccion.length > 0)
						$("#panelArchivos").attr("style","display: block;").fadeIn();
					$("#tablaDatosArchivos").fadeIn();
					$("#msgPopupComentario").hide();
				} else
					alert(data.error);
			}
		});
	}

    function performAjaxSubmit() {
    	$("#archivoInspeccion").attr("id","archivoInspeccion"+$("#codigoInspeccion").val());
    	
    	var archivo = document.getElementById("archivoInspeccion"+$("#codigoInspeccion").val()).files[0];

        var formdata = new FormData();

       formdata.append("archivoSolicitudInspeccion"+$("#codigoInspeccion").val(), archivo);
       
       var xhr = new XMLHttpRequest();    
       xhr.overrideMimeType('text/plain;');

        xhr.open("POST","../CargaArchivosController", true);

        xhr.send(formdata);

        xhr.onload = function(e) {

            if (this.status == 200) {
            	cargarArchivos($("#codigoInspeccion").val(),null);
            	//$("#msgPopupComentario").html();

               alert(this.responseText);

            }

        };                    

    }   
    
    function eliminarArchivo(id) {
    	var r = confirm("Seguro desea eliminar el archivo?");
    	if (r == true) {
    		$.ajax({
				url : '../ArchivoSolicitudInspeccionController',
				data : {
					"codigo" : id,
					"tipoConsulta" : "eliminar"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if (data.success) {
						cargarArchivos($("#codigoInspeccion").val(), null);
					} else
						alert(data.error);
				}
			});
    	}
	}
    
	function cambiarAInspeccionado(){
		var inspeccionId = $("#codigoInspeccion").val();
		$.ajax({
			url : '../SolicitudInspeccionController',
			data : {
				"codigo" : inspeccionId,
				"tipoConsulta" : "actualizarEstado",
				"estado":"Aprobada",
				"enviarCorreo":"si"
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				alert('Se actualizo el estado de la Solicitud');
			}
		});
	}
</script>
</head>
<body>
	<%
			// Permitimos el acceso si la session existe		
				if(session.getAttribute("login") == null){
				    response.sendRedirect("/CotizadorWeb");
				}
%>
	<div class="row crud-nav-bar">
		<div id="loading" style="position: fixed;left: 50%;">
			<div class="loading-indicator">
				<img src="../static/images/ajax-loader.gif" /><br /> <br /> <span
					id="loading-msg">Cargando Inspecciones ...</span>
			</div>
		</div>
	</div>
	<!-- Button trigger modal -->
	<button hidden="hidden" class="btn btn-primary" data-toggle="modal"
		data-target="#add" id="addButton">
		<span class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
	</button>

	<!-- Modal -->
	<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content">
				<form id="formCrud">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Comentarios</h4>
					</div>
					<div class="modal-body">
						<div id="loadingComentarios">
							<div class="loading-indicator">
								<img src="../static/images/ajax-loader.gif" /><br /> <br /> <span
									id="loading-msg">Cargando...</span>
							</div>
						</div>
						<div hidden="hidden" class="alert alert-success"
							id="msgPopupComentario">El comentario se guardo con exito.</div>
						<div class="form-group">
							<input type="hidden" class="form-control" id="codigoInspeccion">

							<table width="100%">
								<tr height-max=300px">
									<td>
										<!-- Datatable -->
										<div class="row">
											<div class="col-lg-12">
												<div class="table-responsive">
													<table hidde="hidden" id="tablaDatosComentarios"
														class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<th>Codigo</th>
																<th>Cotizacion</th>
																<th>Inspector</th>
																<th>Usuario</th>
																<th>Telefono</th>
																<th>Origen</th>
																<th>Destino</th>
																<th>Valor</th>
																<th>Fecha</th>

															</tr>
														</thead>
														<tbody>
															<tr>
																<td id="codigoInspeccionComentario"></td>
																<td id="cotizacionInspeccionComentario"></td>
																<td id="inspectorInspeccionComentario"></td>
																<td id="usuarioInspeccionComentario"></td>
																<td id="telefonoInspeccionComentario"></td>
																<td id="origenInspeccionComentario"></td>
																<td id="destinoInspeccionComentario"></td>
																<td id="valorInspeccionComentario"></td>
																<td id="fechaInspeccionComentario"></td>
															</tr>

														</tbody>
													</table>
												</div>
											</div>
										</div>
									</td>
								</tr>

								<!-- archivos   -->

								<!-- inicio tabla comentarios -->
								<tr height-max=300px">
									<td>
										<div hidden="hidden" class="panel panel-default"
											id="panelComentarios">
											<div class="panel-heading">
												<b>Listado de Comentarios</b>
											</div>
											<div class="panel-body">
												<!-- Datatable -->
												<div class="row">
													<div class="col-lg-12">
														<div class="table-responsive">
															<table
																class="table table-striped table-bordered table-hover"
																id="tablaComentarios">
																<thead>
																	<tr>
																		<th>Usuario</th>
																		<th>Fecha</th>
																		<th>Descripcion</th>

																	</tr>
																</thead>
																<tbody id="dataTableComentarios" class="searchable" style="font-size: small;"></tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
								<!-- fin tabla comentarios -->
								<!-- inicio tabla archivos -->
								<iframe hidden="hidden" src="">
									<form action="../ArchivoSolicitudInspeccionController?"
										method="post"></form>
								</iframe>
								<tr height-max=300px">
									<td>
										<div hidden="hidden" class="panel panel-default"
											id="panelArchivos">
											<div class="panel-heading">
												<b>Listado de Archivos</b>
											</div>
											<div class="panel-body">

												<!-- Datatable -->
												<div class="row">
													<div class="col-lg-12">
														<div class="table-responsive">
															<table
																class="table table-striped table-bordered table-hover"
																id="tablaArchivos">
																<thead>
																	<tr>
																		<th>nombre</th>
																		<th></th>

																	</tr>
																</thead>
																<tbody id="dataTableArchivos" class="searchable"
																	style="font-size: small;">

																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>

								</tr>
								<!-- fin tabla archivos -->
							</table>
						</div>
					</div>					
				</form>
			</div>
		</div>
	</div>
	<!-- Modal -->



	<!-- Datatable -->
	<div class="row">
		<div class="col-lg-12">
			<div class="table-responsive">
				<div class="input-group">
					<span class="input-group-addon">Filtro</span> <input id="filter"
						type="text" class="form-control"
						placeholder="Escriba la palabra a buscar...">
				</div>
				<br>
				<table class="table table-striped table-bordered table-hover" id="dataTable">
					<thead>
						<tr>
							<th>Id Inspección</th>
							<th>Cotización</th>
							<th>Inspector</th>
							<th>Estado</th>
							<th>Usuario</th>
							<th>Telefono Contacto</th>
							<th>Origen</th>
							<th>Destino</th>
							<th>Valor Inspeccion</th>
							<th>Fecha Solicitud</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="dataTableContent" class="searchable"
						style="font-size: small;">

					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Datatable -->
</body>
</html>