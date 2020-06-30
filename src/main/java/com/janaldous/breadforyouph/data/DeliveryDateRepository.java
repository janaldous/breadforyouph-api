package com.janaldous.breadforyouph.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDateRepository extends JpaRepository<DeliveryDate, Long> {

	@Query("SELECT d from DeliveryDate d WHERE d.date >= CURRENT_DATE ORDER BY d.date")
	Page<DeliveryDate> findDeliveryDates(Pageable pageable);

}
