<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Tipo de Rol por Modulo - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		var codigo = "";
		var moduloId = "";
		var rolId = "";
		var tipoConsulta = "";

		var arrRol = new Array();
		var arrCodigoRol = new Object();
		var arrModulo = new Array();
		var arrCodigoModulo = new Object();
		
		$(document).ready(function() {
			activarMenu("TipoRolModulo");
			
			$.ajax({
				url : '../TipoRolModuloController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#loading").remove();
					var listadoTipoRolModulo = data.listadoTipoRolModulo;
					var listadoRol = data.listaRol;
					var listadoModulo= data.listaModulo;
					
			    	$.each(listadoRol, function(index){
			    		arrRol.push(listadoRol[index].nombre );
			    		arrCodigoRol[listadoRol[index].nombre] = listadoRol[index].codigo;
		                $("#rolId").append("<option value='"+listadoRol[index].nombre+"'>"+listadoRol[index].nombre+"</option>");
			    	});
			    	
			    	$.each(listadoModulo, function(index){
			    		arrModulo.push(listadoModulo[index].nombre );
			    		arrCodigoModulo[listadoModulo[index].nombre] = listadoModulo[index].codigo;
		                $("#moduloId").append("<option value='"+listadoModulo[index].nombre+"'>"+listadoModulo[index].nombre+"</option>");
			    	});
			    	
					if(data.numRegistros > 0){
						
				    	$.each(listadoTipoRolModulo, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='moduloId'>"+ listadoTipoRolModulo[index].moduloId +"</td>" +
									" <td relation='rolId'>"+ listadoTipoRolModulo[index].rolId +"</td>" +
									" <td width='175px'>" +
										" <input type='hidden' value='"+ listadoTipoRolModulo[index].codigo +"'/>" +
										" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
		  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
										" </button>" +
										" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
										  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
										" </button>" +
									"</td>" +
								"</tr>");
					
						});
						
						/* Inicio Controles Actualizar Registro*/
						$(".actualizar-btn").bind({click: function() {
								$("#addButton").trigger("click");
								$("#codigo").val($(this).parent().children().first().val());
								var elem = $(this).parent();
								var bandera = 1;
								do {
									elem = elem.prev();
									if (elem.is("td")){
										var elemCode = elem.attr("relation");
										elementType(elem.text(), elemCode, $("#"+elemCode).attr("type"));
									}else {
										bandera = 0;
									}
								} while (bandera == 1);
							  }
						});
						/* Fin Controles Actualizar Registro*/
						
						/* Inicio Controles Elminar Registro */
						$(".eliminar-btn").bind({click: function() {
								var r = confirm("Seguro que desea eliminar?" + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									nombre = ""; rolId = ""; moduloId = ""; 
									tipoConsulta = "eliminar";
									enviarDatos(codigo, moduloId, rolId,tipoConsulta);
							    	$(this).parent().parent().remove();
								}
							}
						});	
						/* Fin Controles Elminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='5'>No existen Registros</td></tr>");
					}
				}
			});			
			
			/* Inicio Controles Grabar Resgistro*/
				$("#save-record").click(function() {
					var retorno=true;
					$(".required").css("border", "1px solid #ccc");
					$(".required").each(function(index) {
						
						var cadena = $(this).val();
						if (cadena.length <= 0&&retorno) {
							$(this).css("border", "1px solid red");
							alert("Por favor ingrese el campo requerido");
							$(this).focus();
							retorno= false;
						}
					});
		
					
					codigo = $("#codigo").val();
					moduloId = arrCodigoModulo[$("#moduloId").val()];
					rolId = arrCodigoRol[$("#rolId").val()];
				
					if (codigo == ""){
						tipoConsulta = "crear";
					}else{
						tipoConsulta = "actualizar";
						}
					if(retorno){
					enviarDatos(codigo, moduloId, rolId, tipoConsulta);
					}
				});
			/* Fin Controles Grabar Resgistro*/
			
			function enviarDatos(codigo, moduloId, rolId, tipoConsulta){
				$.ajax({
					url : '../TipoRolModuloController',
					data : {
						"codigo" : codigo,
						"moduloId" : moduloId,
						"rolId" : rolId,
						"tipoConsulta": tipoConsulta
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						if(data.success)
							$("#msgPopup").show();
						else
							alert(data.error);
					}
				});
			}
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


	<div class="row crud-nav-bar">
		<!-- Button trigger modal -->
		<button class="btn btn-primary" data-toggle="modal" data-target="#add" id="addButton">
			<span class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
		</button>

		<!-- Modal -->
		<div class="modal fade" id="add" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="formCrud">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Nuevo TipoRolModulo</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">La relacion TipoRolModulo de Venta se grabo con exito.</div>
							<div class="form-group">
								<input type="hidden"class="form-control" id="codigo">
								<label>Rol</label>
								<select type="select" class="form-control required" id="rolId" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Modulo</label>
								<select type="select" class="form-control required" id="moduloId" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" id="close-popup"
								data-dismiss="modal">Cerrar</button>
							<button type="button" class="btn btn-primary" id="save-record">Guardar Cambios</button>
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
				<div class="input-group"> <span class="input-group-addon">Filter</span>
				    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
				</div>			
				<table class="table table-striped table-bordered table-hover"
					id="dataTable">
					<thead>
						<tr>
							<th>Modulo</th>
							<th>Rol</th>							
						</tr>
					</thead>
					<tbody id="dataTableContent" class="searchable">
					
						<div id="loading">
							<div class="loading-indicator">
								<img src="../static/images/ajax-loader.gif"/><br /><br />
								<span id="loading-msg">Cargando...</span>
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