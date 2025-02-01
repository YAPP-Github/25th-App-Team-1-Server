package co.yapp.orbit.prereservation.adapter.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreReservationCreateRequest {

    private String name;

    private String phoneNumber;

    public PreReservationCreateRequest(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
