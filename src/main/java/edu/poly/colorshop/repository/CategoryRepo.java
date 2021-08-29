package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String> {
    @Query(value = "select top(3)* from Categories", nativeQuery = true)
    List<Category> findTop();

}
