package net.oumaima.orderservice.repositories;


import net.oumaima.orderservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductItemRepository extends JpaRepository<ProductItem, String> {
}
