package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.id=?1")
    Page<Product> findByCategoryId(String cid, Pageable pageable);

    List<Product> findTop10ByOrderByCreateDateDesc();

    List<Product> findByNameContaining(String name);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query("UPDATE Product p set p.quantity=?1 where p.id=?2")
    Product updateQuantity(Integer quantity, Integer id);

}
