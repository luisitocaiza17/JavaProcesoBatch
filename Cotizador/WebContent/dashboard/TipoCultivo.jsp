<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">

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
    
<title>Tipo de Cultivo</title>
<script >
$(function(){
	  $(".wrapper1").scroll(function(){
	    $(".wrapper2").scrollLeft($(".wrapper1").scrollLeft());
	  });
	  $(".wrapper2").scroll(function(){
	    $(".wrapper1").scrollLeft($(".wrapper2").scrollLeft());
	  });
	});	
	
var TipoCultivoId = "";
var Nombre = "";
var Tipo = "";
var VigenciaDias="";

$(document).ready(function(){
	activarMenu("TipoCultivo");	
	$("#VigenciaDias").kendoNumericTextBox({
		format : '#',
		 decimals: 0
    });
	Cargar();
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
	    	  	TipoCultivoId = $("#TipoCultivoId").val();
	    	  	Tipo = $("#Tipo").val();
				Nombre = $("#Nombre").val();
				VigenciaDias = $("#VigenciaDias").val();
				if(TipoCultivoId == "")
					tipoConsulta = "crear";
				else
					tipoConsulta = "editar";
				
				enviarDatos(TipoCultivoId,Nombre,Tipo,VigenciaDias,tipoConsulta);
	      }
		}
	});
	});
	
function actualizar(TipoCultivo){
	$.ajax({
		url:'../AgriTipoCultivoController',
		data:{
			"TipoCultivoId" : TipoCultivo,
			"tipoConsulta" : "obtenerPorId"
		},
		type : 'POST',
		datatype : 'json',
		success : function(data){
			$("#TipoCultivoId").val(data.TipoCultivoId);
			$("#Nombre").val(data.Nombre);
			$("#Tipo").val(data.Tipo);
			($("#VigenciaDias").data("kendoNumericTextBox")).value(data.VigenciaDias);
		}
	});
}

function eliminar(TipoCultivo){
	var r = confirm("Seguro que desea eliminar");
	if(r == true){			
		TipoCultivoId = TipoCultivo;
		tipoConsulta="eliminar";			
		enviarDatos(TipoCultivoId,"","","",tipoConsulta);
	}
	Cargar();
	$("#msgPopup").hide();
}
	
function enviarDatos(TipoCultivoId,Nombre,Tipo,VigenciaDias,tipoConsulta){
	$.ajax({
		url:'../AgriTipoCultivoController',
		data : {
			"TipoCultivoId" : TipoCultivoId,
			"Nombre" : Nombre,
			"Tipo" : Tipo,
			"VigenciaDias":VigenciaDias,
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
	
function Cargar(){
	$('#configuracionTipoCultivo').children().remove();
	$.ajax({
		url:'../AgriTipoCultivoController',
		data:{'tipoConsulta':'encontrarTodos'},
		type:'post',
		datatype:'json',
		async: false,
		success: function(data){
			var TipoCultivoJSONArray = data.TipoCultivoJSONArray;
			$.each(TipoCultivoJSONArray, function(index){
				$('#configuracionTipoCultivo').append("<tr class='odd gradeX'>"+
						"<td relation='Nombre'>"+TipoCultivoJSONArray[index].Nombre+"</td>"+
						"<td relation='Tipo'>"+TipoCultivoJSONArray[index].Tipo+"</td>"+
						"<td relation='Tipo'>"+TipoCultivoJSONArray[index].VigenciaDias+"</td>"+
						"<td width='175px'>"+									
						"<input type='hidden' id='TipoCultivoId' value='" + TipoCultivoJSONArray[index].TipoCultivoId + "'/>"+	
						" <button type='button'  name='actualizar-btn' data-toggle='modal' data-target='#add' class='btn btn-success btn-xs actualizar-btn' onclick='actualizar("+TipoCultivoJSONArray[index].TipoCultivoId+");'>" +
							" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
						" </button>" +									
						" <button type='button' class='btn btn-danger btn-xs eliminar-btn' onclick='eliminar("+TipoCultivoJSONArray[index].TipoCultivoId+");'>" +
						  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record' ></span> Eliminar" +
						" </button>" +									
					"</td>" +
				"</tr>");
			});
			$("#loading").remove();	
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
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Tipo de Cultivo Agrícola</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">El tipo de cultivo se grabo con exito.</div>
							<div class="status"> </div>	
							<div class="form-group">
								<input type="hidden" class="form-control" id="TipoCultivoId">										
								<label>Nombre</label>
								<input type="text" class="form-control required" id="Nombre" name="Nombre" validationMessage="Campo requerido!!!" required>
								<br />	
								<label>Tipo</label>
								<select type="select" class="form-control required" id="Tipo" name="Tipo"  required="required">	
								<option value=''>Selecione una opción</option>	
								<option value='1'>PERENNE</option>	
								<option value='2'>NO PERENNE</option>						
								</select>
								<br />	
								<br />	
								<table>
								<tr>
								<td width="100px">
								<label>Vigencia (días)</label>
								</td>
								<td>
								<input type="text" name="VigenciaDias" id="VigenciaDias"
								class="datosTipo" validationMessage="Campo requerido!!!" required></input>
								</td>
								</tr>
								</table>
								
								
								<br />	
								<br />																						
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
										<th>Nombre</th>	
										<th>Tipo</th>
										<th>Vigencia (días)</th>																
										<th> </th>
									</tr>	
								</thead>
								<tbody id="configuracionTipoCultivo" class="searchable">
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


