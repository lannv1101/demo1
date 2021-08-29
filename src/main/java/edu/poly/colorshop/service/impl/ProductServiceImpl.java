package edu.poly.colorshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import edu.poly.colorshop.entity.Product;
import edu.poly.colorshop.repository.ProductRepo;
import edu.poly.colorshop.service.ProductService;

@Service
@Repository
public class ProductServiceImpl implements ProductService {
    @Autowired(required = true)
    ProductRepo productRepo;

   
    @Override
	public Product updateQuantity(Integer quantity, Integer id) {
		return productRepo.updateQuantity(quantity, id);
	}

	@Override
	public Page<Product> findByCategoryId(String cid, Pageable pageable) {
		return productRepo.findByCategoryId(cid, pageable);
	}

	@Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return productRepo.findAll(sort);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepo.findByNameContaining(name);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepo.findByNameContaining(name, pageable);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productRepo.findById(id).get();
    }

   

    @Override
    public <S extends Product> S save(S entity) {
        return productRepo.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findTop10ByOrderByCreateDateDesc() {
        return productRepo.findTop10ByOrderByCreateDateDesc();
    }

}
