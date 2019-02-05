<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"><link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	
	<!-- KENDO -->
	<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
	<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
    <script src="../static/js/Kendo/kendo.all.min.js"></script>
    <script src="../static/js/Kendo/kendo.web.min.js"></script>
	
<title>Configuraciones Canal - Cotizador QBE</title>

<script>
	
	 $(function(){
	  	  $(".wrapper1").scroll(function(){
	  	    $(".wrapper2").scrollLeft($(".wrapper1").scrollLeft());
	  	  });
	  	  $(".wrapper2").scroll(function(){
	  	    $(".wrapper1").scrollLeft($(".wrapper2").scrollLeft());
	  	  });
	  	});
	 
	 		 		 
	 	
	 	var tipoConsulta = "";	
	 	var parametroPPVId = "";
	 	var emisionDirecta = "";
	 	var contenedorEnsuranceId = "";
	 	var plantillaEnsuranceId = "";
	 	var modificarTasa = "";
	 	var puntoVentaId = "";
	 	var visualizarTasas = "";
	 	
		$(document).ready(function(){
			activarMenu("ParametrosPymesPuntoVenta");
			
			$("#contenedorEnsuranceId").kendoNumericTextBox({
				format: "#",
		        decimals: 0
			});
			
			$("#plantillaEnsuranceId").kendoNumericTextBox({
				format: "#",
		        decimals: 0
			});
			
			$.ajax({
				url : '../PuntoVentaController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function (data) {
					var listadoPuntoVenta = data.listadoPuntoVenta;
					$.each(listadoPuntoVenta, function (index){
						$("#configuracionCanal").append("<tr class='odd gradeX'>"+
								"<td relation='puntoVentaNombre' style='width: 20%; text-align: left;' class='sorting' >" + listadoPuntoVenta[index].nombre + "</td>"+								
								"<td style='width: 20%; text-align: center;' class='sorting' >"+									
									"<input type='hidden' id = 'puntoVentaId' value='" + listadoPuntoVenta[index].codigo + "'/>"+	
									"<button type='button' class='btn btn-primary btn-xs actualizar-btn' onclick='cargarConfiguracion("+listadoPuntoVenta[index].codigo+");' id='addButton'>"+
							        	"<span hidden='hidden' class='glyphicon glyphicon-plus' ></span> &nbsp; Configurar"+
								    "</button>"+									
								"</td>" +
							"</tr>");		
					});						
					
					$("#loading").remove();		
					
				}
			});	
			
			/*Inicio controloes grabar*/						
			
			$("#addParametroPXPV").bind({click: function(){
				parametroPPVId = $("#parametroPPVId").val();
				
				if($("#emisionDirecta").is(":checked")){
					emisionDirecta = true;
				}else{
					emisionDirecta = false;
				}	
				
				if($("#modificarTasa").is(":checked")){
					modificarTasa = true;
				}else{
					modificarTasa = false;
				}
				
				if($("#visualizarTasas").is(":checked")){
					visualizarTasas = true;
				}else{
					visualizarTasas = false;
				}
				
				
				$(".required").css("border", "1px solid #ccc");
				$(".required").each(function(index) {
					var cadena = $(this).val();
					if (cadena .length <= 0) {
						$(this).css("border", "1px solid red");
						alert("Por favor ingrese el campo requerido");
						$(this).focus();
						return false;
					}		
				});	
				
				puntoVentaId = $("#puntoVentaId").val();
				contenedorEnsuranceId = $("#contenedorEnsuranceId").val();
				plantillaEnsuranceId= $("#plantillaEnsuranceId").val();
				
				if(parametroPPVId=="" || parametroPPVId=="0"){
					parametroPPVId=="";
					tipoConsulta = "crear";
				}
				else{
					tipoConsulta = "actualizar";
				}					
				
				enviarParametroXPV(parametroPPVId,contenedorEnsuranceId,emisionDirecta,plantillaEnsuranceId,modificarTasa,puntoVentaId,visualizarTasas,tipoConsulta);
				
				cargarConfiguracion(puntoVentaId);
				}			
				
			});			
			/*Fin controloes grabar*/
		});	
		
				
		
		function enviarParametroXPV(parametroPPVId,contenedorEnsuranceId,emisionDirecta,plantillaEnsuranceId,modificarTasa,puntoVentaId,visualizarTasas,tipoConsulta){
			$.ajax({
				url : '../PymeParametroXPVController',
				async: false,
				data : {
					"tipoConsulta" : tipoConsulta,
					"parametroPPVId" : parametroPPVId,
					"contenedorEnsuranceId" : contenedorEnsuranceId,
					"emisionDirecta" : emisionDirecta,
					"plantillaEnsuranceId" : plantillaEnsuranceId,
					"modificarTasa" : modificarTasa,
					"visualizarTasas" : visualizarTasas,
					"puntoVentaId" : puntoVentaId
					},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#msgPopup").show();
				}
			});
		}
		
			
			function cargarConfiguracion(puntoVenta){	
				$('#panel_1').hide();
				$('#panel_2').show();	
						$.ajax({
							url : '../PymeParametroXPVController',
							data : {
								"tipoConsulta" : "buscarPorId",
								"puntoVentaId" : puntoVenta				
							},
							type : 'POST',
							datatype : 'json',
							success : function (data) {
								/*$(dataTableContent_2).append("<input type='hidden' id = 'puntoVenta' value='" + puntoVenta + "'/>");*/						
								
									$(tbl_ParametroPPV).append("<input type='hidden' id = 'puntoVentaId' value='" + puntoVenta + "'/>");								
									$("#parametroPPVId").val(data.parametroPPVId);
									$("#puntoVentaId").val(puntoVenta);
									if(data.emisionDirecta){
										$("#emisionDirecta").attr('checked', true);
									}else{
										$("#emisionDirecta").attr('checked', false);
									}
																		
									if(data.modificarTasa){
										$("#modificarTasa").attr('checked', true);
									}else{
										$("#modificarTasa").attr('checked', false);
									}
									if(data.visualizarTasas){
										$("#visualizarTasas").attr('checked', true);
									}else{
										$("#visualizarTasas").attr('checked', false);
									}
									
								    ($("#contenedorEnsuranceId").data("kendoNumericTextBox")).value(data.contenedorEnsuranceId);
								    
								    ($("#plantillaEnsuranceId").data("kendoNumericTextBox")).value(data.plantillaEnsuranceId);
								    
									
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

<div class="content">
		<form id="wizard" action="" method="post">			
			<section id = 'panel_1' style='display:block;'>
				<h2>Punto de venta. </h2>		
				<fieldset class="border">
					<legend class="border">Datos sobre el punto de venta.</legend>	
					<div class="panel panel-primary">
						<div class="panel-body">
						<div class="input-group"> <span class="input-group-addon">Filtro</span>
						    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
						</div>
						 <table class="table table-striped table-bordered table-hover"
							id="dataTable">
								<thead>
									<tr>							
										<th>Canal</th>							
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
				</fieldset>
			</section>
			
			<section id = 'panel_2' style='display:none;'>
				<h2> Configuración Canal. </h2>				
				<fieldset class="border">
					<legend class="border">Datos de la Configuración Canal.</legend>									
						<div class="panel-body">	
						<div class="alert alert-success" id="msgPopup">La configuración se grabo con exito.</div>
							<div class="form-group">
								<div class="panel panel-primary">
						<div class="panel-heading">Parametros PYMES por punto de venta.</div>
						<div class="panel-body">
								<table class="table table-striped table-bordered table-hover" id="tbl_ParametroPPV">
								
									<tr>									
									<td style='width: 25%'>
										<input type='hidden' class='form-control' id='parametroPPVId' value=''/>
										<label>Emisión Directa: </label>
										<input type='checkbox'  id='emisionDirecta' />										
									</td>
									</tr>
									<tr>
									<td style='width: 25%'>
										<label>Visualizar Tasa: </label>
										<input type='checkbox'  id='visualizarTasas' />
									</td>
									<td style='width: 25%'>										
										<label>Modificar Tasa: </label>
										<input type='checkbox'  id='modificarTasa' />										
									</td>
									</tr>
									<tr><td style='width: 25%'>
									<label>Contenedor Ensurance: </label>									
										<input type="text" id="contenedorEnsuranceId"  required="required">
									</td>
									<td style="width: 25%">
											<label>Plantilla Ensurance: </label>											
											<input type="text" class="required" id="plantillaEnsuranceId"  required="required">
									</td>
								</tr>							
								</table>	
								</div>
								</div>
							</div>	
						<div>
							<button type="button" class="btn btn-primary" id="addParametroPXPV">Guardar
												Cambios</button>
							<button type="button" class="btn btn-default" id="close-popup"
								data-dismiss="modal">Cerrar</button>													
						</div>					
						</div>

				</fieldset>
			</section>			
		</form>
	</div>
</body>
</html>