package co.yapp.orbit.prereservation.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class PreReservation {

    private final String email;
    private final String phoneNumber;

    public PreReservation(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreReservation that)) return false;
        return Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }
}