package project.foodordersystem.repository;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.foodordersystem.model.Menu;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsByMenuName(@NotEmpty(message = " this section must not be a null") @NotNull String menuName);

    Optional<Menu> findByMenuName(String menuName);
}
