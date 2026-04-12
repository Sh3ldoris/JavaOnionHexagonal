package usecase;

import com.waterconnect.domain.port.outbound.ConnectorRepository;

/**
 * Use case: Create a connector between pipes.
 *
 * TODO: Implement
 * - Validate connector type constraints
 * - Create Connector aggregate via factory method
 * - Persist via ConnectorRepository port
 */
public class CreateConnectorUseCase {

    private final ConnectorRepository connectorRepository;

    public CreateConnectorUseCase(ConnectorRepository connectorRepository) {
        this.connectorRepository = connectorRepository;
    }

    // TODO: Add command record and execute() method
}
