package edu.poly.colorshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.colorshop.entity.Category;
import edu.poly.colorshop.repository.CategoryRepo;
import edu.poly.colorshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public List<Category> findTop() {
		return categoryRepo.findTop();
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public <S extends Category> S save(S entity) {
		return categoryRepo.save(entity);
	}

	@Override
	public void deleteById(String id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public void delete(Category entity) {
		categoryRepo.delete(entity);
	}

}
