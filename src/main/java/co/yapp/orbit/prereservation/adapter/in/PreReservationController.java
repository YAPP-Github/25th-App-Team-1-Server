package co.yapp.orbit.prereservation.adapter.in;

import co.yapp.orbit.prereservation.adapter.in.request.PreReservationCreateRequest;
import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/prereservations")
@RestController
public class PreReservationController {

    private final CreatePreReservationUseCase createPreReservationUseCase;

    public PreReservationController(CreatePreReservationUseCase createPreReservationUseCase) {
        this.createPreReservationUseCase = createPreReservationUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createPreReservation(@RequestBody PreReservationCreateRequest request) {
        PreReservationCommand command = new PreReservationCommand(
            request.getName(),
            request.getPhoneNumber()
        );
        createPreReservationUseCase.createPreReservation(command);
        return ResponseEntity.ok().build();
    }
}
