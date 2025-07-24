package com.mjcarvajalq.sales_metrics_api.mappers;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import org.springframework.stereotype.Component;

/**
 * Mapper component responsible for converting between OutreachAction entities and DTOs.
 * This component isolates the mapping logic from the service layer, following the 
 * Single Responsibility Principle and making the code more maintainable.
 * 
 * Future enhancement: This will be replaced with MapStruct in Issue #6 for 
 * automatic mapping generation and better performance.
 */
@Component
public class OutreachActionMapper {

    /**
     * Converts an OutreachAction entity to its corresponding DTO.
     * 
     * @param action the OutreachAction entity to convert
     * @return OutreachActionDTO containing the mapped data
     * @throws IllegalArgumentException if action is null
     */
    public OutreachActionDTO toDTO(OutreachAction action) {
        if (action == null) {
            throw new IllegalArgumentException("OutreachAction cannot be null");
        }

        return OutreachActionDTO.builder()
                .userId(action.getUser().getId())
                .type(action.getType())
                .date(action.getDate())
                .notes(action.getNotes())
                .build();
    }
}
