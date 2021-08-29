package edu.poly.colorshop.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.colorshop.entity.Product;
import edu.poly.colorshop.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "{id}")
    public Product getOne(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }

    @GetMapping()
    public List<Product> getAll() {
        return productService.findAll();
    }

    @PostMapping()
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping(value = "{id}")
    public Product update(@PathVariable("id") Integer id, @RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
    }

    @PutMapping(value = "{quantity}/{id}")
    public Product updateQuantity(@PathVariable("quantity") Integer quantity, @PathVariable("id") Integer id) {
        return productService.updateQuantity(quantity, id);
    }
}
