package com.example.project02.repository;

import com.example.project02.entity.Category;
import com.example.project02.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // categoryName과 일치하는 categoryId값
    @Query("SELECT categoryId FROM Category WHERE categoryName = :categoryName")
    Long findCategoryId(@Param("categoryName") String categoryName);
}
