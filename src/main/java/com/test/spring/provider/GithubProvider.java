package com.test.spring.provider;

import com.alibaba.fastjson.JSON;
import com.test.spring.dto.AccessTokenDto;
import com.test.spring.dto.GithubUserDto;

import org.springframework.stereotype.Component;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType json = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String url = "https://github.com/login/oauth/access_token";

        String text = JSON.toJSONString(accessTokenDto);
        RequestBody body = RequestBody.create(json, text);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        try (Response response = client.newCall(request).execute()) {
            // access_token=78fd9694871a9e9aca8bc0a71938eb8999548732&scope=user&token_type=bearer
            //System.out.println(response.body().string());
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenstr = split[0];
            String token = tokenstr.split("=")[1];
            return token;
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        
    }

    public GithubUserDto getUser(String token) {

        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token=" + token;
        Request request = new Request.Builder()
            .url(url)
            .build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GithubUserDto githubUserDto = JSON.parseObject(str, GithubUserDto.class);
            return githubUserDto;
        } catch (Exception e) {
            //TODO: handle exception
        }
        return null;
        
    }
}
