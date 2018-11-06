package pe.com.human.servicios.dao;

import java.util.List;

import pe.com.human.servicios.model.Compania;

/**
 * 
 * @author SERGIO MUROY
 *
 */
public interface CompaniaDAO {
	public List<Compania> listarCompaniasXDocumento(String documento);
}
