package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.model.Action;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Custom;
import pe.com.human.api.model.Datos;
import pe.com.human.api.model.DatosLaborales;
import pe.com.human.api.model.DatosPersonales;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.Documento;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.EmpleadoResumen;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.ItemCumpleanos;
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

			if (rs.next()) {
				Texto nombreTexto = new Texto(rs.getString("EMPNOMBRE"), null);
				Texto apePaternoTexto = new Texto(rs.getString("EMPAPATERN"), null);
				Texto apeMaternoTexto = new Texto(rs.getString("EMPAMATERN"), null);

				NombreCompleto nombre = new NombreCompleto();
				nombre.setNombre(nombreTexto);
				nombre.setApePaterno(apePaternoTexto);
				nombre.setApeMaterno(apeMaternoTexto);

				Texto numeroDocumento = new Texto(rs.getString("EMPNRODOCID"), null);
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
			}

			// ResItem resItem = new ResItem();
			// resItem.setTipo(rs.getString(""));
			// resItem.setArchivo(archivo);;
			empleadoResumen.setResItem(null);

			rs.close();
			buscarEmpleado.close();

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
		return empleadoResumen;
	}

	@Override
	public List<ItemCumpleanos> listarCumpleanos(String idCompania, String idSucursal,
			ConfiguracionDataSource configuracionDataSource) {

		List<ItemCumpleanos> cumpleanos = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/empleado.query").getProperty("listarCumpleanos");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarEmpleados = conexion.prepareStatement(query);
			listarEmpleados.setString(1, idCompania);
			listarEmpleados.setString(2, idSucursal);

			ResultSet rs = listarEmpleados.executeQuery();

			cumpleanos = new ArrayList<>();
			ItemCumpleanos item = null;
			while (rs.next()) {
				item = new ItemCumpleanos();

				Remote remote = new Remote();
				remote.setResTipo("");

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("REMOTO");
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

				Texto textoPrimeraLinea = new Texto();
				textoPrimeraLinea.setTexto(rs.getString("NOMBRE"));
				textoPrimeraLinea.setEstilo(estiloTextoPrimeraLinea);

				Linea primeraLinea = new Linea();
				primeraLinea.setTexto(textoPrimeraLinea);
				primeraLinea.setAction(null);

				Texto textoSegundaLinea = new Texto();
				textoSegundaLinea.setTexto(rs.getString("PUEDESPUE"));
				textoSegundaLinea.setEstilo(estiloTextoPrimeraLinea);

				Local localSegundaLinea = new Local();
				localSegundaLinea.setResTipo("ICON");
				localSegundaLinea.setNombre("phone");
				localSegundaLinea.setExt("xml");

				Archivo archivoSegundaLinea = new Archivo();
				archivoSegundaLinea.setAlmaTipo("LOCAL");
				archivoSegundaLinea.setTipo("VECTOR");
				archivoSegundaLinea.setRemote(null);
				archivo.setLocal(localSegundaLinea);

				Default defaultSegundaLinea = new Default();
				default1.setNombre("PRIMARYDARK");

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

}
