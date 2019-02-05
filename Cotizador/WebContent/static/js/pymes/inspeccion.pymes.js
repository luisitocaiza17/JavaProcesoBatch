function cargarInicalInspeccion(){
	
	$("#tabstrip").kendoTabStrip({
		select: onSelect
    });
	
	$("#numero_total_pisos").kendoNumericTextBox({
		format: "#",
        decimals: 0
	});
	
	$("#anio_construccion").kendoNumericTextBox({
		format: "#",
        decimals: 0
	});
	
    $("#files").kendoUpload();
	
	var QueryString = function () {
		// This function is anonymous, is executed immediately and
		// the return value is assigned to QueryString!
		var query_string = {};
		var query = window.location.search.substring(1);
		var vars = query.split("&");
		for (var i = 0; i < vars.length; i++) {
			var pair = vars[i].split("=");
			// If first entry with this name
			if (typeof query_string[pair[0]] === "undefined") {
				query_string[pair[0]] = pair[1];
				// If second entry with this name
			} else if (typeof query_string[pair[0]] === "string") {
				var arr = [query_string[pair[0]], pair[1]];
				query_string[pair[0]] = arr;
				// If third or later entry with this name
			} else {
				query_string[pair[0]].push(pair[1]);
			}
		}
		return query_string;
	}
	();
	
	if (QueryString.id != null && QueryString != '')
		$("#cotizacion_id").text(QueryString.id);
	
	//Consultar provincias
	cargarProvincias("1");

	cargarTiposConstruccion();
	
	cargarTiposOcupacion();
	
	// eventos
	$("#ubicacion_provincia_1").change(function (e) {
		obtenerCantonPorProvincia('',"1");
	});
	
	cargarPorCotizacionId();
}

function onSelect(){
	initialize();
}

function cargarProvincias(identificador) {
	$("#ubicacion_provincia_"+identificador).empty();
	//Consultar la provincia
	$.ajax({
		url : '../ProvinciaController',
		data : {
			"tipoConsulta" : "encontrarTodos"
		},
		async: false,
		type : 'post',
		datatype : 'json',
		success : function (data) {
			var listadoProvincias = data.listadoProvincia;			
			$("#ubicacion_provincia_"+identificador).append("<option value=''>Seleccione una provincia</option>");
			$.each(listadoProvincias, function (index) {
				$("#ubicacion_provincia_"+identificador).append("<option value='" + listadoProvincias[index].codigo + "'>" + listadoProvincias[index].nombre + "</option>");
			});
		}
	});
}

function cargarTiposConstruccion() {
	$("#tipo_construccion").empty();
	//Consultar la provincia
	$.ajax({
		url : '../TipoConstruccionController',
		data : {
			"tipoConsulta" : "encontrarTodos"
		},
		async: false,
		type : 'post',
		datatype : 'json',
		success : function (data) {
			var listadoTipos = data.listadoTipoConstruccion;			
			$("#tipo_construccion").append("<option value=''>Seleccione un tipo de construcci&oacute;n</option>");
			$.each(listadoTipos, function (index) {
				$("#tipo_construccion").append("<option value='" + listadoTipos[index].codigo + "'>" + listadoTipos[index].nombre + "</option>");
			});
		}
	});
}

function cargarTiposOcupacion() {
	$("#tipo_ocupacion").empty();
	//Consultar la provincia
	$.ajax({
		url : '../TipoOcupacionController',
		data : {
			"tipoConsulta" : "encontrarTodos"
		},
		async: false,
		type : 'post',
		datatype : 'json',
		success : function (data) {
			var listadoTipos = data.listadoTipoOcupacion;			
			$("#tipo_ocupacion").append("<option value=''>Seleccione un tipo de ocupaci&oacute;n</option>");
			$.each(listadoTipos, function (index) {
				$("#tipo_ocupacion").append("<option value='" + listadoTipos[index].codigo + "'>" + listadoTipos[index].nombre + "</option>");
			});
		}
	});
}

function obtenerCantonPorProvincia(seleccionada, codigoDireccion) {
	$("#ubicacion_canton_"+codigoDireccion).empty();
	// Consultar la provincia
	$.ajax({
		url : '../CantonController',
		data : {
			"tipoConsulta" : "encontrarPorProvincia",
			"provincia" : $("#ubicacion_provincia_"+codigoDireccion).val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoCantones = data.cantones;
			$("#ubicacion_canton_"+codigoDireccion).append("<option value=''>Seleccione un Canton</option>");
			$.each(listadoCantones, function(index) {
				var id = undefined;
				if (listadoCantones[index].codigo == undefined) {
					id = listadoCantones[index].id;
				} else {
					id = listadoCantones[index].codigo;
				}
				$("#ubicacion_canton_"+codigoDireccion).append("<option value='" + id + "'>" + listadoCantones[index].nombre + "</option>");
			});
			$("#ubicacion_canton_"+codigoDireccion).val(seleccionada);
		}
	});
}

