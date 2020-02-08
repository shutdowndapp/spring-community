package com.test.spring.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.test.spring.dto.AccessTokenDto;
import com.test.spring.dto.GithubUserDto;
import com.test.spring.mapper.UserMapper;
import com.test.spring.model.User;
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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state,
                            HttpServletRequest request) {
        
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDto githubUserDto = githubProvider.getUser(accessToken);
        System.out.println(githubUserDto.getName());
        String username = githubUserDto.getName();
        
        if (username != null) {
            // login success
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(username);
            user.setAccountId(String.valueOf(githubUserDto.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            // insert user
            userMapper.insert(user);

            request.getSession().setAttribute("user", githubUserDto);
            // redirect
            
        } 

        return "redirect:/";
    }
}