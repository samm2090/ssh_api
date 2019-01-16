package pe.com.human.api.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.com.human.api.constants.ApiConstantes;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.model.EmpleadoApp;
import pe.com.human.api.model.apirequest.NotificacionHumanRequest;
import pe.com.human.api.model.apirequest.NotificacionRequest;
import pe.com.human.api.model.apirequest.NotificacionRequest.Data;

@Service
public class NotificacionService {

	@Autowired
	EmpleadoDAO empleadoDAO;

	public Map<String, Object> enviarNotificacion(NotificacionHumanRequest notificacion) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		String titulo = notificacion.getTitulo();
		String mensaje = notificacion.getMensaje();
		String documento = notificacion.getEmpleado().getDocumento();
		String codcia = notificacion.getBase().getCompania().getId();
		String codsuc = notificacion.getBase().getCompania().getSucursal().getId();
		String baseDatos = notificacion.getBase().getBaseDatos();

		EmpleadoApp empleado = empleadoDAO.buscarEmpleadoApp(codcia, codsuc, baseDatos, documento);

		Data dataNotificacion = new Data();
		dataNotificacion.setConsultaId(notificacion.getExtra().get("idConsulta"));
		dataNotificacion.setEmpleadoId(notificacion.getExtra().get("idEmpleado"));
		dataNotificacion.setTitle(titulo);
		dataNotificacion.setBody(mensaje);

		NotificacionRequest notificacionRequest = new NotificacionRequest();
		notificacionRequest.setTo(empleado.getCodigoFirebase());
		notificacionRequest.setPriority("normal");
		notificacionRequest.setData(dataNotificacion);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", ApiConstantes.FIREBASE_AUTH_KEY);

		HttpEntity<NotificacionRequest> requestEntity = new HttpEntity<>(notificacionRequest, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity(ApiConstantes.URL_SERVICIO_PUSH, requestEntity,
				String.class);

		data.put("mensaje", result);
		respuesta.put("data", data);

		return respuesta;
	}

}
