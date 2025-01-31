package co.yapp.orbit.prereservation.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreReservationRepository extends JpaRepository<PreReservationEntity, Long> {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
}
