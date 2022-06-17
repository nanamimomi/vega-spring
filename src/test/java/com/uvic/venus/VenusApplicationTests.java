package com.uvic.venus;

import com.uvic.venus.model.RegisterUserInfo;
import com.uvic.venus.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VenusApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void DummyTest(){
		String username = "username1";
		assertTrue(username == "username1");
	}

	@Test
	void RegisterUserInfoTest(){
		RegisterUserInfo a = new RegisterUserInfo("username", "user",
													"name", "12345");
		assertTrue(a.getUsername() == "username");
	}
}
