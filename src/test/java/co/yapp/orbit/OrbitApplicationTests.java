package co.yapp.orbit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"discord.webhook.url=http://dummy-url.com"})
class OrbitApplicationTests {

	@Test
	void contextLoads() {
	}

}
