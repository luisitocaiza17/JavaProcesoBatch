<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Punto de Venta - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	<link rel="stylesheet" href="../static/css/select2/select2.css">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<script src="../static/js/select2.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		<!-- Inicio Variables Globales -->
		var sucursal = "";
		var nombre = "";
		var descripcion = "";
		var agente = "";
		var activo = "";
		var tipoConsulta = "";

		var arrAgente = new Array();
		var arrCodigoAgente = new Array();
        var opcionesSelect2Agente= new Array();

        var arrSucursal = new Array();
		var arrCodigoSucursal = new Array();
        var opcionesSelect2Sucursal = new Array();
        
        var arrPtoVentaEnsurance = new Array();
		var arrCodigoPtoVentaEnsurance = new Array();
        var opcionesPtoVentaEnsurance = new Array();
        
        
		$(document).ready(function() {
			activarMenu("PuntoVenta");
			$.ajax({
				url : '../PuntoVentaController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {					
					
					$("#loading").remove();
					
					//Para Cargar Combos
					var listadoAgente = data.listadoAgente;
                    $.each(listadoAgente, function (index) {
                    	arrAgente.push(listadoAgente[index].nombre);
                    	arrCodigoAgente[listadoAgente[index].nombre] = listadoAgente[index].codigo;                       
                    	opcionesSelect2Agente[index]={"id":listadoAgente[index].nombre,"text":listadoAgente[index].nombre};
                    });
                    
                    $("#agente").select2({
        				data : opcionesSelect2Agente,
        				placeholder : "Seleccione un Agente"        				
        			});
                    
					var listadoSucursal = data.listadoSucursal;
                    $.each(listadoSucursal, function (index) {
                    	arrSucursal.push(listadoSucursal[index].nombre);
                    	arrCodigoSucursal[listadoSucursal[index].nombre] = listadoSucursal[index].codigo;                       
                    	opcionesSelect2Sucursal[index]={"id":listadoSucursal[index].nombre,"text":listadoSucursal[index].nombre};
                    });
                    
                    $("#sucursal").select2({
        				data : opcionesSelect2Sucursal,
        				placeholder : "Seleccione una Sucursal"        				
        			});
                    
                    //Punto Venta Ensurance
                    var listadoPuntoVentaEnsurance = data.listadoPuntoVentaEnsurance;
                    $.each(listadoPuntoVentaEnsurance, function (index) {
                    	arrPtoVentaEnsurance.push(listadoPuntoVentaEnsurance[index].nombre);
                    	arrCodigoPtoVentaEnsurance[listadoPuntoVentaEnsurance[index].nombre] = listadoPuntoVentaEnsurance[index].codigo;                       
                    	opcionesPtoVentaEnsurance[index]={"id":listadoPuntoVentaEnsurance[index].nombre,"text":listadoPuntoVentaEnsurance[index].nombre};
                    });
                    
                    $("#codigoEnsurance").select2({
        				data : opcionesPtoVentaEnsurance,
        				placeholder : "Seleccione un Punto de Venta"        				
        			});
                    
					
					if(data.numRegistros > 0){
						var listadoPuntoVenta = data.listadoPuntoVenta;
						$.each(listadoPuntoVenta, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='sucursal'>"+ listadoPuntoVenta[index].sucursal+"</td>" +									
									" <td relation='nombre'>"+ listadoPuntoVenta[index].nombre +"</td>" +
									" <td relation='descripcion'>"+ listadoPuntoVenta[index].descripcion +"</td>" +
									" <td relation='agente'>"+ listadoPuntoVenta[index].agente +"</td>" +
									" <td relation='activo'>"+ listadoPuntoVenta[index].activo +"</td>" +
									" <td relation='codigoEnsurance'>"+ listadoPuntoVenta[index].codigoEnsurance +"</td>" +
									" <td width='175px'>" +
										" <input type='hidden' value='"+ listadoPuntoVenta[index].codigo +"'/>" +
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
								var r = confirm("Seguro que desea eliminar el Punto de Venta " + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									nombre = ""; agente = ""; activo = ""; codigoEnsurance="";
									sucursal = ""; descripcion = "";
									tipoConsulta = "eliminar";
									enviarDatos(codigoEnsurance, codigo, nombre, agente, activo, descripcion, sucursal, tipoConsulta);
							    	$(this).parent().parent().remove();
								}
							}
						});	
						/* Fin Controles Elminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='4'>No existen Registros</td></tr>");
					}
				}
			});			
			
			/* Resetea el formulario cada vez que presiona el boton nuevo */
				$("#addButton").click(function(){
					$("#msgPopup").hide();
					$("#formCrud").get(0).reset();
				});

			/* Inicio Controles Grabar Resgistro*/
				$("#save-record").click(function() {
					$(".required").css("border", "1px solid #ccc");
					$(".required").each(function(index) {
						var cadena = $(this).val();
						if (cadena.length <= 0) {
							$(this).css("border", "1px solid red");
							alert("Por favor ingrese el campo requerido");
							$(this).focus();
							return false;
						}
					});
		
					if ($("#activo").is(':checked')) activo = 1;
					codigo = $("#codigo").val();
					nombre = $("#nombre").val();
					sucursal = arrCodigoSucursal[$("#sucursal").select2("val")];
					agente = arrCodigoAgente[$("#agente").select2("val")];					
					descripcion = $("#descripcion").val();
					
					if (codigo == ""){
						tipoConsulta = "crear";
					}else{
						tipoConsulta = "actualizar";
					}					
					codigoEnsurance = arrCodigoPtoVentaEnsurance[$("#codigoEnsurance").select2("val")];
					enviarDatos(codigoEnsurance, codigo, nombre, agente, activo, descripcion, sucursal, tipoConsulta);
				});
			/* Fin Controles Grabar Resgistro*/
			
			function enviarDatos(codigoEnsurance, codigo, nombre, agente, activo, descripcion, sucursal, tipoConsulta){
				$.ajax({
					url : '../PuntoVentaController',
					data : {
						"codigoEnsurance" : codigoEnsurance,
						"codigo" : codigo,
						"nombre" : nombre,
						"agente" : agente,
						"activo" : activo,
						"descripcion" : descripcion,
						"sucursal" : sucursal,
						"tipoConsulta" : tipoConsulta
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						$("#msgPopup").show();
					}
				});
			}
		});
			</script>
			<script type="text/javascript">			
			function validarNombre(){
				nombre = $("#nombre").val();						
	        	$.ajax({
					url : '../PuntoVentaController',
					data : {
						"nombre" : nombre,
						"tipoConsulta" : "verificacionNombrePuntoVenta"								
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						var auxPuntoVenta = data.PuntoVenta;
						if (auxPuntoVenta.bandera == "verdad"){
						alert("Ya existe un Punto de Venta con ese Nombre");
						$("#nombre").val(" ");
						$("#nombre").focus();
						}
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
								<span aria-hidden="true">&times;</span><span class="sr-only">Cerrar</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Nuevo Punto de Venta</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">El punto de venta se grabo con exito.</div>
							<div class="form-group">
								<input type="hidden"class="form-control" id="codigo">
								<label>Sucursal</label> 
								<input style="width:100%" type="select2" class="required_select2" id="sucursal"></br>
								<label>Nombre del Punto de venta</label> 
								<input type="text"class="form-control required" id="nombre" onblur="validarNombre()">
								<label>Descripción del Punto de venta</label> 
								<input type="text"class="form-control required" id="descripcion">	
								<label>Nombre del Agente</label>
								<input style="width:100%" type="select2" class="required_select2" id="agente"></br>
								<div class="checkbox">
									<label> <input type="checkbox" value="activo"id="activo">Activo</label>
								</div>
								<label>Pto Venta Ensurance</label>
								<input style="width:100%" type="select2" class="required_select2" id="codigoEnsurance"></br> 
<!-- 								<input type="text"class="form-control required" id="codigoEnsurance">								 -->
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
							<th>Sucursal</th>
							<th>Nombre</th>
							<th>Descripcion</th>
							<th>Agente</th>
							<th>Activo</th>
							<th>Pto Venta Ensurance</th>
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