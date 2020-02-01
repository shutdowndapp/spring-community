package com.test.spring.controller;

import com.test.spring.dto.AccessTokenDto;
import com.test.spring.dto.GithubUserDto;
import com.test.spring.provider.GithubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state) {
        
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDto githubUserDto = githubProvider.getUser(accessToken);
        System.out.println(githubUserDto.getName());
        return "index";
    }
}