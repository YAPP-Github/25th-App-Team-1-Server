package co.yapp.orbit.prereservation.application.port.out;

import co.yapp.orbit.prereservation.domain.PreReservation;

public interface SavePreReservationPort {
    boolean existsByEmailAndPhoneNumber(String email, String phoneNumber);
    void save(PreReservation preReservation);
}