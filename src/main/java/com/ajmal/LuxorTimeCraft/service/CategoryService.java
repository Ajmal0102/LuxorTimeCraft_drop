package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.Category;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    void addCategory (Category category);
    List<Category> getAllCategory();
    void removeCategoryById(int id);
    void updateCategoryById(int id, Model model);
    Optional<Category> getCategoryById(int id);



    boolean existsCategory(String name);
}
