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
	<script src="../static/js/sb-admin/plugins/dataTables/jquery.numeric.js"></script>
	<script src="../static/js/util.js"></script>
	
	<!-- KENDO -->
	<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
	<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
    <script src="../static/js/Kendo/kendo.all.min.js"></script>
    <script src="../static/js/Kendo/kendo.web.min.js"></script>
    
  

<title>Reglas Agricola</title>
<script>
	$(function() {
		$(".wrapper1").scroll(function() {
			$(".wrapper2").scrollLeft($(".wrapper1").scrollLeft());
		});
		$(".wrapper2").scroll(function() {
			$(".wrapper1").scrollLeft($(".wrapper2").scrollLeft());
		});
	});

	var ReglaId = "";
	var TipoCultivoId = "";
	var CicloId = "";
	var TipoCalculoId = "";
	var ProvinciaId = "";
	var CantonId = "";
	var CostoProduccion = "";
	var Tasa = "";
	var CostoMantenimiento = "";
	var AceptabilidadDesde = "";
	var AceptabilidadHasta = "";
	var Observaciones = "";
	var Estado = "";

	$(document).ready(
	function() {
	activarMenu("ReglasAgricola");
	$("#CostoProduccion").kendoNumericTextBox({
        format: "c",
        min: 0,
        decimals: 2
    });
	$("#Tasa").kendoNumericTextBox({
		format : '#.00',
		 decimals: 2
    });
	$("#CostoMantenimiento").kendoNumericTextBox({
		format : '#.00',
        min: 0,
        decimals: 2
    });
	$("#AceptabilidadDesde").kendoDatePicker({format:"yyyy-MM-dd"});
    $("#AceptabilidadHasta").kendoDatePicker({format:"yyyy-MM-dd" });

	Cargar();
	ConsultarTipoCultivo();
	ConsultarCiclo();
	ConsultarTipoCalculo();
	ConsultarProvincia();
	$("#save-record").bind({
		click : function() {

		var validator = $("#formCrud").kendoValidator().data("kendoValidator");
		$(".required").css("border", "1px solid #ccc");
			if (validator.validate() === false) {
			$(".required").each(
			function(index) {
			var cadena = $(this).val();
			if (cadena.length <= 0) {
				$(this).css("border","1px solid red");
				alert("Por favor ingrese el campo requerido");
				$(this).focus();
				return false;
			}
			});
			} else {
				var Ciclo = $("#Ciclo").val();
				var Desde = $("#AceptabilidadDesde").val();
				var Hasta = $("#AceptabilidadHasta").val();
				/// Si el check de aceptabilidad esta marcado que seleccione el ciclo
				if ($("#AceptaCicloId").prop("checked") == true && Ciclo==""){
					alert("Por favor debe seleccionar el ciclo");
					$(this).focus();
					return false;
				}
				///Si el check no esta marcado debe ingresar fechas de aceptabilidad
				if ($("#AceptaCicloId").prop("checked") == false &&(Desde==""  	|| Hasta=="")){
					alert("Por favor debe ingresar las fechas de aceptabilidad");
					$(this).focus();
					return false;
				}
				ReglaId = $("#ReglaId").val();
				TipoCultivoId = $("#TipoCultivo").val();
				CicloId = $("#Ciclo").val();
				TipoCalculoId = $("#TipoCalculo").val();
				ProvinciaId = $("#Provincia").val();
				CantonId = $("#Ciudad").val();
				CostoProduccion = $("#CostoProduccion").val();
				Tasa = $("#Tasa").val();
				CostoMantenimiento = $("#CostoMantenimiento").val();
				AceptabilidadDesde = $("#AceptabilidadDesde").val();
				AceptabilidadHasta = $("#AceptabilidadHasta").val();
				Observaciones = $("#Observaciones").val();
				if (ReglaId == "")
					tipoConsulta = "crear";
				else
					tipoConsulta = "editar";

				enviarDatos(ReglaId,TipoCultivoId,CicloId,TipoCalculoId,
				ProvinciaId,CantonId,CostoProduccion,Tasa, CostoMantenimiento,
				AceptabilidadDesde,AceptabilidadHasta,
				Observaciones,Estado,tipoConsulta);
				}
			}
	});
	$("#Provincia").change(function(){	
		Provincia = $("#Provincia").val();
		$("select option:selected").each(function(){
			
			$.ajax({
				url : '../CantonController',
				data : {
					"tipoConsulta":"encontrarPorProvincia",
					"provincia" : Provincia							
				},
				type : 'POST',
				datatype : 'json',
				success : function(data){
					$("#Ciudad").children().remove();
					$("#Ciudad").append("<option>Seleccione una opción</option>");
					var listadoCiudades = data.cantones;
					$.each(listadoCiudades, function (index){								
						$("#Ciudad").append("<option value='"+ listadoCiudades[index].id +"'>"+ listadoCiudades[index].nombre +"</option>");
					});
				}
			});
		});
	});	
	$("#TipoCultivo").change(function(){
		var Tipo = this.options[this.selectedIndex].getAttribute("class");
		$("select option:selected").each(function(){
			if (Tipo=="2"){
				$("#TablaValores").hide();
				($("#UnidadPorHec").data("kendoNumericTextBox")).value("");
				($("#PrecioPorUni").data("kendoNumericTextBox")).value("");
				}
			else {
				$("#TablaValores").show();
				($("#UnidadPorHec").data("kendoNumericTextBox")).value("");
				($("#PrecioPorUni").data("kendoNumericTextBox")).value("");
				}
			
		});
	});	
	$("#Ciclo").change(function(){	
		cicloId = $("#Ciclo").val();
		$("select option:selected").each(function(){
			if (cicloId!=""){
				$("#AceptaCicloId").prop("checked", true);// para poner la marca
				$("#AceptabilidadTable").css("display","none");	
				$("#AceptabilidadDesde").val("");
				$("#AceptabilidadHasta").val("");
			}
			else {
				$("#AceptaCicloId").prop("checked", false);// para poner la marca
				$("#AceptabilidadTable").css("display","block");	
			}
		});
	});	
	$("#AceptaCicloId").change(function(){	
		//Si se cambia el estado del check se oculta/muestra la aceptabilidad
		if ($("#AceptaCicloId").prop("checked") == true){
		$("#AceptabilidadTable").css("display","none");
		$("#AceptabilidadDesde").val("");
		$("#AceptabilidadHasta").val("");
		}
		else {
			$("#AceptabilidadTable").css("display","block");
			$("#Ciclo").val("");
		}
	});
});
	function actualizar(Regla) {
		$.ajax({
			url : '../AgriReglaController',
			data : {
				"ReglaId" : Regla,
				"tipoConsulta" : "obtenerPorId"
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				$("#ReglaId").val(data.ReglaId);
				$("#TipoCultivo").children().remove();
				$("#Ciclo").children().remove();
				$("#TipoCalculo").children().remove();
				$("#Provincia").children().remove();
				$("#Ciudad").children().remove();
				ConsultarTipoCultivo ();
				ConsultarCiclo();
				ConsultarTipoCalculo();
				ConsultarProvincia();
				$("#TipoCultivo").val(data.TipoCultivoId);
				$("#Ciclo").val(data.CicloId);
				$("#TipoCalculo").val(data.TipoCalculoId);
				$("#Provincia").val(data.ProvinciaId);
				ConsultarCiudad();
				$("#Ciudad").val(data.CantonId);
				($("#CostoProduccion").data("kendoNumericTextBox")).value(data.CostoProduccion);
				($("#Tasa").data("kendoNumericTextBox")).value(data.Tasa);
				($("#CostoMantenimiento").data("kendoNumericTextBox")).value(data.CostoMantenimiento);
				$("#AceptabilidadDesde").val(data.AceptabilidadDesde);
				$("#AceptabilidadHasta").val(data.AceptabilidadHasta);
				$("#Observaciones").val(data.Observaciones);
				if (data.CicloId!=null){
					$("#AceptaCicloId").prop("checked", true);// para poner la marca
					$("#AceptabilidadTable").css("display","none");
					}  
				else {
					$("#AceptaCicloId").prop("checked", false);
					$("#AceptabilidadTable").css("display","block");
					}
				//var Tipo = this.options[this.selectedIndex].getAttribute("class");
				var Tipo = $('#TipoCultivo option:selected').attr('class');
				if (Tipo=="2"){
						$("#TablaValores").hide();
						}
				else {
						$("#TablaValores").show();
						}
			}
		});
	}

	function eliminar(Regla) {
		var r = confirm("Seguro que desea eliminar");
		if (r == true) {
			ReglaId = Regla;
			tipoConsulta = "eliminar";
			enviarDatos(ReglaId, "", "", "",
					"", "", "", "","", "", "","", "", tipoConsulta);
		}
		Cargar();
		$("#msgPopup").hide();
	}

	function enviarDatos(ReglaId, TipoCultivoId, CicloId, TipoCalculoId,
			ProvinciaId, CantonId, CostoProduccion, Tasa, CostoMantenimiento,
			AceptabilidadDesde, AceptabilidadHasta,
			Observaciones, Estado, tipoConsulta) {
		$.ajax({
			url : '../AgriReglaController',
			data : {
				"ReglaId" : ReglaId,
				"TipoCultivoId" : TipoCultivoId,
				"CicloId" : CicloId,
				"TipoCalculoId" : TipoCalculoId,
				"ProvinciaId" : ProvinciaId,
				"CantonId" : CantonId,
				"CostoProduccion":CostoProduccion,
				"Tasa" : Tasa,
				"CostoMantenimiento" : CostoMantenimiento,
				"AceptabilidadDesde" : AceptabilidadDesde,
				"AceptabilidadHasta" : AceptabilidadHasta,
				"Observaciones" : Observaciones,
				"Estado" : Estado,
				"tipoConsulta" : tipoConsulta
			},
			type : 'POST',
			async : false,
			datatype : 'json',
			success : function(data) {
				$("#msgPopup").show();
			}
		});
	}
	function Cargar() {
		$('#configuracionRegla').children().remove();
		$
				.ajax({
					url : '../AgriReglaController',
					data : {
						'tipoConsulta' : 'encontrarTodos'
					},
					type : 'post',
					datatype : 'json',
					async : false,
					success : function(data) {
						var ReglaJSONArray = data.ReglaJSONArray;
						$
								.each(
										ReglaJSONArray,
										function(index) {
											$('#configuracionRegla')
													.append(
															"<tr class='odd gradeX'>"
																	+ "<td relation='TipoCultivoId'>"
																	+ ReglaJSONArray[index].TipoCultivoId
																	+ "</td>"
																	+ "<td relation='TipoCalculoId'>"
																	+ ReglaJSONArray[index].TipoCalculoId
																	+ "</td>"
																	+ "<td relation='ProvinciaId'>"
																	+ ReglaJSONArray[index].ProvinciaId
																	+ "</td>"
																	+ "<td relation='CantonId'>"
																	+ ReglaJSONArray[index].CantonId
																	+ "</td>"
																	+ "<td relation='Estado'>"
																	+ ReglaJSONArray[index].Estado
																	+ "</td>"
																	+

																	"<td width='175px'>"
																	+ "<input type='hidden' id='ReglaId' value='" + ReglaJSONArray[index].ReglaId + "'/>"
																	+ " <button type='button'  name='actualizar-btn' data-toggle='modal' data-target='#add' class='btn btn-success btn-xs actualizar-btn' onclick='actualizar("
																	+ ReglaJSONArray[index].ReglaId
																	+ ");'>"
																	+ " <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar"
																	+ " </button>"
																	+ " <button type='button' class='btn btn-danger btn-xs eliminar-btn' onclick='eliminar("
																	+ ReglaJSONArray[index].ReglaId
																	+ ");'>"
																	+ "<span class='glyphicon glyphicon glyphicon-remove' id='delete-record' ></span> Eliminar"
																	+ " </button>"
																	+ "</td>"
																	+ "</tr>");
										});
						$("#loading").remove();
					}
				});
	}
	function ConsultarTipoCultivo (){
		//Consultar los tipo de cultivos
		$.ajax({
			url : '../AgriTipoCultivoController',
			data : {
				"tipoConsulta" : "encontrarTodos"
			},	
			async: false,
			type : 'post',
			datatype : 'json',
			success : function (data) {
				var listadoTiposCultivo = data.TipoCultivoJSONArray;	 
				$("#TipoCultivo").append("<option value='' class=''>Seleccione una opcion</option>");
				$.each(listadoTiposCultivo, function (index) {
					$("#TipoCultivo").append("<option value='" + listadoTiposCultivo[index].TipoCultivoId + "' class='"+ listadoTiposCultivo[index].Tipo_ +"'>" + listadoTiposCultivo[index].Nombre + "</option>");
				});	
				
			}
		});
	}
	function ConsultarCiclo(){
		$.ajax({
			url:'../AgriCicloController',
			data:{"tipoConsulta":"encontrarTodos"},
			async: false,
			type:'post',
			datatype:'json',
			success: function(data){
				var listadoCiclo = data.CicloJSONArray;
				$("#Ciclo").append("<option value =''>Seleccione una opción</option>");
				$.each(listadoCiclo, function(index){
					$("#Ciclo").append("<option value='"+ listadoCiclo[index].CicloId+"'>"+listadoCiclo[index].Nombre+"</option>");
				});
			}
		});
	}
	function ConsultarTipoCalculo(){
		$.ajax({
			url:'../AgriTipoCalculoController',
			data:{"tipoConsulta":"encontrarTodos"},
			async: false,
			type:'post',
			datatype:'json',
			success: function(data){
				var listadoTipoCalculo = data.TipoCalculoJSONArray;
				$("#TipoCalculo").append("<option value =''>Seleccione una opción</option>");
				$.each(listadoTipoCalculo, function(index){
					$("#TipoCalculo").append("<option value='"+ listadoTipoCalculo[index].TipoCalculoId+"'>"+listadoTipoCalculo[index].Nombre+"</option>");
				});
			}
		});
	}
	function ConsultarProvincia(){
		$.ajax({
			url:'../ProvinciaController',
			data:{"tipoConsulta":"encontrarTodos"},
			async: false,
			type:'post',
			datatype:'json',
			success: function(data){
				var listadoProvincia = data.listadoProvincia;
				$("#Provincia").append("<option value =''>Seleccione una opción</option>");
				$.each(listadoProvincia, function(index){
					$("#Provincia").append("<option value='"+ listadoProvincia[index].codigo+"'>"+listadoProvincia[index].nombre+"</option>");
				});
			}
		});
	}
	function ConsultarCiudad(){
		Provincia = $("#Provincia").val();
		$.ajax({
			url : '../CantonController',
			data : {
				"tipoConsulta":"encontrarPorProvincia",
				"provincia" : Provincia							
			},
			async: false,
			type : 'POST',
			datatype : 'json',
			success : function(data){
				$("#Ciudad").children().remove();
				$("#Ciudad").append("<option>Seleccione una opción</option>");
				var listadoCiudades = data.cantones;
				$.each(listadoCiudades, function (index){								
					$("#Ciudad").append("<option value='"+ listadoCiudades[index].id +"'>"+ listadoCiudades[index].nombre +"</option>");
				});
			}
		});
		
	}
