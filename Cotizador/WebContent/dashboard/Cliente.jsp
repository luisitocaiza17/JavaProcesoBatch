<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Cliente - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		var codigo = "";
		var nombre = "";
		var matriz = 0;
		var activo = 0;
		var tipoConsulta = "";
		var arrActividad = new Array();
		var arrCodigoActividad = new Object();
		var arrTipoIdentificacion = new Array();
		var arrCodigoTipoIdentificacion = new Object();
		
		
		$(document).ready(function() {
			activarMenu("Cliente");
			$.ajax({
				url : '../EntidadController',
				data : {
					"tipoConsulta" : "encontrarTodos",
					"tipoEntidad": "clientes"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#loading").remove();
					var listadoActividadEconomica = data.listadoActividadEconomica;
			    	var listadoTipoIdentificacion = data.listadoTipoIdentificacion;

					if(data.numRegistros > 0){
						var listadoCliente = data.listadoCliente;
				    	
				    	$.each(listadoCliente, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='codigoEnsurance' class='hideColumn'>"+ listadoCliente[index].codigoEnsurance +"</td>" +
									" <td relation='tipoIdentificacion'>"+ listadoCliente[index].tipoIdentificacion +"</td>" +
									" <td relation='identificacion'>"+ listadoCliente[index].identificacion +"</td>" +
									" <td relation='nombre'>"+ listadoCliente[index].nombre +"</td>" +
									" <td relation='apellido'>"+ listadoCliente[index].apellido +"</td>" +
									" <td relation='actividadEconomica'>"+ listadoCliente[index].actividadEconomica +"</td>" +
									" <td relation='mail'>"+ listadoCliente[index].mail +"</td>" +
									" <td relation='activo'>"+ listadoCliente[index].activo +"</td>" +
									" <td width='175px'>" +
										" <input type='hidden' value='"+ listadoCliente[index].codigo +"'/>" +
										" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
		  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
										" </button>" +
// 										" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
// 										  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
// 										" </button>" +
										" <button type='button' class='btn btn-info btn-xs cambioEstado-btn'>" +
										  	"<span class='glyphicon glyphicon glyphicon-refresh' id='cambioEstado-btn'>Activar/Desactivar</span>" +
									" </button>" +
									"</td>" +
								"</tr>");
					
						});
				    	//Boton Activar-DesActivar
// 						$.each(listadoCliente, function(index){
// 							if($("#lblActivo").text()=="Si"){
// 							$("#cambioEstado-btn").text('Desactivar');
// 						}else{
// 							$("#cambioEstado-btn").text('Activar');
// 						}						
							
// 						});
					
						$(".cambioEstado-btn").bind({click: function() {		
								codigo = $(this).parent().children().first().val();
								var activoaux = $(this).parent().prev().text();
								 if (activoaux == "Si"){
	  						    		activo = 1;
	  						    		
	  						    	}else {
	  						    		activo = 0;
	  						    	}
								tipoConsulta = "cambioEstado";
 								enviarDatosActivo(codigo, activo, tipoConsulta);
 								alert("Estado Cambiado");
 								location.reload();
 						}
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
								var r = confirm("Seguro que desea eliminar el Cliente " + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									codigoEnsurance=""; tipoIdentificacion=""; identificacion=""; nombre=""; apellido=""; actividadEconomica=""; activo="";
									tipoConsulta = "eliminar"; mail = "";
									enviarDatos(codigoEnsurance, codigo, tipoIdentificacion, identificacion, nombre, apellido, actividadEconomica, activo, tipoConsulta,mail);
							    	$(this).parent().parent().remove();
								}
							}
						});	
						/* Fin Controles Elminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='6'>No existen Registros</td></tr>");
					}
					
					$.each(listadoActividadEconomica, function(index){
			    		arrActividad.push( listadoActividadEconomica[index].nombre );
			    		console.log(arrActividad);
		                arrCodigoActividad[listadoActividadEconomica[index].nombre] = listadoActividadEconomica[index].codigo;
		                $("#actividadEconomica").append("<option value='"+listadoActividadEconomica[index].nombre+"'>"+listadoActividadEconomica[index].nombre+"</option>");
		                //.children().last().after().html
			    	});
			    	
			    	
			    	$.each(listadoTipoIdentificacion, function(index){
			    		arrTipoIdentificacion.push( listadoTipoIdentificacion[index].nombre );
		                arrCodigoTipoIdentificacion[listadoTipoIdentificacion[index].nombre] = listadoTipoIdentificacion[index].codigo;
		                $("#tipoIdentificacion").append("<option value='"+listadoTipoIdentificacion[index].nombre+"'>"+listadoTipoIdentificacion[index].nombre+"</option>");
			    	});	
					
					$(".hideColumn").hide();
				}
			});			
			

			/* Inicio Controles Grabar Resgistro*/
				$("#save-record").click(function() {
					var retorno=true;
					$(".required").css("border", "1px solid #ccc");
					$(".required").each(function(index) {
						var cadena = $(this).val();
						if (cadena.length <= 0 && retorno) {
							$(this).css("border", "1px solid red");
							alert("Por favor ingrese el campo requerido");
							$(this).focus();
							retorno= false;
						}
					});
		
					if ($("#activo").is(':checked')) activo = 1;
					codigoEnsurance = $("#codigoEnsurance").val();
					codigo = $("#codigo").val();
					identificacion = $("#identificacion").val();
					nombre = $("#nombre").val();
					apellido = $("#apellido").val();
					mail = $("#mail").val();
					actividadEconomica = arrCodigoActividad[$("#actividadEconomica").val()]; //$("#actividadEconomica").val();
					tipoIdentificacion = arrCodigoTipoIdentificacion[$("#tipoIdentificacion").val()]; 
					if ($("#activo").is(':checked')) activo = 1;
					
					if (codigo == "")
						tipoConsulta = "crear";
					else
						tipoConsulta = "actualizar";
					
					enviarDatos(codigoEnsurance, codigo, tipoIdentificacion, identificacion, nombre, apellido, actividadEconomica, activo, tipoConsulta,mail);
				});
			/* Fin Controles Grabar Resgistro*/
			
			function enviarDatos(codigoEnsurance, codigo, tipoIdentificacion, identificacion, nombre, apellido, actividadEconomica, activo, tipoConsulta,mail){
				$.ajax({
					url : '../EntidadController',
					data : {
						"codigoEnsurance" : codigoEnsurance,
						"codigo" : codigo,
						"tipoIdentificacion": tipoIdentificacion,
						"identificacion": identificacion,
						"nombre" : nombre,
						"apellido": apellido,
						"actividadEconomica": actividadEconomica,
						"activo" : activo,
						"tipoConsulta" : tipoConsulta,
						"mail" : mail,						
						"tipoEntidad": "clientes"
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
			
			$("#identificacion").change(function(){
				$("#loading_identificacion").fadeIn();
				var identificacion=$("#identificacion").val();
				
				$.ajax({
					url : '../EntidadController',
					data : {
						"identificacion" : identificacion,
						"tipoConsulta" : "cargarPorIdentificacion"
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						var entidad=data.entidad;
						if(entidad.codigo!=null ){
						$("#codigoEnsurance").val(entidad.codigoEnsurance);
						$("#codigo").val(entidad.codigo);
						//$("#identificacion").val();
						$("#nombre").val(entidad.nombre);
						$("#apellido").val(entidad.apellido);
						$("#mail").val(entidad.mail);
						$("#tipoIdentificacion").val(entidad.tipoIdentificacion);} 
						$("#loading_identificacion").fadeOut();
						validarEntidadJr(identificacion);
					},
					error:function(){
						$("#loading_identificacion").fadeOut();
					}
				});
				
			});
			
		});
		
		
		function validarEntidadJr(identificacion){
			
			$.ajax({
				url : "../EntidadJrController",

				data : {
					"tipoConsulta" : "encontrarPorIdentificacion",
					"identificacion" : identificacion
				},
				type : 'post',
				datatype : 'json',
				success : function (data) {
					if(data.success){
							if(data.esRiesgosa){
								alert("La persona se encuentra bloqueda, no puede realizar ningun tramite");
								$("#identificacion").val("");
								$("#nombre").val("");
								$("#mail").val("");
								$("#tipoIdentificacion").val("");
								$("#codigoEnsurance").val("");
								$("#codigo").val("");
								
							}
						}
					else{

					}
				}
			});
		}
		
		function enviarDatosActivo(codigo, activo, tipoConsulta){				
			$.ajax({
				url : '../EntidadController',
				data : {
					"codigo" : codigo,
					"activo" : activo,
					"tipoConsulta" : tipoConsulta,
					"tipoEntidad" : "clientes"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					if(data.success)
						$("#msgPopup").show();
					else
						alert(data.error);
				}
			})
		};
		
		
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
							<h4 class="modal-title" id="myModalLabel">Cliente</h4>
						</div>
						<div class="modal-body">
						<div class="alert alert-info" id="msgPopup">Grabado con exito.</div>
							<div class="form-group">
								<input type="hidden"class="form-control" id="codigo">
								<label>Codigo Ensurance</label> 
								<input type="text"class="form-control required" id="codigoEnsurance">
								<label>Tipo de Identificación</label> 
								<select type="select" class="form-control required" id="tipoIdentificacion">
								  <option>Seleccione una opcion</option>
								</select>
								<label>Número de Identificación</label> 
								<input type="text"class="form-control required" id="identificacion">
								<div id="loading_identificacion" hidden="" ><span id="loading-msg">Cargando...</span><img  src="../static/images/ajax-loader.gif" /></div>
								<label>Nombre del Cliente</label> 
								<input type="text"class="form-control required" id="nombre">
								<label>Apellidos del Cliente</label> 
								<input type="text"class="form-control required" id="apellido">
								<label>Actividad Económica</label> 
								<select type="select" class="form-control required" id="actividadEconomica">
								  <option>Seleccione una opcion</option>
								</select>
								<label>E-Mail</label> 
								<input type="text"class="form-control required" id="mail">
								<div class="checkbox">
									<label> <input type="checkbox" value="activo"id="activo">Activo</label>
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
				<div class="input-group"> <span class="input-group-addon">Filter</span>
				    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
				</div>			
				<table class="table table-striped table-bordered table-hover"
					id="dataTable">
					<thead>
						<tr>
							<th class="hideColumn">Codigo Ensunrace</th>
							<th>Tipo Identificación</th>
							<th>Identificación</th>
							<th>Nombres</th>
							<th>Apellidos</th>
							<th>Actividad Económica</th>
							<th>E-Mail</th>
							<th>Activo</th>
							<th></th>
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