function limpiarControles(){
	$("#tiene_extintores").prop("checked", false);
	$("#tiene_detector_humo").prop("checked", false);
	$("#tiene_spinklers").prop("checked", false);
	$("#tiene_alarma_monitoreada").prop("checked", false);
	$("#tiene_guardiania").prop("checked", false);
	$("#tiene_otros").val("");
	$("#txt_Latitud").val("");
	$("#txt_Longitud").val("");
	$("#anio_construccion").val("");
}

function obtenerConfiguracionDireccion(numeroDireccion) {
	limpiarControles();
	$("#numeroDireccion").val(numeroDireccion);
	$("#cotizacionDetalleId").val($("#cotizacionDetalleId_"+numeroDireccion).val());
	$.ajax({
		url : '../PymesObjetoCotizacionController',
		data : {
			"tipoConsulta" : "obtenerConfiguracionPorDetalleId",
			"cotizacionDetalleId" : $("#cotizacionDetalleId").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			$("#tipo_construccion").val(data.tipoConstruccionId);
			$("#tipo_ocupacion").val(data.tipoOcupacionId);
			
			var numerictextbox1 = $("#numero_total_pisos").data("kendoNumericTextBox");
			numerictextbox1.value(data.numeroTotalPisos);
			
			$("#tiene_extintores").prop("checked", data.extintores);
			$("#tiene_detector_humo").prop("checked", data.detectorHumo);
			$("#tiene_spinklers").prop("checked", data.sprinklers);
			$("#tiene_alarma_monitoreada").prop("checked", data.alarmaMonitoreada);
			$("#tiene_guardiania").prop("checked", data.guardiania);
			$("#tiene_otros").val(data.otros);
			$("#txt_Latitud").val(data.latitud);
			$("#txt_Longitud").val(data.longuitud);
			
			var numerictextbox2 = $("#anio_construccion").data("kendoNumericTextBox");
			numerictextbox2.value(data.anioConstruccion);
			
			if(data.EstadoInspeccion=="1" || data.EstadoInspeccion=="2"){
				$("#tipo_construccion").attr('disabled','disabled');
				$("#tipo_ocupacion").attr('disabled','disabled');
				$("#tiene_extintores").attr('disabled','disabled');
				$("#tiene_detector_humo").attr('disabled','disabled');
				$("#tiene_spinklers").attr('disabled','disabled');
				$("#tiene_alarma_monitoreada").attr('disabled','disabled');
				$("#tiene_guardiania").attr('disabled','disabled');
				$("#tiene_otros").attr('disabled','disabled');
				$("#txt_Latitud").attr('disabled','disabled');
				$("#txt_Longitud").attr('disabled','disabled');
				$("#anio_construccion").attr('disabled','disabled');
				numerictextbox1.enable(false);
				numerictextbox2.enable(false);
				$("#guardarNoAprobado").hide();
				$("#guardarAprobado").hide();
			}
		}
	});
}

