package com.example.vipa.repository;

import com.example.vipa.model.Category;
import com.example.vipa.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
