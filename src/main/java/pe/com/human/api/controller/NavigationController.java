package pe.com.human.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.human.api.model.apirequest.MenusItemsRequest;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apirequest.TabsItemsRequest;
import pe.com.human.api.model.apirequest.TabsRequest;
import pe.com.human.api.model.apiresponse.*;
import pe.com.human.api.service.NavigationService;

import java.util.List;

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
    public ResponseEntity<TabsResponse> navigationMenusTabs(@RequestBody TabsRequest request) {
        List<Tab> tabLst = navigationService.getNavigationMenusTabs(request);
        TabsResponse response = new TabsResponse(new TabsResponse.Data(tabLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "menus/tabs/items",  method = RequestMethod.POST)
    public ResponseEntity<ItemsResponse> navigationMenusTabsItems(@RequestBody TabsItemsRequest request) {
        List<Item> itemLst = navigationService.getNavigationMenusTabsItems(request);
        ItemsResponse response = new ItemsResponse(new ItemsResponse.Data(itemLst));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "menus/items",  method = RequestMethod.POST)
    public ResponseEntity<ItemsResponse> navigationMenusItems(@RequestBody MenusItemsRequest request) {
        List<Item> itemList = navigationService.getNavigationMenusItems(request);
        ItemsResponse response = new ItemsResponse(new ItemsResponse.Data(itemList));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
