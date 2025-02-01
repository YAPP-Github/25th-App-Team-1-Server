package co.yapp.orbit.prereservation.adapter.out;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pre_reservation")
public class PreReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phoneNumber;

    protected PreReservationEntity() {
    }

    public PreReservationEntity(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}