package com.mjcarvajalq.sales_metrics_api.repositories;

import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing OutreachAction entities.
 */
public interface OutreachActionRepository extends JpaRepository<OutreachAction, Long> {

}
