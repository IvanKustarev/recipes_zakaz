package com.example.recipes.service.impl;

import com.example.recipes.model.Meal;
import com.example.recipes.repository.MealRepository;
import com.example.recipes.service.MealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;

    public MealServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public void createMeal(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public Meal findById(Long id) {
        return mealRepository.findById(id).orElseThrow(() ->
                new RuntimeException("No such meal"));
    }

    @Override
    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    @Override
    public void save(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public void deleteById(Long mealId) {
        mealRepository.deleteById(mealId);
    }
}
