package pe.com.human.api.dao;

import java.util.List;
import java.util.Map;

import pe.com.human.api.model.Info;
import pe.com.human.api.model.Item;
import pe.com.human.api.util.ConfiguracionDataSource;

public interface ConsultaDAO {

	public List<Item> listarConsultas(String codcia, String codsuc, String codtra, int rows,
			ConfiguracionDataSource configuracionDataSource);

	public List<Map<String, String>> listarTipos(String codcia, String codsuc, ConfiguracionDataSource configuracionDataSource);

	public List<Item> listarConsultasEstado(String codcia, String codsuc, String codtra, String estado,
			ConfiguracionDataSource configuracionDataSource);

	public Info buscarConsultaId(String codcia, String codsuc, String codtra, String idConsulta,
			ConfiguracionDataSource configuracionDataSource);

	public boolean insertarConsulta(String codcia, String codsuc, String codtra, String idTipo, String mensaje,
			ConfiguracionDataSource configuracionDataSource);

}
