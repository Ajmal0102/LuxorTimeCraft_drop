package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.Category;
import com.ajmal.LuxorTimeCraft.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void removeCategoryById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateCategoryById(int id, Model model) {

    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public boolean existsCategory(String name) {
        return categoryRepository.existsByName(name);
    }




}
