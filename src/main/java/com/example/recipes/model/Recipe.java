package com.example.recipes.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Recipe(Long id, String title, String description, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Recipe(Long id, String title, String description, User author, Meal meal) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.meal = meal;
    }

    public Recipe(String title, String description, User author, Meal meal) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.meal = meal;
    }

    public Recipe() {}

    public Recipe(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "Unknown author ";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                '}';
    }
}
