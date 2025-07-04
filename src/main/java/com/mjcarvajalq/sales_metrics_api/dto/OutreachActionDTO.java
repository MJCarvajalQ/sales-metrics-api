package com.mjcarvajalq.sales_metrics_api.dto;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OutreachActionDTO {

    private Long userId; // To associate this action with a user
    private ActionType type;
    private LocalDate date;
    private String notes;

}
