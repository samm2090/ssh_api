package pe.com.human.api.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.api.dao.ConsultaDAO;
import pe.com.human.api.exception.ExcepcionBDNoResponde;
import pe.com.human.api.model.Alerta;
import pe.com.human.api.model.Archivo;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Extra;
import pe.com.human.api.model.Info;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Linea;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.Textos;
import pe.com.human.api.util.ConexionBaseDatos;
import pe.com.human.api.util.ConfiguracionDataSource;
import pe.com.human.api.util.PropertiesReader;

@Repository
public class ConsultaDAOImpl implements ConsultaDAO {

	@Autowired
	PropertiesReader lector;

	@Override
	public List<Item> listarConsultas(String codcia, String codsuc, String codtra, int rows,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/consulta.query").getProperty("listarConsultas");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarConsultas = conexion.prepareStatement(query);
			listarConsultas.setString(1, codcia);
			listarConsultas.setString(2, codsuc);
			listarConsultas.setString(3, codtra);
			listarConsultas.setInt(4, rows);

			ResultSet rs = listarConsultas.executeQuery();

			items = new ArrayList<>();

			Item item = null;

			while (rs.next()) {
				item = new Item();

				Color colorAlerta = new Color();
				colorAlerta.setTipo("TEXT");
				colorAlerta.setUso("DEFAULT");
				colorAlerta.setDefault1(new Default("PRIMARYDARK"));

				EstiloTexto estiloAlerta = new EstiloTexto();
				estiloAlerta.setColor(colorAlerta);

				String envio = rs.getString("CON_F_CREA");
				String respuesta = rs.getString("CON_F_MOD");
				String tipo = "APPROVED";

				if (respuesta == null) {
					respuesta = "Pendiente";
					tipo = "PENDING";
				}

				Alerta alerta = new Alerta();
				alerta.setTipo(tipo);
				alerta.setEstilo(estiloAlerta);

				Linea primeraLinea = new Linea();
				primeraLinea.setTextos(new Textos(new Texto(envio), new Texto(respuesta)));

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(new Texto(rs.getString("TIPO")));

				String pregunta = rs.getString("CON_HR_MENSAJE");
				pregunta = StringUtils.abbreviate(pregunta, 50);

				Linea terceraLinea = new Linea();
				terceraLinea.setTexto(new Texto(pregunta));

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(new Local("ICON", "time", "xml"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);

				item.setAlerta(alerta);
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setTerceraLinea(terceraLinea);
				item.setResItem(resItem);
				item.setExtra(new Extra(rs.getString("CON_I_CONSULTA")));

				items.add(item);
			}

			rs.close();
			listarConsultas.close();

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
	public List<Map<String, String>> listarTipos(String codcia, String codsuc,
			ConfiguracionDataSource configuracionDataSource) {
		List<Map<String, String>> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/consulta.query").getProperty("listarTipos");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarTipos = conexion.prepareStatement(query);
			// listarTipos.setString(1, codcia);
			// listarTipos.setString(2, codsuc);

			ResultSet rs = listarTipos.executeQuery();

			items = new ArrayList<>();

			while (rs.next()) {
				Map<String, String> item = new HashMap<>();

				item.put("id", rs.getString("TTBKEYOBJ"));
				item.put("descripcion", rs.getString("TTBDESOBJ"));

				items.add(item);
			}
			rs.close();
			listarTipos.close();

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
	public List<Item> listarConsultasEstado(String codcia, String codsuc, String codtra, String estado,
			ConfiguracionDataSource configuracionDataSource) {
		List<Item> items = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/consulta.query").getProperty("listarConsultasEstado");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement listarConsultas = conexion.prepareStatement(query);
			listarConsultas.setString(1, codcia);
			listarConsultas.setString(2, codsuc);
			listarConsultas.setString(3, codtra);
			listarConsultas.setString(4, estado);

			ResultSet rs = listarConsultas.executeQuery();

			items = new ArrayList<>();

			Item item = null;

			while (rs.next()) {
				item = new Item();

				Color colorAlerta = new Color();
				colorAlerta.setTipo("TEXT");
				colorAlerta.setUso("DEFAULT");
				colorAlerta.setDefault1(new Default("PRIMARYDARK"));

				EstiloTexto estiloAlerta = new EstiloTexto();
				estiloAlerta.setColor(colorAlerta);

				String envio = rs.getString("CON_F_CREA");
				String respuesta = rs.getString("CON_F_MOD");
				String tipo = "APPROVED";

				if (respuesta == null) {
					respuesta = "Pendiente";
					tipo = "PENDING";
				}

				Alerta alerta = new Alerta();
				alerta.setTipo(tipo);
				alerta.setEstilo(estiloAlerta);

				Linea primeraLinea = new Linea();
				primeraLinea.setTextos(new Textos(new Texto(envio), new Texto(respuesta)));

				Linea segundaLinea = new Linea();
				segundaLinea.setTexto(new Texto(rs.getString("TIPO")));

				String pregunta = rs.getString("CON_HR_MENSAJE");
				pregunta = StringUtils.abbreviate(pregunta, 50);

				Linea terceraLinea = new Linea();
				terceraLinea.setTexto(new Texto(pregunta));

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(new Local("ICON", "time", "xml"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaEnvio = null;
				try {
					fechaEnvio = sdf.parse(envio);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				SimpleDateFormat sdfMes = new SimpleDateFormat("MMMM");
				SimpleDateFormat sdfAno = new SimpleDateFormat("YYYY");

				item.setAlerta(alerta);
				// item.setTipo("SINGLE_LINE_ICON_RIGHT");
				item.setPrimeraLinea(primeraLinea);
				item.setSegundaLinea(segundaLinea);
				item.setTerceraLinea(terceraLinea);
				item.setResItem(resItem);
				item.setExtra(new Extra(WordUtils.capitalize(sdfMes.format(fechaEnvio)),
						WordUtils.capitalize(sdfAno.format(fechaEnvio))));

				items.add(item);
			}

			rs.close();
			listarConsultas.close();

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
	public Info buscarConsultaId(String codcia, String codsuc, String codtra, String idConsulta,
			ConfiguracionDataSource configuracionDataSource) {
		Info info = null;
		Connection conexion = null;

		String query = lector.leerPropiedad("queries/consulta.query").getProperty("buscarConsultaId");

		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement buscarConsulta = conexion.prepareStatement(query);
			buscarConsulta.setString(1, codcia);
			buscarConsulta.setString(2, codsuc);
			buscarConsulta.setString(3, codtra);
			buscarConsulta.setString(4, idConsulta);

			ResultSet rs = buscarConsulta.executeQuery();

			if (rs.next()) {
				info = new Info();

				String estado = rs.getString("CON_E_ESTADO");
				String tipo = "PENDING";

				if (("1").equals(estado)) {
					tipo = "ANSWERED";
				}

				Color color = new Color();
				color.setTipo("TEXT");
				color.setUso("DEFAULT");
				color.setDefault1(new Default("SECONDARYDARK"));

				EstiloTexto estiloAlerta = new EstiloTexto();
				estiloAlerta.setColor(color);

				Alerta alerta = new Alerta();
				alerta.setTipo(tipo);
				alerta.setEstilo(estiloAlerta);

				Archivo archivo = new Archivo();
				archivo.setAlmaTipo("LOCAL");
				archivo.setTipo("VECTOR");
				archivo.setLocal(new Local("ICON", "Approved", "xml"));

				ResItem resItem = new ResItem();
				resItem.setTipo("ICON");
				resItem.setArchivo(archivo);

				List<Map<String, Object>> thread = new ArrayList<>();

				Map<String, Object> preguntaContenido = new HashMap<>();
				preguntaContenido.put("content", rs.getString("CON_HR_MENSAJE"));
				preguntaContenido.put("created", rs.getString("CON_F_CREA"));
				preguntaContenido.put("senderID", rs.getString("CON_C_USU_EMI"));
				preguntaContenido.put("senderName", rs.getString("EMISOR"));

				Map<String, Object> respuestaContenido = new HashMap<>();
				respuestaContenido.put("content", rs.getString("CON_HR_RESPUESTA"));
				respuestaContenido.put("created", rs.getString("CON_F_MOD"));
				respuestaContenido.put("senderID", rs.getString("CON_C_USU_REC"));
				respuestaContenido.put("senderName", rs.getString("RECEPTOR"));

				Map<String, Object> pregunta = new HashMap<>();
				pregunta.put(idConsulta, preguntaContenido);

				Map<String, Object> respuesta = new HashMap<>();
				respuesta.put(idConsulta, respuestaContenido);

				thread.add(pregunta);
				thread.add(respuesta);

				Map<String, Object> mensaje = new HashMap<>();
				mensaje.put("name", rs.getString("TIPO"));
				mensaje.put("thread", thread);

				Map<String, Object> chatMap = new HashMap<>();
				chatMap.put(idConsulta, mensaje);

				List<Map<String, Object>> chat = new ArrayList<>();
				chat.add(chatMap);

				info.setAlerta(alerta);
				info.setResItem(resItem);
				info.setChat(chat);
			}

			rs.close();
			buscarConsulta.close();

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
		return info;
	}

	@Override
	public boolean insertarConsulta(String codcia, String codsuc, String codtra, String idTipo, String mensaje,
			ConfiguracionDataSource configuracionDataSource) {
		String query = lector.leerPropiedad("queries/consulta.query").getProperty("insertarConsulta");
		boolean resultado = false;

		Connection conexion = null;
		try {
			conexion = ConexionBaseDatos.obtenerConexion(configuracionDataSource);

			PreparedStatement insertarConsulta = conexion.prepareStatement(query);
			insertarConsulta.setString(1, codcia);
			insertarConsulta.setString(2, codsuc);
			insertarConsulta.setString(3, codtra);
			insertarConsulta.setString(4, codtra);
			insertarConsulta.setString(5, "1");
			insertarConsulta.setString(6, mensaje);
			insertarConsulta.setString(7, idTipo);

			resultado = insertarConsulta.executeUpdate() > 0 ? true : false;

			conexion.commit();
			insertarConsulta.close();
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
