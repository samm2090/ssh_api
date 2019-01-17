package pe.com.human.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.AssetsDAO;
import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.ColorDAO;
import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.exception.ExcepcionCompaniaAssets;
import pe.com.human.api.exception.ExcepcionCriterioBusqueda;
import pe.com.human.api.model.Assets;
import pe.com.human.api.model.Color;
import pe.com.human.api.model.Colores;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Default;
import pe.com.human.api.model.DetalleDirectorio;
import pe.com.human.api.model.Estilo;
import pe.com.human.api.model.EstiloTexto;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.ResItem;
import pe.com.human.api.model.Texto;
import pe.com.human.api.model.apirequest.BaseRequest;
import pe.com.human.api.model.apirequest.DirectorioRequest;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
@Service
public class CompaniaService {

	@Autowired
	ColorDAO colorDAO;

	@Autowired
	AssetsDAO assetsDAO;

	@Autowired
	CompaniaDAO companiaDAO;

	@Autowired
	BaseDatosDAO baseDatosDAO;

	public Map<String, Object> estiloCompania(int id) {
		Map<String, Object> respuesta = new HashMap<>();

		Map<String, Object> data = new HashMap<>();

		Compania compania = companiaDAO.buscarCompaniaXId(id);
		Estilo estilo = null;
		if (compania != null) {
			estilo = new Estilo();

			List<Assets> assets = assetsDAO.listarAssetsXCompania(compania);
			List<Colores> colores = colorDAO.listarColoresXCompania(compania);

			if (assets.isEmpty()) {
				throw new ExcepcionCompaniaAssets();
			}
			estilo.setAssets(assets);
			estilo.setColores(colores);

			compania.setEstilo(estilo);
		} else {
			throw new ExcepcionCompaniaAssets();
		}

		data.put("compania", compania);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> directorioArea(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		ConfiguracionDataSource configuracionDataSource = baseDatosDAO
				.buscarConfiguracionXId(Integer.parseInt(empleado.getBase().getBaseDatos()));

		List<Item> items = companiaDAO.buscarDirectorioXEmpleadoArea(codcia, codsuc, codtra,
				configuracionDataSource);

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(new Default("SECONDARYDARK"));

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Contacto");
		titulo.setEstilo(estilo);

		data.put("titulo", titulo);
		data.put("items", items);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> directorioBusqueda(DirectorioRequest directorio) {
		Map<String, Object> respuesta = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		String codcia = directorio.getBase().getCompania().getId();
		String codsuc = directorio.getBase().getCompania().getSucursal().getId();

		String criterio = directorio.getCriterio();
		ConfiguracionDataSource configuracionDataSource = baseDatosDAO
				.buscarConfiguracionXId(Integer.parseInt(directorio.getBase().getBaseDatos()));

		try {
			criterio = criterio.replaceAll("\\s+", "%");
			criterio = "%" + criterio + "%";
			criterio = criterio.toUpperCase();
		} catch (Exception e) {
			throw new ExcepcionCriterioBusqueda();
		}

		List<Item> items = companiaDAO.buscarDirectorioCriterio(codcia, codsuc, criterio, configuracionDataSource);

		Color color = new Color();
		color.setTipo("TEXT");
		color.setUso("DEFAULT");
		color.setDefault1(new Default("SECONDARYDARK"));

		EstiloTexto estilo = new EstiloTexto();
		estilo.setFuente(null);
		estilo.setColor(color);
		estilo.setCustom(null);

		Texto titulo = new Texto();
		titulo.setTexto("Contacto");
		titulo.setEstilo(estilo);

		data.put("titulo", titulo);
		data.put("items", items);

		respuesta.put("data", data);

		return respuesta;
	}

	public Map<String, Object> directorioDetalle(EmpleadoRequest empleado) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = empleado.getBase().getCompania().getId();
		String codsuc = empleado.getBase().getCompania().getSucursal().getId();
		String codtra = empleado.getEmpleado().getId();
		ConfiguracionDataSource configuracionDataSource = baseDatosDAO
				.buscarConfiguracionXId(Integer.parseInt(empleado.getBase().getBaseDatos()));

		DetalleDirectorio detalle = companiaDAO.buscarDirectorioXEmpleado(codcia, codsuc, codtra,
				configuracionDataSource);

		respuesta.put("data", detalle);

		return respuesta;
	}

	public Map<String, Object> convenios(BaseRequest base) {
		Map<String, Object> respuesta = new HashMap<>();

		String codcia = base.getBase().getCompania().getId();
		String codsuc = base.getBase().getCompania().getSucursal().getId();

		ConfiguracionDataSource configuracionDataSource = baseDatosDAO
				.buscarConfiguracionXId(Integer.parseInt(base.getBase().getBaseDatos()));

		List<ResItem> convenios = companiaDAO.listarConvenios(codcia, codsuc, configuracionDataSource);

		respuesta.put("data", convenios);

		return respuesta;
	}

}
