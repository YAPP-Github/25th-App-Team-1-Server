package co.yapp.orbit.prereservation.adapter.out;

import co.yapp.orbit.prereservation.application.port.out.SavePreReservationPort;
import co.yapp.orbit.prereservation.domain.PreReservation;
import org.springframework.stereotype.Component;

@Component
public class PreReservationPersistenceAdapter implements SavePreReservationPort {

    private final PreReservationRepository preReservationRepository;

    public PreReservationPersistenceAdapter(PreReservationRepository preReservationRepository) {
        this.preReservationRepository = preReservationRepository;
    }

    @Override
    public boolean existsByEmailAndPhoneNumber(String email, String phoneNumber) {
        return preReservationRepository.existsByEmailAndPhoneNumber(email, phoneNumber);
    }

    @Override
    public void save(PreReservation preReservation) {
        PreReservationEntity entity = new PreReservationEntity(
            preReservation.getEmail(),
            preReservation.getPhoneNumber()
        );
        preReservationRepository.save(entity);
    }
}