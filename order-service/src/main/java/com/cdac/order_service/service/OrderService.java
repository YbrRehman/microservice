package com.cdac.order_service.service;

import com.cdac.order_service.dto.InventoryResponse;
import com.cdac.order_service.dto.OrderLineItemsDto;
import com.cdac.order_service.dto.OrderRequest;
import com.cdac.order_service.model.Order;
import com.cdac.order_service.model.OrderLineItems;
import com.cdac.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes=order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        //call inventory service if product is in stock
        InventoryResponse[] inventoryResponses=webClient.get()
                                    .uri("http://localhost:8085/api/inventory",
                                            uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                                                 .retrieve()
                                                    .bodyToMono(InventoryResponse[].class)
                                                        .block();
        assert inventoryResponses != null;
        Boolean allProductsInStock= Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);
        if(Boolean.TRUE.equals(allProductsInStock)){
            orderRepository.save(order);
        }else {
            throw  new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
