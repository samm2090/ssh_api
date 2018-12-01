package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.EstiloDAO;
import pe.com.human.api.model.AlmacenamientoTipo;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.ArchivoTipo;
import pe.com.human.api.model.Assets;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Colores;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.Estilo;
import pe.com.human.api.model.Hex;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.Remote;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.ModelResultSet;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class EstiloDAOImpl implements EstiloDAO {

	@Autowired
	PropertiesReader lector;

	@Autowired
	ModelResultSet modelResultSet;

	@Override
	public Estilo buscarEstiloXCompania(Compania compania, ConfiguracionDataSource configBaseAppMovil) {
		Estilo estilo = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/estilo.query").getProperty("buscarEstiloXCompania");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configBaseAppMovil);

			PreparedStatement buscarEstilo = conexion.prepareStatement(query);
			buscarEstilo.setString(1, compania.getId());

			ResultSet rs = buscarEstilo.executeQuery();

			List<Assets> assets = new ArrayList<>();
			List<Colores> colores = new ArrayList<>();
			while (rs.next()) {
				// Assets
				AlmacenamientoTipo almaTipo = modelResultSet.almaTipoResultSet(rs);
				ArchivoTipo tipo = modelResultSet.archivoTipoResultSet(rs);
				Local local = modelResultSet.localTipoResultSet(rs);
				Remote remote = modelResultSet.remotoTipoResultSet(rs);

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo(almaTipo.getNombre());
				archivo.setTipo(tipo.getNombre());
				archivo.setLocal(local);
				archivo.setRemote(remote);

				Assets asset = new Assets();
				asset.setTipo(rs.getString("RET_D_NOMBRE"));
				asset.setArchivo(archivo);

				assets.add(asset);

				Hex hex = new Hex();
				hex.setHex(rs.getString("COC_D_HEX"));

				Custom custom = new Custom();
				custom.setHex(hex);

				Default default1 = new Default();
				default1.setNombre(rs.getString("COT_D_TIPO"));

				// Colores
				Color color = new Color();
				color.setTipo(rs.getString("CTP_D_DECRIPCION"));
				color.setUso(rs.getString("COU_D_TIPO"));
				color.setCustom(custom);
				color.setDefault1(default1);

				Colores coloresObject = new Colores();
				coloresObject.setColor(color);
				coloresObject.setTipo(rs.getString("COT_D_TIPO"));

				colores.add(coloresObject);
			}
			estilo = new Estilo();
			estilo.setAssets(assets);
			estilo.setColores(colores);

			rs.close();
			buscarEstilo.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return estilo;
	}
}
