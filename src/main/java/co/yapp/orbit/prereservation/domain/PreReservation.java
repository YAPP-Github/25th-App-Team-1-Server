package co.yapp.orbit.prereservation.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class PreReservation {

    private final String name;
    private final String phoneNumber;

    public PreReservation(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreReservation that)) return false;
        return Objects.equals(name, that.name) &&
            Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }
}