package cloud_kitchen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud_kitchen.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {

}