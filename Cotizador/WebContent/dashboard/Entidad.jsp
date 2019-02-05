<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Entidad - CotizadorQBE</title>
	
	<!-- Core CSS - Include with every page -->
	<link href="../static/css/sb-admin/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
	<link href="../static/css/loading.css" rel="stylesheet">
	
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="../static/js/sb-admin/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script src="../static/js/util.js"></script>
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
		var codigo = "";
		//var tipoIdentificacion = "";
		var identificacion = "";
		var nombres = "";
		var apellidos = "";
		var codigoEnsurance = "";
		var mail = "";
		var telefono="";
		var celular="";
		var arrTipoIdentificacion = new Array();
		var arrCodigoTipoIdentificacion = new Object();
		
		$(document).ready(function() {
			
			$("#tipoIdentificacion").change(function(){
				
				if($(this).val()=="Pasaporte"){
					$("#identificacion").attr("onkeypress","validarSoloLetrasNumeros(event);");
				}
				else{
					$("#identificacion").attr("onkeypress","validarKeyPress(event,/[0-9]/);");
				}
				
			});
			
			$("#identificacion").change(function(){
				var tipo_identificacion=$("#tipoIdentificacion").val();
				var identificacion=$("#identificacion").val();
				if((tipo_identificacion=='RUC Persona Natural'||tipo_identificacion=='RUC Persona Jurídica')&&!validaRuc(identificacion)){
					alert('Por favor ingrese un RUC valido');
					$("#identificacion").val('');
					$("#nombre").val('');
					$("#nombreCompleto").val('');
					$("#apellido").val('');
					$("#mail").val('');
					$("#telefono").val('');
					$("#convencional").val('');
				} else
				if((tipo_identificacion=='Cédula')&&!validaCedula(identificacion)){
					alert('Por favor ingrese una Cédula valida');
					$("#identificacion").val('');
					$("#codigoEnsurance").val('');
					$("#nombre").val('');
					$("#nombreCompleto").val('');
					$("#apellido").val('');
					$("#mail").val('');
					$("#telefono").val('');
					$("#convencional").val('');
				}
			
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
						if(entidad.entidadIdEnsurance!=null ){
						$("#codigoEnsurance").val(entidad.entidadIdEnsurance);
						if(entidad.codigo!=null)
						$("#codigo").val(entidad.codigo);
						//$("#identificacion").val();
						$("#nombre").val(entidad.nombre);
						$("#nombreCompleto").val(entidad.nombreCompleto);
						$("#apellido").val(entidad.apellido);
						$("#mail").val(entidad.mail);
						$("#telefono").val(entidad.telefono);
						$("#convencional").val(entidad.convencional);
						$("#tipoIdentificacion").val(entidad.tipoIdentificacionNombre); }
						$("#loading_identificacion").fadeOut();
						validarEntidadJr(identificacion)
					},
					error:function(){
						$("#loading_identificacion").fadeOut();
					}
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
								$("#apellido").val("");
								$("#nombreCompleto").val("");
								$("#mail").val("");
								$("#telefono").val("");
								$("#celular").val("");
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
			
			$("#mail").change(function(){
				var mail=$("#mail").val();
				if(!IsEmail(mail))
					{
					alert("Ingrese un correo Valido!");
					$("#mail").val('');
					}
								
			});
			
			$("#nombre").keyup(function(){
				$("#nombreCompleto").val($("#apellido").val()+" "+$("#nombre").val());				
			});
			
			$("#apellido").keyup(function(){
				$("#nombreCompleto").val($("#apellido").val()+" "+$("#nombre").val());				
			});
			
			activarMenu("Entidad");
			$.ajax({
				url : '../EntidadController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#loading").remove();
					if(data.numRegistros > 0){
						var listadoEntidad = data.listadoEntidad;
						var listadoTipoIdentificacion = data.listadoTipoIdentificacion;
						
						
						$.each(listadoTipoIdentificacion, function(index){
				    		arrTipoIdentificacion.push( listadoTipoIdentificacion[index].nombre );
			                arrCodigoTipoIdentificacion[listadoTipoIdentificacion[index].nombre] = listadoTipoIdentificacion[index].codigo;
			                $("#tipoIdentificacion").append("<option value='"+listadoTipoIdentificacion[index].nombre+"'>"+listadoTipoIdentificacion[index].nombre+"</option>");
				    	});				    
						
						$.each(listadoEntidad, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='codigoEnsurance'>"+ listadoEntidad[index].codigoEnsurance +"</td>" +
									" <td relation='tipoIdentificacion'>"+ listadoEntidad[index].tipoIdentificacion+"</td>" +
									" <td relation='identificacion'>"+ listadoEntidad[index].identificacion +"</td>" +
									" <td relation='nombre'>"+ listadoEntidad[index].nombre +"</td>" +
									" <td relation='apellido'>"+ listadoEntidad[index].apellido +"</td>" +
									" <td relation='nombreCompleto'>"+ listadoEntidad[index].nombreCompleto +"</td>" +
									" <td relation='mail'>"+ listadoEntidad[index].mail +"</td>" +
									" <td relation='telefono'>"+ listadoEntidad[index].telefono +"</td>" +
									" <td relation='celular'>"+ listadoEntidad[index].celular +"</td>" +
 									" <td width='175px'>" +
									" <input type='hidden' value='"+ listadoEntidad[index].codigo +"'/>" +
										" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
		  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
										" </button>" +
 										" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
 										  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Eliminar" +
										" </button>" +

									"</td>" +
								"</tr>");
					
						});
						
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
								var r = confirm("Seguro que desea eliminar la Entidad " + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									nombre = "";  codigoEnsurance=""; 
									tipoIdentificacion=""; identificacion="";
									mail ="";
									tipoConsulta = "eliminar";
									enviarDatos(codigoEnsurance, codigo, nombre, apellidos, tipoConsulta, tipoIdentificacion,identificacion,mail);
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
		

					codigoEnsurance = $("#codigoEnsurance").val();
					codigo = $("#codigo").val();
					identificacion = $("#identificacion").val();
					nombre = $("#nombre").val();
					apellido = $("#apellido").val();
					mail = $("#mail").val();
					telefono = $("#telefono").val();
					celular = $("#celular").val();
					tipoIdentificacion = arrCodigoTipoIdentificacion[$("#tipoIdentificacion").val()]; 
					if (codigo == "")
						tipoConsulta = "crear";
					else
						tipoConsulta = "actualizar";
					
					if (nombre=="" && apellido=="" && identificacion=="")
						{
						alert("Ingrese al menos un valor ");
						}
					else
						{
					enviarDatos(codigoEnsurance, codigo, nombre, apellido, tipoConsulta, tipoIdentificacion,identificacion,mail,telefono,celular);
					$("#close-popup").trigger("click");
						}
				});
			/* Fin Controles Grabar Registro*/
			
			$("#close-popup").click(function(){
				 location.reload();
			});
			
			function enviarDatos(codigoEnsurance, codigo, nombre, apellido, tipoConsulta, tipoIdentificacion,identificacion,mail,telefono,celular){				
				$.ajax({
					url : '../EntidadController',
					data : {
						"codigoEnsurance" : codigoEnsurance,
						"codigo" : codigo,
						"nombre" : nombre,
						"apellido" : apellido,
						"tipoConsulta" : tipoConsulta,
						"tipoIdentificacion" : tipoIdentificacion,
						"identificacion" : identificacion,
						"nombreCompleto" : apellido+' '+nombre,
						"mail" : mail,
						"telefono" : telefono,	
						"celular" : celular,	
						"tipoEntidad" : ""
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
                        <h4 class="modal-title" id="myModalLabel">Nueva Entidad</h4>
                    </div>
                    <div class="modal-body">
                        <div class="alert alert-success" id="msgPopup">La Entidad se grabo con exito.</div>
                        <div class="form-group">
                            <input type="hidden" class="form-control" id="codigo">
                             <label>Identificación</label>
                            <input type="text" class="form-control required" id="identificacion">
                            <div id="loading_identificacion" hidden="" ><span id="loading-msg">Cargando...</span><img  src="../static/images/ajax-loader.gif" /></div>
                            <label>Tipo Identificación</label>
                            <select type="select" class="form-control required" id="tipoIdentificacion">
                                <option>Seleccione una opcion</option>
                            </select>                  
							<label>Nombre </label>
                            <input type="text" style="text-transform: uppercase" class="form-control required" id="nombre" onkeypress="validarKeyPress(event,/[a-zA-Z\s]/)">
                            <label>Apellido</label>
                            <input type="text" style="text-transform: uppercase" class="form-control" id="apellido" onkeypress="validarKeyPress(event,/[a-zA-Z\s]/)">
                            <label>Nombre Completo</label>
                            <input type="text" style="text-transform: uppercase" class="form-control" disabled="disabled" id="nombreCompleto" onkeypress="validarKeyPress(event,/[a-zA-Z\s]/)">
                            <label>Codigo Ensurance</label>
                            <input type="text" class="form-control" id="codigoEnsurance" disabled="disabled" onkeypress="validarKeyPress(event,/[0-9]/)">
                            <label>Email</label>
                            <input type="text" class="form-control" id="mail">
                            <label>Teléfono Convencional</label>
                            <input type="text" class="form-control" id="telefono" onkeypress="validarKeyPress(event,/[0-9]/)">
                            <label>Teléfono Celular</label>
                            <input type="text" class="form-control" id="celular" onkeypress="validarKeyPress(event,/[0-9]/)">
                            
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="close-popup"
                                data-dismiss="modal">Close
                        </button>
                        <button type="button" class="btn btn-primary" id="save-record">Save
                            changes
                        </button>
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
				<br>			
				<table class="table table-striped table-bordered table-hover"
					id="dataTable" style="font-size: x-small;">
					<thead>
						<tr>
							<th>Codigo Ensurance</th>
							<th>Tipo Identificacion</th>
							<th>Identificación</th>
							<th>Nombre </th>
							<th>Apellido</th>
							<th>Nombre Completo</th>
							<th>E-mail</th>
							<th>Teléfono Convencional</th>
							<th>Teléfono Celular</th>
							<th>Accion</th>
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
