package com.example.adClear.adClear.controller;

import com.example.adClear.adClear.controller.dto.ClientRequestDto;
import com.example.adClear.adClear.entity.HourlyStat;
import com.example.adClear.adClear.service.HourStatService;
import com.example.adClear.adClear.service.data.ClientRequestData;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientRequestController.class)
public class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    HourStatService hourStatService;

    @Test
    public void clientRequestWithNullFields() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.empty());

        final ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto clientRequestDto = getClientRequestDtoSample("1500000000", null, 2, "2", 2);

        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(clientRequestDto)))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[\"remoteIP, must not be null\"],\"status\":400}"));

        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }

    @Test
    public void clientRequestWithNullBody() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.empty());

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(null)))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[\"Http Message Not Readable,not valid parsable body!\"],\"status\":400}"));

        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }

    @Test
    public void requestWithSuccessReturnCode() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.empty());
        final ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto clientRequestDto = getClientRequestDtoSample("1500000000", "RemoteIp", 2, "2", 2);


        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(clientRequestDto)))
                .andExpect(status().is(200));
    }

    private ClientRequestDto getClientRequestDtoSample(String s, String remoteIp, int customerID, String userID, int tagID) {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setTimestamp(s);
        clientRequestDto.setCustomerID(customerID);
        clientRequestDto.setUserID(userID);
        clientRequestDto.setTagID(tagID);
        clientRequestDto.setRemoteIP(remoteIp);
        return clientRequestDto;
    }

    @Test
    public void malformedJson() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.empty());

        final ObjectMapper objectMapper = new ObjectMapper();

        String malformedJson = "{\"\",,}";
        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(malformedJson)))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[\"Http Message Not Readable,not valid parsable body!\"],\"status\":400}"));


        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));
    }

    @Test
    public void wrongTimeStampValue() throws Exception {
        given(hourStatService.handleClientRequest(anyLong(), any())).willReturn(java.util.Optional.empty());
        final ObjectMapper objectMapper = new ObjectMapper();
        ClientRequestDto clientRequestDto = getClientRequestDtoSample("xxxxx", "RemoteIp", 2, "2", 2);


        mockMvc.perform(post("/clients/2/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(clientRequestDto)))
                .andExpect(status().is(400))
                .andExpect(content().string("{\"errors\":[\"timestamp, must match \\\"^([1-9][5-9]\\\\d{8}\\\\d*|\\\\d{11,})$\\\"\"],\"status\":400}"));

        verify(hourStatService, times(1)).addInvalidRequestToStats(anyLong(), any(Timestamp.class));

    }


}