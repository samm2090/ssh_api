package pe.com.human.api.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ConexionBaseDatos {
	
	public static Connection obtenerConexion(ConfiguracionDataSource configuracion) throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(configuracion.getDriverClassName());
        dataSource.setUrl(configuracion.getUrl());
        dataSource.setUsername(configuracion.getUsername());
        dataSource.setPassword(configuracion.getPassword());
		return dataSource.getConnection();
	}
}
