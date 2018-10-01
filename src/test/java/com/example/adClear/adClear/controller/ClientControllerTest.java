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

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    HourStatService hourStatService;

    @Test
    public void clientRequestWithNullFields() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.of(new HourlyStat()));

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new ClientRequestDto())))
                .andExpect(status().is(400));
           verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }

    @Test
    public void clientRequestWithNullBody() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.of(new HourlyStat()));

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(null)))
                .andExpect(status().is(400));
        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }

    @Test
    public void requestWithSuccessReturnCode() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.of(new HourlyStat()));
        final ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setTimestamp("1500000000");
        clientRequestDto.setCustomerID(2);
        clientRequestDto.setUserID("2");
        clientRequestDto.setTagID(2);
        clientRequestDto.setRemoteIP("RemoteIp");


        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(clientRequestDto)))
                .andExpect(status().is(200));
    }

    @Test
    public void malformedJson() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.of(new HourlyStat()));

        final ObjectMapper objectMapper = new ObjectMapper();

        String malformedJson = "{\"\",,}";
        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(malformedJson)))
                .andExpect(status().is(400));

        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));
    }
    @Test
    public void wrongTimeStampValue() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.of(new HourlyStat()));
        final ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setTimestamp("xxxxx");
        clientRequestDto.setCustomerID(2);
        clientRequestDto.setUserID("2");
        clientRequestDto.setTagID(2);
        clientRequestDto.setRemoteIP("RemoteIp");


        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(clientRequestDto)))
                .andExpect(status().is(400));
        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }


}