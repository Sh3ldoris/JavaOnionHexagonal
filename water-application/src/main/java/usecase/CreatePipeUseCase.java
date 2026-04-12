package usecase;

import com.waterconnect.domain.port.outbound.PipeRepository;

/**
 * Use case: Create a new pipe in the water network.
 *
 * TODO: Implement
 * - Validate pipe specifications (diameter 15-1200mm, pressure > 0)
 * - Create Pipe aggregate via factory method
 * - Persist via PipeRepository port
 */
public class CreatePipeUseCase {

    private final PipeRepository pipeRepository;

    public CreatePipeUseCase(PipeRepository pipeRepository) {
        this.pipeRepository = pipeRepository;
    }

    // TODO: Add command record and execute() method
}
