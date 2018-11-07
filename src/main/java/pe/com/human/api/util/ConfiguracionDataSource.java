package pe.com.human.api.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfiguracionDataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String driverClassName;
	private String url;
	private String username;
	private String password;

	public List<ConfiguracionDataSource> listarConfiguracionJson(String resource) {

		ObjectMapper mapper = new ObjectMapper();
		List<ConfiguracionDataSource> configuraciones = null;
		try {
			configuraciones = mapper.readValue(new File(getClass().getResource("/bases_datos.json").getPath()),
					new TypeReference<List<ConfiguracionDataSource>>() {
					});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return configuraciones;
	}

	public ConfiguracionDataSource(String resource, String baseDatos) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ConfiguracionDataSource> configuraciones = mapper.readValue(
					new File(getClass().getResource("/bases_datos.json").getPath()),
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
