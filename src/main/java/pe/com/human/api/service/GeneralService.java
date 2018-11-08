package pe.com.human.api.service;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.human.api.dao.GeneralDAO;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apiresponse.Icon;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.MenusResponse;

import java.util.ArrayList;
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
}


