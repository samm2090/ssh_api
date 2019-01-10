package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.VacacionesDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.exception.ExcepcionNoDiasVacaciones;
import pe.com.human.api.model.Alerta;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Extra;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Linea;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Vacaciones;
import pe.com.human.api.model.VacacionesSolicitadas;
import pe.com.human.api.model.Widget;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class VacacionesDAOImpl implements VacacionesDAO {

	@Autowired
	PropertiesReader lector;

	static final String TITLE_VACACIONES = "Vacaciones";
	static final String SUBTITLE_VACACIONES = "Días disponibles";

	@Override
	public Widget cantidadSaldo(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		Widget widget = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/vacaciones.query").getProperty("cantidadSaldo");

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
				titulo.setTexto(TITLE_VACACIONES);
				titulo.setEstilo(estiloTitulo);

				Local local = new Local();
				local.setResTipo("ICON");
				local.setNombre("beach_access");
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
				valor.setTexto(rs.getString("SALDO"));
				valor.setEstilo(estiloTitulo);

				Texto subtitulo = new Texto();
				subtitulo.setTexto(SUBTITLE_VACACIONES);
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
	public Vacaciones resumenVacaciones(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		Vacaciones vacaciones = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/vacaciones.query").getProperty("resumenVacaciones");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement resumen = conexion.prepareStatement(query);
			resumen.setString(1, codcia);
			resumen.setString(2, codsuc);
			resumen.setString(3, codtra);
			resumen.setString(4, codcia);
			resumen.setString(5, codsuc);
			resumen.setString(6, codtra);
			resumen.setString(7, codcia);
			resumen.setString(8, codsuc);
			resumen.setString(9, codtra);

			ResultSet rs = resumen.executeQuery();

			if (rs.next()) {
				vacaciones = new Vacaciones();

				int total = rs.getInt("TOTAL");
				int solicitadas = rs.getInt("SOLICITADAS");

				vacaciones.setTotal(total);
				vacaciones.setSolicitadas(solicitadas);
				vacaciones.setDisponibles(total - solicitadas);
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
		return vacaciones;
	}

	@Override
	public VacacionesSolicitadas listarSolicitudVacaciones(String codcia, String codsuc, String codtra, String[] flgEst,
			int rownum, ConfiguracionDataSource configuracionDataSource) {
		VacacionesSolicitadas vacaciones = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/vacaciones.query").getProperty("listarSolicitudVacaciones");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			StringBuilder queryEstados = new StringBuilder(query);
			String campo = " AND VACFLGEST IN (";
			queryEstados.insert(queryEstados.lastIndexOf("?") + 1, campo);

			for (int i = 0; i < flgEst.length; i++) {
				queryEstados.insert(queryEstados.lastIndexOf("(") + 1, "?,");
			}

			queryEstados.replace(queryEstados.lastIndexOf(","), queryEstados.lastIndexOf(",") + 1, ")");

			PreparedStatement listarSolicitudes = conexion.prepareStatement(queryEstados.toString());
			listarSolicitudes.setString(1, codcia);
			listarSolicitudes.setString(2, codsuc);
			listarSolicitudes.setString(3, codtra);
			listarSolicitudes.setInt(4, rownum);
			for (int i = 0; i < flgEst.length; i++) {
				listarSolicitudes.setString(i + 5, flgEst[i]);
			}

			ResultSet rs = listarSolicitudes.executeQuery();

			vacaciones = new VacacionesSolicitadas();
			vacaciones.setTipo("SINGLE_LINE_ICON_RIGHT");
			List<Item> items = new ArrayList<>();

			while (rs.next()) {
				Item item = new Item();

				String flag = rs.getString("VACFLGEST");

				Color colorAlerta = new Color();
				colorAlerta.setTipo("TEXT");
				colorAlerta.setUso("DEFAULT");

				EstiloTexto estiloAlerta = new EstiloTexto();

				Alerta alerta = new Alerta();

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");

				if (("1").equals(flag)) {
					alerta.setTipo("PENDING");
					colorAlerta.setDefault1(new Default("PRIMARYDARK"));
					archivo.setLocal(new Local("ICON", "access_time", "xml"));
				} else if (("4").equals(flag)) {
					alerta.setTipo("APPROVED");
					colorAlerta.setDefault1(new Default("SECONDARYDARK"));
					archivo.setLocal(new Local("ICON", "check_circle", "xml"));
				} else {
					alerta.setTipo("REJECTED");
					colorAlerta.setDefault1(new Default("TERTIARYDARK"));
					archivo.setLocal(new Local("ICON", "close", "xml"));
				}

				estiloAlerta.setColor(colorAlerta);
				alerta.setEstilo(estiloAlerta);

				item.setAlerta(alerta);
				item.setPrimeraLinea(new Linea(new Texto(rs.getString("ESTADO"), null)));

				Date fecIni = rs.getDate("VACFECINI");
				Date fecFin = rs.getDate("VACFECFIN");

				SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM',' YYYY", new Locale("es", "PE"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);

				item.setSegundaLinea(new Linea(new Texto(sdf.format(fecIni) + " - " + sdf.format(fecFin), null)));
				item.setTerceraLinea(new Linea(new Texto(rs.getString("VACDIASPRO") + " Días", null)));
				item.setResItem(resItem);

				items.add(item);
			}

			vacaciones.setItems(items);

			rs.close();
			listarSolicitudes.close();

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
		return vacaciones;
	}

	@Override
	public VacacionesSolicitadas listarSolicitudVacacionesSimple(String codcia, String codsuc, String codtra,
			String[] flgEst, int rownum, ConfiguracionDataSource configuracionDataSource) {
		VacacionesSolicitadas vacaciones = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/vacaciones.query").getProperty("listarSolicitudVacaciones");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			StringBuilder queryEstados = new StringBuilder(query);
			String campo = " AND VACFLGEST IN (";
			queryEstados.insert(queryEstados.lastIndexOf("?") + 1, campo);

			for (int i = 0; i < flgEst.length; i++) {
				queryEstados.insert(queryEstados.lastIndexOf("(") + 1, "?,");
			}

			queryEstados.replace(queryEstados.lastIndexOf(","), queryEstados.lastIndexOf(",") + 1, ")");

			PreparedStatement listarSolicitudes = conexion.prepareStatement(queryEstados.toString());
			listarSolicitudes.setString(1, codcia);
			listarSolicitudes.setString(2, codsuc);
			listarSolicitudes.setString(3, codtra);
			listarSolicitudes.setInt(4, rownum);
			for (int i = 0; i < flgEst.length; i++) {
				listarSolicitudes.setString(i + 5, flgEst[i]);
			}

			ResultSet rs = listarSolicitudes.executeQuery();

			vacaciones = new VacacionesSolicitadas();
			vacaciones.setTipo("SINGLE_LINE_ICON_RIGHT");
			List<Item> items = new ArrayList<>();

			while (rs.next()) {
				Item item = new Item();

				String flag = rs.getString("VACFLGEST");

				Color colorAlerta = new Color();
				colorAlerta.setTipo("TEXT");
				colorAlerta.setUso("DEFAULT");

				EstiloTexto estiloAlerta = new EstiloTexto();

				Alerta alerta = new Alerta();

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");

				if (("1").equals(flag)) {
					alerta.setTipo("PENDING");
					colorAlerta.setDefault1(new Default("PRIMARYDARK"));
					archivo.setLocal(new Local("ICON", "access_time", "xml"));
				} else if (("4").equals(flag)) {
					alerta.setTipo("APPROVED");
					colorAlerta.setDefault1(new Default("SECONDARYDARK"));
					archivo.setLocal(new Local("ICON", "check_circle", "xml"));
				} else {
					alerta.setTipo("REJECTED");
					colorAlerta.setDefault1(new Default("TERTIARYDARK"));
					archivo.setLocal(new Local("ICON", "close", "xml"));
				}

				estiloAlerta.setColor(colorAlerta);
				alerta.setEstilo(estiloAlerta);

				Date fecIni = rs.getDate("VACFECINI");
				Date fecFin = rs.getDate("VACFECFIN");

				SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM',' YYYY", new Locale("es", "PE"));
				SimpleDateFormat sdfMes = new SimpleDateFormat("MMMM");
				SimpleDateFormat sdfAno = new SimpleDateFormat("YYYY");

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);

				Extra extra = new Extra(WordUtils.capitalize(sdfMes.format(fecIni)), sdfAno.format(fecIni));

				item.setAlerta(alerta);
				item.setPrimeraLinea(new Linea(new Texto(sdf.format(fecIni) + " - " + sdf.format(fecFin), null)));
				item.setSegundaLinea(new Linea(new Texto(rs.getString("VACDIASPRO") + " Días", null)));
				item.setResItem(resItem);
				item.setExtra(extra);

				items.add(item);
			}

			vacaciones.setItems(items);

			rs.close();
			listarSolicitudes.close();

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
		return vacaciones;
	}

	@Override
	public boolean insertarSolicitud(String codcia, String codsuc, String codtra, String categoriaVacaciones,
			String fechaInicial, String fechaFinal, ConfiguracionDataSource configuracionDataSource) {
		String query = lector.leerPropiedad("queries/vacaciones.query").getProperty("insertarSolicitud");
		String queryDias = lector.leerPropiedad("queries/vacaciones.query").getProperty("resumenVacaciones");
		boolean resultado = false;

		Connection conexion = null;
		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement resumen = conexion.prepareStatement(queryDias);
			resumen.setString(1, codcia);
			resumen.setString(2, codsuc);
			resumen.setString(3, codtra);
			resumen.setString(4, codcia);
			resumen.setString(5, codsuc);
			resumen.setString(6, codtra);
			resumen.setString(7, codcia);
			resumen.setString(8, codsuc);
			resumen.setString(9, codtra);

			ResultSet rs = resumen.executeQuery();

			int disponibles = 0;

			if (rs.next()) {
				disponibles = rs.getInt("TOTAL") - rs.getInt("SOLICITADAS");
			}

			DateTimeFormatter sdf = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime fechaIni = null;
			DateTime fechaFin = null;

			fechaIni = sdf.parseDateTime(fechaInicial);
			fechaFin = sdf.parseDateTime(fechaFinal);

			int dias = Days.daysBetween(fechaIni.toLocalDate(), fechaFin.toLocalDate()).getDays() + 1;

			if (dias > disponibles) {
				throw new ExcepcionNoDiasVacaciones();
			} else {
				PreparedStatement insertarSolicitud = conexion.prepareStatement(query);
				insertarSolicitud.setString(1, codcia);
				insertarSolicitud.setString(2, codsuc);
				insertarSolicitud.setString(3, codtra);
				insertarSolicitud.setString(4, "02");
				insertarSolicitud.setString(5, fechaInicial);
				insertarSolicitud.setString(6, fechaFinal);

				resultado = insertarSolicitud.executeUpdate() > 0 ? true : false;

				conexion.commit();
				insertarSolicitud.close();
			}

			resumen.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

		return resultado;
	}

}
