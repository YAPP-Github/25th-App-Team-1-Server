package co.yapp.orbit.user.application.port.in;

public interface UpdateUserUseCase {
    void updateUser(Long id, UpdateUserCommand command);
}
