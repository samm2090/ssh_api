package pe.com.human.servicios.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.com.human.servicios.dao.CompaniaDAO;
import pe.com.human.servicios.model.Compania;
import pe.com.human.servicios.model.Sucursal;

/**
 * 
 * @author SERGIO MUROY
 *
 */
@Repository
public class CompaniaDAOImpl implements CompaniaDAO {

	@Autowired
	DataSource datasource;

	@Override
	public List<Compania> listarCompaniasXDocumento(String documento) {
		List<Compania> companias = null;
		//*Optimizar queru
		String query = "SELECT DISTINCT C.* FROM "
				+ "EBCOMPANIA C INNER JOIN HR_EMPLEADO E ON E.EMPCODCIA = C.EBCODCIA AND E.EMPCODSUC = C.EBCODSUC RIGHT JOIN EBUSUEMP UE "
				+ "ON UE.EMPCODTRA = E.EMPCODTRA AND UE.EMPCODCIA = E.EMPCODCIA AND UE.EMPCODSUC = E.EMPCODSUC "
				+ "WHERE E.EMPNRODOCID = ?";

		Connection connection = null;
		try {
			connection = datasource.getConnection();

			PreparedStatement listarCompanias = connection.prepareStatement(query);
			listarCompanias.setString(1, documento);

			ResultSet rs = listarCompanias.executeQuery();
			
			Compania compania;
			Sucursal sucursal;
			while (rs.next()) {
				compania = new Compania();
				compania.setId(rs.getString("EBCODCIA"));
				compania.setNombre(rs.getString("EBDESCIA"));
				
				sucursal = new Sucursal();
				sucursal.setId(rs.getString("EBCODSUC"));
				sucursal.setNombre(rs.getString("EBDESSUCC"));
				
				compania.setSucursal(sucursal);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return companias;
	}

}
