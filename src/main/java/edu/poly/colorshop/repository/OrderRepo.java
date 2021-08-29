package edu.poly.colorshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.poly.colorshop.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.account.username=?1")
    List<Order> findByUsername(String username);

    @Query(value = "select DISTINCT MONTH(CreateDate) from Orders order by MONTH(CreateDate) asc", nativeQuery = true)
    List<Object> selectMonth();

    @Query(value = "select DISTINCT YEAR(CreateDate) from Orders order by YEAR(CreateDate) desc", nativeQuery = true)
    List<Object> selectYear();

    @Query("SELECT Max(o.id) as LastID FROM Order o")
    Long getMaxId();
}
