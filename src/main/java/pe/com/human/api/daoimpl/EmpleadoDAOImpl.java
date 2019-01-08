package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.constants.ApiConstantes;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Action;
import pe.com.human.api.model.Aprobador;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Datos;
import pe.com.human.api.model.DatosLaborales;
import pe.com.human.api.model.DatosPersonales;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.DimensionRatio;
import pe.com.human.api.model.Documento;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.EmpleadoResumen;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Hex;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Linea;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.NombreCompleto;
import pe.com.human.api.model.Phone;
import pe.com.human.api.model.Remote;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class EmpleadoDAOImpl implements EmpleadoDAO {

	private static final String TITLE_SUBORDINADOS = "Mi Equipo";
	private static final String SUBTITLE_SUBORDINADOS = "Colaboradores";

	@Autowired
	PropertiesReader lector;

	@Override
	public Empleado buscarEmpleadoXUsuario(String idCompania, String idSucursal, String documento, String contrasenia,
			ConfiguracionDataSource configuracionDataSource) {
		Empleado resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarEmpleadoXUsuario");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, idCompania);
			buscarEmpleado.setString(2, idSucursal);
			buscarEmpleado.setString(3, documento);
			buscarEmpleado.setString(4, contrasenia);

			ResultSet rs = buscarEmpleado.executeQuery();

			if (rs.next()) {
				resultado = new Empleado();
				resultado.setId(rs.getString("EMPCODTRA"));
				resultado.setRol(rs.getString("ROL"));
			}

			rs.close();
			buscarEmpleado.close();

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
		return resultado;
	}

	@Override
	public Widget cantidadSubordinados(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		Widget widget = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("cantidadColaboradores");

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
				titulo.setTexto(TITLE_SUBORDINADOS);
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
				valor.setTexto(rs.getString("CANTIDAD"));
				valor.setEstilo(estiloTitulo);

				Texto subtitulo = new Texto();
				subtitulo.setTexto(SUBTITLE_SUBORDINADOS);
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
	public EmpleadoResumen buscarEmpleadoResumen(EmpleadoRequest empleado,
			ConfiguracionDataSource configuracionDataSource) {
		EmpleadoResumen empleadoResumen = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarEmpleadoResumen");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, empleado.getBase().getCompania().getId());
			buscarEmpleado.setString(2, empleado.getBase().getCompania().getSucursal().getId());
			buscarEmpleado.setString(3, empleado.getEmpleado().getId());

			ResultSet rs = buscarEmpleado.executeQuery();
			String foto = "";
			Texto numeroDocumento = null;
			empleadoResumen = new EmpleadoResumen();
			if (rs.next()) {
				Texto nombreTexto = new Texto(rs.getString("EMPNOMBRE"), null);
				Texto apePaternoTexto = new Texto(rs.getString("EMPAPATERN"), null);
				Texto apeMaternoTexto = new Texto(rs.getString("EMPAMATERN"), null);

				NombreCompleto nombre = new NombreCompleto();
				nombre.setNombre(nombreTexto);
				nombre.setApePaterno(apePaternoTexto);
				nombre.setApeMaterno(apeMaternoTexto);

				numeroDocumento = new Texto(rs.getString("EMPNRODOCID"), null);
				Texto tipo = new Texto(rs.getString("TIPO_DOCUMENTO"), null);

				Documento documento = new Documento();
				documento.setNumeroDocumento(numeroDocumento);
				documento.setTipo(tipo);

				DatosPersonales personales = new DatosPersonales();
				personales.setNombre(nombre);
				personales.setDocumento(documento);

				Texto codigo = new Texto(rs.getString("EMPCODTRA"), null);
				Texto puesto = new Texto(rs.getString("PUESTO"), null);

				DatosLaborales laborales = new DatosLaborales();
				laborales.setCodigo(codigo);
				laborales.setPuesto(puesto);
				laborales.setRol(rs.getString("ROL"));

				Datos datos = new Datos();
				datos.setPersonales(personales);
				datos.setLaborales(laborales);

				empleadoResumen.setDatos(datos);

				foto = rs.getString("EMPFOTO");
			}

			String url = "http://";
			if (foto != null) {
				url = ApiConstantes.URL_BASE_REPOSITORIO + empleado.getBase().getCompania().getId() + "/FOTO_EMPLEADO/"
						+ foto;
			}

			Remote remote = new Remote();
			remote.setResTipo("AVATAR72");
			remote.setNombre(foto);
			remote.setUrl(url);
			remote.setExt("JPG");

			Archivo archivo = new Archivo();
			archivo.setAlmaTipo("REMOTE");
			archivo.setTipo("IMAGEN");
			archivo.setLocal(null);
			archivo.setRemote(remote);

			ResItem resItem = new ResItem();
			resItem.setTipo("AVATAR72");
			resItem.setArchivo(archivo);

			empleadoResumen.setResItem(resItem);

			rs.close();
			buscarEmpleado.close();

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
		return empleadoResumen;
	}

	@Override
	public List<Item> listarCumpleanos(String idCompania, String idSucursal,
			ConfiguracionDataSource configuracionDataSource) {

		List<Item> cumpleanos = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("listarCumpleanos");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarEmpleados = conexion.prepareStatement(query);
			listarEmpleados.setString(1, idCompania);
			listarEmpleados.setString(2, idSucursal);

			ResultSet rs = listarEmpleados.executeQuery();

			cumpleanos = new ArrayList<>();
			Item item = null;
			while (rs.next()) {
				item = new Item();

				String foto = rs.getString("EMPFOTO");
				String url = "http://";
				if (foto != null) {
					url = ApiConstantes.URL_BASE_REPOSITORIO + idCompania + "/FOTO_EMPLEADO/" + foto;
				}

				Remote remote = new Remote();
				remote.setResTipo("AVATAR40");
				remote.setUrl(url);
				remote.setNombre(foto);
				remote.setDimensionRatio(new DimensionRatio("W,40:40", "40", "40"));
				remote.setExt("JPG");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(remote);

				ResItem resItem = new ResItem();
				resItem.setTipo("AVATAR40");
				resItem.setArchivo(archivo);
				resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("PRIMARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("NOMBRE"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("SECONDARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(rs.getString("AREDESAREA"));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Local localSegundaLinea = new Local();
				localSegundaLinea.setResTipo("ICON");
				localSegundaLinea.setNombre("phone");
				localSegundaLinea.setExt("xml");

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Archivo archivoAction = new Archivo();
				archivoAction.setAlmaTipo("LOCAL");
				archivoAction.setTipo("VECTOR");
				archivoAction.setRemote(null);
				archivoAction.setLocal(localSegundaLinea);

				String telefono = rs.getString("EMPTELFMOV");

				Phone phone = new Phone();
				phone.setTipo("MOBILE");
				phone.setUri(telefono);

				Color colorAction = new Color();
				if (!("").equals(telefono) && null != telefono) {
					colorAction.setTipo("TINT");
					colorAction.setUso("DEFAULT");
					colorAction.setDefault1(new Default("PRIMARYDARK"));
				} else {
					colorAction.setTipo("TINT");
					colorAction.setUso("CUSTOM");
					colorAction.setCustom(new Custom(new Hex("CCCCCC")));
				}

				ResItem resItemAction = new ResItem();
				resItemAction.setTipo("PHONE");
				resItemAction.setArchivo(archivoAction);
				resItemAction.setColor(colorAction);

				Action itemAction = new Action();
				itemAction.setTipo("PHONE");
				itemAction.setResItem(resItemAction);
				itemAction.setPhone(phone);

				List<Action> actions = new ArrayList<>();
				actions.add(itemAction);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setAction(actions);
				cumpleanos.add(item);
			}

			rs.close();
			listarEmpleados.close();

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
		return cumpleanos;
	}

	@Override
	public List<Item> listarFeriados(String idCompania, String idSucursal,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> feriados = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("listarFeriados");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarEmpleados = conexion.prepareStatement(query);
			listarEmpleados.setString(1, idCompania);
			listarEmpleados.setString(2, idSucursal);

			ResultSet rs = listarEmpleados.executeQuery();

			feriados = new ArrayList<>();
			Item item = null;
			while (rs.next()) {
				item = new Item();
				Remote remote = new Remote();
				remote.setResTipo("");

				Local local = new Local();
				local.setResTipo("ICON");
				local.setNombre("schedule");
				local.setExt("xml");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(local);
				archivo.setRemote(null);

				Color colorResItem = new Color();
				colorResItem.setTipo("TINT");
				colorResItem.setUso("DEFAULT");
				colorResItem.setDefault1(new Default("PRIMARYDARK"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);
				resItem.setColor(colorResItem);

				Default default1 = new Default();
				default1.setNombre("PRIMARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("FERDESC"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Date ferFecha = rs.getDate("FERFECHA");
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM',' YYYY", new Locale("es", "PE"));

				Default default2 = new Default();
				default2.setNombre("SECONDARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(WordUtils.capitalize(sdf.format(ferFecha)));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				// Local localSegundaLinea = new Local();
				// localSegundaLinea.setResTipo("ICON");
				// localSegundaLinea.setNombre("schedule");
				// localSegundaLinea.setExt("xml");
				//
				// Archivo archivoSegundaLinea = new Archivo();
				// archivoSegundaLinea.setAlmaTipo("LOCAL");
				// archivoSegundaLinea.setTipo("VECTOR");
				// archivoSegundaLinea.setRemote(null);
				// archivo.setLocal(localSegundaLinea);
				//
				// Default defaultSegundaLinea = new Default();
				// defaultSegundaLinea.setNombre("PRIMARYDARK");
				//
				// Color colorSegundaLinea = new Color();
				// colorSegundaLinea.setTipo("TINT");
				// colorSegundaLinea.setUso("LOCAL");
				// colorSegundaLinea.setDefault1(defaultSegundaLinea);
				// colorSegundaLinea.setCustom(null);
				//
				// ResItem resItemSegundaLinea = new ResItem();
				// resItemSegundaLinea.setTipo("PHONE");
				// resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				// resItemSegundaLinea.setColor(colorSegundaLinea);

				// Phone phone = new Phone();
				// phone.setTipo("MOBILE");
				// phone.setUri(rs.getString("EMPTELFMOV"));

				// Action actionSegundaLinea = new Action();
				// actionSegundaLinea.setTipo("PHONE");
				// actionSegundaLinea.setResItem(resItemSegundaLinea);
				// actionSegundaLinea.setPhone(phone);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Linea lineaTitulo = new Linea();
				lineaTitulo.setTexto(new Texto("Feriado No Laborable", estiloTextoPrimeraLinea));
				lineaTitulo.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(lineaTitulo);
				item.setSegundaLinea(primeraLinea);
				item.setTerceraLinea(segundaLinea);

				feriados.add(item);
			}

			rs.close();
			listarEmpleados.close();

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
		return feriados;
	}

	@Override
	public List<Item> buscarInformacionGeneral(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarInformacionGeneral");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, idCompania);
			buscarEmpleado.setString(2, idSucursal);
			buscarEmpleado.setString(3, idEmpleado);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("DNI", rs.getString("EMPNRODOCID"));
				datoEmpleado.put("Fecha de Nacimiento", rs.getString("EMPFECNAC"));
				datoEmpleado.put("Nacionalidad", rs.getString("NACIONALIDAD"));
				datoEmpleado.put("Estado Civil", rs.getString("ESTADO_CIVIL"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				// Local localSegundaLinea = new Local();
				// localSegundaLinea.setResTipo("ICON");
				// localSegundaLinea.setNombre("phone");
				// localSegundaLinea.setExt("xml");
				//
				// Archivo archivoSegundaLinea = new Archivo();
				// archivoSegundaLinea.setAlmaTipo("LOCAL");
				// archivoSegundaLinea.setTipo("VECTOR");
				// archivoSegundaLinea.setRemote(null);
				// archivo.setLocal(localSegundaLinea);
				//
				// Default defaultSegundaLinea = new Default();
				// default1.setNombre("PRIMARYDARK");
				//
				// Color colorSegundaLinea = new Color();
				// colorSegundaLinea.setTipo("TINT");
				// colorSegundaLinea.setUso("LOCAL");
				// colorSegundaLinea.setDefault1(defaultSegundaLinea);
				// colorSegundaLinea.setCustom(null);
				//
				// ResItem resItemSegundaLinea = new ResItem();
				// resItemSegundaLinea.setTipo("PHONE");
				// resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				// resItemSegundaLinea.setColor(colorSegundaLinea);
				//
				// Phone phone = new Phone();
				// phone.setTipo("MOBILE");
				// phone.setUri(rs.getString("EMPTELFMOV"));
				//
				// Action actionSegundaLinea = new Action();
				// actionSegundaLinea.setTipo("PHONE");
				// actionSegundaLinea.setResItem(resItemSegundaLinea);
				// actionSegundaLinea.setPhone(phone);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarInformacionLaboral(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarInformacionLaboral");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, idCompania);
			buscarEmpleado.setString(2, idSucursal);
			buscarEmpleado.setString(3, idEmpleado);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("Código del trabajador", rs.getString("EMPCODTRA"));
				datoEmpleado.put("Gerencia", rs.getString("GERENCIA"));
				datoEmpleado.put("Área", rs.getString("AREA"));
				datoEmpleado.put("Puesto", rs.getString("PUESTO"));
				datoEmpleado.put("Jefe Directo", rs.getString("JEFE"));
				datoEmpleado.put("Local", rs.getString("LOCAL"));
				datoEmpleado.put("Fecha de Ingreso", rs.getString("EMPFECING"));
				datoEmpleado.put("Anexo", rs.getString("EMPANEXOORG"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				// Local localSegundaLinea = new Local();
				// localSegundaLinea.setResTipo("ICON");
				// localSegundaLinea.setNombre("phone");
				// localSegundaLinea.setExt("xml");
				//
				// Archivo archivoSegundaLinea = new Archivo();
				// archivoSegundaLinea.setAlmaTipo("LOCAL");
				// archivoSegundaLinea.setTipo("VECTOR");
				// archivoSegundaLinea.setRemote(null);
				// archivo.setLocal(localSegundaLinea);
				//
				// Default defaultSegundaLinea = new Default();
				// default1.setNombre("PRIMARYDARK");
				//
				// Color colorSegundaLinea = new Color();
				// colorSegundaLinea.setTipo("TINT");
				// colorSegundaLinea.setUso("LOCAL");
				// colorSegundaLinea.setDefault1(defaultSegundaLinea);
				// colorSegundaLinea.setCustom(null);
				//
				// ResItem resItemSegundaLinea = new ResItem();
				// resItemSegundaLinea.setTipo("PHONE");
				// resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				// resItemSegundaLinea.setColor(colorSegundaLinea);
				//
				// Phone phone = new Phone();
				// phone.setTipo("MOBILE");
				// phone.setUri(rs.getString("EMPTELFMOV"));
				//
				// Action actionSegundaLinea = new Action();
				// actionSegundaLinea.setTipo("PHONE");
				// actionSegundaLinea.setResItem(resItemSegundaLinea);
				// actionSegundaLinea.setPhone(phone);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarDatosDireccion(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarDatosDireccion");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, idCompania);
			buscarEmpleado.setString(2, idSucursal);
			buscarEmpleado.setString(3, idEmpleado);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("Dirección Actual", rs.getString("EMPDIRECCION"));
				datoEmpleado.put("Email Empresa", rs.getString("EMPEMAILORG"));
				datoEmpleado.put("Email Personal", rs.getString("EMPEMAIL"));
				datoEmpleado.put("Celular Personal", rs.getString("EMPTELFMOV"));
				datoEmpleado.put("Teléfono Casa", rs.getString("EMPTELFFIJO"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				// Local localSegundaLinea = new Local();
				// localSegundaLinea.setResTipo("ICON");
				// localSegundaLinea.setNombre("phone");
				// localSegundaLinea.setExt("xml");
				//
				// Archivo archivoSegundaLinea = new Archivo();
				// archivoSegundaLinea.setAlmaTipo("LOCAL");
				// archivoSegundaLinea.setTipo("VECTOR");
				// archivoSegundaLinea.setRemote(null);
				// archivo.setLocal(localSegundaLinea);
				//
				// Default defaultSegundaLinea = new Default();
				// default1.setNombre("PRIMARYDARK");
				//
				// Color colorSegundaLinea = new Color();
				// colorSegundaLinea.setTipo("TINT");
				// colorSegundaLinea.setUso("LOCAL");
				// colorSegundaLinea.setDefault1(defaultSegundaLinea);
				// colorSegundaLinea.setCustom(null);
				//
				// ResItem resItemSegundaLinea = new ResItem();
				// resItemSegundaLinea.setTipo("PHONE");
				// resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				// resItemSegundaLinea.setColor(colorSegundaLinea);
				//
				// Phone phone = new Phone();
				// phone.setTipo("MOBILE");
				// phone.setUri(rs.getString("EMPTELFMOV"));
				//
				// Action actionSegundaLinea = new Action();
				// actionSegundaLinea.setTipo("PHONE");
				// actionSegundaLinea.setResItem(resItemSegundaLinea);
				// actionSegundaLinea.setPhone(phone);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarDatosEmergencia(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarDatosEmergencia");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, idCompania);
			buscarEmpleado.setString(2, idSucursal);
			buscarEmpleado.setString(3, idEmpleado);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			if (rs.next()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				ResItem resItem = new ResItem();
				resItem.setTipo("AVATAR40");
				resItem.setArchivo(archivo);
				resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("EMPNOMEMER"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(rs.getString("EMPDIRECCIONCE"));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Texto textoTercerLinea = new Texto();
				textoTercerLinea.setTexto(rs.getString("EMPTELFCE"));
				textoTercerLinea.setEstilo(estiloTextoSegundaLinea);

				Texto textoCuartaLinea = new Texto();
				textoCuartaLinea.setTexto(rs.getString("EMPCELCE"));
				textoCuartaLinea.setEstilo(estiloTextoSegundaLinea);

				// Local localSegundaLinea = new Local();
				// localSegundaLinea.setResTipo("ICON");
				// localSegundaLinea.setNombre("phone");
				// localSegundaLinea.setExt("xml");
				//
				// Archivo archivoSegundaLinea = new Archivo();
				// archivoSegundaLinea.setAlmaTipo("LOCAL");
				// archivoSegundaLinea.setTipo("VECTOR");
				// archivoSegundaLinea.setRemote(null);
				// archivo.setLocal(localSegundaLinea);
				//
				// Default defaultSegundaLinea = new Default();
				// default1.setNombre("PRIMARYDARK");
				//
				// Color colorSegundaLinea = new Color();
				// colorSegundaLinea.setTipo("TINT");
				// colorSegundaLinea.setUso("LOCAL");
				// colorSegundaLinea.setDefault1(defaultSegundaLinea);
				// colorSegundaLinea.setCustom(null);
				//
				// ResItem resItemSegundaLinea = new ResItem();
				// resItemSegundaLinea.setTipo("PHONE");
				// resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				// resItemSegundaLinea.setColor(colorSegundaLinea);
				//
				// Phone phone = new Phone();
				// phone.setTipo("MOBILE");
				// phone.setUri(rs.getString("EMPTELFMOV"));
				//
				// Action actionSegundaLinea = new Action();
				// actionSegundaLinea.setTipo("PHONE");
				// actionSegundaLinea.setResItem(resItemSegundaLinea);
				// actionSegundaLinea.setPhone(phone);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Linea tercerLinea = new Linea();
				tercerLinea.setTexto(textoTercerLinea);
				tercerLinea.setAction(null);

				Linea cuartaLinea = new Linea();
				cuartaLinea.setTexto(textoCuartaLinea);
				cuartaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setTerceraLinea(tercerLinea);
				item.setCuartaLinea(cuartaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarNivelAcademico(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> nivelAcademico = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarNivelAcademico");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarNivel = conexion.prepareStatement(query);
			buscarNivel.setString(1, codcia);
			buscarNivel.setString(2, codsuc);
			buscarNivel.setString(3, codtra);

			ResultSet rs = buscarNivel.executeQuery();

			nivelAcademico = new ArrayList<>();
			Item item = null;
			while (rs.next()) {
				item = new Item();
				Remote remote = new Remote();
				remote.setResTipo("");

				Local local = new Local();
				local.setResTipo("ICON");
				local.setNombre("cast_for_education");
				local.setExt("xml");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(local);
				archivo.setRemote(null);

				Color colorResItem = new Color();
				colorResItem.setTipo("TINT");
				colorResItem.setUso("DEFAULT");
				colorResItem.setDefault1(new Default("PRIMARYDARK"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);
				resItem.setColor(colorResItem);

				Default default1 = new Default();
				default1.setNombre("PRIMARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("INSTRUCCION"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("SECONDARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(rs.getString("INSTITUCION"));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Linea terceraLinea = new Linea();
				terceraLinea.setTexto(new Texto(rs.getString("CARRERA"), estiloTextoSegundaLinea));
				terceraLinea.setAction(null);

				Linea cuartaLinea = new Linea();
				cuartaLinea.setTexto(new Texto(rs.getString("EMPANIOEGRESO"), estiloTextoSegundaLinea));
				cuartaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setTerceraLinea(terceraLinea);
				item.setCuartaLinea(cuartaLinea);

				nivelAcademico.add(item);
			}

			rs.close();
			buscarNivel.close();

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
		return nivelAcademico;
	}

	@Override
	public List<Item> buscarCuentaHaberes(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarCuentaHaberes");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, codcia);
			buscarEmpleado.setString(2, codsuc);
			buscarEmpleado.setString(3, codtra);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("Banco", rs.getString("BANCOHAB"));
				datoEmpleado.put("Nro. Cuenta", rs.getString("EMPNROCTHAB"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarCuentaCTS(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarCuentaCTS");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, codcia);
			buscarEmpleado.setString(2, codsuc);
			buscarEmpleado.setString(3, codtra);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("Banco", rs.getString("BANCOCTS"));
				datoEmpleado.put("Nro. Cuenta", rs.getString("EMPNROCTCTS"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarPension(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarFondoPensiones");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, codcia);
			buscarEmpleado.setString(2, codsuc);
			buscarEmpleado.setString(3, codtra);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("Tipo", rs.getString("AFP"));
				datoEmpleado.put("Código", rs.getString("EMPNROAFP"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				// ResItem resItem = new ResItem();
				// resItem.setTipo("AVATAR40");
				// resItem.setArchivo(archivo);
				// resItem.setColor(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Item> buscarSeguros(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarSeguros");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, codcia);
			buscarEmpleado.setString(2, codsuc);
			buscarEmpleado.setString(3, codtra);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			Map<String, String> datoEmpleado = new LinkedHashMap<>();
			if (rs.next()) {
				datoEmpleado.put("EPS", rs.getString("EPS"));
				datoEmpleado.put("Seguro Vida", rs.getString("VIDA"));
				datoEmpleado.put("SCTR", rs.getString("SCTR"));
				datoEmpleado.put("Vida Ley", rs.getString("VIDA_LEY"));
			}

			for (String key : datoEmpleado.keySet()) {
				item = new Item();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setLocal(null);
				archivo.setRemote(null);

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(key);
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(datoEmpleado.get(key));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(null);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public List<Widget> buscarBienesAsignados(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Widget> widgets = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarBienesAsignados");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarBien = conexion.prepareStatement(query);
			buscarBien.setString(1, codcia);
			buscarBien.setString(2, codsuc);
			buscarBien.setString(3, codtra);

			ResultSet rs = buscarBien.executeQuery();

			Map<String, String> datoEmpleado = new LinkedHashMap<>();

			if (rs.next()) {
				datoEmpleado.put("Fotocheck", rs.getString("EMPFOTOCHCK"));
				datoEmpleado.put("Minutos Asignados", rs.getString("EMPMINASIGCEL"));
				datoEmpleado.put("Auto", rs.getString("EMPAUTO"));
				datoEmpleado.put("Soat", rs.getString("EMPSOAT"));
				datoEmpleado.put("Laptop", rs.getString("EMPLAPTOP"));
				datoEmpleado.put("Vale Gasolina", rs.getString("EMPVALESGAS"));
				datoEmpleado.put("Pension", rs.getString("EMPPENSION"));
				datoEmpleado.put("Uniforme", rs.getString("EMPUNIFORM"));
			}

			Widget widget = null;
			widgets = new ArrayList<>();
			for (String key : datoEmpleado.keySet()) {
				String indicador = datoEmpleado.get(key);
				if (indicador != null) {

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
					titulo.setTexto(key);
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
					valor.setTexto(indicador);
					valor.setEstilo(estiloTitulo);

					Texto subtitulo = new Texto();
					subtitulo.setTexto("");
					subtitulo.setEstilo(estiloTitulo);

					widget = new Widget();
					widget.setTitulo(titulo);
					widget.setResItem(resItem);
					widget.setValor(valor);
					widget.setSubtitulo(null);
					widget.setColor(colorResItem);

					widgets.add(widget);
				}
			}
			if (rs.next()) {

			}
			rs.close();
			buscarBien.close();

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
		return widgets;
	}

	@Override
	public List<Item> buscarDependientesXIdEmpleado(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("listarDependientes");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarEmpleado = conexion.prepareStatement(query);
			buscarEmpleado.setString(1, codcia);
			buscarEmpleado.setString(2, codsuc);
			buscarEmpleado.setString(3, codtra);

			ResultSet rs = buscarEmpleado.executeQuery();

			items = new ArrayList<>();
			Item item = null;

			while (rs.next()) {
				item = new Item();

				String foto = rs.getString("DEPFOTO");
				String url = "http://";
				if (foto != null) {
					url = ApiConstantes.URL_BASE_REPOSITORIO + codcia + "/FOTO_EMPLEADO/" + foto;
				}

				Remote remote = new Remote();
				remote.setResTipo("AVATAR40");
				remote.setUrl(url);
				remote.setNombre(foto);
				remote.setDimensionRatio(new DimensionRatio("W,40:40", "40", "40"));
				remote.setExt("JPG");

				Default default1 = new Default();
				default1.setNombre("SECONDARYDARK");

				Color colorDefault = new Color();
				colorDefault.setTipo("TEXT");
				colorDefault.setUso("DEFAULT");
				colorDefault.setDefault1(default1);
				colorDefault.setCustom(null);

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTE");
				archivo.setTipo("IMAGEN");
				archivo.setRemote(remote);

				ResItem resItem = new ResItem();
				resItem.setTipo("AVATAR40");
				resItem.setArchivo(archivo);

				EstiloTexto estiloTextoPrimeraLinea = new EstiloTexto();
				estiloTextoPrimeraLinea.setColor(colorDefault);
				estiloTextoPrimeraLinea.setCustom(null);
				estiloTextoPrimeraLinea.setFuente(null);

				Default default2 = new Default();
				default2.setNombre("PRIMARYDARK");

				Color colorDefault2 = new Color();
				colorDefault2.setTipo("TEXT");
				colorDefault2.setUso("DEFAULT");
				colorDefault2.setDefault1(default2);
				colorDefault2.setCustom(null);

				EstiloTexto estiloTextoSegundaLinea = new EstiloTexto();
				estiloTextoSegundaLinea.setColor(colorDefault2);
				estiloTextoSegundaLinea.setCustom(null);
				estiloTextoSegundaLinea.setFuente(null);

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("NOMBRE"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto("FE - " + rs.getString("DEPFECNAC"));
				textoSegundaLinea.setEstilo(estiloTextoSegundaLinea);

				String edad = rs.getString("EDAD");

				Texto textoTercerLinea = new Texto();
				if (!("0").equals(edad)) {
					textoTercerLinea.setTexto(edad + " años");
				} else {
					textoTercerLinea.setTexto("No contiene dato");
				}
				textoTercerLinea.setEstilo(estiloTextoSegundaLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Linea tercerLinea = new Linea();
				tercerLinea.setTexto(textoTercerLinea);
				tercerLinea.setAction(null);

				Linea cuartaLinea = new Linea(new Texto(rs.getString("VINCULO"), estiloTextoSegundaLinea));

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setTerceraLinea(tercerLinea);
				item.setCuartaLinea(cuartaLinea);

				items.add(item);
			}

			rs.close();
			buscarEmpleado.close();

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
	public Aprobador buscarAprobador(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {

		Aprobador aprobador = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("buscarAprobador");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement resumen = conexion.prepareStatement(query);
			resumen.setString(1, codcia);
			resumen.setString(2, codsuc);
			resumen.setString(3, codcia);
			resumen.setString(4, codsuc);
			resumen.setString(5, codtra);

			ResultSet rs = resumen.executeQuery();

			if (rs.next()) {
				aprobador = new Aprobador();

				String foto = rs.getString("EMPFOTO");
				String url = "http://";
				if (foto != null) {
					url = ApiConstantes.URL_BASE_REPOSITORIO + codcia + "/FOTO_EMPLEADO/" + foto;
				}

				Remote remote = new Remote();
				remote.setResTipo("AVATAR40");
				remote.setUrl(url);
				remote.setNombre(foto);
				remote.setExt("JPG");

				Archivo archivo = new Archivo();
				archivo.setTipo("IMAGEN");
				archivo.setAlmaTipo("REMOTE");
				archivo.setRemote(remote);

				ResItem resItem = new ResItem();
				resItem.setArchivo(archivo);
				resItem.setTipo("AVATAR40");

				Color color = new Color();
				color.setTipo("TEXT");
				color.setUso("DEFAULT");
				color.setDefault1(new Default("PRIMARYDARK"));

				EstiloTexto estilo = new EstiloTexto();
				estilo.setColor(color);

				aprobador.setResItem(resItem);
				aprobador.setTexto(new Texto(rs.getString("NOMBRE"), estilo));
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
		return aprobador;
	}

}
