package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;

import java.util.List;

public interface OutreachActionService {
    CreateOutreachActionResponse saveAction (CreateOutreachActionRequest request);
    List<OutreachActionDTO> getAllActions();
    List<OutreachActionDTO> getActionsByUserId(Long userId);
    OutreachActionDetailResponse getActionById(Long id);

}
