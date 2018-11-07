package pe.com.human.servicios.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Component
public class PropertiesReader {

	public Properties leerPropiedad(String nombreArchivo) {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nombreArchivo);

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
