package co.yapp.orbit.user.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final String name;
    private final LocalDate birthDate;
    private final LocalTime birthTime;
    private final Gender gender;

    public User(Long id, String name, LocalDate birthDate, LocalTime birthTime, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User user))
            return false;
        return Objects.equals(id, user.id)
            && Objects.equals(name, user.name)
            && Objects.equals(birthDate, user.birthDate)
            && Objects.equals(birthTime, user.birthTime)
            && gender == user.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, birthTime, gender);
    }
}