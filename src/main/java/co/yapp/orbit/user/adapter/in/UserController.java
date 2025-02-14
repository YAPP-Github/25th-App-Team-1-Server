package co.yapp.orbit.user.adapter.in;

import co.yapp.orbit.user.adapter.in.request.SaveUserRequest;
import co.yapp.orbit.user.adapter.in.response.LoadUserResponse;
import co.yapp.orbit.user.application.port.in.LoadUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserUseCase;
import co.yapp.orbit.user.application.port.in.SaveUserCommand;
import co.yapp.orbit.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final SaveUserUseCase createUserUseCase;
    private final LoadUserUseCase getUserUseCase;

    public UserController(SaveUserUseCase createUserUseCase, LoadUserUseCase getUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody SaveUserRequest request) {
        SaveUserCommand command = new SaveUserCommand(
            request.getName(),
            request.getBirthDate(),
            request.getBirthTime(),
            request.getCalendarType(),
            request.getGender()
        );
        Long userId = createUserUseCase.saveUser(command);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoadUserResponse> getUser(@PathVariable("id") Long id) {
        User user = getUserUseCase.loadUser(id);
        LoadUserResponse response = new LoadUserResponse(
            user.getId(),
            user.getName(),
            user.getBirthDate().toString(),
            user.getBirthTime().toString(),
            user.getCalendarType().toString(),
            user.getGender().name()
        );
        return ResponseEntity.ok(response);
    }
}
