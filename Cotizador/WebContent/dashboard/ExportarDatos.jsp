<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function exportar(){
		$.ajax({
			url : '../AgriExportarDatos',
			data : {
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
			}
		});
	}
</script>
</head>
<body>
<table>
	<tr>
		<td>Está opción permite exportar los datos para poder usarlos en el sistema Off-Line.</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><button id="btnExportar" onclick="exportar()">Exportar Datos Ahora</button></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</body>
</html>