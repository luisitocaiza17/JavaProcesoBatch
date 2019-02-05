<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Archivos Planos- QBE</title>

<!--script src="../static/js/jquery.min.js"></script-->

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
				url : '../ImportarCAPController',
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
            if (this.extension.toLowerCase() != ".xls" && this.extension.toLowerCase() != ".xlsx") {
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
	            saveUrl: "../ImportarCAPController"	           
	        },
	        multiple: false,
	        upload: onUpload,
	        success: onSuccess
	        //cancel: onCancel
		});
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
				realizadas en <strong>Archivos Planos</strong>.</td>
		</tr>
		<tr>
			<td><br /> <br /></td>
		</tr>
		<tr>
			<td colspan="2">Archivos planos:</td>
		</tr>
		<tr>
			<td colspan="2"><input name="files" id="files" type="file" /></td>
		</tr>
		
		<tr>
			<td><br /> <br /></td>
		</tr>
		<tr>
			<td colspan="2"><button id="btnExportar" class="btn btn-primary" onclick="importar()">Importar
					Ahora</button></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</body>
</html>