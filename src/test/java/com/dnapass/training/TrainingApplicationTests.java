package com.dnapass.training;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainingApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void test2(){
		System.out.println("test2 ran");
	}

	@Test
	void test3(){
		assertEquals("Raju", "Raju");
	}

}