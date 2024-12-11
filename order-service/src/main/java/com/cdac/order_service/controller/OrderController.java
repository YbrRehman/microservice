package com.cdac.order_service.controller;

import com.cdac.order_service.dto.OrderRequest;
import com.cdac.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")

public class OrderController {
    @Autowired
    private  OrderService orderService;
    @GetMapping("/test")
    public  String method(){
        return "application is UP";
    }

    @PostMapping("/placeOrder")
    public  String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return  "order successfully completed";
    }
}
