package pe.com.human.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.human.api.dao.NavigationDAO;
import pe.com.human.api.model.apirequest.MenusItemsRequest;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsItemsRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Item;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.Tab;

import java.util.List;

/**
 * @author Armando Angulo
 */
@Service
public class NavigationService {
    @Autowired
    NavigationDAO navigationDAO;

    public List<Menu> getNavigationMenus(MenusRequest request) {
        return navigationDAO.getNavigationMenus(request);
    }

    public List<Tab> getNavigationMenusTabs(TabsRequest request) {
        return navigationDAO.getNavigationMenusTabs(request);
    }

    public List<Item> getNavigationMenusTabsItems(TabsItemsRequest request) {
        return navigationDAO.getNavigationMenusTabsItems(request);
    }

    public List<Item> getNavigationMenusItems(MenusItemsRequest request) {
        return navigationDAO.getNavigationMenusItems(request);
    }
}


