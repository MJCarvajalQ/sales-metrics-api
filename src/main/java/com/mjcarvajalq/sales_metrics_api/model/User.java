package com.mjcarvajalq.sales_metrics_api.model;


import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a user of the system who performs outreach actions.
 * For example: Maria sending prospecting messages to clients.
 */
@Data
@Entity
public class User {

    /** Unique identifier for each user (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Full name of the user (e.g., "Maria Carvajal") */
    private String name;

    /** Email address of the user (must be unique) */
    @Column(unique = true)
    private String email;

}
