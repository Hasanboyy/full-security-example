package com.company.securityroleconfiguration.service;

import com.company.securityroleconfiguration.IntegrationTest;
import com.company.securityroleconfiguration.mock.MockData;
import com.company.securityroleconfiguration.repository.AuthorityRepository;
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
class AuthorityServiceTest {

    @MockBean
    private Jwt jwt;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityRepository repository;

    @AfterEach
    void deleteAll() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("success: create authority and success saved for db")
    void it_should_create() {

        var response = authorityService.create(MockData.requestAuthDto());

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getMessage(), "OK");
        assertEquals(response.getData().getAuthority(), "ADMIN");
        assertEquals(response.getData().getUsername(), "crazy");
        assertEquals(response.getData().getUserId(), 12);
    }

    @Test
    @DisplayName("success: get auth in db success get auth and returned for user")
    void it_should_get_auth() {

        var mockSave = repository.save(MockData.authorities());

        var response = authorityService.getAuth(mockSave.getId());

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getMessage(), "OK");
        assertEquals(response.getData().getAuthority(), "ADMIN");
        assertEquals(response.getData().getUsername(), "crazy");
        assertEquals(response.getData().getUserId(), 12);
    }

    @Test
    @DisplayName("fail: get auth in fail because this id's auth not found in db")
    void it_should_get_auth_fail() {

        int fakeId = 213;

        var response = authorityService.getAuth(fakeId);

        assertNotNull(response);
        assertEquals(response.getCode(), -1);
        assertEquals(response.getMessage(), "Authority with 213 : id is not found!");
    }

    @Test
    @DisplayName("success: auth find by id in db and updates datas")
    void it_should_update() {

        var mockSave = repository.save(MockData.authorities());

        var mockDTO = MockData.requestAuthDto();
        mockDTO.setUsername("wengallbe");

        var response = authorityService.update(mockDTO, mockSave.getId());

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getMessage(), "OK");
        assertEquals(response.getData().getAuthority(), "ADMIN");
        assertEquals(response.getData().getUsername(), "wengallbe");
        assertEquals(response.getData().getUserId(), 12);
    }

    @Test
    @DisplayName("fail: update auth fail because this ids data not found in db or not saved")
    void it_should_update_fail() {

        int fakeId = 213;

        var response = authorityService.update(MockData.requestAuthDto(),fakeId);

        assertNotNull(response);
        assertEquals(response.getCode(), -1);
        assertEquals(response.getMessage(), "Authority with 213 : id is not found!");
    }

    @Test
    @DisplayName("success: auth datas deleted successfully")
    void it_should_delete() {

        var mockSave = repository.save(MockData.authorities());

        var response = authorityService.deleteAuth(mockSave.getId());

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(response.getMessage(), "OK");
    }

    @Test
    @DisplayName("fail: delete fail where ids data not found in db and don't delete")
    void it_should_delete_fail() {

        int fakeId = 213;

        var response = authorityService.update(MockData.requestAuthDto(),fakeId);

        assertNotNull(response);
        assertEquals(response.getCode(), -1);
        assertEquals(response.getMessage(), "Authority with 213 : id is not found!");
    }
}