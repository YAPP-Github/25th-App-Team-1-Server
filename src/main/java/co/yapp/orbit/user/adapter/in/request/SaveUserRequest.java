package co.yapp.orbit.user.adapter.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveUserRequest {

    private String name;
    private String birthDate;
    private String birthTime;
    private String calendarType;
    private String gender;

    public SaveUserRequest(String name, String birthDate, String birthTime, String calendarType, String gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.calendarType = calendarType;
        this.gender = gender;
    }
}
