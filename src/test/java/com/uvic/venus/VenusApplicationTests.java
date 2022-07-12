// package com.uvic.venus;

// import com.uvic.venus.model.RegisterUserInfo;
// import com.uvic.venus.model.SecretInfo;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import static org.junit.jupiter.api.Assertions.*;

// import java.io.File;
// import java.util.Date;

// @SpringBootTest
// class VenusApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// 	@Test
// 	void DummyTest(){
// 		String username = "username1";
// 		assertTrue(username == "username1");
// 	}

// 	@Test
// 	void RegisterUserInfoTest(){
// 		RegisterUserInfo a = new RegisterUserInfo("username", "user",
// 													"name", "12345");
// 		assertTrue(a.getUsername() == "username");
// 	}

// 	@Test
// 	void CreatingNewSecret1(){
// 		SecretInfo secret = new SecretInfo("test1", "This is a secret");
// 		assertTrue(secret.getSecretName() == "test1");
// 	}

// 	@Test
// 	void CreatingNewSecret2(){
// 		SecretInfo secret = new SecretInfo("test1", "This is a secret");
// 		assertTrue(secret.getContent() == "This is a secret");

// 	}
// 	@Test
// 	void CreatingNewSecret3(){
// 		SecretInfo secret = new SecretInfo("test1", "This is a secret");
// 		secret.setSecretOwner("ABC");
// 		assertTrue(secret.getSecretOwner() == "ABC");

// 	}
// 	@Test
// 	void CreatingNewSecret4(){
// 		Date now = new Date();
// 		SecretInfo secret = new SecretInfo("test1", "This is a secret");
// 		secret.setDateCreated(now);
// 		assertTrue(secret.getDateCreated() == now);
// 		System.out.println(now);
// 	}

// 	@Test
// 	void CreatingNewSecret5(){
// 		SecretInfo secret = new SecretInfo("test1", "This is a secret");
// 		assertTrue(secret.getFile() == null);
// 	}
// }
