package com.example.Board.entity;

import jakarta.persistence.*;

@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20)
    private String categoryName;
}
