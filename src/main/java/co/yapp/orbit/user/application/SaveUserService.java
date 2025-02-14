package co.yapp.orbit.user.application;

import co.yapp.orbit.user.application.port.in.SaveUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserCommand;
import co.yapp.orbit.user.application.port.out.SaveUserPort;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveUserService implements SaveUserUseCase {

    private final SaveUserPort saveUserPort;

    public SaveUserService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Transactional
    @Override
    public Long saveUser(SaveUserCommand command) {
        LocalDate birthDate = LocalDate.parse(command.birthDate());
        LocalTime birthTime = LocalTime.parse(command.birthTime());
        Gender gender = Gender.valueOf(command.gender().toUpperCase());
        CalendarType calendarType = CalendarType.valueOf(command.calendarType().toUpperCase());
        User user = new User(null, command.name(), birthDate, birthTime, calendarType, gender);
        return saveUserPort.save(user);
    }
}
