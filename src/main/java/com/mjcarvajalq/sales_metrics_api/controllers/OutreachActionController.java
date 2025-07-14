package com.mjcarvajalq.sales_metrics_api.controllers;

import com.mjcarvajalq.sales_metrics_api.dto.CreateOutreachActionRequest;
import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.services.OutreachActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/actions")
@RequiredArgsConstructor
@Slf4j
public class OutreachActionController {

    private final OutreachActionService outreachActionService;

    @PostMapping
    public ResponseEntity<OutreachAction> createAction(@Valid @RequestBody CreateOutreachActionRequest request){
        OutreachAction saved = outreachActionService.saveAction(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<OutreachActionDTO> listActions(@RequestParam(required = false) Long userId){
        if (userId != null) {
            return outreachActionService.getActionsByUserId(userId);
        } else {
            return outreachActionService.getAllActions();
        }
    }

}

