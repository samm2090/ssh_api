package pe.com.human.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.human.api.model.apirequest.MenusRequest;
import pe.com.human.api.model.apiresponse.Menu;
import pe.com.human.api.model.apiresponse.MenusResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/general/")
public class GeneralController {

    @RequestMapping(value = "menus",  method = RequestMethod.POST)
    public ResponseEntity<MenusResponse> menus(@RequestBody MenusRequest request) {
        MenusResponse menus = new MenusResponse();

        List<Menu> menuLst = new ArrayList<>();
        Menu menu = new Menu();
        
        return new ResponseEntity<MenusResponse>(menus, HttpStatus.OK);
    }

}
