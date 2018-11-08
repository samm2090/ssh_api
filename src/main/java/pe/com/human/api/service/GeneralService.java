package pe.com.human.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.human.api.dao.GeneralDAO;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.Tab;

import java.util.List;

/**
 * @author Armando Angulo
 */
@Service
public class GeneralService {
    @Autowired
    GeneralDAO generalDAO;

    public List<Menu> getNavigationMenus(MenusRequest request) {
        return generalDAO.getNavigationMenus(request);
    }

    public List<Tab> getNavigationMenustabs(TabsRequest request) {
        return generalDAO.getNavigationMenusTabs(request);
    }
}


