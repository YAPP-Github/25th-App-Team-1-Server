package co.yapp.orbit.prereservation.application;

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
        if (savePreReservationPort.existsByNameAndPhoneNumber(command.getName(), command.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 동일한 이름과 전화번호로 사전예약이 존재합니다.");
        }

        PreReservation preReservation = new PreReservation(command.getName(), command.getPhoneNumber());
        savePreReservationPort.save(preReservation);
    }
}
