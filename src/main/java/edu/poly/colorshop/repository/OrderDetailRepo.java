package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.OrderDetail;
import edu.poly.colorshop.model.Report;
import edu.poly.colorshop.model.ReportByMonth;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
        List<OrderDetail> findTop10ByOrderByQuantityDesc();

        @Query("SELECT o FROM OrderDetail o  WHERE o.order.id=?1")
        List<OrderDetail> getAmount(Long orderId);

        @Query("SELECT sum(d.quantity*d.price), d.product.category.name " + " FROM OrderDetail d"
                        + " WHERE MONTH(d.order.createDate)=?2 and YEAR(d.order.createDate)=?1"
                        + " GROUP BY d.product.category.name")
        List getByCateMonthAndYear(Integer year, Integer month);

        @Query("SELECT d" + " FROM OrderDetail d"
                        + " WHERE MONTH(d.order.createDate)=?2 and YEAR(d.order.createDate)=?1")
        List<OrderDetail> getValueByMonthAndYear(Integer year, Integer month);

        @Query("SELECT SUM(d.price*d.quantity),YEAR(d.order.createDate) FROM OrderDetail d group by Year(d.order.createDate) order by Year(d.order.createDate) desc")
        List<Object> getSumYear();

        @Query(value = "SELECT new Report(d.product.category, SUM(d.price*d.quantity))" + " FROM OrderDetail d"
                        + " WHERE YEAR(d.order.createDate)=YEAR(getDate())" + " GROUP BY d.product.category"
                        + " ORDER BY sum(d.price*d.quantity) DESC")
        List<Report> getInventoryByCategory();

        @Query(value = "SELECT new ReportByMonth(SUM(d.price*d.quantity), MONTH(d.order.createDate))"
                        + " FROM OrderDetail d" + " WHERE YEAR(d.order.createDate)=YEAR(getDate())"
                        + " GROUP BY MONTH(d.order.createDate)" + " ORDER BY MONTH(d.order.createDate) ASC")
        List<ReportByMonth> getInventoryByMonth();
}
