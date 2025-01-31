package co.yapp.orbit.prereservation.application.port.out;

import co.yapp.orbit.prereservation.domain.PreReservation;

public interface SavePreReservationPort {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
    void save(PreReservation preReservation);
}