package com.mjcarvajalq.sales_metrics_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
public class OutreachAction {

    /** Unique identifier for each action (auto-generated) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Type of action: EMAIL, CONNECTION, etc. (stored as string in DB) */
    @Enumerated(EnumType.STRING)
    private ActionType type;

    /** Date when the action was performed */
    private LocalDate date;

    /** Additional notes, such as "client asked to follow up next week" */
    private String notes;

    /** User who performed this action */
    @ManyToOne
    private User user;

}
