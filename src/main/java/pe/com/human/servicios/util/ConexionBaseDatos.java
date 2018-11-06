package pe.com.human.servicios.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import pe.com.human.servicios.model.ConfiguracionDataSource;

public class ConexionBaseDatos {
	
	public static Connection obtenerConexion(ConfiguracionDataSource configuracion) throws SQLException {
		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(configuracion.getDriverClassName());
		datasource.setUrl(configuracion.getUrl());
		datasource.setUsername(configuracion.getUsername());
		datasource.setPassword(configuracion.getPassword());
		
		return datasource.getConnection();
	}
}
