package co.yapp.orbit.user.adapter.in.response;

import co.yapp.orbit.user.domain.User;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoadUserResponse {

    private Long id;
    private String name;
    private String birthDate;
    private String birthTime;
    private String calendarType;
    private String gender;

    public LoadUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.birthDate = user.getBirthDate().toString();
        LocalTime birthTime = user.getBirthTime();
        if (birthTime == null) {
            this.birthTime = null;
        } else {
            this.birthTime = birthTime.toString();
        }
        this.gender = user.getGender().toString();
        this.calendarType = user.getCalendarType().toString();
    }
}
