package co.yapp.orbit.fortune.adapter.out.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CreateFortuneRequest {

    private String name;
    private String birthDate;
    private String birthTime;
    private String calendarType;
    private String gender;

    public CreateFortuneRequest(String name, String birthDate, String birthTime,
        String calendarType,
        String gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.calendarType = calendarType;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "CreateFortuneRequest{" +
            "name='" + name + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", birthTime='" + birthTime + '\'' +
            ", calendarType='" + calendarType + '\'' +
            ", gender='" + gender + '\'' +
            '}';
    }
}
