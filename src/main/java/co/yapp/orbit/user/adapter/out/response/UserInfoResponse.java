package co.yapp.orbit.user.adapter.out.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponse {

    private String name;
    private String birthDate;
    private String birthTime;
    private String calendarType;
    private String gender;

    public UserInfoResponse(String name, String birthDate, String birthTime, String calendarType, String gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.calendarType = calendarType;
        this.gender = gender;
    }
}
