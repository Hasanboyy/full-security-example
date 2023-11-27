package com.company.securityroleconfiguration.controller;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.AuthorityRepository;
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
@WithMockUser(authorities = {"ROLE_ADMIN"}, username = "crazy")
class AuthorityControllerTest {

    @MockBean
    private Jwt jwt;

    @Autowired
    private MockMvc webMockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorityRepository repository;

    @Test
    @DisplayName("success: create authority")
    void it_should_create() throws Exception {

        webMockMvc.perform(
                        post("/api/auth")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsBytes(MockData.requestAuthDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.username").value("crazy"))
                .andExpect(jsonPath("$.data.authority").value("ADMIN"));
    }

    @Test
    @DisplayName("success: get auth datas in db successfully")
    void it_should_get_auth() throws Exception {

        var mockSave = repository.save(MockData.authorities());

        webMockMvc.perform(
                        get("/api/auth/{id}", mockSave.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .content(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(mockSave.getId()))
                .andExpect(jsonPath("$.data.username").value("crazy"))
                .andExpect(jsonPath("$.data.authority").value("ADMIN"));
    }

    @Test
    @DisplayName("fail: get authority on this id information not found in db")
    void it_should_get_auth_fail() throws Exception {

        int fakeId = 123;

        webMockMvc.perform(
                get("/api/auth/{id}", fakeId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("Authority with 123 : id is not found!"));
            }

    @Test
    @DisplayName("success: update auth ")
    void it_should_update_auth() throws Exception {

        var mockSave = repository.save(MockData.authorities());

        var mockDTO = MockData.requestAuthDto();
        mockDTO.setAuthority("SUPER_ADMIN");

        webMockMvc.perform(
                put("/api/auth/{id}", mockSave.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(mockDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(mockSave.getId()))
                .andExpect(jsonPath("$.data.authority").value("SUPER_ADMIN"))
                .andExpect(jsonPath("$.data.username").value("crazy"));
    }

    @Test
    @DisplayName("fail: this id's auth not found in db and fail for message NOT FOUND")
    void it_should_update_auth_fail() throws Exception {

        var mockDTO = MockData.requestAuthDto();
        mockDTO.setAuthority("SUPER_ADMIN");

        webMockMvc.perform(
                put("/api/auth/{id}", 4956)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(mockDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("Authority with 4956 : id is not found!"));
    }

    @Test
    @DisplayName("success: get id in db and delete this data")
    void it_should_delete_auth() throws Exception {

        var mockSave = repository.save(MockData.authorities());

        webMockMvc.perform(
                delete("/api/auth/{id}", mockSave.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @DisplayName("fail: get id in db and delete this data fail where id not found in db")
    void it_should_delete_auth_fail() throws Exception {

        webMockMvc.perform(
                        delete("/api/auth/{id}", 4956)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Authority with 4956 : id is not found!"))
                .andExpect(jsonPath("$.success").value(false));
    }
}