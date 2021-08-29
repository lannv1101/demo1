package edu.poly.colorshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.poly.colorshop.entity.Order;
import edu.poly.colorshop.entity.OrderDetail;
import edu.poly.colorshop.repository.OrderDetailRepo;
import edu.poly.colorshop.repository.OrderRepo;
import edu.poly.colorshop.service.OrderService;

@Service
@Repository
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Autowired
	OrderDetailRepo orderDetailRepo;

	@Override
	public Long getMaxId() {
		return orderRepo.getMaxId();
	}

	@Override
	public List<Object> selectMonth() {
		return orderRepo.selectMonth();
	}

	@Override
	public List<Object> selectYear() {
		return orderRepo.selectYear();
	}

	@Override
	public List<Order> findAll() {
		return orderRepo.findAll();
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderRepo.findByUsername(username);
	}

	@Override
	public Order findById(Long id) {
		return orderRepo.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return orderRepo.existsById(id);
	}

	@Override
	public <S extends Order> boolean exists(Example<S> example) {
		return orderRepo.exists(example);
	}

	@Override
	public long count() {
		return orderRepo.count();
	}

	@Override
	public void deleteById(Long id) {
		orderRepo.deleteById(id);
	}

	@Override
	public Order getOne(Long id) {
		return orderRepo.getOne(id);
	}

	@Override
	public <S extends Order> S save(S entity) {
		return orderRepo.save(entity);
	}

	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();// chuyen doi json
		Order order = mapper.convertValue(orderData, Order.class);

		orderRepo.save(order);

		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		// lấy dữ liệu từ orderdetail
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type)

				.stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());
		orderDetailRepo.saveAll(details);// luu nhieu order cùng 1 lúc
		return order;
	}
}
