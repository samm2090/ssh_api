package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.PrestamoDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class PrestamoDAOImpl implements PrestamoDAO {

	@Autowired
	PropertiesReader lector;

	static final String TITLE_PRESTAMO = "Prestamos";
	static final String SUBTITLE_PRESTAMO = "Cuentas Pendientes";

	@Override
	public Widget cantidadCuotasPendientes(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		
		Widget widget = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/prestamo.query").getProperty("cantidadCuotasPendientes");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement calcularCantidad = conexion.prepareStatement(query);
//			calcularCantidad.setString(1, idCompania);
//			calcularCantidad.setString(2, idSucursal);
//			calcularCantidad.setString(3, idEmpleadoS);

			ResultSet rs = calcularCantidad.executeQuery();

			if (rs.next()) {
				Custom custom = new Custom();
				custom.setHex(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorTitulo = new Color();
				colorTitulo.setTipo("TEXT");
				colorTitulo.setUso("DEFAULT");
				colorTitulo.setDefault1(default1);
				colorTitulo.setCustom(custom);

				EstiloTexto estiloTitulo = new EstiloTexto();
				estiloTitulo.setFuente(null);
				estiloTitulo.setColor(colorTitulo);
				estiloTitulo.setCustom(custom);

				Texto titulo = new Texto();
				titulo.setTexto(TITLE_PRESTAMO);
				titulo.setEstilo(estiloTitulo);

				Local local = new Local();
				local.setResTipo("ICON");
				local.setNombre("supervisor_account");
				local.setExt("");
				
				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(local);
				archivo.setRemote(null);
				
				Color colorResItem = new Color();
				colorResItem.setTipo("TINT");
				colorResItem.setUso("DEFAULT");
				colorResItem.setDefault1(default1);
				colorResItem.setCustom(custom);
				
				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);
				resItem.setColor(colorResItem);

				Texto valor = new Texto();
				valor.setTexto("");
//				rs.getString("CANTIDAD")
				valor.setEstilo(estiloTitulo);
				
				Texto subtitulo = new Texto();
				subtitulo.setTexto(SUBTITLE_PRESTAMO);
				subtitulo.setEstilo(estiloTitulo);
				
				widget = new Widget();
				widget.setTitulo(titulo);
				widget.setResItem(resItem);
				widget.setValor(valor);
				widget.setSubtitulo(subtitulo);
				widget.setColor(colorResItem);
			}
			rs.close();
			calcularCantidad.close();

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
		return widget;
	}

}
