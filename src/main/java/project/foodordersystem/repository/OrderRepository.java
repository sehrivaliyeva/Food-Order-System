package project.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.foodordersystem.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
