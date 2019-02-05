var tipoObjeto = document.getElementById("tipoObjetoMetodos").getAttribute(
"tipoObjetoValor");

var configCobertasPorProducto;
var configAsistenciasPorProducto;
var usuarioRol;


function formatDollar(num) {
	var numero=parseFloat(num);
	var p = numero.toFixed(2).split(".");
	return "$" + p[0].split("").reverse().reduce(function(acc, numero, i, orig) {
		return numero + (i && !(i % 3) ? "," : "") + acc;
	}, "") + "." + p[1];
}

function onSelect(e) {
	if ($(e.item).find("> .k-link").text().trim() == "Coberturas") {
		if ($("#total_valor_asegurado").val() != ""
			&& $("#total_valor_asegurado").val() > 0) {
			var numerictextbox1 = $("#valor_estructuras").data(
			"kendoNumericTextBox");
			numerictextbox1.enable(false);
			var numerictextbox2 = $("#valor_muebles_enseres").data(
			"kendoNumericTextBox");
			numerictextbox2.enable(false);
			var numerictextbox3 = $("#valor_maquinarias").data(
			"kendoNumericTextBox");
			numerictextbox3.enable(false);
			var numerictextbox4 = $("#valor_mercaderia").data(
			"kendoNumericTextBox");
			numerictextbox4.enable(false);
			var numerictextbox5 = $("#valor_herramientas").data(
			"kendoNumericTextBox");
			numerictextbox5.enable(false);
			var numerictextbox6 = $("#valor_insumos_medicos").data("kendoNumericTextBox");
			numerictextbox6.enable(false);
			$("#ingresarNuevoValores").css("visibility", "visible");
		}
	}
}

function limpiarCoberturas() {
	if (confirm("El cambio de valores afecta a las coberturas ya establecidas. Desea continuar?")) {
		$("#detalle_cobertura_direccion tr").remove();
		$("#detalle_cobertura_general tr").remove();
		$("#detalle_cobertura_adicionales tr").remove();
		$("#deducibles_general tr").remove();
		var numerictextbox1 = $("#valor_estructuras").data("kendoNumericTextBox");
		numerictextbox1.enable(true);
		var numerictextbox2 = $("#valor_muebles_enseres").data("kendoNumericTextBox");
		numerictextbox2.enable(true);
		var numerictextbox3 = $("#valor_maquinarias").data("kendoNumericTextBox");
		numerictextbox3.enable(true);
		var numerictextbox4 = $("#valor_mercaderia").data("kendoNumericTextBox");
		numerictextbox4.enable(true);
		var numerictextbox5 = $("#valor_herramientas").data("kendoNumericTextBox");
		numerictextbox5.enable(true);
		var numerictextbox6 = $("#valor_insumos_medicos").data("kendoNumericTextBox");
		numerictextbox6.enable(true);
		$("#ingresarNuevoValores").css("visibility", "hidden");
	}
}

function obtenerConfiguracionesCoberturaProducto(productoID) {
	$("#coberturas_por_direccion tr").remove();
	$("#coberturas_generales tr").remove();
	$("#coberturas_adicionales tr").remove();
	$.ajax({
		url : '../PymeConfiguracionCoberturaController',
		data : {
			"tipoConsulta" : "encontrarConfiguracionesPorProductoId",
			"grupoPorProductoId" : productoID,
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			configCobertasPorProducto = data.listadoConfiguraciones;
			$.each(configCobertasPorProducto, function(index) {
				if (configCobertasPorProducto[index].tipoDeclaracion == "2") {
					if (configCobertasPorProducto[index].incluyeEnProducto == 1) {
						$("#coberturas_por_direccion")
						.append(
								'<tr><td style="color: orange;"><input type="checkbox"  id="cobertura_'
								+ configCobertasPorProducto[index].configuracionCoberturaId
								+ '" class="datosPymes" checked disabled>'
								+ configCobertasPorProducto[index].coberturaNombre
								+ '</td></tr>');
					} else {
						$("#coberturas_por_direccion")
						.append(
								'<tr><td style="color: green;"><input type="checkbox"  id="cobertura_'
								+ configCobertasPorProducto[index].configuracionCoberturaId
								+ '" class="datosPymes coberturaPorDireccion" onclick=crearCobertura('
								+ configCobertasPorProducto[index].configuracionCoberturaId
								+ ',"direccion"'
								+ ')>'
								+ configCobertasPorProducto[index].coberturaNombre
								+ '</td></tr>');
					}

				} else {
					if (configCobertasPorProducto[index].tipoCoberturaId == 1) {
						if (configCobertasPorProducto[index].incluyeEnProducto == 1) {
							$("#coberturas_generales")
							.append(
									'<tr><td style="color: orange;"><input type="checkbox"  id="cobertura_'
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ '" class="datosPymes" checked disabled>'
									+ configCobertasPorProducto[index].coberturaNombre
									+ '</td></tr>');
						} else {
							$("#coberturas_generales")
							.append(
									'<tr><td style="color: green;"><input type="checkbox"  id="cobertura_'
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ '" class="datosPymes" onclick=crearCobertura('
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ ',"general"'
									+ ')>'
									+ configCobertasPorProducto[index].coberturaNombre
									+ '</td></tr>');
						}
					} else {
						if (configCobertasPorProducto[index].tipoTasa == 3) {
							$("#coberturas_adicionales")
							.append(
									'<tr><td style="color: blue;"><input type="checkbox"  id="cobertura_'
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ '" class="datosPymes" checked disabled>'
									+ configCobertasPorProducto[index].coberturaNombre
									+ '</td></tr>');
						} else {
							$("#coberturas_adicionales")
							.append(
									'<tr><td style="color: green;"><input type="checkbox"  id="cobertura_'
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ '" class="datosPymes" onclick=crearCobertura('
									+ configCobertasPorProducto[index].configuracionCoberturaId
									+ ',"adicionales"'
									+ ')>'
									+ configCobertasPorProducto[index].coberturaNombre
									+ '</td></tr>');
						}
					}

				}
			});
		}
	});
}

function crearCobertura(configuracionCoberturaId, tipo) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configCobertasPorProducto).filter(function(idx) {
		return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
	});
	if (filterResult.length > 0) {
		if ($("#cobertura_" + configuracionCoberturaId).is(':checked')) {
			var valorMaximo = determinarValorMaximo(configuracionCoberturaId);
			var tasa = determinarTasa(configuracionCoberturaId);
			var clase = filterResult[0].incluyeEnProducto == "1" ? "predeterminado" : "normal";
			$("#detalle_cobertura_" + tipo)
			.append(
					'<tr class="'
					+ clase
					+ '" id="fila_config_cobertura_'
					+ configuracionCoberturaId
					+ '">'
					+ '<td style="width: 50%">'
					+ '<b>'
					+ filterResult[0].coberturaNombre
					+ '</b>'
					+ '<div class="panel panel-primary">'
					+ '<table style="width: 100%" cellpadding="3">'
					+ '<tr>'
					+ '<td><label class="porcentajeTasasPymes" id="lbl_tasa_sugerida_'
					+ configuracionCoberturaId
					+ '">Tasa Sugerida:</label></td>'
					+ '<td><input type="text" style="width: 70px" disabled id="tasa_sugerida_'
					+ configuracionCoberturaId
					+ '" class="datosPymes porcentajeTasasPymes"></td>'
					+ '<td><label class="porcentajeTasasPymes" id="lbl_tasa_ingresada_'
					+ configuracionCoberturaId
					+ '">Tasa Ingresada:</label></td>'
					+ '<td><input type="text" style="width: 70px" id="tasa_ingresada_'
					+ configuracionCoberturaId
					+ '" class="datosPymes porcentajeTasasPymes" onchange="calcularPrimaNeta('
					+ configuracionCoberturaId
					+ ')"></td>'
					+ '<td>Monto Asegurado:</td>'
					+ '<td><input type="text" style="width: 100px" id="monto_asegurado_'
					+ configuracionCoberturaId
					+ '" class="datosPymes" onchange="calcularPrimaNeta('
					+ configuracionCoberturaId
					+ ')"></td>'
					+ '<td>Prima Neta:</td>'
					+ '<td><input type="text" style="width: 100px" id="prima_neta_'
					+ configuracionCoberturaId
					+ '" class="datosPymes primaNetaCobertura" disabled></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td colspan="6" style="color: #FF0000; font-size: x-small;">'
					+ '<label id="advertencia_'
					+ configuracionCoberturaId + '"></label>'
					+ '</td>' + '</tr>' + '</table>' + '</div>'
					+ '</td>' + '</tr>');
			if (typeof filterResult[0].textoDeducible !== "undefined") {
				$("#deducibles_general").append(
						'<tr id="deducible_cobertura_'
						+ configuracionCoberturaId
						+ '">'
						+ '<td><table><tr><td style="font-weight:bold">Deducible:&nbsp;'
						+ filterResult[0].coberturaNombre
						+ '<input type="hidden" class="deducibleIDs" id="deducible_cobertura_'+configuracionCoberturaId
						+ '" value="'+configuracionCoberturaId
						+ '"></td></tr><tr>'
						+ '<td><textarea id="text_area_deducible_cobertura_'
						+ configuracionCoberturaId
						+ '" rows="2" cols="100">'
						+ filterResult[0].textoDeducible
						+ '</textarea></td></tr></table></td></tr>');
			}
			$("#tasa_sugerida_" + configuracionCoberturaId).val(tasa);
			$("#tasa_ingresada_" + configuracionCoberturaId).val(tasa);

			if (valorMaximo != -1 && valorMaximo != 0) {
				if (tipo == "direccion") {
					$("#monto_asegurado_" + configuracionCoberturaId).kendoNumericTextBox({
						max : valorMaximo						
					});
				}
				$("#advertencia_" + configuracionCoberturaId).html(
						"Monto asegurado m&aacute;ximo para esta cobertura: "
						+ formatDollar(valorMaximo));
			} else {
				$("#monto_asegurado_" + configuracionCoberturaId).kendoNumericTextBox();
			}

		} else {
			$("#fila_config_cobertura_" + configuracionCoberturaId).remove();
			$("#deducible_cobertura_" + configuracionCoberturaId).remove();
		}
		if($("#usuarioRol").val()=="0"){
			$(".porcentajeTasasPymes").each(function() {
				//$(this).prop('hidden', 'hidden');
				$(this).css('display','none');
			});
		}		
	}
}


function cargarCobertura(configuracionCoberturaId, tipo, tasaSugerida, tasaIngresada, montoAsegurado, valorPrima) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configCobertasPorProducto)
	.filter(
			function(idx) {
				return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
			});
	if (filterResult.length > 0) {
		$("#cobertura_" + configuracionCoberturaId).attr('checked', true);
		if(Number(tasaSugerida)>0){
			var valorMaximo = determinarValorMaximo(configuracionCoberturaId);
			var clase = filterResult[0].incluyeEnProducto == "1" ? "predeterminado" : "normal";
			$("#detalle_cobertura_" + tipo)
			.append(
					'<tr class="'
					+ clase
					+ '" id="fila_config_cobertura_'
					+ configuracionCoberturaId
					+ '">'
					+ '<td style="width: 50%">'
					+ '<b>'
					+ filterResult[0].coberturaNombre
					+ '</b>'
					+ '<div class="panel panel-primary">'
					+ '<table style="width: 100%" cellpadding="3">'
					+ '<tr>'
					+ '<td><label class="porcentajeTasasPymes" id="lbl_tasa_sugerida_'
					+ configuracionCoberturaId
					+ '">Tasa Sugerida:</label></td>'
					+ '<td><input type="text" style="width: 70px" disabled id="tasa_sugerida_'
					+ configuracionCoberturaId
					+ '" class="datosPymes porcentajeTasasPymes"></td>'
					+ '<td><label class="porcentajeTasasPymes" id="lbl_tasa_ingresada_'
					+ configuracionCoberturaId
					+'">Tasa Ingresada:</label></td>'	
					+ '<td><input type="text" style="width: 70px" id="tasa_ingresada_'
					+ configuracionCoberturaId
					+ '" class="datosPymes porcentajeTasasPymes" onchange="calcularPrimaNeta('
					+ configuracionCoberturaId
					+ ')"></td>'
					+ '<td>Monto Asegurado:</td>'
					+ '<td><input type="text" style="width: 100px" id="monto_asegurado_'
					+ configuracionCoberturaId
					+ '" class="datosPymes" onchange="calcularPrimaNeta('
					+ configuracionCoberturaId
					+ ')"></td>'
					+ '<td>Prima Neta:</td>'
					+ '<td><input type="text" style="width: 100px" id="prima_neta_'
					+ configuracionCoberturaId
					+ '" class="datosPymes primaNetaCobertura" disabled></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td colspan="6" style="color: #FF0000; font-size: x-small;">'
					+ '<label id="advertencia_'
					+ configuracionCoberturaId + '"></label>'
					+ '</td>' + '</tr>' + '</table>' + '</div>'
					+ '</td>' + '</tr>');
			if (valorMaximo != -1 && valorMaximo != 0) {
				if (tipo == "direccion") {
					$("#monto_asegurado_" + configuracionCoberturaId).kendoNumericTextBox({
						max : valorMaximo
					});
				}
				$("#advertencia_" + configuracionCoberturaId).html(
						"Monto asegurado m&aacute;ximo para esta cobertura: "
						+ formatDollar(valorMaximo));
			} else {
				$("#monto_asegurado_" + configuracionCoberturaId)
				.kendoNumericTextBox();
			}
			//Seteo los valores guardados en base
			$("#tasa_sugerida_" + configuracionCoberturaId).val(tasaSugerida);
			$("#tasa_ingresada_" + configuracionCoberturaId).val(tasaIngresada);
			if (tipo == "direccion"){
				var numerictextbox6 = $("#monto_asegurado_" + configuracionCoberturaId).data("kendoNumericTextBox");
				numerictextbox6.value(montoAsegurado);
			}
			else{
				$("#monto_asegurado_" + configuracionCoberturaId).val(montoAsegurado);
			}

			$("#prima_neta_" + configuracionCoberturaId).val(valorPrima);
			//Oculto las tasas si es necesario
			if($("#usuarioRol").val()=="0"){
				$(".porcentajeTasasPymes").each(function() {
					//$(this).prop('hidden', 'hidden');
					$(this).css('display','none');
				});
			}
		}
	}
}

function cerrarAlertFichaPymeError(){
	$("#msgPopupFichaPymeError").hide();
}

function cerrarAlertFichaInspeccionError(){
	$("#msgPopupFichaInspeccionError").hide();
}

function mostrarResumen(){
	$.ajax({
		url : '../PymesObjetoCotizacionController',
		data : {
			"tipoConsulta" : "obtenerResumenValores",
			"cotizacionId" : $("#cotizacion_id").text(),
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			$("#resumen_inspeccion_total_suma_asegurada").val(formatDollar(data.valorAsegurado));
			$("#resumen_inspeccion_prima_sin_impuestos").val(formatDollar(data.valorPrima));
			$("#resumen_inspeccion_super_bancos").val(formatDollar(data.valorSuperBancos));
			if (parseFloat(data.porcentajeDescuento) > 0)
				$("#resumen_inspeccion_filaDescuento").show();
			$("#resumen_inspeccion_porcentaje_descuento").val(formatDollar(data.porcentajeDescuento));
			$("#resumen_inspeccion_seguro_campesino").val(formatDollar(data.valorSeguroCampesino));
			$("#resumen_inspeccion_derecho_emision").val(formatDollar(data.valorDerechosEmision));
			$("#resumen_inspeccion_subtotal").val(formatDollar(data.valorSubTotal));
			$("#resumen_inspeccion_iva").val(formatDollar(data.valorIva));
			$("#resumen_inspeccion_total").val(formatDollar(data.valorTotal));
			$("#resumen_total_suma_asegurada").val(formatDollar(data.valorAsegurado));
			$("#resumen_prima_sin_impuestos").val(formatDollar(data.valorPrima));
			$("#resumen_super_bancos").val(formatDollar(data.valorSuperBancos));
			if (parseFloat(data.porcentajeDescuento) > 0)
				$("#resumen_filaDescuento").show();
			$("#resumen_porcentaje_descuento").val(formatDollar(data.porcentajeDescuento));
			$("#resumen_seguro_campesino").val(formatDollar(data.valorSeguroCampesino));
			$("#resumen_derecho_emision").val(formatDollar(data.valorDerechosEmision));
			$("#resumen_subtotal").val(formatDollar(data.valorSubTotal));
			$("#resumen_iva").val(formatDollar(data.valorIva));
			$("#resumen_total").val(formatDollar(data.valorTotal));
		}
	});
}

function cargarDeducible(configuracionCoberturaId, textDeducible) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configCobertasPorProducto)
	.filter(
			function(idx) {
				return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
			});
	if (filterResult.length > 0) {
		$("#deducibles_general").append(
				'<tr id="deducible_cobertura_'
				+ configuracionCoberturaId
				+ '">'
				+ '<td><table><tr><td style="font-weight:bold">Deducible:&nbsp;'
				+ filterResult[0].coberturaNombre
				+ '<input type="hidden" class="deducibleIDs" id="deducible_cobertura_'+configuracionCoberturaId
				+ '" value="'+configuracionCoberturaId
				+ '"></td></tr><tr>'
				+ '<td><textarea id="text_area_deducible_cobertura_'
				+ configuracionCoberturaId
				+ '" rows="2" cols="100">'
				+ textDeducible
				+ '</textarea></td></tr></table></td></tr>');
	}
}

function cargarAsistencia(asistenciaId, valor) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configAsistenciasPorProducto).filter(
			function(idx) {
				return configAsistenciasPorProducto[idx].asistenciaId === asistenciaId;
			});
	if (filterResult.length > 0) {
		$("#asistencia_" + asistenciaId).attr('checked', true);
		$("#valor_asistencia_" + asistenciaId).val(valor);
		if (filterResult[0].esPredeterminada == 1)
			$("#valor_asistencia_" + asistenciaId).hide();
		else
			$("#valor_asistencia_" + asistenciaId).show();

	}
}

function obtenerAsistenciasPorProducto(productoID) {
	$("#asistencias_generales tr").remove();
	$.ajax({
		url : '../PymeAsistenciaController',
		data : {
			"tipoConsulta" : "buscarPorProductoId",
			"grupoPorProductoId" : productoID,
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			configAsistenciasPorProducto = data.asistenciaJSONArray;
			$.each(configAsistenciasPorProducto,
					function(index) {
				if (configAsistenciasPorProducto[index].esPredeterminada == 1) {
					$("#asistencias_generales")
					.append(
							'<tr><td style="color: orange;"><input type="checkbox"  id="asistencia_'
							+ configAsistenciasPorProducto[index].asistenciaId
							+ '" class="datosPymes" checked disabled>'
							+ configAsistenciasPorProducto[index].nombre
							+ '</td>'
							+ '<td><input type="text" id="valor_asistencia_'
							+ configAsistenciasPorProducto[index].asistenciaId
							+ '" disabled class="datosPymes" style="width: 80px; display: none;" value="'
							+ configAsistenciasPorProducto[index].valor
							+ '">'
							+ '</td></tr>');
				} else {
					$("#asistencias_generales")	.append(
							'<tr><td style="color: green;"><input type="checkbox"  id="asistencia_'
							+ configAsistenciasPorProducto[index].asistenciaId
							+ '" class="datosPymes" onclick=crearAsistencias('
							+ configAsistenciasPorProducto[index].asistenciaId
							+ ')>'
							+ configAsistenciasPorProducto[index].nombre
							+ '</td>'
							+ '<td><input type="text" id="valor_asistencia_'
							+ configAsistenciasPorProducto[index].asistenciaId
							+ '" disabled class="datosPymes" style="width: 80px; display: none;" value="'
							+ configAsistenciasPorProducto[index].valor
							+ '">'
							+ '</td></tr>');
				}
			});
		}
	});
}

function crearAsistencias(asistenciaId) {
	if ($("#asistencia_" + asistenciaId).is(':checked'))
		$("#valor_asistencia_" + asistenciaId).show();
	else
		$("#valor_asistencia_" + asistenciaId).hide();
}

function determinarTasa(configuracionCoberturaId) {
	var filterResult = $(configCobertasPorProducto)
	.filter(
			function(idx) {
				return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
			});
	var total = $("#total_valor_asegurado").val();
	if (filterResult.length > 0) {
		if (filterResult[0].tipoTasa == "1") // Tipo de tasa SIMPLE
		{
			return Number(filterResult[0].tasa);
		} else if (filterResult[0].tipoTasa == "2") // Tipo de tasa COMPUESTA
		{
			$("#asistencias_generales tr").remove();
			$
			.ajax({
				url : '../PymeConfiguracionCoberturaController',
				data : {
					"tipoConsulta" : "encontrarTasasPorProductoID",
					"configuracionCoberturaId" : configuracionCoberturaId,
				},
				async : false,
				type : 'post',
				datatype : 'json',
				success : function(data) {
					var listadoTasas = data.listadoTasas;
					$
					.each(
							listadoTasas,
							function(index) {
								if (Number(total) > Number(listadoTasas[index].valorCoberturaInicial)
										&& Number(total) <= Number(listadoTasas[index].valorCoberturaInicial)) {
									return Number(listadoTasas[index].tasa);
								}
							});
				}
			});
			return Number(0);
		} else if (filterResult[0].tipoTasa == "3") // Tipo de tasa SIN COSTO
		{
			return Number(0);
		} else // Tipo de tasa COPIADA
		{
			if ($("#tasa_ingresada_" + filterResult[0].coberturaCopiaId)) {
				return Number($("#tasa_ingresada_"
						+ filterResult[0].coberturaCopiaId));
			}
		}
	}
}

