package com.example.recipes.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "meal_recipes",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "meal_id")}
    )
    private List<Recipe> dishes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Meal(Long id, String name, List<Recipe> dishes, User author) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
        this.author = author;
    }

    public Meal(Long id, String name, List<Recipe> dishes) {
        this.id = id;
        this.name = name;
        this.dishes = dishes;
    }

    public Meal(String name, User user) {
        this.name = name;
        this.author = user;
    }

    public Meal(String name, List<Recipe> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public Meal(String name, List<Recipe> dishes, User author) {
        this.name = name;
        this.dishes = dishes;
        this.author = author;
    }

    public Meal() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Recipe> getDishes() {
        return dishes;
    }

    public void setDishes(List<Recipe> dishes) {
        this.dishes = dishes;
    }



    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishes=" + dishes +
                ", author=" + author +
                '}';
    }
}