function cargarPorCotizacionId(){
	$.ajax({
				url : '../PymesObjetoCotizacionController',
		data : {
			"tipoConsulta" : "obtenerDireccionesInspeccion",
			"cotizacionId" : $("#cotizacion_id").text()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			//Creo las direcciones configuradas
			var listadoDirecciones = data.direcciones;
			$.each(listadoDirecciones, function(index){
				if(index==0){
					$("#ubicacion_provincia_1").val(listadoDirecciones[index].provinciaId);
					obtenerCantonPorProvincia(listadoDirecciones[index].cantonId, "1");
					$("#cotizacionDetalleId_1").val(listadoDirecciones[index].cotizacionDetalleId);
					$("#ubicacion_calle_principal_1").val(listadoDirecciones[index].callePrincipal);
					$("#ubicacion_numero_1").val(listadoDirecciones[index].numeroDireccion);
					$("#ubicacion_calle_secundaria_1").val(listadoDirecciones[index].calleSecundaria);
				}
				else{
					$("#direcciones").append(
							'<tr>'
							+ '<td>'
							+ '<div class="panel panel-primary">'
							+ '<div class="panel-body">'
							+ '<table>'
							+ '<tr>'
							+ '<td style="width: 10%"><label><b>Provincia:*</b></label></td>'
							+ '<td style="width: 25%"><select id="ubicacion_provincia_'
							+ listadoDirecciones[index].cotizacionDetalleId
							+ '" required="required" class="datosGanadero ubicacionProvincia"></select></td>'
							+ '<td style="width: 5%"><label><b>Cant&oacuten:*</b></label></td>'
							+ '<td style="width: 15%"><select id="ubicacion_canton_'
							+ listadoDirecciones[index].cotizacionDetalleId
							+ '" required="required" class="datosGanadero"></select></td><td></td><td></td>'
							+ '<td style="width: 20%">'
							+ '<button type="button" class="btn btn-success btn-xs actualizar-btn" data-toggle="modal" data-target="#add" onclick="obtenerConfiguracionDireccion('
							+ listadoDirecciones[index].cotizacionDetalleId
							+ ')">'
							+ '<span class="glyphicon glyphicon glyphicon-edit"></span> Informaci&oacute;n para cotizar</button>'
							+ '</td>'
							+ '</tr>'
							+ '<tr>'
							+ '<td><label><b>C. Principal:*</b></label></td>'
							+ '<td><input type="text" id="ubicacion_calle_principal_'
							+ listadoDirecciones[index].cotizacionDetalleId
							+ '" required="required" class="datosGanadero"></input></td>'
							+ '<td><label><b>N&uacutemero:*</b></label></td>'
							+ '<td><input type="text" id="ubicacion_numero_'
							+ listadoDirecciones[index].cotizacionDetalleId
							+ '" required="required" class="datosGanadero"></input></td>'
							+ '<td><label><b>C. Secundaria:*</b></label></td>'
							+ '<td><input type="text" id="ubicacion_calle_secundaria_'
							+ listadoDirecciones[index].cotizacionDetalleId
							+ '" required="required" class="datosGanadero"></input></td>'
							+ '</tr>'
							+ '</table>'
							+ '</div>' + '</div>' + '</td>' + '</td>' + '</tr>');
					$("#contadorDirecciones").val(listadoDirecciones[index].cotizacionDetalleId);
					
					// Consultar provincias
					cargarProvincias(listadoDirecciones[index].cotizacionDetalleId);

					$("#ubicacion_provincia_"+listadoDirecciones[index].cotizacionDetalleId).change(function (e) {
						obtenerCantonPorProvincia('',listadoDirecciones[index].cotizacionDetalleId);
					});
					$("#ubicacion_provincia_"+listadoDirecciones[index].cotizacionDetalleId).val(listadoDirecciones[index].provinciaId);
					obtenerCantonPorProvincia(listadoDirecciones[index].cantonId, listadoDirecciones[index].cotizacionDetalleId);
					$("#cotizacionDetalleId_"+listadoDirecciones[index].cotizacionDetalleId).val(listadoDirecciones[index].cotizacionDetalleId);
					$("#ubicacion_calle_principal_"+listadoDirecciones[index].cotizacionDetalleId).val(listadoDirecciones[index].callePrincipal);
					$("#ubicacion_numero_"+listadoDirecciones[index].cotizacionDetalleId).val(listadoDirecciones[index].numeroDireccion);
					$("#ubicacion_calle_secundaria_"+listadoDirecciones[index].cotizacionDetalleId).val(listadoDirecciones[index].calleSecundaria);
					
				}
			});	
		}
	});
}


function grabarInspeccion(estado){
	$.ajax({
		url : '../PymesObjetoCotizacionController',
		data : {
			"tipoConsulta" : "registrarInspeccion",
			"cotizacionId" : $("#cotizacion_id").text(),
			"cotizacionDetalleId" : $("#cotizacionDetalleId").val(),
			"tipoConstruccionId" : $("#tipo_construccion").val(),
			"tipoOcupacionId" : $("#tipo_ocupacion").val(),
			"numeroTotalPisos" : $("#numero_total_pisos").val(),
			"extintores" : $("#tiene_extintores").is(":checked") ? 1 : 0,
			"detectorHumo" : $("#tiene_extintores").is(":checked") ? 1 : 0,
			"sprinklers" : $("#tiene_spinklers").is(":checked") ? 1 : 0,
			"alarmaMonitoreada" : $("#tiene_alarma_monitoreada").is(":checked") ? 1 : 0,
			"guardiania" : $("#tiene_guardiania").is(":checked") ? 1 : 0,
			"otros" : $("#tiene_otros").val(),
			"anioConstruccion" : $("#anio_construccion").val(),
			"latitud" : $("#txt_Latitud").val(),
			"longuitud" : $("#txt_Longitud").val(),
			"registro" : "",
			"estadoInspeccion" : estado
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			
		}
	});
}