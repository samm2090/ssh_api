package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.EmpleadoDAO;
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
import pe.com.human.api.model.Local;
import pe.com.human.api.model.NombreCompleto;
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
			
//			ResItem resItem = new ResItem();
//			resItem.setTipo(rs.getString(""));
//			resItem.setArchivo(archivo);;
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

}
