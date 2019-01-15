package pe.com.human.api.dao;

import java.util.List;

import pe.com.human.api.model.Aprobador;
import pe.com.human.api.model.Empleado;
import pe.com.human.api.model.EmpleadoResumen;
import pe.com.human.api.model.Item;
import pe.com.human.api.model.Widget;
import pe.com.human.api.model.apirequest.EmpleadoRequest;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * 
 * @author smuroy
 *
 */
public interface EmpleadoDAO {

	public Empleado buscarEmpleadoXUsuario(String idCompania, String idSucursal, String documento, String contrasenia,
			ConfiguracionDataSource configuracionDataSource);

	public Widget cantidadSubordinados(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public EmpleadoResumen buscarEmpleadoResumen(EmpleadoRequest empleado,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> listarCumpleanos(String idCompania, String idSucursal,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> listarFeriados(String idCompania, String idSucursal,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarInformacionGeneral(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarInformacionLaboral(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarDatosDireccion(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);
	
	public List<Item> buscarDatosEmergencia(String idCompania, String idSucursal, String idEmpleado,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarNivelAcademico(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarCuentaHaberes(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarPension(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarCuentaCTS(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarSeguros(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Widget> buscarBienesAsignados(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public List<Item> buscarDependientesXIdEmpleado(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public Aprobador buscarAprobador(String codcia, String codsuc, String codtra,
			ConfiguracionDataSource configuracionDataSource);

	public boolean insertarCodigoFirebase(String codcia, String codsuc, int baseDatos, String documento, String codigo);
}
