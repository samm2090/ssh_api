package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.constants.ApiConstantes;
import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Action;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.DimensionRatio;
import pe.com.human.api.model.Directorio;
import pe.com.human.api.model.Email;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Linea;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.Phone;
import pe.com.human.api.model.Remote;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Sucursal;
import pe.com.human.api.model.Texto;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Repository
public class CompaniaDAOImpl implements CompaniaDAO {

	@Autowired
	PropertiesReader lector;

	private static Logger logger = Logger.getLogger(CompaniaDAOImpl.class);

	@Override
	public List<Map<String, Object>> listarCompaniasXDocumento(String documento) {
		List<Map<String, Object>> resultado = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("listarCompaniasXDocumento");

		try {
			ConfiguracionDataSource configuracion = new ConfiguracionDataSource();

			conexion = ConexionBaseDatos.obtenerConexion(configuracion);

			PreparedStatement listarCompanias = conexion.prepareStatement(query);
			listarCompanias.setString(1, documento);

			ResultSet rs = listarCompanias.executeQuery();

			Compania compania;
			Sucursal sucursal;
			Map<String, Object> companiaBase;
			resultado = new ArrayList<>();
			while (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("COM_C_HUMAN"));
				compania.setNombre(rs.getString("COM_D_NOMBRE"));

				sucursal = new Sucursal();
				sucursal.setId(rs.getString("SUC_C_HUMAN"));
				sucursal.setNombre(rs.getString("SUC_D_NOMBRE"));

				compania.setSucursal(sucursal);

				companiaBase = new HashMap<>();

				companiaBase.put("compania", compania);

				companiaBase.put("baseDatos", rs.getInt("BD_I_ID"));

				resultado.add(companiaBase);
			}
			rs.close();
			listarCompanias.close();

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
	public Compania buscarCompaniaXEmpleado(String idCompania, String idSucursal, String idEmpleado, String contrasenia,
			ConfiguracionDataSource configuracionDataSource) {

		Compania compania = null;

		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("buscarCompaniaXEmpleado");

		logger.info(query);
		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarCompania = conexion.prepareStatement(query);
			buscarCompania.setString(1, idCompania);
			buscarCompania.setString(2, idSucursal);
			buscarCompania.setString(3, idEmpleado);

			ResultSet rs = buscarCompania.executeQuery();

			compania = new Compania();
			if (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("EBCODCIA"));
				compania.setNombre(rs.getString("EBDESCIA"));

				Sucursal sucursal = new Sucursal();
				sucursal.setId(rs.getString("EBCODSUC"));
				sucursal.setNombre(rs.getString("EBDESSUC"));

				compania.setSucursal(sucursal);
			}
			rs.close();
			buscarCompania.close();

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
		return compania;
	}

	@Override
	public Compania buscarCompaniaXId(int id) {
		Compania compania = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("buscarCompaniaXId");

		try {
			ConfiguracionDataSource configuracion = new ConfiguracionDataSource();

			conexion = ConexionBaseDatos.obtenerConexion(configuracion);

			PreparedStatement buscarCompania = conexion.prepareStatement(query);
			buscarCompania.setInt(1, id);

			ResultSet rs = buscarCompania.executeQuery();

			if (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("COM_C_HUMAN"));
				compania.setNombre(rs.getString("COM_D_NOMBRE"));

				Sucursal sucursal = new Sucursal();
				sucursal.setId(rs.getString("SUC_C_HUMAN"));
				sucursal.setNombre(rs.getString("SUC_D_NOMBRE"));

				compania.setSucursal(sucursal);
			}
			rs.close();
			buscarCompania.close();

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
		return compania;
	}

