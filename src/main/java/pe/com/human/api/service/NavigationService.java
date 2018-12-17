package pe.com.human.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.human.api.dao.BaseDatosDAO;
import pe.com.human.api.dao.EmpleadoDAO;
import pe.com.human.api.dao.MenuDAO;
import pe.com.human.api.exception.ExcepcionCompaniaMenu;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.util.ConfiguracionDataSource;

/**
 * @author Armando Angulo
 */
@Service
public class NavigationService {

	private static final int TIPO_MENU = 1;
	private static final int TIPO_TAB = 2;
	private static final int TIPO_ITEM = 3;

	@Autowired
	EmpleadoDAO empleadoDAO;

	@Autowired
	MenuDAO menuDAO;

	@Autowired
	BaseDatosDAO baseDatosDAO;

	public List<Menu> getNavigationMenus(MenusRequest request) {
		String codcia = request.getBase().getCompania().getId();
		int idRol = Integer.parseInt(request.getEmpleado().getRol());
		String rol = "%";

		if (idRol == 2) {
			rol = "2";
		}

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource();
		List<Menu> opciones = menuDAO.buscarMenu(codcia, TIPO_MENU, rol, configuracionDataSource);

		if (opciones == null) {
			throw new ExcepcionCompaniaMenu();
		}

		return opciones;
	}

	public List<Menu> getNavigationMenusTabs(TabsRequest request) {
		String codcia = request.getBase().getCompania().getId();
		int idRol = Integer.parseInt(request.getEmpleado().getRol());
		String rol = "%";
		int idPadre = Integer.parseInt(request.getMenu().getId());

		if (idRol == 2) {
			rol = "2";
		}

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource();

		List<Menu> opciones = menuDAO.buscarMenu(codcia, TIPO_MENU, rol, configuracionDataSource);

		if (opciones == null) {
			throw new ExcepcionCompaniaMenu();
		}

		return opciones;
	}

	public List<Menu> getNavigationMenusTabsItems(TabsRequest request) {
		String codcia = request.getBase().getCompania().getId();
		int idRol = Integer.parseInt(request.getEmpleado().getRol());
		String rol = "%";
		int idPadre = Integer.parseInt(request.getMenu().getId());

		if (idRol == 2) {
			rol = "2";
		}

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource();

		List<Menu> opciones = menuDAO.buscarMenu(codcia, TIPO_MENU, rol, configuracionDataSource);

		if (opciones == null) {
			throw new ExcepcionCompaniaMenu();
		}

		return opciones;
	}

	public List<Menu> getNavigationMenusItems(TabsRequest request) {
		String codcia = request.getBase().getCompania().getId();
		int idRol = Integer.parseInt(request.getEmpleado().getRol());
		String rol = "%";
		int idPadre = Integer.parseInt(request.getMenu().getId());

		if (idRol == 2) {
			rol = "2";
		}

		ConfiguracionDataSource configuracionDataSource = new ConfiguracionDataSource();

		List<Menu> opciones = menuDAO.buscarMenu(codcia, TIPO_MENU, rol, configuracionDataSource);

		if (opciones == null) {
			throw new ExcepcionCompaniaMenu();
		}

		return opciones;
	}
}
