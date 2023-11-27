package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.UserRepository;
import com.company.securityroleconfiguration.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(username = "crazy")
class UserControllerTest {

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private Jwt jwt;

    @MockBean
    private PasswordEncoder passwordEncoder;


    @Autowired
    private MockMvc webMockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    void deleteAll (){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("success: user register")
    void it_should_register() throws Exception {

        webMockMvc.perform(
                        post("/api/users/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsBytes(MockData.requestUserDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.firstname").value("crazy"))
                .andExpect(jsonPath("$.data.lastname").value("boy"))
                .andExpect(jsonPath("$.data.username").value("crazyBoy.me"));

        userRepository.deleteAll();
    }

    @Test
    @DisplayName("fail: user register this user for username already activated")
    void it_should_register_error() throws Exception {

        var mockSave = userRepository.save(MockData.user());

        webMockMvc.perform(
                        post("/api/users/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsBytes(MockData.requestUserDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-2))
                .andExpect(jsonPath("$.errorList").isNotEmpty());

        userRepository.deleteAll();
    }

    @Test
    @DisplayName("fail: login for your registration account fail and returned exception")
    void it_should_login_fail_for_catch() throws Exception {

        var mockSave = userRepository.save(MockData.user());

        webMockMvc.perform(
                        post("/api/users/login")
                                .content(mapper.writeValueAsBytes(mockSave))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-2));
    }

    @Test
    @DisplayName("fail: login not found in db")
    void it_should_login_fail() throws Exception {

        var mockSave = userRepository.save(MockData.user());

        webMockMvc.perform(
                post("/api/users/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(MockData.loginDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Message"));
    }
}