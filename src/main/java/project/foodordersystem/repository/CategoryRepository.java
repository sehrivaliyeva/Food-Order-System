package project.foodordersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.foodordersystem.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
