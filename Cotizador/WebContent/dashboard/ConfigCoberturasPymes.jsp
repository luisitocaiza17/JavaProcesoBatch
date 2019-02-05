<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/jquery.min.js"></script>
	
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
	
<title>Matriz de Coberturas PYMEs - Cotizador QBE</title>

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
	 	var listaValorMinimo = "";
		var listaValorMaximo = "";
		var listaTasa = "";
		var configuracionCoberturaId = "";
		var coberturaPymesId = "";
		var grupoPorProductoId = "";
		var tipoDeclaracion = "";
		var origenValorLimiteAsegurado = "";
		var porcentajeLimiteAsegurado = "";
		var valorMaximoLimiteAsegurado = "";
		var origenValorLimiteCobertura = "";
		var porcentajeLimiteCobertura = "";
		var valorMaximoLimiteCobertura = "";
		var textoDeducible = "";
		var tipoTasa = "";
		var tasa = "";
		var coberturaCopiaId = "";
		var incluyeEnProducto = "";
		var ordenPresentacion = "";
		var dependeValor = "";
		var arrCoberturaPymes = new Array();
	    
		function cargarCoberturas() {
			// Consultar listado de grupos productos
			$.ajax({
				url : '../PymeCoberturaController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},	
				async: false,
				type : 'post',
				datatype : 'json',
				success : function (data) {
					var listadoCoberturas = data.listadoCoberturaPymes;	 
					$("#coberturas").append("<option value=''>Seleccione una opcion</option>");
					$.each(listadoCoberturas, function (index) {
						$("#coberturas").append("<option value='" + listadoCoberturas[index].coberturaPymesId + "'>" + listadoCoberturas[index].nombre + "</option>");
					});		
					$("#cobertura_copiada").append("<option value=''>Seleccione una opcion</option>");
					$.each(listadoCoberturas, function (index) {
						$("#cobertura_copiada").append("<option value='" + listadoCoberturas[index].coberturaPymesId + "'>" + listadoCoberturas[index].nombre + "</option>");
					});
					
				}
			});
		}
		
		
		function obtenerProductosPorGrupoProducto() {
			// Consultar listado de productos dentro de un grupos de productos
			$("#productos").empty();

			$.ajax({
					url : '../GrupoPorProductoController',

					data : {
						"tipoConsulta" : "encontrarTodosPorGrupo",
						"tipoObjeto" : "PYMES",
						"grupoProductoId" : "82"
					},
					async : false,
					type : 'post',
					datatype : 'json',
					success : function(data) {
						var listadoGrupos = data.listadoGruposPorProducto;
						$("#productos").append(
								"<option value=''>Seleccione una opcion</option>");
						$.each(
							listadoGrupos,
							function(index) {
								var inspeccionRequerida = listadoGrupos[index].inspeccionRequerida;
								$("#productos")
										.append(
												"<option value='"
														+ listadoGrupos[index].id
														+ "' inspeccionRequerida='"
														+ inspeccionRequerida
														+ "' >"
														+ listadoGrupos[index].nombre
														+ "</option>");
							});
					}
				});
		}
		
		
		
		$(document).ready(function(){	
			activarMenu("ConfigCoberturasPymes");
			cargar();
			
			$("#orden_presentacion").kendoNumericTextBox({		    
				format: "#"
            });
			
			$("#porcentaje_limite_asegurado").kendoNumericTextBox({
                format: "#",
		        decimals: 0,
		        min: 0,
		        max: 100
            });			
			
			 $("#valor_maximo_limite_asegurado").kendoNumericTextBox({
                 format: "c",
                 min: 0,
                 decimals: 2
             });
			 
			 $("#tasa").kendoNumericTextBox({				 
				 format : '#.000000',
				 decimals: 6
             });
			 
			 $("#valor_cobertura_inicial").kendoNumericTextBox({
                 format: "c",
                 min: 0,
                 decimals: 2
             });
			 
			 $("#valor_cobertura_final").kendoNumericTextBox({
                 format: "c",
                 min: 0,
                 decimals: 2
             });
			 
			 $("#valor_cobertura_tasa").kendoNumericTextBox({		 
	             format : '#.000000',
	             decimals: 6
             });	 
			 
			$("#tabstrip").kendoTabStrip({
		        animation:  {
		            open: {
		                effects: "fadeIn"
		            }
		        }
		    });
			
			$("#tasas_compuesta tr").remove();
			
			obtenerProductosPorGrupoProducto();
			
			cargarCoberturas();
			
			$("#loading").remove();	
			
			$('#tipo_tasa_simple').click(function() {
				$("#fila_tasa_simple").hide();
				$("#fila_tasa_compuesta").hide();
				$("#fila_tasa_copiada").hide();
				//limpiar los cuadros de texto
				$("#tasas_compuesta tr").remove();
				$("#cobertura_copiada").val("");
				
				if ($(this).is(':checked')) {
		        	$("#fila_tasa_simple").show();
		        }
		    });
			
			$('#tipo_tasa_compuesta').click(function() {
				$("#fila_tasa_simple").hide();
				$("#fila_tasa_compuesta").hide();
				$("#fila_tasa_copiada").hide();
				//limpiar los cuadros de texto
				$("#tasa").val("");
				$("#cobertura_copiada").val("");
				
		        if ($(this).is(':checked')) {
		        	$("#fila_tasa_compuesta").show();
		        }
		    });
			
			$('#tipo_tasa_sin_valor').click(function() {
				$("#fila_tasa_simple").hide();
				$("#fila_tasa_compuesta").hide();
				$("#fila_tasa_copiada").hide();
				//limpiar los cuadros de texto
				$("#tasa").val("");
				$("#tasas_compuesta tr").remove();
				$("#cobertura_copiada").val("");
		    });
			
			$('#tipo_tasa_copiada').click(function() {
				$("#fila_tasa_simple").hide();
				$("#fila_tasa_compuesta").hide();
				$("#fila_tasa_copiada").hide();
				//limpiar los cuadros de texto
				$("#tasa").val("");
				$("#tasas_compuesta tr").remove();
		        if ($(this).is(':checked')) {
		        	$("#fila_tasa_copiada").show();
		        }
		    });			
			 
			
			$("#save-record").bind({click: function(){				
				//bandera para verificar que un radioButton este seleccionado
				var bandera = 0;
				
				if($("#tipo_tasa_simple").is(":checked")){					
					$("#tasa").addClass("required");
					$("#tasa").attr("required", true);
					bandera = 1;
				}else if($('#tipo_tasa_compuesta').is(':checked')){
					$("#tasa").removeClass("required");
					$("#tasa").attr("required", false);
					bandera = 1;
				}else if($('#tipo_tasa_sin_valor').is(':checked')){
					$("#tasa").removeClass("required");
					$("#tasa").attr("required", false);
					bandera = 1;
				}else if($('#tipo_tasa_copiada').is(':checked')) {
					$("#tasa").removeClass("required");
					$("#tasa").attr("required", false);
					bandera = 1;
			    }
				
				var validator = $("#formCrud").kendoValidator().data("kendoValidator");
				$(".required").css("border", "1px solid #ccc");
				 if (validator.validate() === false) {
					 alert("Por favor ingrese el campo requerido");
				}else if(bandera === 0){
			    	  alert("Por favor ingrese la tasa");
			    }else{					
						listaValorMinimo = "";
						listaValorMaximo = "";
						listaTasa = "";
						
						$("#tasas_compuesta tbody tr").each(function(index) {
							$(this).children("td").each(
								function(index2) {
									switch (index2) {
									case 0:
										listaValorMinimo = listaValorMinimo + "," + $(this).children().val();
										break;
									case 1:
										if ($(this).children().val())
											listaValorMaximo = listaValorMaximo + "," + $(this).children().val();
										else
											listaValorMaximo = listaValorMaximo + "," + " ";
										break;
									case 2:
										if ($(this).children().val())
											listaTasa = listaTasa + "," + $(this).children().val();
										else
											listaTasa = listaTasa + "," + " ";
										break;
									}
								});
						});
						
						configuracionCoberturaId = $("#configuracionCoberturaId").val();
						
						if(configuracionCoberturaId=="" || configuracionCoberturaId== 0)
							tipoConsulta = "crear";
						else
							tipoConsulta = "editar";
						
						coberturaPymesId = $("#coberturas").val();
						grupoPorProductoId = $("#productos").val();
						tipoDeclaracion = $("#tipo_declaracion").val();
						origenValorLimiteAsegurado = $("#origen_valor_limite_asegurado").val();
						porcentajeLimiteAsegurado = $("#porcentaje_limite_asegurado").val();
						valorMaximoLimiteAsegurado = $("#valor_maximo_limite_asegurado").val();					
						origenValorLimiteCobertura = "9";
						porcentajeLimiteCobertura = "0";
						valorMaximoLimiteCobertura = "0";
						textoDeducible = $("#texto_deducible").val();
						if ($("#tipo_tasa_simple").is(":checked"))
							tipoTasa = 1;
						else if ($("#tipo_tasa_compuesta").is(":checked"))
							tipoTasa = 2;
						else if ($("#tipo_tasa_sin_valor").is(":checked"))
							tipoTasa = 3;
						else
							tipoTasa = 4;
						tasa = $("#tasa").val();
						coberturaCopiaId = $("#cobertura_copiada").val();
						if($("#incluye_en_producto").is(":checked")){
							incluyeEnProducto = 1;
						}else{
							incluyeEnProducto = 0;
						}						
						
						dependeValor = $("#depende_valor").val();
						ordenPresentacion = $("#orden_presentacion").val();
												
						enviarDatos(tipoConsulta, configuracionCoberturaId, coberturaPymesId, grupoPorProductoId, tipoDeclaracion, origenValorLimiteAsegurado,
									porcentajeLimiteAsegurado, valorMaximoLimiteAsegurado, origenValorLimiteCobertura, porcentajeLimiteCobertura, valorMaximoLimiteCobertura,
									textoDeducible, tipoTasa, tasa, coberturaCopiaId, incluyeEnProducto, dependeValor, ordenPresentacion, listaValorMinimo, listaValorMaximo, listaTasa);
					}
				}
				
			});	
		});
		
		function cargar(){
			$("#configuracionCanal").children().remove();
			$.ajax({
				url : '../PymeConfiguracionCoberturaController',
				data : {
					"tipoConsulta" : "encontrarTodosVista"
				},
				type : 'POST',
				datatype : 'json',
				success : function (data) {
					var listadoConfiguraciones = data.listadoConfiguraciones;
					$.each(listadoConfiguraciones, function (index){
						$("#configuracionCanal").append("<tr class='odd gradeX'>"+								
								"<td relation='coberturaNombre' >" + listadoConfiguraciones[index].coberturaNombre + "</td>"+								
								"<td relation='grupoNombre' >" + listadoConfiguraciones[index].grupoNombre + "</td>"+
								"<td relation='tipoDeclaracionNombre' >" + listadoConfiguraciones[index].tipoDeclaracionNombre + "</td>"+
								"<td width='175px'>"+									
									" <button type='button'  name='actualizar-btn' data-toggle='modal' data-target='#add' class='btn btn-success btn-xs actualizar-btn' onclick='actualizar("+ listadoConfiguraciones[index].configuracionCoberturaId +")'>" +
  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
									" </button>" +									
									" <button type='button' class='btn btn-danger btn-xs eliminar-btn' onclick='eliminar("+ listadoConfiguraciones[index].configuracionCoberturaId +")'>" +
									  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
									" </button>" +									
								"</td>" +
							"</tr>");		
					});				
					$("#loading").remove();	
					var grupoCoberturaArr = data.grupoCoberturaArr;
					$.each(grupoCoberturaArr, function (index){
						$("#configuracionCoberturaId").append("<option value='"+ grupoCoberturaArr[index].gCoberturaId +"'>"+ grupoCoberturaArr[index].gCoberturaNombre +"</option>");
					});
					var tipoCoberturaArr = data.tipoCoberturaArr;
					$.each(tipoCoberturaArr, function (index){
						$("#configuracionCoberturaId").append("<option value='"+ tipoCoberturaArr[index].tCoberturaId +"'>"+ tipoCoberturaArr[index].tCoberturaNombre +"</option>");
					});	
				}
			});
		}
		
		function actualizar(configId){
			$.ajax({
				url : '../PymeConfiguracionCoberturaController',
				async : false,
				data : {
					"configuracionCoberturaId" : configId,
				 	"tipoConsulta" : "encontrarPorId"
				},
				async : false,
				type : 'POST',
				datatype : 'json',
				success : function(data){
					$("#configuracionCoberturaId").val(data.configuracionCoberturaId);
					$("#coberturas").val(data.coberturaPymesId);
					$("#productos").val(data.grupoPorProductoId);
					$("#tipo_declaracion").val(data.tipoDeclaracion);
					$("#origen_valor_limite_asegurado").val(data.origenValorLimiteAsegurado);
					($("#porcentaje_limite_asegurado").data("kendoNumericTextBox")).value(data.porcentajeLimiteAsegurado);					
					($("#valor_maximo_limite_asegurado").data("kendoNumericTextBox")).value(data.valorMaximoLimiteAsegurado);					
					$("#texto_deducible").val(data.textoDeducible);
					
					$("#fila_tasa_simple").hide();
					$("#fila_tasa_compuesta").hide();
					$("#fila_tasa_copiada").hide();
					
					if(data.tipoTasa == "1"){
						$("#tipo_tasa_simple").prop("checked", true);
						$("#fila_tasa_simple").show();
					}
					else
						$("#tipo_tasa_simple").prop("checked", false);

					if(data.tipoTasa == "2"){
						$("#tipo_tasa_compuesta").prop("checked", true);
						$("#fila_tasa_compuesta").show();
					}
					else
						$("#tipo_tasa_compuesta").prop("checked", false);
					
					if(data.tipoTasa == "3")
						$("#tipo_tasa_sin_valor").prop("checked", true);
					else
						$("#tipo_tasa_sin_valor").prop("checked", false);
					
					if(data.tipoTasa == "4"){
						$("#tipo_tasa_copiada").prop("checked", true);
						$("#fila_tasa_copiada").show();
					}
					else
						$("#tipo_tasa_copiada").prop("checked", false);
					
					($("#tasa").data("kendoNumericTextBox")).value(data.tasa);					
					$("#cobertura_copiada").val(data.coberturaCopiaId);
					if(data.incluyeEnProducto == "1"){
						$("#incluye_en_producto").prop("checked", true);
					}else{
						$("#incluye_en_producto").prop("checked", false);
					}
					
					($("#orden_presentacion").data("kendoNumericTextBox")).value(data.ordenPresentacion);
					$("#depende_valor").val(data.dependeValor);				
										
					var tasas = data.tasas;
					
					$.each(data.tasas,function(index) {
						$("#tasas_compuesta").append(
										'<tr>'+
										'<td>'+
										'<input style="width: 100px" type="text"'+
										' id="valor_cobertura_inicial_'+tasas[index].coberturaTasaId+
										'" value='+tasas[index].valorCoberturaInicial+
										'></td>'+
										'<td>'+
										'<input style="width: 100px" type="text"'+
										'id="valor_cobertura_final_'+tasas[index].coberturaTasaId+
										'" value='+tasas[index].valorCoberturaFinal+
										'></td>'+
										'<td>'+
										'<input style="width: 100px" type="text"'+
										'id="valor_cobertura_tasa_'+tasas[index].coberturaTasaId+
										'" value='+Math.floor(tasas[index].tasa* 100) / 100+
										'></td>'+
										'<td>'+
										'<button type="button" class="btn btn-danger btn-xs eliminar-extra-btn">'+
										' <span class="glyphicon glyphicon glyphicon-remove"></span> Eliminar'+
										' </button></td>'+
										'</tr>');
					});
					
					$(".eliminar-extra-btn").bind(
							{
								click : function() {
									$(this).parent().parent().remove();
									numeroTasa = parseInt($("#contadorTasas").val()) - parseInt(1);
									$("#contadorTasas").val(numeroTasa);
							}
					});
				}
			});
			
		}
		
		function eliminar(configId){			
			var r = confirm("Seguro que desea eliminar");
			if(r == true){
				configuracionCoberturaId = configId;
			 	tipoConsulta = "eliminar";
			 	enviarDatos(tipoConsulta, configId, "", "", "", "",
						"", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "");		 	
			}
			cargar();
		}
		
		function agregarNuevoTasa() {
			$("#valor_cobertura_inicial").css("border", "1px solid #ccc");
			$("#valor_cobertura_final").css("border", "1px solid #ccc");
			$("#valor_cobertura_tasa").css("border", "1px solid #ccc");			
			if($("#valor_cobertura_inicial").val() == "" || $("#valor_cobertura_final").val() == "" || $("#valor_cobertura_tasa").val() == "" ){
				$("#valor_cobertura_inicial").css("border", "1px solid red");
				$("#valor_cobertura_final").css("border", "1px solid red");
				$("#valor_cobertura_tasa").css("border", "1px solid red");
				alert("Por favor ingrese el campo requerido");
				$("#valor_cobertura_inicial").focus();
				return false;
			}
			
			var numeroTasa = parseInt($("#contadorTasas").val()) + parseInt(1);

			$("#tasas_compuesta")
					.append(
							'<tr>'+
							'<td>'+
							'<input style="width: 100px" type="text"'+
							' id="valor_cobertura_inicial_'+numeroTasa+
							'" value='+$("#valor_cobertura_inicial").val()+
							'></td>'+
							'<td>'+
							'<input style="width: 100px" type="text"'+
							'id="valor_cobertura_final_'+numeroTasa+
							'" value='+$("#valor_cobertura_final").val()+
							'></td>'+
							'<td>'+
							'<input style="width: 100px" type="text"'+
							'id="valor_cobertura_tasa_'+numeroTasa+
							'" value='+$("#valor_cobertura_tasa").val()+
							'></td>'+
							'<td>'+
							'<button type="button" class="btn btn-danger btn-xs eliminar-extra-btn">'+
							' <span class="glyphicon glyphicon glyphicon-remove"></span> Eliminar'+
							' </button></td>'+
							'</tr>');
			$("#contadorTasas").val(numeroTasa);
			($("#valor_cobertura_inicial").data("kendoNumericTextBox")).value("");
			($("#valor_cobertura_final").data("kendoNumericTextBox")).value("");
			($("#valor_cobertura_tasa").data("kendoNumericTextBox")).value("");
			
			$(".eliminar-extra-btn").bind(
				{
					click : function() {
						$(this).parent().parent().remove();
						numeroTasa = parseInt($("#contadorTasas").val()) - parseInt(1);
						$("#contadorTasas").val(numeroTasa);
				}
			});
		}
		
		function enviarDatos(tipoConsulta, configuracionCoberturaId, coberturaPymesId, grupoPorProductoId, tipoDeclaracion, origenValorLimiteAsegurado,
				porcentajeLimiteAsegurado, valorMaximoLimiteAsegurado, origenValorLimiteCobertura, porcentajeLimiteCobertura, valorMaximoLimiteCobertura,
				textoDeducible, tipoTasa, tasa, coberturaCopiaId, incluyeEnProducto, dependeValor, ordenPresentacion, listaValorMinimo, listaValorMaximo, listaTasa){

			$.ajax({
				url : '../PymeConfiguracionCoberturaController',
				async : false,
				data : {
					"tipoConsulta" : tipoConsulta,
					"configuracionCoberturaId" : configuracionCoberturaId,
					"coberturaPymesId" : coberturaPymesId,
					"grupoPorProductoId" : grupoPorProductoId,
					"tipoDeclaracion" : tipoDeclaracion,
					"origenValorLimiteAsegurado" : origenValorLimiteAsegurado,
					"porcentajeLimiteAsegurado" : porcentajeLimiteAsegurado,
					"valorMaximoLimiteAsegurado" : valorMaximoLimiteAsegurado,
					"origenValorLimiteCobertura" : origenValorLimiteCobertura,
					"porcentajeLimiteCobertura" : porcentajeLimiteCobertura,
					"valorMaximoLimiteCobertura" : valorMaximoLimiteCobertura,
					"textoDeducible" : textoDeducible,
					"tipoTasa" : tipoTasa,
					"tasa" : tasa,
					"coberturaCopiaId" : coberturaCopiaId,
					"incluyeEnProducto" : incluyeEnProducto,
					"dependeValor" : dependeValor, 
					"ordenPresentacion" :ordenPresentacion,
					"listaValorMinimo" : listaValorMinimo,
					"listaValorMaximo" : listaValorMaximo,
					"listaTasa" : listaTasa
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
							<h4 class="modal-title" id="myModalLabel">Matriz de Coberturas</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">La parametrización de la cobertura
								se grabo con éxito.</div>							
							<input type="hidden" class="form-control" id="configuracionCoberturaId">
							<div class="panel panel-primary">
								<div class="panel-heading">Datos de la Cobertura</div>
								<div class="panel-body">
									<table>
										<tr>
											<td>
												Producto:
											</td>
											<td>
												<select name="productos" id="productos" class="datosGanadero" validationMessage="Campo requerido!!!" required></select>
											</td>
											<td>
												Cobertura:
											</td>
											<td>
												<select style="width: 180px" name="coberturas" id="coberturas"  class="datosGanadero" validationMessage="Campo requerido!!!" required></select>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td>
												Tipo de Declaración:
											</td>
											<td>
												<select name="tipo_declaracion" id="tipo_declaracion" class="datosGanadero" validationMessage="Campo requerido!!!" required>
													<option value=''>Seleccione una opción</option>
													<option value='1'>GENERAL</option>
													<option value='2'>POR DIRECCIÓN</option>
												</select>
											</td>
											<td>
												Depende de Valor:
											</td>
											<td>
												<select name="depende_valor" id="depende_valor" class="datosGanadero" validationMessage="Campo requerido!!!" required>
													<option value=''>Seleccione una opción</option>
													<option value='1'>De Ninguno</option>
													<option value='2'>De Estructuras</option>
													<option value='3'>De Muebles y Enseres</option>
													<option value='4'>De Maquinaria</option>
													<option value='5'>De Mercadería</option>
													<option value='6'>De Equipos y Herramientas</option>
													<option value='7'>De Insumos Médicos</option>
												</select>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
										<tr>
											<td>
												Orden de Presentación:
											</td>
											<td>
											<input type="text" name="orden_presentacion" id="orden_presentacion"
															class="datosGanadero" ></input>
											</td>
											<td>
												Incluido en el Producto:
											</td>
											<td>
												<input type="checkbox" id="incluye_en_producto" class="datosGanadero" name="incluye_en_producto">
											</td>
										</tr>
									</table>
								</div>
							</div>

							<div class="form-group">
								<div id="tabstrip">
									<ul>
										<li class="k-state-active">Límite Asegurado por Cobertura</li>
										<li>Deducibles</li>
										<li>Tasas</li>
									</ul>
									<div class="tab_strip">
										<br />
										<div class="panel panel-primary">
											<div class="panel-body">
												<table style="width: 100%">
													<tr>
														<td>Origen del Valor:</td>
														<td colspan="3">
															<select name="origen_valor_limite_asegurado" id="origen_valor_limite_asegurado" required="required" validationMessage="Campo requerido!!!" class="datosGanadero">
																<option>Seleccione una opción</option>
																<option value='1'>SUMA VALORES</option>
																<option value='2'>SOLO ESTRUCTURA</option>
																<option value='3'>SOLO CONTENIDO</option>
																<option value='4'>SOLO MAQUINARIA</option>
																<option value='5'>SOLO MERCADERIA</option>
																<option value='6'>SOLO INSUMOS MÉDICOS-NO ELÉCTRICOS</option>
																<option value='7'>VALOR ASEGURADO DE INCENDIO</option>
																<option value='8'>VALOR ASEGURADO DE ROBO</option>
																<option value='9'>NO APLICA</option>
																<option value='10'>VALOR</option>
																<option value='11'>VALOR PLO(RESPONSABILIDAD CIVIL)</option>
															</select>
														</td>
														
													</tr>
													<tr>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td>Porcentaje del Origen:</td>
														<td style="width: 30%"><input type="text"
															name="porcentaje_limite_asegurado" id="porcentaje_limite_asegurado"
															class="datosGanadero" ></input></td>
														<td>Valor Máximo:</td>
														<td style="width: 30%"><input type="text"
															name="valor_maximo_limite_asegurado" id="valor_maximo_limite_asegurado"
															class="datosGanadero" ></input></td>
													</tr>
												</table>
											</div>
										</div>
									</div>
									<div class="tab_strip">
										<br />
										<div class="panel panel-primary">
											<div class="panel-body">
												<table style="width: 100%">
													<tr>
														<td>Deducibles:</td>
														<td><textarea cols="40" rows="5" style="width:400px; height:50px; vertical-align: top;" 
															name="texto_deducible"
															id="texto_deducible"
															class="datosGanadero"></textarea></td>														
													</tr>
												</table>
											</div>
										</div>
									</div>
									<div class="tab_strip">
										<br />
										<div class="panel panel-primary">
											<div class="panel-body">
											<div class="status"></div>
												<table style="width: 100%">
													<tr>
														<td>Tasa Simple&nbsp;<input type="radio"
															name="tipo_tasa"
															id="tipo_tasa_simple"
															class="datosGanadero" required="required" required></input></td>
														<td>Tasa Compuesta&nbsp;<input type="radio"
															name="tipo_tasa"
															id="tipo_tasa_compuesta"
															class="datosGanadero" required="required" required></input></td>	
														<td>Sin Costo&nbsp;<input type="radio"
															name="tipo_tasa"
															id="tipo_tasa_sin_valor"
															class="datosGanadero" required="required" required></input></td>
														<td>Tasa Copiada&nbsp;<input type="radio"
															name="tipo_tasa"
															id="tipo_tasa_copiada"
															class="datosGanadero" required="required" required></input></td>										
													</tr>
													<tr id="fila_tasa_simple" style="display: none">
														<td>Tasa:</td>
														<td colspan="7"><input type="text"
															name="tasa"
															id="tasa" validationMessage="Campo requerido!!!"></input></td>
													</tr>
													<tr id="fila_tasa_compuesta" style="display: none">
														<td colspan="8">
															<input type="hidden" value="0" id="contadorTasas"><br>
															<table width="100%">
																<tr>
																	<td style="width: 15%; text-align: center;">
										                            	Desde:
										                            </td>
										                            <td style="width: 15%; text-align: center;">
										                            	Hasta:
										                            </td>
										                            <td style="width: 20%; text-align: center;">
										                            	Tasa:
										                            </td>
										                            <td style="width: 50%; text-align: center;">
										                            </td>
																</tr>
																<tr>
																	<td>
										                            	<input style="width: 100px" type="text"
																			name="valor_cobertura_inicial"
																			id="valor_cobertura_inicial">
										                            </td>
										                            <td>
										                            	<input style="width: 100px" type="text"
																			name="valor_cobertura_final"
																			id="valor_cobertura_final">
										                            </td>
										                            <td>
										                            	<input style="width: 100px" type="text"
																			name="valor_cobertura_tasa"
																			id="valor_cobertura_tasa">
										                            </td>
										                            <td>
										                            	<button type="button" class="btn btn-primary btn-xs actualizar-btn" id="btnAnadirTasa"
										                                    onclick="agregarNuevoTasa();">
										                                    <span class="glyphicon glyphicon glyphicon-plus"></span>A&ntilde;adir Tasa
										                                </button>
										                            </td>
																</tr>
															</table>
															<table id="tasas_compuesta">
		                        
		                    								</table>
														</td>
													</tr>
													<tr id="fila_tasa_copiada" style="display: none">
														<td>Copiar de:</td>
														<td colspan="3">
															<select name="cobertura_copiada" id="cobertura_copiada">
															</select>
														</td>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
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
				<div class="panel panel-primary">
						<div class="panel-body">
						<div class="input-group"> <span class="input-group-addon">Filtro</span>
						    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
						</div>
						 <table class="table table-striped table-bordered table-hover"
							id="dataTable">
								<thead>
									<tr>
										<th>Cobertura</th>
										<th>Producto</th>
										<th>Tipo</th>
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