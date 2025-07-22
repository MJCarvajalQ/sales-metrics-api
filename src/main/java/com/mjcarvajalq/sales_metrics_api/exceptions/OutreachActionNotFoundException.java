package com.mjcarvajalq.sales_metrics_api.exceptions;

public class OutreachActionNotFoundException extends RuntimeException {
    public OutreachActionNotFoundException(Long id) {
        super("Outreach action with ID " + id + " not found");
    }

}
