package com.mjcarvajalq.sales_metrics_api.repositories;

import com.mjcarvajalq.sales_metrics_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository for performing CRUD operations on the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
