package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.AssetsDAO;
import pe.com.human.api.model.AlmacenamientoTipo;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.ArchivoTipo;
import pe.com.human.api.model.Assets;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.Remote;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.ModelResultSet;
import pe.com.human.api.util.PropertiesReader;

/**
 * 
 * @author smuroy
 *
 */
@Repository
public class AssetsDAOImpl implements AssetsDAO {

	@Autowired
	PropertiesReader lector;

	@Autowired
	ModelResultSet modelResultSet;

	@Override
	public List<Assets> listarAssetsXCompania(Compania compania) {
		List<Assets> assets = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/estilo.query").getProperty("listarAssetsXCompania");

		try {
			ConfiguracionDataSource configuracion = new ConfiguracionDataSource();

			conexion = ConexionBaseDatos.obtenerConexion(configuracion);

			PreparedStatement listarAssets = conexion.prepareStatement(query);
			listarAssets.setString(1, compania.getId());

			ResultSet rs = listarAssets.executeQuery();

			assets = new ArrayList<>();
			while (rs.next()) {
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
				asset.setArchivo(archivo);
				asset.setTipo(rs.getString("RET_D_NOMBRE"));

				assets.add(asset);
			}
			rs.close();
			listarAssets.close();

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
		return assets;
	}

}
