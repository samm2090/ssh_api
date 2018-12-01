package pe.com.human.api.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import pe.com.human.api.model.AlmacenamientoTipo;
import pe.com.human.api.model.ArchivoTipo;
import pe.com.human.api.model.DimensionRatio;
import pe.com.human.api.model.Local;
import pe.com.human.api.model.Remote;

@Component
public class ModelResultSet {
	
	public AlmacenamientoTipo almaTipoResultSet(ResultSet rs) throws SQLException {
		AlmacenamientoTipo almaTipo = new AlmacenamientoTipo();
		almaTipo.setId(rs.getString("ALT_I_ID"));
		almaTipo.setNombre(rs.getString("ALT_D_NOMBRE"));
		almaTipo.setEstado(rs.getString("ALT_I_EST"));

		return almaTipo;
	}

	public ArchivoTipo archivoTipoResultSet(ResultSet rs) throws SQLException {
		ArchivoTipo archivoTipo = new ArchivoTipo();
		archivoTipo.setId(rs.getString("ART_I_ID"));
		archivoTipo.setNombre(rs.getString("ART_D_NOMBRE"));
		archivoTipo.setEstado(rs.getString("ART_E_EST"));

		return archivoTipo;
	}

	public Local localTipoResultSet(ResultSet rs) throws SQLException {
		DimensionRatio dimensionRatio = dimensionRatioResultSet(rs);
		Local local = new Local();
		local.setResTipo(rs.getString("ART_D_NOMBRE"));
		local.setNombre(rs.getString("ARC_D_NOMBRE"));
		local.setDimensionRatio(dimensionRatio);
		local.setExt(rs.getString("ARE_D_EXT"));

		return local;
	}

	public Remote remotoTipoResultSet(ResultSet rs) throws SQLException {
		DimensionRatio dimensionRatio = dimensionRatioResultSet(rs);
		Remote remoto = new Remote();
		remoto.setResTipo(rs.getString("ART_D_NOMBRE"));
		remoto.setUrl(rs.getString("ARC_D_URL"));
		remoto.setNombre(rs.getString("ARC_D_NOMBRE"));
		remoto.setDimensionRatio(dimensionRatio);
		remoto.setExt(rs.getString("ARE_D_EXT"));

		return remoto;
	}

	public DimensionRatio dimensionRatioResultSet(ResultSet rs) throws SQLException {
		DimensionRatio dimensionRatio = new DimensionRatio();
		dimensionRatio.setAlto(rs.getString("ARC_N_ALTO"));
		dimensionRatio.setAncho(rs.getString("ARC_N_ANCHO"));
		dimensionRatio.setRadio(rs.getString("ARC_N_RADIO"));

		return dimensionRatio;
	}

}
