package pe.com.human.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.BoletaDAO;
import pe.com.human.api.model.BoletaEmpleado;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Proceso;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.apirequest.EmpleadoProcesoRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConfiguracionDataSource;

@Service
public class BoletaService {

	@Autowired
	BoletaDAO boletaDAO;

	@Autowired
	BaseDatosDAO baseDatosDAO;

	public Map<String, Object> buscarBoletasXEmpleado(EmpleadoProcesoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		Map<String, Object> data = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());
		String idProceso = empleado.getEmpleado().getIdProceso();
//		String idProceso = "";

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		List<BoletaEmpleado> boletas = boletaDAO.listarBoletasXIdEmpleado(codcia, codsuc, codtra, idProceso,
				configuracionDataSource);

		Texto texto = new Texto();
		texto.setTexto("Boletas de Pago");

		data.put("titulo", texto);
		data.put("boletasPago", boletas);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> buscarProcesosXEmpleado(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		Map<String, Object> data = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		int baseDatos = Integer.parseInt(empleado.getBase().getBaseDatos());

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO.buscarConfiguracionXId(baseDatos);

		List<Proceso> procesos = boletaDAO.listarProcesosXIdEmpleado(codcia, codsuc, codtra, configuracionDataSource);

		Default default1 = new Default();
		default1.setNombre("SECONDARYDARK");

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(default1);
		color.setCustom(null);

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);
		
		Texto texto = new Texto();
		texto.setTexto("Boletas de Pago");
		texto.setEstilo(estilo);

		data.put("titulo", texto);
		data.put("procesos", procesos);

		respuesta.put("data", data);

		return respuesta;
	}

}
