package pe.com.human.api.daoimpl;

import org.springframework.stereotype.Repository;
import pe.com.human.api.dao.NavigationDAO;
import pe.com.human.api.model.apirequest.MenusItemsRequest;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsItemsRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Icon;
import pe.com.human.api.model.apiresponse.Item;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Armando Angulo
 */
@Repository
public class NavigationDAOImpl implements NavigationDAO {
    @Override
    public List<Menu> getNavigationMenus(MenusRequest request) {
        List<Menu> menuLst = new ArrayList<>();
        Menu menu = new Menu(
                "drawerlayout_dashboard",
                1,
                new Icon("dashboard", null),
                "Dashboard",
                "dashboardFragment",
                "01");
        menuLst.add(menu);
        Menu menu2 = new Menu(
                "drawerlayout_employee_info",
                2,
                new Icon("person", null),
                "Mi Información",
                "informationFragment",
                "02");
        menuLst.add(menu2);
        return menuLst;
    }

    @Override
    public List<Tab> getNavigationMenusTabs(TabsRequest request) {
        List<Tab> tabLst =  new ArrayList<>();
        Tab tab = new Tab(
                "",
                1,
                new Icon("", null),
                "");
        tabLst.add(tab);
        return tabLst;
    }

    @Override
    public List<Item> getNavigationMenusTabsItems(TabsItemsRequest request) {
        List<Item> itemLst = new ArrayList<>();
        Item item = new Item(
                "X",
                1,
                new Icon("A", null),
                "");
        itemLst.add(item);
        return itemLst;
    }

    @Override
    public List<Item> getNavigationMenusItems(MenusItemsRequest request) {
        List<Item> itemLst = new ArrayList<>();
        Item item = new Item(
                "Y",
                1,
                new Icon("A", null),
                "");
        itemLst.add(item);
        return itemLst;
    }
}
