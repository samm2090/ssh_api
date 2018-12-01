package pe.com.human.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.AssetsDAO;
import pe.com.human.api.dao.ColorDAO;
import pe.com.human.api.dao.CompaniaDAO;
import pe.com.human.api.model.Assets;
import pe.com.human.api.model.Colores;
import pe.com.human.api.model.Compania;
import pe.com.human.api.model.Estilo;

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

	
	public Map<String, Object> estiloCompania(int id) {
		Map<String, Object> respuesta = new HashMap<>();
		
		Map<String, Object> data = new HashMap<>();
		
		Compania compania = companiaDAO.buscarCompaniaXId(id);
		Estilo estilo = null;
		if(compania != null){
			estilo = new Estilo();
			
			List<Assets> assets = assetsDAO.listarAssetsXCompania(compania);
			List<Colores> colores = colorDAO.listarColoresXCompania(compania);
			
			estilo.setAssets(assets);
			estilo.setColores(colores);
			
			compania.setEstilo(estilo);
		}
		data.put("compania", compania);
		
		respuesta.put("data", data);
		
		return respuesta;
	}

	
	
}
