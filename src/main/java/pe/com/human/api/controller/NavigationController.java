package pe.com.human.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.MenusResponse;
import pe.com.human.api.service.NavigationService;

/**
 * @author Armando Angulo
 */
@RestController
@RequestMapping("/v1/navigation/")
public class NavigationController {
    @Autowired
    NavigationService navigationService;

    @RequestMapping(value = "menus",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> navigationMenus(@RequestBody MenusRequest request) {
        List<Menu> menuLst = navigationService.getNavigationMenus(request);
        MenusResponse response = new MenusResponse(new MenusResponse.Data(menuLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "menus/tabs",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> navigationMenusTabs(@RequestBody TabsRequest request) {
        List<Menu> tabLst = navigationService.getNavigationMenusTabs(request);
        MenusResponse response = new MenusResponse(new MenusResponse.Data(tabLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "menus/tabs/items",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> navigationMenusTabsItems(@RequestBody TabsRequest request) {
        List<Menu> itemLst = navigationService.getNavigationMenusTabsItems(request);
        MenusResponse response = new MenusResponse(new MenusResponse.Data(itemLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "menus/items",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> navigationMenusItems(@RequestBody TabsRequest request) {
        List<Menu> itemList = navigationService.getNavigationMenusItems(request);
        MenusResponse response = new MenusResponse(new MenusResponse.Data(itemList));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