function determinarValorMaximo(configuracionCoberturaId, valorMaximo) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configCobertasPorProducto)
	.filter(
			function(idx) {
				return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
			});
	if (filterResult.length > 0) {
		if (filterResult[0].origenValorLimiteAsegurado == "1") // SUMA VALORES
		{
			// var valorCalculado=Number($("#total_valor_asegurado").val());
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				return Number(filterResult[0].valorMaximoLimiteAsegurado);
			// if(valorCalculado>limite)
			// return limite;
			// else
			// return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "2") // SOLO
			// ESTRUCTURA
		{
			var valorCalculado = 0;
			var valorBase = Number($("#valor_estructuras").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "3") // SOLO
			// CONTENIDO
		{
			var valorCalculado = 0;
			var valorBase = Number($("#valor_muebles_enseres").val())
			+ Number($("#valor_maquinarias").val())
			+ Number($("#valor_mercaderia").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "4") // SOLO
			// MAQUINARIA
		{
			var valorCalculado = 0;
			var valorBase = Number($("#valor_muebles_enseres").val())
			+ Number($("#valor_maquinarias").val())
			+ Number($("#valor_mercaderia").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "5") // SOLO
			// MERCADERIA
		{
			var valorCalculado = 0;
			var valorBase = Number($("#valor_mercaderia").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "6") // SOLO
			// INSUMOS
			// MÉDICOS
			// - NO
			// ELÉCTRICOS
		{
			var valorCalculado = 0;
			var valorBase = Number($("#valor_insumos_medicos").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "7") // TOTAL
			// DE
			// INCENDIO
		{
			var valorCalculado = 0;
			var valorBase = Number($("#total_valor_asegurado").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		} else if (filterResult[0].origenValorLimiteAsegurado == "8") // TOTAL
			// DE
			// ROBO
		{
			var valorCalculado = 0;
			if ($("#monto_asegurado_8")) {
				var valorBase = Number($("#total_valor_asegurado").val());
				if (filterResult[0].porcentajeLimiteAsegurado != "") {
					var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
					valorCalculado = valorBase * porcentaje / 100;
				}
				var limite = 0;
				if (filterResult[0].valorMaximoLimiteAsegurado != "")
					limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
				if (valorCalculado > limite)
					return limite;
				else
					return valorCalculado;
			}
		} else if (filterResult[0].origenValorLimiteAsegurado == "9") // NO
			// APLICA
		{
			return -1;
		} else if (filterResult[0].origenValorLimiteAsegurado == "10") // VALOR
		{
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				return Number(filterResult[0].valorMaximoLimiteAsegurado);
		} else if (filterResult[0].origenValorLimiteAsegurado == "11") // VALOR
		{
			var valorCalculado = 0;
			var valorBase = Number($("#total_valor_asegurado").val());
			if (filterResult[0].porcentajeLimiteAsegurado != "") {
				var porcentaje = Number(filterResult[0].porcentajeLimiteAsegurado);
				valorCalculado = valorBase * porcentaje / 100;
			}
			var limite = 0;
			if (filterResult[0].valorMaximoLimiteAsegurado != "")
				limite = Number(filterResult[0].valorMaximoLimiteAsegurado);
			if (valorCalculado > limite)
				return limite;
			else
				return valorCalculado;
		}
	}
}

function calcularPrimaNeta(configuracionCoberturaId) {
	// Filtro en el array de cobertura el id seleccionado
	var filterResult = $(configCobertasPorProducto)
	.filter(
			function(idx) {
				return configCobertasPorProducto[idx].configuracionCoberturaId === configuracionCoberturaId;
			});
	// Calculo el total neto de la prima en la cobertura seleccionada
	var p1 = Number($("#tasa_ingresada_" + configuracionCoberturaId).val());
	var p2 = Number($("#monto_asegurado_" + configuracionCoberturaId).val());
	$("#prima_neta_" + configuracionCoberturaId).val(p1 * p2);

	// Calculo total de la prima de esta direccion
	var valorTotal = 0;
	$(".primaNetaCobertura").each(function() {
		valorTotal = valorTotal + Number($(this).val());
	});
	$("#prima_neta_por_direccion").text(formatDollar(valorTotal));
}

function calcularTotalValorAsegurado() {
	var p1 = Number($("#valor_estructuras").val());
	var p2 = Number($("#valor_muebles_enseres").val());
	var p3 = Number($("#valor_maquinarias").val());
	var p4 = Number($("#valor_mercaderia").val());
	var p5 = Number($("#valor_herramientas").val());
	var p6 = Number($("#valor_insumos_medicos").val());

	var numerictextbox = $("#total_valor_asegurado")
	.data("kendoNumericTextBox");
	numerictextbox.value(p1 + p2 + p3 + p4 + p5 + p6);

	$(".predeterminado").each(function() {
		$(this).remove();
	});

	// Busco todas las coberturas que son predeterminadas ára generarlas
	// automáticamente
	var filterResult = $(configCobertasPorProducto).filter(function(idx) {
		return configCobertasPorProducto[idx].incluyeEnProducto == "1";
	});
	$
	.each(
			filterResult,
			function(index) {
				$(
						"#deducible_cobertura_"
						+ filterResult[index].configuracionCoberturaId)
						.remove();
				if (filterResult[index].tipoDeclaracion == "2") {
					if (Number(filterResult[index].tasa) > 0) {
						crearCobertura(
								filterResult[index].configuracionCoberturaId,
								"direccion");
						var numerictextbox = $(
								"#monto_asegurado_"
								+ filterResult[index].configuracionCoberturaId)
								.data("kendoNumericTextBox");
						numerictextbox.value(Number($(
						"#total_valor_asegurado").val()));
						numerictextbox.enable(numerictextbox.element
								.is(':disabled'));
						calcularPrimaNeta(filterResult[index].configuracionCoberturaId);
					} else {
						if (typeof filterResult[index].textoDeducible !== "undefined") {
							$("#deducibles_general")
							.append(
									'<tr id="deducible_cobertura_'
									+ filterResult[index].configuracionCoberturaId
									+ '">'
									+ '<td><table><tr><td style="font-weight:bold">Deducible:&nbsp;'
									+ filterResult[index].coberturaNombre
									+ '<input type="hidden" class="deducibleIDs" id="deducible_cobertura_'+filterResult[index].configuracionCoberturaId
									+ '" value="'+filterResult[index].configuracionCoberturaId
									+ '"></td></tr><tr>'
									+ '<td><textarea id="text_area_deducible_cobertura_'
									+ filterResult[index].configuracionCoberturaId
									+ '" rows="2" cols="100">'
									+ filterResult[index].textoDeducible
									+ '</textarea></td></tr></table></td></tr>');
						}
					}
				} else {
					if (Number(filterResult[index].tasa) > 0) {
						crearCobertura(
								filterResult[index].configuracionCoberturaId,
								"general");
						var numerictextbox = $(
								"#monto_asegurado_"
								+ filterResult[index].configuracionCoberturaId)
								.data("kendoNumericTextBox");
						numerictextbox.value(Number($(
						"#total_valor_asegurado").val()));
						numerictextbox.enable(numerictextbox.element
								.is(':disabled'));
						calcularPrimaNeta(filterResult[index].configuracionCoberturaId);
					} else {
						if (typeof filterResult[index].textoDeducible !== "undefined") {
							$("#deducibles_general")
							.append(
									'<tr id="deducible_cobertura_'
									+ filterResult[index].configuracionCoberturaId
									+ '">'
									+ '<td><table><tr><td style="font-weight:bold">Deducible:&nbsp;'
									+ filterResult[index].coberturaNombre
									+ '<input type="hidden" class="deducibleIDs" id="deducible_cobertura_'+filterResult[index].configuracionCoberturaId
									+ '" value="'+filterResult[index].configuracionCoberturaId
									+ '"></td></tr><tr>'
									+ '<td><textarea id="text_area_deducible_cobertura_'
									+ filterResult[index].configuracionCoberturaId
									+ '" rows="2" cols="100">'
									+ filterResult[index].textoDeducible
									+ '</textarea></td></tr></table></td></tr>');
						}
					}
				}
			});

}

function grabarDireccion() {
	var coberturas = new Array();
	var contador = 0;
	var cotizacionId = $("#cotizacion_id").html().trim();
	$.each(configCobertasPorProducto,
			function(index) {
		if(configCobertasPorProducto[index].tipoDeclaracion=="2"){
			if ($("#cobertura_"+ configCobertasPorProducto[index].configuracionCoberturaId).is(':checked')) {
				var tasaSugerida = 0;
				var tasaIngresada = 0;
				var montoAsegurado = 0;
				var primaCalculada = 0;
				if ($("#tasa_sugerida_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					tasaSugerida = Number($("#tasa_sugerida_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#tasa_ingresada_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					tasaIngresada = Number($("#tasa_ingresada_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#monto_asegurado_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					montoAsegurado = Number($("#monto_asegurado_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#prima_neta_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					primaCalculada = Number($("#prima_neta_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				var cobertura = {
						configuracionCoberturaId : configCobertasPorProducto[index].configuracionCoberturaId,
						tasaSugerida : tasaSugerida,
						tasaIngresada : tasaIngresada,
						valorIngresado : montoAsegurado,
						primaCalculada : primaCalculada,
						tipoOrigen : "POR DIRECCION",
						textoDeducible : ""
				};
				coberturas[contador] = cobertura;
				contador++;
			}
		}
	});
	var direccionActualId = $("#numeroDireccion").val();
	$.ajax({
		url : '../PymesObjetoCotizacionController',

		data : {
			"tipoConsulta" : "crearDireccion",
			"cotizacionId" : cotizacionId,
			"cotizacionDetalleId" : $("#cotizacionDetalleId").val(),
			"provinciaId" : $("#ubicacion_provincia_" + direccionActualId).val(),
			"cantonId" : $("#ubicacion_canton_" + direccionActualId).val(),
			"callePrincipal" : $("#ubicacion_calle_principal_" + direccionActualId).val(),
			"numeroDireccion" : $("#ubicacion_numero_" + direccionActualId).val(),
			"calleSecundaria" : $("#ubicacion_calle_secundaria_" + direccionActualId).val(),
			"actividadEconomicaId" : $("#actividad_economica").val(),
			"tieneMasDosAnios" : $("#tiene_mas_dos_anios_si").is(":checked") ? "true" : "false",
			"contabilidadFormal" : $("#contabilidad_formal_si").is(":checked") ? "true" : "false",
			"requiereInspeccion" : $("#requiere_inspeccion_si").is(":checked") ? "true" : "false",
			"sector" : $("#ubicacion_sector").val(),
			"nombreEdificio" : $("#ubicacion_nombre_edificio").val(),
			"numeroOficina" : $("#ubicacion_numero_oficina").val(),
			"valorEstructuras" : $("#valor_estructuras").val(),
			"valorMueblesEnseres" : $("#valor_muebles_enseres").val(),
			"valorMaquinaria" : $("#valor_maquinarias").val(),
			"valorMercaderia" : $("#valor_mercaderia").val(),
			"valorInsumosNoelectronicos" : $("#valor_insumos_medicos").val(),
			"valorEquipoHerramienta" : $("#valor_herramientas").val(),
			"primaNeta" : $("#prima_neta_por_direccion").text(),
			"coberturas" : JSON.stringify(coberturas)
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			$("#cotizacionDetalleId_" + direccionActualId).val(data.cotizacionDetalleId);
		}
	});
}

function verificarPuntosVenta(puntoVentaId) {

	var puntoId = "";
	if (puntoVentaId != null && puntoVentaId != '')
		puntoId = puntoVentaId;
	else
		puntoId = $('#punto_venta').val();
	$.ajax({
		url : '../PuntoVentaController',
		data : {
			"tipoConsulta" : "verificacionPuntoVenta",
			"tipoObjeto" : tipoObjeto,
			"puntoVentaId" : puntoId,

		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			if (data.punto_venta.agente_id == null
					|| data.punto_venta.agente_id == "") {
				$('#agentes').prop("disabled", false);
				$("#agentes option:first").attr('selected', 'selected');
			} else {

				$('#agentes option[value=' + data.punto_venta.agente_id + ']')
				.attr('selected', 'selected');
				// $('#agentes').val(data.punto_venta.agente_id);
				$('#agentes').prop("disabled", true);
			}
		}
	});
}



/* METODOS DE CALCULOS */
function calcularIngresosEgresosNatural() {

	var salario = Number($("#salario_mensual_natural").val());
	var otrosIngresos = Number($("#otros_ingresos_natural").val());
	var egresos = Number($("#egresos_natural").val());

	$("#ingresos_egresos_natural").val((salario + otrosIngresos) - egresos);

}

function calcularPatrimonioNatural() {

	var activos = Number($("#activos_natural").val());
	var pasivos = Number($("#pasivos_natural").val());

	$("#patrimonio_natural").val(activos - pasivos);

}

function calcularIngresosEgresosJuridica() {

	var salario = Number($("#salario_mensual_juridica").val());
	var otrosIngresos = Number($("#otros_ingresos_juridica").val());
	var egresos = Number($("#egresos_juridica").val());

	$("#ingresos_egresos_juridica").val((salario + otrosIngresos) - egresos);

}

function calcularPatrimonioJuridica() {

	var activos = Number($("#activos_juridica").val());
	var pasivos = Number($("#pasivos_juridica").val());

	$("#patrimonio_juridica").val(activos - pasivos);

}

/** METODOS PARA LA DESCARGA DE LOS REPORTES* */
function cambioDescargaCertificados() {

	var valor = $("#selectDescargas").val();
	if (valor == 1) {
		$(".descargaCertificado").fadeOut("slow");
		$("#descargar_FichaCotizacion").fadeIn("slow");
	}
	if (valor == 2) {
		$(".descargaCertificado").fadeOut("slow");
		$("#descargar_certificadoCotizacion").fadeIn("slow");
	}
	if (valor == 3) {
		$(".descargaCertificado").fadeOut("slow");
		$("#descargar_certificadoNormasParticulares").fadeIn("slow");

	}
	/*
	 * if (valor == 4) { $(".descargaCertificado").fadeOut("slow");
	 * $("#descargar_certificadoUPLA").fadeIn("slow"); } if (valor == 5) {
	 * $(".descargaCertificado").fadeOut("slow");
	 * $("#descargar_certificadoPoliza").fadeIn("slow"); }
	 */

}

function abrirFichaCotizacion() {
	var cotizacion = $("#cotizacion_id").html().trim();

	var parametros = {
			"parametros" : {
				"cot_Id" : cotizacion
			},
			"pathReporte" : "/static/reportes/Ganadero/cabecera.jasper"
	};
	abrirReporte('POST', '../ReportesController', parametros, "_blank");
}

function abrirCertificadoCotizacion() {
	var cotizacion = $("#cotizacion_id").html().trim();

	var parametros = {
			"parametros" : {
				"Cot_Id" : cotizacion
			},
			"pathReporte" : "/static/reportes/Ganadero/CotiVacas2KR2012.jasper"
	};
	abrirReporte('POST', '../ReportesController', parametros, "_blank");
}

function abrirCertificadoNormaParticulares() {
	var cotizacion = $("#cotizacion_id").html().trim();

	var parametros = {
			"parametros" : {
				"cot_Id" : cotizacion
			},
			"pathReporte" : "/static/reportes/Ganadero/CondicionesParticulares.jasper"
	};
	abrirReporte('POST', '../ReportesController', parametros, "_blank");
}
/*
 * METODO QUE MUESTRA O ESCONDE LOS CAMPOS DEL FORMULARIO DE LA UPLA DEPENDIENDO
 * DEL TIPO DE IDENTIFICACION QUE SELECCIONE EL USUARIO
 */
function mostrarTipoIdentificacionSolicitante() {
	var tipoIdentificacionId = $("#tipo_identificacion_principal").val();
	if (tipoIdentificacionId == '' || tipoIdentificacionId == '1'
		|| tipoIdentificacionId == '2' || tipoIdentificacionId == '3') {
		$("#nombre_completo").val("");
	} else {
		$("#nombres").val("");
		$("#apellidos").val("");
	}

	if (tipoIdentificacionId == '' || tipoIdentificacionId == '1'
		|| tipoIdentificacionId == '3' || tipoIdentificacionId == '4') {
		$("#identificacion").attr("onkeypress",
		"validarKeyPress(event,/[0-9]/);");

	} else {
		$("#identificacion").attr("onkeypress",
		"validarSoloLetrasNumeros(event);");
	}
}

function mostrarTipoIdentificacionAsegurado() {
	var tipoIdentificacionId = $("#tipo_identificacion_asegurado").val();
	if (tipoIdentificacionId == '' || tipoIdentificacionId == '1'
		|| tipoIdentificacionId == '2' || tipoIdentificacionId == '3') {
		$("#nombre_completo_asegurado").val("");
		$("#ubicacionPersonaNatural").show();
		$("#datosPoliticaNatural").show();
		$("#situacionFinancieraNatural").show();
		$("#ubicacionPersonaJuridica").hide();
		$("#datosPoliticaJuridica").hide();
		$("#situacionFinancieraJuridica").hide();
	} else {
		$("#nombres_asegurado").val("");
		$("#apellidos_asegurado").val("");
		$("#ubicacionPersonaNatural").hide();
		$("#datosPoliticaNatural").hide();
		$("#situacionFinancieraNatural").hide();
		$("#ubicacionPersonaJuridica").show();
		$("#datosPoliticaJuridica").show();
		$("#situacionFinancieraJuridica").show();
	}

	if (tipoIdentificacionId == '' || tipoIdentificacionId == '1'
		|| tipoIdentificacionId == '3' || tipoIdentificacionId == '4') {
		$("#identificacion_asegurado").attr("onkeypress",
		"validarKeyPress(event,/[0-9]/);");

	} else {
		$("#identificacion_asegurado").attr("onkeypress",
		"validarSoloLetrasNumeros(event);");
	}
}

function cargarPestanaEndosoBeneficiario(identificacion, monto, beneficiario) {
	cargarTiposIdentificacionPymes("", "asegurado", false);
	if (identificacion == null || identificacion == "") {
		$("#identificacion_asegurado").val($("#identificacion").val()).trigger(
		'change');
	} else {
		$("#identificacion_asegurado").val(identificacion);
		cargarPorIdentificacion("datosAsegurado", identificacion);
	}

	if (monto == null || monto == "") {
		$("#valor_endoso_beneficiario").val(0);
	} else {
		$("#valor_endoso_beneficiario").val(monto);
	}
	if (beneficiario != null && beneficiario != '') {
		$('#beneficiario').select2('val', beneficiario);
	}
}

/*
 * METODO QUE RECIBE EL ID DEL PUNTO DE VENTA Y SETEA EL CORRESPONDIENTE AL ID
 * QUE RECIBE.
 */
function obtenerPuntosVentaPorProducto(seleccionada, productoActualID) {
	var productoid = "";
	if (productoActualID != null && productoActualID != '')
		productoid = productoActualID;
	else
		productoid = $('#productos').val();
	$
	.ajax({
		url : '../PuntoVentaController',
		data : {
			"tipoConsulta" : "puntosVentaXProducto",
			"tipoObjeto" : tipoObjeto,
			"grupoPorProductoId" : productoid,

		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var sucursales = data.sucursales;
			var options = '';
			options = '<option value="">Seleccione un punto de venta</option>';
			var contador;
			for (var j = 0; j < sucursales.length; j++) {
				contador = 0;
				for (var i = 0; i < data.puntosVenta.length; i++) {
					if (data.puntosVenta[i].sucursal == sucursales[j].id) {
						contador++;
						if (contador == 1) {
							$("#productos")
							.append(
							"<option value=''>Seleccione una opcion</option>");
							options += '<option value="" disabled="disabled" class="seleccionado" ">'
								+ sucursales[j].nombre
								+ '</option>';
						}
						options += '<option value="'
							+ data.puntosVenta[i].id
							+ '" pxpv="'
							+ data.puntosVenta[i].productoPorPuntoDeVenta
							+ '"  >&nbsp;&nbsp;&nbsp;&nbsp;'
							+ data.puntosVenta[i].nombre
							+ '</option>';
					}
				}
			}
			$("#punto_venta").html(options);
			if (seleccionada != '') {
				$("#punto_venta").val(seleccionada);
				verificarPuntosVenta(seleccionada);
			}
		}
	});
}

function obtenerProductosPorGrupoProducto(producto) {
	// Consultar listado de productos dentro de un grupos de productos
	$("#productos").empty();

	$
	.ajax({
		url : '../GrupoPorProductoController',

		data : {
			"tipoConsulta" : "encontrarTodosPorGrupo",
			"tipoObjeto" : tipoObjeto,
			"grupoProductoId" : $("#grupo_productos").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoGrupos = data.listadoGruposPorProducto;
			$("#productos").append(
			"<option value=''>Seleccione una opcion</option>");
			$
			.each(
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
			if (producto != '') {
				$("#productos").val(producto);
				obtenerConfiguracionesCoberturaProducto(producto);
				obtenerAsistenciasPorProducto(producto);
				obtenerPuntosVentaPorProducto($(
						"#puntoVentaSeleccionado").val(), producto);
				configurarValoresMostrarPorProducto(producto);
			}
		}
	});
}

function configurarValoresMostrarPorProducto(productoId) {
	$.ajax({
		url : '../PymeParametroXGrupoPorProductoController',
		data : {
			"tipoConsulta" : "obtenerPorProductoId",
			"parametroGrupoProductoId" : productoId
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			if (data.mostrarValorEstructuras == "1")
				$("#fila_estructuras").show();
			if (data.mostrarValorMueblesEnseres == "1")
				$("#fila_muebles_enseres").show();
			if (data.mostrarMaquinaria == "1")
				$("#fila_maquinaria").show();
			if (data.mostrarValorMercaderia == "1")
				$("#fila_mercaderia").show();
			if (data.mostrarValorInsumos == "1")
				$("#fila_insumos_medicos").show();
			if (data.mostrarValorEquipoHerramienta == "1")
				$("#fila_equipos_herramienta").show();

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

function obtenerParroquiaPorCanton(seleccionada) {
	$("#ubicacion_parroquia1").empty();
	$.ajax({
		url : '../ParroquiaController',
		data : {
			"tipoConsulta" : "encontrarPorCanton",
			"canton" : $("#ubicacion_canton1").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoParroquias = data.listadoParroquia;
			$("#ubicacion_parroquia1").append(
			"<option value=''>Seleccione una Parroquia</option>");
			$.each(listadoParroquias, function(index) {
				var id = undefined;
				if (listadoParroquias[index].codigo == undefined) {
					id = listadoParroquias[index].id;
				} else {
					id = listadoParroquias[index].codigo;
				}

				$("#ubicacion_parroquia1")
				.append(
						"<option value='" + id + "'>"
						+ listadoParroquias[index].nombre
						+ "</option>");
			});
			$("#ubicacion_parroquia1").val(seleccionada);
		}
	});
}

function obtenerCiudadesPorProvinciaPNatural(seleccionada) {
	$("#ciudad_direccion_cliente_natural").empty();
	$.ajax({
		url : '../CiudadController',
		data : {
			"tipoConsulta" : "encontrarPorProvincia",
			"provincia" : $("#provincia_direccion_cliente_natural").val()
		},
		type : 'post',
		datatype : 'json',
		success : function(datos) {

			var listadoCiudades = datos.ciudades;
			$("#ciudad_direccion_cliente_natural").append(
			"<option value=''>Seleccione una Ciudad</option>");
			$.each(listadoCiudades, function(index) {
				var id = undefined;
				if (listadoCiudades[index].codigo == undefined) {
					id = listadoCiudades[index].id;
				} else {
					id = listadoCiudades[index].codigo;
				}

				$("#ciudad_direccion_cliente_natural").append(
						"<option value='" + id + "'>"
						+ listadoCiudades[index].nombre + "</option>");
			});
			$("#ciudad_direccion_cliente_natural").val(seleccionada);
		}
	});
}

function obtenerCiudadesPorProvinciaPJuridica(seleccionada) {
	$("#ciudad_direccion_matriz_juridica").empty();
	$.ajax({
		url : '../CiudadController',
		data : {
			"tipoConsulta" : "encontrarPorProvincia",
			"provincia" : $("#provincia_direccion_matriz_juridica").val()
		},
		type : 'post',
		datatype : 'json',
		success : function(datos) {

			var listadoCiudades = datos.ciudades;
			$("#ciudad_direccion_matriz_juridica").append(
			"<option value=''>Seleccione una Ciudad</option>");
			$.each(listadoCiudades, function(index) {
				var id = undefined;
				if (listadoCiudades[index].codigo == undefined) {
					id = listadoCiudades[index].id;
				} else {
					id = listadoCiudades[index].codigo;
				}

				$("#ciudad_direccion_matriz_juridica").append(
						"<option value='" + id + "'>"
						+ listadoCiudades[index].nombre + "</option>");
			});
			$("#ciudad_direccion_matriz_juridica").val(seleccionada);
		}
	});
}

function obtenerCantonPorProvinciaPNatural(seleccionada) {
	$("#canton_direccion_cliente_natural").empty();
	// Consultar la provincia
	$.ajax({
		url : '../CantonController',
		data : {
			"tipoConsulta" : "encontrarPorProvincia",
			"provincia" : $("#provincia_direccion_cliente_natural").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoCantones = data.cantones;
			$("#canton_direccion_cliente_natural").append(
			"<option value=''>Seleccione un Canton</option>");
			$.each(listadoCantones, function(index) {
				var id = undefined;
				if (listadoCantones[index].codigo == undefined) {
					id = listadoCantones[index].id;
				} else {
					id = listadoCantones[index].codigo;
				}

				$("#canton_direccion_cliente_natural").append(
						"<option value='" + id + "'>"
						+ listadoCantones[index].nombre + "</option>");
			});
			$("#canton_direccion_cliente_natural").val(seleccionada);
		}
	});
}

function obtenerCantonPorProvinciaPJuridica(seleccionada) {
	$("#canton_direccion_matriz_juridica").empty();
	// Consultar la provincia
	$.ajax({
		url : '../CantonController',
		data : {
			"tipoConsulta" : "encontrarPorProvincia",
			"provincia" : $("#provincia_direccion_matriz_juridica").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoCantones = data.cantones;
			$("#canton_direccion_matriz_juridica").append(
			"<option value=''>Seleccione un Canton</option>");
			$.each(listadoCantones, function(index) {
				var id = undefined;
				if (listadoCantones[index].codigo == undefined) {
					id = listadoCantones[index].id;
				} else {
					id = listadoCantones[index].codigo;
				}

				$("#canton_direccion_matriz_juridica").append(
						"<option value='" + id + "'>"
						+ listadoCantones[index].nombre + "</option>");
			});
			$("#canton_direccion_matriz_juridica").val(seleccionada);
		}
	});
}

function obtenerParroquiaPorCantonPNatural(seleccionada) {
	$("#parroquia_direccion_cliente_natural").empty();
	$.ajax({
		url : '../ParroquiaController',
		data : {
			"tipoConsulta" : "encontrarPorCanton",
			"canton" : $("#canton_direccion_cliente_natural").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoParroquias = data.listadoParroquia;
			$("#parroquia_direccion_cliente_natural").append(
			"<option value=''>Seleccione una Parroquia</option>");
			$.each(listadoParroquias, function(index) {
				var id = undefined;
				if (listadoParroquias[index].codigo == undefined) {
					id = listadoParroquias[index].id;
				} else {
					id = listadoParroquias[index].codigo;
				}

				$("#parroquia_direccion_cliente_natural")
				.append(
						"<option value='" + id + "'>"
						+ listadoParroquias[index].nombre
						+ "</option>");
			});
			$("#parroquia_direccion_cliente_natural").val(seleccionada);
		}
	});
}

function obtenerParroquiaPorCantonPJuridica(seleccionada) {
	$("#parroquia_direccion_matriz_juridica").empty();
	$.ajax({
		url : '../ParroquiaController',
		data : {
			"tipoConsulta" : "encontrarPorCanton",
			"canton" : $("#canton_direccion_matriz_juridica").val()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			var listadoParroquias = data.listadoParroquia;
			$("#parroquia_direccion_matriz_juridica").append(
			"<option value=''>Seleccione una Parroquia</option>");
			$.each(listadoParroquias, function(index) {
				var id = undefined;
				if (listadoParroquias[index].codigo == undefined) {
					id = listadoParroquias[index].id;
				} else {
					id = listadoParroquias[index].codigo;
				}

				$("#parroquia_direccion_matriz_juridica")
				.append(
						"<option value='" + id + "'>"
						+ listadoParroquias[index].nombre
						+ "</option>");
			});
			$("#parroquia_direccion_matriz_juridica").val(seleccionada);
		}
	});
}

function cambiaZonaDireccion(event, tipo) {
	var target = event.target || event.srcElement;
	var seleccionada = $(target).val();
	if (seleccionada == "U") {
		if (tipo == 'N') {
			$("#ciudad_cliente_label").fadeIn();
			$("#ciudad_cliente_select").fadeIn();
			$("#canton_cliente_label").fadeOut();
			$("#canton_cliente_select").fadeOut();
			$("#parroquia_cliente_label").fadeOut();
			$("#parroquia_cliente_select").fadeOut();
		}
		if (tipo == 'J') {
			$("#ciudad_matriz_label").fadeIn();
			$("#ciudad_matriz_select").fadeIn();
			$("#canton_matriz_label").fadeOut();
			$("#canton_matriz_select").fadeOut();
			$("#parroquia_matriz_label").fadeOut();
			$("#parroquia_matriz_select").fadeOut();
		}
	}
	if (seleccionada == "R") {
		if (tipo == 'N') {
			$("#canton_cliente_label").fadeIn();
			$("#canton_cliente_select").fadeIn();
			$("#parroquia_cliente_label").fadeIn();
			$("#parroquia_cliente_select").fadeIn();
			$("#ciudad_cliente_label").fadeOut();
			$("#ciudad_cliente_select").fadeOut();
		}
		if (tipo == 'J') {
			$("#canton_matriz_label").fadeIn();
			$("#canton_matriz_select").fadeIn();
			$("#parroquia_matriz_label").fadeIn();
			$("#parroquia_matriz_select").fadeIn();
			$("#ciudad_matriz_label").fadeOut();
			$("#ciudad_matriz_select").fadeOut();
		}
	}
}

function limpiarControles(){

	$("#detalle_cobertura_direccion tr").remove();
	$("#actividad_economica").val();
	$("#tiene_mas_dos_anios").prop("checked",false);
	$("#contabilidad_formal").prop("checked",false);
	$("#ubicacion_sector").val("");
	$("#ubicacion_nombre_edificio").val("");
	$("#ubicacion_numero_oficina").val("");

	$("#prima_neta_por_direccion").text("");

	var numerictextbox1 = $("#valor_estructuras").data("kendoNumericTextBox");
	numerictextbox1.value(null);
	numerictextbox1.enable(true);

	var numerictextbox2 = $("#valor_muebles_enseres").data("kendoNumericTextBox");
	numerictextbox2.value(null);
	numerictextbox2.enable(true);

	var numerictextbox3 = $("#valor_maquinarias").data("kendoNumericTextBox");
	numerictextbox3.value(null);
	numerictextbox3.enable(true);

	var numerictextbox4 = $("#valor_mercaderia").data("kendoNumericTextBox");
	numerictextbox4.value(null);
	numerictextbox4.enable(true);

	var numerictextbox5 = $("#valor_insumos_medicos").data("kendoNumericTextBox");
	numerictextbox5.value(null);
	numerictextbox5.enable(true);

	var numerictextbox6 = $("#valor_herramientas").data("kendoNumericTextBox");
	numerictextbox6.value(null);
	numerictextbox6.enable(true);

	var numerictextbox = $("#total_valor_asegurado").data("kendoNumericTextBox");
	numerictextbox.value(null);

	$("#ingresarNuevoValores").css("visibility", "hidden");


	$(".coberturaPorDireccion").each(function() {
		$(this).prop("checked",false);
	});
}

function obtenerConfiguracionDireccion(numeroDireccion) {
	limpiarControles();	
	$("#numeroDireccion").val(numeroDireccion);
	$("#cotizacionDetalleId").val($("#cotizacionDetalleId_"+numeroDireccion).val());
	if($("#cotizacionDetalleId").val()!=""){
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
				$("#actividad_economica").val(data.actividadEconomicaId);
				if(data.tieneMasDosAnios)
					$("#tiene_mas_dos_anios_si").attr('checked', true);
				else
					$("#tiene_mas_dos_anios_no").attr('checked', true);
				if(data.contabilidadFormal)
					$("#contabilidad_formal_si").attr('checked', true);
				else
					$("#contabilidad_formal_no").attr('checked', true);
				if(data.requiereInspeccion)
					$("#requiere_inspeccion_si").attr('checked', true);
				else
					$("#requiere_inspeccion_no").attr('checked', true);
				$("#ubicacion_sector").val(data.sector);
				$("#ubicacion_nombre_edificio").val(data.nombreEdificio);
				$("#ubicacion_numero_oficina").val(data.numeroOficina);

				var numerictextbox1 = $("#valor_estructuras").data("kendoNumericTextBox");
				numerictextbox1.value(data.valorEstructuras);
				numerictextbox1.enable(false);

				var numerictextbox2 = $("#valor_muebles_enseres").data("kendoNumericTextBox");
				numerictextbox2.value(data.valorMueblesEnseres);
				numerictextbox2.enable(false);

				var numerictextbox3 = $("#valor_maquinarias").data("kendoNumericTextBox");
				numerictextbox3.value(data.valorMaquinaria);
				numerictextbox3.enable(false);

				var numerictextbox4 = $("#valor_mercaderia").data("kendoNumericTextBox");
				numerictextbox4.value(data.valorMercaderia);
				numerictextbox4.enable(false);

				var numerictextbox5 = $("#valor_insumos_medicos").data("kendoNumericTextBox");
				numerictextbox5.value(data.valorInsumosNoelectronicos);
				numerictextbox5.enable(false);

				var numerictextbox6 = $("#valor_herramientas").data("kendoNumericTextBox");
				numerictextbox6.value(data.valorEquipoHerramienta);
				numerictextbox6.enable(false);

				$("#ingresarNuevoValores").css("visibility", "visible");

				var p1 = Number($("#valor_estructuras").val());
				var p2 = Number($("#valor_muebles_enseres").val());
				var p3 = Number($("#valor_maquinarias").val());
				var p4 = Number($("#valor_mercaderia").val());
				var p5 = Number($("#valor_herramientas").val());
				var p6 = Number($("#valor_insumos_medicos").val());

				var numerictextbox = $("#total_valor_asegurado").data("kendoNumericTextBox");
				numerictextbox.value(p1 + p2 + p3 + p4 + p5 + p6);

				var coberturasConfiguradas= data.coberturas;
				$.each(coberturasConfiguradas, function(index) {
					cargarCobertura(coberturasConfiguradas[index].configuracionCoberturaId, 
							"direccion", 
							coberturasConfiguradas[index].tasaSugerida, 
							coberturasConfiguradas[index].tasaIngresada, 
							coberturasConfiguradas[index].valorIngresado, 
							coberturasConfiguradas[index].primaCalculada);
				});

				// Calculo total de la prima de esta direccion
				var valorTotal = 0;
				$(".primaNetaCobertura").each(function() {
					valorTotal = valorTotal + Number($(this).val());
				});
				$("#prima_neta_por_direccion").text(formatDollar(valorTotal));
			}
		});
	}
}

function agregarNuevaDireccion() {

	var numeroDirecciones = parseInt($("#contadorDirecciones").val())
	+ parseInt(1);

	$("#direcciones")
	.append(
			'<tr>'
			+ '<td>'
			+ '<div class="panel panel-primary">'
			+ '<div class="panel-body">'
			+ '<table>'
			+ '<tr>'
			+ '<td style="width: 10%"><label><b>Provincia:*</b></label></td>'
			+ '<td style="width: 25%"><select id="ubicacion_provincia_'
			+ numeroDirecciones
			+ '" required="required" class="datosPymes ubicacionProvincia"></select></td>'
			+ '<td style="width: 5%"><label><b>Cant&oacuten:*</b></label></td>'
			+ '<td style="width: 15%"><select id="ubicacion_canton_'
			+ numeroDirecciones
			+ '" required="required" class="datosPymes"></select></td><td></td><td></td>'
			+ '<td style="width: 20%">'
			+ '<button type="button" class="btn btn-success btn-xs actualizar-btn" data-toggle="modal" data-target="#add" onclick="obtenerConfiguracionDireccion('
			+ numeroDirecciones
			+ ')">'
			+ '<span class="glyphicon glyphicon glyphicon-edit"></span> Informaci&oacute;n para cotizar</button>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td><label><b>C. Principal:*</b></label></td>'
			+ '<td><input type="text" id="ubicacion_calle_principal_"'
			+ numeroDirecciones
			+ ' required="required" class="datosPymes"></input></td>'
			+ '<td><label><b>N&uacutemero:*</b></label></td>'
			+ '<td><input type="text" id="ubicacion_numero_"'
			+ numeroDirecciones
			+ ' required="required" class="datosPymes"></input></td>'
			+ '<td><label><b>C. Secundaria:*</b></label></td>'
			+ '<td><input type="text" id="ubicacion_calle_secundaria_"'
			+ numeroDirecciones
			+ ' required="required" class="datosPymes"></input></td>'
			+ '<td style="width: 20%" class="sorting">'
			+ '<button type="button" class="btn btn-danger btn-xs eliminar-extra-btn">'
			+ ' <span class="glyphicon glyphicon glyphicon-remove"></span> Eliminar'
			+ ' </button><input type="hidden" id="cotizacionDetalleId_'+numeroDirecciones
			+ '" value=""></td>'
			+ '</tr>'
			+ '</table>'
			+ '</div>' + '</div>' + '</td>' + '</td>' + '</tr>');
	$("#contadorDirecciones").val(numeroDirecciones);

	$(".eliminar-extra-btn").bind(
			{
				click : function() {
					$(this).parent().parent().parent().parent().parent()
					.parent().parent().remove();
					numeroDirecciones = parseInt($("#contadorDirecciones")
							.val())
							- parseInt(1);
					$("#contadorDirecciones").val(numeroDirecciones);
				}
			});

	// Consultar provincias
	cargarProvincias(numeroDirecciones);

	$("#ubicacion_provincia_"+numeroDirecciones).change(function (e) {
		obtenerCantonPorProvincia('',numeroDirecciones);
	});
}

function verificarEstadoInspeccion(){
	var resultado=false;
	$.ajax({
		url : '../PymeCotizacionController',
		data : {
			"tipoConsulta" : "encontrarPorId",
			"id" : $("#cotizacion_id").text()
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			if (data.success) {
				var datosCotizacion = data.datosCotizacion;
				if(datosCotizacion.estadoCotizacion!="Pendiente"){
					$("#lblContenidoMensajeInspeccion").html("No puede continuar, porque debe aprobar la inspecci&oacute;n.");
					$("#msgPopupFichaInspeccionError").show();
					resultado = false;
				}
				else
					resultado = true;
			}
		}
	});
	return resultado;
}

function cambiarEstadoCotizacion(estado) {
	$.ajax({
		url : '../PymeCotizacionController',
		data : {
			"tipoConsulta" : "cambiarEstado",
			"cotizacionId" : $("#cotizacion_id").text(),
			"estadoNombre" : estado,
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			$("#cotizacion_id").text(data.cotizacionId);
			// Validacion poner en la URL el id de la cotizacion
			var valorId = getParameterByName("id");
			if (valorId != null) {
				$.pushVar("id", data.cotizacionId, "", window.location.href);
				if (estado == "Denegado")
					$("#msgPopupEmitidoRechazado").show();
				else if(estado == "Pendiente de Inspeccion"){
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$("#msgInspeccionAprobada").show();
				}
				else 
				{
					$("#msgPopupFinalizadoCorrectamente").show();
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$("#filaEmitirCotizacion").hide();
					$("#datosFinales").hide();
					$("#filaEnviarCotizacion").hide();
				}
			}
		}
	});
}

function procesarReporte() {
	/*$.ajax({
		url : '../PymeCotizacionController',
		data : {
			"tipoConsulta" : "generarReporte",
			"cotizacionId" : $("#cotizacion_id").text(),
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			
		}
	});*/
	
	window.open('../PymeCotizacionController?tipoConsulta=generarReporte&cotizacionId=' + $("#cotizacion_id").text());
}

function emitirPoliza() {
	$("#msgPopupEmitidoCorrectamente").hide();
	if ($("#fecha_inicio_vigencia").val() == "") {
		alert("Debe ingresar la fecha en la que desea que se emita la poliza.");
	} else {
		$.ajax({
			url : '../PymesObjetoCotizacionController',
			data : {
				"tipoConsulta" : "emitirPoliza",
				"cotizacionId" : $("#cotizacion_id").text(),
				"fechaInicioVigencia" : $("#fecha_inicio_vigencia").val(),
			},
			async : false,
			type : 'post',
			datatype : 'json',
			success : function(data) {
				$("#cotizacion_id").text(data.cotizacionId);
				$("#msgPopupEmitidoCorrectamente").show();
				// Validacion poner en la URL el id de la cotizacion
				/*var valorId = getParameterByName("id");
				if (valorId != null) {
					$
					.pushVar("id", data.cotizacionId, "",
							window.location.href);
					$("#msgPopupEmitidoCorrectamente").show();
				}*/
			}
		});
	}
}

function cargarDireccionFactura(formulario, datos) {
	if (datos != null) {
		if (formulario == "solicitante") {
			if ($("#tipo_identificacion_principal").val() == "4") {
				$("#nombre_direccion_solicitante").val(
						$("#nombre_completo").val());
			} else {
				$("#nombre_direccion_solicitante").val(
						$("#nombres").val() + " " + $("#apellidos").val());
			}
			$("#cedula_direccion_solicitante").val($("#identificacion").val());
			$("#telefono_direccion_solicitante").val(datos.telefono);
			$("#celular_direccion_solicitante").val(datos.celular);
			$("#mail_direccion_solicitante").val(datos.email);
			options = '';
		}
		if (formulario == "asegurado") {
			$("#tipo_identificacion_asegurado").val(datos.tipoIdentificacion);
			$("#nombres_asegurado").val(datos.nombre);
			$("#apellidos_asegurado").val(datos.apellido);
			$("#identificacion_asegurado").val(datos.identificacion);
			if (datos.tipoIdentificacion == "4") {
				$("#nombre_direccion_asegurado").val(datos.nombreCompleto);
				$("#filaJuridicaAsegurado").show();
				$("#filaNaturalAsegurado").hide();

			} else {
				$("#nombre_direccion_asegurado").val(
						datos.nombre + " " + datos.apellido);
				$("#filaNaturalAsegurado").show();
				$("#filaJuridicaAsegurado").hide();
			}

			if (datos.zona == "1") {
				$("#zona_direccion_asegurado").val("U");
				if (datos.tipoIdentificacion == "4") {
					$("#provincia_direccion_matriz_juridica").val(
							datos.provincia);
					obtenerCiudadesPorProvinciaPJuridica(datos.ciudad);
				} else {
					$("#provincia_direccion_cliente_natural").val(
							datos.provincia);
					obtenerCiudadesPorProvinciaPNatural(datos.ciudad);
				}
				$("#fila_ciudad_direccion_asegurado").fadeIn("slow");
				$("#fila_canton_direccion_asegurado").fadeOut("slow");
				$("#fila_parroquia_direccion_asegurado").fadeOut("slow");
				$("#principal_direccion_asegurado").val(datos.callePrincipal);
				$("#secundaria_direccion_asegurado").val(datos.calleSecundaria);
				$("#numero_direccion_asegurado").val(datos.numero);
				$("#referencia_direccion_asegurado").val(datos.datosReferencia);
			}
			if (datos.zona == "2") {
				$("#zona_direccion_asegurado").val("R");
				if (datos.tipoIdentificacion == "4") {
					// cargarProvinciasPJuridica(datos.provincia,
					// "direccion_asegurado");
					$("#provincia_direccion_matriz_juridica").val(
							datos.provincia);
					obtenerCantonPorProvinciaPJuridica(datos.canton);
					obtenerParroquiaPorCantonPJuridica(datos.parroquia);
				} else {
					$("#provincia_direccion_cliente_natural").val(
							datos.provincia);
					obtenerCantonPorProvinciaPNatural(datos.canton);
					obtenerParroquiaPorCantonPNatural(datos.parroquia);
				}
				// cargarProvincias(datos.provincia, "direccion_asegurado");
				// cargarCantones(datos.canton, datos.provincia,
				// "direccion_asegurado");
				$("#fila_ciudad_direccion_asegurado").fadeOut("slow");
				$("#fila_canton_direccion_asegurado").fadeIn("slow");
				cargarParroquias(datos.parroquia, datos.canton,
				"direccion_asegurado");
				$("#fila_parroquia_direccion_asegurado").fadeIn("slow");
				$("#principal_direccion_asegurado").val(datos.callePrincipal);
				$("#secundaria_direccion_asegurado").val(datos.calleSecundaria);
				$("#numero_direccion_asegurado").val(datos.numero);
				$("#referencia_direccion_asegurado").val(datos.datosReferencia);
			}
		}
	}
	if ((datos == null || datos == "")
			&& (formulario == "" || formulario == null)) {
		var options = '<option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>';
		options += '<option value="U">&nbsp;&nbsp;Urbana </option>';
		options += '<option value="R">&nbsp;&nbsp;Rural </option>';
		$("#zona_direccion_asegurado").html(options);
		$("#zona_direccion_solicitante").html(options);

		cargarProvincias("", "direccion_asegurado");
		cargarProvincias("", "direccion_solicitante");
	}
	if ((datos == null || datos == "") && (formulario == "solicitante")) {
		var options = '<option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>';
		options += '<option value="U">&nbsp;&nbsp;Urbana </option>';
		options += '<option value="R">&nbsp;&nbsp;Rural </option>';
		$("#zona_direccion_solicitante").html(options);

		cargarProvincias("", "direccion_solicitante");
	}
	if ((datos == null || datos == "") && (formulario == "asegurado")) {
		var options = '<option value="">&nbsp;&nbsp;&nbsp;&nbsp;Seleccione&nbsp;&nbsp;&nbsp;&nbsp;</option>';
		options += '<option value="U">&nbsp;&nbsp;Urbana </option>';
		options += '<option value="R">&nbsp;&nbsp;Rural </option>';
		$("#zona_direccion_asegurado").html(options);

		cargarProvincias("", "direccion_asegurado");
	}
}

function cambioFormaPago(valor, limpiar) {
	$("#tarjetaNumero").next().next().hide();
	$("#msgPopupPago").fadeOut("slow");
	var formaPago = valor;

	if (limpiar) {
		limpiaForm(forma_debitos);
		limpiaForm(forma_tarjeta);
		limpiaForm(forma_cuotas);
	}

	if (formaPago == '1') {
		$('#forma_tarjeta').fadeOut("slow");
		$('#forma_cuotas').fadeOut("slow");
		$('#forma_debitos').fadeOut("slow");
		$("#save-pagoContado").show();
	}

	if (formaPago == '2') { //Corresponde a Debitos Bancos
		$("#detallePagoCuotas").empty();
		$("#rowDetallePagos").hide();
		$("#save-pagoContado").hide();
		$('#forma_tarjeta').fadeOut("slow");
		$('#forma_cuotas').fadeOut("slow");
		$('#forma_debitos').fadeIn("slow");
		//var fecha=new Date();
		//$("#txtfechaInicialpago").attr('');

	}

	if (formaPago == '3') { //Corresponde a Debitos Tarjetas
		$("#detallePagoCuotas").empty();
		$("#rowDetallePagos").hide();
		$("#save-pagoContado").hide();
		$('#forma_cuotas').fadeOut("slow");
		$('#forma_debitos').fadeOut("slow");
		$('#forma_tarjeta').fadeIn("slow");
	}

	if (formaPago == '4') { //Corresponde a Cuotas
		$("#save-pagoContado").hide();
		$('#forma_tarjeta').fadeOut("slow");
		$('#forma_debitos').fadeOut("slow");
		$('#forma_cuotas').fadeIn("slow");
	}
}

function validarValoresPagos(tipo) {
	var formulario = $("#cboFpFormaPago").val();
	var cuotaInicial;
	var numCuotas;

	if (formulario != "1" || tipo != null) {
		if (formulario == "4" || tipo == "CREDITO CUOTAS") {
			cuotaInicial = $("#txtcuotaInicial");
			numCuotas = $("#cboFpPlazo");
		}
		if (formulario == "2" || tipo == "DEBITO BANCARIO") {
			cuotaInicial = $("#txtcuotaInicialbancoPlazo");
			numCuotas = $("#bancoPlazo");
		}
		if (formulario == "3" || tipo == "DEBITO TARJETA") {
			cuotaInicial = $("#txtcuotaInicialtarjetaPlazo");
			numCuotas = $("#tarjetaPlazo");
		}

		var valorCuotaInicial = Number($(cuotaInicial).val());
		var valorNumCuotas = Number($(numCuotas).val());
		var valor = $("#total").val();
		if (valorNumCuotas == 12) {
			valor = valor * parseFloat(1.0842);
		}
		var valorRestante = valor - valorCuotaInicial;
		var valorCuotas = parseFloat(valorRestante).toFixed(2) / parseInt(valorNumCuotas);

		if (valorCuotaInicial > valor && valor!=0) {
			alert("El valor de la cuota inicial no puede ser mayor al valor del pago!");
			$(cuotaInicial).val(0);

		}
		if (valorCuotaInicial > 0 && valorCuotaInicial < 50) {
			alert("El valor minimo de la cuota inicial es $50");
			$(cuotaInicial).val(0);

		}
		if (valorCuotaInicial == 0) {
			var slctCts = $(numCuotas).attr("id");
			var opcion = $("#" + slctCts + " option[value='9']");
			var clase = $(opcion).attr("class");
			if ($("#" + slctCts + " option[value='10']").length == 0)
				$(opcion).after("<option value='10' class='" + clase + "'>10 meses</option>");
			if (valorCuotas < 50) {
				valorNumCuotas = Math.floor(valorRestante / 50) == 0 ? 1 : Math.floor(valorRestante / 50);
				valorCuotas = parseFloat(valorRestante).toFixed(2) / parseInt(valorNumCuotas);
				$(numCuotas).val(valorNumCuotas); //.trigger("change");
				alert("Las cuotas mensuales no pueden ser menores a $50. Usted puede pagar la diferencia en m&aacute;ximo " + valorNumCuotas + " cuotas");
			}
		}
		if (valorCuotaInicial >= 50 && valorCuotaInicial < valor) {
			var slctCts = $(numCuotas).attr("id");
			$("#" + slctCts + " option[value='10']").remove();
			if (valorCuotas < 50) {
				valorNumCuotas = Math.floor(valorRestante / 50) == 0 ? 1 : Math.floor(valorRestante / 50);
				valorNumCuotas = valorNumCuotas == 10 ? 9 : valorNumCuotas;
				$(numCuotas).val(valorNumCuotas); //.trigger("change");
				alert("Las cuotas mensuales no pueden ser menores a $50. Usted puede pagar la diferencia en m&aacute;ximo " + valorNumCuotas + " cuotas");
			}
			valorNumCuotas = valorNumCuotas == 10 ? 9 : valorNumCuotas;
			$(numCuotas).val(valorNumCuotas); //.trigger("change");
		}
	}
	calcularValoresCuotas();
}

function validarCuotaMinima(event) {
	var target = event.target || event.srcElement;
	var aux = $(target).attr('id');
	var cuotaInicial = $("#txtcuotaInicial" + aux.replace("txtcuotaInicial", "")).val();
	var numCuotas = $(target).val();
	var valor = $("#total").val();
	var valorRestante = valor - cuotaInicial;
	var valorCuotas = parseFloat(valorRestante).toFixed(2) / parseInt(numCuotas);

	if (valorCuotas < 50) {
		numCuotas = Math.floor(valorRestante / 50) == 0 ? 1 : Math.floor(valorRestante / 50);
		valorCuotas = parseFloat(valorRestante).toFixed(2) / parseInt(numCuotas);
		$(target).val(numCuotas); //.trigger("change");
		alert("Las cuotas mensuales no pueden ser menores a $50. Usted puede pagar la diferencia en maximo " + numCuotas + " cuotas");
		$("#" + aux + " option[value='10']").remove();
		var opcion = $("#" + id + " option[value='9']");
		var clase = $(opcion).attr("class");
		$(opcion).after("<option value='10' class='" + clase + "'>10 meses</option>");
		calcularValoresCuotas();
	} else
		$(target).parent().parent().prev().children().last().children().first().trigger("change");
}

function validarCuotaInicial(event) {
	$("#detallePagoCuotas").empty();
	var target = event.target || event.srcElement;
	var id = $(target).attr("id");
	var slctCts = id.replace("txtcuotaInicial", "");
	if ($(target).val() == "")
		$(target).val(0);
	if ($(target).val() < 50 && $(target).val() != 0) {
		alert("El valor minimo de la cuota inicial es $50");
		$(target).val(0);

		var opcion = $("#" + slctCts + " option[value='9']");
		var clase = $(opcion).attr("class");
		if ($("#" + id + " option[value='10']").length == 0)
			$(opcion).after("<option value='10' class='" + clase + "'>10 meses</option>");
	} else {
		if ($(target).val() != 0)
			$("#" + slctCts + " option[value='10']").remove();
		else {
			var opcion = $("#" + slctCts + " option[value='9']");
			var clase = $(opcion).attr("class");
			if ($("#" + id + " option[value='10']").length == 0)
				$(opcion).after("<option value='10' class='" + clase + "'>10 meses</option>");
		}
		$(target).parent().parent().next().children().last().children().first().trigger("change");
		calcularValoresCuotas();

	}
}

function calcularValoresCuotas() {
	var formulario = $("#cboFpFormaPago").val();
	var cuotaInicial = "";
	var numeroCuotas = "";
	var valorTotal = $("#total").val();
	var diferencia;
	var valorCuotas;

	if (formulario != "1") {
		if (formulario == "4") {
			cuotaInicial = $("#txtcuotaInicial").val();
			numeroCuotas = $("#cboFpPlazo").val();
		}
		if (formulario == "2") {
			cuotaInicial = $("#txtcuotaInicialbancoPlazo").val();
			numeroCuotas = $("#bancoPlazo").val();
		}
		if (formulario == "3") {
			cuotaInicial = $("#txtcuotaInicialtarjetaPlazo").val();
			numeroCuotas = $("#tarjetaPlazo").val();
		}
		cuotaInicial = Number(cuotaInicial);
		numeroCuotas = Number(numeroCuotas);
		if (numeroCuotas == 12) {
			valorTotal = valorTotal * parseFloat(1.0842);
		}
		diferencia = Number(valorTotal - cuotaInicial);
		valorCuotas = parseFloat(diferencia / numeroCuotas).toFixed(2);

		var mensaje = "El valor total a pagar es $" + parseFloat(valorTotal).toFixed(2);
		mensaje += " en " + numeroCuotas + " cuotas de $" + valorCuotas;
		if (cuotaInicial > 0)
			mensaje += ", cuota inicial de $" + cuotaInicial;

		if (numeroCuotas == 1)
			mensaje = mensaje.replace("cuotas", "cuota");
		if (numeroCuotas != 0)
			$("#msgPopupPago").attr("class", "alert alert-info").html(mensaje).fadeIn();
	}
}

function cargarFormasPagoGanadero(seleccionada) {
	$("#rowDetallePagos").hide();
	/*
	 * $.ajax({ url : '../FormaPagoController', data : { "tipoConsulta" :
	 * "encontrarTodos", }, type : 'post', datatype : 'json', success : function
	 * (data) { var listadoFormaPago = data.listadoFormaPago; var options = '';
	 * $.each(listadoFormaPago, function(index){ options += '<option value="' +
	 * listadoFormaPago[index].codigo + '">' + listadoFormaPago[index].nombre + '</option>';
	 * }); $("#cboFpFormaPago").empty().append(options);
	 * 
	 * if (seleccionada > 0){ cambioFormaPago(seleccionada, false);
	 * $("#cboFpFormaPago").val(seleccionada); }
	 * 
	 * for(i=2014; i<=2019; i++) $("#tarjetaAnioExp").append('<option value="' +
	 * i + '">' + i + '</option>'); } });
	 */

	$
	.ajax({
		url : '../InstitucionFinancieraController',
		data : {
			"tipoConsulta" : "encontrarTodos",
		},
		type : 'post',
		datatype : 'json',
		success : function(datos) {
			var listadoInstitucionFinanciera = datos.listadoInstitucionFinanciera;

			$("#banco_forma_pago").empty();
			$("#cboFpBanco").empty();
			$
			.each(
					listadoInstitucionFinanciera,
					function(index) {
						if (listadoInstitucionFinanciera[index].tarjeta == '0'
							&& listadoInstitucionFinanciera[index].debito == '1') {
							$("#bancoId")
							.append(
									"<option value='"
									+ listadoInstitucionFinanciera[index].codigo
									+ "' nemotecnico='"
									+ listadoInstitucionFinanciera[index].nemotecnico
									+ "'>"
									+ listadoInstitucionFinanciera[index].nombre
									+ "</option>");
						}

						if (listadoInstitucionFinanciera[index].tarjeta == '1'
							&& listadoInstitucionFinanciera[index].debito == '1') {
							$("#tarjetaId")
							.append(
									"<option value='"
									+ listadoInstitucionFinanciera[index].codigo
									+ "' nemotecnico='"
									+ listadoInstitucionFinanciera[index].nemotecnico
									+ "'>"
									+ listadoInstitucionFinanciera[index].nombre
									+ "</option>");
						}
					});

			var fecha = new Date();
			var anio = fecha.getFullYear();
			var anioLimite = parseInt(anio) + 10;

			for (i = parseInt(anio); i <= anioLimite; i++) {
				$("#cboFpAnio").append(
						"<option value='" + i + "'>" + i + "</option>");
			}
		}
	});
}

function cargarPorCotizacionId(id) {
	var etapa;

	$
	.ajax({
		url : '../PymeCotizacionController',
		data : {
			"tipoConsulta" : "encontrarPorId",
			"id" : id
		},
		async : false,
		type : 'post',
		datatype : 'json',
		success : function(data) {
			if (data.success) {
				var datosCotizacion = data.datosCotizacion;
				$("#estadoCotizacion").val(
						datosCotizacion.estadoCotizacion);
				if (datosCotizacion != null)
					$("#grupo_productos").val(
							datosCotizacion.etapa1.productos);

				if (datosCotizacion.etapa1 != null) {

					$("#grupo_productos").removeAttr('required');
					$("#productos").removeAttr('required');
					$("#tipo_identificacion_principal").removeAttr('required');
					$("#identificacion").removeAttr('required');
					$("#nombres").removeAttr('required');
					$("#email").removeAttr('required');
					$("#apellidos").removeAttr('required');
					$("#tipo_identificacion").removeAttr('required');
					$("#agentes").removeAttr('required');
					$("#vigencia").removeAttr('required');
					$("#punto_venta").removeAttr('required');

					var etapa1 = datosCotizacion.etapa1;

					// Lleno los combos que son base de otros combos
					$("#grupo_productos").val(etapa1.grupoProductos);
					$("#puntoVentaSeleccionado").val(etapa1.puntoVenta);

					// Lleno los combos dependientes
					obtenerProductosPorGrupoProducto(etapa1.productos);
					cargarTiposIdentificacionPymes(etapa1.tipoIdentificacion, 'principal', true);
					$("#vigencia").val(etapa1.vigenciaPoliza);
					$("#identificacion").val(etapa1.identificacion);
					$("#nombres").val(etapa1.nombres);
					$("#nombre_completo").val(etapa1.nombreCompleto);
					$("#apellidos").val(etapa1.apellidos);
					$("#email").val(etapa1.mail);
					$("#celular").val(etapa1.celular);
					$("#telefono").val(etapa1.telefono);

					if($("#identificacion_asegurado").val()==""){
						cargarTiposIdentificacionPymes(etapa1.tipoIdentificacion, 'asegurado', true);
						$("#identificacion_asegurado").val(etapa1.identificacion);
						$("#nombres_asegurado").val(etapa1.nombres);
						$("#nombre_completo_asegurado").val(etapa1.nombreCompleto);
						$("#apellidos_asegurado").val(etapa1.apellidos);
					}
					etapa = 1;
				}

				if (datosCotizacion.etapa2 != null) {
					$("a[href='#next']").click();
					var etapa2 = datosCotizacion.etapa2;

					//Creo las direcciones configuradas
					var listadoDirecciones = etapa2.direcciones;
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
									+ '" required="required" class="datosPymes ubicacionProvincia"></select></td>'
									+ '<td style="width: 5%"><label><b>Cant&oacuten:*</b></label></td>'
									+ '<td style="width: 15%"><select id="ubicacion_canton_'
									+ listadoDirecciones[index].cotizacionDetalleId
									+ '" required="required" class="datosPymes"></select></td><td></td><td></td>'
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
									+ '" required="required" class="datosPymes"></input></td>'
									+ '<td><label><b>N&uacutemero:*</b></label></td>'
									+ '<td><input type="text" id="ubicacion_numero_'
									+ listadoDirecciones[index].cotizacionDetalleId
									+ '" required="required" class="datosPymes"></input></td>'
									+ '<td><label><b>C. Secundaria:*</b></label></td>'
									+ '<td><input type="text" id="ubicacion_calle_secundaria_'
									+ listadoDirecciones[index].cotizacionDetalleId
									+ '" required="required" class="datosPymes"></input></td>'
									+ '<td style="width: 20%" class="sorting">'
									+ '<button type="button" class="btn btn-danger btn-xs eliminar-extra-btn">'
									+ ' <span class="glyphicon glyphicon glyphicon-remove"></span> Eliminar'
									+ ' </button><input type="hidden" id="cotizacionDetalleId_'+listadoDirecciones[index].cotizacionDetalleId
									+ '" value="'+ listadoDirecciones[index].cotizacionDetalleId
									+ '"></td>'
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

					//Creo las coberturas generales configuradas
					var coberturasConfiguradas= etapa2.coberturas;
					$.each(coberturasConfiguradas, function(index) {
						if(coberturasConfiguradas[index].tipoOrigen=="ADICIONALES"){
							cargarCobertura(coberturasConfiguradas[index].configuracionCoberturaId, 
									"adicionales", 
									coberturasConfiguradas[index].tasaSugerida, 
									coberturasConfiguradas[index].tasaIngresada, 
									coberturasConfiguradas[index].valorIngresado, 
									coberturasConfiguradas[index].primaCalculada);
						}
						else if(coberturasConfiguradas[index].tipoOrigen=="GENERAL"){
							cargarCobertura(coberturasConfiguradas[index].configuracionCoberturaId, 
									"general", 
									coberturasConfiguradas[index].tasaSugerida, 
									coberturasConfiguradas[index].tasaIngresada, 
									coberturasConfiguradas[index].valorIngresado, 
									coberturasConfiguradas[index].primaCalculada);
						}
						else if(coberturasConfiguradas[index].tipoOrigen=="ASISTENCIA"){
							cargarAsistencia(coberturasConfiguradas[index].configuracionCoberturaId, coberturasConfiguradas[index].primaCalculada);
						}
						else if(coberturasConfiguradas[index].tipoOrigen=="DEDUCIBLE"){
							cargarDeducible(coberturasConfiguradas[index].configuracionCoberturaId, coberturasConfiguradas[index].textoDeducible);
						}
					});

					etapa = 2;
				}

				if (datosCotizacion.etapa3 != null) {
					$("a[href='#next']").click();
					var etapa3=datosCotizacion.etapa3;
					if(etapa3.valoresCalculados != null){
						$("#inspeccion_prima_sin_impuestos").val(formatDollar(etapa3.valoresCalculados.valorPrima));
						$("#inspeccion_super_bancos").val(formatDollar(etapa3.valoresCalculados.valorSuperBancos));
						$("#inspeccion_seguro_campesino").val(formatDollar(etapa3.valoresCalculados.valorSeguroCampesino));
						$("#inspeccion_derecho_emision").val(formatDollar(etapa3.valoresCalculados.valorDerechosEmision));
						$("#inspeccion_subtotal").val(formatDollar(etapa3.valoresCalculados.valorSubTotal));
						$("#inspeccion_iva").val(formatDollar(etapa3.valoresCalculados.valorIva));
						$("#inspeccion_total").val(formatDollar(etapa3.valoresCalculados.valorTotal));
						
						$("#prima_sin_impuestos").val(etapa3.valoresCalculados.valorPrima);
						$("#super_bancos").val(etapa3.valoresCalculados.valorSuperBancos);
						$("#seguro_campesino").val(etapa3.valoresCalculados.valorSeguroCampesino);
						$("#derecho_emision").val(etapa3.valoresCalculados.valorDerechosEmision);
						$("#subtotal").val(etapa3.valoresCalculados.valorSubTotal);
						$("#iva").val(etapa3.valoresCalculados.valorIva);
						$("#total").val(etapa3.valoresCalculados.valorTotal);
						$("#resumenTotalPago").children().first().empty().append("$" + etapa3.valoresCalculados.valorTotal);
					}
					if(datosCotizacion.estadoCotizacion=="Pendiente" || datosCotizacion.estadoCotizacion=="Pendiente de Inspeccion")
						$("#registrarParaInspeccion").hide();
					else
						$("#registrarParaInspeccion").show();
					etapa = 3;
				}

				if (datosCotizacion.etapa4 != null) {
					$("a[href='#next']").click();
					var etapa4 = datosCotizacion.etapa4;

					
					
					if (etapa4.endosoBeneficiario != null) {
						if (etapa4.endosoBeneficiario.beneficiarioId != null
								|| etapa4.endosoBeneficiario.beneficiarioId != "")
							cargarPestanaEndosoBeneficiario(
									etapa4.endosoBeneficiario.identificacion,
									etapa4.endosoBeneficiario.monto,
									etapa4.endosoBeneficiario.beneficiarioId);
						$("#asegurado_id").val(etapa4.endosoBeneficiario.entidadId);
						$("#identificacion_asegurado").val(etapa4.endosoBeneficiario.identificacion);
						$("#nombres_asegurado").val(etapa4.endosoBeneficiario.nombres);
						$("#apellidos_asegurado").val(etapa4.endosoBeneficiario.apellidos);
						$("#nombre_completo_asegurado").val(etapa4.endosoBeneficiario.nombreCompleto);

						cargarTiposIdentificacionPymes(etapa4.endosoBeneficiario.tipoIdentificacion, 'asegurado', true);
						$("#endoso_beneficiario_id").val(etapa4.endosoBeneficiario.endosoBeneficiarioId);
					} else {
						cargarPestanaEndosoBeneficiario(datosCotizacion.etapa1.identificacion, 0, "");
					}

					if (etapa4.formaPago != null && etapa4.formaPago.pagoId != null) {

						$("#descargar_certificado").fadeIn("slow").removeAttr("disabled");
						$("#enviar_certificado").fadeIn("slow").removeAttr("disabled");
						$("#codigoPagoRegistrado").val(etapa4.formaPago.pagoId);

						cargarTiposIdentificacionPymes("", "banco", false);
						cargarTiposIdentificacionPymes("", "tarjeta", false);

						cargarFormasPago(etapa4.formaPago.formaPagoId, "formasPago");

						if (etapa4.formaPago.formaPagoNombre.trim().toString() == "DEBITO BANCARIO") {
							cargarTiposIdentificacionPymes("", "tarjeta", false);
							cargarTiposIdentificacionPymes(etapa4.formaPago.tipoIdentificacion, "banco", true);
							$("#bancoNumeroCuenta").val(etapa4.formaPago.numCuentaTarjeta);
							$("#bancoTitular").val(etapa4.formaPago.nombreTitular);
							$("#bancoIdentificacion").val(etapa4.formaPago.identificacionTitular);
							$("#txtcuotaInicialbancoPlazo").val(etapa4.formaPago.cuotaInicial);
							cargarFormasPago(etapa4.formaPago.institucionFinancieraId, "intitucionesFinancieras");
							$("#bancoTipoCuenta").val(etapa4.formaPago.tipoCuenta);
							//$("#bancoTipoIdentificacion option[value='"+ etapa3.formaPago.tipoIdentificacion +"']").attr("selected", "selected");
							$("#txtfechaInicialpago").val(etapa4.formaPago.fechaDebito);
							$("#bancoPlazo").val(etapa4.formaPago.plazo);
							if (etapa4.formaPago.cuotaInicial > 0)
								$("#bancoPlazo option[value='10']").remove();
						}

						if (etapa4.formaPago.formaPagoNombre.trim().toString() == "DEBITO TARJETA") {
							cargarTiposIdentificacionPymes(etapa4.formaPago.tipoIdentificacion, "tarjeta", true);
							cargarTiposIdentificacionPymes("", "banco", false);
							cargarFormasPago(etapa4.formaPago.institucionFinancieraId, "intitucionesFinancieras");
							cargarFormasPago(etapa4.formaPago.anioExpTarjeta, "aniosVigencia");
							$("#tarjetaTipoCuenta").val(etapa4.formaPago.tipoCuenta);
							$("#tarjetaNumero").val(etapa4.formaPago.numCuentaTarjeta);
							$("#tarjetaTitular").val(etapa4.formaPago.nombreTitular);
							$("#tarjetaMesExp").val(etapa4.formaPago.mesExpTarjeta);
							$("#txtcuotaInicialtarjetaPlazo").val(etapa4.formaPago.cuotaInicial);
							$("#tarjetaIdentificacion").val(etapa4.formaPago.identificacionTitular);
							$("#tarjetaPlazo").val(etapa4.formaPago.plazo);
							$("#txtfechaInicialpagoTarjeta").val(etapa4.formaPago.fechaDebito);
							if (etapa4.formaPago.cuotaInicial > 0)
								$("#tarjetaPlazo option[value='10']").remove();
						}

						if (etapa4.formaPago.formaPagoNombre.trim().toString() == "CREDITO CUOTAS") {
							var listadoCuotas = etapa4.listadoCuotas;
							var filasCuotas = "";
							$("#detallePagoCuotas").empty();
							if (etapa4.formaPago.cuotaInicial > 0)
								$("#cboFpPlazo option[value='10']").remove();
							$.each(listadoCuotas, function (index) {
								filasCuotas = filasCuotas + "<tr>" +
									"<td align='center'><b>" + listadoCuotas[index].cuotaOrden + "</b></td>";
								if (index == 0) { 
									$("#txtcuotaInicial").val(parseFloat(etapa4.formaPago.cuotaInicial).toFixed(2));
									$("#cboFpPlazo").val(etapa4.formaPago.plazo);
									filasCuotas = filasCuotas + "<td align='center'><input type='text' class='detalleChequesPagos' id='cuotaInicial'  size='12' style='margin: 5px; padding: 5px;' value='" + listadoCuotas[index].cuotaNumCheque + "' disabled='disabled'></td>";
								} else {
									filasCuotas = filasCuotas + "<td align='center'><input type='text' class='detalleChequesPagos' id='cuotaInicial'  size='12' style='margin: 5px; padding: 5px;' value='" + listadoCuotas[index].cuotaNumCheque + "' disabled='disabled'></td>";
								}

								filasCuotas = filasCuotas + "<td align='center'>" + parseFloat(listadoCuotas[index].cuotaValor).toFixed(2) + "</td>" +
									"<td align='center'>" + listadoCuotas[index].cuotaFechaPago + "</td>";
							});
							$("#detallePagoCuotas").append(filasCuotas);
							$("#rowDetallePagos").show();
							
							validarValoresPagos("CREDITO CUOTAS");
						}

					} else {
						cargarTiposIdentificacionPymes("", "banco",
								false);
						cargarTiposIdentificacionPymes("", "tarjeta",
								false);
					}

					$("#loading").fadeIn();
					$(".loading-indicator").delay(
							(1000 * parseInt($('#numero_vehiculos')
									.val()))).fadeOut();
					$("#tabbable").delay(1000).show();

					etapa = 4;

				} else {
					cargarTiposIdentificacionPymes("", "banco", false);
					cargarTiposIdentificacionPymes("", "tarjeta", false);
					// $("a[href='#next']").click();
					etapa = 4;
				}

				var tipoIdentificacionId = data.datosCotizacion.etapa1.tipoIdentificacion;
				if (tipoIdentificacionId == ''
					|| tipoIdentificacionId == '1'
						|| tipoIdentificacionId == '2'
							|| tipoIdentificacionId == '3') {
					// natural
					if (data.datosCotizacion == null
							|| data.datosCotizacion.datosFacturaCliente == null) {
						$("#nombre_direccion_solicitante").val(
								datosCotizacion.etapa1.nombreCompleto);
						$("#nombre_direccion_solicitante").val(
								datosCotizacion.etapa1.nombreCompleto);
						cargarDireccionFactura();
					} else {
						// $("a[href='#next']").click();
						// cargarDireccionFactura("solicitante",data.datosCotizacion.datosFacturaCliente);
						if (data.datosCotizacion.datosFacturaAsegurado != null)
							cargarDireccionFactura(
									"asegurado",
									data.datosCotizacion.datosFacturaAsegurado);
						else
							cargarDireccionFactura("asegurado");

					}
				} else {
					cargarDireccionFactura();

				}

				if (datosCotizacion.estadoCotizacion == "Borrador") {
					$("#filaAprobarCotizacion").hide();
					$("#filaAprobadaClienteCotizacion").hide();
					$("#filaEmitirCotizacion").hide();
					$("#datosFinales").hide();
					$("#filaEnviarCotizacion").show();
					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
				} else if (datosCotizacion.estadoCotizacion == "Pendiente de Inspeccion"){
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});
				} else if (datosCotizacion.estadoCotizacion == "Pendiente"){
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$(".datosPymesPosterior").each(function() {
						$(this).removeAttr("disabled");
					});
				} else if (datosCotizacion.estadoCotizacion == "Pendiente de Revisar") {
					$("#filaAprobarCotizacion").show();
					$("#filaAprobadaClienteCotizacion").hide();
					$("#filaEmitirCotizacion").hide();
					$("#datosFinales").hide();
					$("#filaEnviarCotizacion").hide();
					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
				} else if (datosCotizacion.estadoCotizacion == "Revision Aprobada") {
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$("#Aprobada_Cliente_Cotizacion").removeAttr('disabled');
					$("#Negada_Cliente_Cotizacion").removeAttr('disabled');
					$("#filaAprobarCotizacion").hide();
					$("#filaAprobadaClienteCotizacion").show();
					$("#filaEmitirCotizacion").hide();
					$("#datosFinales").hide();
					$("#filaEnviarCotizacion").hide();
					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
					$("#selectDescargas").append("<option value='2'>Certificado de la Cotizaci&oacute;n</option>");
				} else if (datosCotizacion.estadoCotizacion == "Revision Negada") {
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$("#filaAprobarCotizacion").hide();
					$("#filaAprobadaClienteCotizacion").hide();
					$("#filaEmitirCotizacion").hide();
					$("#datosFinales").hide();
					$("#filaEnviarCotizacion").hide();
					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
					$("#selectDescargas").append("<option value='2'>Certificado de la Cotizaci&oacute;n</option>");
				} else if (datosCotizacion.estadoCotizacion == "Pendiente de Emitir") {
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});
					$("#Aprobar_Cotizacion").removeAttr('disabled');
					$("#Negar_Cotizacion").removeAttr('disabled');
					$("#filaEmitirCotizacion").show();
					$("#datosFinales").show();
					$("#filaEnviarCotizacion").hide();
					$("#finish").hide();

					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
					$("#selectDescargas").append("<option value='2'>Certificado de la Cotizaci&oacute;n</option>");
				} else if (datosCotizacion.estadoCotizacion == "Emitido") {
					$(".datosPymes").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":button").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$(":checkbox").each(function() {
						$(this).attr("disabled", "disabled");
					});

					$("#tipoAnimalOrigin").empty();
					$("#selectDescargas").append("<option value='1'>Ficha de la Cotizaci&oacute;n</option>");
					$("#selectDescargas").append("<option value='2'>Certificado de la Cotizaci&oacute;n</option>");
				}
				$("#descargar_FichaCotizacion").removeAttr('disabled');
				$("#descargar_certificadoCotizacion").removeAttr('disabled');
				$("#descargar_certificadoNormasParticulares").removeAttr('disabled');
			} else {
				alert(data.error);
			}
		}
	});
	return etapa;
}

function cargarResumenValores() {
	$.ajax({
		url : '../PymesObjetoCotizacionController',
		data : {
			"tipoConsulta" : "obtenerResumenValores",
			"cotizacionId" : $("#cotizacion_id").text(),
		},
		type : 'POST',
		datatype : 'json',
		success : function(data) {
			$("#inspeccion_total_suma_asegurada").val(formatDollar(data.valorAsegurado));
			$("#inspeccion_prima_sin_impuestos").val(formatDollar(data.valorPrima));
			$("#inspeccion_super_bancos").val(formatDollar(data.valorSuperBancos));
			if (parseFloat(data.porcentajeDescuento) > 0)
				$("#inspeccion_filaDescuento").show();
			$("#inspeccion_porcentaje_descuento").val(formatDollar(data.porcentajeDescuento));
			$("#inspeccion_seguro_campesino").val(formatDollar(data.valorSeguroCampesino));
			$("#inspeccion_derecho_emision").val(formatDollar(data.valorDerechosEmision));
			$("#inspeccion_subtotal").val(formatDollar(data.valorSubTotal));
			$("#inspeccion_iva").val(formatDollar(data.valorIva));
			$("#inspeccion_total").val(formatDollar(data.valorTotal));

			//Cargo los valores del paso 4 forma de pago
			$("#total_suma_asegurada").val(formatDollar(data.valorAsegurado));
			$("#prima_sin_impuestos").val(formatDollar(data.valorPrima));
			$("#super_bancos").val(formatDollar(data.valorSuperBancos));
			if (parseFloat(data.porcentajeDescuento) > 0)
				$("#filaDescuento").show();
			$("#porcentaje_descuento").val(formatDollar(data.porcentajeDescuento));
			$("#seguro_campesino").val(formatDollar(data.valorSeguroCampesino));
			$("#derecho_emision").val(formatDollar(data.valorDerechosEmision));
			$("#subtotal").val(formatDollar(data.valorSubTotal));
			$("#iva").val(formatDollar(data.valorIva));
			$("#total").val(formatDollar(data.valorTotal));
			$("#resumenTotalPago").children().first().empty().append("$" + data.valorTotal);
		}
	});
}
/*
 * METODO QUE CONSULTA LOS DATOS DE LA ENTIDAD EN BASE AL DOCUMENTO DE
 * IDENTIFICACIÃ“N Y MUESTRA LOS DATOS EN EL FORMULARIO CORRESPONDIENTE
 */
function cargarPorIdentificacion(formulario, valor) {
	var identificacion = valor;
	if ((identificacion.length >= 10 && $("#tipo_identificacion_principal")
			.val() != 2)
			|| (identificacion.length >= 5 && ($(
			"#tipo_identificacion_principal").val() == 2 || $(
			"#tipo_identificacion_principal").val() == ""))) {
		$(".loading_identificacion").fadeIn();
		var entidad = "";

		if (formulario == "datosClienteInicio")
			cargarTablaPorIdentificacion(identificacion);
		else if (formulario == "datosAsegurado")
			cargarTablaPorIdentificacion(identificacion);

		$.ajax({
			url : '../EntidadController',
			data : {
				"identificacion" : identificacion,
				"tipoConsulta" : "cargarPorIdentificacionEnsurance"
			},
			Async : false,
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				var entidad = data.entidad;
				if (entidad === undefined) {
					$("#nombres").val("");
					$("#apellidos").val("");
					$("#nombre_completo").val("");
					$("#email").val(data.email);
					$("#telefono").val(data.telefono);
					$("#celular").val(data.celular);
				} else if (entidad.clienteIdEnsurance == "") {
					/*
					 * $("#nombres").val(data.datosFactura.nombres);
					 * $("#apellidos").val(datosFactura.apellidos);
					 * $("#nombre_completo").val(datosFactura.nombre_completo);
					 * $("#email").val(datosFactura.email);
					 * $("#telefono").val(datosFactura.telefono);
					 * $("#celular").val(datosFactura.celular);
					 */
					$(".loading_identificacion").fadeOut();
				} else {
					if (entidad.clienteIdEnsurance != ""
						&& entidad.entidadIdEnsurance != "") {
						if (formulario == "datosClienteInicio") {
							$("#codigoEntidadEnsurance").val(
									entidad.entidadIdEnsurance);
							$("#codigoClienteEnsurance").val(
									entidad.clienteIdEnsurance);
							$("#nombres").val(entidad.nombre);
							$("#apellidos").val(entidad.apellido);
							$("#nombre_completo").val(
									entidad.apellido + entidad.nombre);
							$("#email").val(entidad.mail);
							$("#tipo_identificacion_principal").val(
									entidad.tipoIdentificacion).trigger(
									"change");
						}

						if (formulario == "formasPagoDebitoBancario") {
							$("#bancoTitular").val(
									entidad.nombre + " " + entidad.apellido);
							$("#bancoTipoIdentificacion").val(
									entidad.tipoIdentificacion);
						}

						if (formulario == "formasPagoDebitoTarjetas") {
							$("#tarjetaTitular").val(
									entidad.nombre + " " + entidad.apellido);
							$("#tarjetaTipoIdentificacion").val(
									entidad.tipoIdentificacion);
						}

						if (formulario == "datosAsegurado") {
							$("#nombres_asegurado").val(entidad.nombre);
							$("#apellidos_asegurado").val(entidad.apellido);
							$("#nombre_completo_asegurado").val(
									entidad.nombre + " " + entidad.apellido);
							cargarTiposIdentificacionPymes(
									entidad.tipoIdentificacion, "asegurado",
									true);
							// if(data.datosFactura!=null){
							// cargarDireccionFactura("asegurado",
							// data.datosFactura);
							// }
						}
					} else {
						$("#nombres").val("");
						$("#apellidos").val("");
						$("#nombre_completo").val("");
						$("#email").val("");
						$("#telefono").val("");
						$("#celular").val("");
					}
				}
				$(".loading_identificacion").fadeOut();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				$(".loading_identificacion").fadeOut();
			}
		});
	}
}

function mostrarAdicionalesSolicitante() {
	var opcion = $('#tipo_identificacion_principal').val();

	if (opcion == 1 || opcion == 2 || opcion == 3) {
		$("#filaNatural").show();
		$("#filaJuridica").hide();
	} else if (opcion == 4 || opcion == 5) {
		$("#filaNatural").hide();
		$("#filaJuridica").show();
	}
}

function mostrarAdicionalesAsegurado() {
	var opcion = $('#tipo_identificacion_asegurado').val();
	if (opcion == 1 || opcion == 2 || opcion == 3) {
		$("#filaNaturalAsegurado").show();
		$("#filaJuridicaAsegurado").hide();

		$("#ubicacionPersonaNatural").show();
		$("#datosPoliticaNatural").show();
		$("#situacionFinancieraNatural").show();
		$("#ubicacionPersonaJuridica").hide();
		$("#datosPoliticaJuridica").hide();
		$("#situacionFinancieraJuridica").hide();
		if (!cargadoDatosUPLA)
			cargarDatosEnGanaderoUPLANatural({
				"" : ""
			});
	} else if (opcion == 4 || opcion == 5) {
		$("#filaNaturalAsegurado").hide();
		$("#filaJuridicaAsegurado").show();
		$("#ubicacionPersonaNatural").hide();
		$("#datosPoliticaNatural").hide();
		$("#situacionFinancieraNatural").hide();
		$("#ubicacionPersonaJuridica").show();
		$("#datosPoliticaJuridica").show();
		$("#situacionFinancieraJuridica").show();
		if (!cargadoDatosUPLA)
			cargarDatosEnGanaderoUPLAJuridica({
				"" : ""
			});
	}
}

function cargarDatosEnGanaderoUPLAJuridica(datosJuridica) {
	if (datosJuridica.objetoSocial != null)
		$("#objeto_social_juridica").val(datosJuridica.objetoSocial);
	else
		$("#objeto_social_juridica").val("");

	if (datosJuridica.ciudadJuridica != null)
		$("#ciudad_juridica").val(datosJuridica.ciudadJuridica);
	else
		$("#ciudad_juridica").val("");

	if (datosJuridica.zonaDireccionMatriz != null)
		$("#zona_direccion_matriz_juridica").val(
				datosJuridica.zonaDireccionMatriz).attr('required', 'required')
				.trigger('change');
	else
		$("#zona_direccion_matriz_juridica").val("").attr('required',
		'required');

	if (datosJuridica.provinciaDireccionMatriz != null)
		cargarProvincias(datosJuridica.provinciaDireccionMatriz,
		"direccion_matriz_juridica");
	else
		cargarProvincias("", "direccion_matriz_juridica");

	if (datosJuridica.zonaDireccionMatriz == "R") {
		if (datosJuridica.cantonDireccionMatriz != null) {
			if (datosJuridica.provinciaDireccionMatriz != null)
				cargarCantones(datosJuridica.cantonDireccionMatriz,
						datosJuridica.provinciaDireccionMatriz,
				"direccion_matriz_juridica");
		} else {
			if (datosJuridica.provinciaDireccionMatriz != null)
				cargarCantones("", datosJuridica.provinciaDireccionMatriz,
				"direccion_matriz_juridica");
		}

		if (datosJuridica.parroquiaDireccionMatriz != null) {
			if (datosJuridica.cantonDireccionMatriz != null)
				cargarParroquias(datosJuridica.parroquiaDireccionMatriz,
						datosJuridica.cantonDireccionMatriz,
				"direccion_matriz_juridica");
		} else {
			if (datosJuridica.cantonDireccionMatriz != null)
				cargarParroquias("", datosJuridica.cantonDireccionMatriz,
				"direccion_matriz_juridica");
		}

	} else {
		if (datosJuridica.ciudadDireccionMatriz != null) {
			if (datosJuridica.provinciaDireccionMatriz != null)
				obtenerCiudadesPorProvinciaPJuridica(
						datosJuridica.ciudadDireccionMatriz,
						datosJuridica.provinciaDireccionMatriz,
				"direccion_matriz_juridica");
		} else {
			if (datosJuridica.provinciaDireccionMatriz != null)
				obtenerCiudadesPorProvinciaPJuridica("",
						datosJuridica.provinciaDireccionMatriz,
				"direccion_matriz_juridica");
		}
	}

	if (datosJuridica.callePrincipalMatriz != null)
		$("#calle_principal_direccion_juridica").val(
				datosJuridica.callePrincipalMatriz);
	else
		$("#calle_principal_direccion_juridica").val("");

	if (datosJuridica.numeroDireccionMatriz != null)
		$("#numero_direccion_juridica")
		.val(datosJuridica.numeroDireccionMatriz);
	else
		$("#numero_direccion_juridica").val("");

	if (datosJuridica.calleSecundariaMatriz != null)
		$("#calle_secundaria_direccion_juridica").val(
				datosJuridica.calleSecundariaMatriz);
	else
		$("#calle_secundaria_direccion_juridica").val("");

	if (datosJuridica.referenciaDireccionMatriz != null)
		$("#referencia_direccion_juridica").val(
				datosJuridica.referenciaDireccionMatriz);
	else
		$("#referencia_direccion_juridica").val("");

	if (datosJuridica.sucursalDireccion != null)
		$("#direccion_sucursal_juridica").val(datosJuridica.sucursalDireccion);
	else
		$("#direccion_sucursal_juridica").val("");

	if (datosJuridica.sucursalCiudad != null)
		$("#ciudad_sucursal_juridica").val(datosJuridica.sucursalCiudad);
	else
		$("#ciudad_sucursal_juridica").val("");

	if (datosJuridica.telefono != null)
		$("#telefono_juridica").val(datosJuridica.telefono);
	else
		$("#telefono_juridica").val("");

	if (datosJuridica.fax != null)
		$("#fax_juridica").val(datosJuridica.fax);
	else
		$("#fax_juridica").val("");

	// actividad persona natural

	if (datosJuridica.nombresRepresentanteLegal != null)
		$("#nombres_representante_juridica").val(
				datosJuridica.nombresRepresentanteLegal);
	else
		$("#nombres_representante_juridica").val("");

	if (datosJuridica.apellidosRepresentanteLegal != null)
		$("#apellidos_representante_juridica").val(
				datosJuridica.apellidosRepresentanteLegal);
	else
		$("#apellidos_representante_juridica").val("");

	if (datosJuridica.tipoIdentificacionRepresentante != null)
		cargarTiposIdentificacionPymes(
				datosJuridica.tipoIdentificacionRepresentante,
				"representante_juridica", true);
	else
		cargarTiposIdentificacionPymes("", "representante_juridica", false);

	if (datosJuridica.identificacionRepresentante != null)
		$("#identificacion_representante_juridica").val(
				datosJuridica.identificacionRepresentante);
	else
		$("#identificacion_representante_juridica").val("");

	if (datosJuridica.lugarNacimientoRepresentante != null)
		$("#lugar_nacimiento_representante_juridica").val(
				datosJuridica.lugarNacimientoRepresentante);
	else
		$("#lugar_nacimiento_representante_juridica").val("");

	if (datosJuridica.fechaNacimiento != null) {
		var dia = datosJuridica.fechaNacimiento.date < 10 ? '0'
				+ datosJuridica.fechaNacimiento.date
				: datosJuridica.fechaNacimiento.date;
		var mes = datosJuridica.fechaNacimiento.month < 9 ? "0"
				+ (datosJuridica.fechaNacimiento.month + 1)
				: (datosJuridica.fechaNacimiento.month + 1);
				var anio = (1900 + datosJuridica.fechaNacimiento.year);
				var aux = '' + anio + '-' + mes + '-' + dia;

				$("#fecha_nacimiento_representante_juridica").val(aux);
	} else
		$("#fecha_nacimiento_representante_juridica").val("");

	if (datosJuridica.residenciaRepresentante != null)
		$("#residencia_representante_juridica").val(
				datosJuridica.residenciaRepresentante);
	else
		$("#residencia_representante_juridica").val("");

	/*
	 * if (datosJuridica.paisRepresentante != null)
	 * $("#pais_representante_juridica").val(datosJuridica.paisRepresentante);
	 * else $("#pais_representante_juridica").val("");
	 */

	if (datosJuridica.provinciaRepresentante != null)
		cargarProvincias(datosJuridica.provinciaRepresentante,
		"representante_juridica");
	// $("#provincia_representante_juridica").val(datosJuridica.provinciaRepresentante);
	else
		cargarProvincias("", "representante_juridica");
	// $("#provincia_representante_juridica").val("");

	if (datosJuridica.ciudadRepresentante != null)
		obtenerCiudadesPorProvinciaPJuridica(datosJuridica.ciudadRepresentante,
				datosJuridica.provinciaRepresentante, "representante_juridica");
	// $("#ciudad_representante_juridica").val(datosJuridica.ciudadRepresentante);
	else
		// $("#ciudad_representante_juridica").val("");
		obtenerCiudadesPorProvinciaPJuridica('',
				datosJuridica.provinciaRepresentante, "representante_juridica");

	if (datosJuridica.telefonoRepresentante != null)
		$("#telefono_representante_juridica").val(
				datosJuridica.telefonoRepresentante);
	else
		$("#telefono_representante_juridica").val("");

	if (datosJuridica.celularRepresentante != null)
		$("#celular_representante_legal").val(
				datosJuridica.celularRepresentante);
	else
		$("#celular_representante_legal").val("");

	if (datosJuridica.expuestoRepresentante != null)
		$("#expuesto_representante_juridica").val(
				datosJuridica.expuestoRepresentante ? 1 : 0);
	else
		$("#expuesto_representante_juridica").val("");

	if (datosJuridica.cargoExpuesta != null)
		$("#cargo_expuesta_representante_juridica").val(
				datosJuridica.cargoExpuesta);
	else
		$("#cargo_expuesta_representante_juridica").val("");

	if (datosJuridica.expuestoFamiliar != null)
		$("#expuesto_familiar_juridica").val(
				datosJuridica.expuestoFamiliar ? 1 : 0);
	else
		$("#expuesto_familiar_juridica").val("");

	if (datosJuridica.parentescoExpuestoFamiliar != null)
		$("#parentesco_expuesto_familiar_juridico").val(
				datosJuridica.parentescoExpuestoFamiliar);
	else
		$("#parentesco_expuesto_familiar_juridico").val("");

	if (datosJuridica.cargoExpuestoFamiliar != null)
		$("#cargo_expuesto_familiar_juridica").val(
				datosJuridica.cargoExpuestoFamiliar);
	else
		$("#cargo_expuesto_familiar_juridica").val("");

	if (datosJuridica.apellidoPaternoConyuge != null)
		$("#apellido_paterno_conyuge_juridica").val(
				datosJuridica.apellidoPaternoConyuge);
	else
		$("#apellido_paterno_conyuge_juridica").val("");

	if (datosJuridica.apellidoMaternoConyuge != null)
		$("#apellido_materno_conyuge_juridica").val(
				datosJuridica.apellidoMaternoConyuge);
	else
		$("#apellido_materno_conyuge_juridica").val("");

	if (datosJuridica.nombreConyuge != null)
		$("#nombre_conyuge_juridica").val(datosJuridica.nombreConyuge);
	else
		$("#nombre_conyuge_juridica").val("");

	if (datosJuridica.tipoIdentificacionConyuge != null)
		cargarTiposIdentificacionPymes(datosJuridica.tipoIdentificacionConyuge,
				"conyugue_juridica", true);
	else
		cargarTiposIdentificacionPymes("", "conyugue_juridica", false);

	if (datosJuridica.identificacionConyuge != null)
		$("#identificacion_conyuge_juridica").val(
				datosJuridica.identificacionConyuge);
	else
		$("#identificacion_conyuge_juridica").val("");

	if (datosJuridica.salarioMensual != null)
		$("#salario_mensual_juridica").val(datosJuridica.salarioMensual);
	else
		$("#salario_mensual_juridica").val("");

	if (datosJuridica.activos != null)
		$("#activos_juridica").val(datosJuridica.activos);
	else
		$("#activos_juridica").val("");

	if (datosJuridica.otrosIngresos != null)
		$("#otros_ingresos_juridica").val(datosJuridica.otrosIngresos);
	else
		$("#otros_ingresos_juridica").val("");

	if (datosJuridica.pasivos != null)
		$("#pasivos_juridica").val(datosJuridica.pasivos);
	else
		$("#pasivos_juridica").val("");

	if (datosJuridica.egresos != null)
		$("#egresos_juridica").val(datosJuridica.egresos);
	else
		$("#egresos_juridica").val("");

	if (datosJuridica.patrimonio != null)
		$("#patrimonio_juridica").val(datosJuridica.patrimonio);
	else
		$("#patrimonio_juridica").val("");

	if (datosJuridica.ingresoEgreso != null)
		$("#ingresos_egresos_juridica").val(datosJuridica.ingresoEgreso);
	else
		$("#ingresos_egresos_juridica").val("");

	if (datosJuridica.esAsegurado != null)
		$("#es_asegurado_juridica").val(datosJuridica.esAsegurado ? 1 : 0);
	else
		$("#es_asegurado_juridica").val("");

	if (datosJuridica.esBeneficiario != null)
		$("#es_beneficiario_juridica")
		.val(datosJuridica.esBeneficiario ? 1 : 0);
	else
		$("#es_beneficiario_juridica").val("");

	if (datosJuridica.tipoIdentificacionAsegurado != null)
		cargarTiposIdentificacionPymes(
				datosJuridica.tipoIdentificacionAsegurado,
				"asegurado_juridica", true);
	else
		cargarTiposIdentificacionPymes("", "asegurado_juridica", false);

	if (datosJuridica.identificacionAsegurado != null)
		$("#identificacion_asegurado_juridica").val(
				datosJuridica.identificacionAsegurado);
	else
		$("#identificacion_asegurado_juridica").val("");

	if (datosJuridica.nombreCompletoAsegurado != null)
		$("#nombre_asegurado_juridica").val(
				datosJuridica.nombreCompletoAsegurado);
	else
		$("#nombre_asegurado_juridica").val("");

	if (datosJuridica.direccionAsegurado != null)
		$("#direccion_asegurado_juridica")
		.val(datosJuridica.direccionAsegurado);
	else
		$("#direccion_asegurado_juridica").val("");

	if (datosJuridica.telefonoAsegurado != null)
		$("#telefono_asegurado_juridica").val(datosJuridica.telefonoAsegurado);
	else
		$("#telefono_asegurado_juridica").val("");

	if (datosJuridica.celularAsegurado != null)
		$("#celular_asegurado_juridica").val(datosJuridica.celularAsegurado);
	else
		$("#celular_asegurado_juridica").val("");

	if (datosJuridica.relacionAsegurado != null)
		$("#relacion_asegurado_juridica").val(datosJuridica.relacionAsegurado);
	else
		$("#relacion_asegurado_juridica").val("");

	// beneficiario
	if (datosJuridica.tipoIdentificacionBeneficiario != null)
		cargarTiposIdentificacionPymes(
				datosJuridica.tipoIdentificacionBeneficiario,
				"beneficiario_juridica", true);
	else
		cargarTiposIdentificacionPymes("", "beneficiario_juridica", false);

	if (datosJuridica.identificacionBeneficiario != null)
		$("#identificacion_beneficiario_juridica").val(
				datosJuridica.identificacionBeneficiario);
	else
		$("#identificacion_beneficiario_juridica").val("");

	if (datosJuridica.nombreCompletoBeneficiario != null)
		$("#nombre_beneficiario_juridica").val(
				datosJuridica.nombreCompletoBeneficiario);
	else
		$("#nombre_beneficiario_juridica").val("");

	if (datosJuridica.direccionBeneficiario != null)
		$("#direccion_beneficiario_juridica").val(
				datosJuridica.direccionBeneficiario);
	else
		$("#direccion_beneficiario_juridica").val("");

	if (datosJuridica.telefonoBeneficiario != null)
		$("#telefono_beneficiario_juridica").val(
				datosJuridica.telefonoBeneficiario);
	else
		$("#telefono_beneficiario_juridica").val("");

	if (datosJuridica.celularBeneficiario != null)
		$("#celular_beneficiario_juridica").val(
				datosJuridica.celularBeneficiario);
	else
		$("#celular_beneficiario_juridica").val("");

	if (datosJuridica.relacionBeneficiario != null)
		$("#relacion_beneficiario_juridica").val(
				datosJuridica.relacionBeneficiario);
	else
		$("#relacion_beneficiario_juridica").val("");

	cargadoDatosUPLA = true;

}

function cargarDatosEnGanaderoUPLANatural(datosNatural) {

	if (datosNatural.lugarNacimiento != null)
		$("#lugar_nacimiento_natural").val(datosNatural.lugarNacimiento);
	else
		$("#lugar_nacimiento_natural").val("");

	if (datosNatural.fechaNacimiento != null) {
		var dia = datosNatural.fechaNacimiento.date < 10 ? '0'
				+ datosNatural.fechaNacimiento.date
				: datosNatural.fechaNacimiento.date;
		var mes = datosNatural.fechaNacimiento.month < 9 ? "0"
				+ (datosNatural.fechaNacimiento.month + 1)
				: (datosNatural.fechaNacimiento.month + 1);
				var anio = (1900 + datosNatural.fechaNacimiento.year);
				var aux = '' + anio + '-' + mes + '-' + dia;

				$("#fecha_nacimiento_natural").val(aux);
	} else
		$("#fecha_nacimiento_natural").val("");

	if (datosNatural.zonaDireccionCliente != null)
		$("#zona_direccion_natural").val(datosNatural.zonaDireccionCliente)
		.attr('required', 'required').trigger('change');
	else
		$("#zona_direccion_natural").val("").attr('required', 'required');

	if (datosNatural.provinciaDireccionCliente != null)
		cargarProvinciasPNatural(datosNatural.provinciaDireccionCliente,
		"direccion_cliente_natural");
	else
		cargarProvinciasPNatural("", "direccion_cliente_natural");

	if (datosNatural.zonaDireccionCliente == "R") {
		if (datosNatural.cantonDireccionCliente != null) {
			if (datosNatural.provinciaDireccionCliente != null)
				cargarCantones(datosNatural.cantonDireccionCliente,
						datosNatural.provinciaDireccionCliente,
				"direccion_cliente_natural");
		} else {
			if (datosNatural.provinciaDireccionCliente != null)
				cargarCantones("", datosNatural.provinciaDireccionCliente,
				"direccion_cliente_natural");
		}

		if (datosNatural.parroquiaDireccionCliente != null) {
			if (datosNatural.cantonDireccionCliente != null)
				cargarParroquias(datosNatural.parroquiaDireccionCliente,
						datosNatural.cantonDireccionCliente,
				"direccion_cliente_natural");
		} else {
			if (datosNatural.cantonDireccionCliente != null)
				cargarParroquias("", datosNatural.cantonDireccionCliente,
				"direccion_cliente_natural");
		}

	} else {
		if (datosNatural.ciudadDireccionCliente != null) {
			if (datosNatural.provinciaDireccionCliente != null)
				obtenerCiudadesPorProvinciaPNatural(
						datosNatural.ciudadDireccionCliente,
						datosNatural.provinciaDireccionCliente,
				"direccion_cliente_natural");
		} else {
			if (datosNatural.provinciaDireccionCliente != null)
				obtenerCiudadesPorProvinciaPNatural("",
						datosNatural.provinciaDireccionCliente,
				"direccion_cliente_natural");
		}
	}

	if (datosNatural.callePrincipalCliente != null)
		$("#calle_principal_direccion_natural").val(
				datosNatural.callePrincipalCliente);
	else
		$("#calle_principal_direccion_natural").val("");

	if (datosNatural.numeroDireccionCliente != null)
		$("#numero_direccion_natural").val(datosNatural.numeroDireccionCliente);
	else
		$("#numero_direccion_natural").val("");

	if (datosNatural.calleSecundariaCliente != null)
		$("#calle_secundaria_direccion_natural").val(
				datosNatural.calleSecundariaCliente);
	else
		$("#calle_secundaria_direccion_natural").val("");

	if (datosNatural.referenciaDireccionCliente != null)
		$("#referencia_direccion_natural").val(
				datosNatural.referenciaDireccionCliente);
	else
		$("#referencia_direccion_natural").val("");

	if (datosNatural.telefonoCliente != null)
		$("#telefono_cliente_natural").val(datosNatural.telefonoCliente);
	else
		$("#telefono_cliente_natural").val("");

	if (datosNatural.celularCliente != null)
		$("#celular_cliente_natural").val(datosNatural.celularCliente);
	else
		$("#celular_cliente_natural").val("");

	/*
	 * if (datosNatural.generoCliente != null)
	 * $("#genero_cliente_natural").html(genero).val(datosNatural.generoCliente);
	 * else $("#genero_cliente_natural").html(genero).val("");
	 */

	if (datosNatural.mail != null)
		$("#mail_cliente_natural").val(datosNatural.mail);
	else
		$("#mail_cliente_natural").val("");

	/*
	 * if (datosNatural.tipoActividadCliente != null)
	 * $("#tipo_actividad_cliente_natural").val(datosNatural.tipoActividadCliente);
	 * else $("#tipo_actividad_cliente_natural").val("");
	 */

	if (datosNatural.cargoOcupaCliente != null)
		$("#cargo_ocupa_cliente_natural").val(datosNatural.cargoOcupaCliente);
	else
		$("#cargo_ocupa_cliente_natural").val("");

	if (datosNatural.expuestoCliente != null)
		$("#expuesto_cliente_natural").val(datosNatural.expuesto ? 1 : 0);
	else
		$("#expuesto_cliente_natural").val("");

	if (datosNatural.cargoExpuestoCliente != null)
		$("#cargo_expuesto_cliente_natural").val(datosNatural.cargoExpuesta);
	else
		$("#cargo_expuesto_cliente_natural").val("");

	if (datosNatural.expuestoFamiliar != null)
		$("#expuesto_familiar_natural").val(
				datosNatural.expuestoFamiliar ? 1 : 0);
	else
		$("#expuesto_familiar_natural").val("");

	if (datosNatural.parentescoExpuestoFamiliar != null)
		$("#parentesco_expuesto_familiar_natural").val(
				datosNatural.parentescoExpuestoFamiliar);
	else
		$("#parentesco_expuesto_familiar_natural").val("");

	if (datosNatural.cargoExpuestoFamiliar != null)
		$("#cargo_expuesto_familiar_natural").val(
				datosNatural.cargoExpuestoFamiliar);
	else
		$("#cargo_expuesto_familiar_natural").val("");

	if (datosNatural.apellidoPaternoConyuge != null)
		$("#apellido_paterno_conyuge_natural").val(
				datosNatural.apellidoPaternoConyuge);
	else
		$("#apellido_paterno_conyuge_natural").val("");

	if (datosNatural.apellidoMaternoConyuge != null)
		$("#apellido_materno_conyuge_natural").val(
				datosNatural.apellidoMaternoConyuge);
	else
		$("#apellido_materno_conyuge_natural").val("");

	if (datosNatural.nombreConyuge != null)
		$("#nombre_conyuge_natural").val(datosNatural.nombreConyuge);
	else
		$("#nombre_conyuge_natural").val("");

	if (datosNatural.tipoIdentificacionConyuge != null)
		cargarTiposIdentificacionPymes(datosNatural.tipoIdentificacionConyuge,
				"conyuge_natural", true);
	else
		cargarTiposIdentificacionPymes("", "conyuge_natural", false);

	if (datosNatural.identificacionConyuge != null)
		$("#identificacion_conyuge_natural").val(
				datosNatural.identificacionConyuge);
	else
		$("#identificacion_conyuge_natural").val("");

	if (datosNatural.salarioMensual != null)
		$("#salario_mensual_natural").val(datosNatural.salarioMensual);
	else
		$("#salario_mensual_natural").val("");

	if (datosNatural.activos != null)
		$("#activos_natural").val(datosNatural.activos);
	else
		$("#activos_natural").val("");

	if (datosNatural.otrosIngresos != null)
		$("#otros_ingresos_natural").val(datosNatural.otrosIngresos);
	else
		$("#otros_ingresos_natural").val("");

	if (datosNatural.pasivos != null)
		$("#pasivos_natural").val(datosNatural.pasivos);
	else
		$("#pasivos_natural").val("");

	if (datosNatural.egresos != null)
		$("#egresos_natural").val(datosNatural.egresos);
	else
		$("#egresos_natural").val("");

	if (datosNatural.patrimonio != null)
		$("#patrimonio_natural").val(datosNatural.patrimonio);
	else
		$("#patrimonio_natural").val("");

	if (datosNatural.ingresoEgreso != null)
		$("#ingresos_egresos_natural").val(datosNatural.ingresoEgreso);
	else
		$("#ingresos_egresos_natural").val("");

	/*
	 * if (datosNatural.esAsegurado != null)
	 * $("#es_asegurado_natural").val(datosNatural.esAsegurado?1:0); else
	 * $("#es_asegurado_natural").val("");
	 * 
	 * if (datosNatural.esBeneficiario != null)
	 * $("#es_beneficiario_natural").val(datosNatural.esBeneficiario?1:0); else
	 * $("#es_beneficiario_natural").val("");
	 */

	if (datosNatural.tipoIdentificacionAsegurado != null)
		cargarTiposIdentificacionPymes(
				datosNatural.tipoIdentificacionAsegurado, "asegurado_natural",
				true);
	else
		cargarTiposIdentificacionPymes("", "asegurado_natural", false);

	if (datosNatural.identificacionAsegurado != null)
		$("#identificacion_asegurado_natural").val(
				datosNatural.identificacionAsegurado);
	else
		$("#identificacion_asegurado_natural").val("");

	if (datosNatural.nombreCompletoAsegurado != null)
		$("#nombre_asegurado_natural")
		.val(datosNatural.nombreCompletoAsegurado);
	else
		$("#nombre_asegurado_natural").val("");

	if (datosNatural.direccionAsegurado != null)
		$("#direccion_asegurado_natural").val(datosNatural.direccionAsegurado);
	else
		$("#direccion_asegurado_natural").val("");

	if (datosNatural.telefonoAsegurado != null)
		$("#telefono_asegurado_natural").val(datosNatural.telefonoAsegurado);
	else
		$("#telefono_asegurado_natural").val("");

	if (datosNatural.celularAsegurado != null)
		$("#celular_asegurado_natural").val(datosNatural.celularAsegurado);
	else
		$("#celular_asegurado_natural").val("");

	if (datosNatural.relacionAsegurado != null)
		$("#relacion_asegurado_natural").val(datosNatural.relacionAsegurado);
	else
		$("#relacion_asegurado_natural").val("");

	// beneficiario
	if (datosNatural.tipoIdentificacionBeneficiario != null)
		cargarTiposIdentificacionPymes(
				datosNatural.tipoIdentificacionBeneficiario,
				"beneficiario_natural", true);
	else
		cargarTiposIdentificacionPymes("", "beneficiario_natural", false);

	if (datosNatural.identificacionBeneficiario != null)
		$("#identificacion_beneficiario_natural").val(
				datosNatural.identificacionBeneficiario);
	else
		$("#identificacion_beneficiario_natural").val("");

	if (datosNatural.nombreCompletoBeneficiario != null)
		$("#nombre_beneficiario_natural").val(
				datosNatural.nombreCompletoBeneficiario);
	else
		$("#nombre_beneficiario_natural").val("");

	if (datosNatural.direccionBeneficiario != null)
		$("#direccion_beneficiario_natural").val(
				datosNatural.direccionBeneficiario);
	else
		$("#direccion_beneficiario_natural").val("");

	if (datosNatural.telefonoBeneficiario != null)
		$("#telefono_beneficiario_natural").val(
				datosNatural.telefonoBeneficiario);
	else
		$("#telefono_beneficiario_natural").val("");

	if (datosNatural.celularBeneficiario != null)
		$("#celular_beneficiario_natural")
		.val(datosNatural.celularBeneficiario);
	else
		$("#celular_beneficiario_natural").val("");

	if (datosNatural.relacionBeneficiario != null)
		$("#relacion_beneficiario_natural").val(
				datosNatural.relacionBeneficiario);
	else
		$("#relacion_beneficiario_natural").val("");

	cargadoDatosUPLA = true;

}

//Arreglo de elementos unicos JSON
function arregloUnicoJSON(obj) {
	var uniques = [];
	var stringify = {};
	for (var i = 0; i < obj.length; i++) {
		var keys = Object.keys(obj[i]);
		keys.sort(function(a, b) {
			return a - b;
		});
		var str = '';
		for (var j = 0; j < keys.length; j++) {
			str += JSON.stringify(keys[j]);
			str += JSON.stringify(obj[i][keys[j]]);
		}
		if (!stringify.hasOwnProperty(str)) {
			uniques.push(obj[i]);
			stringify[str] = true;
		}
	}
	return uniques;
}

function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
	.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g,
	" "));
}

