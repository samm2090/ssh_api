package pe.com.human.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.ConsultaDAO;
import pe.com.human.api.model.Info;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.apirequest.EmpleadoConsultaRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConfiguracionDataSource;

@Service
public class ConsultaService {

	@Autowired
	BaseDatosDAO baseDatosDAO;

	@Autowired
	ConsultaDAO consultaDAO;

	public Map<String, Object> listarRecientes(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		int rows = 2;
		Map<String, Object> recientes = new HashMap<>();
		List<Item> items = consultaDAO.listarConsultas(codcia, codsuc, codtra, rows, configuracionDataSource);
	
		recientes.put("tipo", "SINGLE_LINE_ICON_RIGHT");
		recientes.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("recientes", recientes);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> listarTipos(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		Map<String, Object> tipo = new HashMap<>();
		List<Map<String, String>> items = consultaDAO.listarTipos(codcia, codsuc, configuracionDataSource);

		tipo.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("tipo", tipo);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> listarHistorial(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String estado = "%";
		Map<String, Object> historial = new HashMap<>();
		List<Item> items = consultaDAO.listarConsultasEstado(codcia, codsuc, codtra, estado, configuracionDataSource);

		historial.put("tipo", "SINGLE_LINE_ICON_RIGHT");
		historial.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("historial", historial);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> listarPendientes(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		String estado = "0";
		Map<String, Object> pendientes = new HashMap<>();
		List<Item> items = consultaDAO.listarConsultasEstado(codcia, codsuc, codtra, estado, configuracionDataSource);

		pendientes.put("tipo", "SINGLE_LINE_ICON_RIGHT");
		pendientes.put("items", items);

		Map<String, Object> data = new HashMap<>();

		data.put("pendientes", pendientes);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> detalle(EmpleadoConsultaRequest empleado) {
		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());
		String idConsulta = empleado.getConsulta().getId();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);
		Map<String, Object> respuesta = new HashMap<>();

		Info info = consultaDAO.buscarConsultaId(codcia, codsuc, codtra, idConsulta, configuracionDataSource);

		Map<String, Object> data = new HashMap<>();

		data.put("info", info);
		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> enviar(EmpleadoConsultaRequest empleado) {
		boolean resultado = false;
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());
		String idTipo = empleado.getConsulta().getIdTipo();
		String mensaje = empleado.getConsulta().getMensaje();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		resultado = consultaDAO.insertarConsulta(codcia, codsuc, codtra, idTipo, mensaje, configuracionDataSource);

		Map<String, Object> data = new HashMap<>();
		data.put("mensaje", "Se envió la consulta exitosamente");
		respuesta.put("data", data);

		if (resultado) {
			// String ruta = "/" + codcia + "/images/Logo-THR-pequeno.png";
			// consultaEmpleado.setTipoConsulta(valorConceptualDao.find(consultaEmpleado,
			// "909"));
			// consultaEmpleado.setIdConsulta(resultado);
			// new Thread(new TareaEnvioCorreoConsultasAsincrono(empleado,
			// consultaEmpleado, ruta)).start();
		}

		return respuesta;
	}

}
