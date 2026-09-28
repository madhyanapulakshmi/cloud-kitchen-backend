package cloud_kitchen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud_kitchen.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}