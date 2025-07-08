package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;

import java.util.List;

public interface OutreachActionService {
    OutreachAction saveAction (OutreachActionDTO dto);

    List<OutreachActionDTO> getAllActions();
    List<OutreachActionDTO> getActionsByUserId(Long userId);

}
