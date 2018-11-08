package pe.com.human.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.MenusResponse;
import pe.com.human.api.model.apiresponse.Tab;
import pe.com.human.api.model.apiresponse.TabsResponse;
import pe.com.human.api.service.GeneralService;

import java.util.List;

/**
 * @author Armando Angulo
 */
@RestController
@RequestMapping("/v1/general/")
public class GeneralController {
    @Autowired
    GeneralService generalService;

    @RequestMapping(value = "navigation/menus",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> navigationMenus(@RequestBody MenusRequest request) {
        List<Menu> menuLst = generalService.getNavigationMenus(request);
        MenusResponse response = new MenusResponse(new MenusResponse.Data(menuLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "navigation/menus/tabs",  method = RequestMethod.POST)
    public ResponseEntity<TabsResponse> navigationMenusTabs(@RequestBody TabsRequest request) {
        List<Tab> tabLst = generalService.getNavigationMenustabs(request);
        TabsResponse response = new TabsResponse(new TabsResponse.Data(tabLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
