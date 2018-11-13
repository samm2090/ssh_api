package pe.com.human.api.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.human.api.service.EmpleadoService;

public class ConfiguracionDataSource implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(EmpleadoService.class);
	
	private int id;
	private String nombre;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	public List<ConfiguracionDataSource> listarConfiguracionJson(String resource) {

		ObjectMapper mapper = new ObjectMapper();
		List<ConfiguracionDataSource> configuraciones = null;
		try {
			configuraciones = mapper.readValue(getClass().getResourceAsStream(resource),
					new TypeReference<List<ConfiguracionDataSource>>() {
					});

		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		logger.info(configuraciones);

		return configuraciones;
	}

	public ConfiguracionDataSource(String resource, String baseDatos) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ConfiguracionDataSource> configuraciones = mapper.readValue(getClass().getResourceAsStream(resource),
					new TypeReference<List<ConfiguracionDataSource>>() {
					});

			for (ConfiguracionDataSource configuracion : configuraciones) {
				if (configuracion.getNombre().equals(baseDatos)) {
					this.nombre = configuracion.getNombre();
					this.driverClassName = configuracion.getDriverClassName();
					this.url = configuracion.getUrl();
					this.username = configuracion.getUsername();
					this.password = configuracion.getPassword();
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfiguracionDataSource() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
