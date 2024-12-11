package com.cdac.inventory_service.controller;

import com.cdac.inventory_service.dto.InventoryResponse;
import com.cdac.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory")
public class InventoryController {
    private InventoryService inventoryService;

    @GetMapping("/test")
    public  String method(){
        return "application is UP";
    }

    //Path variable:http://localhos:8085/api/inventory/iPhone-13,iphone13-red
    //requestParam: http://localhos:8085/api/inventory?skuCode=iPhone-13&sku-code=iphone13-red
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public  List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
          return  inventoryService.isInStock(skuCode);
    }
}

