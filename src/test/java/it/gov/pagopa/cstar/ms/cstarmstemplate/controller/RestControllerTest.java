package it.gov.pagopa.cstar.ms.cstarmstemplate.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.cstar.ms.cstarmstemplate.model.FileMetadata;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RestControllerImpl.class)
@Slf4j
class RestControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @SpyBean
  RestControllerImpl restController;

  final ObjectMapper mapper = new ObjectMapper();

  static String BASE_URI = "http://localhost:8080";

  static String HEALTHCHECK_ENDPOINT = "/";
  static String HELLO_ENDPOINT = "/hello";
  static String FILE_ENDPOINT = "/file";

  @Test
  void shouldHealthcheck() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_URI + HEALTHCHECK_ENDPOINT)
            .accept(MediaType.TEXT_PLAIN))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "Admin",
      "Foo",
      "Bar"
  })
  void shouldGreet(String name) throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .get(BASE_URI + HELLO_ENDPOINT)
            .param("name", name)
            .accept(MediaType.TEXT_PLAIN))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    assertEquals("Hello " + name + "!", result.getResponse().getContentAsString());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "{\n"
          + "    \"id\": 1,\n"
          + "    \"name\": \"test0\",\n"
          + "    \"hash\": \"abc\"\n"
          + "}",
      "{\n"
          + "    \"id\": 5,\n"
          + "    \"name\": \"test1\",\n"
          + "    \"hash\": \"def\"\n"
          + "}"
  })
  void shouldPutFile(String body) throws Exception {
    FileMetadata f =new FileMetadata(1, "test0", "abc");
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .put(BASE_URI + FILE_ENDPOINT)
            .param("filename", "prova")
            .content(mapper.writeValueAsString(f))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    assertThat(result.getResponse().getContentAsString(), containsString("Server: received FileMetadata"));
  }

}
