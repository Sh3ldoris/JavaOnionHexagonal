package com.waterconnect.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.application.usecase.CreateConnectorUseCase;

/**
 * TODO: Implement connector endpoints from openapi.yaml
 */
@RestController
@RequestMapping("/api/v1/connectors")
public class ConnectorController {

    private final CreateConnectorUseCase createConnectorUseCase;

    public ConnectorController(CreateConnectorUseCase createConnectorUseCase) {
        this.createConnectorUseCase = createConnectorUseCase;
    }

    // TODO: Implement endpoints
}
