package co.yapp.orbit.fortune.adapter.out.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFortuneRequest {

    private String name;
    private String birthday;
    private String birthTime;
    private String gender;

    public CreateFortuneRequest(String name, String birthday, String birthTime, String gender) {
        this.name = name;
        this.birthday = birthday;
        this.birthTime = birthTime;
        this.gender = gender;
    }
}
