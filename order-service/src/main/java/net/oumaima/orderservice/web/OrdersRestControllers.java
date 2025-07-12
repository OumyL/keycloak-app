package net.oumaima.orderservice.web;

import net.oumaima.orderservice.entities.Order;
import net.oumaima.orderservice.repositories.OrderRepository;
import net.oumaima.orderservice.restClients.InventoryRestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersRestControllers {
    private OrderRepository orderRepository;
    private InventoryRestClient inventoryRestClient;

    public OrdersRestControllers(OrderRepository orderRepository, InventoryRestClient inventoryRestClient) {
        this.orderRepository = orderRepository;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping("/orders")
   public List<Order> findAll() {
        List<Order> allOrders = orderRepository.findAll();
        allOrders.forEach(o->{
            o.getProductItems().forEach(pi->{
                pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));
            });
        });
        return allOrders;

    }
    @GetMapping("/orders/{id}")
    public Order findOrderById(@PathVariable String id) {
        Order order = orderRepository.findById(id).get();
        order.getProductItems().forEach(pi->{
            pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));

        });
        return order;
    }
}
