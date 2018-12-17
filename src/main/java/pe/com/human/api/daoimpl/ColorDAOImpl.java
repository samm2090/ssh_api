package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.ColorDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Colores;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.Hex;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

/**
 * 
 * @author smuroy
 *
 */
@Repository
public class ColorDAOImpl implements ColorDAO {

	@Autowired
	PropertiesReader lector;

	@Override
	public List<Colores> listarColoresXCompania(Compania compania) {
		List<Colores> colores = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/estilo.query").getProperty("listarColoresXCompania");

		try {
			ConfiguracionDataSource configuracion = new ConfiguracionDataSource();

			conexion = ConexionBaseDatos.obtenerConexion(configuracion);

			PreparedStatement listarColores = conexion.prepareStatement(query);
			listarColores.setString(1, compania.getId());

			ResultSet rs = listarColores.executeQuery();
			colores = new ArrayList<>();
			while (rs.next()) {
				Hex hex = new Hex();
				hex.setHex(rs.getString("COC_D_HEX"));

				Custom custom = new Custom();
				custom.setHex(hex);

				Default default1 = new Default();
				default1.setNombre(rs.getString("COT_D_TIPO"));

				Color color = new Color();
				color.setTipo(rs.getString("CTP_D_DECRIPCION"));
				color.setUso(rs.getString("COU_D_TIPO"));
				color.setCustom(custom);
				color.setDefault1(default1);

				Colores colors = new Colores();
				colors.setColor(color);
				colors.setTipo(rs.getString("COT_D_TIPO"));

				colores.add(colors);
			}
			rs.close();
			listarColores.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcepcionBDNoResponde();
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return colores;
	}

}
