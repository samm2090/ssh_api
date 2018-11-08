package pe.com.human.api.dao;

import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.Tab;

import java.util.List;

/**
 * @author Armando Angulo
 */
public interface GeneralDAO {
    List<Menu> getNavigationMenus(MenusRequest request);

    List<Tab> getNavigationMenusTabs(TabsRequest request);
}
