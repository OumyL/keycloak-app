package net.oumaima.orderservice.repositories;

import net.oumaima.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, String> {
}
