package co.yapp.orbit.user.adapter.out;

import co.yapp.orbit.user.domain.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private LocalTime birthTime;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    protected UserEntity() {
    }

    public UserEntity(String name, LocalDate birthDate, LocalTime birthTime, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.gender = gender;
    }
}
