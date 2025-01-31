package co.yapp.orbit.prereservation.application.port.in;

import lombok.Getter;

@Getter
public class PreReservationCommand {
    private String name;
    private String phoneNumber;

    public PreReservationCommand() {
    }

    public PreReservationCommand(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}