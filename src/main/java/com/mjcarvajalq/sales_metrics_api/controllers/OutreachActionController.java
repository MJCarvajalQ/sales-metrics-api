package com.mjcarvajalq.sales_metrics_api.controllers;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionResponse;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDetailResponse;
import com.mjcarvajalq.sales_metrics_api.services.OutreachActionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Slf4j
public class OutreachActionController {

    private final OutreachActionService outreachActionService;

    @PostMapping
    public ResponseEntity<CreateOutreachActionResponse> createAction(@Valid @RequestBody CreateOutreachActionRequest request){
        log.info("Creating outreach action for user: {}", request.getUserId());
        CreateOutreachActionResponse response = outreachActionService.saveAction(request);
        log.info("Successfully created outreach action with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OutreachActionDTO>> getAllActions() {
        List<OutreachActionDTO> actions = outreachActionService.getAllActions();
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutreachActionDetailResponse> getActionById(@PathVariable Long id) {
        log.info("Fetching outreach action with ID: {}", id);
        OutreachActionDetailResponse response = outreachActionService.getActionById(id);
        return ResponseEntity.ok(response);
    }

}

