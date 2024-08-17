package pl.srb.srb.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Konstruktor bezparametrowy
    public Role() {
    }

    // Konstruktor z parametrami
    public Role(String name) {
        this.name = name;
    }
}