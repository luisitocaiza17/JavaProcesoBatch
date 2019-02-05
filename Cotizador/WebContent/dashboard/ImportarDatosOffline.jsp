<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Datos Offline - QBE</title>

<script src="../static/js/jquery.min.js"></script>

<!-- KENDO -->
<link rel="stylesheet" href="../static/css/Kendo/kendo.common.min.css" />
<link rel="stylesheet" href="../static/css/Kendo/kendo.blueopal.min.css" />
<script src="../static/js/Kendo/kendo.all.min.js"></script>
<script src="../static/js/Kendo/kendo.web.min.js"></script>
	
<script type="text/javascript">
var tipoConsulta= "";
var nombreArchivo = "";
	function importar() {
		if(nombreArchivo!==""){
			tipoConsulta = "importar";
			$.ajax({
				url : '../AgriImportarDatosOfflineController',
				data : {
					"tipoConsulta" : "importar",
					"nombreArchivo" : nombreArchivo
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					alert(data.mensaje);
				}
			});			
		}else{
			alert("Seleccione un archivo");
		}
		
	}
    
    function onUpload(e) {
        // Array with information about the uploaded files
        var files = e.files;

        // Check the extension of each file and abort the upload if it is not .jpg
        $.each(files, function () {
            if (this.extension.toLowerCase() != ".config") {
                alert("Por favor seleccioanar el achivo correcto.");
                e.preventDefault();
            }
        });
    }
    
    function onSuccess(e) {
    	nombreArchivo = e.files[0].name;
    }

	$(document).ready(function() {
		$("#files").kendoUpload({
			async: {
	            saveUrl: "../AgriImportarDatosOfflineController"	           
	        },
	        multiple: false,
	        upload: onUpload,
	        success: onSuccess
	        //cancel: onCancel
		});
		
		
		
		
		/* $.ajax({
			url : '../PuntoVentaController',
			data : {
				"tipoConsulta" : "puntosVentaXProducto",
				"tipoObjeto" : "Agricola",
				"grupoPorProductoId" : "159",

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
			}
		}); */
		
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

	<table>
		<tr>
			<td colspan="2">Está opción le permite importar las cotizaciones
				realizadas en el cotizador Off-Line.</td>
		</tr>
		<tr>
			<td><br />
			<br /></td>
		</tr>
		<tr>
			<td colspan="2">Informe de Inspección:</td>
		</tr>
		<tr>
			<td colspan="2"><input name="files" id="files" type="file"/></td>
		</tr>
		<!-- <tr>
		<td>Seleccione el punto de venta al que usted pertence:</td>
		<td><select name="punto_venta" id="punto_venta" required="required"></select></td>
	</tr> -->
		<tr>
			<td><br />
			<br /></td>
		</tr>
		<tr>
			<td colspan="2"><button id="btnExportar" onclick="importar()">Importar
					Ahora</button></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</body>
</html>