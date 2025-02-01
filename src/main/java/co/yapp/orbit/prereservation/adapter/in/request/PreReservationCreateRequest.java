package co.yapp.orbit.prereservation.adapter.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreReservationCreateRequest {

    private String email;

    private String phoneNumber;

    public PreReservationCreateRequest(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
