package com.ajmal.LuxorTimeCraft.service;

import com.ajmal.LuxorTimeCraft.model.Category;
import com.ajmal.LuxorTimeCraft.model.Product;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void addProduct (Product product);
    List<Product> getAllProduct();
    void removeProductById(long id);
    void updateProductById(long id, Model model);
    Optional<Product> getProductById(long id);
    List<Product> getAllProductsByCategoryId(int id);


}
