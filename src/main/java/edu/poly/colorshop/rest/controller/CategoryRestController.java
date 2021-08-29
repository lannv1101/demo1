package edu.poly.colorshop.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.colorshop.entity.Category;
import edu.poly.colorshop.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @PostMapping(value = "")
    public Category create(@RequestBody Category entity) {
        return categoryService.save(entity);
    }

    @PutMapping(value = "{id}")
    public Category update(@PathVariable("id") String id, @RequestBody Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") String id) {
        categoryService.deleteById(id);
    }
}
