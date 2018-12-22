package com.ashim.fxdeals;

import com.ashim.fxdeals.exception.StorageFileNotFoundException;
import com.ashim.fxdeals.service.FxDealService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DealControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FxDealService fxDealService;

	@Test
	public void showUploadSuccessfully() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "sample.csv",
				"text/csv", "Deal Service".getBytes());

		given(this.fxDealService.saveDeal(multipartFile)).willReturn(true);

		this.mvc.perform(multipart("/upload").file(multipartFile))
				.andExpect(status().isFound())
				.andExpect(header().string("Location", "/"))
				.andExpect(flash().attribute("message", "Successfully uploaded sample.csv!"))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void shouldGiveAlreadyUploadedMessage() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "sample.csv",
				"text/csv", "Deal Service".getBytes());

		given(this.fxDealService.saveDeal(multipartFile)).willReturn(false);

		this.mvc.perform(multipart("/upload").file(multipartFile))
				.andExpect(status().isFound())
				.andExpect(header().string("Location", "/"))
				.andExpect(flash().attribute("message", "Already uploaded sample.csv!"))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void shouldThrowIOException() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "sample.csv",
				"text/csv", "Deal Service".getBytes());

		given(this.fxDealService.saveDeal(multipartFile)).willThrow(new IOException());

		this.mvc.perform(multipart("/upload").file(multipartFile))
				.andExpect(status().isFound())
				.andExpect(header().string("Location", "/"))
				.andExpect(flash().attribute("message", "Failed to upload sample.csv!"))
				.andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void should404WhenMissingFile() throws Exception {
		given(this.fxDealService.loadAsResource("sample.csv"))
				.willThrow(StorageFileNotFoundException.class);

		this.mvc.perform(get("/files/sample.csv")).andExpect(status().isNotFound());
	}

}
