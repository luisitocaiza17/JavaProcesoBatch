<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<!-- <script src="../static/js/jquery.min.js"></script> -->
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	
	<!-- KENDO -->
	<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
	<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
    <script src="../static/js/Kendo/kendo.all.min.js"></script>
    <script src="../static/js/Kendo/kendo.web.min.js"></script>
    
    <style type="text/css">
    	.tab_strip
		{
		    height: 200px;
		}
    </style>
    
<title>Asistencia PYMES - Cotizador QBE</title>
<script>

	$(function(){
	  $(".wrapper1").scroll(function(){
	    $(".wrapper2").scrollLeft($(".wrapper1").scrollLeft());
	  });
	  $(".wrapper2").scroll(function(){
	    $(".wrapper1").scrollLeft($(".wrapper2").scrollLeft());
	  });
	});	

	var asistenciaId = "";
	var grupoPorProductoId = "";
	var nombre = "";
	var valor = "";
	var esPredeterminada = "";
	var tipoConsulta="";
	
	$(document).ready(function(){
		activarMenu("AsistenciasPymes");	
		cargar();
		
		 $("#valor").kendoNumericTextBox({
             format: "c",
             min: 0,
             decimals: 2
         });		
		
		$("#save-record").bind({click: function(){
			
			var validator = $("#formCrud").kendoValidator().data("kendoValidator"); 
			$(".required").css("border", "1px solid #ccc");
			 if (validator.validate() === false) {     
				$(".required").each(function(index) {
						var cadena = $(this).val();
						if (cadena.length <= 0) {
							$(this).css("border", "1px solid red");
							alert("Por favor ingrese el campo requerido");
							$(this).focus();
							return false;
						}		
					});
		      }else{
		    	  asistenciaId = $("#asistenciaId").val();
					grupoPorProductoId = $("#grupoPorProductoId").val();
					nombre = $("#nombre").val();
					valor = $("#valor").val();
					if($("#esPredeterminada").is(":checked")){
						esPredeterminada = true;
					}else{
						esPredeterminada = false;
					}
					
					if(asistenciaId == "")
						tipoConsulta = "crear";
					else
						tipoConsulta = "editar";
					
					enviarDatos(asistenciaId,grupoPorProductoId,nombre,valor,esPredeterminada,tipoConsulta);
		      }
			}
		});
	});
	
	function actualizar(asistencia){
		$.ajax({
			url:'../PymeAsistenciaController',
			data:{
				"asistenciaId" : asistencia,
				"tipoConsulta" : "obtenerPorId"
			},
			type : 'POST',
			datatype : 'json',
			success : function(data){
				$("#asistenciaId").val(data.asistenciaId);
				$("#grupoPorProductoId").val(data.grupoPorProductoId);
				$("#nombre").val(data.nombre);
				($("#valor").data("kendoNumericTextBox")).value(data.valor);
				if(data.esPredeterminada){
					$("#esPredeterminada").attr('checked', true);
				}else{
					$("#esPredeterminada").attr('checked', false);
				}
			}
		});
	}
	
	function eliminar(asistencia){
		var r = confirm("Seguro que desea eliminar");
		if(r == true){			
			asistenciaId = asistencia;
			grupoPorProductoId = "";
			nombre = "";
			valor = "";
			esPredeterminada = "";
			tipoConsulta="eliminar";			
			enviarDatos(asistenciaId,grupoPorProductoId,nombre,valor,esPredeterminada,tipoConsulta);
		}
		cargar();
	}
	
	function enviarDatos(asistenciaId,grupoPorProductoId,nombre,valor,esPredeterminada,tipoConsulta){
		$.ajax({
			url:'../PymeAsistenciaController',
			data : {
				"asistenciaId" : asistenciaId,
				"grupoPorProductoId" : grupoPorProductoId,
				"nombre" : nombre,
				"valor" : valor,
				"esPredeterminada" : esPredeterminada,
				"tipoConsulta" : tipoConsulta
			},
			type : 'POST',
			async : false,
			datatype : 'json',
			success : function(data){
				$("#msgPopup").show();
			}
		});
	}
	
	/*Inicio cargar datos en la tabla principal*/
	function cargar(){
		$("#grupoPorProductoId").children().remove();
		$('#configuracionCanal').children().remove();
		$.ajax({
			url: '../PymeAsistenciaController',
			data : {
				'tipoConsulta' : 'encontrarTodos'
			},
			type : 'post',
			datatype : 'json',
			async :false,
			success : function(data){
				var asistenciaJSONArray = data.asistenciaJSONArray;
				$.each(asistenciaJSONArray, function(index){
					$('#configuracionCanal').append("<tr class='odd gradeX'>"+
							"<td relation='grupoPorProducto'>"+asistenciaJSONArray[index].grupoPorProductoId+"</td>"+
							"<td relation='nombre'>"+asistenciaJSONArray[index].nombre+"</td>"+
							"<td relation='valor'>"+asistenciaJSONArray[index].valor+"</td>"+
							"<td relation='esPredeterminada'>"+asistenciaJSONArray[index].esPredeterminada+"</td>"+
							"<td width='175px'>"+									
							"<input type='hidden' id='coberturaPymesId' value='" + asistenciaJSONArray[index].asistenciaId + "'/>"+	
							" <button type='button'  name='actualizar-btn' data-toggle='modal' data-target='#add' class='btn btn-success btn-xs actualizar-btn' onclick='actualizar("+asistenciaJSONArray[index].asistenciaId+");'>" +
								" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
							" </button>" +									
							" <button type='button' class='btn btn-danger btn-xs eliminar-btn' onclick='eliminar("+asistenciaJSONArray[index].asistenciaId+");'>" +
							  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record' ></span> Eliminar" +
							" </button>" +									
						"</td>" +
					"</tr>");
				});
				$("#loading").remove();	
				var grupoPPJSONArray = data.grupoPPJSONArray;
				$("#grupoPorProductoId").append("<option value=''>Seleccione una opción</option>");
				$.each(grupoPPJSONArray, function(index){
					$("#grupoPorProductoId").append("<option value='"+grupoPPJSONArray[index].grupoPPId+"'>"+grupoPPJSONArray[index].grupoPPNombre+"</option>");
				});
			}
		});
	}
	/*Fin cargar datos en la tabla principal*/
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
							<h4 class="modal-title" id="myModalLabel">Asistencia PYMES</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">La Asistencia se grabo con exito.</div>
							<div class="status"> </div>	
							<div class="form-group">
								<input type="hidden"class="form-control" id="asistenciaId">
								<label>Grupo Por Producto</label>
								<select id="grupoPorProductoId" class="form-control required" validationMessage="Campo requerido!!!" required>								
								</select>								
								<label>Nombre</label>
								<input type="text" class="form-control required" id="nombre" validationMessage="Campo requerido!!!" required>
								<br/>								
								<label>Valor</label>								
								<input type="text" class="required" id="valor" validationMessage="Campo requerido!!!" required/>
								<br />	 
								<br />
								<label>Predeterminada</label> 
								<input type="checkbox" id="esPredeterminada"/>																							
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
				<div class="panel panel-primary">
						<div class="panel-body">
						<div class="input-group"> <span class="input-group-addon">Filtro</span>
						    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
						</div>
						 <table class="table table-striped table-bordered table-hover"
							id="dataTable">
								<thead>
									<tr>
										<th>Grupo Por Producto</th>
										<th>Nombre</th>	
										<th>Valor</th>	
										<th>Predeterminada</th>																
										<th> </th>
									</tr>	
								</thead>
								<tbody id="configuracionCanal" class="searchable">
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
		</div>
	</div>
	<!-- Datatable -->	
</body>
</html>