	@Override
	public List<Directorio> buscarDirectorioXEmpleadoArea(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource) {
		List<Directorio> directorio = null;
		List<Item> valores = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("listarDirectorioArea");
		String area = "";

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarDirectorio = conexion.prepareStatement(query);
			listarDirectorio.setString(1, codcia);
			listarDirectorio.setString(2, codsuc);
			listarDirectorio.setString(3, codtra);
			listarDirectorio.setString(4, codcia);
			listarDirectorio.setString(5, codsuc);

			ResultSet rs = listarDirectorio.executeQuery();

			directorio = new ArrayList<>();
			valores = new ArrayList<>();
			Directorio item = null;
			while (rs.next()) {
				Item valor = new Item();

				String foto = rs.getString("EMPFOTO");
				String url = null;
				if (foto != null) {
					url = ApiConstantes.URL_BASE_REPOSITORIO + codcia + "/FOTO_EMPLEADO/" + foto;
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
				textoSegundaLinea.setTexto(rs.getString("PUESTO"));
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

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(textoSegundaLinea);
				segundaLinea.setAction(null);

				Archivo archivoPhone = new Archivo();
				archivoPhone.setAlmaTipo("LOCAL");
				archivoPhone.setTipo("VECTOR");
				archivoPhone.setLocal(new Local("ICON", "phone", "xml"));

				Color colorPhone = new Color();
				colorPhone.setTipo("TINT");
				colorPhone.setUso("DEFAULT");
				colorPhone.setDefault1(new Default("DEFAULT"));

				ResItem resItemPhone = new ResItem();
				resItemPhone.setTipo("ICON");
				resItemPhone.setArchivo(archivoPhone);
				resItemPhone.setColor(colorPhone);

				List<Action> action = new ArrayList<>();

				Action movilePhone = new Action();
				movilePhone.setTipo("PHONE");
				movilePhone.setResItem(resItemPhone);
				movilePhone.setPhone(new Phone("MOBILE", rs.getString("EMPTELFMOV")));

				Action workPhone = new Action();
				workPhone.setTipo("PHONE");
				workPhone.setResItem(resItemPhone);
				workPhone.setPhone(new Phone("WORK", rs.getString("EMPTELFFIJO")));

				Archivo archivoMail = new Archivo();
				archivoMail.setAlmaTipo("LOCAL");
				archivoMail.setTipo("VECTOR");
				archivoMail.setLocal(new Local("ICON", "email", "xml"));

				Color colorMail = new Color();
				colorMail.setTipo("TINT");
				colorMail.setUso("DEFAULT");
				colorMail.setDefault1(new Default("DEFAULT"));

				ResItem resItemMail = new ResItem();
				resItemMail.setTipo("ICON");
				resItemMail.setArchivo(archivoMail);
				resItemMail.setColor(colorMail);

				Action email = new Action();
				email.setTipo("EMAIL");
				email.setResItem(resItemMail);
				email.setEmail(new Email("PERSONAL", rs.getString("EMPEMAIL")));

				Action emailOrg = new Action();
				emailOrg.setTipo("EMAIL");
				emailOrg.setResItem(resItemMail);
				emailOrg.setEmail(new Email("WORK", rs.getString("EMPEMAILORG")));

				action.add(movilePhone);
				action.add(workPhone);
				action.add(email);
				action.add(emailOrg);

				valor.setResItem(resItem);
				valor.setPrimeraLinea(primeraLinea);
				valor.setSegundaLinea(segundaLinea);
				valor.setAction(action);
				valores.add(valor);

				area = rs.getString("AREA");
			}

			Color coloreEstiloArea = new Color();
			coloreEstiloArea.setTipo("TEXT");
			coloreEstiloArea.setUso("DEFAULT");
			coloreEstiloArea.setDefault1(new Default("SECONDARYDARK"));

			EstiloTexto estiloTextoArea = new EstiloTexto();
			estiloTextoArea.setFuente(null);
			estiloTextoArea.setColor(coloreEstiloArea);

			Texto textoArea = new Texto();
			textoArea.setTexto(area);
			textoArea.setEstilo(estiloTextoArea);

			item = new Directorio();
			item.setTipo("SINGLE_LINE_AVATAR");
			item.setArea(textoArea);
			item.setValores(valores);
			directorio.add(item);

			rs.close();
			listarDirectorio.close();

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
		return directorio;
	}

	@Override
	public List<Directorio> buscarDirectorioCriterio(String codcia, String codsuc, String criterio,
			ConfiguracionDataSource configuracionDataSource) {
		List<Directorio> directorio = null;
		List<Item> valores = null;
		Connection conexion = null;

		List<Map<String, String>> areas;

		String query = lector.leerPropiedad("queries/compania.query").getProperty("listarDirectorioCriterio");
		String query2 = lector.leerPropiedad("queries/compania.query").getProperty("listarDirectorioCodArea");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarDirectorio = conexion.prepareStatement(query);
			listarDirectorio.setString(1, codcia);
			listarDirectorio.setString(2, codsuc);
			listarDirectorio.setString(3, criterio);

			ResultSet rs = listarDirectorio.executeQuery();

			areas = new ArrayList<>();
			while (rs.next()) {
				Map<String, String> area = new HashMap<>();
				area.put("empcodarea", rs.getString("EMPCODAREA"));
				area.put("area", rs.getString("AREA"));

				areas.add(area);
			}

			directorio = new ArrayList<>();
			for (Map<String, String> area : areas) {
				PreparedStatement listarDir = conexion.prepareStatement(query2);
				listarDir.setString(1, codcia);
				listarDir.setString(2, codsuc);
				listarDir.setString(3, area.get("empcodarea"));
				listarDir.setString(4, criterio);

				ResultSet rs2 = listarDir.executeQuery();

				valores = new ArrayList<>();
				Directorio item = null;
				while (rs2.next()) {
					Item valor = new Item();
					
					String foto = rs.getString("EMPFOTO");
					String url = null;
					if (foto != null) {
						url = ApiConstantes.URL_BASE_REPOSITORIO + codcia + "/FOTO_EMPLEADO/" + foto;
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
					textoPrimeraLinea.setTexto(rs2.getString("NOMBRE"));
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
					textoSegundaLinea.setTexto(rs2.getString("PUESTO"));
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
					phone.setUri(rs2.getString("EMPTELFMOV"));

					Linea segundaLinea = new Linea();
					segundaLinea.setTexto(textoSegundaLinea);
					segundaLinea.setAction(null);

					Archivo archivoPhone = new Archivo();
					archivoPhone.setAlmaTipo("LOCAL");
					archivoPhone.setTipo("VECTOR");
					archivoPhone.setLocal(new Local("ICON", "phone", "xml"));

					Color colorPhone = new Color();
					colorPhone.setTipo("TINT");
					colorPhone.setUso("DEFAULT");
					colorPhone.setDefault1(new Default("DEFAULT"));

					ResItem resItemPhone = new ResItem();
					resItemPhone.setTipo("ICON");
					resItemPhone.setArchivo(archivoPhone);
					resItemPhone.setColor(colorPhone);

					List<Action> action = new ArrayList<>();

					Action movilePhone = new Action();
					movilePhone.setTipo("PHONE");
					movilePhone.setResItem(resItemPhone);
					movilePhone.setPhone(new Phone("MOBILE", rs2.getString("EMPTELFMOV")));

					Action workPhone = new Action();
					workPhone.setTipo("PHONE");
					workPhone.setResItem(resItemPhone);
					workPhone.setPhone(new Phone("WORK", rs2.getString("EMPTELFFIJO")));

					Archivo archivoMail = new Archivo();
					archivoMail.setAlmaTipo("LOCAL");
					archivoMail.setTipo("VECTOR");
					archivoMail.setLocal(new Local("ICON", "email", "xml"));

					Color colorMail = new Color();
					colorMail.setTipo("TINT");
					colorMail.setUso("DEFAULT");
					colorMail.setDefault1(new Default("DEFAULT"));

					ResItem resItemMail = new ResItem();
					resItemMail.setTipo("ICON");
					resItemMail.setArchivo(archivoMail);
					resItemMail.setColor(colorMail);

					Action email = new Action();
					email.setTipo("EMAIL");
					email.setResItem(resItemMail);
					email.setEmail(new Email("PERSONAL", rs2.getString("EMPEMAIL")));

					Action emailOrg = new Action();
					emailOrg.setTipo("EMAIL");
					emailOrg.setResItem(resItemMail);
					emailOrg.setEmail(new Email("WORK", rs2.getString("EMPEMAIL")));

					action.add(movilePhone);
					action.add(workPhone);
					action.add(email);
					action.add(emailOrg);

					valor.setResItem(resItem);
					valor.setPrimeraLinea(primeraLinea);
					valor.setSegundaLinea(segundaLinea);
					valor.setAction(action);
					valores.add(valor);
				}
				Color coloreEstiloArea = new Color();
				coloreEstiloArea.setTipo("TEXT");
				coloreEstiloArea.setUso("DEFAULT");
				coloreEstiloArea.setDefault1(new Default("SECONDARYDARK"));

				EstiloTexto estiloTextoArea = new EstiloTexto();
				estiloTextoArea.setFuente(null);
				estiloTextoArea.setColor(coloreEstiloArea);

				Texto textoArea = new Texto();
				textoArea.setTexto(area.get("area"));
				textoArea.setEstilo(estiloTextoArea);

				item = new Directorio();
				item.setTipo("SINGLE_LINE_ACTION");
				item.setArea(textoArea);
				item.setValores(valores);
				directorio.add(item);

				rs2.close();
				listarDir.close();
			}

			rs.close();
			listarDirectorio.close();

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
		return directorio;
	}

}
