package com.qbe.cotizador.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class Reportes {
	
	public Reportes() {        
    }

	//evaldez metodo que devuelve la conexión a la base 
	public Connection getConnection() {
		Connection conn = null;			
		
		try {
			DataSource dataSource = (DataSource) new InitialContext().lookup("jdbc/MySQLResource");
			conn = dataSource.getConnection();
		} catch (SQLException | NamingException e) {			
			e.printStackTrace();
		}							
		return conn;
	} 
	
}
