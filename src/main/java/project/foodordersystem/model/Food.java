package project.foodordersystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foodName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToMany(mappedBy = "foods")
    @JsonIgnore
    private List<Order> orders;

}
