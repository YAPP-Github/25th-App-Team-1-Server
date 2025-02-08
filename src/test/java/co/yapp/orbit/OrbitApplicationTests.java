package co.yapp.orbit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
	"discord.webhook.url=http://dummy-url.com",
	"GEMINI_URL=http://gemini-dummy-url.com",
	"GEMINI_KEY=your-api-key"
})
class OrbitApplicationTests {

	@Test
	void contextLoads() {
	}

}
