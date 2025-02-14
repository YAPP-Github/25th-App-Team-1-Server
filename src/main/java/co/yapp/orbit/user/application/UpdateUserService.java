package co.yapp.orbit.user.application;

import co.yapp.orbit.user.application.port.in.UpdateUserCommand;
import co.yapp.orbit.user.application.port.in.UpdateUserUseCase;
import co.yapp.orbit.user.application.port.out.UpdateUserPort;
import co.yapp.orbit.user.domain.CalendarType;
import co.yapp.orbit.user.domain.Gender;
import co.yapp.orbit.user.domain.User;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService implements UpdateUserUseCase {

    private final UpdateUserPort updateUserPort;

    public UpdateUserService(UpdateUserPort updateUserPort) {
        this.updateUserPort = updateUserPort;
    }

    @Transactional
    @Override
    public void updateUser(Long id, UpdateUserCommand command) {
        LocalDate birthDate = LocalDate.parse(command.birthDate());
        LocalTime birthTime = LocalTime.parse(command.birthTime());
        Gender gender = Gender.valueOf(command.gender().toUpperCase());
        CalendarType calendarType = CalendarType.valueOf(command.calendarType().toUpperCase());

        User updatedUser = new User(id, command.name(), birthDate, birthTime, calendarType, gender);
        updateUserPort.update(updatedUser);
    }
}
