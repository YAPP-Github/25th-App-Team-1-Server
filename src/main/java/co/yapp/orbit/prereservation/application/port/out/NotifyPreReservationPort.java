package co.yapp.orbit.prereservation.application.port.out;

import co.yapp.orbit.prereservation.domain.PreReservation;

public interface NotifyPreReservationPort {
    void notify(PreReservation preReservation);
}
