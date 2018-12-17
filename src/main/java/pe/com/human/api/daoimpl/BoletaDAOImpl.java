package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.constants.ApiConstantes;
import pe.com.human.api.dao.BoletaDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.BoletaEmpleado;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.Remote;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class BoletaDAOImpl implements BoletaDAO {

	@Autowired
	PropertiesReader lector;

	private static final String TITLE_BOLETAS = "Boletas";
	private static final String SUBTITLE_BOLETAS = "No leídas";
	private static final String BOLETAS = "/BOLETAS/";

	@Override
	public Widget cantidadPagosMesActual(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		Widget widget = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/boleta.query").getProperty("cantidadPagosMesActual");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement calcularCantidad = conexion.prepareStatement(query);
			calcularCantidad.setString(1, idCompania);
			calcularCantidad.setString(2, idSucursal);
			calcularCantidad.setString(3, idEmpleado);

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
				titulo.setTexto(TITLE_BOLETAS);
				titulo.setEstilo(estiloTitulo);

				Local local = new Local();
				local.setResTipo("ICON");
				local.setNombre("payment");
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
				valor.setTexto(rs.getString("CANTIDAD"));
				valor.setEstilo(estiloTitulo);

				Texto subtitulo = new Texto();
				subtitulo.setTexto(SUBTITLE_BOLETAS);
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

	@Override
	public List<BoletaEmpleado> listarBoletasXIdEmpleado(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {

		List<BoletaEmpleado> boletas = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/boleta.query").getProperty("listarBoletasXIdEmpleado");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarBoletas = conexion.prepareStatement(query);
			listarBoletas.setString(1, codcia);
			listarBoletas.setString(2, codsuc);
			listarBoletas.setString(3, codtra);

			ResultSet rs = listarBoletas.executeQuery();
			boletas = new ArrayList<>();
			BoletaEmpleado boleta;
			while (rs.next()) {
				String periodo = rs.getString("PERCODPER");

				String url = ApiConstantes.URL_BASE + codcia + BOLETAS + codtra + "_" + periodo + ".pdf";

				Remote remote = new Remote();
				remote.setResTipo(null);
				remote.setUrl(url);
				remote.setNombre("Boleta_" + periodo);
				remote.setDimensionRatio(null);
				remote.setExt("PDF");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTO");
				archivo.setTipo("DOCUMENTO");
				archivo.setLocal(null);
				archivo.setRemote(remote);

				boleta = new BoletaEmpleado();
				boleta.setNombre("Boleta");
				boleta.setPeriodo(periodo);
				boleta.setArchivo(archivo);

				boletas.add(boleta);
			}
			rs.close();
			listarBoletas.close();

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
		return boletas;
	}

}
