package com.company.securityroleconfiguration.mock;

import com.company.securityroleconfiguration.dto.request.LoginDto;
import com.company.securityroleconfiguration.dto.request.RequestAuthDto;
import com.company.securityroleconfiguration.dto.request.RequestCardDto;
import com.company.securityroleconfiguration.dto.request.RequestUserDto;
import com.company.securityroleconfiguration.module.Authorities;
import com.company.securityroleconfiguration.module.Card;
import com.company.securityroleconfiguration.module.User;

public class MockData {


    public static Card card() {
        var card = new Card();
        card.setCardName("crazy");
        card.setFullName("crazy's test");
        card.setCardCode("9860");
        card.setCardPassword("0101");
        return card;
    }

    public static RequestCardDto requestCardDto() {
        var cardDto = new RequestCardDto();
        cardDto.setCardName("crazy");
        cardDto.setFullName("crazy's test");
        cardDto.setCardCode("9860");
        cardDto.setCardPassword("0101");
        return cardDto;
    }

    public static RequestAuthDto requestAuthDto (){
        var auth = new RequestAuthDto();
        auth.setUsername("crazy");
        auth.setAuthority("ADMIN");
        auth.setUserId(12);
        return auth;
    }
    public static Authorities authorities (){
        var auth = new Authorities();
        auth.setUsername("crazy");
        auth.setAuthority("ADMIN");
        auth.setUserId(12);
        return auth;
    }

    public static RequestUserDto requestUserDto (){
        var user = new RequestUserDto();
        user.setFirstname("crazy");
        user.setLastname("boy");
        user.setUsername("crazyBoy.me");
        user.setPassword("crazy4956");
        return user;
    }

    public static User user (){
        var user = new User();
        user.setFirstname("crazy");
        user.setLastname("boy");
        user.setUsername("crazyBoy.me");
        user.setPassword("crazy4956");
        user.setEnabled(Boolean.TRUE);
        return user;
    }

    public static LoginDto loginDto (){
        var login = new LoginDto();
        login.setPassword("crazy4956");
        login.setUsername("crazy");
        return login;
    }
}
