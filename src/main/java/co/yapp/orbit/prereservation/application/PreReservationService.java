package co.yapp.orbit.prereservation.application;

import co.yapp.orbit.prereservation.application.exception.DuplicatePreReservationException;
import co.yapp.orbit.prereservation.application.port.in.CreatePreReservationUseCase;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import co.yapp.orbit.prereservation.application.port.out.NotifyPreReservationPort;
import co.yapp.orbit.prereservation.application.port.out.SavePreReservationPort;
import co.yapp.orbit.prereservation.domain.PreReservation;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PreReservationService implements CreatePreReservationUseCase {

    private final SavePreReservationPort savePreReservationPort;
    private final NotifyPreReservationPort notifyPreReservationPort; // 추가

    public PreReservationService(
        SavePreReservationPort savePreReservationPort,
        NotifyPreReservationPort notifyPreReservationPort
    ) {
        this.savePreReservationPort = savePreReservationPort;
        this.notifyPreReservationPort = notifyPreReservationPort;
    }

    @Transactional
    @Override
    public void createPreReservation(PreReservationCommand command) {
        if (checkDuplicate(command.email(), command.phoneNumber())) {
            throw new DuplicatePreReservationException("중복된 사전 예약이 이미 존재합니다.");
        }
        PreReservation preReservation = new PreReservation(command.email(), command.phoneNumber());
        savePreReservationPort.save(preReservation);

        notifyPreReservationPort.notify(preReservation);
    }

    private boolean checkDuplicate(String email, String phoneNumber) {
        return savePreReservationPort.existsByEmailAndPhoneNumber(email, phoneNumber);
    }
}
