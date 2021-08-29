package edu.poly.colorshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.colorshop.entity.OrderDetail;
import edu.poly.colorshop.model.Report;
import edu.poly.colorshop.model.ReportByMonth;
import edu.poly.colorshop.repository.OrderDetailRepo;
import edu.poly.colorshop.service.OrderDetailService;

@Service
public class OrderDetailImpl implements OrderDetailService {
	@Autowired
	OrderDetailRepo orderDetailRepo;

	// @Override
	// public List<Report> getInventory(Integer year, Integer month) {
	// return orderDetailRepo.getInventory(year, month);
	// }

	@Override
	public List<OrderDetail> getValueByMonthAndYear(Integer year, Integer month) {
		return orderDetailRepo.getValueByMonthAndYear(year, month);
	}

	@Override
	public List<ReportByMonth> getInventoryByMonth() {
		return orderDetailRepo.getInventoryByMonth();
	}

	@Override
	public List<Report> getInventoryByCategory() {
		return orderDetailRepo.getInventoryByCategory();
	}

	// @Override
	// public List<Object> getSumCateByYear(Integer year) {
	// return orderDetailRepo.getSumCateByYear(year);
	// }

	@Override
	public List<Object> getSumYear() {
		return orderDetailRepo.getSumYear();
	}

	@Override
	public List getByCateMonthAndYear(Integer year, Integer month) {
		return orderDetailRepo.getByCateMonthAndYear(year, month);
	}

	@Override
	public List<OrderDetail> getAmount(Long orderId) {
		return orderDetailRepo.getAmount(orderId);
	}

	@Override
	public List<OrderDetail> findTop10ByOrderByQuantityDesc() {
		return orderDetailRepo.findTop10ByOrderByQuantityDesc();
	}

	@Override
	public <S extends OrderDetail> S save(S entity) {
		return orderDetailRepo.save(entity);
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepo.findAll();
	}

	@Override
	public Optional<OrderDetail> findById(Long id) {
		return orderDetailRepo.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		orderDetailRepo.deleteById(id);
	}

	@Override
	public <S extends OrderDetail> List<S> saveAll(Iterable<S> entities) {
		return orderDetailRepo.saveAll(entities);
	}

}
