package com.janaldous.breadforyouph.data;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDateRepository extends JpaRepository<DeliveryDate, Long> {

	@Query("SELECT d from DeliveryDate d LEFT JOIN OrderDetail o ON d.id = o.deliveryDate WHERE d.date >= CURRENT_DATE GROUP BY d.date, d.orderLimit, d.id HAVING COUNT(d.date) < d.orderLimit ORDER BY d.date")
	Page<DeliveryDate> findDeliveryDates(Pageable pageable);

	Optional<DeliveryDate> findByDate(Date date);

}
