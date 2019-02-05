<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"><link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.numeric.js"></script>
	<script src="../static/js/util.js"></script>
<title>Precios Referenciales - Cotizador QBE</title>

	<script>
	$(function(){
  	  $(".wrapper1").scroll(function(){
  	    $(".wrapper2").scrollLeft($(".wrapper1").scrollLeft());
  	  });
  	  $(".wrapper2").scroll(function(){
  	    $(".wrapper1").scrollLeft($(".wrapper2").scrollLeft());
  	  });
  	});
	 
	var codigo = "";
    var tipoGanado = "";
    var tipoProduccion = "";
    var precioMinimo = "";
    var precioMaximo = "";
    var region = "";
    var arrTipoGanado = new Array();
    var arrTipoGanadoCodigo = Object();
		
	$(document).ready(function(){
			activarMenu("PrecioReferencialGanadero");
			
			$.ajax({
				url : '../TipoGanadoController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function (data) {
					$(':input[type="number"]').numeric(".");					
					var listadoTiposGanado = data.listadoTiposGanado;			
					$("#tipoGanado").append("<option value=''>Seleccione una opci�n</option>");
					$.each(listadoTiposGanado, function (index) {
						arrTipoGanado.push(listadoTiposGanado[index].nombre);
						arrTipoGanadoCodigo[listadoTiposGanado[index].nombre] = listadoTiposGanado[index].id;
						$("#tipoGanado").append("<option value='" + listadoTiposGanado[index].id + "'>" + listadoTiposGanado[index].nombre + "</option>");
					});
				}
			});
		
			
			$.ajax({
				url : '../PrecioReferencialGanaderoController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data){
					$("#loading").remove();
					if(data.numRegistros > 0){
						var listadoPrecioReferencial = data.listadoPrecioReferencial;
						$.each(listadoPrecioReferencial, function(index){
							$("#dataTableContent").append("<tr class='odd gradeX'>"+
							"<td relation='tipoGanado'>" + listadoPrecioReferencial[index].tipoGanado + "</td>"+
							"<td relation='deducible'>" + listadoPrecioReferencial[index].tipoProduccion + "</td>"+
							"<td relation='numeroSiniestro'>" + listadoPrecioReferencial[index].precioMinimo + "</td>"+
							"<td relation='rangoInicial'>" + listadoPrecioReferencial[index].precioMaximo + "</td>"+
							"<td relation='rangoFinal'>" + listadoPrecioReferencial[index].region + "</td>"+
							"<td width='175px'>"+
								"<input type='hidden' value='" + listadoPrecioReferencial[index].codigo + "'/>"+
								"<button type='button' name='actualizar-btn' class='btn btn-success btn-xs actualizar-btn'>"+
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
								$.ajax({
									url : '../PrecioReferencialGanaderoController',
									data : {
										"tipoConsulta" : "obtenerPorID",
										"codigo":$("#codigo").val()
									},
									type : 'post',
									datatype : 'json',
									success : function (data) {
										$("#tipoGanado").val(data.tipoGanado);
										$("#tipoProduccion").val(data.tipoProduccion);
										$("#precioMinimo").val(data.precioMinimo);
										$("#precioMaximo").val(data.precioMaximo);
										$("#region").val(data.region);
									}
								});
								
							  } 
						});	
						/* Fin Controles Actualizar Registro*/
						
						/* Inicio Controles Elminar Registro */
						$(".eliminar-btn").bind({click: function(){
							var r = confirm("Seguro que desea eliminar " + $(this).parent().parent().children().first().text());
							if(r == true){
								codigo = $(this).parent().children().first().val();
								tipoGanado = "";
								tipoProduccion = "";
								precioMinimo = "";
								precioMaximo = "";
								region = "";
								tipoConsulta = "eliminar";
								
								enviarDatos(codigo,tipoGanado,tipoProduccion,precioMinimo,precioMaximo,region,tipoConsulta);
								$(this).parent().parent().remove();
							}
						}
					});
						/* Fin Controles Elminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='4'>No existen Registros</tr>");
					}
				}
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
			
				codigo =  $("#codigo").val();				
				tipoGanado = $("#tipoGanado").val();
				tipoProduccion = $("#tipoProduccion").val();
				precioMinimo = $("#precioMinimo").val();
				precioMaximo = $("#precioMaximo").val();
				region = $("#region").val();
				if(codigo == ""){
					tipoConsulta = "crear";
				}else{
					tipoConsulta = "actualizar";
				}
				enviarDatos(codigo,tipoGanado,tipoProduccion,precioMinimo,precioMaximo,region,tipoConsulta);	
			});				
					
		});	
								
			
		function enviarDatos(codigo,tipoGanado,tipoProduccion,precioMinimo,precioMaximo,region,tipoConsulta){
			$.ajax({
				url : '../PrecioReferencialGanaderoController',
				data : {
					"codigo" : codigo,
					"tipoGanado" : tipoGanado,
					"tipoProduccion" : tipoProduccion,
					"precioMinimo" : precioMinimo,
					"precioMaximo" : precioMaximo,
					"region" : region,
					"tipoConsulta" : tipoConsulta						
				},					
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#msgPopup").show();
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
	    <button hidden="hidden" class="btn btn-primary" data-toggle="modal" data-target="#add" id="addButton">
	        <span hidden="hidden" class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
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
							<h4 class="modal-title" id="myModalLabel">Nuevo Precio Referencial Ganadero</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">El Precio Referencial Ganadero se grabo con exito.</div>
							<div class="form-group">
							
								<input type="hidden" class="form-control" id="codigo">
								<label>Tipo Animal</label>
								<select type="select" class="form-control required" id="tipoGanado">                            	                           	
								 </select>
								 <label>Tipo Produccion</label>
								 <select type="select" class="form-control required" id="tipoProduccion">
									<option>Seleccione una opci�n</option>
									<option>LECHE</option>
									<option>CARNE</option>                            	
								 </select>
								 <label>Precio Minimo</label>
								 <input type="number" class="form-control required" id="precioMinimo">
								 <label>Precio Maximo</label>
								 <input type="number" class="form-control required" id="precioMaximo">                            
								<label>Region</label>
								 <select type="select" class="form-control required" id="region">
									<option>Seleccione una opci�n</option>
									<option>COSTA</option>
									<option>SIERRA</option>  
									<option>ORIENTE</option>                                 
								 </select>                            	
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" id="close-popup"
								data-dismiss="modal">Cerrar</button>
							<button type="button" class="btn btn-primary" id="save-record">Guardar
								Cambios</button>
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
		<div class="wrapper1">
  			<div class="div1"></div>
			</div>
			<div class="wrapper2">
			<div class="div2">
			<div style="overflow: auto;" class="table-responsive">
				<div class="input-group"> <span class="input-group-addon">Filtro</span>
				    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
				</div>			
				<table class="table table-striped table-bordered table-hover"
					id="dataTable">
					<thead>
						<tr>							
							<th>Tipo Ganado</th>
							<th>Tipo Producci�n</th>
							<th>Precio Minimo</th>
							<th>Precio Maximo</th>
							<th>Reguion</th>
							<th> </th>
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
			</div></div>
		</div>
	</div>
	<!-- Datatable -->	

</body>
</html>