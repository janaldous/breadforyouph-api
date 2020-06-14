package com.janaldous.breadforyouph.data;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long> {

	@Query("select a from OrderDetail a join OrderTracking b on a.tracking=b.id where b.status = :status")
	public List<OrderDetail> findAllByStatus(OrderStatus status, Sort by);
	
}