//************
//***GRABAR COTIZACION Y OBJETO-GANADERO***
//************
function guardarCotizacion() {

	var pppv = $('#punto_venta').find(":selected").attr('pxpv');
	// alert(pppv);
	$.ajax({
		url : "../PymeCotizacionController",
		data : {
			"tipoConsulta" : "crear",
			"producto" : "PYMES",
			"grupoProductoId" : $("#grupo_productos").val(),
			"puntoVentaId" : $("#punto_venta").val(),
			"vigenciaPoliza" : $("#vigencia").val(),
			"tipoIdentificacion" : $("#tipo_identificacion_principal").val(),
			"productoXPuntoDeVenta" : pppv,
			"identificacion" : $("#identificacion").val(),
			"nombres" : $("#nombres").val(),
			"apellidos" : $("#apellidos").val(),
			"nombreCompleto" : $("#nombre_completo").val(),
			"email" : $("#email").val(),
			"telefono" : $("#telefono").val(),
			"celular" : $("#celular").val(),
			"agenteId" : $("#agentes").val(),
			"codigoEntidadEnsurance" : $("#codigoEntidadEnsurance").val(),
			"codigoClienteEnsurance" : $("#codigoClienteEnsurance").val(),
			"cotizacionId" : $("#cotizacion_id").text(),
			"tipoObjeto" : tipoObjeto,
			"grupoPorProductoId" : $("#productos").val(),
			"tasaProductoId" : $("#tasas").val(),
			"tasaProductoValor" : $("#tasa").val(),

		},
		type : 'post',
		datatype : 'json',
		success : function(data) {
			$("#cotizacion_id").text(data.cotizacionId);
			$("#punto_venta").attr("unidadNegocio", data.unidadNegocioId);
			// Validacion poner en la URL el id de la cotizacion
			var valorId = getParameterByName("id");
			if (valorId != null) {
				$.pushVar("id", data.cotizacionId, "", window.location.href);
			}
		}
	});
}

