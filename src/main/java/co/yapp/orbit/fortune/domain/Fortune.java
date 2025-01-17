package co.yapp.orbit.fortune.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Fortune {

    @Id
    private Long id;
}
