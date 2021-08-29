package edu.poly.colorshop.service;

import java.util.List;
import java.util.Optional;

import edu.poly.colorshop.entity.OrderDetail;
import edu.poly.colorshop.model.Report;
import edu.poly.colorshop.model.ReportByMonth;

public interface OrderDetailService {

	List<OrderDetail> findTop10ByOrderByQuantityDesc();

	void deleteById(Long id);

	Optional<OrderDetail> findById(Long id);

	List<OrderDetail> findAll();

	<S extends OrderDetail> S save(S entity);

	List<OrderDetail> getAmount(Long orderId);

	// Object getByMonthAndYear(Integer year, Integer month);

	List<OrderDetail> getValueByMonthAndYear(Integer year, Integer month);

	List getByCateMonthAndYear(Integer year, Integer month);

	List<Object> getSumYear();

//	List<Object> getSumCateByYear(Integer year);

	List<Report> getInventoryByCategory();

	List<ReportByMonth> getInventoryByMonth();

	<S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

	// List<Report> getInventory(Integer year, Integer month);
}
