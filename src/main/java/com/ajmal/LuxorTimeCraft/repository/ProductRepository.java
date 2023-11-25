package com.ajmal.LuxorTimeCraft.repository;

import com.ajmal.LuxorTimeCraft.model.Category;
import com.ajmal.LuxorTimeCraft.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);
}
