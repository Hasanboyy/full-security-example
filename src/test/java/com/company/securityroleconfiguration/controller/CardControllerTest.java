package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.CardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ROLE_ADMIN", "ROLE_USER"}, username = "crazy")
class CardControllerTest {

    @MockBean
    private Jwt jwt;

    @Autowired
    private MockMvc webMockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CardRepository cardRepository;

    @Test
    @DisplayName("success: create card and saving to db ")
    void it_should_create() throws Exception {

        webMockMvc.perform(
                        post("/api/card")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsBytes(MockData.requestCardDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.cardName").value("crazy"))
                .andExpect(jsonPath("$.data.fullName").value("crazy's test"))
                .andExpect(jsonPath("$.data.cardCode").value("9860"))
                .andExpect(jsonPath("$.data.cardPassword").value("0101"));
    }

    @Test
    @DisplayName("success: get card's datas in db")
    void it_should_get_card() throws Exception {

        var mockSave = cardRepository.save(MockData.card());

        webMockMvc.perform(
                        get("/api/card/{id}", mockSave.getCardId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.cardId").value(mockSave.getCardId()))
                .andExpect(jsonPath("$.data.cardName").value("crazy"))
                .andExpect(jsonPath("$.data.fullName").value("crazy's test"))
                .andExpect(jsonPath("$.data.cardCode").value("9860"))
                .andExpect(jsonPath("$.data.cardPassword").value("0101"));
    }

    @Test
    @DisplayName("fail: get card's datas in db fail this card with id not found")
    void it_should_get_card_fail() throws Exception {

        webMockMvc.perform(
                get("/api/card/{id}", 2121)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("Card with 2121 id is not found!"));
    }
}