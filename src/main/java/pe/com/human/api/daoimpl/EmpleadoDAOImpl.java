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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.constants.ApiConstantes;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Action;
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

				empleadoResumen = new EmpleadoResumen();
				empleadoResumen.setDatos(datos);

				foto = rs.getString("EMPFOTO");
			}

			String url = null;
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
				String url = null;
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

				Archivo archivoSegundaLinea = new Archivo();
				archivoSegundaLinea.setAlmaTipo("LOCAL");
				archivoSegundaLinea.setTipo("VECTOR");
				archivoSegundaLinea.setRemote(null);
				archivoSegundaLinea.setLocal(localSegundaLinea);

				Default defaultSegundaLinea = new Default();
				defaultSegundaLinea.setNombre("PRIMARYDARK");

				Color colorSegundaLinea = new Color();
				colorSegundaLinea.setTipo("TINT");
				colorSegundaLinea.setUso("LOCAL");
				colorSegundaLinea.setDefault1(defaultSegundaLinea);
				colorSegundaLinea.setCustom(null);

				ResItem resItemSegundaLinea = new ResItem();
				resItemSegundaLinea.setTipo("PHONE");
				resItemSegundaLinea.setArchivo(archivoSegundaLinea);
				resItemSegundaLinea.setColor(colorSegundaLinea);

				Phone phone = new Phone();
				phone.setTipo("MOBILE");
				phone.setUri(rs.getString("EMPTELFMOV"));

				Action actionSegundaLinea = new Action();
				actionSegundaLinea.setTipo("PHONE");
				actionSegundaLinea.setResItem(resItemSegundaLinea);
				actionSegundaLinea.setPhone(phone);

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(actionSegundaLinea);

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

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
				SimpleDateFormat sdf = new SimpleDateFormat("EEEE',' MMM',' YYYY", new Locale("es", "PE"));

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
				textoSegundaLinea.setTexto(sdf.format(ferFecha));
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

				item.setTipo("SINGLE_LINE_ACTION");
				item.setResItem(resItem);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);

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

}
