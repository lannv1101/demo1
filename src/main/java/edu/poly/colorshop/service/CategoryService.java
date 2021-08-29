package edu.poly.colorshop.service;

import java.util.List;

import edu.poly.colorshop.entity.Category;

public interface CategoryService {

	List<Category> findAll();

	void delete(Category entity);

	void deleteById(String id);

	<S extends Category> S save(S entity);

	List<Category> findTop();

}
