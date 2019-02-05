<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Agente - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		var codigo = "";
		var nombre = "";
		var matriz = 0;
		var activo = 0;
		var tipoConsulta = "";
		var arrTipoIdentificacion = new Array();
		var arrCodigoTipoIdentificacion = new Object();
		
		$(document).ready(function() {
			$('#comisiones').hide();
			activarMenu("Agente");
			$.ajax({
				url : '../EntidadController',
				data : {
					"tipoConsulta" : "encontrarTodos",
					"tipoEntidad": "agentes"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#loading").remove();
					if(data.numRegistros > 0){
						var listadoAgente = data.listadoAgente;
						var listadoTipoIdentificacion = data.listadoTipoIdentificacion;
						
						
						$.each(listadoTipoIdentificacion, function(index){
				    		arrTipoIdentificacion.push( listadoTipoIdentificacion[index].nombre );
			                arrCodigoTipoIdentificacion[listadoTipoIdentificacion[index].nombre] = listadoTipoIdentificacion[index].codigo;
			                $("#tipoIdentificacion").append("<option value='"+listadoTipoIdentificacion[index].nombre+"'>"+listadoTipoIdentificacion[index].nombre+"</option>");
				    	});				    
						
						$.each(listadoAgente, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='codigoEnsurance'>"+ listadoAgente[index].codigoEnsurance +"</td>" +
									" <td relation='tipoIdentificacion'>"+ listadoAgente[index].tipoIdentificacion+"</td>" +
									" <td relation='identificacion'>"+ listadoAgente[index].identificacion +"</td>" +
									" <td relation='nombre'>"+ listadoAgente[index].nombre +"</td>" +
									" <td class = 'hidden' relation='comisionVariable'>"+ listadoAgente[index].comisionVariable+"</td>" +
   									" <td class = 'hidden' relation='comisionVH'>"+ listadoAgente[index].comisionVH+"</td>" +
   									" <td class = 'hidden' relation='pymes'>"+ listadoAgente[index].pymes+"</td>" +
									" <td relation='agenteEnsurance'>"+ listadoAgente[index].agenteEnsurance +"</td>" +
 									" <td class = 'hidden' relation='comision1'>"+ listadoAgente[index].comision1 +"</td>" +
 									" <td class = 'hidden' relation='comision2'>"+ listadoAgente[index].comision2 +"</td>" +
 									" <td class = 'hidden' relation='comision3'>"+ listadoAgente[index].comision3 +"</td>" +
 									" <td class = 'hidden' relation='ramo'>"+ listadoAgente[index].ramo +"</td>" +
  									" <td class = 'hidden' relation='credencial'>"+ listadoAgente[index].credencial +"</td>" +
 									" <td relation='mail'>"+ listadoAgente[index].mail +"</td>" +
 									" <td id = 'lblActivo' relation='activo'>"+ listadoAgente[index].activo +"</td>" +
									" <td width='175px'>" +
									
										" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
		  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
										" </button>" +
 	//									" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
 		//								  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
			//							" </button>" +
 										" <button type='button' class='btn btn-info btn-xs cambioEstado-btn'>" +
 										  	"<span class='glyphicon glyphicon glyphicon-refresh' id='cambioEstado-btn'>Activar/Desactivar</span>" +
										" </button>" +
									"</td>" +
								"</tr>");
					
						});
						
						//Boton Activar-DesActivar
// 						$.each(listadoAgente, function(index){
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
						
						/* Inicio Controles Eliminar Registro */
						$(".eliminar-btn").bind({click: function() {
								var r = confirm("Seguro que desea eliminar el Agente " + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									nombre = ""; activo = ""; codigoEnsurance=""; 
									tipoIdentificacion=""; comisionVariable=""; comisionVH =""; pymes=""; agente=""; identificacion="";
									agenteEnsurance =""; comision1 =""; comision2 =""; comision3 =""; ramo =""; credencial ="";mail ="";
									tipoConsulta = "eliminar";
									enviarDatos(codigoEnsurance, codigo, nombre, activo, tipoConsulta, tipoIdentificacion,identificacion,comisionVariable,comisionVH,pymes,agenteEnsurance,comision1,comision2,comision3,ramo,credencial,mail);
							    	$(this).parent().parent().remove();
								}
							}
						});	
						/* Fin Controles Eliminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='4'>No existen Registros</td></tr>");
					}
					$(".hideColumn").hide();
				}
			});			
			
			/* Inicio Controles Grabar Registro*/
				$("#save-record").click(function() {
					$(".required").css("border", "1px solid #ccc");
					$(".required").each(function(index) {
						var cadena = $(this).val();
						if (cadena.length <= 0) {
							$(this).css("border", "1px solid red");
							$(this).focus();
							return false;   
						}
					});
		

					if ($("#activo").is(':checked')) activo = 1;
					codigoEnsurance = $("#codigoEnsurance").val();
					codigo = $("#codigo").val();
					identificacion = $("#identificacion").val();
					nombre = $("#nombre").val();
					mail = $("#mail").val();
					tipoIdentificacion = arrCodigoTipoIdentificacion[$("#tipoIdentificacion").val()]; 
					comisionVariable = $("#comisionVariable").val();
					comisionVH = $("#comisionVH").val();
					pymes = $("#pymes").val();
					agenteEnsurance = $("#agenteEnsurance").val();
					comision1 = $("#comision1").val();
					comision2 = $("#comision2").val();
					comision3 = $("#comision3").val();
					ramo = $("#ramo").val();
					credencial = $("#credencial").val();
					if (codigo == "")
						tipoConsulta = "crear";
					else
					tipoConsulta = "actualizar";
					
					if (identificacion=="" && nombre=="" )
						{
						alert("Ingrese al menos un valor ");
						}
					else 
					{
						codigoEnsurance = $("#codigoEnsurance").val();
						enviarDatos(codigoEnsurance, codigo, nombre, activo, tipoConsulta, tipoIdentificacion,identificacion,comisionVariable,comisionVH,pymes,agenteEnsurance,comision1,comision2,comision3,ramo,credencial,mail);
						$("#close-popup").trigger("click");
						}
					
				});
			/* Fin Controles Grabar Registro*/
			
			$("#close-popup").click(function(){
				 location.reload();
			});
			
			function enviarDatos(codigoEnsurance, codigo, nombre, activo, tipoConsulta, tipoIdentificacion,identificacion,comisionVariable,comisionVH,pymes,agenteEnsurance,comision1,comision2,comision3,ramo,credencial,mail){				
				$.ajax({
					url : '../EntidadController',
					data : {
						"codigoEnsurance" : codigoEnsurance,
						"codigo" : codigo,
						"nombre" : nombre,
						"activo" : activo,
						"tipoConsulta" : tipoConsulta,
						"tipoIdentificacion" : tipoIdentificacion,
						"identificacion" : identificacion,
						"comisionVariable" : comisionVariable,
						"comisionVH" : comisionVH,			
						"pymes" : pymes,
						"agenteEnsurance" : agenteEnsurance,
						"comision1" : comision1,
						"comision2" : comision2,
						"comision3" : comision3,
						"ramo" : ramo,
						"credencial" : credencial,
						"mail" : mail,	
						"tipoEntidad" : "agentes"
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
						"tipoConsulta" : "cargarPorIdentificacionEnsurance"
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						var entidad=data.entidad;
						if(entidad.entidadIdEnsurance!=null  ){
							$("#codigoEnsurance").val(entidad.entidadIdEnsurance);
							if(entidad.codigo!=null)
						$("#codigoEnsurance").val(entidad.codigoEnsurance);
						$("#codigo").val(entidad.codigo);
						//$("#identificacion").val();
						$("#nombre").val(entidad.nombreCompleto);
						$("#agenteEnsurance").val(entidad.agenteIdEnsurance);
						$("#credencial").val(entidad.numerocredencialEnsurance);
						//$("#apellido").val(entidad.apellido);
						$("#mail").val(entidad.mail);
						$("#tipoIdentificacion").val(entidad.tipoIdentificacionNombre); }
						$("#loading_identificacion").fadeOut();
						validarEntidadJr(identificacion)
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
					"tipoEntidad" : "agentes"
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
		};
		
// 		metodo para mostrar u ocultar comisiones variables
		$(function() {
		    $('#comisionVariable').change(function(){
		        if ($(this).val() == "1") {
		            $('#comisiones').show();
		        } else {
		            $('#comisiones').hide();
		        }
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
	<div class="row crud-nav-bar">
		<!-- Button trigger modal -->
		<button class="btn btn-primary" data-toggle="modal" data-target="#add" id="addButton">
			<span class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
		</button>

		<!-- Modal -->
		<div class="modal fade" id="add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<form id="formCrud">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Agente</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">Grabado con exito.</div>
							<div class="form-group">
							<div class="col-md-6" >
							<label>Número de Identificación</label> 
								<input type="text"class="form-control required" id="identificacion" placeholder="Identificacion">
								<div id="loading_identificacion" hidden="" ><span id="loading-msg">Cargando...</span><img  src="../static/images/ajax-loader.gif" /></div>
								<label>Tipo de Identificación</label> 
								<select type="select" class="form-control required" id="tipoIdentificacion">
								  <option>Seleccione una opcion</option>
								</select>
								<input type="hidden"class="form-control" id="codigo">
								<label>Codigo Ensurance</label> 
								<input type="text" class="form-control required" id="codigoEnsurance" disabled="disabled"  placeholder="Codigo en el Ensurance">
								<label>Nombre del Agente</label> 
								<input type="text"class="form-control required" id="nombre"  disabled="disabled" placeholder="Nombre completo del Agente">
								<label>E-Mail</label> 
								<input type="text"class="form-control required" id="mail" placeholder="Correo del Agente">
								<label>Comision Variable</label> 
								<select type="select" class="form-control" id="comisionVariable">
								  <option value = "0">No</option>
								  <option value = "1">Si</option>								  
								</select>
								<br>
								<div class="well" id = "comisiones">
								<label>Comision 1</label> 
								<input type="text"class="form-control" id="comision1" placeholder="Ej: 5.2">
								<label>Comision 2</label> 
								<input type="text"class="form-control" id="comision2" placeholder="Ej: 5.2">
								<label>Comision 3</label> 
								<input type="text"class="form-control" id="comision3" placeholder="Ej: 5.2">
								</div>
								<br>
								</div>
								<div class="col-md-6" >
								<label>Comision VH</label> 
								<input type="text"class="form-control" id="comisionVH" placeholder="Ej: 5.2">
								<label>Comision PYMES</label> 
								<input type="text"class="form-control" id="pymes" placeholder="Ej: 5.2">
								<label>Agente Ensurance</label> 
								<input type="text"class="form-control required" id="agenteEnsurance" disabled="disabled"  placeholder="Numero de Agente en el Ensurance">
								<label>Ramo Multiriesgo</label> 
								<select type="select" class="form-control" id="ramo">
								  <option value = "0">No</option>
								  <option value = "1">Si</option>								  
								</select>
								<label>Credencial</label> 
								<input type="text" class="form-control" id="credencial" disabled="disabled" placeholder="Credencial">
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
			<br>
				<div class="input-group"> <span class="input-group-addon">Filter</span>
				    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
				</div>
			<table class="table table-striped table-bordered table-hover"
					id="dataTable" style="font-size: x-small;">
					<thead>
						<tr>
							<th>Codigo Ensurance</th>
							<th>Tipo Identificacion</th>
							<th>Identificación</th>
							<th>Nombre / Razón Social</th>
							<th>Agente Ensurance</th>
							<th>E-mail</th>
							<th>Activo</th>
							<th></th>
						</tr>
					</thead>
						<tbody id="dataTableContent" class="searchable" style="font-size: x-small;">
					
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
