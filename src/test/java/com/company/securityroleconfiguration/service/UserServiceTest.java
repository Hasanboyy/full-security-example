package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.UserRepository;
import com.company.securityroleconfiguration.security.JwtUtil;
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
class UserServiceTest {

    @MockBean
    private Jwt jwt;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("success: register user success and success saved to db ")
    void it_should_register() {

    var response = userService.register(MockData.requestUserDto());

    assertNotNull(response);
    assertNotNull(response.getData());
    assertEquals(response.getMessage(),"OK");
    assertEquals(response.getData().getUsername(),"crazyBoy.me");
    assertEquals(response.getData().getFirstname(),"crazy");
    assertEquals(response.getData().getLastname(),"boy");
    }

    @Test
    @DisplayName("fail: this user already registered and you don't save this again")
    void it_should_register_fail(){

        var mockSave = userRepository.save(MockData.user());

        var response = userService.register(MockData.requestUserDto());

        assertNotNull(response);
        assertEquals(response.getCode(), -2);
    }


    @Test
    @DisplayName("fail: login for your registration account fail and returned exception")
    void it_should_login() {

        var mockSave = userRepository.save(MockData.user());

        var response = userService.login(MockData.loginDto());

        assertNotNull(response);
        assertEquals(response.getCode(),0);
        assertEquals(response.getMessage(),"Message");

    }
}