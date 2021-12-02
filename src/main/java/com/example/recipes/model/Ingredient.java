package com.example.recipes.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipes = new HashSet<>();

    @ManyToMany(mappedBy = "ingredientsToBuy")
    private Set<User> usersWantedToBuy = new HashSet<>();

    private String text;

    public Ingredient(String text) {
        this.text = text;
    }

    public Ingredient() {
    }

    public void addToRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Set<User> getUsersWantedToBuy() {
        return usersWantedToBuy;
    }

    public void setUsersWantedToBuy(Set<User> usersWantedToBuy) {
        this.usersWantedToBuy = usersWantedToBuy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