function guardarProductoPymes() {
	var resVal = false;
	var coberturasGenerales = new Array();
	var contador = 0;
	//Lleno el objeto de las coberturas generales
	$.each(configCobertasPorProducto,
			function(index) {
		if(configCobertasPorProducto[index].tipoDeclaracion=="1"){
			if ($("#cobertura_"+ configCobertasPorProducto[index].configuracionCoberturaId).is(':checked')) {
				var tasaSugerida = 0;
				var tasaIngresada = 0;
				var montoAsegurado = 0;
				var primaCalculada = 0;
				if ($("#tasa_sugerida_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					tasaSugerida = Number($("#tasa_sugerida_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#tasa_ingresada_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					tasaIngresada = Number($("#tasa_ingresada_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#monto_asegurado_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					montoAsegurado = Number($("#monto_asegurado_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				if ($("#prima_neta_"+ configCobertasPorProducto[index].configuracionCoberturaId).length)
					primaCalculada = Number($("#prima_neta_"+ configCobertasPorProducto[index].configuracionCoberturaId).val());
				var cobertura = {
						configuracionCoberturaId : configCobertasPorProducto[index].configuracionCoberturaId,
						tasaSugerida : tasaSugerida,
						tasaIngresada : tasaIngresada,
						valorIngresado : montoAsegurado,
						primaCalculada : primaCalculada,
						tipoOrigen : configCobertasPorProducto[index].tipoCoberturaId==1?"GENERAL":"ADICIONALES",
								textoDeducible : ""
				};
				coberturasGenerales[contador] = cobertura;
				contador++;
			}
		}
	});

	//Lleno el objeto de las asistencias
	$.each(configAsistenciasPorProducto, function(index) {
		if ($("#asistencia_"+ configAsistenciasPorProducto[index].asistenciaId).is(':checked')) {
			var valorAsistencia = 0;
			if ($("#valor_asistencia_"+ configAsistenciasPorProducto[index].asistenciaId).length)
				valorAsistencia = Number($("#valor_asistencia_"+ configAsistenciasPorProducto[index].asistenciaId).val());
			var cobertura = {
					configuracionCoberturaId : configAsistenciasPorProducto[index].asistenciaId,
					tasaSugerida : 0,
					tasaIngresada : 0,
					valorIngresado : 0,
					primaCalculada : valorAsistencia,
					tipoOrigen : "ASISTENCIA",
					textoDeducible : ""
			};
			coberturasGenerales[contador] = cobertura;
			contador++;
		}
	});

	//Lleno el objeto de los deducibles
	$(".deducibleIDs").each(function() {
		var deducibleId = $(this).val();
		var textoDeducible = $("#text_area_deducible_cobertura_"+deducibleId).val();
		var cobertura = {
				configuracionCoberturaId : deducibleId,
				tasaSugerida : 0,
				tasaIngresada : 0,
				valorIngresado : 0,
				primaCalculada : 0,
				tipoOrigen : "DEDUCIBLE",
				textoDeducible : textoDeducible
		};
		coberturasGenerales[contador] = cobertura;
		contador++;
	});

	$.ajax({url : "../PymesObjetoCotizacionController",
		data : {
			"cotizacionId" : $("#cotizacion_id").text(),
			"tipoConsulta" : "crear",
			"coberturas" : JSON.stringify(coberturasGenerales)
		},
		type : 'post',
		async : false,
		datatype : 'json',
		success : function(data) {
			$("#msgPopupFichaPymeError").hide();
			if(data.success){
				$("#cotizacion_id").text(data.cotizacionId);
				resVal=true;
			}
			else{
				$("#lblContenidoMensaje").html(data.error);
				$("#msgPopupFichaPymeError").show();
				resVal=false;
			}
		}
	});
	return resVal;
}

/* GUARDAR EL ASEGURADO */

function guardarAsegurado(tipoConsulta) {

	var tipoIdentificacionAsegurado = $("#tipo_identificacion_asegurado").val();
	var identificacionAsegurado = $("#identificacion_asegurado").val();
	var nombreAsegurado = $("#nombres_asegurado").val();
	var apellidoAsegurado = $("#apellidos_asegurado").val();
	var nombreCompletoAsegurado = $("#nombre_completo_asegurado").val();
	var cotizacionId = $("#cotizacion_id").text();
	var aseguradoId = $("#asegurado_id").val();
	var valido = true;

	if (tipoIdentificacionAsegurado == ""
		|| tipoIdentificacionAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html(
		"Seleccione un tipo de identificación");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#tipo_identificacion_asegurado").focus();
		valido = false;
	} else {
		if (tipoIdentificacionAsegurado != '4') {
			nombreCompletoAsegurado = apellidoAsegurado + ' ' + nombreAsegurado;
			if (nombreAsegurado == "" || nombreAsegurado == null) {
				$("#mensajeErrorEndosoBeneficiario").html(
				"Ingrese el nombre del asegurado");
				$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
				$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
				$("#nombres_asegurado").focus();
				valido = false;
			}
			if (apellidoAsegurado == "" || apellidoAsegurado == null) {
				$("#mensajeErrorEndosoBeneficiario").html(
				"Ingrese el apellido del asegurado");
				$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
				$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
				$("#apellidos_asegurado").focus();
				valido = false;
			}
		}
	}

	if (identificacionAsegurado == "" || identificacionAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html("Ingrese una identificación");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#identificacion_asegurado").focus();
		valido = false;
	}

	if (nombreCompletoAsegurado == "" || nombreCompletoAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html(
		"Ingrese el nombre completo del asegurado");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#nombre_completo_asegurado").focus();
		valido = false;
	}

	if (tipoConsulta == "guardar") {
		if (aseguradoId == "" || aseguradoId == null) {
			tipoConsulta = "crear";
		} else {
			tipoConsulta = "actualizar";
		}
	}
	if (valido)
		$.ajax({
			url : "../EndosoBeneficiarioController",

			data : {
				"tipoConsulta" : tipoConsulta,
				"tipoIdentificacionAsegurado" : tipoIdentificacionAsegurado,
				"identificacionAsegurado" : identificacionAsegurado,
				"nombreAsegurado" : nombreAsegurado,
				"apellidoAsegurado" : apellidoAsegurado,
				"nombreCompletoAsegurado" : nombreCompletoAsegurado,
				"aseguradoId" : aseguradoId,
				"cotizacionId" : cotizacionId,
				"guardarAsegurado" : "1"
			},
			asyn : false,
			type : 'post',
			datatype : 'json',
			success : function(data) {
				if (data.success) {
					$.ajax({
						url : '../PymeCotizacionController',
						data : {
							"cotizacionId" : $("#cotizacion_id").text(),
							"etapaNumero" : "4",
							"tipoConsulta" : "cambiarEtapa"
						},
						asyn : false,
						type : 'post',
						datatype : 'json',
						success : function(datos) {
							$("#asegurado_id").val(data.aseguradoId);
							$("#mensajeGraboEndosoBeneficiario").html(
							"Se guard&oacute; el asegurado exitosamente");
							$("#msgPopupEndosoBeneficiarioGrabo").fadeIn("slow");
							$("#msgPopupEndosoBeneficiarioError").fadeOut("slow");
							if (tipoConsulta == 'eliminar') {
								$("#mensajeGraboEndosoBeneficiario").html(
								"Se elimin&oacute; el asegurado exitosamente");
								$("#asegurado_id").val('');
							}
						}
					});


				} else {
					$("#mensajeErrorEndosoBeneficiario").html(
					"No se pudo guardar el asegurado");
					if (tipoConsulta == 'eliminar')
						$("#mensajeGraboEndosoBeneficiario").html(
						"No se pudo eliminar el asegurado");
					$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
					$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
					alert(data.error);
				}
			}
		});
}

function guardarBeneficiario(tipoConsulta) {

	var beneficiario = $("#beneficiario").select2('val');
	var monto = $("#valor_endoso_beneficiario").val();
	var cotizacionId = $("#cotizacion_id").text();
	var endosoBeneficiarioId = $("#endoso_beneficiario_id").val();
	var valido = true;

	if (beneficiario == "" || beneficiario == null) {
		$("#mensajeErrorEndosoBeneficiario").html("Seleccione un beneficiario");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#beneficiario").focus();
		valido = false;
	}

	if (monto == "" || monto == null) {
		$("#mensajeErrorEndosoBeneficiario").html("Ingrese un monto");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#valor_endoso_beneficiario").focus();
		valido = false;
	} else {
		if (Number(monto) <= 0 || Number(monto) > Number($("#total_suma_asegurada_vh").val())) {
			$("#mensajeErrorEndosoBeneficiario").html("El monto debe ser menor al valor asegurado y mayor a 0");
			$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
			$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
			$("#valor_endoso_beneficiario").focus();
			valido = false;
		}
	}

	if (tipoConsulta == "guardar") {
		if (endosoBeneficiarioId == "" || endosoBeneficiarioId == null) {
			tipoConsulta = "crear";
		} else {
			tipoConsulta = "actualizar";
		}
	}
	if (valido)
		$.ajax({
			url : "../EndosoBeneficiarioController",

			data : {
				"tipoConsulta" : tipoConsulta,
				"beneficiarioId" : beneficiario,
				"monto" : monto,
				"codigo" : endosoBeneficiarioId,
				"cotizacionId" : cotizacionId,
				"guardarAsegurado" : "0"
			},
			type : 'post',
			datatype : 'json',
			success : function (data) {
				if (data.success) {
					$("#endoso_beneficiario_id").val(data.endosoBeneficiarioId);
					$("#mensajeGraboEndosoBeneficiario").html("Se guard&oacute; el beneficiario exitosamente");
					$("#msgPopupEndosoBeneficiarioGrabo").fadeIn("slow");
					$("#msgPopupEndosoBeneficiarioError").fadeOut("slow");
					if (tipoConsulta == 'eliminar') {
						$("#mensajeGraboEndosoBeneficiario").html("Se elimin&oacute; el beneficiario exitosamente");
						$("#endoso_beneficiario_id").val('');
						$("#valor_endoso_beneficiario").val('');
						$("#beneficiario").val('');
					}

				} else {
					$("#mensajeErrorEndosoBeneficiario").html("No se pudo guardar el beneficiario");
					$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
					$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
					if (tipoConsulta == 'eliminar')
						$("#mensajeGraboEndosoBeneficiario").html("No se pudo eliminar el beneficiario");

					alert(data.error);
				}
			}
		});
}

function guardarDatosUPLAJuridicaEnGanadero() {

	var objetoSocial = $("#objeto_social_juridica").val();
	// var ciudad = $("#ciudad_direccion_matriz_juridica").val();
	var zonaDireccionMatriz = $("#zona_direccion_matriz_juridica").val();
	var provinciaDireccionMatriz = $("#provincia_direccion_matriz_juridica")
	.val();
	var cantonDireccionMatriz = $("#canton_direccion_matriz_juridica").val();
	var parroquiaDireccionMatriz = $("#parroquia_direccion_matriz_juridica")
	.val();
	var ciudadDireccionMatriz = $("#ciudad_direccion_matriz_juridica").val();
	var callePrincipalDireccion = $("#calle_principal_direccion_juridica")
	.val();
	var numeroDireccion = $("#numero_direccion_juridica").val();
	var calleSecundariaDireccion = $("#calle_secundaria_direccion_juridica")
	.val();
	var referenciaDireccion = $("#referencia_direccion_juridica").val();
	/*
	 * var direccionSucursal = $("#direccion_sucursal_juridica").val(); var
	 * ciudadSucursal = $("#ciudad_sucursal_juridica").val();
	 */
	var telefono = $("#telefono_juridica").val();
	var fax = $("#fax_juridica").val();
	var salarioMensual = $("#salario_mensual_juridica").val();
	var activos = $("#activos_juridica").val();
	var otrosIngresos = $("#otros_ingresos_juridica").val();
	var pasivos = $("#pasivos_juridica").val();
	var egresos = $("#egresos_juridica").val();
	var patrimonio = $("#patrimonio_juridica").val();
	var ingresosEgresos = $("#ingresos_egresos_juridica").val();
	/*
	 * var actividad = $("#actividad_economica_juridica").select2("val"); var
	 * nombresRepresentante = $("#nombres_representante_juridica").val(); var
	 * apellidosRepresentante = $("#apellidos_representante_juridica").val();
	 * var tipoIdentificacionRepresentante =
	 * $("#tipo_identificacion_representante_juridica").val(); var
	 * identificacionRepresentante =
	 * $("#identificacion_representante_juridica").val(); var
	 * lugarNacimientoRepresentante =
	 * $("#lugar_nacimiento_representante_juridica").val(); var
	 * fechaNacimientoRepresentante =
	 * $("#fecha_nacimiento_representante_juridica").val(); var
	 * residenciaRepresentante = $("#residencia_representante_juridica").val();
	 * //var paisRepresentante = $("#pais_representante_juridica").val(); var
	 * provinciaRepresentante = $("#provincia_representante_juridica").val();
	 * var ciudadRepresentante = $("#ciudad_representante_juridica").val(); var
	 * telefonoRepresentante = $("#telefono_representante_juridica").val();
	 * 
	 * var expuestoRepresentante = $("#expuesto_representante_juridica").val();
	 * var cargoExpuestaRepresentante =
	 * $("#cargo_expuesta_representante_juridica").val(); var expuestoFamiliar =
	 * $("#expuesto_familiar_juridica").val(); var parentescoExpuestoFamiliar =
	 * $("#parentesco_expuesto_familiar_juridico").val(); var
	 * cargoExpuestoFamiliar = $("#cargo_expuesto_familiar_juridica").val(); var
	 * apellidoPaternoConyuge = $("#apellido_paterno_conyuge_juridica").val();
	 * var apellidoMaternoConyuge =
	 * $("#apellido_materno_conyuge_juridica").val(); var nombreConyuge =
	 * $("#nombre_conyuge_juridica").val(); var tipoIdentificacionConyugue =
	 * $("#tipo_identificacion_conyugue_juridica").val(); var
	 * identificacionConyuge = $("#identificacion_conyuge_juridica").val(); var
	 * esAsegurado = $("#es_asegurado_juridica").val(); var esBeneficiario =
	 * $("#es_beneficiario_juridica").val(); var tipoIdentificacionAsegurado =
	 * $("#tipo_identificacion_asegurado_juridica").val(); var
	 * identificacionAsegurado = $("#identificacion_asegurado_juridica").val();
	 * var nombreAsegurado = $("#nombre_asegurado_juridica").val(); var
	 * direccionAsegurado = $("#direccion_asegurado_juridica").val(); var
	 * telefonoAsegurado = $("#telefono_asegurado_juridica").val(); var
	 * celularAsegurado = $("#celular_asegurado_juridica").val(); var
	 * relacionAsegurado = $("#relacion_asegurado_juridica").val(); var
	 * tipoIdentificacionBeneficiario =
	 * $("#tipo_identificacion_beneficiario_juridica").val(); var
	 * identificacionBeneficiario =
	 * $("#identificacion_beneficiario_juridica").val(); var nombreBeneficiario =
	 * $("#nombre_beneficiario_juridica").val(); var direccionBeneficiario =
	 * $("#direccion_beneficiario_juridica").val(); var telefonoBeneficiario =
	 * $("#telefono_beneficiario_juridica").val(); var celularBeneficiario =
	 * $("#celular_beneficiario_juridica").val(); var relacionBeneficiario =
	 * $("#telefono_beneficiario_juridica").val();
	 */
	var identificacion = $("#identificacion_asegurado").val();
	var genero = $("#genero_representante_juridico").val();
	var mail = $("#mail_representante_juridico").val();
	var celularRepresentante = $("#celular_representante_legal").val();
	var cotizacion = $("#cotizacion_id").text();
	// var ciudadJuridica = $("#ciudad_juridica").val();

	$.ajax({
		url : '../UPLAController',
		data : {
			"identificacion" : identificacion,
			"tipoConsulta" : "guardarDatosJuridica",
			"objetoSocial" : objetoSocial,
			"zonaDireccionMatriz" : zonaDireccionMatriz,
			"provinciaDireccionMatriz" : provinciaDireccionMatriz,
			"cantonDireccionMatriz" : cantonDireccionMatriz,
			"parroquiaDireccionMatriz" : parroquiaDireccionMatriz,
			"ciudadDireccionMatriz" : ciudadDireccionMatriz,
			"callePrincipalDireccion" : callePrincipalDireccion,
			"numeroDireccion" : numeroDireccion,
			"calleSecundariaDireccion" : calleSecundariaDireccion,
			"referenciaDireccion" : referenciaDireccion,
			"salarioMensual" : salarioMensual,
			"activos" : activos,
			"otrosIngresos" : otrosIngresos,
			"pasivos" : pasivos,
			"egresos" : egresos,
			"patrimonio" : patrimonio,
			"ingresosEgresos" : ingresosEgresos,
			"celularRepresentante" : celularRepresentante,
			/*
			 * "direccionSucursal":direccionSucursal,
			 * "ciudadSucursal":ciudadSucursal,
			 */
			"telefono" : telefono,
			"fax" : fax,
			/*
			 * "actividad":actividad,
			 * "nombresRepresentante":nombresRepresentante,
			 * "apellidosRepresentante":apellidosRepresentante,
			 * "tipoIdentificacionRepresentante":tipoIdentificacionRepresentante,
			 * "identificacionRepresentante":identificacionRepresentante,
			 * "lugarNacimientoRepresentante":lugarNacimientoRepresentante,
			 * "fechaNacimientoRepresentante":fechaNacimientoRepresentante,
			 * "residenciaRepresentante":residenciaRepresentante,
			 * //"paisRepresentante":paisRepresentante,
			 * "provinciaRepresentante":provinciaRepresentante,
			 * "ciudadRepresentante":ciudadRepresentante,
			 * "telefonoRepresentante":telefonoRepresentante,
			 * 
			 * "expuestoRepresentante":expuestoRepresentante,
			 * "cargoExpuestaRepresentante":cargoExpuestaRepresentante,
			 * "expuestoFamiliar":expuestoFamiliar,
			 * "parentescoExpuestoFamiliar":parentescoExpuestoFamiliar,
			 * "cargoExpuestoFamiliar":cargoExpuestoFamiliar,
			 * "apellidoPaternoConyuge":apellidoPaternoConyuge,
			 * "apellidoMaternoConyuge":apellidoMaternoConyuge,
			 * "nombreConyuge":nombreConyuge,
			 * "tipoIdentificacionConyugue":tipoIdentificacionConyugue,
			 * "identificacionConyuge":identificacionConyuge,
			 * "esAsegurado":esAsegurado, "esBeneficiario":esBeneficiario,
			 * "tipoIdentificacionAsegurado":tipoIdentificacionAsegurado,
			 * "identificacionAsegurado":identificacionAsegurado,
			 * "nombreAsegurado":nombreAsegurado,
			 * "direccionAsegurado":direccionAsegurado,
			 * "telefonoAsegurado":telefonoAsegurado,
			 * "celularAsegurado":celularAsegurado,
			 * "relacionAsegurado":relacionAsegurado,
			 * "tipoIdentificacionBeneficiario":tipoIdentificacionBeneficiario,
			 * "identificacionBeneficiario":identificacionBeneficiario,
			 * "nombreBeneficiario":nombreBeneficiario,
			 * "direccionBeneficiario":direccionBeneficiario,
			 * "telefonoBeneficiario":telefonoBeneficiario,
			 * "celularBeneficiario":celularBeneficiario,
			 * "relacionBeneficiario":relacionBeneficiario,
			 */
			"genero" : genero,
			"mail" : mail,
			// "ciudad":ciudadJuridica,
			"cotizacion" : cotizacion
		},
		type : 'post',
		datatype : 'json',
		success : function(datos) {

		}
	});
}

function guardarDatosUPLANaturalEnGanadero() {
	var lugarNacimiento = $("#lugar_nacimiento_natural").val();
	var fechaNacimiento = $("#fecha_nacimiento_natural").val();
	var zonaDireccionCliente = $("#zona_direccion_natural").val();
	var callePrincipalCliente = $("#calle_principal_direccion_natural").val();
	var calleSecundariaCliente = $("#calle_secundaria_direccion_natural").val();
	var numeroDireccionCliente = $("#numero_direccion_natural").val();
	var refenciaDireccionCliente = $("#referencia_direccion_natural").val();
	var provincia = $("#provincia_direccion_cliente_natural").val();
	var canton = $("#canton_direccion_cliente_natural").val();
	var parroquia = $("#parroquia_direccion_cliente_natural").val();
	var ciudad = $("#ciudad_direccion_cliente_natural").val();
	var telefono = $("#telefono_cliente_natural").val();
	var celular = $("#celular_cliente_natural").val();
	var mail = $("#mail_cliente_natural").val();
	var tipoIdentificacionAsegurado = $("#tipo_identificacion_asegurado").val();
	var identificacionAsegurado = $("#identificacion_asegurado").val();
	var nombreAsegurado = $("#nombres_asegurado").val();
	var apellidosAsegurado = $("#apellidos_asegurado").val();
	var domicilioAsegurado = $("#direccion_asegurado_natural").val();
	var salarioMensual = $("#salario_mensual_natural").val();
	var activo = $("#activos_natural").val();
	var otrosIngresos = $("#otros_ingresos_natural").val();
	var pasivos = $("#pasivos_natural").val();
	var egresos = $("#egresos_natural").val();
	var patrimonio = $("#patrimonio_natural").val();
	var ingresosEgresos = $("#ingresos_egresos_natural").val();
	var cotizacion = $("#cotizacion_id").text();
	var cargoOcupa = $("#cargo_ocupa_cliente_natural").val();
	/*
	 * var actividad = $("#actividad_economica_cliente_natural").select2("val");
	 * var tipoActividad = $("#tipo_actividad_cliente_natural").val(); var
	 * tipoRamoContrata = $("#ramo_contrata_cliente_natural").val(); var
	 * expuesto = $("#expuesto_cliente_natural").val(); var cargoExpuesta =
	 * $("#cargo_expuesto_cliente_natural").val(); var expuestoFamiliar =
	 * $("#expuesto_familiar_natural").val(); var parentescoFamiliar =
	 * $("#parentesco_expuesto_familiar_natural").val(); var cargoFamiliar =
	 * $("#cargo_expuesto_familiar_natural").val(); var apellidoPaternoConyuge =
	 * $("#apellido_paterno_conyuge_natural").val(); var apellidoMaternoConyuge =
	 * $("#apellido_materno_conyuge_natural").val(); var nombresConyuge =
	 * $("#nombre_conyuge_natural").val(); var tipoIdentificacionConyuge =
	 * $("#tipo_identificacion_conyuge_natural").val(); var
	 * identificacionConyuge = $("#identificacion_conyuge_natural").val();
	 * 
	 * var esAsegurado = $("#es_asegurado_natural").val(); var esBeneficiario =
	 * $("#es_beneficiario_natural").val();
	 * 
	 * var relacionAsegurado = $("#relacion_asegurado_natural").val(); var
	 * tipoIdentificacionBeneficiario =
	 * $("#tipo_identificacion_beneficiario_natural").val(); var
	 * identificacionBeneficiario =
	 * $("#identificacion_beneficiario_natural").val(); var nombreBeneficiario =
	 * $("#nombre_beneficiario_natural").val(); var domicilioBeneficiario =
	 * $("#direccion_beneficiario_natural").val(); var telefonoBeneficiario =
	 * $("#telefono_beneficiario_natural").val(); var celularBeneficiario =
	 * $("#celular_beneficiario_natural").val(); var relacionBeneficiario =
	 * $("#relacion_beneficiario_natural").val();
	 */

	$.ajax({
		url : '../UPLAController',
		data : {
			"identificacion" : identificacionAsegurado,
			"tipoConsulta" : "guardarDatosNatural",
			"lugarNacimiento" : lugarNacimiento,
			"fechaNacimiento" : fechaNacimiento,
			"zonaDireccionCliente" : zonaDireccionCliente,
			"callePrincipalCliente" : callePrincipalCliente,
			"calleSecundariaCliente" : calleSecundariaCliente,
			"numeroDireccionCliente" : numeroDireccionCliente,
			"refenciaDireccionCliente" : refenciaDireccionCliente,
			"provincia" : provincia,
			"canton" : canton,
			"parroquia" : parroquia,
			"ciudad" : ciudad,
			"telefono" : telefono,
			"celular" : celular,
			"tipoIdentificacionAsegurado" : tipoIdentificacionAsegurado,
			"identificacionAsegurado" : identificacionAsegurado,
			"nombreAsegurado" : nombreAsegurado,
			"apellidosAsegurado" : apellidosAsegurado,
			"domicilioAsegurado" : domicilioAsegurado,
			"salarioMensual" : salarioMensual,
			"activo" : activo,
			"otrosIngresos" : otrosIngresos,
			"pasivos" : pasivos,
			"egresos" : egresos,
			"patrimonio" : patrimonio,
			"ingresosEgresos" : ingresosEgresos,
			"cargoOcupa" : cargoOcupa,
			/*
			 * "actividad" : actividad, "tipoActividad" : tipoActividad,
			 * "tipoRamoContrata" : tipoRamoContrata, "expuesto" : expuesto,
			 * "cargoExpuesta" : cargoExpuesta, "expuestoFamiliar" :
			 * expuestoFamiliar, "parentescoFamiliar" : parentescoFamiliar,
			 * "cargoFamiliar" : cargoFamiliar, "apellidoPaternoConyuge" :
			 * apellidoPaternoConyuge, "apellidoMaternoConyuge" :
			 * apellidoMaternoConyuge, "nombresConyuge" : nombresConyuge,
			 * "tipoIdentificacionConyuge" : tipoIdentificacionConyuge,
			 * "identificacionConyuge" : identificacionConyuge, "esBeneficiario" :
			 * esBeneficiario,
			 * 
			 * "relacionAsegurado" : relacionAsegurado,
			 * "tipoIdentificacionBeneficiario" :
			 * tipoIdentificacionBeneficiario, "identificacionBeneficiario" :
			 * identificacionBeneficiario, "nombreBeneficiario" :
			 * nombreBeneficiario, "domicilioBeneficiario" :
			 * domicilioBeneficiario, "telefonoBeneficiario" :
			 * telefonoBeneficiario, "celularBeneficiario" :
			 * celularBeneficiario, "relacionBeneficiario" :
			 * relacionBeneficiario,
			 */
			"mail" : mail,
			"cotizacion" : cotizacion
		},
		type : 'post',
		datatype : 'json',
		success : function(datos) {

		}
	});
}

function guardarAsegurado(tipoConsulta) {
	var tipoIdentificacionAsegurado = $("#tipo_identificacion_asegurado").val();
	var identificacionAsegurado = $("#identificacion_asegurado").val();
	var nombreAsegurado = $("#nombres_asegurado").val();
	var apellidoAsegurado = $("#apellidos_asegurado").val();
	var nombreCompletoAsegurado = $("#nombre_completo_asegurado").val();
	var cotizacionId = $("#cotizacion_id").text();
	var aseguradoId = $("#asegurado_id").val();
	var valido = true;

	if (tipoIdentificacionAsegurado == ""
		|| tipoIdentificacionAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html(
		"Seleccione un tipo de identificación");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#tipo_identificacion_asegurado").focus();
		valido = false;
	} else {
		if (tipoIdentificacionAsegurado != '4') {
			nombreCompletoAsegurado = apellidoAsegurado + ' ' + nombreAsegurado;
			if (nombreAsegurado == "" || nombreAsegurado == null) {
				$("#mensajeErrorEndosoBeneficiario").html(
				"Ingrese el nombre del asegurado");
				$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
				$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
				$("#nombres_asegurado").focus();
				valido = false;
			}
			if (apellidoAsegurado == "" || apellidoAsegurado == null) {
				$("#mensajeErrorEndosoBeneficiario").html(
				"Ingrese el apellido del asegurado");
				$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
				$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
				$("#apellidos_asegurado").focus();
				valido = false;
			}
		}
	}

	if (identificacionAsegurado == "" || identificacionAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html("Ingrese una identificación");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#identificacion_asegurado").focus();
		valido = false;
	}

	if (nombreCompletoAsegurado == "" || nombreCompletoAsegurado == null) {
		$("#mensajeErrorEndosoBeneficiario").html(
		"Ingrese el nombre completo del asegurado");
		$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
		$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
		$("#nombre_completo_asegurado").focus();
		valido = false;
	}

	if (tipoConsulta == "guardar") {
		if (aseguradoId == "" || aseguradoId == null) {
			tipoConsulta = "crear";
		} else {
			tipoConsulta = "actualizar";
		}
	}
	if (valido)
		$.ajax({
			url : "../EndosoBeneficiarioController",

			data : {
				"tipoConsulta" : tipoConsulta,
				"tipoIdentificacionAsegurado" : tipoIdentificacionAsegurado,
				"identificacionAsegurado" : identificacionAsegurado,
				"nombreAsegurado" : nombreAsegurado,
				"apellidoAsegurado" : apellidoAsegurado,
				"nombreCompletoAsegurado" : nombreCompletoAsegurado,
				"aseguradoId" : aseguradoId,
				"cotizacionId" : cotizacionId,
				"guardarAsegurado" : "1"
			},
			type : 'post',
			datatype : 'json',
			success : function(data) {
				if (data.success) {
					$.ajax({
						url : '../PymeCotizacionController',
						data : {
							"cotizacionId" : $("#cotizacion_id").text(),
							"etapaNumero" : "4",
							"tipoConsulta" : "cambiarEtapa"
						},
						asyn : false,
						type : 'post',
						datatype : 'json',
						success : function(datos) {
							$("#asegurado_id").val(data.aseguradoId);
							$("#mensajeGraboEndosoBeneficiario").html(
							"Se guard&oacute; el asegurado exitosamente");
							$("#msgPopupEndosoBeneficiarioGrabo").fadeIn("slow");
							$("#msgPopupEndosoBeneficiarioError").fadeOut("slow");
							if (tipoConsulta == 'eliminar') {
								$("#mensajeGraboEndosoBeneficiario").html(
								"Se elimin&oacute; el asegurado exitosamente");
								$("#asegurado_id").val('');
							}
						}
					});
				} else {
					$("#mensajeErrorEndosoBeneficiario").html(
					"No se pudo guardar el asegurado");
					if (tipoConsulta == 'eliminar')
						$("#mensajeGraboEndosoBeneficiario").html(
						"No se pudo eliminar el asegurado");
					$("#msgPopupEndosoBeneficiarioGrabo").fadeOut("slow");
					$("#msgPopupEndosoBeneficiarioError").fadeIn("slow");
					alert(data.error);
				}
			}
		});
}

function guardarPago(valor) {
	var tipoConsulta = "crear";

	$("#save-pagoContado").attr("disabled", "disabled");
	$("#save-pagoDebitoBanco").attr("disabled", "disabled");
	$("#save-pagoDebitoTarjeta").attr("disabled", "disabled");
	$("#save-pagoCuotas").attr("disabled", "disabled");
	$("#msgPopupPagoGrabo").hide();
	$("#msgPopupPagoError").hide();
	if (parseInt($("#codigoPagoRegistrado").val()) > 0)
		tipoConsulta = "actualizar";

	if (valor == 'contado') {
		$.ajax({
			url : '../PagoController',
			data : {
				"codigoCotizacion" : $("#cotizacion_id").text(),
				"codigoFormaPago" : $("#cboFpFormaPago").val(),
				"codigoInstFinanciera" : "",
				"plazo" : "1",
				"fechaPago" : "",
				"formaPagoSeleccionada" : "1",
				"tipoConsulta" : "guardarPorCotizacion"
			},
			asyn : false,
			type : 'post',
			datatype : 'json',
			success : function(datos) {
				if (datos.success) {
					$.ajax({
						url : '../PymeCotizacionController',
						data : {
							"cotizacionId" : $("#cotizacion_id").text(),
							"etapaNumero" : "4",
							"tipoConsulta" : "cambiarEtapa"
						},
						asyn : false,
						type : 'post',
						datatype : 'json',
						success : function(datos) {
							if (datos.success) {
								$("#codigoPagoRegistrado").val(datos.pagoId);
								$("#msgPopupPagoGrabo").show();
								$("#msgPopupPago").attr("class", "alert alert-success").html("La forma de pago se ha registrado correctamente.").fadeIn("slow");
							}
						}
					});
				} else {
					$("#codigoPagoRegistrado").val("");
					$("#msgPopupPago").attr("class",
					"alert alert-danger").html(datos.error)
					.fadeIn("slow");
				}
				$("#save-pagoContado").removeAttr("disabled");
				$("#save-pagoDebitoBanco").removeAttr("disabled");
				$("#save-pagoDebitoTarjeta").removeAttr("disabled");
				$("#save-pagoCuotas").removeAttr("disabled");

			}
		});
	}

	if (valor == 'banco') {
		var bandera = true;
		$("#save-pagoDebitoBanco")
		.parent()
		.parent()
		.parent()
		.find(':input')
		.each(
				function() {
					var elemento = this;
					if (elemento.value == ""
						&& elemento.type != "button") {
						alert("Todos los campos del formulario son requerido. Por favor verifique que esten llenos.");
						$("#" + elemento.id).css('border-color', 'red');
						bandera = false;
						$("#save-pagoContado").removeAttr("disabled");
						$("#save-pagoDebitoBanco").removeAttr(
						"disabled");
						$("#save-pagoDebitoTarjeta").removeAttr(
						"disabled");
						$("#save-pagoCuotas").removeAttr("disabled");
						return false;
					}
				});

		if ((Date.parse($("#txtfechaInicialpago").val())) < (Date
				.parse(new Date().setHours(0, 0, 0, 0)))) {
			alert("La fecha de inicio de pagos no puede ser menor a la fecha actual.");
			$("#txtfechaInicialpago").css('border-color', 'red');
			bandera = false;
			$("#save-pagoContado").removeAttr("disabled");
			$("#save-pagoDebitoBanco").removeAttr("disabled");
			$("#save-pagoDebitoTarjeta").removeAttr("disabled");
			$("#save-pagoCuotas").removeAttr("disabled");
			return false;
		}

		if (bandera) {
			var plazo = Number($("#bancoPlazo").val());
			// if($("#txtcuotaInicialbancoPlazo").val()!=""&&$("#txtcuotaInicialbancoPlazo").val()!="0")
			// plazo++;

			$.ajax({
				url : '../PagoController',
				data : {
					"codigoCotizacion" : $("#cotizacion_id").text(),
					"formaPagoSeleccionada" : "2",
					"codigoInstFinanciera" : $("#bancoId").val(),
					"tipoCuenta" : $("#bancoTipoCuenta").val(),
					"numCuenta" : $("#bancoNumeroCuenta").val(),
					"titular" : $("#bancoTitular").val(),
					"tipoIdentificacionId" : $(
					"#tipo_identificacion_banco").val(),
					"identificacion" : $("#bancoIdentificacion").val(),
					"plazo" : plazo,
					"fechaPago" : $("#txtfechaInicialpago").val(),
					"cuotaInicial" : $("#txtcuotaInicialbancoPlazo")
					.val(),
					"fechaPago" : $("#txtfechaInicialpago").val(),
					"tipoConsulta" : "guardarPorCotizacion"
				},
				type : 'post',
				datatype : 'json',
				success : function(datos) {
					if (datos.success) {
						$.ajax({
							url : '../PymeCotizacionController',
							data : {
								"cotizacionId" : $("#cotizacion_id").text(),
								"etapaNumero" : "4",
								"tipoConsulta" : "cambiarEtapa"
							},
							asyn : false,
							type : 'post',
							datatype : 'json',
							success : function(datos) {
								$("#codigoPagoRegistrado").val(datos.pagoId);
								$("#msgPopupPagoGrabo").show();
								$("#msgPopupPago").attr("class", "alert alert-success").html("La forma de pago se ha registrado correctamente.").fadeIn("slow");
							}
						});
					} else {
						$("#codigoPagoRegistrado").val("");
						$("#msgPopupPago").attr("class",
						"alert alert-danger").html(datos.error)
						.fadeIn("slow");
					}
					$("#save-pagoContado").removeAttr("disabled");
					$("#save-pagoDebitoBanco").removeAttr("disabled");
					$("#save-pagoDebitoTarjeta").removeAttr("disabled");
					$("#save-pagoCuotas").removeAttr("disabled");

				}
			});

		}
	}

	if (valor == 'tarjeta') {

		var plazo = Number($("#tarjetaPlazo").val());
		// if($("#txtcuotaInicialbancoPlazo").val()!=""&&$("#txtcuotaInicialbancoPlazo").val()!="0")
		// plazo++;

		var bandera = true;
		$("#save-pagoDebitoTarjeta")
		.parent()
		.parent()
		.parent()
		.find(':input')
		.each(
				function() {
					var elemento = this;
					if (elemento.value == ""
						&& elemento.type != "button") {
						alert("Todos los campos del formulario son requerido. Por favor verifique que esten llenos.");
						$("#" + elemento.id).css('border-color', 'red');
						bandera = false;
						$("#save-pagoContado").removeAttr("disabled");
						$("#save-pagoDebitoBanco").removeAttr(
						"disabled");
						$("#save-pagoDebitoTarjeta").removeAttr(
						"disabled");
						$("#save-pagoCuotas").removeAttr("disabled");

						return false;
					}
				});

		if (bandera) {
			$
			.ajax({
				url : '../PagoController',
				data : {
					"codigoCotizacion" : $("#cotizacion_id").text(),
					"formaPagoSeleccionada" : "3",
					"codigoInstFinanciera" : $("#tarjetaId").val(),
					"numCuenta" : $("#tarjetaNumero").val(),
					"titular" : $("#tarjetaTitular").val(),
					"tipoCuenta" : 'T',
					"tipoIdentificacionId" : $(
					"#tipo_identificacion_tarjeta").val(),
					"identificacion" : $("#tarjetaIdentificacion")
					.val(),
					"tarjetaAnioExp" : $("#tarjetaAnioExp").val(),
					"tarjetaMesExp" : $("#tarjetaMesExp").val(),
					"plazo" : plazo,
					"fechaPago" : $("#txtfechaInicialpagoTarjeta")
					.val(),
					"cuotaInicial" : $("#txtcuotaInicialtarjetaPlazo")
					.val(),
					"tipoConsulta" : "guardarPorCotizacion"
				},
				type : 'post',
				datatype : 'json',
				success : function(datos) {
					if (datos.success) {
						$.ajax({
							url : '../PymeCotizacionController',
							data : {
								"cotizacionId" : $("#cotizacion_id").text(),
								"etapaNumero" : "4",
								"tipoConsulta" : "cambiarEtapa"
							},
							asyn : false,
							type : 'post',
							datatype : 'json',
							success : function(datos) {
								$("#codigoPagoRegistrado").val(datos.pagoId);
								$("#msgPopupPagoGrabo").show();
								$("#msgPopupPago").attr("class", "alert alert-success").html("La forma de pago se ha registrado correctamente.").fadeIn("slow");
							}
						});
					} else {
						$("#codigoPagoRegistrado").val("");
						$("#msgPopupPago").attr("class",
						"alert alert-danger").html(datos.error)
						.fadeIn("slow");
					}
					$("#save-pagoContado").removeAttr("disabled");
					$("#save-pagoDebitoBanco").removeAttr("disabled");
					$("#save-pagoDebitoTarjeta").removeAttr("disabled");
					$("#save-pagoCuotas").removeAttr("disabled");

				}
			});
		}
	}

	if (valor == 'cuota') {
		var plazo = Number($("#cboFpPlazo").val());
		// if($("#txtcuotaInicialbancoPlazo").val()!=""&&$("#txtcuotaInicialbancoPlazo").val()!="0")
		// plazo++;

		var bandera = true;
		$("#save-pagoCuotas")
		.parent()
		.parent()
		.parent()
		.find(':input')
		.each(
				function() {
					var elemento = this;
					if (elemento.value == "0"
						&& elemento.type != "button") {
						alert("Todos los campos del formulario son requerido. Por favor verifique que esten llenos.");
						$("#" + elemento.id).css('border-color', 'red');
						bandera = false;
						$("#save-pagoContado").removeAttr("disabled");
						$("#save-pagoDebitoBanco").removeAttr(
						"disabled");
						$("#save-pagoDebitoTarjeta").removeAttr(
						"disabled");
						$("#save-pagoCuotas").removeAttr("disabled");

						return false;
					}
				});

		if (bandera && $("#cboFpPlazo").val() != "0") {
			var listadoCheques = "";

			$(".detalleChequesPagos").each(function() {
				if ($(this).val() != "")
					listadoCheques = listadoCheques + $(this).val() + ",";
				else
					listadoCheques = listadoCheques + " ,";
			});

			$.ajax({
				url : '../PagoController',
				data : {
					"codigoCotizacion" : $("#cotizacion_id").text(),
					"tipoIdentificacionId" : $(
					"#tarjetaTipoIdentificacion").val(),
					"identificacion" : $("#tarjetaIdentificacion")
					.val(),
					"plazo" : plazo,
					"formaPagoSeleccionada" : "4",
					"tipoConsulta" : "guardarPorCotizacion",
					"cuotaInicial" : $("#txtcuotaInicial").val()
				},
				type : 'post',
				datatype : 'json',
				success : function(datos) {
					if (datos.success) {
						$.ajax({
							url : '../PymeCotizacionController',
							data : {
								"cotizacionId" : $("#cotizacion_id").text(),
								"etapaNumero" : "4",
								"tipoConsulta" : "cambiarEtapa"
							},
							asyn : false,
							type : 'post',
							datatype : 'json',
							success : function(datos) {
								$("#codigoPagoRegistrado").val(datos.pagoId);
								$("#msgPopupPagoGrabo").show();
								$("#msgPopupPago").attr("class", "alert alert-success").html("La forma de pago se ha registrado correctamente.").fadeIn("slow");
							}
						});
					} else {
						$("#codigoPagoRegistrado").val("");
						$("#msgPopupPago").attr("class",
						"alert alert-danger").html(datos.error)
						.fadeIn("slow");
					}
					$("#save-pagoContado").removeAttr("disabled");
					$("#save-pagoDebitoBanco").removeAttr("disabled");
					$("#save-pagoDebitoTarjeta").removeAttr("disabled");
					$("#save-pagoCuotas").removeAttr("disabled");
				}
			});
		}
	}
}
