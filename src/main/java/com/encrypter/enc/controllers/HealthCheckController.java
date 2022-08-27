package com.encrypter.enc.controllers;

import com.encrypter.enc.constants.SuccessMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    /**
     * Method for health check of system.
     *
     * @return String
     */
    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok().body(SuccessMessages.HEALTH_CHECK);
    }

}
