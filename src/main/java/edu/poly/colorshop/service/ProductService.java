package edu.poly.colorshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.colorshop.entity.Product;

public interface ProductService {

	List<Product> findAll();

	Product findById(Integer id);

	// List<Product> findByCategoryId(String cid);

	void deleteById(Integer id);

	<S extends Product> S save(S entity);

	List<Product> findTop10ByOrderByCreateDateDesc();

	Page<Product> findByNameContaining(String name, Pageable pageable);

	List<Product> findByNameContaining(String name);

	List<Product> findAll(Sort sort);

	Page<Product> findAll(Pageable pageable);
	//
	// Page<Product> findByCategoryId(Optional<String> cid, Pageable pageable);

	Page<Product> findByCategoryId(String cid, Pageable pageable);

	Product updateQuantity(Integer quantity, Integer id);

}
