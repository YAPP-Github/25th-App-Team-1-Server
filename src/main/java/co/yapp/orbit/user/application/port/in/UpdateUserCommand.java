package co.yapp.orbit.user.application.port.in;

public record UpdateUserCommand(
    String name,
    String birthDate,
    String birthTime,
    String calendarType,
    String gender
) {}
