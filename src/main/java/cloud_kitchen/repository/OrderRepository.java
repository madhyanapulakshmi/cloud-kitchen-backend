package cloud_kitchen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud_kitchen.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Get orders of a particular customer,
    // newest orders first
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);

    // Get all orders, newest orders first
    List<Order> findAllByOrderByOrderDateDesc();
}