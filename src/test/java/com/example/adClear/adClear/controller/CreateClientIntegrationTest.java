package com.example.adClear.adClear.controller;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.entity.HourlyStat;
import com.example.adClear.adClear.service.HourStatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class CreateClientIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    HourStatService hourStatService;

    @Test
    public void clientRequest() throws Exception {
        given(hourStatService.handleClientRequest(any())).willReturn(new HourlyStat());

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new ClientRequestDto())))
                .andExpect(status().is(400));
    }

//    public static String asJsonString(final Object obj) {
//        try {
//            final ObjectMapper mapper = new ObjectMapper();
//            final String jsonContent = mapper.writeValueAsString(obj);
//            return jsonContent;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}