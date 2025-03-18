package project.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.foodordersystem.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
