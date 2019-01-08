package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.PrestamoDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Alerta;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.CuotaDeuda;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.DeudaMes;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.FechaDeuda;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.MontoDeuda;
import pe.com.human.api.model.Prestamo;
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
			// calcularCantidad.setString(1, idCompania);
			// calcularCantidad.setString(2, idSucursal);
			// calcularCantidad.setString(3, idEmpleadoS);

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
				// rs.getString("CANTIDAD")
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

	@Override
	public Map<String, Object> deudaTotal(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		Map<String, Object> deuda = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/prestamo.query").getProperty("deudaTotal");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement resumen = conexion.prepareStatement(query);
			resumen.setString(1, codcia);
			resumen.setString(2, codsuc);
			resumen.setString(3, codtra);

			ResultSet rs = resumen.executeQuery();

			if (rs.next()) {
				deuda = new HashMap<>();

				deuda.put("moneda", "Soles");
				deuda.put("monto", rs.getString("BALANCE"));
			}

			rs.close();
			resumen.close();

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
		return deuda;
	}

	@Override
	public List<DeudaMes> deudaPorMes(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {

		List<DeudaMes> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/prestamo.query").getProperty("deudaPorMes");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement deudaPorMes = conexion.prepareStatement(query);
			deudaPorMes.setString(1, codcia);
			deudaPorMes.setString(2, codsuc);
			deudaPorMes.setString(3, codtra);

			ResultSet rs = deudaPorMes.executeQuery();

			items = new ArrayList<>();
			DeudaMes item = null;

			while (rs.next()) {
				item = new DeudaMes();

				String moneda = "SOLES";

				item.setMoneda(moneda);
				item.setMonto(rs.getString("CUOTA"));
				item.setMes(rs.getString("MES"));
				item.setAnio(rs.getString("ANO"));

				items.add(item);
			}

			rs.close();
			deudaPorMes.close();

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
		return items;
	}

	@Override
	public List<Prestamo> listarCuotas(String codcia, String codsuc, String codtra, String estado,
			ConfiguracionDataSource configuracionDataSource) {

		List<Prestamo> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/prestamo.query").getProperty("listarCuotas");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarCuotas = conexion.prepareStatement(query);
			listarCuotas.setString(1, codcia);
			listarCuotas.setString(2, codsuc);
			listarCuotas.setString(3, codtra);
			listarCuotas.setString(4, estado);

			ResultSet rs = listarCuotas.executeQuery();

			items = new ArrayList<>();
			Prestamo item = null;

			while (rs.next()) {
				item = new Prestamo();

				Color color = new Color();
				color.setTipo("TEXT");
				color.setUso("DEFAULT");
				color.setDefault1(new Default("PRIMARYDARK"));

				EstiloTexto estilo = new EstiloTexto();
				estilo.setColor(color);

				Alerta alerta = new Alerta();
				alerta.setTipo("CURRENT");
				alerta.setEstilo(estilo);

				item.setAlerta(alerta);
				item.setFecha(new FechaDeuda(rs.getString("CTLFECPRE"), rs.getString("CTLFECVIG")));
				item.setTipo(rs.getString("TIPO"));
				item.setMonto(new MontoDeuda(rs.getString("CTLDIASFR"), rs.getString("MONEDA"), rs.getString("CTLPMONTOP")));
				item.setCuota(new CuotaDeuda(rs.getInt("CTLNUMCUO"), rs.getString("MONEDA"), rs.getString("CTLPCUOTA")));

				items.add(item);
			}

			rs.close();
			listarCuotas.close();

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
		return items;
	}

}
