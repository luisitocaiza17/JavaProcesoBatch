package com.qbe.cotizador.servlets.producto.agricola;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.sun.xml.wss.core.Timestamp;

public class ReadExelFile {

	public static List<CotizacionArchivoPlano> readXLSFile(String rutaArchivo) throws IOException, ParseException		{
		List<CotizacionArchivoPlano> cotizacionArray = new ArrayList<CotizacionArchivoPlano>();
		InputStream ExcelFileToRead = new FileInputStream(rutaArchivo);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();
		int i = 0;

		while (rows.hasNext())
		{

			if(i<1){
				rows.next();
				i++;				
				continue;				
			}				

			row=(HSSFRow) rows.next();

			//Iterator cells = row.cellIterator();
			CotizacionArchivoPlano cotizacion = new CotizacionArchivoPlano();

			//cotizacion.setCanal(row.getCell(0).getStringCellValue());
			cotizacion.setCanal(readCell(row.getCell(0)));
			cotizacion.setCliente(readCell(row.getCell(1)));
			cotizacion.setClienteIdentificacion(readCell(row.getCell(2)));
			cotizacion.setMontoAsegurado(Double.parseDouble(readCell(row.getCell(3))));
			cotizacion.setFechaSolicitud(stringToTimestamp(readCell(row.getCell(4))));
			cotizacion.setFechaSiembra(stringToTimestamp(readCell(row.getCell(5))));
			cotizacion.setTipoCultivoNombre(readCell(row.getCell(6)));
			cotizacion.setNumeroHectareasAseguradas(Double.parseDouble(readCell(row.getCell(7))));
			cotizacion.setNumeroHectareasLote(Double.parseDouble(readCell(row.getCell(8))));
			if(readCell(row.getCell(9)).equalsIgnoreCase("SI"))
				cotizacion.setEsTecnificado(true);
			else
				cotizacion.setEsTecnificado(false);
			cotizacion.setProvinciaNombre(readCell(row.getCell(10)));
			cotizacion.setCantonNombre(readCell(row.getCell(11)));
			cotizacion.setParroquiaNombre(readCell(row.getCell(12)));
			cotizacion.setUbicacionCultivo(readCell(row.getCell(13)));
			cotizacionArray.add(cotizacion);		
		}
		return cotizacionArray;

	}



	public static List<CotizacionArchivoPlano> readXLSXFile(String rutaArchivo) throws IOException, ParseException
	{

		List<CotizacionArchivoPlano> cotizacionArray = new ArrayList<CotizacionArchivoPlano>();
		InputStream ExcelFileToRead = new FileInputStream(rutaArchivo);
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);	

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		int i = 0;

		while (rows.hasNext())
		{

			if(i<1){
				rows.next();
				i++;				
				continue;				
			}				

			row=(XSSFRow) rows.next();

			//Iterator cells = row.cellIterator();
			CotizacionArchivoPlano cotizacion = new CotizacionArchivoPlano();

			//cotizacion.setCanal(row.getCell(0).getStringCellValue());
			cotizacion.setCanal(readCell(row.getCell(0)));
			cotizacion.setCliente(readCell(row.getCell(1)));
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			cotizacion.setClienteIdentificacion(readCell(row.getCell(2)));
			cotizacion.setMontoAsegurado(Double.parseDouble(readCell(row.getCell(3))));
			cotizacion.setFechaSolicitud(stringToTimestamp(readCell(row.getCell(4))));
			cotizacion.setFechaSiembra(stringToTimestamp(readCell(row.getCell(5))));
			cotizacion.setTipoCultivoNombre(readCell(row.getCell(6)));
			cotizacion.setNumeroHectareasAseguradas(Double.parseDouble(readCell(row.getCell(7))));
			cotizacion.setNumeroHectareasLote(Double.parseDouble(readCell(row.getCell(8))));
			if(readCell(row.getCell(9)).equalsIgnoreCase("SI"))
				cotizacion.setEsTecnificado(true);
			else
				cotizacion.setEsTecnificado(false);
			cotizacion.setProvinciaNombre(readCell(row.getCell(10)));
			cotizacion.setCantonNombre(readCell(row.getCell(11)));
			cotizacion.setParroquiaNombre(readCell(row.getCell(12)));
			cotizacion.setUbicacionCultivo(readCell(row.getCell(13)));
			cotizacionArray.add(cotizacion);

		}
		return cotizacionArray;

	}

	public static String readCell (XSSFCell cell){		
		String val = "";
		if (cell!=null) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				val = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(HSSFDateUtil.isCellDateFormatted(cell))
					val = formatter.format(cell.getDateCellValue());
				else
					val = Double.toString(cell.getNumericCellValue());
				break;		        
			}
		}
		return val;		
	}

	public static String readCell (HSSFCell cell){		
		String val = "";
		if (cell!=null) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				val = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(HSSFDateUtil.isCellDateFormatted(cell))		        		
					val = formatter.format(cell.getDateCellValue());
				else
					val = Double.toString(cell.getNumericCellValue());
				break;
			}
		}
		return val;		
	}


	public static java.sql.Timestamp stringToTimestamp(String str_date) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			// you can change format of date
			Date date = formatter.parse(str_date);
			java.sql.Timestamp timeStampDate = new java.sql.Timestamp(date.getTime());

			return timeStampDate;
		} catch (Exception e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}

}
