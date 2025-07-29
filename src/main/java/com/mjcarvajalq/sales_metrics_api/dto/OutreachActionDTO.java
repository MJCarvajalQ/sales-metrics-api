package com.mjcarvajalq.sales_metrics_api.dto;

import com.mjcarvajalq.sales_metrics_api.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutreachActionDTO {

    private Long userId; // To associate this action with a user
    private ActionType type;
    private LocalDateTime dateTime;
    private String notes;

}
