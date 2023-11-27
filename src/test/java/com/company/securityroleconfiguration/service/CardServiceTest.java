package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.CardRepository;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
@IntegrationTest
@WithMockUser(username = "crazy")
class CardServiceTest {

    @MockBean
    private Jwt jwt;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @AfterEach
    void deleteAll(){
        cardRepository.deleteAll();
    }

    @Test
    @DisplayName("success: create card and success save to db")
    void it_should_create() {

    var response = cardService.create(MockData.requestCardDto());

    assertNotNull(response);
    assertNotNull(response.getData());
    assertEquals(response.getData().getFullName(), "crazy's test");
    assertEquals(response.getData().getCardCode(),"9860");
    assertEquals(response.getData().getCardName(),"crazy");
    assertEquals(response.getData().getCardPassword(),"0101");
    assertEquals(response.getMessage(),"OK");
    }

    @Test
    @DisplayName("fail: get card in db success this card find in db and returned for user ")
    void it_should_get_card(){

        var mockSave = cardRepository.save(MockData.card());

        var response = cardService.getCard(mockSave.getCardId());

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getData().getFullName(), "crazy's test");
        assertEquals(response.getData().getCardCode(),"9860");
        assertEquals(response.getData().getCardName(),"crazy");
        assertEquals(response.getData().getCardPassword(),"0101");
        assertEquals(response.getMessage(),"OK");
    }

    @Test
    @DisplayName("fail: get card in db fail this id's card not found in db ")
    void it_should_get_card_fail() {

        int fakeId = 2234;

        var response = cardService.getCard(fakeId);

        assertNotNull(response);
        assertEquals(response.getCode(),-1);
        assertEquals(response.getMessage(), "Card with 2234 id is not found!");
    }
}