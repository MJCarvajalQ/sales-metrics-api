package com.mjcarvajalq.sales_metrics_api.controllers;

import com.mjcarvajalq.sales_metrics_api.dto.OutreachActionDTO;
import com.mjcarvajalq.sales_metrics_api.model.OutreachAction;
import com.mjcarvajalq.sales_metrics_api.services.OutreachActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/actions")
public class OutreachActionController {

    private final OutreachActionService outreachActionService;

    public OutreachActionController(OutreachActionService outreachActionService){
        this.outreachActionService = outreachActionService;
    }

    @PostMapping
    public ResponseEntity<OutreachAction> createAction(@RequestBody OutreachActionDTO dto){
        OutreachAction saved = outreachActionService.saveAction(dto);
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

