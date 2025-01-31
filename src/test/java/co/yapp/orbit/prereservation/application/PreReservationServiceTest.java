package co.yapp.orbit.prereservation.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.yapp.orbit.prereservation.application.exception.DuplicatePreReservationException;
import co.yapp.orbit.prereservation.application.port.in.PreReservationCommand;
import co.yapp.orbit.prereservation.application.port.out.SavePreReservationPort;
import co.yapp.orbit.prereservation.domain.PreReservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PreReservationServiceTest {

    private PreReservationService preReservationService;
    private SavePreReservationPort savePreReservationPort;

    @BeforeEach
    void setUp() {
        savePreReservationPort = Mockito.mock(SavePreReservationPort.class);
        preReservationService = new PreReservationService(savePreReservationPort);
    }

    @Test
    @DisplayName("중복되지 않은 사전예약 요청이면 정상적으로 저장되어야 한다.")
    void createPreReservation_whenNotExist_thenSave() {
        // given
        PreReservationCommand command = new PreReservationCommand("홍길동", "010-1234-5678");

        when(savePreReservationPort.existsByNameAndPhoneNumber("홍길동", "010-1234-5678"))
            .thenReturn(false);

        // when
        preReservationService.createPreReservation(command);

        // then
        verify(savePreReservationPort, times(1)).save(any(PreReservation.class));
    }

    @Test
    @DisplayName("이미 동일 정보가 존재하면 예외 발생")
    void createPreReservation_whenExist_thenThrowException() {
        // given
        PreReservationCommand command = new PreReservationCommand("홍길동", "010-1234-5678");

        when(savePreReservationPort.existsByNameAndPhoneNumber("홍길동", "010-1234-5678"))
            .thenReturn(true);

        // when & then
        assertThrows(DuplicatePreReservationException.class, () ->
            preReservationService.createPreReservation(command)
        );

        verify(savePreReservationPort, never()).save(any(PreReservation.class));
    }
}
