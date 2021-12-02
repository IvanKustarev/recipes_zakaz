package com.example.recipes.model;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private Meal meal;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<User> usersFavoriteRecipe = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "RECIPE_INGREDIENTS",
            joinColumns = @JoinColumn(name = "RECIPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "INGREDIENT_ID")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

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

    public Recipe() {
    }

    public Recipe(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Recipe(String title, String description, User author, String ingredient) {
        this.ingredients.add(new Ingredient(ingredient));
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
                ", ingredients=" + ingredients +
                '}';
    }

    public Ingredient[] getIngredients() {
        return ingredients.toArray(new Ingredient[0]);
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<User> getUsersFavoriteRecipe() {
        return usersFavoriteRecipe;
    }

    public void setUsersFavoriteRecipe(Set<User> usersFavoriteRecipe) {
        this.usersFavoriteRecipe = usersFavoriteRecipe;
    }
}
