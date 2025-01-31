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
    public boolean existsByNameAndPhoneNumber(String name, String phoneNumber) {
        return preReservationRepository.existsByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void save(PreReservation preReservation) {
        PreReservationEntity entity = new PreReservationEntity(
            preReservation.getName(),
            preReservation.getPhoneNumber()
        );
        preReservationRepository.save(entity);
    }
}