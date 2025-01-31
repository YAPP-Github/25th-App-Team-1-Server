package co.yapp.orbit.prereservation.application;

import co.yapp.orbit.prereservation.application.exception.DuplicatePreReservationException;
import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import co.yapp.orbit.prereservation.application.port.out.SavePreReservationPort;
import co.yapp.orbit.prereservation.domain.PreReservation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PreReservationService implements CreatePreReservationUseCase {

    private final SavePreReservationPort savePreReservationPort;

    public PreReservationService(SavePreReservationPort savePreReservationPort) {
        this.savePreReservationPort = savePreReservationPort;
    }

    @Transactional
    @Override
    public void createPreReservation(PreReservationCommand command) {
        if (checkDuplicate(command.getName(), command.getPhoneNumber())) {
            throw new DuplicatePreReservationException("중복된 사전 예약이 이미 존재합니다.");
        }
        PreReservation preReservation = new PreReservation(command.getName(), command.getPhoneNumber());
        savePreReservationPort.save(preReservation);
    }

    private boolean checkDuplicate(String name, String phoneNumber) {
        return savePreReservationPort.existsByNameAndPhoneNumber(name, phoneNumber);
    }
}