</script>
</head>
<body>
	<%
		// Permitimos el acceso si la session existe		
		if (session.getAttribute("login") == null) {
			response.sendRedirect("/CotizadorWeb");
		}
	%>

	<div class="row crud-nav-bar">
		<!-- Button trigger modal -->
		<button class="btn btn-primary" data-toggle="modal" data-target="#add"
			id="addButton">
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
							<h4 class="modal-title" id="myModalLabel">Reglas Agrícola</h4>
						</div>
						<div class="modal-body">
						<div class="alert alert-success" id="msgPopup">La regla se grabo con éxito.</div>
						<input type="hidden" class="form-control" id="ReglaId">
						<div class="panel panel-primary">
							<div class="panel-heading">Datos Generales</div>
							<div class="panel-body">
								<table>
									<tr>
										<td>Tipo de Cultivo:</td>
										<td width="210px">
										<select style="width: 180px" name="TipoCultivo"
											id="TipoCultivo" class="datosRegla"
											validationMessage="Campo requerido!!!" required>
											</select></td>
										<td width="70px">Tipo de Cálculo:</td>
										<td width="190px"><select style="width: 180px" name="TipoCalculo"
											id="TipoCalculo" class="datosRegla"
											validationMessage="Campo requerido!!!" required></select></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>Provincia:</td>
										<td><select style="width: 180px" name="Provincia"
											id="Provincia" class="datosRegla"
											validationMessage="Campo requerido!!!" required>
										</select></td>
										<td>Cantón:</td>
										<td><select style="width: 180px" name="Ciudad"
											id="Ciudad" class="datosRegla"
											validationMessage="Campo requerido!!!" required>
										</select></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>Ciclo:</td>
										<td><select style="width: 180px" name="Ciclo"
											id="Ciclo" class="datosRegla">
										</select></td>
										
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="panel panel-primary">
							<div class="panel-heading">Datos Regla</div>
							<div class="panel-body">
								<table>
									<tr>
										<td width="90px">Costo de Producción:</td>
										<td width="180px">
											<input type="text"
															name="CostoProduccion" id="CostoProduccion"
															class="datosRegla" validationMessage="Campo requerido!!!" required></input>
											</td>
										<td width="120px">Tasa:</td>
										<td><input type="number" name="Tasa" id="Tasa"
											class="datosRegla" validationMessage="Campo requerido!!!" required></td>

									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
								</table>
								<table id="TablaValores" width="100%">
									<tr>
									<td width="90px">Costo de Mantenimiento:</td>
										<td width="180px"><input type="number" name="CostoMantenimiento"
											id="CostoMantenimiento" class="datosRegla" ></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									</table>
								<table>
									<tr>
										<td width="90px">Acepta Ciclo Agrícola:</td>
										<td width="40px"><input type="checkbox" id="AceptaCicloId"
											class="datosRegla" name="AceptaCicloId"></td>
											<td><table id="AceptabilidadTable">
											<tr>
											<td width="90px">Aceptabilidad:</td>
										<td>Desde:<input type="text" name="AceptabilidadDesde"
											id="AceptabilidadDesde" class="datosRegla">
										</td>
										<td>&nbsp;</td>
										<td>Hasta:<input type="text" name="AceptabilidadHasta"
											id="AceptabilidadHasta" class="datosRegla">
										</td>
											</tr>
											</table></td>
										
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
								</table>
								<table>
									<tr>

										<td width="100px">Observaciones:</td>
										<td ><textarea cols="20" rows="3" style="width: 430px; "  name="Observaciones"
											id="Observaciones" class="datosRegla"></textarea></td>
									</tr>
								</table>
							</div>
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
					<div class="input-group">
						<span class="input-group-addon">Filtro</span> <input id="filter"
							type="text" class="form-control"
							placeholder="Escriba la palabra a buscar...">
					</div>
					<table class="table table-striped table-bordered table-hover"
						id="dataTable">
						<thead>
							<tr>
								<th>Tipo Cultivo</th>
								<th>Tipo Calculo</th>
								<th>Provincia</th>
								<th>Cantón</th>
								<th>Estado</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="configuracionRegla" class="searchable">
							<div id="loading">
								<div class="loading-indicator">
									<img src="../static/images/ajax-loader.gif" /><br /> <br />
									<span id="loading-msg">Cargando...</span>
								</div>
							</div>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- Datatable -->
</body>
</html>