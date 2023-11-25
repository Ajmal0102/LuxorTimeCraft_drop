package com.ajmal.LuxorTimeCraft.repository;

import com.ajmal.LuxorTimeCraft.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Integer> {

    boolean existsByName(String name);
}
