package com.dnapass.training;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrainingApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void test2(){
		System.out.println("test2 ran");
		assertEquals("Updated Plan", "Updated Plan");
	}

}
