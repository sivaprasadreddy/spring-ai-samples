package com.sivalabs.aidemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Disabled
@SpringBootTest
@Import(ContainersConfig.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
