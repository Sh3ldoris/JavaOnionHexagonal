package com.waterconnect.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterconnect.application.usecase.CreateConnectorUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * TODO: Implement connector endpoints from openapi.yaml
 */
@Tag(name = "Connectors", description = "Connector management (endpoints pending).")
@RestController
@RequestMapping("/api/v1/connectors")
public class ConnectorController {

    private final CreateConnectorUseCase createConnectorUseCase;

    public ConnectorController(CreateConnectorUseCase createConnectorUseCase) {
        this.createConnectorUseCase = createConnectorUseCase;
    }

    // TODO: Implement endpoints
}
