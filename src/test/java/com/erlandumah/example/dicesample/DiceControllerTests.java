package com.erlandumah.example.dicesample;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DiceControllerTests {
	@Autowired
	private MockMvc mockMvc;

	//@Test
	//@Order(1)
	//void dicerolltotalcleanup() throws Exception {
	//	mockMvc.perform(MockMvcRequestBuilders.delete("/dicerolltotal?nSides=5&nDice=2").accept(MediaType.APPLICATION_JSON))
	//			.andExpect(status().isOk());
	//}

	@Test
	@Order(2)
	void contextLoads() {
	}

	@Test
	@Order(3)
	void helloControllerWorks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello REST user :)")));
	}

	@Test
	@Order(4)
	void diceControllerWorks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/dice?nSides=5&nDice=2&nRolls=5").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void diceControllerRejectsBadNumberOfSides() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/dice?nSides=3&nDice=2&nRolls=5").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(6)
	void diceControllerRejectsBadNumberOfDice() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/dice?nSides=5&nDice=-5&nRolls=5").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(7)
	void diceControllerRejectsBadNumberOfRolls() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/dice?nSides=5&nDice=2&nRolls=0").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@Order(8)
	void diceControllerDicerollTotalPresentReturnsAvailableData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/dicerolltotal?nSides=5&nDice=2").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(not(equalTo("No data available"))));
	}

	@Test
	@Order(9)
	void diceControllerDicerollTotalAbsentReturnsNoDataAvailable() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/dicerolltotal?nSides=10&nDice=10").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("No data available")));
	}

	@Test
	@Order(10)
	void diceControllerDicerollTotalPercentagePresentReturnsAvailableData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/dicerolltotalpercentage?nSides=5&nDice=2").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(content().string(not(equalTo("No data available"))));
	}

}
