package edu.poly.colorshop.service;

import java.util.List;

import org.springframework.data.domain.Example;

import com.fasterxml.jackson.databind.JsonNode;

import edu.poly.colorshop.entity.Order;

public interface OrderService {

	Order create(JsonNode orderData);

	<S extends Order> S save(S entity);

	Order getOne(Long id);

	void deleteById(Long id);

	long count();

	<S extends Order> boolean exists(Example<S> example);

	boolean existsById(Long id);

	Order findById(Long id);

	List<Order> findByUsername(String username);

	public List<Order> findAll();

	List<Object> selectYear();

	List<Object> selectMonth();

	Long getMaxId();

}
