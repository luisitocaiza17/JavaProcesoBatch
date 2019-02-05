<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Producto por Punto de Venta - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<!-- Importar librerias para paginación -->
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	
	<!-- Table Tools -->
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.tableTools.js"></script>
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.tableTools.css" rel="stylesheet">
	
	<!-- Fin librerias -->
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
	var codigo = "";
	var productoId = "";
	var grupoPorProductoId = "";
	
	var grupoProductoId="";
	var contenedorId="";
	
	var puntoVentaId = "";
	var unidadNegocioId = "";
	var unidadProduccionId = "";
	var contenedor = "";
	var tipoConsulta = "";
	var contenedorDescripcion = "";

	var arrGrupoPorProducto = new Array();
	var arrCodigoGrupoPorProducto = new Object();
	var arrPuntoVenta = new Array();
	var arrCodigoPuntoVenta = new Object();
	var arrUnidadNegocio = new Array();
	var arrCodigoUnidadNegocio = new Object();
	var arrUnidadProduccion = new Array();
	var arrCodigoUnidadProduccion = new Object();
	var arrPlan = new Array();
	var arrCodigoPlan = new Object();
	
	var arrGrupoProducto = new Array();
	var arrCodigoGrupoProducto = new Object();
	
	var arrGrupoProducto_busq = new Array();
	var arrCodigoGrupoProducto_busq = new Object();
	
	//Variables / arr/ Objetos filtro busqueda 
	
	//GRUPO PRODUCTO 
	var arrGrupoPorProducto_busq = new Array();
	var arrCodigoGrupoPorProducto_busq = new Object();
		
	// UNIDAD PRODUCCION
	var arrUnidadProduccion_busq = new Array();
	var arrCodigoUnidadProduccion_busq = new Object();
	
	// UNIDAD NEGOCIO 
	var arrUnidadNegocio_busq = new Array();
	var arrCodigoUnidadNegocio_busq = new Object();
	// PUNTO DE VENTA 
	var arrPuntoVenta_busq = new Array();
	var arrCodigoPuntoVenta_busq = new Object();
	// Fin variables /arr / objetos busqueda 
	
    var arrPlan_busq = new Array();
	var arrCodigoPlan_busq = new Object();
	
	var arrContenedor= new Array();
	var arrCodigoContenedor = new Object();
	
		$(document).ready(function() {
			
			$("#addButton").click(function(){
				$('#add').find("input,textarea,select").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked", "").end();
			});
			
			
			$('#add').on('hidden.bs.modal', function (e) {
				  $(this).find("input,textarea,select").val('').end().find("input[type=checkbox], input[type=radio]").prop("checked", "").end();
				});
			
			activarMenu("ProductoPuntoVenta");
			$.ajax({
				url : '../ProductoXPuntoVentaController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) 
				{
					var productoGrupos=data.listadoProductoGrupo;
					$("#loading").fadeOut();
					if(data.numRegistros > 0)
					{
						var listadoProductoPuntoVenta = data.listaProductoPuntoVenta;
						var listadoPuntoVenta = data.listaPuntoVenta;
						var listadoUnidadNegocio = data.listaUnidadNegocio;
						var listadoUnidadProduccion = data.listaUnidadProduccion;
						var listadoGrupoPorProducto = data.listaGrupoPorProducto;
						var listadoPlan = data.listaPlan;
						var listaContenedor=data.listaContenedor
						
						var listadoGrupoProducto = data.listaGrupoProducto;
						
						var listadoGrupoProducto_busq = data.listaGrupoProducto;
						//Variables filtro busqueda 
						//GRUPO
						var listadoGrupoPorProducto_busq = data.listaGrupoPorProducto;
						//PRODUCTO

						//UNIDAD PRODUCCION
						var listadoUnidadProduccion_busq = data.listaUnidadProduccion;
						//UNIDAD NEGOCIO 
						var listadoUnidadNegocio_busq = data.listaUnidadNegocio;
						//PUNTO DE VENTA
						var listadoPuntoVenta_busq = data.listaPuntoVenta;
						//Fin Variables busqueda 
						var listadoPlan_busq = data.listaPlan;
						
						$.each(listadoGrupoProducto, function(index){
							arrGrupoProducto.push(listadoGrupoProducto[index].nombre );
							arrCodigoGrupoProducto[listadoGrupoProducto[index].nombre] = listadoGrupoProducto[index].codigo;
								   $("#grupo_producto").append("<option value='"+listadoGrupoProducto[index].codigo+"' grupoproducto='"+listadoGrupoProducto[index].codigo+"'>"+listadoGrupoProducto[index].nombre+"</option>");

				    	});

						$.each(listaContenedor, function(index){
							arrContenedor.push(listaContenedor[index].nombre );
							arrCodigoContenedor[listaContenedor[index].nombre] = listaContenedor[index].codigo;
								   $("#contenedorEnsurance").append("<option value='"+listaContenedor[index].codigo+"' contenedor='"+listaContenedor[index].codigo+"'>"+listaContenedor[index].nombre+" - "+listaContenedor[index].descripcion+"</option>");

				    	});
						
						
						$.each(listadoGrupoProducto_busq, function(index){
							arrGrupoProducto_busq.push(listadoGrupoProducto_busq[index].nombre );
							arrCodigoGrupoProducto_busq[listadoGrupoProducto_busq[index].nombre] = listadoGrupoProducto_busq[index].codigo;
							
							 if(index == 0)
				                {
				                	$("#grupo_producto_busq").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
				                }
							   else
								   {
								   $("#grupo_producto_busq").append("<option value='"+listadoGrupoProducto_busq[index].nombre+"' grupoproducto='"+listadoGrupoProducto_busq[index].codigo+"'>"+listadoGrupoProducto_busq[index].nombre+"</option>");
								   }
			              
				    	});
						
						

						$.each(listadoGrupoPorProducto, function(index){
							arrGrupoPorProducto.push(listadoGrupoPorProducto[index].nombre );
							arrCodigoGrupoPorProducto[listadoGrupoPorProducto[index].nombre] = listadoGrupoPorProducto[index].codigo;
			                $("#grupo_por_producto").append("<option value='"+listadoGrupoPorProducto[index].nombre+"' grupoporproducto='"+listadoGrupoPorProducto[index].codigo+"'>"+listadoGrupoPorProducto[index].nombre+"</option>");
				    	});
												
											
				    	$.each(listadoPuntoVenta, function(index){
				    		arrPuntoVenta.push(listadoPuntoVenta[index].nombre );
			                arrCodigoPuntoVenta[listadoPuntoVenta[index].nombre] = listadoPuntoVenta[index].codigo;
			                $("#puntoVenta").append("<option value='"+listadoPuntoVenta[index].nombre+"' puntoventa='"+listadoPuntoVenta[index].codigo+"'>"+listadoPuntoVenta[index].nombre+"</option>");
				    	});
						
				    	// Función filtro busqueda por Punto de vEnta
				    	$.each(listadoPuntoVenta_busq, function(index){
				    		arrPuntoVenta_busq.push(listadoPuntoVenta_busq[index].nombre );
			                arrCodigoPuntoVenta_busq[listadoPuntoVenta_busq[index].nombre] = listadoPuntoVenta_busq[index].codigo;
			                if(index == 0)
			                {
			                	$("#puntoVenta_busq").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
			                }
			                else
			                	{
			                $("#puntoVenta_busq").append("<option value='"+listadoPuntoVenta_busq[index].codigo+"'puntoventa='"+listadoPuntoVenta_busq[index].codigo+"'>"+listadoPuntoVenta_busq[index].nombre+"</option>");
			                	}	
			                });
				    	
				    	
				    	$.each(listadoUnidadNegocio, function(index){
				    		arrUnidadNegocio.push(listadoUnidadNegocio[index].nombre );
			                arrCodigoUnidadNegocio[listadoUnidadNegocio[index].nombre] = listadoUnidadNegocio[index].codigo;
			                $("#unidadNegocio").append("<option value='"+listadoUnidadNegocio[index].nombre+"'unidadnegocio='"+listadoUnidadNegocio[index].codigo+"'>"+listadoUnidadNegocio[index].nombre+"</option>");	
			           				    	});
						
				    	// Función filtro busqueda por Unidad de Negocio 
				    	
				    		$.each(listadoUnidadNegocio_busq, function(index){
				    		arrUnidadNegocio_busq.push(listadoUnidadNegocio_busq[index].nombre );
			                arrCodigoUnidadNegocio_busq[listadoUnidadNegocio_busq[index].nombre] = listadoUnidadNegocio_busq[index].codigo;
			                if(index == 0)
			                {
			                	$("#unidadNegocio_busq").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
			                }
			                else
			                	{
			                
			                $("#unidadNegocio_busq").append("<option value='"+listadoUnidadNegocio_busq[index].codigo+"'unidadnegocio='"+listadoUnidadNegocio_busq[index].codigo+"'>"+listadoUnidadNegocio_busq[index].nombre+"</option>");	
			                	}
				    	});

				    	$.each(listadoPlan, function(index){
							arrPlan.push(listadoPlan[index].nombre );
							arrCodigoPlan[listadoPlan[index].nombre] = listadoPlan[index].codigo;							
			                $("#plan").append("<option value='"+listadoPlan[index].nombre+"' planid='"+listadoPlan[index].codigo+"'>"+listadoPlan[index].nombre+"</option>");
				    	});
				    	
				    	//				    	
				    	$.each(listadoPlan_busq, function(index){
							arrPlan.push(listadoPlan_busq[index].nombre );
							arrCodigoPlan[listadoPlan_busq[index].nombre] = listadoPlan_busq[index].codigo;			
						    if(index == 0)
			                {
			                	$("#plan_busq").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
			                }
			                else
			                	{
			                $("#plan_busq").append("<option value='"+listadoPlan_busq[index].codigo+"' planid='"+listadoPlan_busq[index].codigo+"'>"+listadoPlan_busq[index].nombre+"</option>");
			                	}});
				    	//						
					}
					else
					{
						$("#dataTableContent").append("<tr><td colspan='5'>No existen Registros</td></tr>");
					}
				}
				
			});		
			
			// FUNCIÓN BOTON LIMPIAR EVENTO CLIC  

			 $('#LimpiarBtn').click(function(){	    				 
		    	 	$("#contenedor_id_busq").val("");
		    	 	$("#contenedor_descripcion_busq").val("");
		    	 	$("#grupo_por_producto_busq").val("");
		    	 	$("#grupo_producto_busq").val("");
		    	 	$("#unidadProduccion_busq").val("");	    	 	
		    	 	$("#unidadNegocio_busq").val("");
		    	 	$("#puntoVenta_busq").val("");
		    	 	$("#plan_busq").val("");
		    	 	$('#dataTable').hide();
		 			$('#dataTableContent').html('');
		 			$('#dataTable_wrapper').hide();
		    	 });
			//FIN FUNCIÓN LIMPIAR
			
			
			//FUNCIÓN BOTON CONSULTAR /BUSQUEDA 
			
			 $('#ConsultaBtn').click(function()
					 
			{			 
				 $('#dataTable').hide();
		 			$('#dataTable_wrapper').hide();	 				 			
		    		 $("#buscando").fadeIn("slow");

		    		 var contenedor_id_busq=$("#contenedor_id_busq").val();
			    	 var grupo_por_producto_busq=$("#grupo_por_producto_busq").val();
			    	 var unidadProduccion_busq=	$("#unidadProduccion_busq").val();	    	 	
			    	 var unidadNegocio_busq= $("#unidadNegocio_busq").val();
			    	 var puntoVenta_busq= $("#puntoVenta_busq").val();
			    	 var plan_busq= $("#plan_busq").val();
		    		 		    		 
		    		 if(contenedor_id_busq ==""  && grupo_por_producto_busq==""  && unidadProduccion_busq=="" && unidadNegocio_busq=="" && puntoVenta_busq=="" && plan_busq=="")
		    		 {
		     		alert("Ingrese al menos un campo de busqueda");
		    	 	$("#buscando").fadeOut("slow");
		    	 	return false;
		    		 }
		    		 
		    		 $.ajax({
			 				url : '../ProductoXPuntoVentaController',
			 				data : 
			 				{
			 					"contenedor_id_busq" : contenedor_id_busq,
			 					"grupo_por_producto_busq" : grupo_por_producto_busq,
			 					"unidadProduccion_busq" : unidadProduccion_busq,
			 					"unidadNegocio_busq":unidadNegocio_busq,
			 					"puntoVenta_busq": puntoVenta_busq,
			 					"plan_busq":plan_busq,
			 					"tipoConsulta" : "buscador"
			 				},
			 					type : 'POST',
			 					datatype : 'json',
			 					success : function(data) 
			 					{
			 						var existenRegistro = false;
			 						$('#dataTable').show();
				 					$('#dataTable_wrapper').show();	 					
				 					$('#dataTableContent').html('');
				 					$("#datos_temporal").val("");
			 						var listaProductoPuntoVenta = data.listaProductoPuntoVenta;
			 						var auxiliar = "";
			 						if(listaProductoPuntoVenta.length > 0)
			 						{
			 							$.each(listaProductoPuntoVenta, function(index)
			 							{
			 								var aux="	<tr class='odd gradeX'>" +
			 								        " <td relation='grupo_producto' style=\"display:none;\">"+ listaProductoPuntoVenta[index].grupo_producto +"</td>" +
			 								       " <td relation='grupo_productoId'>"+ listaProductoPuntoVenta[index].grupo_productoId +"</td>" +
			 										" <td relation='grupo_por_producto'>"+ listaProductoPuntoVenta[index].grupo_por_producto +"</td>" +
			 										" <td relation='puntoVenta'>"+ listaProductoPuntoVenta[index].puntoVenta +"</td>" +
			 										" <td relation='unidadNegocio'>"+ listaProductoPuntoVenta[index].unidadNegocio +"</td>" +
			 										" <td relation='unidadProduccion'>"+listaProductoPuntoVenta[index].unidadProduccion +"</td>" +
			 										" <td relation='contenedorEnsuranceId'>"+ listaProductoPuntoVenta[index].contenedorId +"</td>" +
			 										" <td relation='contenedorEnsurance' style=\"display:none;\">"+ listaProductoPuntoVenta[index].contenedor +"</td>" +
			 										" <td relation='plan'>"+ listaProductoPuntoVenta[index].plan +"</td>" +
			 										" <td width='175px'>" +
			 											" <input type='hidden' value='"+ listaProductoPuntoVenta[index].codigo +"'/>" +
			 											" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
			 			  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
			 											" </button>" +
			 											" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
			 											  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
			 											" </button>" +
			 										"</td>" +
			 									"</tr>";
			 						
			 								auxiliar +=	aux;
				 							
			 		 						$("#buscando").fadeOut("slow");	 		
			 							}); // fin imprime tabla 
			 							
			 							var oTable = $('#dataTable').dataTable();
										oTable.fnDestroy();							
										$('#dataTable tbody').html(auxiliar);
										
										
										/* Inicio Controles Actualizar Registro*/
										$(".actualizar-btn").bind({click: function() {
												$("#addButton").trigger("click");								
												$("#codigo").val($(this).parent().children().first().val());
												var elem = $(this).parent();
												var bandera = 1;
												var valorUnidadProduccion = "";
												do {
													elem = elem.prev();
													if (elem.is("td")){
														var elemCode = elem.attr("relation");										
														elementType(elem.text(), elemCode, $("#"+elemCode).attr("type"));
														if(elemCode == "unidadProduccion"){
															valorUnidadProduccion = elem.text();			
														}
													}else {
														bandera = 0;
													}
												} while (bandera == 1);			
												
												unidadesProduccionPorUnidadNegocio(valorUnidadProduccion);

											  }
										});
										/* Fin Controles Actualizar Registro*/

										/* Inicio Controles Elminar Registro */
										$(".eliminar-btn").bind({click: function() {
												var r = confirm("Seguro que desea eliminar el Producto Punto Venta " + $(this).parent().parent().children().first().text());
												if (r == true){
													codigo = $(this).parent().children().first().val();
													nombre = ""; matriz = ""; activo = ""; codigoEnsurance=""; grupoPorProductoId=""; contenedorDescripcion="";planId="";
													tipoConsulta = "eliminar";
													enviarDatos(codigo, puntoVentaId, unidadNegocioId, unidadProduccionId, contenedor, tipoConsulta,grupoPorProductoId,planId);
											    	$(this).parent().parent().remove();
												}
											}
										});	
										
										$('#dataTable').dataTable( {	 							
			        						"pagingType": "full",
			        						"bFilter": true,
			        						"bLengthChange": false,
			        						"bSort" : false,
			        						"iDisplayLength": 10, // Limitamos el numero de filas en la paginacion
			        						"dom": 'T<"clear">lfrtip',
			        						"tableTools": {
			            						"sSwfPath": "/CotizadorWeb/static/js/sb-admin/plugins/dataTables/swf/copy_csv_xls.swf",
			            					}		        						
			    						});
										
										existenRegistro = true;
			 						} // fin lista tamaño / length
			 						
			 						if(!existenRegistro)
			 						{
				 						var oTable = $('#dataTable').dataTable();
										oTable.fnDestroy();							
										$('#dataTable tbody').html("<tr><td colspan='12'>No existen Registros</td></tr>");
				 						$("#buscando").fadeOut("slow");
				 				    }
			 					}
			 			}); // fin ajax
		    		 
			}); // fin botón buscar
			
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
				grupoPorProductoId = $("#grupo_por_producto").find(':selected').attr('grupoporproducto');
				puntoVentaId = $("#puntoVenta").find(':selected').attr('puntoventa');					
				unidadNegocioId =$("#unidadNegocio").find(':selected').attr('unidadnegocio');					
				unidadProduccionId = $("#unidadProduccion").find(':selected').attr('unidadproduccion');
				contenedorId = $("#contenedorEnsurance").find(':selected').attr('contenedor');
				planId = $("#plan").find(':selected').attr('planid');					
				if (codigo == "")
					tipoConsulta = "crear";
				else
					tipoConsulta = "actualizar";
				enviarDatos(codigo,puntoVentaId, unidadNegocioId, unidadProduccionId, contenedorId, tipoConsulta,grupoPorProductoId,planId);
			});
		/* Fin Controles Grabar Resgistro*/
						
	});
		
		
		function unidadesProduccionPorUnidadNegocio(valorUnidadProduccion){
			$.ajax({
				url : '../UnidadProduccionController',
				data : {						
					"unidadNegocioId" : $('#unidadNegocio').find(':selected').attr('unidadnegocio'),				
					"tipoConsulta": "encontrarUnidadesProduccionPorUnidadNegocio"
				},
				type : 'POST',
				datatype : 'json',
				
				success : function(data) {
					 
					var listadoUnidadProduccion = data.listadoUnidadProduccion;
					$("#unidadProduccion").append('<option value="">&nbsp;&nbsp;Seleccione una opcion</option>');
					$.each(listadoUnidadProduccion, function(index){
		
						$("#unidadProduccion").append("<option value='"+listadoUnidadProduccion[index].nombre+"' unidadproduccion='"+listadoUnidadProduccion[index].codigo+"'>"+listadoUnidadProduccion[index].nombre+"</option>");
					});	
					if(valorUnidadProduccion!="")
						$("#unidadProduccion").val(valorUnidadProduccion);			
				}
				
			
			});$("#unidadProduccion" ).html("");
		
			
		}
		
		
		function unidadesProduccionPorUnidadNegocio_busq(valorUnidadProduccion){
			$.ajax({
				url : '../UnidadProduccionController',
				data : {						
					"unidadNegocioId" : $('#unidadNegocio_busq').find(':selected').attr('unidadnegocio'),				
					"tipoConsulta": "encontrarUnidadesProduccionPorUnidadNegocio"
				},
				type : 'POST',
				datatype : 'json',
				
				success : function(data) {
					 
					var listadoUnidadProduccion = data.listadoUnidadProduccion;
					$("#unidadProduccion_busq").append('<option value="">&nbsp;&nbsp;Seleccione una opcion</option>');
					$.each(listadoUnidadProduccion, function(index){
		
						$("#unidadProduccion_busq").append("<option value='"+listadoUnidadProduccion[index].codigo+"' unidadproduccion='"+listadoUnidadProduccion[index].codigo+"'>"+listadoUnidadProduccion[index].nombre+"</option>");
					});	
					if(valorUnidadProduccion!="")
						$("#unidadProduccion_busq").val(valorUnidadProduccion);			
				}
				
			
			});$("#unidadProduccion_busq" ).html("");
		
			
		}
		
		function grupoProducto_busq(valorGrupoProducto){
			$.ajax({
				url : '../GrupoPorProductoController',
				data : {						
					"grupoProductoId" : $('#grupo_producto_busq').find(':selected').attr('grupoproducto'),				
					"tipoConsulta": "encontrarGrupoPorProductoPorGrupoProducto"  
				},
				type : 'POST',
				datatype : 'json',
				
				success : function(data) {
					 
					var listadoGrupoPorProducto = data.listadoGrupoPorProducto;
					$("#grupo_por_producto_busq").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
					$.each(listadoGrupoPorProducto, function(index){
						$("#grupo_por_producto_busq").append("<option value='"+listadoGrupoPorProducto[index].codigo+"' grupoporproducto='"+listadoGrupoPorProducto[index].codigo+"'>"+listadoGrupoPorProducto[index].nombre+"</option>");
					});	
					if(valorGrupoProducto!="")
						$("#grupo_por_producto_busq").val(valorGrupoProducto);			
				}
				
			});$("#grupo_por_producto_busq" ).html("");
		
			
		}
		
		function grupoProducto(valorGrupoProducto){
			$.ajax({
				url : '../GrupoPorProductoController',
				data : {						
					"grupoProductoId" : $('#grupo_producto').find(':selected').attr('grupoproducto'),				
					"tipoConsulta": "encontrarGrupoPorProductoPorGrupoProducto"  
				},
				type : 'POST',
				datatype : 'json',
				
				success : function(data) {
					 
					var listadoGrupoPorProducto = data.listadoGrupoPorProducto;
					$("#grupo_por_producto").append('<option value="">&nbsp;&nbsp;Seleccione</option>');
					$.each(listadoGrupoPorProducto, function(index){
						$("#grupo_por_producto").append("<option value='"+listadoGrupoPorProducto[index].nombre+"' grupoporproducto='"+listadoGrupoPorProducto[index].codigo+"'>"+listadoGrupoPorProducto[index].nombre+"</option>");
					});	
					if(valorGrupoProducto!="")
						$("#grupo_por_producto").val(valorGrupoProducto);			
				}
				
			});$("#grupo_por_producto" ).html("");
		
			
		}
				
		function mostrarBotoLoading(){
			$("#loading").fadeIn();
		}

		function enviarDatos(codigo, puntoVentaId, unidadNegocioId, unidadProduccionId, contenedorId, tipoConsulta,grupoPorProductoId,planId){
			$.ajax({
				url : '../ProductoXPuntoVentaController',
				data : {
					"codigo" : codigo,
					"grupoPorProductoId" : grupoPorProductoId,
					"puntoVenta" : puntoVentaId,
					"unidadNegocio" : unidadNegocioId,
					"unidadProduccion" : unidadProduccionId,
					"contenedorEnsurance" : contenedorId,
					"planId" : planId,
					"tipoConsulta": tipoConsulta
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
	<div class="well">
			<table class="table">
				<thead>
					<tr>
						<td colspan="3" style="font-weight: bold;"><center>Buscador
								Mantenimiento Producto por Punto de Venta</center></td>
					</tr>
					<tr>
						<th>Busqueda por Contenedor:</th>
						<th>Número: <input type="text" id="contenedor_id_busq"
							></th>
					</tr>
					<tr>
						<th>Busqueda por Producto:</th>
						<th>Grupo Producto: <select id="grupo_producto_busq" onchange="grupoProducto_busq()"></select></th>
						<th>Grupo por Producto: <select id="grupo_por_producto_busq"></select></th>
											</tr>
					<tr>
						<th>Busqueda por Unidad:</th>
						<th>Uni. Negocio: <select id="unidadNegocio_busq" onchange="unidadesProduccionPorUnidadNegocio_busq()"></select></th>
						<th>Uni. Producción: <select id="unidadProduccion_busq" ></select></th>
					</tr>
					<tr>
						<th>Busqueda por Punto Venta</th>
						<th>Punto Venta: <select id="puntoVenta_busq"></select></th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<th>Busqueda por Plan</th>
						<th>Plan: <select id="plan_busq"></select></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>
							<button class="btn btn-primary" id="ConsultaBtn">
								<span class="glyphicon glyphicon-search"></span> &nbsp; Consulta
							</button>							
						</th>
						<th>
							<button class="btn btn-primary" id="LimpiarBtn">
								<span class="glyphicon glyphicon-trash"></span> &nbsp; Limpiar
							</button>							
						</th>
						<th>
							<button class="btn btn-primary" data-toggle="modal" data-target="#add" id="addButton">
			<span class="glyphicon glyphicon-plus"></span> &nbsp; Nuevo
		</button>
						</th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<th><div id="buscando" hidden="hidden">
								<div style="margin-top: 10px;">
									<span id="loading-msg"></span><img
										src="../static/images/ajax-loader.gif" /> Buscando la
									informacion, por favor espere...
								</div>
							</div></th>
						<th>&nbsp;</th>
					</tr>
				</tbody>
			</table>
		</div>
	
		<!-- Button trigger modal -->

	</div>
	
	<div class="row crud-nav-bar">
		<!-- Button trigger modal -->

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
							<h4 class="modal-title" id="myModalLabel">Producto - Punto de Venta</h4>
						</div>
						<div class="modal-body">
							<div class="alert alert-success" id="msgPopup">El Producto - Punto de Venta se grabo con exito.</div>
							<div class="form-group">
								<input type="hidden"class="form-control" id="codigo">
								<label>Grupo Producto</label>
								<select type="select" class="form-control required" id="grupo_producto" class="required" onchange="grupoProducto()">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Grupo por Producto</label>
								<select type="select" class="form-control required" id="grupo_por_producto" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>								
								<label>Punto de Venta</label>
								<select type="select" class="form-control required" id="puntoVenta" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Unidad de Negocio</label>
								<select type="select" class="form-control required" id="unidadNegocio" class="required" onchange="unidadesProduccionPorUnidadNegocio()">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Unidad de Producción</label>
								<select type="select" class="form-control required" id="unidadProduccion" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Contenedor Ensurance</label>
								<select type="select" class="form-control required" id="contenedorEnsurance" class="required">
								  <option value=''>Seleccione una opcion</option>
								</select>
								<label>Plan</label>
								<select type="select" class="form-control required" id="plan" class="required">
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
							<table class="table table-striped table-bordered table-hover"
					id="dataTable">
					<thead>
						<tr>
						 <th style="display:none;" >Grupo Producto</th>
						 <th>Grupo Producto</th>
							<th>Grupo por Producto</th>
							<th>Punto Venta</th>
							<th>Unidad Negocio</th>
							<th>Unidad Produccion</th>
							<th>ContenedorId</th>
							<th style="display:none;" >Contenedor</th>
							<th>Plan</th>
							<th> Acción</th>
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