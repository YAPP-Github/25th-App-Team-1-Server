package co.yapp.orbit.fortune.adapter.in;

import co.yapp.orbit.fortune.adapter.in.response.LoadFortuneResponse;
import co.yapp.orbit.fortune.application.port.in.CreateFortuneUseCase;
import co.yapp.orbit.fortune.application.port.in.LoadFortuneCommand;
import co.yapp.orbit.fortune.domain.Fortune;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fortunes")
public class FortuneController {

    private final CreateFortuneUseCase createFortuneUseCase;

    public FortuneController(CreateFortuneUseCase createFortuneUseCase) {
        this.createFortuneUseCase = createFortuneUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createFortune(@RequestParam("userId") Long userId) {
        LoadFortuneCommand command = new LoadFortuneCommand(userId);
        Fortune fortune = createFortuneUseCase.createFortune(command);

        LoadFortuneResponse response = LoadFortuneResponse.of(fortune);

        return ResponseEntity.ok().body(response);
    }
}
