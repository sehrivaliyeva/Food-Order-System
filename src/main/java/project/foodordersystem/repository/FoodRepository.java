package project.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.foodordersystem.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
