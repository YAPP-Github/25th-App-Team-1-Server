package co.yapp.orbit.user.adapter.in.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoadUserResponse {

    private Long id;
    private String name;
    private String birthDate;
    private String birthTime;
    private String gender;

    public LoadUserResponse(Long id, String name, String birthDate, String birthTime, String gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.gender = gender;
    }
}
