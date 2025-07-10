package com.mjcarvajalq.sales_metrics_api.services;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;

import java.util.List;

public interface OutreachActionService {
    OutreachAction saveAction (CreateOutreachActionRequest request);
    List<OutreachAction> getAllActions();  // Optional: for testing
}
