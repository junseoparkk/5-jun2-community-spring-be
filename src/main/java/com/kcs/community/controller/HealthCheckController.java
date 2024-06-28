package com.kcs.community.controller;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Value("${server.env}")
    private String env;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${serverName}")
    private String serverName;

    @GetMapping("/healthcheck")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> response = new TreeMap<>();
        response.put("serverName", serverName);
        response.put("serverAddress", serverAddress);
        response.put("serverPort", serverPort);
        response.put("env", env);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv() {
        return ResponseEntity.ok(env);
    }
}
