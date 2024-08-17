package pl.srb.srb.model;

import jakarta.persistence.*;


@Entity
@Table(name = "lanes")
public class Lane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}

