package com.ruhuna.campuscaresystem.model;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(length = 1000)
    private String description;

    private String location;

    private LocalDate date;

    private String status;

    private String imagePath;

    // Default constructor (required by JPA)
    public Issue() {}

    // Parameterized constructor
    public Issue(String title, String category, String description,
                 String location, LocalDate date, String status) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    // Getters and setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}