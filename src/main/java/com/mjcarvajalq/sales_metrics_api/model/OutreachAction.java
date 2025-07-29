package com.mjcarvajalq.sales_metrics_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime dateTime;

    /** Additional notes, such as "client asked to follow up next week" */
    private String notes;

    /** User who performed this action */
    @ManyToOne
    private User user;

}
